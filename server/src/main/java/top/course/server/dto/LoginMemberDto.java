package top.course.server.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Sinvirance
 * @Date: 2021/8/7 18:11
 * @Description: 登录成功会员数据传输对象
 */

@Getter
@Setter
public class LoginMemberDto {

    private String id;

    private String mobile;

    private String name;

    private String token;

    private String photo;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LoginMemberDto{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", mobile='").append(mobile).append('\'');
        sb.append(", token='").append(token).append('\'');
        sb.append(", photo='").append(photo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
