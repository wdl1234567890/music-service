package com.fl.wdl.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSong implements Serializable{
	@TableId(type=IdType.AUTO)
	private Integer id;
	private Integer userId;
	private String songId;
	private Integer play;
	private Integer share;
	private Integer download;
	private Integer collect;

	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof UserSong))return false;
		return ((UserSong)o).id.equals(id) || ((UserSong)o).userId.equals(userId) && ((UserSong)o).songId.equals(songId);
	}
	
	public void addCount(String key) {
		switch (key) {
		case "play":
			this.play++;
			break;
        case "share":
			this.share++;
			break;
        case "download":
        	this.download++;
        	break;
        case "collect":
        	this.collect++;
        	break;
		default:
			break;
		}
	}
	
	public void reduceCount(String key) {
		switch (key) {
		case "play":
			if(this.play > 0)this.play--;
			break;
        case "share":
        	if(this.share > 0)this.share--;
			break;
        case "download":
        	if(this.download > 0)this.download--;
        	break;
        case "collect":
        	if(this.collect > 0)this.collect--;
        	break;
		default:
			break;
		}
	}
}
