package com.cqupt.miaosha.utils;

import java.util.UUID;

public class UUIDUtil {
	//生成32为随机字符
	public static String uuid(){
		String uuid = UUID.randomUUID().toString().replace("-", "");
		return uuid;
	}
}
