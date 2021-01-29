package top.course.server.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Sinvirance
 * @Date: 2021/xx/xx xx:xx
 * @Description: CourseCategory数据传输对象
 */

@Getter
@Setter
public class CourseCategoryDto {

    /* id */
    private String id;

    /* 课程|course.id */
    private String courseId;

    /* 分类|course.id */
    private String categoryId;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", courseId=").append(courseId);
        sb.append(", categoryId=").append(categoryId);
        sb.append("]");
        return sb.toString();
    }

}