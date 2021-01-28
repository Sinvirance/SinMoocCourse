package top.course.server.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Sinvirance
 * @Date: 2021/1/28 3:30
 * @Description: 小节分页数据传输对象
 */

@Getter
@Setter
public class SectionPageDto extends PageDto{

    private String courseId;

    private String chapterId;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SectionPageDto{");
        sb.append("page=").append(page);
        sb.append(", size=").append(size);
        sb.append(", total=").append(total);
        sb.append(", list=").append(list);
        sb.append(", courseId='").append(courseId).append('\'');
        sb.append(", chapterId='").append(chapterId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
