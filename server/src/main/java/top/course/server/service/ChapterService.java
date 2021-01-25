package top.course.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.course.server.domain.Chapter;
import top.course.server.domain.ChapterExample;
import top.course.server.dto.ChapterDto;
import top.course.server.dto.PageDto;
import top.course.server.mapper.ChapterMapper;
import top.course.server.util.CopyUtil;
import top.course.server.util.UUIDUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Sinvirance
 * @Date: 2020/12/28 3:28
 * @Description: Chapter持久层接口
 */

@Service
public class ChapterService {
    @Resource
    private ChapterMapper chapterMapper;

    /**
     * chapter表列表分页查询
     * @param pageDto 分页组件传输对象
     */
    public void list(PageDto pageDto) {
        // startPage 会对最近的第一个Select语句进行分页
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        ChapterExample chapterExample = new ChapterExample();
        List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);

        PageInfo<Chapter> pageInfo = new PageInfo<>(chapterList);
        pageDto.setTotal(pageInfo.getTotal());
        List<ChapterDto> chapterDtoList = CopyUtil.copyList(chapterList, ChapterDto.class);
        pageDto.setList(chapterDtoList);
    }

    /**
     * 保存: ChapterDto对象有id属性值时更新，无值时新增
     * @param chapterDto 大章数据传输对象
     */
    public void save(ChapterDto chapterDto) {
        Chapter chapter = CopyUtil.copy(chapterDto, Chapter.class);
        if (StringUtils.isEmpty(chapterDto.getId())) {
            this.insert(chapter);
        } else {
            this.update(chapter);
        }
    }

    /**
     * 新增:生成短id作为Chapter对象id插入chapter表
     * @param chapter (无ID)Chapter对象
     */
    private void insert(Chapter chapter) {
        chapter.setId(UUIDUtil.getShortUUID());
        chapterMapper.insert(chapter);
    }

    /**
     * 更新:根据Chapter对象id查询条件修改chapter表数据
     * @param chapter (有ID)Chapter对象
     */
    private void update(Chapter chapter) {
        chapterMapper.updateByPrimaryKey(chapter);
    }

    /**
     * 删除: 根据id删除chapter表数据
     * @param id 大章id
     */
    public void delete(String id) {
        chapterMapper.deleteByPrimaryKey(id);
    }
}