package top.course.business.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.course.server.dto.ChapterDto;
import top.course.server.dto.ResponseDto;
import top.course.server.exception.ValidatorException;


/**
 * @Author: Sinvirance
 * @Date: 2021/1/24 17:08
 * @Description: 自定义Controller层全局异常类
 */

@ControllerAdvice
public class ControllerExceptionHandler {
    
    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * 用于拦截出现自定义 ValidatorException异常的Controller层中的方法
     * @param e 自定义校验异常
     * @return 统一响应对象，isSuccess=false
     */
    @ExceptionHandler(value = ValidatorException.class)
    @ResponseBody
    public ResponseDto validatorExceptionHandler(ValidatorException e) {
        ResponseDto<ChapterDto> responseDto = new ResponseDto<>();
        responseDto.setSuccess(false);
        LOG.warn(e.getMessage());
        responseDto.setMessage("请求参数异常");
        return responseDto;
    }
}
