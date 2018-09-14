package com.cqupt.miaosha.exception;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cqupt.miaosha.result.CodeMsg;
import com.cqupt.miaosha.result.Result;


@ControllerAdvice
//以json的格式返回
@ResponseBody
public class GlobalExceptionHandler {
	/*
	 * 异常处理器
	 */
	@ExceptionHandler(value=Exception.class)
	public Result<CodeMsg> exceptionHandler(HttpServletRequest request,Exception e){
		e.printStackTrace();
		if(e instanceof GlobalEception){
			GlobalEception ex = (GlobalEception) e;
			CodeMsg codeMsg = ex.getCodeMsg();
			return Result.error(codeMsg);
		}else if(e instanceof BindException){
			BindException ex = (BindException) e;
			List<ObjectError> allErrors = ex.getAllErrors();
			ObjectError error = allErrors.get(0);
			String message = error.getDefaultMessage();
			return Result.error(CodeMsg.BIND_ERROR.fillArgs(message));
		}else{
			return Result.error(CodeMsg.SERVER_ERROR);
		}
	}
}
