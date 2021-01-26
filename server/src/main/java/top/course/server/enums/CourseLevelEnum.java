package top.course.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Sinvirance
 * @Date: 2021/1/26 20:32
 * @Description: 课程表`course`level字段枚举常量类
 */


@Getter
@AllArgsConstructor
public enum CourseLevelEnum {

    /* level1枚举常量 */
    ONE("1", "初级"),

    /* level2枚举常量 */
    TWO("2", "中级"),

    /* level2枚举常量 */
    THREE("3", "高级");

    private String code;

    private String desc;
}
