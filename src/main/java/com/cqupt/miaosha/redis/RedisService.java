package com.cqupt.miaosha.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {

	/*@Autowired
	RedisPoolFactory redisPoolFactory;*/
	@Autowired
	JedisPool jedisPool;
	/*
	 * 获得一个对象
	 */
	public <T> T get(KeyPrefix keyPrefix,String key, Class<T> clazz) {
		Jedis jedis = null;
		try {
			// 使用jedis 连接 ，使用jedispool获得jedis
			//JedisPool jedisPool = redisPoolFactory.JedisPoolFactory();
			jedis = jedisPool.getResource();
			
			//用真正的key 获取对象
			String keyReal = keyPrefix.getPrefix()+key;
			
			String str = jedis.get(keyReal);
			T t = stringToBean(str,clazz);
			return t;
		} finally {
			returnJedisPool(jedis);
		}
	}
	//将字符串转换成bean（java对象） 除了 String，int，long
	@SuppressWarnings("unchecked")
	private <T> T stringToBean(String str,Class<T> clazz) {
		if(str==null||str.length()<=0)
			return null;
		if(clazz == String.class){
			return (T) str;
		}else if(clazz == int.class){
			return (T) Integer.valueOf(str);
		}else if(clazz == Long.class){
			return (T) Long.valueOf(str);
		}
		return JSON.toJavaObject(JSON.parseObject(str), clazz);
	}
	//使用完jedis  需要返回给jedispool
	private void returnJedisPool(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}
	/*
	 * 设置一个对象
	 */
	public <T> boolean set(KeyPrefix prefix,String key, T value) {
		Jedis jedis = null;
		try {
			// 使用jedis 连接 ，使用jedispool获得jedis
			//JedisPool jedisPool = redisPoolFactory.JedisPoolFactory();
			jedis = jedisPool.getResource();
			String strValue = beanToString(value);
			if(strValue==null||strValue.length()<=0)
				return false;
			//生成真正的key
			String realKey = prefix.getPrefix()+key;//UserKey:id 1
			
			int seconds = prefix.expireSeconds();
			if(seconds<=0){
				//默认为0  永不过期
				jedis.set(realKey, strValue);
			}else{
				//setex设置过期时间
				jedis.setex(realKey, seconds, strValue);
			}
			return true;
		} finally {
			returnJedisPool(jedis);
		}
	}
	//将bean（java对象）转换成json字符串  除了 String，int，long
	private <T> String beanToString(T value) {
		if(value==null){
			return null;
		}
		Class<?> clazz = value.getClass();
		if(clazz == String.class){
			return (String) value;
		}else if(clazz == int.class){
			return ""+value;
		}else if(clazz == Long.class){
			return ""+value;
		}
		return JSON.toJSONString(value);
	}
	/*
	 * 判断是否存在一个对象
	 */
	public <T> boolean exists(KeyPrefix prefix,String key, T value) {
		Jedis jedis = null;
		try {
			// 使用jedis 连接 ，使用jedispool获得jedis
			//JedisPool jedisPool = redisPoolFactory.JedisPoolFactory();
			jedis = jedisPool.getResource();
			//生成真正的key
			String realKey = prefix.getPrefix()+key;//UserKey:id 1
			return 	jedis.exists(realKey);
		} finally {
			returnJedisPool(jedis);
		}
	}
	/*
	 * 增加值
	 */
	public <T> Long incr(KeyPrefix prefix,String key, T value) {
		Jedis jedis = null;
		try {
			// 使用jedis 连接 ，使用jedispool获得jedis
			//JedisPool jedisPool = redisPoolFactory.JedisPoolFactory();
			jedis = jedisPool.getResource();
			//生成真正的key
			String realKey = prefix.getPrefix()+key;//UserKey:id 1
			return 	jedis.incr(realKey);
		} finally {
			returnJedisPool(jedis);
		}
	}
	/*
	 * 减少值
	 */
	public <T> Long decr(KeyPrefix prefix,String key, T value) {
		Jedis jedis = null;
		try {
			// 使用jedis 连接 ，使用jedispool获得jedis
			//JedisPool jedisPool = redisPoolFactory.JedisPoolFactory();
			jedis = jedisPool.getResource();
			//生成真正的key
			String realKey = prefix.getPrefix()+key;//UserKey:id 1
			return 	jedis.decr(realKey);
		} finally {
			returnJedisPool(jedis);
		}
	}
}
