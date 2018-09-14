package com.cqupt.miaosha.config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.cqupt.miaosha.domain.MiaoshaUser;
import com.cqupt.miaosha.service.MiaoshaUserService;

@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

	@Autowired
	MiaoshaUserService miaoshaUserService;

	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> clazz = parameter.getParameterType();
		return clazz == MiaoshaUser.class;
	}

	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest
				.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse response = webRequest
				.getNativeResponse(HttpServletResponse.class);
		String cookieToken = request
				.getParameter(MiaoshaUserService.COOKIE_TOKEN_NAME);
		String paramToken = getCookieValue(request,
				MiaoshaUserService.COOKIE_TOKEN_NAME);

		if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken))
			return null;
		String token = StringUtils.isEmpty(paramToken) ? cookieToken
				: paramToken;

		MiaoshaUser user = miaoshaUserService.getByToken(response, token);
		return user;
	}

	private String getCookieValue(HttpServletRequest request,
			String cookieTokenName) {
		Cookie[] cookies = request.getCookies();
		if(cookies == null || cookies.length<=0)
			return null;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(cookieTokenName)) {
				return cookie.getValue();
			}
		}
		return null;
	}

}
