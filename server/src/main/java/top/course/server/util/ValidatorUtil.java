package top.course.server.util;

import top.course.server.exception.ValidatorException;
import org.springframework.util.StringUtils;

/**
 * @Author: Sinvirance
 * @Date: 2021/1/24 4:34
 * @Description: 后端校验类
 */

public class ValidatorUtil {

    /**
     * 空校验（null or ""）
     * @param str 校验的字段值
     * @param fieldName 校验的字段名
     */
    public static void require(Object str, String fieldName) {
        if (StringUtils.isEmpty(str)) {
            throw new ValidatorException(fieldName + "不能为空");
        }
    }

    /**
     * 字段长度校验,当字段为空时，不进行校验
     * @param str 校验的字段值
     * @param fieldName 校验的字段名
     * @param min 最小长度
     * @param max 最大长度
     */
    public static void length(String str, String fieldName, int min, int max) {
        if (!StringUtils.isEmpty(str)) {
            int length = str.length();
            if (length < min || length > max) {
                throw new ValidatorException(fieldName + "长度应该为" + min + "~" + max + "位");
            }
        }
    }
}
