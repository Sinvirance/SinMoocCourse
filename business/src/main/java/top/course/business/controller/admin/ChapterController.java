package top.course.business.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.course.server.dto.PageDto;
import top.course.server.service.ChapterService;

import javax.annotation.Resource;

/**
 * @Author: Sinvirance
 * @Date: 2020/12/27 19:30
 * @Description: 大章查询控制层
 */

@RestController
@RequestMapping(value = "/admin/chapter")
public class ChapterController {
    
    private static final Logger LOG = LoggerFactory.getLogger(ChapterController.class);

    @Resource
    private ChapterService chapterService;

    @RequestMapping(value = "/list")
    public PageDto list(@RequestBody(required = false) PageDto pageDto) {
        LOG.info("pageDTo: {}", pageDto);
        chapterService.list(pageDto);
        return pageDto;
    }
}