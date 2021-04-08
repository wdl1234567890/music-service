package com.fl.wdl.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.fl.wdl.pojo.Style;

@Mapper
public interface StyleMapper extends BaseMapper<Style>{
	public List<Style> getStylesByUserId(Integer userId);
    public Boolean addStyleToUser(@Param("userId")Integer userId, @Param("styleId")Integer styleId);
	public Boolean removeStyleFromUser(@Param("userId")Integer userId,@Param("styleId")Integer styleId);
	public Style getUserStyleByUserId(@Param("userId")Integer userId, @Param("styleId")Integer styleId);
	public List<Style> getStylesFromSong(String songId);
	public Boolean addStyleToSong(@Param("songId")String songId, @Param("styleId")Integer styleId);
	public Style getStyleBySongIdAndStyleId(@Param("songId")String songId, @Param("styleId")Integer styleId);
	public List<Style> getStylesFromSongList(String songListId);
	public Style getStyleBySongListIdAndStyleId(@Param("songListId")String songListId, @Param("styleId")Integer styleId);
	public Boolean addStyleToSongList(@Param("songListId")String songListId, @Param("styleId")Integer styleId);
}
