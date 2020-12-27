package top.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.core.env.Environment;


@SpringBootApplication
// 声明当前的应用是 Eureka 服务注册中心
@EnableEurekaServer
public class EurekaApplication {

    // 指定类初始化日志对象
    private static final Logger LOG = LoggerFactory.getLogger(EurekaApplication.class);

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(EurekaApplication.class);
        Environment environment = application.run(args).getEnvironment();
        LOG.info("启动成功");
        LOG.info("Eureka地址: \thttp://127.0.0.1:{}", environment.getProperty("server.port"));
    }
}
