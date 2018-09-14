package com.cqupt.miaosha.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {
	
	private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");//1后面10个数字
	//验证手机号 是否是1开头后面跟着10个数字
	public static boolean isMobile(String src){
		Matcher matcher = mobile_pattern.matcher(src);
		return  matcher.matches();
	}
	//测试
	public static void main(String[] args) {
		System.out.println(isMobile("17894561233"));
		System.out.println(isMobile("1559959"));
	}
}
