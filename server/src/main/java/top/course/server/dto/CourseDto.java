package top.course.server.dto;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Sinvirance
 * @Date: 2021/xx/xx xx:xx
 * @Description: Course数据传输对象
 */

@Getter
@Setter
public class CourseDto {

    /* id */
    private String id;

    /* 名称 */
    private String name;

    /* 概述 */
    private String summary;

    /* 时长|单位秒 */
    private Integer time;

    /* 价格（元） */
    private BigDecimal price;

    /* 封面 */
    private String image;

    /* 级别|ONE("1", "初级"),TWO("2", "中级"),THREE("3", "高级") */
    private String level;

    /* 收费|CHARGE("C", "收费"),FREE("F", "免费") */
    private String charge;

    /* 状态|PUBLISH("P", "发布"),DRAFT("D", "草稿") */
    private String status;

    /* 报名数 */
    private Integer enroll;

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
        sb.append(", name=").append(name);
        sb.append(", summary=").append(summary);
        sb.append(", time=").append(time);
        sb.append(", price=").append(price);
        sb.append(", image=").append(image);
        sb.append(", level=").append(level);
        sb.append(", charge=").append(charge);
        sb.append(", status=").append(status);
        sb.append(", enroll=").append(enroll);
        sb.append(", sort=").append(sort);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append("]");
        return sb.toString();
    }

}