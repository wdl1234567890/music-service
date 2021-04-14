package com.fl.wdl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fl.wdl.constant.ResponseStatus;
import com.fl.wdl.exception.FLException;
import com.fl.wdl.mapper.StyleMapper;
import com.fl.wdl.pojo.Scene;
import com.fl.wdl.pojo.SongStyle;
import com.fl.wdl.pojo.Style;

@Service
public class StyleService {
	@Autowired
	StyleMapper styleMapper;

	public List<Style> getStyleList(){
		return styleMapper.selectList(null);
	}
	
	public List<Style> getStylesByUserId(Integer userId){
		if(userId == null || userId.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		return styleMapper.getStylesByUserId(userId);
	}
	
	public Boolean addStyle(Style style) {
		if(style == null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		int result = styleMapper.insert(style);
		if(result < 1)return false;
		return true;
	}
	
	
	public Boolean addStylesToUser(Integer userId, List<Integer> styleIds) {
		if(userId == null || userId < 1 || styleIds == null || styleIds.size() <= 0)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		styleIds.forEach(styleId->{
			styleMapper.addStyleToUser(userId, styleId);
		});
		return true;
	}
	public Boolean removeStylesFromUser(Integer userId,List<Integer> styleIds) {
		if(userId == null || userId < 1 || styleIds == null || styleIds.size() <= 0)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		styleIds.forEach(styleId->{
			styleMapper.removeStyleFromUser(userId, styleId);
		});
		return true;
	}
	
	public List<Style> getStylesFromSong(String songId){
		if(songId == null || songId.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		return styleMapper.getStylesFromSong(songId);
	}
	
	public Boolean addStyleToSong(String songId, Integer styleId) {
		if(songId == null || songId.equals("") || styleId == null || styleId < 1)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		Style style = styleMapper.getStyleBySongIdAndStyleId(songId, styleId);
        if(style != null)return true;
		return styleMapper.addStyleToSong(songId, styleId);
	}
	
	
	public List<Style> getStylesFromSongList(String songListId){
		if(songListId == null || songListId.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		return styleMapper.getStylesFromSongList(songListId);
	}
	
	public Boolean addStyleToSongList(String songListId, Integer styleId) {
		if(songListId == null || songListId.equals("") || styleId == null || styleId < 1)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		Style style = styleMapper.getStyleBySongListIdAndStyleId(songListId, styleId);
        if(style != null)return true;
		return styleMapper.addStyleToSongList(songListId, styleId);
	}
}
