package top.course.business.controller.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import top.course.server.dto.CourseDto;
import top.course.server.dto.CoursePageDto;
import top.course.server.dto.PageDto;
import top.course.server.dto.ResponseDto;
import top.course.server.enums.CourseStatusEnum;
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


    /**
     * 热门课程查询：报名最多的的3门课程
     * @return 统一返回响应对象(包含热门课程列表)
     */
    @GetMapping("/list-hot")
    public ResponseDto<List<CourseDto>> listHot() {
        PageDto pageDto = new PageDto<>();
        /* 通过设置分页条件获得查询内容 */
        pageDto.setPage(1);
        pageDto.setSize(3);
        ResponseDto<List<CourseDto>> responseDto = new ResponseDto<>();
        List<CourseDto> courseDtoList = courseService.listHot(pageDto);
        responseDto.setContent(courseDtoList);
        return responseDto;
    }


    /**
     * 首页课程数据列表查询: 查询已发布的课程
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody CoursePageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        pageDto.setStatus(CourseStatusEnum.PUBLISH.getCode());
        courseService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }
}
