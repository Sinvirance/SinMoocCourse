package top.course.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.course.server.domain.User;
import top.course.server.domain.UserExample;
import top.course.server.dto.UserDto;
import top.course.server.dto.PageDto;
import top.course.server.mapper.UserMapper;
import top.course.server.util.CopyUtil;
import top.course.server.util.UUIDUtil;

import javax.annotation.Resource;
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
        userMapper.insert(user);
    }

    /**
     * 更新:根据User对象id查询条件修改user表数据
     * @param user (有ID)User对象
     */
    private void update(User user) {
        userMapper.updateByPrimaryKey(user);
    }

    /**
     * 删除: 根据id删除user表数据
     * @param id id
     */
    public void delete(String id) {
        userMapper.deleteByPrimaryKey(id);
    }
}