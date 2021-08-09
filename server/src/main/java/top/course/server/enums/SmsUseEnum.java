package top.course.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Sinvirance
 * @Date: 2021/8/9 16:11
 * @Description: TODO
 */

@Getter
@AllArgsConstructor
public enum SmsUseEnum {

    REGISTER("R", "注册"),
    FORGET("F", "忘记密码");

    private String code;

    private String desc;

}

