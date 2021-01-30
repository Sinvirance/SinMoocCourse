package top.course.file.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;


@SpringBootApplication
@EnableEurekaClient
@ComponentScan("top.course")
@MapperScan("top.course.server.mapper")
public class FileApplication {

    // 指定类初始化日志对象
    private static final Logger LOG = LoggerFactory.getLogger(FileApplication.class);

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(FileApplication.class);
        Environment environment = application.run(args).getEnvironment();
        LOG.info("启动成功");
        LOG.info("File地址: \thttp://127.0.0.1:{}", environment.getProperty("server.port"));
    }
}
