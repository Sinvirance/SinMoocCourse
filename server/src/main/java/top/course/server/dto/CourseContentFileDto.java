package top.course.server.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Sinvirance
 * @Date: 2021/xx/xx xx:xx
 * @Description: CourseContentFile数据传输对象
 */

@Getter
@Setter
public class CourseContentFileDto {

    /* id */
    private String id;

    /* 课程id */
    private String courseId;

    /* 地址 */
    private String url;

    /* 文件名 */
    private String name;

    /* 大小|字节b */
    private Integer size;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", courseId=").append(courseId);
        sb.append(", url=").append(url);
        sb.append(", name=").append(name);
        sb.append(", size=").append(size);
        sb.append("]");
        return sb.toString();
    }

}