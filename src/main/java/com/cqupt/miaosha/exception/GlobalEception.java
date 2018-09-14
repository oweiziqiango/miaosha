package com.cqupt.miaosha.exception;

import com.cqupt.miaosha.result.CodeMsg;

/**
 * 使用这个全局异常
 * 抛出CodeMsg信息
 * 交给异常处理器处理
 */
public class GlobalEception extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private CodeMsg codeMsg;
	
	public GlobalEception(CodeMsg codeMsg) {
		super(codeMsg.toString());
		this.codeMsg = codeMsg;
	}

	public CodeMsg getCodeMsg() {
		return codeMsg;
	}
	
}
