package top.course.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Sinvirance
 * @Date: 2021/1/26 20:32
 * @Description: Section功能Charge枚举常量类
 */


@Getter
@AllArgsConstructor
public enum SectionChargeEnum {

    /* 小节收费枚举常量 */
    CHARGER("C", "收费"),
    /* 小节免费枚举常量 */
    FREE("F", "免费");

    private String code;

    private String desc;
}
