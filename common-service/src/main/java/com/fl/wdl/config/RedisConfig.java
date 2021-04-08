package com.fl.wdl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.fl.wdl.pojo.User;

@Configuration
public class RedisConfig {
//	@Bean
//	public StringRedisTemplate stringRedisTemplate() {
//		return new StringRedisTemplate();
//	}
//	
	@Bean
	public RedisTemplate<String,User> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String,User> redisTemplate = new RedisTemplate<String,User>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}
}
