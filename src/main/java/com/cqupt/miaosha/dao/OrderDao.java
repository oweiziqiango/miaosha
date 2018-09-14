package com.cqupt.miaosha.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.cqupt.miaosha.domain.MiaoshaOrder;
import com.cqupt.miaosha.domain.OrderInfo;

@Mapper
public interface OrderDao {

	@Select("select * from miaosha_order where user_id=#{userId} and goods_id=#{goodsId}")
	public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userId")Long userId,@Param("goodsId")long goodsId);
	
	@Insert("insert into order_info(user_id,goods_id,goods_name,goods_price,goods_count,order_channel,status,create_date) " +
			"values(#{userId},#{goodsId},#{goodsName},#{goodsPrice},#{goodsCount},#{orderChannel},#{status},#{createDate})")
	@SelectKey(keyColumn="id",keyProperty="id",resultType=long.class,before=false,statement="select last_insert_id()")
	public long insert(OrderInfo orderInfo);
	
	@Insert("insert into miaosha_order(user_id,order_id,goods_id) values(#{userId},#{orderId},#{goodsId})")
	public void insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);
}
