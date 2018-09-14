package com.cqupt.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cqupt.miaosha.domain.User;
import com.cqupt.miaosha.redis.KeyPrefix;
import com.cqupt.miaosha.redis.RedisService;
import com.cqupt.miaosha.redis.UserKey;
import com.cqupt.miaosha.result.CodeMsg;
import com.cqupt.miaosha.result.Result;
import com.cqupt.miaosha.service.UserService;
/**
 *例子
 *结合mybatis  redis
 */
@RestController
@RequestMapping("/demo")
public class SimpleController {
	@Autowired
	UserService userService;
	@Autowired
	RedisService redisService;
	
	@RequestMapping("/home")
	String home() {
		return "Hello World!";
	}

	// Controller里的方法 分为两大类 1.rest api json输出 2.页面
	@RequestMapping("/hello")
	public Result<String> hello() {

		return Result.success("hello world");
		// return new Result(0,"success","hello world");
	}

	@RequestMapping("/helloError")
	public Result<CodeMsg> helloError() {

		return Result.error(CodeMsg.SERVER_ERROR);
		// return return new Result(500100,"服务器端错误");
	}

	@RequestMapping("/thymeleaf")
	public ModelAndView thymeleaf(ModelAndView model) {
		// model.addAttribute("name", "wzq");
		// return "hello";//单独使用Model 返回字符串没有起作用
		model.addObject("name", "wzq123");
		model.setViewName("hello");// 这里会匹配thymeleaf的前后缀
		return model;
	}

	// 测试mybatis druid 连接
	@RequestMapping("/db/getUser")
	public Result<User> getUser() {
		User user = userService.getUserById(1);
		return Result.success(user);
	}

	// tx 在service里使用 事务注解

	// 通过redis 获取数据
	@RequestMapping("/redis/get")
	public Result<User> getRedis() {
		User user = redisService.get(UserKey.getById,""+1,User.class);
		return Result.success(user);
	}

	// 通过redis 获取数据
	@RequestMapping("/redis/set")
	public Result<User> setRedis() {
		//规定一下  key的命名
		//通用缓存key封装
		User user = new User();
		user.setId(1);
		user.setName("wzq");
		boolean b = redisService.set(UserKey.getById,""+user.getId(),user);
		User userResult = null;
		if(b==true){
			userResult = redisService.get(UserKey.getById,""+user.getId(),User.class);
		}
		return Result.success(userResult);
	}
}
