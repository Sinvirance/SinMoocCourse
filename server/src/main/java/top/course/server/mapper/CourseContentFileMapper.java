package top.course.server.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import top.course.server.domain.CourseContentFile;
import top.course.server.domain.CourseContentFileExample;

public interface CourseContentFileMapper {
    long countByExample(CourseContentFileExample example);

    int deleteByExample(CourseContentFileExample example);

    int deleteByPrimaryKey(String id);

    int insert(CourseContentFile record);

    int insertSelective(CourseContentFile record);

    List<CourseContentFile> selectByExample(CourseContentFileExample example);

    CourseContentFile selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CourseContentFile record, @Param("example") CourseContentFileExample example);

    int updateByExample(@Param("record") CourseContentFile record, @Param("example") CourseContentFileExample example);

    int updateByPrimaryKeySelective(CourseContentFile record);

    int updateByPrimaryKey(CourseContentFile record);
}