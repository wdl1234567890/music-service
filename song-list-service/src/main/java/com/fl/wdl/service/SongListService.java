package com.fl.wdl.service;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.fl.wdl.constant.ResponseStatus;
import com.fl.wdl.exception.FLException;
import com.fl.wdl.mapper.SongListMapper;
import com.fl.wdl.pojo.Scene;
import com.fl.wdl.pojo.Song;
import com.fl.wdl.pojo.SongList;
import com.fl.wdl.pojo.SongListScene;
import com.fl.wdl.pojo.SongListStyle;
import com.fl.wdl.pojo.Style;

@Service
public class SongListService {
	
	@Autowired
	SongListMapper songListMapper;
	
	@Autowired
	StyleService styleService;
	
	@Autowired
	SceneService sceneService;
	
	public SongList getSongListById(String id) {
		if(id == null || id.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		return songListMapper.getSongListById(id);
	}
	
	public String addSongList(SongList songList) {
		if(songList == null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		SongList songList1 = songListMapper.getSongListByAllMatchTitle(songList.getTitle());
		if(songList1!=null) {
			return songList1.getId();
		}
		
		int result = songListMapper.insert(songList);
		if(result < 1)throw new FLException(ResponseStatus.DATABASE_ERROR.code(),ResponseStatus.DATABASE_ERROR.message());
		return songList.getId();
	}
	
	public Boolean updateSongList(SongList songList) {
		if(songList == null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		int result = songListMapper.updateById(songList);
		if(result < 1)return false;
		return true;
	}
	
	public Boolean addSong(String songListId,String songId) {
		if(songListId == null || songListId.equals("") || songId == null || songId.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		Song song = new Song();
		song.setId(songId);
		SongList songList = this.getSongListById(songListId);
		if(songList == null || songList.getSongs() == null || songList.getSongs().contains(song))return false;
		return songListMapper.addSong(songListId, songId);
	}

	
	public List<Style> getStyles(String songListId){
		if(songListId == null || songListId.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		return JSONArray.parseArray(JSONArray.toJSONString(styleService.getStylesFromSongList(songListId).getData()), Style.class);
		
	}
	
	public List<Scene> getScenes(String songListId){
		if(songListId == null || songListId.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		return JSONArray.parseArray(JSONArray.toJSONString(sceneService.getScenesFromSongList(songListId).getData()), Scene.class);

	}
	
	
	public Boolean addStyle(String songListId,Integer styleId) {
		if(songListId == null || songListId.equals("") || styleId == 0 || styleId < 1)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		
		SongListStyle songListStyle = new SongListStyle();
		songListStyle.setSongListId(songListId);
		songListStyle.setStyleId(styleId);
		return styleService.addStyleToSongList(songListStyle).getSuccess();
	}
	
	public Boolean addScene(String songListId,Integer sceneId) {
		if(songListId == null || songListId.equals("") || sceneId == 0 || sceneId < 1)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		
		SongListScene songListScene = new SongListScene();
		songListScene.setSongListId(songListId);
		songListScene.setSceneId(sceneId);
		return sceneService.addSceneToSongList(songListScene).getSuccess();
	}
	
	
	public List<SongList> getSongListsByStyle(Integer styleId){
		if(styleId == null || styleId.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		return songListMapper.getSongListsByStyle(styleId);
	}
	
	public List<SongList> getSongListsByScene(Integer sceneId){
		if(sceneId == null || sceneId.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		return songListMapper.getSongListsByScene(sceneId);
	}
	
	public SongList getSongListBySingerId(Integer singerId){
		if(singerId == null || singerId.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		return songListMapper.getSongListBySingerId(singerId);
	}
	
	public List<SongList> getSongListsBySingerIds(List<Integer> singerIds){
		if(singerIds == null || singerIds.size() <= 0)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		List<SongList> songLists = singerIds.stream().map(singerId->this.getSongListBySingerId(singerId)).collect(Collectors.toList());
		if(songLists == null || songLists.size() <= 0)return null;
		songLists = songLists.stream().map(songList->this.getSongListById(songList.getId())).collect(Collectors.toList());
		return songLists;
	}
	
    public SongList getRandomSongList(){
    	List<SongList> songLists = songListMapper.getSongListsByAllMatchTitle("随机歌单");
    	return this.getSongListById(songLists.get(0).getId());
	}
    
    public SongList getHotSongList(){
    	List<SongList> songLists = songListMapper.getSongListsByAllMatchTitle("热门歌单");
    	if(songLists == null || songLists.size() == 0)return null;
    	return this.getSongListById(songLists.get(0).getId());
    }
    
    public SongList getNewSongList(){
    	List<SongList> songLists = songListMapper.getSongListsByAllMatchTitle("新歌歌单");
    	return this.getSongListById(songLists.get(0).getId());
    }
    
    public List<SongList> getSongListsByTitleLike(String title) {
    	if(title == null || title.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
    	return songListMapper.getSongListsByAllMatchTitle(title);
    }
    
    public Boolean addCommentCount(String id) {
		if(id == null || id.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		SongList songList = this.getSongListById(id);
		if(songList == null)throw new FLException(ResponseStatus.SONG_LIST_IS_NOT_EXISTED.code(),ResponseStatus.SONG_LIST_IS_NOT_EXISTED.message());
		return songListMapper.addCommentCount(id); 
	}
	public Boolean reduceCommentCount(String id) {
		if(id == null || id.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		SongList songList = this.getSongListById(id);
		if(songList == null)throw new FLException(ResponseStatus.SONG_LIST_IS_NOT_EXISTED.code(),ResponseStatus.SONG_LIST_IS_NOT_EXISTED.message());
		if(songList.getCommentCount() < 1)return false;	
		return songListMapper.reduceCommentCount(id); 
	}
	
    public List<SongList> getSongListsOfTopSevenNew(){
    	return songListMapper.getSongListsOfTopSevenNew();
    }
}





