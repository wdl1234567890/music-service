package com.fl.wdl.pojo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fl.wdl.validation.Groups;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongListScene {
	
	@TableId(type=IdType.AUTO)
	@Null(message="歌单场景id必须为空",groups=Groups.Add.class)
	private Integer id;
	
	@NotNull(message="歌单id不能为空",groups=Groups.Add.class)
	private String songListId;
	
	@NotNull(message="场景id不能为空",groups=Groups.Add.class)
	@Min(message="场景id必须为正整数",value=1,groups=Groups.Add.class)
	private Integer sceneId;
	
	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof SongListStyle))return false;
		return ((SongListScene)o).id.equals(id) || ((SongListScene)o).songListId.equals(songListId) && ((SongListScene)o).sceneId.equals(sceneId);
	}

}