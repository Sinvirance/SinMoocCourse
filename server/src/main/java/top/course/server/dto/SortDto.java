package top.course.server.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Sinvirance
 * @Date: 2021/1/30 15:13
 * @Description: 排序字段传输对象
 */

@Getter
@Setter
public class SortDto {

    /* 课程id */
    private String id;

    /* 排序前字段 */
    private int oldSort;

    /* 排序后字段 */
    private int newSort;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SortDto{");
        sb.append("id='").append(id).append('\'');
        sb.append(", oldSort=").append(oldSort);
        sb.append(", newSort=").append(newSort);
        sb.append('}');
        return sb.toString();
    }
}
