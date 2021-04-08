package com.fl.wdl.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongList implements Serializable{
	@TableId(type=IdType.ASSIGN_UUID)
	private String id;
	private String title;
	private String cover;
	private List<Song> songs = new ArrayList<Song>();
	private Integer commentCount = 0;
	private Integer songCount = 0;
	private Map<String,Object> metaObject = new HashMap<>();
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
	
	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof SongList))return false;
		return ((SongList)o).id.equals(id) || ((SongList)o).title.equals(title);
	}
}
