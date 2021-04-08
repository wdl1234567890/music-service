package com.fl.wdl.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import com.fl.wdl.constant.ResponseStatus;
import com.fl.wdl.exception.FLException;
import com.fl.wdl.pojo.User;

@Service
public class LoginService {
	
	@Value("${login.url}")
	private String loginUrl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenService tokenService;
	
	public String getOpenId(String code) {
		if(code == "" || code.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		Map<String,Object> result = restTemplate.getForObject(String.format(loginUrl, code), Map.class);
		
		String openId = (String)result.get("openid");	
		
		return openId;
	}
	
	public String save(User user) {
		if(user == null || user.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		userService.save(user);
		return tokenService.saveUserToCache(user);
	}

	
	
}
