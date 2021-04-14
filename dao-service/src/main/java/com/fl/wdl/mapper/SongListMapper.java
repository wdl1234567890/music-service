package com.fl.wdl.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fl.wdl.pojo.Scene;
import com.fl.wdl.pojo.SongList;
import com.fl.wdl.pojo.Style;

@Mapper
public interface SongListMapper extends BaseMapper<SongList>{
	List<SongList> getSongListsByStyle(Integer styleId);
	List<SongList> getSongListsByScene(Integer sceneId);
	SongList getSongListById(String id);
	List<SongList> getSongListsByAllMatchTitle(String title);
	List<SongList> getSongListsByLikeTitle(String title);
	SongList getSongListBySingerId(Integer singerId);
	SongList getSongListByAllMatchTitle(String title);
    public Boolean addCommentCount(String id);	
	public Boolean reduceCommentCount(String id);
	public int updateById(SongList songList);
//	Boolean setStyle(@Param("songListId")String songListId,@Param("styleId")Integer styleId);
//	Boolean setScene(@Param("songListId")String songListId,@Param("sceneId")Integer sceneId);
	Boolean addSong(@Param("songListId")String songListId,@Param("songId")String songId);
	int insert(SongList songList);
	List<SongList> getSongListsOfTopSevenNew();
	//	List<Style> getStyles(String songListId);
//  List<Scene> getScenes(String songListId);
}
