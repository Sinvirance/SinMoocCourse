package top.course.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.course.server.domain.Section;
import top.course.server.domain.SectionExample;
import top.course.server.dto.SectionDto;
import top.course.server.dto.PageDto;
import top.course.server.mapper.SectionMapper;
import top.course.server.util.CopyUtil;
import top.course.server.util.UUIDUtil;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Sinvirance
 * @Date: 2021/01/25 20:28
 * @Description: Section持久层接口
 */

@Service
public class SectionService {
    @Resource
    private SectionMapper sectionMapper;

    /**
     * section表列表分页查询
     * @param pageDto 分页组件传输对象
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        SectionExample sectionExample = new SectionExample();
        List<Section> sectionList = sectionMapper.selectByExample(sectionExample);

        PageInfo<Section> pageInfo = new PageInfo<>(sectionList);
        pageDto.setTotal(pageInfo.getTotal());
        List<SectionDto> sectionDtoList = CopyUtil.copyList(sectionList, SectionDto.class);
        pageDto.setList(sectionDtoList);
    }

    /**
     * 保存: SectionDto对象有id属性值时更新，无值时新增
     * @param sectionDto 数据传输对象
     */
    public void save(SectionDto sectionDto) {
        Section section = CopyUtil.copy(sectionDto, Section.class);
        if (StringUtils.isEmpty(sectionDto.getId())) {
            this.insert(section);
        } else {
            this.update(section);
        }
    }

    /**
     * 新增:生成短id作为Section对象id插入section表
     * @param section (无ID)Section对象
     */
    private void insert(Section section) {
        section.setId(UUIDUtil.getShortUUID());
        sectionMapper.insert(section);
    }

    /**
     * 更新:根据Section对象id查询条件修改section表数据
     * @param section (有ID)Section对象
     */
    private void update(Section section) {
        sectionMapper.updateByPrimaryKey(section);
    }

    /**
     * 删除: 根据id删除section表数据
     * @param id id
     */
    public void delete(String id) {
        sectionMapper.deleteByPrimaryKey(id);
    }
}