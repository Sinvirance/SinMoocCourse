package top.course.server.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import top.course.server.util.UUIDUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

/**
 * @Author: Sinvirance
 * @Date: 2021/1/24 22:14
 * @Description: TODO
 */

@Aspect
@Component
public class LogAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    /** 定义一个切点 */
    @Pointcut("execution(public * top.course.*.controller..*Controller.*(..))")
    public void controllerPointcut() {}

    @Before("controllerPointcut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        /* 日志编号 */
        MDC.put("UUID", UUIDUtil.getShortUUID());

        // 开始打印请求日志
        /* 获取HttpServletRequest对象 */
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        /* getSignature(): 获取封装了署名信息的对象,在该对象中可以获取到目标方法名,所属类的Class等信息*/
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();

        /* 打印业务操作 */
        String nameCn = "";
        if (name.contains("list") || name.contains("query")) {
            nameCn = "查询";
        } else if (name.contains("save")) {
            nameCn = "保存";
        } else if (name.contains("delete")) {
            nameCn = "删除";
        } else {
            nameCn = "操作";
        }

        // 使用反射，获取业务名称
        /* getDeclaringType(): 返回方法所在的包名和类名 */
        Class clazz = signature.getDeclaringType();
        /* Field: 成员变量, 每个成员变量有类型和值，为我们提供了获取当前对象的成员变量的类型，和重新设值的方法 */
        Field field;
        String businessName = "";
        try {
            /* getField(): 获得该参数名在该类field对象，找不到就去父类，乃至更高递归查找*/
            field = clazz.getField("BUSINESS_NAME");
            if (!StringUtils.isEmpty(field)) {
                businessName = (String) field.get(clazz);
            }
        } catch (NoSuchFieldException e) {
            LOG.error("未获取到业务名称");
        } catch (SecurityException e) {
            LOG.error("获取业务名称失败", e);
        }

        // 打印请求信息
        LOG.info("------------- 【{}】{}开始 -------------", businessName, nameCn);
        LOG.info("请求地址: {} {}", request.getRequestURL().toString(), request.getMethod());
        LOG.info("类名方法: {}.{}", signature.getDeclaringTypeName(), name);
        LOG.info("远程地址: {}", request.getRemoteAddr());

        // 打印请求参数
        Object[] args = joinPoint.getArgs();
        Object[] arguments  = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest
                    || args[i] instanceof ServletResponse
                    || args[i] instanceof MultipartFile) {
                continue;
            }
            arguments[i] = args[i];
        }
        // 排除字段，敏感字段或太长的字段不显示
        String[] excludeProperties = {"shard"};
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();
        excludefilter.addExcludes(excludeProperties);
        LOG.info("请求参数: {}", JSONObject.toJSONString(arguments, excludefilter)); // 为空的会不打印，但是像图片等长字段也会打印
    }


    @Around("controllerPointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        /*
         * ProceedingJoinPoint: 继承了 JoinPoint, 在JoinPoint的基础上暴露出 proceed 这个方法。
         * proceed()很重要，这个是aop代理链执行的方法。没有这个方法，下面的拦截无法执行
         */
        Object result = proceedingJoinPoint.proceed();
        // 排除字段，敏感字段或太长的字段不显示
        String[] excludeProperties = {"password","shard"};
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();
        excludefilter.addExcludes(excludeProperties);
        LOG.info("返回结果: {}", JSONObject.toJSONString(result, excludefilter));
        LOG.info("------------- 结束 耗时：{} ms -------------\n", System.currentTimeMillis() - startTime);
        return result;
    }
}
