package top.course.business.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import top.course.server.dto.CourseContentFileDto;
import top.course.server.dto.ResponseDto;
import top.course.server.service.CourseContentFileService;
import top.course.server.util.ValidatorUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Sinvirance
 * @Date: 2021/02/09 13:04
 * @Description: CourseContentFile控制层
 */

@RestController
@RequestMapping(value = "/admin/course-content-file")
public class CourseContentFileController {

    private static final Logger LOG = LoggerFactory.getLogger(CourseContentFileController.class);

    /* CourseContentFile控制器 CourseContentFileController 标识名 */
    public static final String BUSINESS_NAME = "课程内容文件";

    @Resource
    private CourseContentFileService courseContentFileService;

    /**
     * 查询: CourseContentFile对象列表
     * @param courseId 根据课程id查询对应课程内容
     * @return 统一返回响应对象
     */
    @GetMapping("/list/{courseId}")
    public ResponseDto list(@PathVariable String courseId) {
        ResponseDto responseDto = new ResponseDto();
        List<CourseContentFileDto> fileDtoList = courseContentFileService.list(courseId);
        responseDto.setContent(fileDtoList);
        return responseDto;
    }

    /**
     * 保存: CourseContentFileDto对象有id属性值时更新，无值时新增
     * @param courseContentFileDto 数据传输对象
     * @return 统一返回响应对象
     */
    @PostMapping("/save")
    public ResponseDto<CourseContentFileDto> save(@RequestBody CourseContentFileDto courseContentFileDto) {
        /* 保存校验 */
        ValidatorUtil.require(courseContentFileDto.getCourseId(), "课程id");
        ValidatorUtil.length(courseContentFileDto.getUrl(), "地址", 1, 100);
        ValidatorUtil.length(courseContentFileDto.getName(), "文件名", 1, 100);

        ResponseDto<CourseContentFileDto> responseDto = new ResponseDto<>();
        courseContentFileService.save(courseContentFileDto);
        responseDto.setContent(courseContentFileDto);
        return responseDto;
    }

    /**
     * 删除: 指定id的CourseContentFile对象
     * @param id CourseContentFile对象id
     * @return 统一返回响应对象
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto<CourseContentFileDto> delete(@PathVariable String id) {
        ResponseDto<CourseContentFileDto> responseDto = new ResponseDto<>();
        courseContentFileService.delete(id);
        return responseDto;
    }
}