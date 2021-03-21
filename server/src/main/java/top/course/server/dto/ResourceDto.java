package top.course.server.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    /* 嵌套子节点 */
    private List<ResourceDto> children;


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ResourceDto{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", page='").append(page).append('\'');
        sb.append(", request='").append(request).append('\'');
        sb.append(", parent='").append(parent).append('\'');
        sb.append(", children=").append(children);
        sb.append('}');
        return sb.toString();
    }
}