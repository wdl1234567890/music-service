package com.fl.wdl.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongScene {
	@TableId(type=IdType.AUTO)
	private Integer id;
	private String songId;
	private Integer sceneId;
	
	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof SongScene))return false;
		return ((SongScene)o).id.equals(id) || ((SongScene)o).songId.equals(songId) && ((SongScene)o).sceneId.equals(sceneId);
	}
}
