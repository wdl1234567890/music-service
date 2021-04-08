package com.fl.wdl.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fl.wdl.pojo.Singer;
import com.fl.wdl.pojo.SingerSongList;
import com.fl.wdl.vo.CommonResult;

@Component
@FeignClient(name="singer-service")
public interface SingerService {
	
	
	@PostMapping("/singer")
	public CommonResult addSinger(@RequestBody Singer singer);
	
	@PostMapping("/singer/songList")
	public CommonResult addSongList(@RequestBody SingerSongList singerSongList);
	
	@GetMapping("/singer/singerName/{singerName}")
	public CommonResult getSingersBySingerName(@PathVariable("singerName")String singerName);
}
