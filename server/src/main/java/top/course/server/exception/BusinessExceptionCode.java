package top.course.server.exception;

/**
 * @Author: Sinvirance
 * @Date: 2021/3/7 17:49
 * @Description: 业务异常标识枚举类
 */

public enum BusinessExceptionCode {

    USER_LOGIN_NAME_EXIST("用户名已存在"),
    USER_LOGIN_ERROR("用户名或密码错误"),
    LOGIN_USER_ERROR("用户名不存在或密码错误"),
    LOGIN_MEMBER_ERROR("手机号不存在或密码错误"),
    MOBILE_CODE_TOO_FREQUENT("短信请求过于频繁"),
    MOBILE_CODE_ERROR("短信验证码不正确"),
    MOBILE_CODE_EXPIRED("短信验证码已失效，请重新发送短信"),
    MEMBER_NOT_EXIST("会员不存在"),
    ;

    private String desc;

    BusinessExceptionCode(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
