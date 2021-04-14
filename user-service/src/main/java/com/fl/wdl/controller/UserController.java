package com.fl.wdl.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fl.wdl.pojo.Style;
import com.fl.wdl.pojo.User;
import com.fl.wdl.service.UserService;
import com.fl.wdl.utils.UserUtils;
import com.fl.wdl.vo.CommonResult;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/{id}")
	public CommonResult getUserById(@PathVariable("id") Integer id){
		User user = userService.getUserById(id);
		List<Style> styles = userService.getStyleList(id);
		Map<String,Object> data = new HashMap<>();
		data.put("userBase", user);
		data.put("styles", styles);
		return CommonResult.buildSuccess(data);
	}
	
	@GetMapping("/info")
	public CommonResult getUser(@RequestHeader("token")String token){
		//User user = UserUtils.getCurrentUser(token);
		User user = new User();
		user.setId(1);
		List<Style> styles = userService.getStyleList(user.getId());
		Map<String,Object> data = new HashMap<>();
		data.put("userBase", user);
		data.put("styles", styles);
		return CommonResult.buildSuccess(data);
	}
	
	@GetMapping("/OSSParam")
	public CommonResult getPostAvatorOSSParam(){
		Map<String,String> map = userService.getPostAvatorOSSParam();
		return CommonResult.buildSuccess(map);
	}
	
	@GetMapping
	public CommonResult getList(){
		List<User> users = userService.getList();
		return CommonResult.buildSuccess(users);
	}
	
	@PostMapping
	public CommonResult save(@RequestBody User user) {
		Integer result = userService.save(user);
		if(result != -1)return CommonResult.buildSuccess(result);
		return CommonResult.buildError();
	}
	
	@PutMapping
	public CommonResult update(@RequestHeader("token")String token,@RequestBody User user) {
		//Integer userId = UserUtils.getCurrentUser(token).getId();
		Integer userId = 1;
		user.setId(userId);
		Boolean result = userService.update(user);
		if(result)return CommonResult.buildSuccess(null);
		return CommonResult.buildError();
	}
	
	@PutMapping("/vip")
	public CommonResult upgradeToVip(@RequestHeader("token")String token) {
		//Integer userId = UserUtils.getCurrentUser(token).getId();
		Integer userId = 1;
		Boolean result = userService.upgradeToVip(userId);
		if(result)return CommonResult.buildSuccess(null);
		return CommonResult.buildError();
	}
	
	@PutMapping("/noVip")
	public CommonResult lowerToNormalUser(@RequestHeader("token")String token) {
		//Integer userId = UserUtils.getCurrentUser(token).getId();
		Integer userId = 1;
		Boolean result = userService.lowerToNormalUser(userId);
		if(result)return CommonResult.buildSuccess(null);
		return CommonResult.buildError();
	}
	
	@PutMapping("/styles")
	public CommonResult saveStyles(@RequestHeader("token")String token, @RequestBody List<Integer> styleIds) {	

		//Integer userId = UserUtils.getCurrentUser(token).getId();
		Integer userId = 1;
		Boolean result = userService.saveStyles(userId, styleIds);
		if(result)return CommonResult.buildSuccess(null);
		return CommonResult.buildError();
	}
	
	@GetMapping("/styles")
	public CommonResult getStyleList(@RequestHeader("token")String token){
		//Integer userId = UserUtils.getCurrentUser(token).getId();
		Integer userId = 1;
		List<Style> styles = userService.getStyleList(userId);
		return CommonResult.buildSuccess(styles);
	}
	
	@GetMapping("/{id}/styles")
	public CommonResult getStyleList(@PathVariable("id") Integer id){
		List<Style> styles = userService.getStyleList(id);
		return CommonResult.buildSuccess(styles);
	}
	
	@GetMapping("/count")
	public CommonResult getUserCount() {
		int count = userService.getUserCount();
		return CommonResult.buildSuccess(count);
	}

}
