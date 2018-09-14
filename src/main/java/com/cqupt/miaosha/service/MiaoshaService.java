package com.cqupt.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cqupt.miaosha.domain.MiaoshaUser;
import com.cqupt.miaosha.domain.OrderInfo;
import com.cqupt.miaosha.vo.GoodsVo;

@Service
public class MiaoshaService {

	@Autowired
	OrderService orderService;
	@Autowired
	GoodsService goodsService;
	
	@Transactional
	public OrderInfo miaosha(MiaoshaUser user,GoodsVo goods) {
		//减少库存
		goodsService.reduceStock(goods);
		
		return orderService.createOrder(user,goods);
	}
	
}
