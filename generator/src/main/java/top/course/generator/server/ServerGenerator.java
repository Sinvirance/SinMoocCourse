package top.course.generator.server;

import freemarker.template.TemplateException;
import top.course.generator.util.FreemarkerUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Sinvirance
 * @Date: Freemarker 代码生成器启动类
 * @Description: TODO
 */

public class ServerGenerator {

    static String toServicePath = "server\\src\\main\\java\\top\\course\\server\\service\\";

    public static void main(String[] args) throws IOException, TemplateException {

        String Domain="Section";
        String domain="section";
        Map<String, Object> map = new HashMap<>();
        map.put("Domain", Domain);
        map.put("domain", domain);

        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(toServicePath + Domain + "Service.java", map);
    }
}
