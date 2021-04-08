package com.fl.wdl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fl.wdl.constant.ResponseStatus;
import com.fl.wdl.exception.FLException;
import com.fl.wdl.mapper.UserMapper;
import com.fl.wdl.pojo.Style;
import com.fl.wdl.pojo.User;
import com.fl.wdl.pojo.UserStyle;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private StyleService styleService;
	
	public User getUserById(Integer id){
		if(id < 1 || id.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		return userMapper.selectById(id);
	}
	
	public List<User> getList(){
		return userMapper.selectList(null);
	}
	
	public Boolean save(User user) {
		if(user==null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		int count = userMapper.insert(user);
		if(count < 1)return false;
		return true;
	}
	
	public Boolean update(User user) {
		if(user==null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		int count = userMapper.updateById(user);
		if(count < 1)return false;
		return true;
	}
	
	public Boolean upgradeToVip(Integer id) {
		if(id==null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		User user = new User();
		user.setId(id);
		user.setIsVip(true);
		int count = userMapper.updateById(user);
		if(count < 1)return false;
		return true;
	}
	
	public Boolean lowerToNormalUser(Integer id) {
		if(id==null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		User user = new User();
		user.setId(id);
		user.setIsVip(false);
		int count = userMapper.updateById(user);
		if(count < 1)return false;
		return true;
	}
	
	public Boolean addStyles(Integer id, List<Integer> styleIds) {
		if(id < 1 || id.equals("") || styleIds == null || styleIds.size() == 0)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		styleIds.stream().forEach(styleId->{
			UserStyle userStyle = new UserStyle();
			userStyle.setUserId(id);
			userStyle.setStyleId(styleId);
			if(!styleService.addStyleToUser(userStyle).getSuccess())throw new FLException(ResponseStatus.DATABASE_ERROR.code(),ResponseStatus.DATABASE_ERROR.message());
		});
		return true;
	}
	
	public Boolean removeStyles(Integer id, List<Integer> styleIds) {
		if(id.equals("") || id < 1 || styleIds == null || styleIds.size() == 0)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		styleIds.stream().forEach(styleId->{
			UserStyle userStyle = new UserStyle();
			userStyle.setUserId(id);
			userStyle.setStyleId(styleId);
			if(!styleService.removeStyleFromUser(userStyle).getSuccess())throw new FLException(ResponseStatus.DATABASE_ERROR.code(),ResponseStatus.DATABASE_ERROR.message());
		});
		return true;
	}
	
	public List<Style> getStyleList(Integer userId){
		if(userId == null || userId < 1)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		return (List<Style>) styleService.getStylesByUserId(userId).getData();
	}
	
}








