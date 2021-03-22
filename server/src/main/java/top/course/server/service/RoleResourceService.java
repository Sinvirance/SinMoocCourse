package top.course.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.course.server.domain.RoleResource;
import top.course.server.domain.RoleResourceExample;
import top.course.server.dto.PageDto;
import top.course.server.dto.RoleResourceDto;
import top.course.server.mapper.RoleResourceMapper;
import top.course.server.util.CopyUtil;
import top.course.server.util.UUIDUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Sinvirance
 * @Date: 2021/xx/xx xx:xx
 * @Description: RoleResource持久层接口
 */

@Service
public class RoleResourceService {
    @Resource
    private RoleResourceMapper roleResourceMapper;

    /**
     * roleResource表列表分页查询
     * @param pageDto 分页组件传输对象
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        RoleResourceExample roleResourceExample = new RoleResourceExample();
        List<RoleResource> roleResourceList = roleResourceMapper.selectByExample(roleResourceExample);

        PageInfo<RoleResource> pageInfo = new PageInfo<>(roleResourceList);
        pageDto.setTotal(pageInfo.getTotal());
        List<RoleResourceDto> roleResourceDtoList = CopyUtil.copyList(roleResourceList, RoleResourceDto.class);
        pageDto.setList(roleResourceDtoList);
    }

    /**
     * 保存: RoleResourceDto对象有id属性值时更新，无值时新增
     * @param roleResourceDto 数据传输对象
     */
    public void save(RoleResourceDto roleResourceDto) {
        RoleResource roleResource = CopyUtil.copy(roleResourceDto, RoleResource.class);
        if (StringUtils.isEmpty(roleResourceDto.getId())) {
            this.insert(roleResource);
        } else {
            this.update(roleResource);
        }
    }

    /**
     * 新增:生成短id作为RoleResource对象id插入roleResource表
     * @param roleResource (无ID)RoleResource对象
     */
    private void insert(RoleResource roleResource) {
        roleResource.setId(UUIDUtil.getShortUUID());
        roleResourceMapper.insert(roleResource);
    }

    /**
     * 更新:根据RoleResource对象id查询条件修改roleResource表数据
     * @param roleResource (有ID)RoleResource对象
     */
    private void update(RoleResource roleResource) {
        roleResourceMapper.updateByPrimaryKey(roleResource);
    }

    /**
     * 删除: 根据id删除roleResource表数据
     * @param id id
     */
    public void delete(String id) {
        roleResourceMapper.deleteByPrimaryKey(id);
    }


    /**
     * 查询: 对应角色的所有资源
     * @param roleId 角色对应Id
     * @return 角色对应资源列表 resourceIdList
     */
    public List<String> listResource(String roleId) {
        RoleResourceExample example = new RoleResourceExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<RoleResource> roleResourceList = roleResourceMapper.selectByExample(example);
        List<String> resourceIdList = new ArrayList<>();
        for (RoleResource roleResource : roleResourceList) {
            resourceIdList.add(roleResource.getResourceId());
        }
        return resourceIdList;
    }
}