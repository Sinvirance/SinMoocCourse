package top.course.generator.util;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Sinvirance
 * @Date: 2021/1/25 23:12
 * @Description: 数据表字段转Java变量
 */

@Getter
@Setter
public class Field {

    /* 字段名：course_id */
    private String name;

    /* 小驼峰字段名：courseId */
    private String nameHump;

    /* 大驼峰字段名：CourseId */
    private String nameBigHump;

    /* 字段中文名：course -> 课程 */
    private String nameCn;

    /* SQL数据类型：char(8) */
    private String type;

    /* Java类型：String*/
    private String javaType;

    /* 注释：course -> 课程|course.id */
    private String comment;

    /* 是否可为空 */
    private Boolean nullAble;

    /* 字符串长度 */
    private Integer length;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Field{");
        sb.append("name='").append(name).append('\'');
        sb.append(", nameHump='").append(nameHump).append('\'');
        sb.append(", nameBigHump='").append(nameBigHump).append('\'');
        sb.append(", nameCn='").append(nameCn).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", javaType='").append(javaType).append('\'');
        sb.append(", comment='").append(comment).append('\'');
        sb.append('}');
        return sb.toString();
    }
}