package top.course.business.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;


@SpringBootApplication
/* @EnableEurekaClient */
@EnableDiscoveryClient
@ComponentScan("top.course")
@MapperScan("top.course.server.mapper")
public class BusinessApplication {

    // 指定类初始化日志对象
    private static final Logger LOG = LoggerFactory.getLogger(BusinessApplication.class);

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(BusinessApplication.class);
        Environment environment = application.run(args).getEnvironment();
        LOG.info("启动成功");
        LOG.info("Business地址: \thttp://127.0.0.1:{}", environment.getProperty("server.port"));
    }
}
