package top.course.server.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Sinvirance
 * @Date: 2021/xx/xx xx:xx
 * @Description: CourseContent数据传输对象
 */

@Getter
@Setter
public class CourseContentDto {

    /* 课程id */
    private String id;

    /* 课程内容 */
    private String content;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", content=").append(content);
        sb.append("]");
        return sb.toString();
    }

}