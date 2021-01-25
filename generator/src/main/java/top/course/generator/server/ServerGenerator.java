package top.course.generator.server;

import top.course.generator.util.DbUtil;
import top.course.generator.util.Field;
import top.course.generator.util.FreemarkerUtil;

import java.util.*;

/**
 * @Author: Sinvirance
 * @Date: 2021/01/25 18:05
 * @Description: Freemarker 代码生成器启动类
 */

public class ServerGenerator {

    static String MODULE = "business";
    static String toServicePath = "server\\src\\main\\java\\top\\course\\server\\service\\";
    static String toDtoPath = "server\\src\\main\\java\\top\\course\\server\\dto\\";
    static String toControllerPath = MODULE + "\\src\\main\\java\\top\\course\\" + MODULE + "\\controller\\admin\\";

    public static void main(String[] args) throws Exception {

        String Domain = "Section";
        String domain = "section";
        String tableNameCn = "小节";

        String module = MODULE;
        List<Field> fieldList = DbUtil.getColumnByTableName(domain);
        /* 用于dto import，一种数据类型只需要import一次 */
        Set<String> typeSet = getJavaTypes(fieldList);

        Map<String, Object> map = new HashMap<>();
        map.put("Domain", Domain);
        map.put("domain", domain);
        map.put("tableNameCn",tableNameCn);
        map.put("module", module);
        map.put("fieldList", fieldList);
        map.put("typeSet", typeSet);

        /* 生成Service层 */
        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(toServicePath + Domain + "Service.java", map);

        /* 生成Controller层 */
        FreemarkerUtil.initConfig("controller.ftl");
        FreemarkerUtil.generator(toControllerPath + Domain + "Controller.java", map);

        /* 生成Dto层 */
        FreemarkerUtil.initConfig("dto.ftl");
        FreemarkerUtil.generator(toDtoPath + Domain + "Dto.java", map);
    }

    /**
     * 用Set去重数据表中得到的Java类型
     * @param fieldList 数据表字段成员对象
     * @return 不重复的Java数据类型集合
     */
    private static Set<String> getJavaTypes(List<Field> fieldList) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            set.add(field.getJavaType());
        }
        return set;
    }
}
