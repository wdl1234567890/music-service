package com.fl.wdl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fl.wdl.pojo.Scene;
import com.fl.wdl.pojo.Song;
import com.fl.wdl.pojo.SongScene;
import com.fl.wdl.pojo.SongStyle;
import com.fl.wdl.pojo.Style;
import com.fl.wdl.service.SongService;
import com.fl.wdl.validation.Groups;
import com.fl.wdl.vo.CommonResult;

@RestController
@RequestMapping("/song")
public class SongController {
	
	@Autowired
	SongService songService;
	
	@GetMapping("/{id}")
	public CommonResult getSongById(@PathVariable("id")String id) {
		Song song = songService.getSongById(id);
		return CommonResult.buildSuccess(song);
	}
	
	@GetMapping("/type/{column}/key/{key}/songs")
	public CommonResult getSongsByColumnLike(@PathVariable("column")String column,@PathVariable("key")String key){
		List<Song> songs = songService.getSongsByColumnLike(column, key);
		return CommonResult.buildSuccess(songs);
	}
	
	@GetMapping
	public CommonResult getList(){
		List<Song> songs = songService.getList();
		return CommonResult.buildSuccess(songs);
	}
	
	@GetMapping("/style/{styleId}/songs")
	public CommonResult getListByStyle(@PathVariable("styleId")Integer styleId){
		List<Song> songs = songService.getListByStyle(styleId);
		return CommonResult.buildSuccess(songs);
	}
	
	@PostMapping
	public CommonResult addSong(@RequestBody Song song) {
		String result = songService.addSong(song);
		return CommonResult.buildSuccess(result);
	}
	
	
	@GetMapping("/{id}/styles")
	public CommonResult getStyles(@PathVariable("id")String id){
		List<Style> styles = songService.getStyles(id);
		return CommonResult.buildSuccess(styles);
		
	}
	
	@GetMapping("/{id}/scenes")
	public CommonResult getScenes(@PathVariable("id")String id){
		List<Scene> scenes = songService.getScenes(id);
		return CommonResult.buildSuccess(scenes);
	}
	

	@PostMapping("/style")
	public CommonResult addStyle(@RequestBody @Validated(Groups.Add.class)SongStyle songStyle) {
		Boolean result = songService.addStyle(songStyle.getSongId(), songStyle.getStyleId());
		if(result)return CommonResult.buildSuccess(null);
		return CommonResult.buildError();
	}
	
	@PostMapping("/scene")
	public CommonResult addScene(@RequestBody @Validated(Groups.Add.class)SongScene songScene) {
		Boolean result = songService.addScene(songScene.getSongId(), songScene.getSceneId());
		System.out.println(result);
		if(result)return CommonResult.buildSuccess(null);
		return CommonResult.buildError();
	}
	
	@PutMapping("/commentCount/add")
	public CommonResult addCommentCount(@RequestBody String id) {
		Boolean result = songService.addCommentCount(id); 
		if(result)return CommonResult.buildSuccess(null);
		return CommonResult.buildError();
	}
	
	@PutMapping("/commentCount/reduce")
	public CommonResult reduceCommentCount(@RequestBody String id) {
		Boolean result = songService.reduceCommentCount(id); 
		if(result)return CommonResult.buildSuccess(null);
		return CommonResult.buildError(); 
	}
	
	@GetMapping("/count")
	public CommonResult getSongCount() {
		int count = songService.getSongCount();
		return CommonResult.buildSuccess(count);
	}
}
