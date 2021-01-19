package top.course.server.service;


import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.course.server.domain.Chapter;
import top.course.server.domain.ChapterExample;
import top.course.server.dto.ChapterDto;
import top.course.server.mapper.ChapterMapper;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    public List<ChapterDto> list() {
        ChapterExample chapterExample = new ChapterExample();
        List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);
        List<ChapterDto> chapterDtoList = new ArrayList<>();
        for (int i = 0, l = chapterList.size(); i < l ; i++) {
            Chapter chapter = chapterList.get(i);
            ChapterDto chapterDto = new ChapterDto();
            // 将chapter的属性值复制到数据传输对象chapterDto
            BeanUtils.copyProperties(chapter, chapterDto);
            chapterDtoList.add(chapterDto);
        }
        return chapterDtoList;
    }
}