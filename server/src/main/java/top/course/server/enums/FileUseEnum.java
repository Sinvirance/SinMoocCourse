package top.course.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Sinvirance
 * @Date: 2021/02/09 01:29
 * @Description: 文件表`file`use字段枚举常量类
 */


@Getter
@AllArgsConstructor
public enum FileUseEnum {

    /* 收费课程枚举常量 */
    COURSE("C", "课程"),

    /* 免费课程枚举常量 */
    TEACHER("F", "讲师");

    private String code;

    private String desc;

    /**
     * 根据code获得枚举类型
     */
    public static FileUseEnum getByCode(String code){
        for(FileUseEnum e: FileUseEnum.values()){
            if(code.equals(e.getCode())){
                return e;
            }
        }
        return  null;
    }
}
