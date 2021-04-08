package com.fl.wdl.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fl.wdl.pojo.Song;
import com.fl.wdl.pojo.User;
import com.fl.wdl.pojo.UserSong;
import com.fl.wdl.service.UserSongService;
import com.fl.wdl.utils.UserUtils;
import com.fl.wdl.vo.CommonResult;

@RestController
@RequestMapping("/recommend")
public class UserSongController {
	
	@Autowired
	UserSongService userSongService;
	
	@PutMapping("/action/{key}/song/{songId}/score")
	public CommonResult addActionCount(@RequestHeader("token")String token, @PathVariable("key")String key, @PathVariable("songId")String songId) {
//		User user = UserUtils.getCurrentUser(token);
		User user = new User();
		user.setId(1);
		Boolean result = userSongService.addActionCount(key, songId, user);
		if(result)return CommonResult.buildSuccess(null);
		return CommonResult.buildError();
	}
	
	@PutMapping("/cancelAction/{key}/song/{songId}/score")
	public CommonResult reduceActionCount(@RequestHeader("token")String token,@PathVariable("key")String key, @PathVariable("songId")String songId) {
//		User user = UserUtils.getCurrentUser(token);
		User user = new User();
		user.setId(1);
		Boolean result = userSongService.reduceActionCount(key, songId, user);
		if(result)return CommonResult.buildSuccess(null);
		return CommonResult.buildError();
	}
	
	@GetMapping("/song/{songId}/score")
	public CommonResult getUserSongBySongIdAndUserId(@RequestHeader("token")String token,@PathVariable("songId")String songId) {
//		User user = UserUtils.getCurrentUser(token);
		User user = new User();
		user.setId(1);
		UserSong userSong = userSongService.getUserSongBySongIdAndUser(user.getId(), songId);
		return CommonResult.buildSuccess(userSong);
	}
	
	@GetMapping("/topFiveSong")
	public CommonResult getTopFiveSongByUserId(@RequestHeader("token")String token){
//		User user = UserUtils.getCurrentUser(token);
		User user = new User();
		user.setId(1);
		List<Map<String,Object>> datas = userSongService.getTopFiveSongByUser(user);
		return CommonResult.buildSuccess(datas);
	}
	
    @GetMapping("/topTenSimilarUser")
	public CommonResult getTopTenSimilarUser(@RequestHeader("token")String token){
//		User user = UserUtils.getCurrentUser(token);
		User user = new User();
		user.setId(1);
    	List<User> users = userSongService.getTopTenSimilarUser(user);
    	return CommonResult.buildSuccess(users);
    }

    @GetMapping("/personRecommendSongListOne")
	public CommonResult getRecommendSongListOne(@RequestHeader("token")String token){
//		User user = UserUtils.getCurrentUser(token);
		User user = new User();
		user.setId(1);
    	List<Song> songs = userSongService.getRecommendSongListOne(user);
    	return CommonResult.buildSuccess(songs);
    }
	
    @GetMapping("/personRecommendSongListTwo")
	public CommonResult getRecommendSongListTwo(@RequestHeader("token")String token){
//		User user = UserUtils.getCurrentUser(token);
		User user = new User();
		user.setId(1);
    	List<Song> songs = userSongService.getRecommendSongListTwo(user);
    	return CommonResult.buildSuccess(songs);
    }
}
