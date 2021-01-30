package top.course.server.mapper.my;

import org.apache.ibatis.annotations.Param;
import top.course.server.dto.SortDto;

/**
 * @Author: Sinvirance
 * @Date: 2021/1/28 20:46
 * @Description: 自定义Course持久层接口
 */

public interface MyCourseMapper {

    int updateTime(@Param("courseId") String courseId);

    void updateSort(SortDto sortDto);

    void moveSortsForward(SortDto sortDto);

    void moveSortsBackward(SortDto sortDto);
}
