package com.fl.wdl.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.fl.wdl.pojo.Scene;
import com.fl.wdl.pojo.SongListScene;
import com.fl.wdl.pojo.SongScene;
import com.fl.wdl.vo.CommonResult;

@Component
@FeignClient(name="scene-service")
public interface SceneService {
	
	
	@PostMapping("/scene")
	public CommonResult addScene(@RequestBody Scene scene);
	
	@GetMapping("/scene")
	public CommonResult getList();
	
	@GetMapping("/scene/song/{songId}/scenes")
	public CommonResult getScenesFromSong(@PathVariable("songId")String songId);
	
	@PostMapping("/scene/song/style")
	public CommonResult addSceneToSong(@RequestBody SongScene songScene);
	
	@GetMapping("/scene/songList/{songListId}/scenes")
	public CommonResult getScenesFromSongList(@PathVariable("songListId")String songListId);
	
	@PostMapping("/scene/songList/scene")
	public CommonResult addSceneToSongList(@RequestBody SongListScene songListScene);
}
