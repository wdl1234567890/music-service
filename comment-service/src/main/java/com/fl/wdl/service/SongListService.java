package com.fl.wdl.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fl.wdl.vo.CommonResult;

@Component
@FeignClient(name="song-list-service")
public interface SongListService {
	@PutMapping("/songList/commentCount/add")
	public CommonResult addCommentCount(@RequestBody String id);
	
	@PutMapping("/songList/commentCount/reduce")
	public CommonResult reduceCommentCount(@RequestBody String id);
}
