package com.fl.wdl.pojo;

import java.util.List;

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
public class SongListSong {
	@TableId(type=IdType.AUTO)
	@Null(message="歌单歌曲id必须为空",groups=Groups.Add.class)
	private Integer id;
	
	@NotNull(message="歌单id不能为空",groups=Groups.Add.class)
	private String songListId;
	
	@NotNull(message="歌曲id不能为空",groups=Groups.Add.class)
	private String songId;
	
	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof SongListSong))return false;
		return ((SongListSong)o).id.equals(id) || ((SongListSong)o).songId.equals(songId) && ((SongListSong)o).songListId.equals(songListId);
	}
}
