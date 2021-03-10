package top.course.server.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Sinvirance
 * @Date: 2021/03/07 14:20
 * @Description: User登录数据传输对象
 */

@Getter
@Setter
public class LoginUserDto {

    /* id */
    private String id;

    /* 登陆名 */
    private String loginName;

    /* 昵称 */
    private String name;

    /* 登录token */
    private String token;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LoginUserDto{");
        sb.append("id='").append(id).append('\'');
        sb.append(", loginName='").append(loginName).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", token='").append(token).append('\'');
        sb.append('}');
        return sb.toString();
    }

}