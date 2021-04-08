package com.fl.wdl.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fl.wdl.constant.PojoConst;
import com.fl.wdl.constant.ResponseStatus;
import com.fl.wdl.exception.FLException;
import com.fl.wdl.mapper.UserSongMapper;
import com.fl.wdl.pojo.Song;
import com.fl.wdl.pojo.Style;
import com.fl.wdl.pojo.User;
import com.fl.wdl.pojo.UserSong;

@Service
public class UserSongService {
	
	@Autowired
	UserSongMapper userSongMapper;
	
	@Autowired
	SongService songService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	StyleService styleService;
	
	@Autowired
	RedisTemplate<String, User> redisTemplate;
	
	public Boolean addActionCount(String key,String songId,User user) {
		if(songId == null || songId.equals("") || user == null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		QueryWrapper<UserSong> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("song_id", songId);
		queryWrapper.eq("user_id", user.getId());
		UserSong userSong = userSongMapper.selectOne(queryWrapper);
		if(userSong == null)userSongMapper.insert(new UserSong(null,user.getId(),songId,0,0,0,0));
		userSong.addCount(key);
		int result = userSongMapper.updateById(userSong);
		if(result < 1)throw new FLException(ResponseStatus.DATABASE_ERROR.code(),ResponseStatus.DATABASE_ERROR.message());
		return true;
		
	}
	
	public Boolean reduceActionCount(String key,String songId,User user) {
		if(songId == null || songId.equals("") || user == null)return false;
		QueryWrapper<UserSong> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("song_id", songId);
		queryWrapper.eq("user_id", user.getId());
		UserSong userSong = userSongMapper.selectOne(queryWrapper);
		if(userSong == null)throw new FLException(ResponseStatus.USER_SONG_NOT_EXISTED.code(),ResponseStatus.USER_SONG_NOT_EXISTED.message());
		userSong.reduceCount(key);
		int result = userSongMapper.updateById(userSong);
		if(result < 1)throw new FLException(ResponseStatus.DATABASE_ERROR.code(),ResponseStatus.DATABASE_ERROR.message());
		return true;
		
	}
	
	public UserSong getUserSongBySongIdAndUser(Integer userId,String songId) {
		if(userId == null || songId == null || userId.equals("") || songId.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		QueryWrapper<UserSong> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("song_id", songId);
		queryWrapper.eq("user_id", userId);
		return userSongMapper.selectOne(queryWrapper);
	}
	
	private int calculateSongScore(Integer userId,String songId) {
		UserSong userSong = this.getUserSongBySongIdAndUser(userId,songId);
		if(userSong == null)return 0;
		int score = 0;
		score += userSong.getPlay() * PojoConst.SONG_PLAY_WEIGHT;
		score += userSong.getShare() * PojoConst.SONG_SHARE_WEIGHT;
		score += userSong.getDownload() * PojoConst.SONG_DOWNLOAD_WEIGHT;
		score += userSong.getCollect() * PojoConst.SONG_COLLECT_WEIGHT;
		return score;
	}
	
	public List<Map<String,Object>> getTopFiveSongByUser(User user){
		Integer userId = user.getId();
		if(userId == null || userId.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		List<Map<String,Object>> topFiveList = new LinkedList<>();
		QueryWrapper<UserSong> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userId);
		List<UserSong> userSongList = userSongMapper.selectList(queryWrapper);
		
		if(userSongList == null || userSongList.size() == 0)return null;
		
		topFiveList = userSongList.stream().map(userSong->{
			int score = this.calculateSongScore(userId,userSong.getSongId());
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("score", score);
			map.put("song", userSong);
			return map;
		}).collect(Collectors.toList());
		
		topFiveList = topFiveList.stream().sorted((a,b)->{
			return (int)b.get("score") - (int)a.get("score");
		}).limit(5)
		  .map(e->{
			UserSong item = (UserSong)e.get("song");
			e.put("song", songService.getSongById(item.getSongId()));
			return e;
		})
		  .collect(Collectors.toList());
		
		return topFiveList;
	
	}
	
	private int getSimilarScore(List<Map<String,Object>> listData) {
		if(listData == null || listData.size() == 0)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		int score = 0;
		int length = listData.size();
		for(int i = 0; i < length; i++) {
			score += (length-i) * (int)(listData.get(i).get("score"));
		}
		return score;
	}

	public List<User> getTopTenSimilarUser(User user){
		Integer userId = user.getId();
		if(userId == null || userId.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		List<Map<String,Object>> topFiveList = getTopFiveSongByUser(user);
		List<User> users = (List<User>) userService.getList().getData();
		return users.stream().map(userItem->{
			List<Map<String,Object>> topFiveList1 = topFiveList.stream().map(item->{
				Song song = (Song)item.get("song");
				int score = this.calculateSongScore(userItem.getId(), song.getId());
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("score", score);
				map.put("song", song);
				return map;
			}).collect(Collectors.toList());
			int score = getSimilarScore(topFiveList1);
			Map<String,Object> map = new HashMap<>();
			map.put("score", score);
			map.put("user", userItem);
			return map;
		}).sorted((a,b)->{
			return (int)b.get("score") - (int)a.get("score");
		}).limit(10).map(item->(User)(item.get("user"))).collect(Collectors.toList());

	}

	public List<Song> getRecommendSongListOne(User user){
		Integer userId = user.getId();
		if(userId == null || userId.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		List<User> users = this.getTopTenSimilarUser(user);
		List<Song> songList = new LinkedList<>();
		users.stream().forEach(userItem->{
			if(songList.size() > 30)return;
			List<Map<String,Object>> topFiveSong = this.getTopFiveSongByUser(userItem);
			topFiveSong.stream().map(item->(Song)item.get("song")).forEach(song->{
				if(!songList.contains(song))songList.add(song);
			});
		});
		return songList;
	}
	
	public List<Song> getRecommendSongListTwo(User user){
		Integer userId = user.getId();
		if(userId == null || userId.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		List<Song> songList = new LinkedList<>();
		List<Style> styles = (List<Style>) styleService.getStylesByUserId(userId).getData();
		styles.forEach(style->{
			List<Song> songs = JSONArray.parseArray(JSONArray.toJSONString(songService.getListByStyle(style.getId()).getData()), Song.class);
			songs.forEach(song->{
				if(!songList.contains(song))songList.add(song);
			});
		});
		
		
		if(songList.size() < 30)return songList;
		
		List<Song> songs2 = new LinkedList<>();
		
		for(int i = 0; i < 30; i++) {
			int randomIndex = (int)Math.random()*songList.size();
			songs2.add(songList.get(randomIndex));
			songList.remove(randomIndex);		
		}
		return songs2;
		
		
	}
	
}
