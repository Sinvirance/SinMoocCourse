package top.course.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.course.server.domain.RoleUser;
import top.course.server.domain.RoleUserExample;
import top.course.server.dto.PageDto;
import top.course.server.dto.RoleUserDto;
import top.course.server.mapper.RoleUserMapper;
import top.course.server.util.CopyUtil;
import top.course.server.util.UUIDUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Sinvirance
 * @Date: 2021/xx/xx xx:xx
 * @Description: RoleUser持久层接口
 */

@Service
public class RoleUserService {
    @Resource
    private RoleUserMapper roleUserMapper;

    /**
     * roleUser表列表分页查询
     * @param pageDto 分页组件传输对象
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RoleUserExample roleUserExample = new RoleUserExample();
        List<RoleUser> roleUserList = roleUserMapper.selectByExample(roleUserExample);

        PageInfo<RoleUser> pageInfo = new PageInfo<>(roleUserList);
        pageDto.setTotal(pageInfo.getTotal());
        List<RoleUserDto> roleUserDtoList = CopyUtil.copyList(roleUserList, RoleUserDto.class);
        pageDto.setList(roleUserDtoList);
    }

    /**
     * 保存: RoleUserDto对象有id属性值时更新，无值时新增
     * @param roleUserDto 数据传输对象
     */
    public void save(RoleUserDto roleUserDto) {
        RoleUser roleUser = CopyUtil.copy(roleUserDto, RoleUser.class);
        if (StringUtils.isEmpty(roleUserDto.getId())) {
            this.insert(roleUser);
        } else {
            this.update(roleUser);
        }
    }

    /**
     * 新增:生成短id作为RoleUser对象id插入roleUser表
     * @param roleUser (无ID)RoleUser对象
     */
    private void insert(RoleUser roleUser) {
        roleUser.setId(UUIDUtil.getShortUUID());
        roleUserMapper.insert(roleUser);
    }

    /**
     * 更新:根据RoleUser对象id查询条件修改roleUser表数据
     * @param roleUser (有ID)RoleUser对象
     */
    private void update(RoleUser roleUser) {
        roleUserMapper.updateByPrimaryKey(roleUser);
    }

    /**
     * 删除: 根据id删除roleUser表数据
     * @param id id
     */
    public void delete(String id) {
        roleUserMapper.deleteByPrimaryKey(id);
    }


    /**
     * 查询: 角色Id对应所有用户
     * @param roleId 角色对应Id
     * @return 角色对应的用户列表 userIdList
     */
    public List<String> listUser(String roleId) {
        RoleUserExample example = new RoleUserExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<RoleUser> roleUserList = roleUserMapper.selectByExample(example);
        List<String> userIdList = new ArrayList<>();
        for (int i = 0, l = roleUserList.size(); i < l; i++) {
            userIdList.add(roleUserList.get(i).getUserId());
        }
        return userIdList;
    }
}