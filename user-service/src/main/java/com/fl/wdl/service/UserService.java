package com.fl.wdl.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
	
	@Autowired
	private OSSService OSSService;
	
	public User getUserById(Integer id){
		if(id < 1 || id.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		return userMapper.selectById(id);
	}
	
	public List<User> getList(){
		return userMapper.selectList(null);
	}
	
	public Map<String,String> getPostAvatorOSSParam(){
		return OSSService.getOSSPostObjectParams();
	}
	
	public Integer save(User user) {
		if(user==null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("open_id", user.getOpenId());
		if(userMapper.selectOne(queryWrapper) != null)return 0;
		int count = userMapper.insert(user);
		if(count < 1)return -1;
		return 1;
	}
	
	public Boolean update(User user) {
		if(user==null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		String host = "https://" + OSSService.bucket + "." + OSSService.endpoint;
		user.setAvator(host + "/" + user.getAvator());
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
	
	public Boolean saveStyles(Integer id, List<Integer> styleIds) {
		if(id == null || id <= 0 || styleIds == null || styleIds.size() <= 0)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		List<Style> styles = JSONArray.parseArray(JSONArray.toJSONString(styleService.getStylesByUserId(id).getData()), Style.class);
	    List<Integer> addStyleIds = null;
	    List<Integer> removeStyleIds = null;
		if(styles == null || styles.size() <= 0) {
			addStyleIds = new LinkedList<>();
			addStyleIds.addAll(styleIds);
	    }else {
	    	addStyleIds = styleIds.stream().filter(styleId->{
	    		return !styles.contains(new Style(styleId, null));
	    	}).collect(Collectors.toList());
	    	removeStyleIds = styles.stream().filter(style->{
	    		return !styleIds.contains(style.getId());
	    	}).map(style->{
	    		return style.getId();
	    	}).collect(Collectors.toList());
	    	
	    }
		if(addStyleIds != null && addStyleIds.size() > 0) {
			List<UserStyle> addUserStyle = addStyleIds.stream().map(styleId->{
				return new UserStyle(null,id,styleId);
			}).collect(Collectors.toList());
			styleService.addStylesToUser(addUserStyle);
		}
        if(removeStyleIds != null && removeStyleIds.size() > 0) {
        	List<UserStyle> removeUserStyle = removeStyleIds.stream().map(styleId->{
				return new UserStyle(null,id,styleId);
			}).collect(Collectors.toList());
			styleService.addStylesToUser(removeUserStyle);
		}
		return true;
	}
	
	public List<Style> getStyleList(Integer userId){
		if(userId == null || userId < 1)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		return JSONArray.parseArray(JSONArray.toJSONString(styleService.getStylesByUserId(userId).getData()), Style.class);
	}
	
	public int getUserCount() {
		return userMapper.selectCount(null);
	}
	
}








