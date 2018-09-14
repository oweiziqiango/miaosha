package com.cqupt.miaosha.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cqupt.miaosha.result.Result;
import com.cqupt.miaosha.service.MiaoshaUserService;
import com.cqupt.miaosha.vo.LoginVo;

@RequestMapping("/login")
@Controller
public class LoginController {
	
	@Autowired
	MiaoshaUserService miaoshaUserService;
	
	public static Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping("/to_login")
	public String toLogin(){
		return "login";  //可以根据thymeleaf进行前后缀的匹配  login.html
	}
	
	
	@RequestMapping("/do_login")
	@ResponseBody 
	public Result<String> doLogin(HttpServletResponse response,@Valid LoginVo loginVo){
		log.info(loginVo.toString());
		/*
		 * 参数校验的过程 使用注解解决
		 */
		//获得参数
		/*String mobile = loginVo.getMobile();
		String passInput = loginVo.getPassword();*/
		//参数校验  
		//判断是否为空
		/*if(StringUtils.isEmpty(mobile)){
			return Result.error(CodeMsg.MOBILE_EMPTY);
		}
		if(StringUtils.isEmpty(passInput)){
			return Result.error(CodeMsg.PASSWORD_EMPTY);
		}*/
		//判断手机号的模式
		/*if(!ValidatorUtil.isMobile(mobile)){
			return Result.error(CodeMsg.MOBILE_PATTERN);
		}*/
		//登录
		
		String token = miaoshaUserService.login(response,loginVo);
		return Result.success(token);
	}
}
