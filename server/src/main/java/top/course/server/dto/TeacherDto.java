package top.course.server.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Sinvirance
 * @Date: 2021/xx/xx xx:xx
 * @Description: Teacher数据传输对象
 */

@Getter
@Setter
public class TeacherDto {

    /* id */
    private String id;

    /* 姓名 */
    private String name;

    /* 昵称 */
    private String nickname;

    /* 头像 */
    private String image;

    /* 职位 */
    private String position;

    /* 座右铭 */
    private String motto;

    /* 简介 */
    private String intro;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", nickname=").append(nickname);
        sb.append(", image=").append(image);
        sb.append(", position=").append(position);
        sb.append(", motto=").append(motto);
        sb.append(", intro=").append(intro);
        sb.append("]");
        return sb.toString();
    }

}