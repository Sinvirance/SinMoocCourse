package top.course.server.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Sinvirance
 * @Date: 2021/xx/xx xx:xx
 * @Description: MemberCourse数据传输对象
 */

@Getter
@Setter
public class MemberCourseDto {

    /* id */
    private String id;

    /* 会员id */
    private String memberId;

    /* 课程id */
    private String courseId;

    /* 报名时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date at;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", memberId=").append(memberId);
        sb.append(", courseId=").append(courseId);
        sb.append(", at=").append(at);
        sb.append("]");
        return sb.toString();
    }

}