package com.cqupt.miaosha.redis;

/*
 * 关键字前缀
 */
public interface KeyPrefix {
	public int expireSeconds();//到期时间
	public String getPrefix();//获得前缀
}
