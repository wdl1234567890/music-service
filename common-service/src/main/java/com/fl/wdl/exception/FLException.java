package com.fl.wdl.exception;

/**
 * 
 * @ClassName FLException
 * @Description 项目通用自定义异常
 * @author fuling
 * @date 2021年3月18日 上午2:18:06
 */
public class FLException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	private Integer code;
	private String message;

	public FLException(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
