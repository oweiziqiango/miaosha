package com.cqupt.miaosha.vo;

import java.util.Date;

import com.cqupt.miaosha.domain.Goods;

/*
 * Goods的包装类
 * 用来包装秒杀商品信息
 */
public class GoodsVo extends Goods{
	private Double miaoshaPrice;
	private Integer stockCount;//秒杀库存
	private Date startDate;
	private Date endDate;
	
	
	public Double getMiaoshaPrice() {
		return miaoshaPrice;
	}
	public void setMiaoshaPrice(Double miaoshaPrice) {
		this.miaoshaPrice = miaoshaPrice;
	}
	public Integer getStockCount() {
		return stockCount;
	}
	public void setStockCount(Integer stockCount) {
		this.stockCount = stockCount;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
