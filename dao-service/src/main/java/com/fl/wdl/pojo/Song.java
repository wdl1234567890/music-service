package com.fl.wdl.pojo;

import java.io.Serializable;
import java.sql.Time;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Song implements Serializable{
	@TableId(type=IdType.ASSIGN_UUID)
	private String id;
	private String cover;
	private String songName;
	private String singerName;
	private Boolean isVip = false;
	private Time songTime;
	private Integer commentCount = 0;
	private String lyric;
	private String url;
	//{id=abc, cover=https://www.baidu.com, songName=芜湖, singerName=gj, isVip=false, songTime=10:50:00, commentCount=999, lyric=aabbcc, url=https://www.baidu.com}

	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof Song))return false;
		return ((Song)o).id.equals(id) || ((Song)o).url.equals(url);
	}

}
