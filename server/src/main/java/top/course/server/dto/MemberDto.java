package top.course.server.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Sinvirance
 * @Date: 2021/xx/xx xx:xx
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


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", mobile=").append(mobile);
        sb.append(", password=").append(password);
        sb.append(", name=").append(name);
        sb.append(", photo=").append(photo);
        sb.append(", registerTime=").append(registerTime);
        sb.append("]");
        return sb.toString();
    }

}