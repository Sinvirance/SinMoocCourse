package top.course.generator.server;

import freemarker.template.TemplateException;
import top.course.generator.util.FreemarkerUtil;

import java.io.IOException;

/**
 * @Author: Sinvirance
 * @Date: 2021/1/25 19:11
 * @Description: TODO
 */

public class ServerGenerator {

    static String toPath = "generator\\src\\main\\java\\top\\course\\generator\\test\\";

    public static void main(String[] args) throws IOException, TemplateException {
        FreemarkerUtil.initConfig("test.ftl");
        FreemarkerUtil.generator(toPath + "Test.java");
    }
}
