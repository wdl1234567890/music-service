package com.fl.wdl.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.fl.wdl.vo.CommonResult;

@Component
@FeignClient(name="song-list-service")
public interface SongListService {
	@GetMapping("/songList/title/like/{title}")
	public CommonResult getSongListsByTitleLike(@PathVariable("title")String title);
}
