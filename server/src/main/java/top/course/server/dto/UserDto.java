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


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", loginName=").append(loginName);
        sb.append(", name=").append(name);
        sb.append(", password=").append(password);
        sb.append("]");
        return sb.toString();
    }

}