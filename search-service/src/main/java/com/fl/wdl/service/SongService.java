package com.fl.wdl.service;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.fl.wdl.vo.CommonResult;
@Component
@FeignClient(name="song-service")
public interface SongService {
	
//	@GetMapping("/song/{id}")
//	public CommonResult getSongById(@PathVariable("id")String id);
//	
	@GetMapping("/song/type/{column}/key/{key}/songs")
	public CommonResult getSongsByColumnLike(@PathVariable("column")String column,@PathVariable("key")String key);
	
//	@GetMapping("/song")
//	public CommonResult getList();
//	
//	@GetMapping("/song/song/style/{styleId}/songs")
//	public CommonResult getListByStyle(@PathVariable("styleId")Integer styleId);
//	
//	@PostMapping("/song")
//	public CommonResult addSong(@RequestBody Song song);
//	
//	@GetMapping("/song/{id}/styles")
//	public CommonResult getStyles(@PathVariable("id")String id);
//	
//	@GetMapping("/song/{id}/scenes")
//	public CommonResult getScenes(@PathVariable("id")String id);
//	
//
//	@PostMapping("/song/style")
//	public CommonResult addStyle(@RequestBody SongStyle songStyle);
//	
//	@PostMapping("/song/scene")
//	public CommonResult addScene(@RequestBody SongScene songScene);
//	
}
