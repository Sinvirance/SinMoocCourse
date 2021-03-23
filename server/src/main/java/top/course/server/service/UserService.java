package top.course.server.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.course.server.domain.User;
import top.course.server.domain.UserExample;
import top.course.server.dto.LoginUserDto;
import top.course.server.dto.PageDto;
import top.course.server.dto.ResourceDto;
import top.course.server.dto.UserDto;
import top.course.server.exception.BusinessException;
import top.course.server.exception.BusinessExceptionCode;
import top.course.server.mapper.UserMapper;
import top.course.server.mapper.my.MyUserMapper;
import top.course.server.util.CopyUtil;
import top.course.server.util.UUIDUtil;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;

/**
 * @Author: Sinvirance
 * @Date: 2021/03/07 14:20
 * @Description: User持久层接口
 */

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private MyUserMapper myUserMapper;

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    /**
     * user表列表分页查询
     * @param pageDto 分页组件传输对象
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        UserExample userExample = new UserExample();
        List<User> userList = userMapper.selectByExample(userExample);

        PageInfo<User> pageInfo = new PageInfo<>(userList);
        pageDto.setTotal(pageInfo.getTotal());
        List<UserDto> userDtoList = CopyUtil.copyList(userList, UserDto.class);
        pageDto.setList(userDtoList);
    }

    /**
     * 保存: UserDto对象有id属性值时更新，无值时新增
     * @param userDto 数据传输对象
     */
    public void save(UserDto userDto) {
        User user = CopyUtil.copy(userDto, User.class);
        if (StringUtils.isEmpty(userDto.getId())) {
            this.insert(user);
        } else {
            this.update(user);
        }
    }

    /**
     * 新增:生成短id作为User对象id插入user表
     * @param user (无ID)User对象
     */
    private void insert(User user) {
        user.setId(UUIDUtil.getShortUUID());
        User userDb = this.selectByLoginName(user.getLoginName());
        if (userDb != null) {
            throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
        }
        userMapper.insert(user);
    }

    /**
     * 更新:根据User对象id查询条件修改user表数据
     * @param user (有ID)User对象
     */
    private void update(User user) {
        user.setPassword(null);
        /* updateByPrimaryKeySelective方法对字段进行非空判断，值为空就不更新 */
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 删除: 根据id删除user表数据
     * @param id id
     */
    public void delete(String id) {
        userMapper.deleteByPrimaryKey(id);
    }


    /**
     * 根据登录名查询用户信息
     * @param loginName 用户名
     * @return 存在返回用户信息列表，不存在返回null
     */
    public User selectByLoginName(String loginName) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andLoginNameEqualTo(loginName);
        List<User> userList = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        } else {
            return userList.get(0);
        }
    }


    /**
     * 修改指定用户的密码
     * @param userDto User信息传输对象
     */
    public void savePassword(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setPassword(userDto.getPassword());
        userMapper.updateByPrimaryKeySelective(user);
    }


    /**
     * 用户登录
     * @param userDto User信息传输对象
     * @return User登录信息传输对象
     */
    public LoginUserDto login(UserDto userDto) {
        User user = selectByLoginName(userDto.getLoginName());

        if (user == null) {
            /* 无此用户名 */
            LOG.info("用户名不存在: {}", userDto.getLoginName());
            throw new BusinessException(BusinessExceptionCode.USER_LOGIN_ERROR);
        } else {
            if (user.getPassword().equals(userDto.getPassword())) {
                /* 登录成功 */
                LoginUserDto loginUserDto = CopyUtil.copy(user, LoginUserDto.class);
                /* 为登录用户读取权限 */
                setAuth(loginUserDto);
                return loginUserDto;
            }
            LOG.info("用户密码错误, 输入密码：{}, 数据库密码：{}", userDto.getPassword(), user.getPassword());
            throw new BusinessException(BusinessExceptionCode.USER_LOGIN_ERROR);
        }
    }


    /**
     * 设置登录用户资源认证
     * @param loginUserDto 登录用户前后端传输对象
     */
    private void setAuth(LoginUserDto loginUserDto) {
        /* 获取当前登录用户的资源列表将其放入前后端传输对象中 */
        List<ResourceDto> resourceDtoList = myUserMapper.findResources(loginUserDto.getId());
        loginUserDto.setResources(resourceDtoList);

        HashSet<String> requestSet = new HashSet<>();
        /* 对有资源的请求进行整理 */
        /* 当前端资源非空时 */
        if (!CollectionUtils.isEmpty(resourceDtoList)) {
            for (ResourceDto resourceDto : resourceDtoList) {
                String request = resourceDto.getRequest();
                List<String> requestList = JSON.parseArray(request, String.class);
                /* 当后端请求非空时 */
                if (!CollectionUtils.isEmpty(requestList)) {
                    requestSet.addAll(requestList);
                }
            }
        }
        LOG.info("有权限的请求：{}", requestSet);
        loginUserDto.setRequests(requestSet);
    }
}