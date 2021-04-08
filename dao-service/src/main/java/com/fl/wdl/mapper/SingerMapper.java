package com.fl.wdl.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fl.wdl.pojo.Singer;
import com.fl.wdl.pojo.SongList;

@Mapper
public interface SingerMapper extends BaseMapper<Singer>{
	public Boolean addSongList(@Param("songListId")String songListId,@Param("singerId")Integer singerId);
	public SongList getSongList(Integer id);
}
