package top.course.file.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Sinvirance
 * @Date: 2021/2/7 14:18
 * @Description: file 模块 SpringBoot 配置
 */

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    /**
     * 本地资源的虚拟访问路径配置：http://127.0.0.1:9003/file/f/teacher/ZeQxe9gR-IMG_5989.JPG 或者 http://127.0.0.1:9000/file/f/teacher/ZeQxe9gR-IMG_5989.JPG
     * @param registry 用于资源存储的注册对象
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /* file: 用于外部访问目录 */
        /* classpath: 用于自定义目录，是tomcat启动后WEB-INF文件夹下的classes目录 */
        registry.addResourceHandler("/f/**").addResourceLocations("file:D:\\Sinvirance\\WorkSpace\\JavaProject\\SinMooc\\static\\");
    }
}