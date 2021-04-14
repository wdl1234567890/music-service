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
public class SongStyle {
	@TableId(type=IdType.AUTO)
	@Null(message="歌曲风格id必须为空",groups=Groups.Add.class)
	private Integer id;
	
	@NotNull(message="歌曲id不能为空",groups=Groups.Add.class)
	private String songId;
	
	@NotNull(message="风格id不能为空",groups=Groups.Add.class)
	@Min(message="风格id必须为正整数",value=1,groups=Groups.Add.class)
	private Integer styleId;
	
	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof SongStyle))return false;
		return ((SongStyle)o).id.equals(id) || ((SongStyle)o).songId.equals(songId) && ((SongStyle)o).styleId.equals(styleId);
	}
}
