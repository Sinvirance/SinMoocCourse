package top.course.server.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author: Sinvirance
 * @Date: 2021/03/23 01:28
 * @Description: Role数据传输对象
 */

@Getter
@Setter
public class RoleDto {

    /* id */
    private String id;

    /* 角色 */
    private String name;

    /* 描述 */
    private String desc;

    /* 角色对应资源 */
    private List<String> resourceIds;


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RoleDto{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", resourceIds=").append(resourceIds);
        sb.append('}');
        return sb.toString();
    }

}