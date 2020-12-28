package top.course.server.service;


import org.springframework.stereotype.Service;
import top.course.server.mapper.TestMapper;

import javax.annotation.Resource;

/**
 * @Author: Sinvirance
 * @Date: 2020/12/28 3:28
 * @Description: TODO
 */

@Service
public class TestService {

    @Resource
    private TestMapper testMapper;
}
