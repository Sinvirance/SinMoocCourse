package top.course.server.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @Author: Sinvirance
 * @Date: 2021/3/28 5:06
 * @Description: 时间处理工具类
 */

public class DateUtil {


    public static Date BeforeMonthDate(Integer month) {
        Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
        ca.setTime(new Date()); // 设置时间为当前时间
        ca.add(Calendar.MONTH, -month);// 月份减1
        return ca.getTime(); // 结果
    }
}
