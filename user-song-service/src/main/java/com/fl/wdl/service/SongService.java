package com.fl.wdl.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.fl.wdl.vo.CommonResult;

@Component
@FeignClient(name="song-service")
public interface SongService {
	
	@GetMapping("/song/{id}")
	public CommonResult getSongById(@PathVariable("id")String id);
	
	@GetMapping("/song/count")
	public CommonResult getSongCount();
	
	@GetMapping
	public CommonResult getList();
	
	@GetMapping("/song/{id}/styles")
	public CommonResult getStyles(@PathVariable("id")String id);
	
	@GetMapping("/song/ids/songs")
	public CommonResult getSongsByIds(@RequestBody List<String> ids);
	
	@GetMapping("/song/singers/songs")
	public CommonResult getSongsBySingerNames(@RequestBody List<String> singerNames);
	
//	@GetMapping("/type/{column}/key/{key}/songs")
//	public CommonResult getSongsByColumnLike(@PathVariable("column")String column,@PathVariable("key")String key);
//	
//	@GetMapping
//	public CommonResult getList();
	
	@GetMapping("/song/style/{styleId}/songs")
	public CommonResult getListByStyle(@PathVariable("styleId")Integer styleId);
	
//	@PostMapping
//	public CommonResult addSong(@RequestBody Song song);
//	
//	@GetMapping("/{id}/styles")
//	public CommonResult getStyles(@PathVariable("id")String id);
//	
//	@GetMapping("/{id}/scenes")
//	public CommonResult getScenes(@PathVariable("id")String id);
//	
//
//	@PostMapping("/style")
//	public CommonResult addStyle(@RequestBody SongStyle songStyle);
//	
//	@PostMapping("/scene")
//	public CommonResult addScene(@RequestBody SongScene songScene);
}
