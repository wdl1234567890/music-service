package com.fl.wdl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fl.wdl.constant.ResponseStatus;
import com.fl.wdl.exception.FLException;
import com.fl.wdl.pojo.Scene;
import com.fl.wdl.pojo.SongListScene;
import com.fl.wdl.pojo.SongScene;
import com.fl.wdl.service.SceneService;
import com.fl.wdl.vo.CommonResult;

@RestController
@RequestMapping("/scene")
public class SceneController {
	
	@Autowired
	SceneService sceneService;
	
	@PostMapping
	public CommonResult addScene(@RequestBody Scene scene) {
		Boolean result = sceneService.addScene(scene);
		if(result)return CommonResult.buildSuccess(null);
		return CommonResult.buildError();
	}
	
	@GetMapping
	public CommonResult getList(){
		List<Scene> scenes = sceneService.getList();
		return CommonResult.buildSuccess(scenes);
	}
	
	@GetMapping("/song/{songId}/scenes")
	public CommonResult getScenesFromSong(@PathVariable("songId")String songId){
		List<Scene> scenes = sceneService.getScenesFromSong(songId);
		return CommonResult.buildSuccess(scenes);
	}
	
	@PostMapping("/song/style")
	public CommonResult addSceneToSong(@RequestBody SongScene songScene) {
		Boolean result = sceneService.addSceneToSong(songScene.getSongId(), songScene.getSceneId());
        if(result)return CommonResult.buildSuccess(null);
        return CommonResult.buildError();
	}
	
	@GetMapping("/songList/{songListId}/scenes")
	public CommonResult getScenesFromSongList(@PathVariable("songListId")String songListId){
		List<Scene> scenes = sceneService.getScenesFromSongList(songListId);
		return CommonResult.buildSuccess(scenes);
	}
	
	@PostMapping("/songList/scene")
	public CommonResult addSceneToSongList(@RequestBody SongListScene songListScene) {
		if(songListScene == null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		Boolean result = sceneService.addSceneToSongList(songListScene.getSongListId(), songListScene.getSceneId());
		if(result)return CommonResult.buildSuccess(songListScene);
		return CommonResult.buildError();
	}
}