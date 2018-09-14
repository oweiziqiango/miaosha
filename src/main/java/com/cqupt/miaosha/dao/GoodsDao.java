package com.cqupt.miaosha.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cqupt.miaosha.domain.Goods;
import com.cqupt.miaosha.domain.MiaoshaGoods;
import com.cqupt.miaosha.vo.GoodsVo;

@Mapper
public interface GoodsDao {
	
	@Select("select g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date from miaosha_goods mg left join goods g on mg.goods_id = g.id")
	public List<GoodsVo> toList();
	
	
	@Select("select g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date from miaosha_goods mg left join goods g on mg.goods_id = g.id where g.id=#{id}")
	public GoodsVo findGoodsVoBy(@Param("id")long id);//商品详情中的商品id

	@Update("update miaosha_goods set stock_count = stock_count-1 where goods_id = #{goodsId} and stock_count > 0")
	public void reduceStock(MiaoshaGoods g);
}
