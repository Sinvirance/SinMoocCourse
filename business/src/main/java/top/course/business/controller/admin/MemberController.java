package top.course.business.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.course.server.dto.PageDto;
import top.course.server.dto.ResponseDto;
import top.course.server.service.MemberService;

import javax.annotation.Resource;

/**
 * @Author: Sinvirance
 * @Date: 2021/xx/xx xx:xx
 * @Description: Member控制层
 */

@RestController
@RequestMapping(value = "/admin/member")
public class MemberController {

    private static final Logger LOG = LoggerFactory.getLogger(MemberController.class);

    /* Member控制器 MemberController 标识名 */
    public static final String BUSINESS_NAME = "会员";

    @Resource
    private MemberService memberService;

    /**
     * 查询: Member对象分页列表
     * @param pageDto 分页信息传输对象
     * @return 统一返回响应对象
     */
    @PostMapping(value = "/list")
    public ResponseDto<PageDto> list(@RequestBody(required = false) PageDto pageDto) {
        ResponseDto<PageDto> responseDto = new ResponseDto<>();
        memberService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }
}