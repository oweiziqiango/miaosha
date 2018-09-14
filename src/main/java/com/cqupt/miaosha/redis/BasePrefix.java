package com.cqupt.miaosha.redis;

/*
 * 抽象类 可以有变量和方法实现
 * 但是不能被实例化  只能通过继承 实例化子类
 */
public abstract class BasePrefix implements KeyPrefix {
	
	private int expireSeconds;
	private String prefix;
	
	public BasePrefix(String prefix) {
		this.expireSeconds = 0;
		this.prefix = prefix;
	}
	
	public BasePrefix(int expireSeconds,String prefix) {
		this.expireSeconds = expireSeconds;
		this.prefix = prefix;
	}
	
	
	public int expireSeconds() {//默认0代表永不过期
		return expireSeconds;
	}
	public String getPrefix() {
		String className = getClass().getSimpleName();
		return className+":"+prefix;//根据调用的类 拼接前缀
	}

}
