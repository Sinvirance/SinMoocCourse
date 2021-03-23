package top.course.server.mapper.my;

import org.apache.ibatis.annotations.Param;
import top.course.server.dto.ResourceDto;

import java.util.List;

/**
 * @Author: Sinvirance
 * @Date: 2021/03/24 01:11
 * @Description: 自定义用户持久层接口
 */

public interface MyUserMapper {

    List<ResourceDto> findResources(@Param("userId") String userId);

}
