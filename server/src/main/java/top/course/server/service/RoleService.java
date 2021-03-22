package top.course.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.course.server.domain.*;
import top.course.server.dto.PageDto;
import top.course.server.dto.RoleDto;
import top.course.server.mapper.RoleMapper;
import top.course.server.mapper.RoleResourceMapper;
import top.course.server.mapper.RoleUserMapper;
import top.course.server.util.CopyUtil;
import top.course.server.util.UUIDUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Sinvirance
 * @Date: 2021/xx/xx xx:xx
 * @Description: Role持久层接口
 */

@Service
public class RoleService {
    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleResourceMapper roleResourceMapper;

    @Resource
    private RoleUserMapper roleUserMapper;

    /**
     * role表列表分页查询
     * @param pageDto 分页组件传输对象
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RoleExample roleExample = new RoleExample();
        List<Role> roleList = roleMapper.selectByExample(roleExample);

        PageInfo<Role> pageInfo = new PageInfo<>(roleList);
        pageDto.setTotal(pageInfo.getTotal());
        List<RoleDto> roleDtoList = CopyUtil.copyList(roleList, RoleDto.class);
        pageDto.setList(roleDtoList);
    }

    /**
     * 保存: RoleDto对象有id属性值时更新，无值时新增
     * @param roleDto 数据传输对象
     */
    public void save(RoleDto roleDto) {
        Role role = CopyUtil.copy(roleDto, Role.class);
        if (StringUtils.isEmpty(roleDto.getId())) {
            this.insert(role);
        } else {
            this.update(role);
        }
    }

    /**
     * 新增:生成短id作为Role对象id插入role表
     * @param role (无ID)Role对象
     */
    private void insert(Role role) {
        role.setId(UUIDUtil.getShortUUID());
        roleMapper.insert(role);
    }

    /**
     * 更新:根据Role对象id查询条件修改role表数据
     * @param role (有ID)Role对象
     */
    private void update(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    /**
     * 删除: 根据id删除role表数据
     * @param id id
     */
    public void delete(String id) {
        roleMapper.deleteByPrimaryKey(id);
    }

    /**
     * 按角色保存资源
     * @param roleDto 具有角色对应资源的角色前后端传输对象
     */
    @Transactional
    public void saveResource(RoleDto roleDto) {
        String roleId = roleDto.getId();
        List<String> resourceIds = roleDto.getResourceIds();
        // 清空库中所有的当前角色下的记录
        RoleResourceExample example = new RoleResourceExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        roleResourceMapper.deleteByExample(example);

        // 保存角色资源
        for (int i = 0; i < resourceIds.size(); i++) {
            RoleResource roleResource = new RoleResource();
            roleResource.setId(UUIDUtil.getShortUUID());
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(resourceIds.get(i));
            roleResourceMapper.insert(roleResource);
        }
    }


    /**
     * 保存: 按角色保存用户到数据表role_user
     * @param roleDto 具有角色对应用户的角色前后端传输对象
     */
    public void saveUser(RoleDto roleDto) {
        String roleId = roleDto.getId();
        List<String> userIdList = roleDto.getUserIds();
        // 清空库中所有的当前角色下的记录
        RoleUserExample example = new RoleUserExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        roleUserMapper.deleteByExample(example);

        // 保存角色用户
        for (String s : userIdList) {
            RoleUser roleUser = new RoleUser();
            roleUser.setId(UUIDUtil.getShortUUID());
            roleUser.setRoleId(roleId);
            roleUser.setUserId(s);
            roleUserMapper.insert(roleUser);
        }
    }

}