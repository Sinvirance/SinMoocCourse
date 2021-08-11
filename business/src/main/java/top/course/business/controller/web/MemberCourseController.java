package top.course.business.controller.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import top.course.server.dto.MemberCourseDto;
import top.course.server.dto.ResponseDto;
import top.course.server.service.MemberCourseService;
import top.course.server.util.ValidatorUtil;

import javax.annotation.Resource;

/**
 * @Author: Sinvirance
 * @Date: 2021/8/10 17:59
 * @Description: 前台 MemberCourse 控制层
 */


@RestController("webMemberCourseController")
@RequestMapping("/web/member-course")
public class MemberCourseController {

    private static final Logger LOG = LoggerFactory.getLogger(MemberCourseController.class);
    public static final String BUSINESS_NAME = "会员课程报名";

    @Resource
    private MemberCourseService memberCourseService;

    /**
     * web端会员报名课程
     */
    @PostMapping("/enroll")
    public ResponseDto enroll(@RequestBody MemberCourseDto memberCourseDto) {
        /* 保存校验 */
        ValidatorUtil.require(memberCourseDto.getMemberId(), "会员id");
        ValidatorUtil.require(memberCourseDto.getCourseId(), "课程id");

        ResponseDto responseDto = new ResponseDto();
        memberCourseDto = memberCourseService.enroll(memberCourseDto);
        responseDto.setContent(memberCourseDto);
        return responseDto;
    }

    /**
     * web 端会员取消报名课程
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        memberCourseService.delete(id);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/get-enroll")
    public ResponseDto getEnroll(@RequestBody MemberCourseDto memberCourseDto) {
        ResponseDto responseDto = new ResponseDto();
        memberCourseDto = memberCourseService.getEnroll(memberCourseDto);
        responseDto.setContent(memberCourseDto);
        return responseDto;
    }
}
