package com.fl.wdl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fl.wdl.constant.ResponseStatus;
import com.fl.wdl.exception.FLException;
import com.fl.wdl.pojo.Singer;
import com.fl.wdl.pojo.SingerSongList;
import com.fl.wdl.service.SingerService;
import com.fl.wdl.vo.CommonResult;

@RestController
@RequestMapping("/singer")
public class SingerController {
	
	@Autowired
	SingerService singerService;
	
	@PostMapping
	public CommonResult addSinger(@RequestBody Singer singer) {
		Integer result = singerService.addSinger(singer);
		return CommonResult.buildSuccess(result);
	}
	
	@PostMapping("/songList")
	public CommonResult addSongList(@RequestBody SingerSongList singerSongList) {
		Boolean result = singerService.addSongList(singerSongList.getSongListId(), singerSongList.getSingerId());
		if(result)return CommonResult.buildSuccess(null);
		return CommonResult.buildError();
	}
	
	@GetMapping("/singerName/{singerName}")
	public CommonResult getSingersBySingerName(@PathVariable("singerName")String singerName){
		List<Singer> singers = singerService.getSingersBySingerName(singerName);
		return CommonResult.buildSuccess(singers);
	}
	
	//getSongList
}
