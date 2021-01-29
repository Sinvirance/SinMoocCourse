package top.course.server.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import top.course.server.domain.CourseCategory;
import top.course.server.domain.CourseCategoryExample;

public interface CourseCategoryMapper {
    long countByExample(CourseCategoryExample example);

    int deleteByExample(CourseCategoryExample example);

    int deleteByPrimaryKey(String id);

    int insert(CourseCategory record);

    int insertSelective(CourseCategory record);

    List<CourseCategory> selectByExample(CourseCategoryExample example);

    CourseCategory selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CourseCategory record, @Param("example") CourseCategoryExample example);

    int updateByExample(@Param("record") CourseCategory record, @Param("example") CourseCategoryExample example);

    int updateByPrimaryKeySelective(CourseCategory record);

    int updateByPrimaryKey(CourseCategory record);
}