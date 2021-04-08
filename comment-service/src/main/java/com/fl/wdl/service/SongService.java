package com.fl.wdl.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fl.wdl.vo.CommonResult;

@Component
@FeignClient(name="song-service")
public interface SongService {
	@PutMapping("/song/commentCount/add")
	public CommonResult addCommentCount(@RequestBody String id);
	
	@PutMapping("/song/commentCount/reduce")
	public CommonResult reduceCommentCount(@RequestBody String id);
}
