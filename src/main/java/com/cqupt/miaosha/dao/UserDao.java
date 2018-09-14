package com.cqupt.miaosha.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cqupt.miaosha.domain.User;

@Mapper
public interface UserDao {
	@Select("select * from user where id = #{id}")
	public User getUserById(@Param("id")int id);
}
