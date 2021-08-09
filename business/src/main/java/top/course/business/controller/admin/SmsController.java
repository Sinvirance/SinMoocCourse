package top.course.business.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import top.course.server.dto.SmsDto;
import top.course.server.dto.PageDto;
import top.course.server.dto.ResponseDto;
import top.course.server.service.SmsService;
import top.course.server.util.ValidatorUtil;

import javax.annotation.Resource;

/**
 * @Author: Sinvirance
 * @Date: 2021/xx/xx xx:xx
 * @Description: Sms控制层
 */

@RestController
@RequestMapping(value = "/admin/sms")
public class SmsController {

    private static final Logger LOG = LoggerFactory.getLogger(SmsController.class);

    /* Sms控制器 SmsController 标识名 */
    public static final String BUSINESS_NAME = "短信验证码";

    @Resource
    private SmsService smsService;

    /**
     * 查询: Sms对象分页列表
     * @param pageDto 分页信息传输对象
     * @return 统一返回响应对象
     */
    @PostMapping(value = "/list")
    public ResponseDto<PageDto> list(@RequestBody(required = false) PageDto pageDto) {
        ResponseDto<PageDto> responseDto = new ResponseDto<>();
        smsService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存: SmsDto对象有id属性值时更新，无值时新增
     * @param smsDto 数据传输对象
     * @return 统一返回响应对象
     */
    @PostMapping("/save")
    public ResponseDto<SmsDto> save(@RequestBody SmsDto smsDto) {
        /* 保存校验 */
        ValidatorUtil.require(smsDto.getMobile(), "手机号");
        ValidatorUtil.length(smsDto.getMobile(), "手机号", 1, 50);
        ValidatorUtil.require(smsDto.getCode(), "验证码");
        ValidatorUtil.require(smsDto.getUse(), "用途");
        ValidatorUtil.require(smsDto.getAt(), "生成时间");
        ValidatorUtil.require(smsDto.getStatus(), "状态");

        ResponseDto<SmsDto> responseDto = new ResponseDto<>();
        smsService.save(smsDto);
        responseDto.setContent(smsDto);
        return responseDto;
    }

    /**
     * 删除: 指定id的Sms对象
     * @param id Sms对象id
     * @return 统一返回响应对象
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto<SmsDto> delete(@PathVariable String id) {
        ResponseDto<SmsDto> responseDto = new ResponseDto<>();
        smsService.delete(id);
        return responseDto;
    }
}