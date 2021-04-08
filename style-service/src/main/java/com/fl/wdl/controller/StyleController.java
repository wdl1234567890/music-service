package com.fl.wdl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fl.wdl.constant.ResponseStatus;
import com.fl.wdl.exception.FLException;
import com.fl.wdl.pojo.SongListStyle;
import com.fl.wdl.pojo.SongStyle;
import com.fl.wdl.pojo.Style;
import com.fl.wdl.pojo.UserStyle;
import com.fl.wdl.service.StyleService;
import com.fl.wdl.vo.CommonResult;

@RestController
@RequestMapping("/style")
public class StyleController {
	
	@Autowired
	StyleService styleService;
	
	@PostMapping
	public CommonResult addStyle(@RequestBody Style style) {
		Boolean result = styleService.addStyle(style);
		if(result)return CommonResult.buildSuccess(null);
		return CommonResult.buildError();
	}
	
	@GetMapping
	public CommonResult getList(){
		List<Style> styles = styleService.getStyleList();
		return CommonResult.buildSuccess(styles);
	}
	
	@GetMapping("/user/{userId}/styles")
	public CommonResult getStylesByUserId(@PathVariable("userId")Integer userId){
		List<Style> styles = styleService.getStylesByUserId(userId);
		return CommonResult.buildSuccess(styles);
	}
	
	@PostMapping("/user/style")
	public CommonResult addStyleToUser(@RequestBody UserStyle userStyle) {
		if(userStyle.getStyleId() == null || userStyle.getStyleId() < 1)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		Boolean result = styleService.addStyleToUser(userStyle.getUserId(), userStyle.getStyleId());
		if(result)return CommonResult.buildSuccess(null);
		return CommonResult.buildError();
	}
	
	@DeleteMapping("/user/style")
	public CommonResult removeStyleFromUser(@RequestBody UserStyle userStyle) {
		if(userStyle.getStyleId() == null || userStyle.getStyleId() < 1)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		Boolean result = styleService.removeStyleFromUser(userStyle.getUserId(), userStyle.getStyleId());
		if(result)return CommonResult.buildSuccess(null);
		return CommonResult.buildError();
	}

	@GetMapping("/song/{songId}/styles")
	public CommonResult getStylesFromSong(@PathVariable("songId")String songId){
		if(songId == null || songId.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		List<Style> styles = styleService.getStylesFromSong(songId);
		return CommonResult.buildSuccess(styles);
	}
	
	@PostMapping("/song/style")
	public CommonResult addStyleToSong(@RequestBody SongStyle songStyle) {
		if(songStyle == null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		Boolean result = styleService.addStyleToSong(songStyle.getSongId(), songStyle.getStyleId());
		if(result) return CommonResult.buildSuccess(null);
		return CommonResult.buildError();
	}
	
	@GetMapping("/songList/{songListId}/styles")
	public CommonResult getStylesFromSongList(@PathVariable("songListId")String songListId){
		List<Style> styles = styleService.getStylesFromSongList(songListId);
		return CommonResult.buildSuccess(styles);
	}
	
	@PostMapping("/songList/style")
	public CommonResult addStyleToSongList(@RequestBody SongListStyle songListStyle) {
		if(songListStyle == null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		Boolean result = styleService.addStyleToSongList(songListStyle.getSongListId(), songListStyle.getStyleId());
		if(result)return CommonResult.buildSuccess(null);
		return CommonResult.buildError();
	}
}
