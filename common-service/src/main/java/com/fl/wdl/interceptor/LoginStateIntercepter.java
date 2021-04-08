package com.fl.wdl.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fl.wdl.constant.ConfigConst;
import com.fl.wdl.constant.ResponseStatus;
import com.fl.wdl.pojo.User;
import com.fl.wdl.vo.CommonResult;

public class LoginStateIntercepter implements HandlerInterceptor{
	@Autowired
	private RedisTemplate<String,User> redisTemplate;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader("token");
		if(token==null) {
			this.renderJson(response, CommonResult.buildError(ResponseStatus.ILLEGAL_ACCESS.code(), ResponseStatus.ILLEGAL_ACCESS.message()));
			return false;
		}
		
		User tokenValue = redisTemplate.opsForValue().get(token);
		
		
		//检测token是否为空
		if(tokenValue==null) {
			this.renderJson(response, CommonResult.buildError(ResponseStatus.LOGIN_RETRY.code(), ResponseStatus.LOGIN_RETRY.message()));
			return false;
		}
		//检测有效时间（>=0或为-1）
		Long ttl = redisTemplate.getExpire(token);
		if(ttl<-1) {
			this.renderJson(response, CommonResult.buildError(ResponseStatus.LOGIN_EXPIRE.code(), ResponseStatus.LOGIN_EXPIRE.message()));
			return false;
		}
		
		//未过期，仍处于登陆状态
		redisTemplate.expire(token, ConfigConst.REDIS_EXPIRE, TimeUnit.SECONDS);
		
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
	/**
	 * 将json数据转换成字符串打印给客户端
	 * @param response
	 * @param jsondata
	 */
	private void renderJson(HttpServletResponse response,CommonResult jsondata) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		
		try {
			PrintWriter writer = response.getWriter();
			ObjectMapper mapper=new ObjectMapper();
			writer.print(mapper.writeValueAsString(jsondata));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public LoginStateIntercepter() {
		super();
	}
	

}
