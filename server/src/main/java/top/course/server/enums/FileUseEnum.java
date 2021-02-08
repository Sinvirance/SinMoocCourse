package top.course.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Sinvirance
 * @Date: 2021/02/09 01:29
 * @Description: 文件表`file`use字段枚举常量类
 */


@Getter
@AllArgsConstructor
public enum FileUseEnum {

    /* 收费课程枚举常量 */
    COURSE("C", "讲师"),

    /* 免费课程枚举常量 */
    TEACHER("F", "课程");

    private String code;

    private String desc;
}
