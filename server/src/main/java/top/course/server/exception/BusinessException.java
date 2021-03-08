package top.course.server.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Sinvirance
 * @Date: 2021/3/7 17:48
 * @Description: 业务异常类
 */

@Setter
@Getter
public class BusinessException extends RuntimeException{

    /* 业务异常枚举标识码 */
    private BusinessExceptionCode code;

    public BusinessException (BusinessExceptionCode code) {
        super(code.getDesc());
        this.code = code;
    }

    /**
     * 不写入堆栈信息，提高性能
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}