package com.fl.wdl.controller;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fl.wdl.pojo.Comment;
import com.fl.wdl.pojo.User;
import com.fl.wdl.service.CommentService;
import com.fl.wdl.vo.CommonResult;

@RestController
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	CommentService commentService;
	
	@PostMapping
	public CommonResult addComment(@RequestHeader("token") String token, @RequestBody Comment comment) {
		//User user = UserUtils.getCurrentUser(token);
		User user = new User();
		user.setId(1);
		Boolean result = commentService.addComment(comment, user);
		if(result)return CommonResult.buildSuccess(null);
		else return CommonResult.buildError();
	}
	
	@DeleteMapping
	public CommonResult removeComment(@RequestHeader("token") String token,@RequestBody Comment comment) {
		//User user = UserUtils.getCurrentUser(token);
		User user = new User();
		user.setId(1);
		Boolean result = commentService.removeComment(comment, user);
		if(result)return CommonResult.buildSuccess(null);
		else return CommonResult.buildError();
	}

	@PutMapping("/thumb/{id}")
	public CommonResult thumbUpComment(@PathVariable("id")String id) {
		int result = commentService.thumbUpComment(id);
		if(result > 0)return CommonResult.buildSuccess(null);
		else return CommonResult.buildError();
	}
	
	@PutMapping("/cancelThumb/{id}")
	public CommonResult cancelThumbUpComment(@PathVariable("id")String id) {
		int result = commentService.cancelThumbUpComment(id);
		if(result > -1)return CommonResult.buildSuccess(null);
		else return CommonResult.buildError();
	}
	
	@GetMapping("/songOrList/{songOrListId}")
	public CommonResult getLevelOneCommentsBySongOrListId(@PathVariable("songOrListId")String songOrListId){
		List<Comment> comments = commentService.getLevelOneCommentsBySongOrListId(songOrListId);
		return CommonResult.buildSuccess(comments);
	}
	
	@GetMapping("/comment/{id}")
	public CommonResult getLevelTwoCommentsByCommentId(@PathVariable("id")String id){
		CopyOnWriteArrayList<Comment> comments =  commentService.getLevelTwoCommentsByCommentId(id);
		if(comments != null && comments.size() != 0) {
			comments.stream().forEach(comment->{
				List<Comment> threeComments = commentService.getLevelThreeCommentsByCommentId(comment.getId());
				comments.addAll(threeComments);
			});
		}
		comments.stream().sorted((commentA,commentB)->{
			return (int)(commentB.getCreateTime().getTime() - commentA.getCreateTime().getTime());
		});
		return CommonResult.buildSuccess(comments);
	}
}
