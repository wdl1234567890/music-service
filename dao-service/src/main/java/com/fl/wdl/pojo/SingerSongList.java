package com.fl.wdl.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingerSongList {
	@TableId(type=IdType.AUTO)
	private Integer id;
	private String songListId;
	private Integer singerId;
	
	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof SingerSongList))return false;
		return ((SingerSongList)o).id.equals(id) || ((SingerSongList)o).songListId.equals(songListId) && ((SingerSongList)o).singerId.equals(singerId);
	}

}