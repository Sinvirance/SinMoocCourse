package top.course.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

/**
 * @Author: Sinvirance
 * @Date: 2021/01/26 02:31
 * @Description: Section数据传输对象
 */

@Getter
@Setter
public class SectionDto {

    /* 小节Id */
    private String id;

    /* 标题 */
    private String title;

    /* 课程|course.id */
    private String courseId;

    /* 大章|chapter.id */
    private String chapterId;

    /* 视频 */
    private String video;

    /* 时长|单位秒(s) */
    private Integer time;

    /* 收费|C:收费;F:免费 */
    private String charge;

    /* 顺序 */
    private Integer sort;

    /* 创建时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdAt;

    /* 修改时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatedAt;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", courseId=").append(courseId);
        sb.append(", chapterId=").append(chapterId);
        sb.append(", video=").append(video);
        sb.append(", time=").append(time);
        sb.append(", charge=").append(charge);
        sb.append(", sort=").append(sort);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append("]");
        return sb.toString();
    }

}