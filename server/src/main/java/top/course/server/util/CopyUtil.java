package top.course.server.util;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Sinvirance
 * @Date: 2021/1/23 3:45
 * @Description: 复制对象和对象类别工具类
 */

public class CopyUtil {

    /**
     * 将一个都是A对象的数组列表，根据B的反射对象为模板，复制成B对象数组列表
     * @param source 需要复制的对象数组列表
     * @param clazz 复制后的数组单个元素的反射对象，也就是模板
     * @return 返回复制后对象数组列表
     */
    public static <T> List<T> copyList(List source, Class<T> clazz) {
        List<T> target = new ArrayList<>();
        if (!CollectionUtils.isEmpty(source)){
            if (!CollectionUtils.isEmpty(source)){
                for (Object c: source) {
                    T obj = copy(c, clazz);
                    target.add(obj);
                }
            }
        }
        return target;
    }

    /**
     * 将A对象根据B的反射对象模板复制成和B同一种种类的对象
     * @param source 需要复制的对象
     * @param clazz 复制后的数组单个元素的反射对象，也就是模板
     * @return 复制完成的对象
     */
    public static <T> T copy(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        T obj = null;
        try {
            obj = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BeanUtils.copyProperties(source, obj);
        return obj;
    }
}

