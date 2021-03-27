package top.course.business.controller.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.course.server.dto.CourseDto;
import top.course.server.dto.PageDto;
import top.course.server.dto.ResponseDto;
import top.course.server.service.CourseService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Sinvirance
 * @Date: 2021/3/28 0:15
 * @Description: 前台Course控制层
 */

/* Spring默认采用类名(不含包名)管理Bean：这里使用webCourseController作为管理名 */
@RestController("webCourseController")
@RequestMapping("/web/course")
public class CourseController {

    private static final Logger LOG = LoggerFactory.getLogger(CourseController.class);

    public static final String BUSINESS_NAME = "课程";

    @Resource
    private CourseService courseService;


    /**
     * 新课程查询：最新的3门课程
     * @return 统一返回响应对象(包含新课列表)
     */
    @GetMapping("/list-new")
    public ResponseDto<List<CourseDto>> listNew() {
        PageDto pageDto = new PageDto<>();
        /* 通过设置分页条件获得查询内容 */
        pageDto.setPage(1);
        pageDto.setSize(3);
        ResponseDto<List<CourseDto>> responseDto = new ResponseDto<>();
        List<CourseDto> courseDtoList = courseService.listNew(pageDto);
        responseDto.setContent(courseDtoList);
        return responseDto;
    }
}
