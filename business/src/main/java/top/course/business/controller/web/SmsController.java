package top.course.business.controller.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.course.server.dto.ResponseDto;
import top.course.server.dto.SmsDto;
import top.course.server.service.SmsService;

import javax.annotation.Resource;

/**
 * @Author: Sinvirance
 * @Date: 2021/8/9 17:26
 * @Description: 前台web端短信Controller
 */

@RestController("webSmsController")
@RequestMapping("/web/sms")
public class SmsController {

    private static final Logger LOG = LoggerFactory.getLogger(SmsController.class);
    public static final String BUSINESS_NAME = "短信验证码";

    @Resource
    private SmsService smsService;

    /**
     * 发送短信
     * @param smsDto 短信传输对象
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseDto send(@RequestBody SmsDto smsDto) {
        LOG.info("发送短信请求开始: {}", smsDto);
        ResponseDto responseDto = new ResponseDto();
        smsService.sendCode(smsDto);
        LOG.info("发送短信请求结束");
        return responseDto;
    }
}
