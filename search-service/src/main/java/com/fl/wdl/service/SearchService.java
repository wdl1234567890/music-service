package com.fl.wdl.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fl.wdl.constant.ResponseStatus;
import com.fl.wdl.exception.FLException;
import com.fl.wdl.mapper.SearchKeyMapper;
import com.fl.wdl.pojo.SearchKey;
import com.fl.wdl.pojo.Singer;
import com.fl.wdl.pojo.Song;
import com.fl.wdl.pojo.SongList;

@Service
public class SearchService {
	@Autowired
	SearchKeyMapper searchKeyMapper;
	
	@Autowired
	SongService songService;
	
	@Autowired
	SongListService songListService;
	
	@Autowired
	SingerService singerService;
	
	public Boolean addKeyCount(String key) {
		QueryWrapper<SearchKey> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("one_key", key);
		if(searchKeyMapper.selectOne(queryWrapper) == null) {
			SearchKey searchKey = new SearchKey(null,key,1);
			searchKeyMapper.insert(searchKey);
		}else{
			searchKeyMapper.addCount(key);
		}
		return true;
	}
	
	
	public List<Song> searchSongs(String key,Set<String> keys){
		if(key == null || key.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(), ResponseStatus.PARAM_IS_EMPTY.message());
		List<Song> songsResult = JSONArray.parseArray(JSONArray.toJSONString(songService.getSongsByColumnLike("song_name", key).getData()), Song.class);
		
		final List<Song> songs = songsResult == null?new LinkedList<>():songsResult;
		songs.stream().forEach(song->{
			String songName = song.getSongName();
			if(Math.abs(key.length() - songName.length()) < 3)keys.add(songName);
		});
		List<Song> songs2 = JSONArray.parseArray(JSONArray.toJSONString(songService.getSongsByColumnLike("singer_name", key).getData()), Song.class);
        if(songs2 != null && songs2.size() != 0) {
        	
        	songs2.stream().forEach(song->{
    			if(!songs.contains(song)) {
    				songs.add(song);
    				String singerName = song.getSingerName();
    				if(Math.abs(key.length() - singerName.length()) < 3)keys.add(singerName);
    			}
    		});
        }
		
		
		return songs;
	}
	
	public List<SongList> searchSongLists(String key,Set<String> keys){
		if(key == null || key.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(), ResponseStatus.PARAM_IS_EMPTY.message());
		List<SongList> songListsResult = JSONArray.parseArray(JSONArray.toJSONString(songListService.getSongListsByTitleLike(key).getData()), SongList.class);
		final List<SongList> songLists = songListsResult == null?new LinkedList<>():songListsResult;
		songLists.stream().forEach(songList->{
			String title = songList.getTitle();
			if(Math.abs(key.length() - title.length()) < 3)keys.add(title);
		});
		return songLists;
	}
	
	public List<Singer> searchSingers(String key,Set<String> keys){
		if(key == null || key.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(), ResponseStatus.PARAM_IS_EMPTY.message());
		List<Singer> singersResult = JSONArray.parseArray(JSONArray.toJSONString(singerService.getSingersBySingerName(key).getData()), Singer.class);
		final List<Singer> singers = singersResult == null?new LinkedList<>():singersResult;
		singers.stream().forEach(singer->{
			String name = singer.getName();
			if(Math.abs(key.length() - name.length()) < 3)keys.add(name);
		});
		return singers;
	}
	
	public List<SearchKey> getHotSearchKey(){
		List<SearchKey> searchKeys = searchKeyMapper.selectList(null);
		return searchKeys.stream().sorted((keyA,keyB)->{
			return keyB.getSearchCount() - keyA.getSearchCount();
		}).limit(10).collect(Collectors.toList());
	}
}
