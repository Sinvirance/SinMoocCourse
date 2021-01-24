package top.course.server.exception;

/**
 * @Author: Sinvirance
 * @Date: 2021/1/24 4:38
 * @Description: 自定义校验异常；由于校验异常是业务异常，不需要我们 try-catch，所以继承Try-catch
 */


public class ValidatorException extends RuntimeException{

    public ValidatorException(String message) {
        super(message);
    }
}
