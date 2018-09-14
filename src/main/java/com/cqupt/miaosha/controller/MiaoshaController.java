package com.cqupt.miaosha.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cqupt.miaosha.domain.MiaoshaOrder;
import com.cqupt.miaosha.domain.MiaoshaUser;
import com.cqupt.miaosha.domain.OrderInfo;
import com.cqupt.miaosha.result.CodeMsg;
import com.cqupt.miaosha.service.GoodsService;
import com.cqupt.miaosha.service.MiaoshaService;
import com.cqupt.miaosha.service.OrderService;
import com.cqupt.miaosha.vo.GoodsVo;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {
	
	@Autowired
	MiaoshaService miaoshaService;
	@Autowired
	GoodsService goodsService;
	@Autowired
	OrderService orderService;
	/*
	 * 
	 * 测试阿里云39.104.52.3  1G 1vCPU  内存有限 无法开辟更多的线程 去测试
	 * 1000*1 QPS 46.0  redis
	 * 
	 * 本机测试  
	 * 1000*1 QPS 87.5  redis
	 */
	@RequestMapping("/do_miaosha")
	public String miaosha(Model model,MiaoshaUser user,long goodsId){
		model.addAttribute("user",user);
		
		GoodsVo goodsVo = goodsService.findGoodsVoById(goodsId);
		//查看秒杀库存
		if(goodsVo.getStockCount()<=0){
			model.addAttribute("errmsg",CodeMsg.STOCK_COUNT_ERROR.getMsg());
			return "miaosha_fail";
		}
		//判断是否已经秒杀到了
		MiaoshaOrder miaoshaOrder = orderService.findMiaoshaOrderByUserIdandGoodsId(user.getId(),goodsId);
		if(miaoshaOrder !=null){
			model.addAttribute("errmsg", CodeMsg.MIAOSHA_ORDER_REPEAT.getMsg());
			return "miaosha_fail";
		}
		//插入miaosha_order和order_info 返回orderInfo
		OrderInfo orderInfo = miaoshaService.miaosha(user,goodsVo);
		
		model.addAttribute("orderInfo", orderInfo);
		model.addAttribute("goods", goodsVo);
		
		return "order_detail";
	}
}
