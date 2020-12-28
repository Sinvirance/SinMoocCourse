package top.course.server.service;


import org.springframework.stereotype.Service;
import top.course.server.domain.Test;
import top.course.server.mapper.TestMapper;

import javax.annotation.Resource;
import java.util.List;

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
