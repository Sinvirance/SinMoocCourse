package top.course.business.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import top.course.server.dto.*;
import top.course.server.service.CourseCategoryService;
import top.course.server.service.CourseService;
import top.course.server.util.ValidatorUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Sinvirance
 * @Date: 2021/01/29 01:14
 * @Description: Course控制层
 */

@RestController
@RequestMapping(value = "/admin/course")
public class CourseController {

    private static final Logger LOG = LoggerFactory.getLogger(CourseController.class);

    /* Course控制器 CourseController 标识名 */
    public static final String BUSINESS_NAME = "课程";

    @Resource
    private CourseService courseService;

    @Resource
    private CourseCategoryService courseCategoryService;

    /**
     * 查询: Course对象分页列表
     * @param pageDto 分页信息传输对象
     * @return 统一返回响应对象
     */
    @PostMapping(value = "/list")
    public ResponseDto<PageDto> list(@RequestBody(required = false) CoursePageDto pageDto) {
        ResponseDto<PageDto> responseDto = new ResponseDto<>();
        courseService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存: CourseDto对象有id属性值时更新，无值时新增
     * @param courseDto 数据传输对象
     * @return 统一返回响应对象
     */
    @PostMapping("/save")
    public ResponseDto<CourseDto> save(@RequestBody CourseDto courseDto) {
        /* 保存校验 */
        ValidatorUtil.require(courseDto.getName(), "名称");
        ValidatorUtil.length(courseDto.getName(), "名称", 1, 50);
        ValidatorUtil.length(courseDto.getSummary(), "概述", 1, 2000);
        ValidatorUtil.length(courseDto.getImage(), "封面", 1, 100);

        ResponseDto<CourseDto> responseDto = new ResponseDto<>();
        courseService.save(courseDto);
        responseDto.setContent(courseDto);
        return responseDto;
    }

    /**
     * 删除: 指定id的Course对象
     * @param id Course对象id
     * @return 统一返回响应对象
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto<CourseDto> delete(@PathVariable String id) {
        ResponseDto<CourseDto> responseDto = new ResponseDto<>();
        courseService.delete(id);
        return responseDto;
    }


    /**
     * 查找: 课程的所有分类
     * @param courseId 查找的Course对象id
     */
    @PostMapping("/list-category/{courseId}")
    public ResponseDto<List<CourseCategoryDto>> listCategory(@PathVariable(value = "courseId") String courseId) {
        ResponseDto<List<CourseCategoryDto>> responseDto = new ResponseDto<>();
        List<CourseCategoryDto> dtoList = courseCategoryService.listByCourse(courseId);
        responseDto.setContent(dtoList);
        return responseDto;
    }

    /**
     * 查找: 课程内容
     * @param id 查找的Course对象id
     * @return 统一返回响应对象
     */
    @GetMapping("/find-content/{id}")
    public ResponseDto<CourseContentDto> findContent(@PathVariable String id) {
        ResponseDto<CourseContentDto> responseDto = new ResponseDto<>();
        CourseContentDto courseContentDto = courseService.findContent(id);
        responseDto.setContent(courseContentDto);
        return responseDto;
    }

    /**
     * 保存: 课程内容
     * @param courseContentDto 课程内容数据传输对象
     * @return 统一返回响应对象
     */
    @PostMapping("/save-content")
    public ResponseDto<CourseContentDto> saveContent(@RequestBody CourseContentDto courseContentDto) {
        ResponseDto<CourseContentDto> responseDto = new ResponseDto<>();
        courseService.saveContent(courseContentDto);
        return responseDto;
    }

    /**
     * 更新: 课程顺序
     * @param sortDto 课程顺序传输对象
     * @return 统一返回响应对象
     */
    @RequestMapping(value = "/sort")
    public ResponseDto sort(@RequestBody SortDto sortDto) {
        LOG.info("更新排序");
        ResponseDto responseDto = new ResponseDto();
        courseService.sort(sortDto);
        return responseDto;
    }
}