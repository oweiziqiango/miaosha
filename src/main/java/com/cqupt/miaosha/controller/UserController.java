package com.cqupt.miaosha.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cqupt.miaosha.domain.MiaoshaUser;
import com.cqupt.miaosha.result.Result;

@RequestMapping("/user")
@Controller
public class UserController {
	@RequestMapping("/info")
	@ResponseBody
	public Result<MiaoshaUser> info(Model model,MiaoshaUser user){
		return Result.success(user);  //可以根据thymeleaf进行前后缀的匹配
	}
}