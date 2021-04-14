package com.fl.wdl.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fl.wdl.pojo.SongList;
import com.fl.wdl.pojo.SongListScene;
import com.fl.wdl.pojo.SongListSong;
import com.fl.wdl.pojo.SongListStyle;
import com.fl.wdl.vo.CommonResult;

@Component
@FeignClient(name="song-list-service")
public interface SongListService {
	@GetMapping("/songList/{id}")
	public CommonResult getSongListById(@PathVariable("id") String id);
	
	@PostMapping("/songList")
	public CommonResult addSongList(@RequestBody SongList songList);
	
	@PutMapping("/songList")
	public CommonResult updateSongList(@RequestBody SongList songList);
	
	@PostMapping("/songList/song")
	public CommonResult addSong(@RequestBody SongListSong songListSong);
	
	@PostMapping("/songList/songs")
	public CommonResult addSongs(@RequestBody List<SongListSong> songListSong);

	@GetMapping("/songList/{songListId}/styles")
	public CommonResult getStyles(@PathVariable("songListId")String songListId);
	
	@GetMapping("/songList/{songListId}/scenes")
	public CommonResult getScenes(@PathVariable("songListId")String songListId);
	
	@PostMapping("/songList/style")
	public CommonResult addStyle(@RequestBody SongListStyle songListStyle);
	
	@GetMapping("/songList/style/{styleId}/songLists")
	public CommonResult getSongListsByStyle(@PathVariable("styleId")Integer styleId);
	
	@GetMapping("/songList/scene/{sceneId}/songLists")
	public CommonResult getSongListsByScene(@PathVariable("sceneId")Integer sceneId);
	
	@GetMapping("/songList/singer/{singerId}/songLists")
	public CommonResult getSongListsBySingerId(@PathVariable("singerId")Integer singerId);
	
	@GetMapping("/songList/random")
    public CommonResult getRandomSongList();
    
	@GetMapping("/songList/hot")
    public CommonResult getHotSongList();
    
	@GetMapping("/songList/new")
    public CommonResult getNewSongList();
	
	@PostMapping("/songList/scene")
	public CommonResult addScene(@RequestBody SongListScene songListScene);
	
	@GetMapping("/songList/title/like/{title}")
	public CommonResult getSongListsByTitleLike(@PathVariable("title")String title);
	
	@PutMapping("/songList/commentCount/add")
	public CommonResult addCommentCount(@RequestBody String id);
	
	@PutMapping("/songList/commentCount/reduce")
	public CommonResult reduceCommentCount(@RequestBody String id);
}
