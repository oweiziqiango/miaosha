package com.cqupt.miaosha.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cqupt.miaosha.dao.MiaoshaUserDao;
import com.cqupt.miaosha.domain.MiaoshaUser;
import com.cqupt.miaosha.exception.GlobalEception;
import com.cqupt.miaosha.redis.MiaoshaUserKey;
import com.cqupt.miaosha.redis.RedisService;
import com.cqupt.miaosha.result.CodeMsg;
import com.cqupt.miaosha.utils.MD5Util;
import com.cqupt.miaosha.utils.UUIDUtil;
import com.cqupt.miaosha.vo.LoginVo;

@Service
public class MiaoshaUserService {
	@Autowired
	MiaoshaUserDao miaoshaUserDao;
	@Autowired
	RedisService redisService;

	public static final String COOKIE_TOKEN_NAME = "token";

	public MiaoshaUser getById(long id) {
		return miaoshaUserDao.getById(id);
	}

	/*
	 * 业务中返回CodeMsg 不太合适 CodeMsg只是用来存在错误码和错误信息 修改一下
	 */
	public String login(HttpServletResponse response, LoginVo loginVo) {

		if (loginVo == null) {
			throw new GlobalEception(CodeMsg.SERVER_ERROR);
		}
		String mobile = loginVo.getMobile();
		// 验证用户是否存在
		MiaoshaUser miaoshaUser = getById(Long.parseLong(mobile));
		if (miaoshaUser == null)
			throw new GlobalEception(CodeMsg.MOBILE_NOT_EXIST);
		// 验证密码
		String formInput = loginVo.getPassword();
		String dbInput = MD5Util.formPasstToDBPass(formInput,
				miaoshaUser.getSalt());
		String dbPass = miaoshaUser.getPassword();
		// System.out.println(dbInput);
		if (!dbPass.equals(dbInput)) {
			throw new GlobalEception(CodeMsg.PASSWORD_ERROR);
		}
		// 生成cookie
		String token = UUIDUtil.uuid();
		addCookie(response,token, miaoshaUser);
		return token;
	}

	public void addCookie(HttpServletResponse response,String token, MiaoshaUser miaoshaUser) {

		
		// 先放redis里放 key是tk:32位随机数 value是秒杀用户信息（登录的用户信息）
		redisService.set(MiaoshaUserKey.token, token, miaoshaUser);
		Cookie cookie = new Cookie(MiaoshaUserService.COOKIE_TOKEN_NAME, token);
		cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
		cookie.setPath("/");
		// 将cookie放入response中
		response.addCookie(cookie);
	}

	public MiaoshaUser getByToken(HttpServletResponse response, String token) {
		if (StringUtils.isEmpty(token))
			return null;
		// 为了确保更新有效时间 每次获取 都是用户最新的一次登录操作，都更新token

		MiaoshaUser miaoshaUser = redisService.get(MiaoshaUserKey.token, token,
				MiaoshaUser.class);
		//更新有效时间   达到延长有效期的作用
		if(miaoshaUser!=null)
			addCookie(response,token,miaoshaUser);
		return miaoshaUser;
	}
}
