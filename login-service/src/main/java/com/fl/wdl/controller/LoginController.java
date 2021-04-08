package com.fl.wdl.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fl.wdl.constant.ResponseStatus;
import com.fl.wdl.exception.FLException;
import com.fl.wdl.pojo.User;
import com.fl.wdl.service.LoginService;
import com.fl.wdl.vo.CommonResult;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	LoginService loginService;
	
	
	@GetMapping
	public CommonResult login(String code,String userName,String avator) {
		String openId = loginService.getOpenId(code);
		User user = new User();
		user.setUserName(userName);
		user.setAvator(avator);
		user.setOpenId(openId);
		String token = loginService.save(user);
		
		Map<String,String> data = new HashMap<String,String>();
		data.put("token", token);
		return CommonResult.buildSuccess(data);
	}
	

}
