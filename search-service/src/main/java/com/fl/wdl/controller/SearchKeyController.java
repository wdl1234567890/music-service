package com.fl.wdl.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fl.wdl.pojo.SearchKey;
import com.fl.wdl.pojo.Singer;
import com.fl.wdl.pojo.Song;
import com.fl.wdl.pojo.SongList;
import com.fl.wdl.service.SearchService;
import com.fl.wdl.vo.CommonResult;

@RestController
@RequestMapping("/search")
@Validated
public class SearchKeyController {
	
	@Autowired
	SearchService searchService;
	
	@GetMapping("/{key}")
	public CommonResult search(@PathVariable("key") @Size(min=1,max=40)String key){
		Set<String> keys = new HashSet<>();
		List<Song> songs = searchService.searchSongs(key,keys);
		List<SongList> songLists = searchService.searchSongLists(key,keys);
		List<Singer> singers = searchService.searchSingers(key,keys);
		Map<String,Object> map = new HashMap<>();
		map.put("songs", songs);
		map.put("songLists", songLists);
		map.put("singers", singers);
		keys.stream().forEach(keyItem->{
			searchService.addKeyCount(keyItem);
		});
		return CommonResult.buildSuccess(map);
	}
	
	@GetMapping("/hotKey")
	public CommonResult getHotSearchKey(){
		List<SearchKey> searchKeys = searchService.getHotSearchKey();
        return CommonResult.buildSuccess(searchKeys);
	}
}
