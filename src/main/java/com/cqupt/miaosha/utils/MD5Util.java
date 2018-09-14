package com.cqupt.miaosha.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util{
	
	
	
	
	public static String MD5(String inputPass){
		return DigestUtils.md5Hex(inputPass);
	}
	
	public static final String salt = "1a2b3c4d";
	
	/*
	 * inputPass 是用户输入的
	 * formPass 是页面md5之后的
	 * dbPass 是后台接收到formPass再md5的
	 */
	
	//inputPasstToFormPass这一部分其实在前端页面就已经完成
	//第一次md5
	public static String inputPassToFormPass(String inputPass){
		String str = salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
		return MD5(str);
	}
	//获取到前端的第一次md5
	//第二次md5
 	public static String formPasstToDBPass(String inputPass,String salt){
		String str = salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
		return MD5(str);
	}
 	//综合两次md5
	public static String inputPassToDBPass(String inputPass,String salt){
		String formPass = inputPassToFormPass(inputPass);
		String dbPass = formPasstToDBPass(formPass,salt);
		return dbPass;
	}
	
	public static void main(String[] args) {
		System.out.println(inputPassToFormPass("123456"));
		System.out.println(formPasstToDBPass(inputPassToFormPass("123456"),"1a2b3c4d"));
		//System.out.println(inputPassToDBPass("123456","1a2b3c4d"));
	}
}
