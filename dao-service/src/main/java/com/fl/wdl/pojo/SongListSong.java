package com.fl.wdl.pojo;

import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongListSong {
	@TableId(type=IdType.AUTO)
	private Integer id;
	private String songListId;
	private String songId;
	
	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof SongListSong))return false;
		return ((SongListSong)o).id.equals(id) || ((SongListSong)o).songId.equals(songId) && ((SongListSong)o).songListId.equals(songListId);
	}
}
