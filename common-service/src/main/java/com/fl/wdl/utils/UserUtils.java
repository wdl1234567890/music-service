package com.fl.wdl.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;

import com.fl.wdl.pojo.User;

public class UserUtils {
	@Autowired
	static RedisTemplate<String,User> redisTemplate;
	
	public static User getCurrentUser(String token) {
		return redisTemplate.opsForValue().get(token);
	}
}
