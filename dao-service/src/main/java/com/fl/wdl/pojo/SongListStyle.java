package com.fl.wdl.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongListStyle {
	@TableId(type=IdType.AUTO)
	private Integer id;
	private String songListId;
	private Integer styleId;
	
	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof SongListStyle))return false;
		return ((SongListStyle)o).id.equals(id) || ((SongListStyle)o).songListId.equals(songListId) && ((SongListStyle)o).styleId.equals(styleId);
	}

}
