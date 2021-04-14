package com.fl.wdl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fl.wdl.constant.ResponseStatus;
import com.fl.wdl.exception.FLException;
import com.fl.wdl.mapper.SingerMapper;
import com.fl.wdl.pojo.Singer;
import com.fl.wdl.pojo.SongList;

@Service
public class SingerService {
	
	@Autowired
	SingerMapper singerMapper;
	
	public Integer addSinger(Singer singer) {
		if(singer == null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(), ResponseStatus.PARAM_IS_EMPTY.message());
		QueryWrapper<Singer> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("name", singer.getName());
		Singer singer1 = singerMapper.selectOne(queryWrapper);
		if(singer1 != null) {
			return singer1.getId();
		}
		int result = singerMapper.insert(singer);
		if(result < 1)throw new FLException(ResponseStatus.DATABASE_ERROR.code(), ResponseStatus.DATABASE_ERROR.message());
		return singer.getId();
	}
	
	public Boolean addSongList(String songListId,Integer singerId) {
		if(songListId == null || songListId.equals("") || singerId == null || singerId.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(), ResponseStatus.PARAM_IS_EMPTY.message());
		SongList songList = singerMapper.getSongList(singerId);
		if(songList != null)return false;
		return singerMapper.addSongList(songListId, singerId);
	}
	
	public List<Singer> getSingersBySingerName(String singerName){
		if(singerName == null || singerName.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(), ResponseStatus.PARAM_IS_EMPTY.message());
		QueryWrapper<Singer> queryWrapper = new QueryWrapper<>();
		queryWrapper.like("name", singerName);
		return singerMapper.selectList(queryWrapper);
	}
	
	public List<Singer> getSingersBySingerNames(List<String> singerNames){
		if(singerNames == null || singerNames.size() <= 0)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(), ResponseStatus.PARAM_IS_EMPTY.message());
		QueryWrapper<Singer> queryWrapper = new QueryWrapper<>();
		queryWrapper.in("name", singerNames);
		return singerMapper.selectList(queryWrapper);
	}
	
//	public List<Singer> getSingers(){
//		
//	}
}
