package top.course.server.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.course.server.domain.Resource;
import top.course.server.domain.ResourceExample;
import top.course.server.dto.PageDto;
import top.course.server.dto.ResourceDto;
import top.course.server.mapper.ResourceMapper;
import top.course.server.util.CopyUtil;

import java.util.ArrayList;
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
    
    private static final Logger LOG = LoggerFactory.getLogger(ResourceService.class);

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
     * 新增: 作为Resource对象id插入resource表
     * @param resource (无ID)Resource对象
     */
    private void insert(Resource resource) {
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


    /**
     * 保存资源树
     * @param json json格式的资源树列表
     */
    @Transactional
    public void saveJson(String json) {
        List<ResourceDto> jsonList = JSON.parseArray(json, ResourceDto.class);
        List<ResourceDto> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jsonList)) {
            for (ResourceDto d: jsonList) {
                d.setParent("");
                add(list, d);
            }
        }
        LOG.info("共{}条", list.size());

        resourceMapper.deleteByExample(null);
        for (int i = 0; i < list.size(); i++) {
            this.insert(CopyUtil.copy(list.get(i), Resource.class));
        }
    }

    /**
     * 递归，将树型结构的节点全部取出来，放到list
     * @param list
     * @param dto
     */
    public void add(List<ResourceDto> list, ResourceDto dto) {
        list.add(dto);
        if (!CollectionUtils.isEmpty(dto.getChildren())) {
            for (ResourceDto d: dto.getChildren()) {
                d.setParent(dto.getId());
                add(list, d);
            }
        }
    }
}