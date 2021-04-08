package com.fl.wdl.mapper;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fl.wdl.pojo.Comment;

@Mapper
public interface CommentMapper extends BaseMapper<Comment>{
	Boolean insertComment(Comment comment);
	CopyOnWriteArrayList<Comment> getLevelOneOrTwoListBySongOrListId(String id);
	List<Comment> getLevelThreeListByCommentId(String id);
	Comment selectById(String id);
	Boolean addThumbUp(String id);
	Boolean cancelThumbUp(String id);
	Boolean addReplyCount(String id);
	Boolean reduceReplyCount(String id);
	Boolean softDeleteById(String id);
}
