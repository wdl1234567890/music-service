package com.fl.wdl.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.fl.wdl.constant.ResponseStatus;
import com.fl.wdl.exception.FLException;
import com.fl.wdl.pojo.Style;
import com.fl.wdl.pojo.UserStyle;
import com.fl.wdl.vo.CommonResult;

@Component
@FeignClient(name="style-service")
public interface StyleService {
	
	@PostMapping("/style")
	public CommonResult addStyle(@RequestBody Style style);
	
	@GetMapping("/style")
	public CommonResult getList();
	
	@GetMapping("/style/user/{userId}/styles")
	public CommonResult getStylesByUserId(@PathVariable("userId")Integer userId);
	
	@PostMapping("/style/user/styles")
	public CommonResult addStylesToUser(@RequestBody List<UserStyle> userStyle);
	
	@DeleteMapping("/style/user/styles")
	public CommonResult removeStylesFromUser(@RequestBody List<UserStyle> userStyle);
}
