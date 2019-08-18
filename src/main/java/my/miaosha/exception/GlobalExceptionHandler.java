package my.miaosha.exception;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import my.miaosha.result.CodeMsg;
import my.miaosha.result.Result;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Result<CodeMsg> exceptionHandler(HttpServletResponse response,Exception exception){
		exception.printStackTrace();
		if(exception instanceof GlobalException) {
			GlobalException e = (GlobalException) exception;
			CodeMsg cm = e.getCm();
			return Result.error(cm);
		}else if(exception instanceof BindException) {
			BindException e=(BindException) exception;
			List<ObjectError> errors = e.getAllErrors();
			ObjectError error = errors.get(0);
			String msg = error.getDefaultMessage();
			return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
		}
		return null;
	}

}
