package top.course.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Sinvirance
 * @Date: 2021/1/26 20:32
 * @Description: 课程表`course`status字段枚举常量类
 */


@Getter
@AllArgsConstructor
public enum CourseStatusEnum {

    /* 发布状态枚举常量 */
    PUBLISH("P", "发布"),

    /* 草稿状态枚举常量 */
    DRAFT("D", "草稿");

    private String code;

    private String desc;
}
