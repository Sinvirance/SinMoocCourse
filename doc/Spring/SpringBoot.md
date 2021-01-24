## SpringBoot 重要注解

### 全局处理Controller异常处理

#### `@ControllerAdvice`
注解定义全局异常处理类，可以通过Spring扫描为bean组件。其可以包含由@ExceptionHandler、@InitBinder 和@ModelAttribute标注的方法，
可以处理多个Controller类，这样所有控制器的异常可以在一个地方进行处理
```java
@ControllerAdvice
public class ControllerExceptionHandler {
}
```

#### `@ExceptionHandler`
统一处理某一类异常，从而能够减少代码重复率和复杂度
```java
@ControllerAdvice
public class ControllerExceptionHandler {
    // 该方法将处理所有Controller层抛出来的Exception异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(){
        return "Exception Deal!";
    }
}
```