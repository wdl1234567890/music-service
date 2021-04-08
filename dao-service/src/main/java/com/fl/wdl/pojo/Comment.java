package com.fl.wdl.pojo;

import java.io.Serializable;
import java.util.Date;

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
public class Comment implements Serializable{

	@TableId(type=IdType.ASSIGN_UUID)
	private String id;
	private String content;
	private String songOrListId;
	private Integer level = 0;
	private Integer replyCount = 0;
	private User user;
	private Integer thumbUpCount = 0;
	private Comment fromComment;
	
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
	
	public int addThumbUpCount() {
		this.thumbUpCount++;
		return this.thumbUpCount;
	}
	
	public int reduceThumbUpCount() {
		this.thumbUpCount--;
		return this.thumbUpCount;
	}
}
