package com.fl.wdl.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fl.wdl.validation.CommentLevel;
import com.fl.wdl.validation.FromCommentNotNull;
import com.fl.wdl.validation.Groups;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable{

	@TableId(type=IdType.ASSIGN_UUID)
	@NotNull(message="评论id不能为空", groups=Groups.Delete.class)
	@Null(message="评论id必须为空", groups=Groups.Add.class)
	private String id;
	
	@NotNull(message="评论内容不能为空", groups=Groups.Add.class)
	@Size(min=1,max=400, groups=Groups.Add.class)
	private String content;
	
	@NotNull(message="评论来源歌曲/歌单id不能为空", groups=Groups.Add.class)
	private String songOrListId;
	
	@NotNull(message="评论等级不能为空", groups=Groups.Delete.class)
	@CommentLevel(groups=Groups.Delete.class)
	private Integer level = 0;
	
	private Integer replyCount = 0;
	
	private User user;
	
	private Integer thumbUpCount = 0;
	
	@FromCommentNotNull(groups={Groups.Add.class, Groups.Delete.class})
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
