package com.cqupt.miaosha.result;

public class CodeMsg {
	private int code;
	private String msg;
	
	//根据不同的错误 直接调用对应的静态变量
	public static CodeMsg SUCCESS = new CodeMsg(0,"success");
	
	public static CodeMsg SERVER_ERROR = new CodeMsg(500100,"服务器端错误");
	public static CodeMsg BIND_ERROR = new CodeMsg(500100,"参数校验异常:%s");// %s 填写参数
	
	//登录模块  错误码 5002xx
	public static CodeMsg SESSION_ERROR = new CodeMsg(500210,"Session不存在或者已经失效");
	public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211,"登录密码不能为空");
	public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212,"手机号不能为空");//mobile
	public static CodeMsg MOBILE_PATTERN = new CodeMsg(500213,"手机号格式不正确");//mobile
	public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214,"手机号不存在");//mobile
	public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215,"密码错误");//mobile
	//商品模块   5003XX
	
	//订单模块   5004xx

	//秒杀模块   5005xx
	public static CodeMsg STOCK_COUNT_ERROR = new CodeMsg(500501,"秒杀商品不足");
	public static CodeMsg MIAOSHA_ORDER_REPEAT = new CodeMsg(500501,"已参与秒杀");
	
	private CodeMsg(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public CodeMsg fillArgs(Object...objects ){
		int code = this.code;
		String message = String.format(this.msg,objects);
		return new CodeMsg(code,message);
	}
	
	public int getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}



	@Override
	public String toString() {
		return "CodeMsg [code=" + code + ", msg=" + msg + "]";
	}
	
}
