package top.course.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: Sinvirance
 * @Date: 2021/08/08 16:17
 * @Description: Member数据传输对象
 */

@Getter
@Setter
public class MemberDto {

    /* id */
    private String id;

    /* 手机号 */
    private String mobile;

    /* 密码 */
    private String password;

    /* 昵称 */
    private String name;

    /* 头像url */
    private String photo;

    /* 注册时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date registerTime;

    /* 图片验证码 */
    private String imageCode;

    /* 图片验证码token */
    private String imageCodeToken;

    /* 短信验证码 */
    private String smsCode;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MemberDto{");
        sb.append("id='").append(id).append('\'');
        sb.append(", mobile='").append(mobile).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", photo='").append(photo).append('\'');
        sb.append(", registerTime=").append(registerTime);
        sb.append(", imageCode='").append(imageCode).append('\'');
        sb.append(", imageCodeToken='").append(imageCodeToken).append('\'');
        sb.append(", smsCode='").append(smsCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}