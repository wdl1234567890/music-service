package com.fl.wdl.constant;

/**
 * 
 * @ClassName ResponseStatus
 * @Description 返回状态信息
 * @author fuling
 * @date 2021年3月18日 上午2:18:29
 */
public enum ResponseStatus {
	
	SUCCESS(20000,"成功"),
	
	DEFAULT_ERROR(10000,"错误"),
	
	PARAM_VALIDATE_FAILED(30000,"参数错误"),
    
	CLIENT_ERROR(40000,"客户端错误"),
	
	ILLEGAL_ACCESS(40001,"非法访问"),
	
	LOGIN_RETRY(40002,"请重新登录"),
	
	LOGIN_EXPIRE(40003,"登录过期"),
	
	PARAM_IS_EMPTY(40004,"参数为空"),
	
	USER_IS_NOT_EXISTED(40005,"用户不存在"),
	
	SONG_IS_NOT_EXISTED(40006,"歌曲不存在"),
	
	SONG_LIST_IS_NOT_EXISTED(40007,"歌单不存在"),
	
	COMMENT_IS_NOT_EXISTED(40008,"评论不存在"),
	
	USER_SONG_NOT_EXISTED(40009,"用户对该歌曲还没有任何操作，不能减分"),
	
	SERVER_ERROR(50000,"服务端错误"),
	
	DATABASE_ERROR(50001,"数据库错误");
	
	private Integer code;
	
	private String message;
	
	ResponseStatus(Integer code, String message){
		this.code = code;
		this.message = message;
	}
	
	public Integer code() {
		return code;
	}
	
	public String message() {
		return message;
	}
}
