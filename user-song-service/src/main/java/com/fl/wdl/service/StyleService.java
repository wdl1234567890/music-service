package com.fl.wdl.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.fl.wdl.vo.CommonResult;
@Component
@FeignClient(name="style-service")
public interface StyleService {
	
//	@PostMapping
//	public CommonResult addStyle(@RequestBody Style style);
//	
	@GetMapping
	public CommonResult getList();
	
	@GetMapping("/style/user/{userId}/styles")
	public CommonResult getStylesByUserId(@PathVariable("userId")Integer userId);
	
//	@PostMapping("/user/style")
//	public CommonResult addStyleToUser(@RequestBody UserStyle userStyle);
//	
//	@DeleteMapping("/user/style")
//	public CommonResult removeStyleFromUser(@RequestBody UserStyle userStyle);
//
//	@GetMapping("/song/{songId}/styles")
//	public CommonResult getStylesFromSong(@PathVariable("songId")String songId);
//	@PostMapping("/song/style")
//	public CommonResult addStyleToSong(@RequestBody SongStyle songStyle);
//	
//	@GetMapping("/songList/{songListId}/styles")
//	public CommonResult getStylesFromSongList(@PathVariable("songListId")String songListId);
//	
//	@PostMapping("/songList/style")
//	public CommonResult addStyleToSongList(@RequestBody SongListStyle songListStyle) ;
}
