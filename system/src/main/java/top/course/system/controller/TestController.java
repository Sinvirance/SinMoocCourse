package top.course.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Sinvirance
 * @Date: 2020/12/27 19:30
 * @Description: 测试 Spring Boot 项目
 */

@RestController
public class TestController {

    @RequestMapping("/test")
    public String test() {
        return "success";
    }
}