package top.course.server.exception;

/**
 * @Author: Sinvirance
 * @Date: 2021/3/7 17:49
 * @Description: 业务异常标识枚举类
 */

public enum BusinessExceptionCode {

    USER_LOGIN_NAME_EXIST("登录名已存在"),
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
