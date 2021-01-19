package top.course.server.service;


import org.springframework.stereotype.Service;
import top.course.server.domain.Chapter;
import top.course.server.domain.ChapterExample;
import top.course.server.mapper.ChapterMapper;

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

    public List<Chapter> list() {
        ChapterExample chapterExample = new ChapterExample();
        chapterExample.createCriteria().andIdEqualTo("1");
        chapterExample.setOrderByClause("id desc");
        return chapterMapper.selectByExample(chapterExample);
    }
}