package top.course.system.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.course.server.dto.ResponseDto;

/**
 * @Author: Sinvirance
 * @Date: 2021/8/19 16:31
 * @Description: Nacos 配置中心测试
 */

@RefreshScope
@RestController
@RequestMapping("/test")
public class TestController {

    @Value("${system.test}")
    private String systemTest;

    @GetMapping("/list")
    public ResponseDto list() {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(systemTest);
        return responseDto;
    }
}
