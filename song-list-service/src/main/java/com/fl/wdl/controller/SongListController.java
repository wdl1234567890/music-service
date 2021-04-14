package com.fl.wdl.controller;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fl.wdl.constant.ResponseStatus;
import com.fl.wdl.exception.FLException;
import com.fl.wdl.pojo.Scene;
import com.fl.wdl.pojo.SongList;
import com.fl.wdl.pojo.SongListScene;
import com.fl.wdl.pojo.SongListSong;
import com.fl.wdl.pojo.SongListStyle;
import com.fl.wdl.pojo.Style;
import com.fl.wdl.service.SongListService;
import com.fl.wdl.vo.CommonResult;

@RestController
@RequestMapping("/songList")
public class SongListController {
	
	@Autowired
	SongListService songListService;
	
	@GetMapping("/{id}")
	public CommonResult getSongListById(@PathVariable("id") String id) {
		SongList songList = songListService.getSongListById(id);
		return CommonResult.buildSuccess(songList);
	}
	
	@PostMapping
	public CommonResult addSongList(@RequestBody SongList songList) {
		if(songList == null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		String result = songListService.addSongList(songList);
		return CommonResult.buildSuccess(result);
	}
	
	@PutMapping
	public CommonResult updateSongList(@RequestBody SongList songList) {
		if(songList == null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		Boolean result = songListService.updateSongList(songList);
		if(result)return CommonResult.buildSuccess(null);
		return CommonResult.buildError();
	}
	
	@PostMapping("/song")
	public CommonResult addSong(@RequestBody SongListSong songListSong) {
		if(songListSong == null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		Boolean result = songListService.addSong(songListSong.getSongListId(),songListSong.getSongId());
		if(result)return CommonResult.buildSuccess(null);
		return CommonResult.buildError();
	}
	
	@PostMapping("/songList/songs")
	public CommonResult addSongs(@RequestBody List<SongListSong> songListSongs) {
		if(songListSongs == null || songListSongs.size() <= 0)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		songListSongs.forEach(songListSong->songListService.addSong(songListSong.getSongListId(),songListSong.getSongId()));
		return CommonResult.buildSuccess(null);
	}

	@GetMapping("/{songListId}/styles")
	public CommonResult getStyles(@PathVariable("songListId")String songListId){
		List<Style> styles = songListService.getStyles(songListId);
		return CommonResult.buildSuccess(styles);
		
	}
	
	@GetMapping("/{songListId}/scenes")
	public CommonResult getScenes(@PathVariable("songListId")String songListId){
		List<Scene> scenes = songListService.getScenes(songListId);
		return CommonResult.buildSuccess(scenes);
	}
	
	@PostMapping("/style")
	public CommonResult addStyle(@RequestBody SongListStyle songListStyle) {
		if(songListStyle == null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		Boolean result = songListService.addStyle(songListStyle.getSongListId(), songListStyle.getStyleId());
		if(result)return CommonResult.buildSuccess(null);
		return CommonResult.buildError();
	}
	
	@GetMapping("/style/{styleId}/songLists")
	public CommonResult getSongListsByStyle(@PathVariable("styleId")Integer styleId){
		List<SongList> songLists = songListService.getSongListsByStyle(styleId);
		return CommonResult.buildSuccess(songLists);
	}
	
	@GetMapping("/scene/{sceneId}/songLists")
	public CommonResult getSongListsByScene(@PathVariable("sceneId")Integer sceneId){
		List<SongList> songLists = songListService.getSongListsByScene(sceneId);
		return CommonResult.buildSuccess(songLists);
	}
	
	@GetMapping("/singer/{singerId}/songLists")
	public CommonResult getSongListsBySingerId(@PathVariable("singerId")Integer singerId){
		SongList songLists = songListService.getSongListsBySingerId(singerId);
		return CommonResult.buildSuccess(this.getSongListById(songLists.getId()));
	}
	
	@GetMapping("/random")
    public CommonResult getRandomSongList(){
		SongList songList = songListService.getRandomSongList();
		return CommonResult.buildSuccess(songList);
	}
    
	@GetMapping("/hot")
    public CommonResult getHotSongList(){
		SongList songList = songListService.getHotSongList();
		if(songList == null)return CommonResult.buildSuccess(null);
		List<String> songNames = songList.getSongs().stream().map(song->song.getSongName()).limit(3).collect(Collectors.toList());
		songList.getMetaObject().put("songNames", songNames);
		return CommonResult.buildSuccess(songList);
    }
    
	@GetMapping("/new")
    public CommonResult getNewSongList(){
		SongList songList = songListService.getNewSongList();
		List<String> songNames = songList.getSongs().stream().map(song->song.getSongName()).limit(3).collect(Collectors.toList());
		songList.getMetaObject().put("songNames", songNames);
    	return CommonResult.buildSuccess(songList);
    }
	
	@PostMapping("/scene")
	public CommonResult addScene(@RequestBody SongListScene songListScene) {
		if(songListScene == null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		Boolean result = songListService.addScene(songListScene.getSongListId(), songListScene.getSceneId());
		if(result)return CommonResult.buildSuccess(null);
		return CommonResult.buildError();
		
	}
	
	@GetMapping("/title/like/{title}")
	public CommonResult getSongListsByTitleLike(@PathVariable("title")String title) {
		List<SongList> songLists = songListService.getSongListsByTitleLike(title);
		return CommonResult.buildSuccess(songLists);
	}
	
	@PutMapping("/commentCount/add")
	public CommonResult addCommentCount(@RequestBody String id) {
		Boolean result = songListService.addCommentCount(id); 
		if(result)return CommonResult.buildSuccess(null);
		return CommonResult.buildError();
	}
	
	@PutMapping("/commentCount/reduce")
	public CommonResult reduceCommentCount(@RequestBody String id) {
		Boolean result = songListService.reduceCommentCount(id); 
		if(result)return CommonResult.buildSuccess(null);
		return CommonResult.buildError(); 
	}
}
