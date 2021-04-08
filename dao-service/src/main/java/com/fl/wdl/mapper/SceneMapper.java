package com.fl.wdl.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fl.wdl.pojo.Scene;
import com.fl.wdl.pojo.Style;

@Mapper
public interface SceneMapper extends BaseMapper<Scene>{
	public List<Scene> getScenesFromSong(String songId);
	public Boolean addSceneToSong(@Param("songId")String songId, @Param("sceneId")Integer sceneId);
	public Scene getSceneBySongListIdAndSceneId(@Param("songListId")String songListId, @Param("sceneId")Integer sceneId);
	public Boolean addSceneToSongList(@Param("songListId")String songListId, @Param("sceneId")Integer sceneId);
	public List<Scene> getScenesFromSongList(String songListId);
}
