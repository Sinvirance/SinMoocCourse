package top.course.generator.vue;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import top.course.generator.util.DbUtil;
import top.course.generator.util.Field;
import top.course.generator.util.FreemarkerUtil;

import java.io.File;
import java.util.*;

/**
 * @Author: Sinvirance
 * @Date: 2021/01/25 18:05
 * @Description: Freemarker 前端Vue框架代码生成器启动类
 */

public class VueGenerator {

    static String MODULE = "file";
    static String toVuePath = "admin\\src\\views\\admin\\";
    static String generatorConfigPath = "server\\src\\main\\resources\\generator\\generatorConfig.xml";

    public static void main(String[] args) throws Exception {
        String module = MODULE;

        // 只生成配置文件中的第一个table节点
        File file = new File(generatorConfigPath);
        SAXReader reader=new SAXReader();
        /* 读取XML文件,获得document对象 */
        Document doc=reader.read(file);

        /* 获取xml文件的根节点 */
        Element rootElement=doc.getRootElement();
        /* 读取context节点 */
        Element contextElement = rootElement.element("context");
        //定义一个Element用于遍历
        Element tableElement;
        /* 获取子节点context下的子节点table的一个迭代器, 迭代器将table节点的每一个元素取出来转化为Element类型 */
        tableElement=contextElement.elementIterator("table").next();

        /* 取出节点属性名为domainObjectName的属性值 */
        String Domain = tableElement.attributeValue("domainObjectName");
        /* 取出节点属性名为tableName的属性值 */
        String tableName = tableElement.attributeValue("tableName");
        String tableNameCn = DbUtil.getTableComment(tableName);
        String domain = Domain.substring(0, 1).toLowerCase() + Domain.substring(1);
        System.out.println("表："+tableElement.attributeValue("tableName"));
        System.out.println("Domain："+tableElement.attributeValue("domainObjectName"));

        /* 获取数据表所有列的成员变量File对象列表 */
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

        /* 生成Vue */
        FreemarkerUtil.initConfig("vue.ftl");
        FreemarkerUtil.generator(toVuePath + domain + ".vue", map);
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
