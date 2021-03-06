package top.course.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Sinvirance
 * @Date: 2021/1/26 20:32
 * @Description: 小节表`section`charge字段枚举常量类
 */


@Getter
@AllArgsConstructor
public enum SectionChargeEnum {

    /* 收费小节枚举常量 */
    CHARGER("C", "收费"),

    /* 免费小节枚举常量 */
    FREE("F", "免费");

    private String code;

    private String desc;
}
