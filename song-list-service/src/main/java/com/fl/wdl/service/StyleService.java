package com.fl.wdl.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fl.wdl.pojo.SongListStyle;
import com.fl.wdl.pojo.Style;
import com.fl.wdl.vo.CommonResult;

@Component
@FeignClient(name="style-service")
public interface StyleService {
	
	@PostMapping("/style")
	public CommonResult addStyle(@RequestBody Style style);
	
	@GetMapping("/style")
	public CommonResult getList();

	@GetMapping("/style/songList/{songListId}/styles")
	public CommonResult getStylesFromSongList(@PathVariable("songListId")String songListId);
	
	@PostMapping("/style/songList/style")
	public CommonResult addStyleToSongList(@RequestBody SongListStyle songListStyle);
}
