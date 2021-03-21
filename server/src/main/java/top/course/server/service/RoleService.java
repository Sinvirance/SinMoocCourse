package top.course.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.course.server.domain.Role;
import top.course.server.domain.RoleExample;
import top.course.server.dto.RoleDto;
import top.course.server.dto.PageDto;
import top.course.server.mapper.RoleMapper;
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
}