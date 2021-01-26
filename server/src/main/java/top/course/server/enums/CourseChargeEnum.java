package top.course.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Sinvirance
 * @Date: 2021/1/26 20:32
 * @Description: 课程表`course`charge字段枚举常量类
 */


@Getter
@AllArgsConstructor
public enum CourseChargeEnum {

    /* 收费课程枚举常量 */
    CHARGER("C", "收费"),

    /* 免费课程枚举常量 */
    FREE("F", "免费");

    private String code;

    private String desc;
}
