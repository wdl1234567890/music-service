package com.fl.wdl.service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.stereotype.Service;

import com.fl.wdl.constant.ConfigConst;
import com.fl.wdl.constant.ResponseStatus;
import com.fl.wdl.exception.FLException;
import com.fl.wdl.pojo.User;

@Service
public class TokenService {
	
	@Autowired
	private RedisTemplate<String,User> redisTemplate;
	
	public User getUser(String token) {
		if(token==null||token.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		return redisTemplate.opsForValue().get(token);
	}
	
	public String saveUserToCache(User user) {
		if(user==null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		String token = UUID.randomUUID().toString();
		redisTemplate.opsForValue().set(token, user);
		redisTemplate.expire(token, ConfigConst.REDIS_EXPIRE, TimeUnit.SECONDS);
		return token;
	}
}
