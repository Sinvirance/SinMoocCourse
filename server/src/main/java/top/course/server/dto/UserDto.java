package top.course.server.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Sinvirance
 * @Date: 2021/03/07 14:20
 * @Description: User数据传输对象
 */

@Getter
@Setter
public class UserDto {

    /* id */
    private String id;

    /* 登陆名 */
    private String loginName;

    /* 昵称 */
    private String name;

    /* 密码 */
    private String password;

    /* 登录验证码 */
    private String imageCode;

    /* 图像验证码Token */
    private String imageCodeToken;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserDto{");
        sb.append("id='").append(id).append('\'');
        sb.append(", loginName='").append(loginName).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", imageCode='").append(imageCode).append('\'');
        sb.append(", imageCodeToken='").append(imageCodeToken).append('\'');
        sb.append('}');
        return sb.toString();
    }
}