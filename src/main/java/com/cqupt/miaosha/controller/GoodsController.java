package com.cqupt.miaosha.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cqupt.miaosha.domain.MiaoshaUser;
import com.cqupt.miaosha.service.GoodsService;
import com.cqupt.miaosha.service.MiaoshaUserService;
import com.cqupt.miaosha.vo.GoodsVo;

@RequestMapping("/goods")
@Controller
public class GoodsController {
	
	@Autowired
	MiaoshaUserService miaoshaUserService;
	
	@Autowired
	GoodsService goodsService;
	
	public static Logger log = LoggerFactory.getLogger(GoodsController.class);
	
	
	/*
	 * 测试阿里云39.104.52.3   1G 1vCPU  内存有限 无法开辟更多的线程 去测试
	 * 1000*1 QPS 37.2   mysql
	 * 
	 * 本机测试  
	 * 1000*1 QPS 85.8  mysql
	 * 
	 */
	@RequestMapping("/to_list")
	public String list(Model model,MiaoshaUser user){
		
		/*
		 * 从cookie或者request中获取token值
		 * 通过token 去redis中获取对应的value即miaoshauser，再返回给前端
		 */
		// 根据token获取user的过程  交给框架的自定义参数添加
		/*if(StringUtils.isEmpty(cookieToken)&&StringUtils.isEmpty(paramToken))
			return "login";
		String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
		MiaoshaUser user = miaoshaUserService.getByToken(token);*/
		
		
		
		model.addAttribute("user",user);
		List<GoodsVo> goodsList = goodsService.toList();
		model.addAttribute("goodsList",goodsList);
		return "goods_list";  //可以根据thymeleaf进行前后缀的匹配
	}
	
	@RequestMapping("/to_detail/{goodsId}")
	public String detail(Model model,MiaoshaUser user,@PathVariable("goodsId")long goodsId){
		/*
		 * 互联网公司中  数据库的id 不用uuid()和自增   
		 *	一般使用  snowflake 64位自增id算法 
		 */
		model.addAttribute("user", user);
		
		GoodsVo goodsVo = goodsService.findGoodsVoById(goodsId);
		model.addAttribute("goods", goodsVo);
		long startAt = goodsVo.getStartDate().getTime();//milliseconds 
		long endAt = goodsVo.getEndDate().getTime();//milliseconds 
		long now = System.currentTimeMillis();
		
		int miaoshaStatus = 0;
		int remainSeconds = 0;
		
		if(now < startAt){//秒杀还没开始
			miaoshaStatus = 0;
			remainSeconds = (int) ((startAt - now)/1000); //正整数
		}else if(endAt < now){//秒杀结束
			miaoshaStatus = 2;
			remainSeconds = -1;
		}else{//秒杀进行中
			miaoshaStatus = 1;
			remainSeconds = 0;
		}
		
		model.addAttribute("miaoshaStatus", miaoshaStatus);
		model.addAttribute("remainSeconds", remainSeconds);
		
		return "goods_detail";
	}
}
