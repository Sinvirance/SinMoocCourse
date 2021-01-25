package top.course.generator.server;

import freemarker.template.TemplateException;
import top.course.generator.util.FreemarkerUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Sinvirance
 * @Date: 2021/01/25 18:05
 * @Description: Freemarker 代码生成器启动类
 */

public class ServerGenerator {

    static String toServicePath = "server\\src\\main\\java\\top\\course\\server\\service\\";
    static String toControllerPath = "business\\src\\main\\java\\top\\course\\business\\controller\\admin\\";

    public static void main(String[] args) throws IOException, TemplateException {

        String Domain="Section";
        String domain="section";
        Map<String, Object> map = new HashMap<>();
        map.put("Domain", Domain);
        map.put("domain", domain);

        /* 生成Service层 */
        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(toServicePath + Domain + "Service.java", map);

        /* 生成Controller层 */
        FreemarkerUtil.initConfig("controller.ftl");
        FreemarkerUtil.generator(toControllerPath + Domain + "Controller.java", map);
    }
}
