package top.course.business.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import top.course.server.dto.TeacherDto;
import top.course.server.dto.PageDto;
import top.course.server.dto.ResponseDto;
import top.course.server.service.TeacherService;
import top.course.server.util.ValidatorUtil;

import javax.annotation.Resource;

/**
 * @Author: Sinvirance
 * @Date: 2021/01/30 21:05
 * @Description: Teacher控制层
 */

@RestController
@RequestMapping(value = "/admin/teacher")
public class TeacherController {

    private static final Logger LOG = LoggerFactory.getLogger(TeacherController.class);

    /* Teacher控制器 TeacherController 标识名 */
    public static final String BUSINESS_NAME = "讲师";

    @Resource
    private TeacherService teacherService;

    /**
     * 查询: Teacher对象分页列表
     * @param pageDto 分页信息传输对象
     * @return 统一返回响应对象
     */
    @PostMapping(value = "/list")
    public ResponseDto<PageDto> list(@RequestBody(required = false) PageDto pageDto) {
        ResponseDto<PageDto> responseDto = new ResponseDto<>();
        teacherService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存: TeacherDto对象有id属性值时更新，无值时新增
     * @param teacherDto 数据传输对象
     * @return 统一返回响应对象
     */
    @PostMapping("/save")
    public ResponseDto<TeacherDto> save(@RequestBody TeacherDto teacherDto) {
        /* 保存校验 */
        ValidatorUtil.require(teacherDto.getName(), "姓名");
        ValidatorUtil.length(teacherDto.getName(), "姓名", 1, 50);
        ValidatorUtil.length(teacherDto.getNickname(), "昵称", 1, 50);
        ValidatorUtil.length(teacherDto.getImage(), "头像", 1, 100);
        ValidatorUtil.length(teacherDto.getPosition(), "职位", 1, 50);
        ValidatorUtil.length(teacherDto.getMotto(), "座右铭", 1, 50);
        ValidatorUtil.length(teacherDto.getIntro(), "简介", 1, 500);

        ResponseDto<TeacherDto> responseDto = new ResponseDto<>();
        teacherService.save(teacherDto);
        responseDto.setContent(teacherDto);
        return responseDto;
    }

    /**
     * 删除: 指定id的Teacher对象
     * @param id Teacher对象id
     * @return 统一返回响应对象
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto<TeacherDto> delete(@PathVariable String id) {
        ResponseDto<TeacherDto> responseDto = new ResponseDto<>();
        teacherService.delete(id);
        return responseDto;
    }
}