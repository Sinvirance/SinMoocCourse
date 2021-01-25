package top.course.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.course.server.domain.${Domain};
import top.course.server.domain.${Domain}Example;
import top.course.server.dto.${Domain}Dto;
import top.course.server.dto.PageDto;
import top.course.server.mapper.${Domain}Mapper;
import top.course.server.util.CopyUtil;
import top.course.server.util.UUIDUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Sinvirance
 * @Date: 2021/xx/xx xx:xx
 * @Description: ${Domain}持久层接口
 */

@Service
public class ${Domain}Service {
    @Resource
    private ${Domain}Mapper ${domain}Mapper;

    /**
     * ${domain}表列表分页查询
     * @param pageDto 分页组件传输对象
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        List<${Domain}> ${domain}List = ${domain}Mapper.selectByExample(${domain}Example);

        PageInfo<${Domain}> pageInfo = new PageInfo<>(${domain}List);
        pageDto.setTotal(pageInfo.getTotal());
        List<${Domain}Dto> ${domain}DtoList = CopyUtil.copyList(${domain}List, ${Domain}Dto.class);
        pageDto.setList(${domain}DtoList);
    }

    /**
     * 保存: ${Domain}Dto对象有id属性值时更新，无值时新增
     * @param ${Domain}Dto 数据传输对象
     */
    public void save(${Domain}Dto ${domain}Dto) {
        ${Domain} ${domain} = CopyUtil.copy(${domain}Dto, ${Domain}.class);
        if (StringUtils.isEmpty(${domain}Dto.getId())) {
            this.insert(${domain});
        } else {
            this.update(${domain});
        }
    }

    /**
     * 新增:生成短id作为${Domain}对象id插入${domain}表
     * @param ${domain} (无ID)${Domain}对象
     */
    private void insert(${Domain} ${domain}) {
        ${domain}.setId(UUIDUtil.getShortUUID());
        ${domain}Mapper.insert(${domain});
    }

    /**
     * 更新:根据${Domain}对象id查询条件修改${domain}表数据
     * @param ${domain} (有ID)${Domain}对象
     */
    private void update(${Domain} ${domain}) {
        ${domain}Mapper.updateByPrimaryKey(${domain});
    }

    /**
     * 删除: 根据id删除${domain}表数据
     * @param id id
     */
    public void delete(String id) {
        ${domain}Mapper.deleteByPrimaryKey(id);
    }
}