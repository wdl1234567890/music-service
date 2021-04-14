package com.fl.wdl.service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fl.wdl.constant.PojoConst;
import com.fl.wdl.constant.ResponseStatus;
import com.fl.wdl.exception.FLException;
import com.fl.wdl.mapper.CommentMapper;
import com.fl.wdl.pojo.Comment;
import com.fl.wdl.pojo.User;

@Service
public class CommentService {
	@Autowired
	CommentMapper commentMapper;
	
	@Autowired
	RedisTemplate<String, User> redisTemplate;
	
	@Autowired
	SongService songService;
	
	@Autowired
	SongListService songListService;
	
	public Boolean addComment(Comment comment,User user) {
		if(comment==null || user == null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		String fromId = comment.getFromComment().getId();
		String songOrListId = comment.getSongOrListId();
		//一级评论
		if(fromId.equals(songOrListId)) {
			comment.setLevel(PojoConst.COMMENT_LEVEL_ONE);
			
		}else {
			Comment fromComment = commentMapper.selectById(fromId);
			//二级评论
			if(fromComment.getLevel().equals(PojoConst.COMMENT_LEVEL_ONE)) {
				comment.setLevel(PojoConst.COMMENT_LEVEL_TWO);
			}
			
			//三级评论
	        if(fromComment.getLevel().equals(PojoConst.COMMENT_LEVEL_TWO)) {
	        	comment.setLevel(PojoConst.COMMENT_LEVEL_THREE);
			}
	        
	        this.addReplyCount(fromId);
		}
		
		if(!songService.addCommentCount(songOrListId).getSuccess()) {
			songListService.addCommentCount(songOrListId);
		}
		
		comment.setId(UUID.randomUUID().toString());
        comment.setUser(user);
        comment.setReplyCount(0);
        comment.setThumbUpCount(0);
        
        Boolean result = commentMapper.insertComment(comment);
        
        if(!result)return false;
        
		return true;
	}
	
	public Boolean removeComment(Comment comment,User user) {
		String fromId = comment.getFromComment().getId();
		Integer level = comment.getLevel();
		String id = comment.getId();
		Comment comment1 = commentMapper.selectById(id);
		if(comment1 == null)return false;
		Boolean result = commentMapper.softDeleteById(id);
        if(!result)return false;
        if(level != 0) {
        	this.reduceReplyCount(fromId);
        } 
        if(!songService.reduceCommentCount(fromId).getSuccess()) {
			songListService.reduceCommentCount(fromId);
		}
		return true;
	}

	public int thumbUpComment(String id) {
		if(id == null || id.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		Comment comment = commentMapper.selectById(id);
		if(comment == null)throw new FLException(ResponseStatus.COMMENT_IS_NOT_EXISTED.code(),ResponseStatus.COMMENT_IS_NOT_EXISTED.message());;
		commentMapper.addThumbUp(id);
		return comment.getThumbUpCount() + 1;
	}
	
	public int cancelThumbUpComment(String id) {
		if(id == null || id.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		Comment comment = commentMapper.selectById(id);
		if(comment == null)throw new FLException(ResponseStatus.COMMENT_IS_NOT_EXISTED.code(),ResponseStatus.COMMENT_IS_NOT_EXISTED.message());
		int thumbCount = comment.getThumbUpCount();
		if(thumbCount == 0)return -1;
		commentMapper.cancelThumbUp(id);
		return thumbCount - 1;
	}
	
	public List<Comment> getLevelOneCommentsBySongOrListId(String songOrListId){
		if(songOrListId == null || songOrListId.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		return commentMapper.getLevelOneOrTwoListBySongOrListId(songOrListId);
	}
	
	public CopyOnWriteArrayList<Comment> getLevelTwoCommentsByCommentId(String id){
		if(id == null || id.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		return commentMapper.getLevelOneOrTwoListBySongOrListId(id);
	}
	
	public List<Comment> getLevelThreeCommentsByCommentId(String id){
		if(id == null || id.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		return commentMapper.getLevelThreeListByCommentId(id);
	}
	
	public Boolean addReplyCount(String id) {
		return commentMapper.addReplyCount(id);
	}
	
	public Boolean reduceReplyCount(String id) {
		Comment comment = commentMapper.selectById(id);
		if(comment.getReplyCount() < 1)return false;
		return commentMapper.reduceReplyCount(id);
	}
}
