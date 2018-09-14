package com.cqupt.miaosha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cqupt.miaosha.dao.GoodsDao;
import com.cqupt.miaosha.domain.Goods;
import com.cqupt.miaosha.domain.MiaoshaGoods;
import com.cqupt.miaosha.vo.GoodsVo;


@Service
public class GoodsService {
	@Autowired
	GoodsDao goodsDao;
	public List<GoodsVo> toList(){
		List<GoodsVo> listGoodsVo = goodsDao.toList();
		return listGoodsVo;
	}
	public GoodsVo findGoodsVoById(long id){
		GoodsVo goodsVo = goodsDao.findGoodsVoBy(id);
		return goodsVo;
	}
	public void reduceStock(GoodsVo goods) {
		MiaoshaGoods g = new MiaoshaGoods();
		g.setGoodsId(goods.getId());
		goodsDao.reduceStock(g);
	}
}
