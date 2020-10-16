package com.costar.core.exception;

import com.costar.core.response.FailRe;
import com.costar.core.response.ReCode;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.util.Set;

/**
 * @author zs
 * 全局异常处理器
 * Created by cloud on 2019/6/17.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)//处理提交参数异常
    public String handleApiConstraintViolationException(ConstraintViolationException ex) {
        String message = "";
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            message += violation.getMessage() + ", ";
        }
        return FailRe.createRe(ReCode.PARAM_ERROR,message).toJsonStr();
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)//处理方法参数无效异常
    public String handleApiMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String message = "";
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            message += error.getDefaultMessage()+ ", ";
        }
        return FailRe.createRe(ReCode.PARAM_ERROR,message).toJsonStr();
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)//处理拒绝访问异常
    public String handleApiMethodArgumentNotValidException(AccessDeniedException ex) {
        String message = ex.getLocalizedMessage();
        return FailRe.createRe(ReCode.AUTHORITY_ERROR,message).toJsonStr();
    }




}
