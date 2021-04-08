package com.fl.wdl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fl.wdl.constant.ResponseStatus;
import com.fl.wdl.exception.FLException;
import com.fl.wdl.mapper.SongMapper;
import com.fl.wdl.pojo.Scene;
import com.fl.wdl.pojo.Song;
import com.fl.wdl.pojo.SongScene;
import com.fl.wdl.pojo.SongStyle;
import com.fl.wdl.pojo.Style;

@Service
public class SongService {
	
	@Autowired
	SongMapper songMapper;
	
	@Autowired
	StyleService styleService;
	
	@Autowired
	SceneService sceneService;
	
	public Song getSongById(String id) {
		if(id==null || id.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		return songMapper.selectById(id);
	}
	
	public List<Song> getSongsByColumnLike(String column,String key){
		if(key==null||key.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		QueryWrapper<Song> queryWrapper = new QueryWrapper<>();
		queryWrapper.like(column, key);
		return songMapper.selectList(queryWrapper);
	}
	public List<Song> getList(){
		return songMapper.selectList(null);
	}
	
	public List<Song> getListByStyle(Integer id){
		if(id == null || id < 1)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		return songMapper.getListByStyle(id);
	}
	
	public String addSong(Song song) {
		if(song == null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		QueryWrapper<Song> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("lyric", song.getLyric());
		Song song1 = songMapper.selectOne(queryWrapper);
		if(song1!=null) {
			return song1.getId();
		}
		int result = songMapper.insert(song);
		if(result < 1)throw new FLException(ResponseStatus.DATABASE_ERROR.code(),ResponseStatus.DATABASE_ERROR.message());
		return song.getId();
	}
	
	public List<Style> getStyles(String songId){
		if(songId == null || songId.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		return JSONArray.parseArray(JSONArray.toJSONString(styleService.getStylesFromSong(songId).getData()), Style.class);
		
	}
	
	public List<Scene> getScenes(String songId){
		if(songId == null || songId.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		return JSONArray.parseArray(JSONArray.toJSONString(sceneService.getScenesFromSong(songId).getData()), Scene.class);

	}
	

	public Boolean addStyle(String songId, Integer styleId) {
		if(songId == null || songId.equals("") || styleId == null || styleId < 1)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		SongStyle songStyle = new SongStyle();
		songStyle.setSongId(songId);
		songStyle.setStyleId(styleId);
		return styleService.addStyleToSong(songStyle).getSuccess();
	}
	

	public Boolean addScene(String songId, Integer sceneId) {
		if(songId == null || songId.equals("") || sceneId == null || sceneId < 1)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		SongScene songScene = new SongScene();
		songScene.setSongId(songId);
		songScene.setSceneId(sceneId);
		return sceneService.addSceneToSong(songScene).getSuccess();
	}
	public Boolean addCommentCount(String id) {
		if(id == null || id.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		Song song = this.getSongById(id);
		if(song == null)throw new FLException(ResponseStatus.SONG_IS_NOT_EXISTED.code(),ResponseStatus.SONG_IS_NOT_EXISTED.message());
		return songMapper.addCommentCount(id); 
	}
	public Boolean reduceCommentCount(String id) {
		if(id == null || id.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		Song song = this.getSongById(id);
		if(song == null)throw new FLException(ResponseStatus.SONG_IS_NOT_EXISTED.code(),ResponseStatus.SONG_IS_NOT_EXISTED.message());
		if(song.getCommentCount() < 1)return false;
		return songMapper.reduceCommentCount(id); 
	}
}









