package top.course.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.course.server.domain.Resource;
import top.course.server.domain.ResourceExample;
import top.course.server.dto.ResourceDto;
import top.course.server.dto.PageDto;
import top.course.server.mapper.ResourceMapper;
import top.course.server.util.CopyUtil;
import top.course.server.util.UUIDUtil;

import java.util.List;

/**
 * @Author: Sinvirance
 * @Date: 2021/03/11 15:41
 * @Description: Resource持久层接口
 */

@Service
public class ResourceService {
    @javax.annotation.Resource
    private ResourceMapper resourceMapper;

    /**
     * resource表列表分页查询
     * @param pageDto 分页组件传输对象
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        ResourceExample resourceExample = new ResourceExample();
        List<Resource> resourceList = resourceMapper.selectByExample(resourceExample);

        PageInfo<Resource> pageInfo = new PageInfo<>(resourceList);
        pageDto.setTotal(pageInfo.getTotal());
        List<ResourceDto> resourceDtoList = CopyUtil.copyList(resourceList, ResourceDto.class);
        pageDto.setList(resourceDtoList);
    }

    /**
     * 保存: ResourceDto对象有id属性值时更新，无值时新增
     * @param resourceDto 数据传输对象
     */
    public void save(ResourceDto resourceDto) {
        Resource resource = CopyUtil.copy(resourceDto, Resource.class);
        if (StringUtils.isEmpty(resourceDto.getId())) {
            this.insert(resource);
        } else {
            this.update(resource);
        }
    }

    /**
     * 新增:生成短id作为Resource对象id插入resource表
     * @param resource (无ID)Resource对象
     */
    private void insert(Resource resource) {
        resource.setId(UUIDUtil.getShortUUID());
        resourceMapper.insert(resource);
    }

    /**
     * 更新:根据Resource对象id查询条件修改resource表数据
     * @param resource (有ID)Resource对象
     */
    private void update(Resource resource) {
        resourceMapper.updateByPrimaryKey(resource);
    }

    /**
     * 删除: 根据id删除resource表数据
     * @param id id
     */
    public void delete(String id) {
        resourceMapper.deleteByPrimaryKey(id);
    }
}