## freeMarker
* FreeMarker 是一款 模板引擎： 即一种基于模板和要改变的数据， 并用来生成输出文本(HTML网页，电子邮件，配置文件，源代码等)的通用工具。<br/>
它不是面向最终用户的，而是一个Java类库，是一款程序员可以嵌入他们所开发产品的组件
* ***在线手册***：http://freemarker.foofun.cn/

### 简单入门
* 依赖引入
```xml
<dependencies>
    <dependency>
        <groupId>org.freemarker</groupId>
        <artifactId>freemarker</artifactId>
        <version>2.3.29</version>
    </dependency>
</dependencies>
```
* 代码生成器类
```java
public class TestUtil {

    /*
     * ftlPath:模板文件路径
     * toPath: 文件生成路径
     */
    static String ftlPath = "generator\\src\\main\\java\\top\\course\\generator\\test\\";
    static String toPath = "generator\\src\\main\\java\\top\\course\\generator\\test\\";
    public static void main(String[] args) throws IOException, TemplateException {
        /* 根据freeMarker版本读取配置并针对配置和模板文件ftl */
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setDirectoryForTemplateLoading(new File(ftlPath));
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_29));
        // step3 创建数据模型
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("classPath", "com.freemarker.hello");
        dataMap.put("className", "AutoCodeDemo");
        dataMap.put("helloWorld", "通过简单的 <代码自动生产程序> 演示 FreeMarker的HelloWorld！");
        /* 加载模板文件 */
        Template temp = cfg.getTemplate("test.ftl");
        /* 生成数据 */
        FileWriter fw = new FileWriter(toPath + "Test.java");
        BufferedWriter bw = new BufferedWriter(fw);
        /* 输出文件 */
        temp.process(dataMap, bw);
        bw.flush();
        fw.close();
    }
}
```