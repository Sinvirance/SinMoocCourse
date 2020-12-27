package top.course.system.service;

import org.springframework.stereotype.Service;
import top.course.system.domain.Test;
import top.course.system.mapper.TestMapper;

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

    public List<Test> list() {
        return testMapper.list();
    }
}
