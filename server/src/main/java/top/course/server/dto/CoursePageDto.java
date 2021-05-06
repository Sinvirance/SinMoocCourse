package top.course.server.dto;

/**
 * @Author: Sinvirance
 * @Date: 2021/5/7 7:00
 * @Description: TODO
 */

public class CoursePageDto extends PageDto {

    /* 课程状态：已发布，草稿 */
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CoursePageDto{");
        sb.append("status='").append(status).append('\'');
        sb.append(", page=").append(page);
        sb.append(", size=").append(size);
        sb.append(", total=").append(total);
        sb.append(", list=").append(list);
        sb.append('}');
        return sb.toString();
    }
}
