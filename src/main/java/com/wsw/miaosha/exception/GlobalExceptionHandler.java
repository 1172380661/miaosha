package com.wsw.miaosha.exception;

import com.wsw.miaosha.result.CodeMsg;
import com.wsw.miaosha.result.Result;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * @Author: wsw
 * @Date: 2019/8/27 9:31
 */

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public Result exceptionHandler(Exception e) {
        e.printStackTrace();
        if (e instanceof GlobalException) {
            GlobalException ge = (GlobalException) e;
            return new Result(ge.getCodeMsg());
        } else if (e instanceof BindException) {
            BindException be = (BindException) e;
            List<ObjectError> errors = be.getAllErrors();
            ObjectError objectError = errors.get(0);
            String msg = objectError.getDefaultMessage();
            return new Result(CodeMsg.BIND_ERROR.fillArgs(msg));
        } else if (e instanceof DuplicateKeyException) {
            return new Result(CodeMsg.REPEAT_MIAOSHA);
        } else {
            return new Result(CodeMsg.SERVER_ERROR);
        }
    }
}
