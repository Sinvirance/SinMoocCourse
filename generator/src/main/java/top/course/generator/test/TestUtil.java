package top.course.generator.test;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @Author: Sinvirance
 * @Date: 2021/1/25 17:17
 * @Description: TODO
 */

public class TestUtil {

    /*
     * ftlPath:模板文件路径
     * toPath: 文件生成路径
     */
    static String ftlPath = "generator\\src\\main\\java\\top\\course\\generator\\test\\";
    static String toPath = "generator\\src\\main\\java\\top\\course\\generator\\test\\";

    public static void main(String[] args) throws IOException, TemplateException {
        /* 根据freeMarker版本读取配置 */
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setDirectoryForTemplateLoading(new File(ftlPath));
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_29));
        /* 加载模板文件 */
        Template temp = cfg.getTemplate("test.ftl");
        /* 生成数据 */
        FileWriter fw = new FileWriter(toPath + "Test.java");
        BufferedWriter bw = new BufferedWriter(fw);
        /* 输出文件 */
        temp.process(null, bw);
        bw.flush();
        fw.close();
    }
}
