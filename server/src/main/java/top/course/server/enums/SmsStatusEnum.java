package top.course.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Sinvirance
 * @Date: 2021/8/9 16:14
 * @Description: TODO
 */


@Getter
@AllArgsConstructor
public enum SmsStatusEnum {

    USED("U", "已使用"),
    NOT_USED("N", "未使用");

    private String code;

    private String desc;

}

