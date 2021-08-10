package top.course.business.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import top.course.server.dto.MemberCourseDto;
import top.course.server.dto.PageDto;
import top.course.server.dto.ResponseDto;
import top.course.server.service.MemberCourseService;
import top.course.server.util.ValidatorUtil;

import javax.annotation.Resource;

/**
 * @Author: Sinvirance
 * @Date: 2021/xx/xx xx:xx
 * @Description: MemberCourse控制层
 */

@RestController
@RequestMapping(value = "/admin/memberCourse")
public class MemberCourseController {

    private static final Logger LOG = LoggerFactory.getLogger(MemberCourseController.class);

    /* MemberCourse控制器 MemberCourseController 标识名 */
    public static final String BUSINESS_NAME = "会员课程报名";

    @Resource
    private MemberCourseService memberCourseService;

    /**
     * 查询: MemberCourse对象分页列表
     * @param pageDto 分页信息传输对象
     * @return 统一返回响应对象
     */
    @PostMapping(value = "/list")
    public ResponseDto<PageDto> list(@RequestBody(required = false) PageDto pageDto) {
        ResponseDto<PageDto> responseDto = new ResponseDto<>();
        memberCourseService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存: MemberCourseDto对象有id属性值时更新，无值时新增
     * @param memberCourseDto 数据传输对象
     * @return 统一返回响应对象
     */
    @PostMapping("/save")
    public ResponseDto<MemberCourseDto> save(@RequestBody MemberCourseDto memberCourseDto) {
        /* 保存校验 */
        ValidatorUtil.require(memberCourseDto.getMemberId(), "会员id");
        ValidatorUtil.require(memberCourseDto.getCourseId(), "课程id");
        ValidatorUtil.require(memberCourseDto.getAt(), "报名时间");

        ResponseDto<MemberCourseDto> responseDto = new ResponseDto<>();
        memberCourseService.save(memberCourseDto);
        responseDto.setContent(memberCourseDto);
        return responseDto;
    }

    /**
     * 删除: 指定id的MemberCourse对象
     * @param id MemberCourse对象id
     * @return 统一返回响应对象
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto<MemberCourseDto> delete(@PathVariable String id) {
        ResponseDto<MemberCourseDto> responseDto = new ResponseDto<>();
        memberCourseService.delete(id);
        return responseDto;
    }
}