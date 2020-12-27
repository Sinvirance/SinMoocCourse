package top.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.env.Environment;


@SpringBootApplication
@EnableEurekaClient
public class GatewayApplication {

    // 指定类初始化日志对象
    private static final Logger LOG = LoggerFactory.getLogger(GatewayApplication.class);

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(GatewayApplication.class);
        Environment environment = application.run(args).getEnvironment();
        LOG.info("启动成功");
        LOG.info("System地址: \thttp://127.0.0.1:{}", environment.getProperty("server.port"));
    }
}
