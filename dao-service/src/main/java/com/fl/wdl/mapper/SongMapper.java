package com.fl.wdl.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fl.wdl.pojo.Scene;
import com.fl.wdl.pojo.Song;
import com.fl.wdl.pojo.Style;
import com.fl.wdl.pojo.User;

@Mapper
public interface SongMapper extends BaseMapper<Song>{
	public List<Song> getListByStyle(Integer id);
	
	public Boolean addCommentCount(String id);
	
	public Boolean reduceCommentCount(String id);
	
//	public Boolean setStyle(String songId, Integer styleId);
	
//	public Boolean setScene(String songId, Integer sceneId);
	
//	public List<Style> getStyles(String songId);
	
//	public List<Scene> getScenes(String songId);
}
