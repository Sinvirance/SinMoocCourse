package top.course.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: Sinvirance
 * @Date: 2021/xx/xx xx:xx
 * @Description: Sms数据传输对象
 */

@Getter
@Setter
public class SmsDto {

    /* id */
    private String id;

    /* 手机号 */
    private String mobile;

    /* 验证码 */
    private String code;

    /* 用途|枚举[SmsUseEnum]：REGISTER("R", "注册"), FORGET("F", "忘记密码") */
    private String use;

    /* 生成时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date at;

    /* 状态|枚举[SmsStatusEnum]：USED("U", "已使用"), NOT_USED("N", "未使用") */
    private String status;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", mobile=").append(mobile);
        sb.append(", code=").append(code);
        sb.append(", use=").append(use);
        sb.append(", at=").append(at);
        sb.append(", status=").append(status);
        sb.append("]");
        return sb.toString();
    }

}