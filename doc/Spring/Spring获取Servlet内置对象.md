## Spring 获取 Servlet 内置对象的4种方法

### 注解注入
```java
public class Test{
    @Autowired
    private HttpServletRequest request;
}
```

### 将内置对象通过方法参数传递
```java
public class Test{
    @RequestMapping("/test")
    @ResponseBody
    public void test(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        
    }
}
```

### SpringMVC 提供的 RequestContextHolder类
* `RequestContextHolder` 通过`ThreadLocal` 保存当前线程下的 `request`, 保证了每一次获取到的Request是该请求的request.
* 使用这种方式的好处：在非Controller层获取`request`和`response`对象
```java
public class Test{
    // 两个方法在没有使用JSF的项目中是没有区别的
    public void test1() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
    }

    public void test2() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
    }
}
```


