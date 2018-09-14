package com.cqupt.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cqupt.miaosha.dao.UserDao;
import com.cqupt.miaosha.domain.User;

@Service
public class UserService {
	@Autowired
	UserDao userDao;
	public User getUserById(int id){
		return userDao.getUserById(id);
	}
}
