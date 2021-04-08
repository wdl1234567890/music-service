package com.fl.wdl.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongListScene {
	
	@TableId(type=IdType.AUTO)
	private Integer id;
	private String songListId;
	private Integer sceneId;
	
	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof SongListStyle))return false;
		return ((SongListScene)o).id.equals(id) || ((SongListScene)o).songListId.equals(songListId) && ((SongListScene)o).sceneId.equals(sceneId);
	}

}