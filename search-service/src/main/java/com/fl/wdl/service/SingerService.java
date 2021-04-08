package com.fl.wdl.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fl.wdl.pojo.Singer;
import com.fl.wdl.vo.CommonResult;

@Component
@FeignClient(name="singer-service")
public interface SingerService {
	
	@GetMapping("/singer/singerName/{singerName}")
	public CommonResult getSingersBySingerName(@PathVariable("singerName")String singerName);
}
