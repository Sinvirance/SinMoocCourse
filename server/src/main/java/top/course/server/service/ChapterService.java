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
 * @Description: 大章持久层接口
 */

@Service
public class ChapterService {
    @Resource
    private ChapterMapper chapterMapper;

    /**
     * 通过PageHelper分页列表显示数据库大章数据
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
     * 根据传输对象中是否包含 id 来判断是新增保存，还是更新保存
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
     * 根据前端传递过来的大章对象，生成短id，并保存到数据库中
     * @param chapter 大章对象(无ID)
     */
    private void insert(Chapter chapter) {
        chapter.setId(UUIDUtil.getShortUUID());
        chapterMapper.insert(chapter);
    }

    /**
     * 根据前端传递过来的大章对象，根据短id，到数据库中修改指定对象并保存
     * @param chapter 大章对象(有ID)
     */
    private void update(Chapter chapter) {
        chapterMapper.updateByPrimaryKey(chapter);
    }

    /**
     * 从数据库中删除指定id大章数据
     * @param id 大章id
     */
    public void delete(String id) {
        chapterMapper.deleteByPrimaryKey(id);
    }
}