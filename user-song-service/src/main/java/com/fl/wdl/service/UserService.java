package com.fl.wdl.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import com.fl.wdl.vo.CommonResult;

@Component
@FeignClient(name="user-service")
public interface UserService {
	
//	@GetMapping("/{id}")
//	public CommonResult getUserById(@PathVariable("id") Integer id);
//	
	@GetMapping("/user")
	public CommonResult getList();
//	
//	@PostMapping
//	public CommonResult save(@RequestBody User user);
//	
//	@PutMapping
//	public CommonResult update(@RequestHeader("token")String token,@RequestBody User user);
//	
//	@PutMapping("/vip")
//	public CommonResult upgradeToVip(@RequestHeader("token")String token);
//	
//	@PutMapping("/noVip")
//	public CommonResult lowerToNormalUser(@RequestHeader("token")String token);
//	
//	@PostMapping("/styles")
//	public CommonResult addStyles(@RequestHeader("token")String token, @RequestBody List<Integer> styleIds);
//	
//	@DeleteMapping("/styles")
//	public CommonResult removeStyles(@RequestHeader("token")String token, @RequestBody List<Integer> styleIds);
//	
//	@GetMapping("/styles")
//	public CommonResult getStyleList(@RequestHeader("token")String token);
	
}
