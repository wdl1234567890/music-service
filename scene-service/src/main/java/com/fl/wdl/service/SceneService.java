package com.fl.wdl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fl.wdl.constant.ResponseStatus;
import com.fl.wdl.exception.FLException;
import com.fl.wdl.mapper.SceneMapper;
import com.fl.wdl.pojo.Scene;

@Service
public class SceneService {
	@Autowired
	SceneMapper sceneMapper;

	public Boolean addScene(Scene scene) {
		if(scene == null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		int result = sceneMapper.insert(scene);
		if(result < 1)return false;
		return true;
	}
	
	public List<Scene> getList(){
		return sceneMapper.selectList(null);
	}
	
	public List<Scene> getScenesFromSong(String songId){
		if(songId == null || songId.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		return sceneMapper.getScenesFromSong(songId);
	}
	
	public Boolean addSceneToSong(String songId, Integer sceneId) {
		if(songId == null || songId.equals("") || sceneId == null || sceneId < 1)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		Scene scene = sceneMapper.getSceneBySongListIdAndSceneId(songId, sceneId);
        if(scene != null)return true;
		return sceneMapper.addSceneToSong(songId, sceneId);
	}
	
	
	public Boolean addSceneToSongList(String songListId, Integer sceneId) {
		if(songListId == null || songListId.equals("") || sceneId == null || sceneId < 1)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		Scene scene = sceneMapper.getSceneBySongListIdAndSceneId(songListId, sceneId);
		if(scene != null)return true;
		return sceneMapper.addSceneToSongList(songListId, sceneId);
	}
	
	public List<Scene> getScenesFromSongList(String songListId){
		return sceneMapper.getScenesFromSongList(songListId);
	}
}
