package com.fl.wdl.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongStyle {
	@TableId(type=IdType.AUTO)
	private Integer id;
	private String songId;
	private Integer styleId;
	
	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof SongStyle))return false;
		return ((SongStyle)o).id.equals(id) || ((SongStyle)o).songId.equals(songId) && ((SongStyle)o).styleId.equals(styleId);
	}
}
