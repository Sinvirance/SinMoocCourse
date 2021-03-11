package top.course.server.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Sinvirance
 * @Date: 2021/xx/xx xx:xx
 * @Description: Resource数据传输对象
 */

@Getter
@Setter
public class ResourceDto {

    /* id */
    private String id;

    /* 名称|菜单或按钮 */
    private String name;

    /* 页面|路由 */
    private String page;

    /* 请求|接口 */
    private String request;

    /* 父id */
    private String parent;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", page=").append(page);
        sb.append(", request=").append(request);
        sb.append(", parent=").append(parent);
        sb.append("]");
        return sb.toString();
    }

}