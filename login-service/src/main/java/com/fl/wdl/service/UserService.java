package com.fl.wdl.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.fl.wdl.pojo.User;
import com.fl.wdl.vo.CommonResult;

@Component
@FeignClient(name="user-service")
public interface UserService {
	
	@GetMapping("/user/{id}")
	public CommonResult getUserById(@PathVariable("id")Integer id);
	
	@GetMapping("/user")
	public CommonResult getList();
	
	@PostMapping("/user")
	public CommonResult save(@RequestBody User user);
	
	@PutMapping("/user")
	public CommonResult update(@RequestHeader("token")String token,@RequestBody User user);
	
	@PutMapping("/user/vip")
	public CommonResult upgradeToVip(@RequestHeader("token")String token);
	
	@PutMapping("/user/noVip")
	public CommonResult lowerToNormalUser(@RequestHeader("token")String token);
	
	@PostMapping("/user/style")
	public CommonResult addStyle(@RequestHeader("token")String token, @RequestBody List<Integer> styleIds);
	
	@DeleteMapping("/user/style")
	public CommonResult removeStyle(@RequestHeader("token")String token, @RequestBody List<Integer> styleIds);
}
