package com.cqupt.miaosha.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cqupt.miaosha.dao.OrderDao;
import com.cqupt.miaosha.domain.MiaoshaOrder;
import com.cqupt.miaosha.domain.MiaoshaUser;
import com.cqupt.miaosha.domain.OrderInfo;
import com.cqupt.miaosha.vo.GoodsVo;

@Service
public class OrderService {
	@Autowired
	OrderDao orderDao;

	public MiaoshaOrder findMiaoshaOrderByUserIdandGoodsId(Long userId, long goodsId) {
		
		return orderDao.getMiaoshaOrderByUserIdGoodsId(userId,goodsId);
	}

	@Transactional
	public OrderInfo createOrder(MiaoshaUser user, GoodsVo goods) {
		/*
		 * 根据用户信息 和 商品信息    创建订单和秒杀订单
		 */
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setUserId(user.getId());
		orderInfo.setGoodsId(goods.getId());
		orderInfo.setGoodsName(goods.getGoodsName());
		orderInfo.setGoodsPrice(goods.getGoodsPrice());
		orderInfo.setOrderChannel(1);
		orderInfo.setStatus(0);
		orderInfo.setCreateDate(new Date());
		orderInfo.setGoodsCount(1);
		long orderId = orderDao.insert(orderInfo);
		
		MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
		miaoshaOrder.setGoodsId(goods.getId());
		miaoshaOrder.setOrderId(orderId);
		miaoshaOrder.setUserId(user.getId());
		orderDao.insertMiaoshaOrder(miaoshaOrder);
		
		return orderInfo;
	}
	
}
