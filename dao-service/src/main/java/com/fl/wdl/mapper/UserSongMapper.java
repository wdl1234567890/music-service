package com.fl.wdl.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fl.wdl.pojo.UserSong;

@Mapper
public interface UserSongMapper extends BaseMapper<UserSong>{
	public List<UserSong> getListByUserId(Integer userId);
	public Integer getAllCountGroupUser();
}
