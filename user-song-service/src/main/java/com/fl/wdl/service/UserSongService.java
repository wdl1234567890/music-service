package com.fl.wdl.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
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
import com.fl.wdl.pojo.Singer;
import com.fl.wdl.pojo.Song;
import com.fl.wdl.pojo.SongList;
import com.fl.wdl.pojo.SongListSong;
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
	SongListService songListService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	StyleService styleService;
	
	@Autowired
	SingerService singerService;
	
	@Autowired
	RedisTemplate<String, User> redisTemplate;
	
	public int getAllCountGroupUser() {
		return userSongMapper.getAllCountGroupUser();
	}
	
	public int getUserSongCountByUser(User user) {
		if(user == null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		List<UserSong> userSongs = userSongMapper.getListByUserId(user.getId());
		if(userSongs == null)return 0;
		return userSongs.size();
	}
	
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
	
	private BigDecimal calculateSongScore(UserSong userSong) {
//		UserSong userSong = this.getUserSongBySongIdAndUser(userId,songId);
		if(userSong == null)return new BigDecimal("0");
		double score = 0;
		score += userSong.getPlay() * PojoConst.SONG_PLAY_WEIGHT;
		score += userSong.getShare() * PojoConst.SONG_SHARE_WEIGHT;
		score += userSong.getDownload() * PojoConst.SONG_DOWNLOAD_WEIGHT;
		score += userSong.getCollect() * PojoConst.SONG_COLLECT_WEIGHT;
		BigDecimal BigDecimal = new BigDecimal(String.valueOf(score));
		return BigDecimal;
	}
	
	
//	private int getSimilarScore(List<Map<String,Object>> listData) {
//		if(listData == null || listData.size() == 0)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
//		int score = 0;
//		int length = listData.size();
//		for(int i = 0; i < length; i++) {
//			score += (length-i) * (int)(listData.get(i).get("score"));
//		}
//		return score;
//	}

	
	public List<Song> getRecommendSongListOne(User user){
//		Integer userId = user.getId();
//		if(userId == null || userId.equals(""))throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
//		List<User> users = this.getTopTenSimilarUser(user);
//		List<Song> songList = new LinkedList<>();
//		users.stream().forEach(userItem->{
//			if(songList.size() > 30)return;
//			List<Map<String,Object>> topFiveSong = this.getTopFiveSongByUser(userItem);
//			topFiveSong.stream().map(item->(Song)item.get("song")).forEach(song->{
//				if(!songList.contains(song))songList.add(song);
//			});
//		});
//		return songList;
		return null;
	}
	
	public List<Song> getRecommendSongListTwo(User user){
		return null;
		
		
	}
	
	public BigDecimal hotScore(Song song) {
		if(song == null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		QueryWrapper<UserSong> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("song_id", song.getId());
		List<UserSong> userSongs = userSongMapper.selectList(queryWrapper);
		int userCount = (int)userService.getUserCount().getData();
		if(userCount <= 0)return new BigDecimal("0");
		BigDecimal hotScore = new BigDecimal("0");
		userSongs.forEach(userSong->{
			hotScore.add(this.calculateSongScore(userSong));
			
		});
		return hotScore.divide(new BigDecimal(String.valueOf(userCount)));
	}
	
	public SongList getHotSongListInfo(){
		SongList songList = (SongList)songListService.getHotSongList().getData();
		return songList;
	}
	
	public SongList getNewSongListInfo(){
		SongList songList = (SongList)songListService.getNewSongList().getData();
		return songList;
	}
	
	public SongList getRandomSongListInfo(){
		SongList songList = (SongList)songListService.getRandomSongList().getData();
		return songList;
	}
	
	public List<SongList> getSongListByStyle(int styleId){
		return JSONArray.parseArray(JSONArray.toJSONString(songListService.getSongListsByStyle(styleId).getData()), SongList.class);
	}
	
	public List<SongList> getSongListByScene(int sceneId){
		return JSONArray.parseArray(JSONArray.toJSONString(songListService.getSongListsByScene(sceneId).getData()), SongList.class);
	}
	
	public SongList getSongListBySinger(int singerId){
		return (SongList)songListService.getSongListBySingerId(singerId).getData();
	}
	
	
	private BigDecimal avgScoreByUser(Integer userId) {
		if(userId == null || userId <= 0)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		List<UserSong> userSongs = userSongMapper.getListByUserId(userId);
		if(userSongs == null || userSongs.size() <= 0)return new BigDecimal("0");
		int songCount = (int)songService.getSongCount().getData();
		if(songCount <= 0)return new BigDecimal("0");
		BigDecimal avgScore = new BigDecimal("0");
		userSongs.forEach(userSong->{
			avgScore.add(this.calculateSongScore(userSong));
		});
		return avgScore.divide(new BigDecimal(String.valueOf(songCount)));
	}
	
	private BigDecimal getScoreByUserAndSong(List<UserSong> userSongs, String songId, Integer userId) {
		if(userSongs == null || userSongs.size() <= 0 || songId == null || songId.equals("") || userId == null || userId <= 0)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		Optional<UserSong> userSongOptional = userSongs.stream().filter(userSong->{
			return userSong.getSongId().equals(songId) && userSong.getUserId().equals(userId);
		}).findFirst();
		if(userSongOptional.isPresent()) {
			UserSong userSong = userSongOptional.get();
			return this.calculateSongScore(userSong);
		}
		return new BigDecimal("0");
	}
	
	public List<User> get50SimilarUser(User user){
		if(user == null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		BigDecimal AAvgScore = this.avgScoreByUser(user.getId());
		if(AAvgScore.compareTo(new BigDecimal("0")) == -1)return null;
		List<Map<String, Object>> userScores = new LinkedList<>();
		List<UserSong> userSongsA = userSongMapper.getListByUserId(user.getId());
		if(userSongsA == null || userSongsA.size() <= 0)return null;
		List<User> users = JSONArray.parseArray(JSONArray.toJSONString(userService.getList().getData()), User.class);
		if(users == null || users.size() <= 0)return null;
		users.forEach(u->{
			BigDecimal onePart = new BigDecimal("0");
			BigDecimal twoPart = new BigDecimal("0");
			BigDecimal threePart = new BigDecimal("0");
			BigDecimal BAvgScore = this.avgScoreByUser(u.getId());
			List<UserSong> userSongsB = userSongMapper.getListByUserId(user.getId());
			if(userSongsB == null || userSongsB.size() <= 0)return;
			List<Song> songs = JSONArray.parseArray(JSONArray.toJSONString(songService.getList().getData()), Song.class);
			songs.forEach(song->{
				BigDecimal userSongScoreA = this.getScoreByUserAndSong(userSongsA, song.getId(), user.getId());
				BigDecimal userSongScoreB = this.getScoreByUserAndSong(userSongsB, song.getId(), u.getId());
				BigDecimal hotScore = this.hotScore(song);
				BigDecimal onePartTemp = new BigDecimal("1").divide(hotScore).multiply(userSongScoreA.subtract(AAvgScore)).multiply(userSongScoreB.subtract(BAvgScore));
				onePart.add(onePartTemp);
				BigDecimal twoPartTemp = userSongScoreA.subtract(AAvgScore).pow(2);
				twoPart.add(twoPartTemp);
				BigDecimal threePartTemp = userSongScoreB.subtract(BAvgScore).pow(2);
				twoPart.add(threePartTemp);
			});
			BigDecimal simA_B = onePart.divide(new BigDecimal(String.valueOf(Math.sqrt(twoPart.doubleValue()) * Math.sqrt(threePart.doubleValue()))));
			Map<String, Object> userScoreMap = new HashMap<>();
			userScoreMap.put("user", u);
			userScoreMap.put("similarScore", simA_B);
			userScores.add(userScoreMap);
		});
		if(userScores.size() <= 0)return null;
		return userScores.stream().sorted((userScoreA,userScoreB)->{
			return ((BigDecimal)userScoreB.get("similarScore")).compareTo((BigDecimal)userScoreA.get("similarScore"));
		}).limit(50).map(userScore->{
			return (User)userScore.get("user");
		}).collect(Collectors.toList());
	}
	
	public List<User> filterSimilarUser(List<User> similarUsers, User user){
		if(similarUsers == null || similarUsers.size() <= 0 || user == null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		List<Style> userStylesA = JSONArray.parseArray(JSONArray.toJSONString(userService.getStyleList(user.getId()).getData()), Style.class);
		List<Style> allStyles = JSONArray.parseArray(JSONArray.toJSONString(styleService.getList().getData()), Style.class);
		List<Map<String,Object>> userScores = new LinkedList<>();
		similarUsers.stream().forEach(u->{
			BigDecimal allScore = new BigDecimal("0");
			List<Style> userStylesB = JSONArray.parseArray(JSONArray.toJSONString(userService.getStyleList(u.getId()).getData()), Style.class);
			allStyles.forEach(style->{
				int scoreA = userStylesA.contains(style)?1:0;
				int scoreB = userStylesB.contains(style)?1:0;
				allScore.add((new BigDecimal(String.valueOf(scoreA - scoreB))).pow(2));
			});
			BigDecimal score = new BigDecimal("1").subtract(allScore.divide(new BigDecimal(String.valueOf(allStyles.size()))));
			Map<String,Object> userScore = new HashMap<>();
			userScore.put("user", u);
			userScore.put("score", score);
			userScores.add(userScore);
		});
		if(userScores.size() <= 0)return null;
		return userScores.stream().sorted((userScoreA,userScoreB)->{
			return ((BigDecimal)userScoreB.get("score")).compareTo((BigDecimal)userScoreA.get("score"));
		}).limit(10).map(userScore->{
			return (User)userScore.get("user");
		}).collect(Collectors.toList());
		
	}
	
	private List<Song> getTopFourSongsByUser(User user){
		if(user == null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		List<UserSong> userSongs = userSongMapper.getListByUserId(user.getId());
		if(userSongs == null || userSongs.size() <= 0)return null;
		List<Map<String, Object>> songScores = new LinkedList<>();
		userSongs.forEach(userSong->{
			BigDecimal score = this.calculateSongScore(userSong);
			Map<String, Object> songScore = new HashMap<>();
			songScore.put("songId", userSong.getSongId());
			songScore.put("score", score);
			songScores.add(songScore);
		});
		if(songScores.size() <= 0)return null;
		return songScores.stream().sorted((songScoreA, songScoreB)->{
			return ((BigDecimal)songScoreB.get("score")).compareTo((BigDecimal)songScoreA.get("score"));
		}).limit(4).map(songScore->{
			return (Song)songService.getSongById((String)songScore.get("songId")).getData();
		}).collect(Collectors.toList());
	}
	
	public List<Song> getPersonalRecommendSongList(List<User> users){
		if(users == null || users.size() <= 0)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		List<Song> songs = new LinkedList<>();
		users.forEach(user->{
			songs.addAll(this.getTopFourSongsByUser(user));
		});
		if(songs.size() <= 0)return null;
		return songs.stream().distinct().collect(Collectors.toList());
	}
	
	public List<Song> getBaseContentRecommendSongList(User user){
		if(user == null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		List<Style> allStyles = JSONArray.parseArray(JSONArray.toJSONString(styleService.getList().getData()), Style.class);
		List<Style> userStyles = JSONArray.parseArray(JSONArray.toJSONString(userService.getStyleList(user.getId()).getData()), Style.class);
		List<Song> songs = JSONArray.parseArray(JSONArray.toJSONString(songService.getList().getData()), Song.class);
		List<Map<String, Object>> songScores = new LinkedList<>();
		songs.forEach(song->{
			BigDecimal songScore = new BigDecimal("0");
			List<Style> songStyles = JSONArray.parseArray(JSONArray.toJSONString(songService.getStyles(song.getId()).getData()), Style.class);
			if(songStyles == null || songStyles.size() <= 0)return;
			allStyles.forEach(style->{
				int scoreA = userStyles.contains(style)?1:0;
				int scoreB = songStyles.contains(style)?1:0;
				songScore.add((new BigDecimal(String.valueOf(scoreA - scoreB))).pow(2));
			});
			BigDecimal score = new BigDecimal("1").subtract(songScore.divide(new BigDecimal(String.valueOf(allStyles.size()))));
			Map<String, Object> item = new HashMap<>();
			item.put("song", song);
			item.put("score", score);
			songScores.add(item);
		});
		if(songScores.size() <= 0)return null;
		return songScores.stream().sorted((songScoreA, songScoreB)->{
			return ((BigDecimal)songScoreB.get("score")).compareTo((BigDecimal)songScoreA.get("score"));
		}).limit(40).map(songScoreItem->{
			return (Song)songScoreItem.get("song");
		}).collect(Collectors.toList());
	}
	
	public List<Song> getPersonalRecommend(User user) {
		if(user == null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
	    user.setId(1);
		if(this.getUserSongCountByUser(user) < 100 || this.getAllCountGroupUser() < 100) {
			List<User> users = this.get50SimilarUser(user);
			if(users != null && users.size() > 0) {
				users = this.filterSimilarUser(users, user);
				if(users != null && users.size() > 0) {
					List<Song> songs = this.getPersonalRecommendSongList(users);
					return songs;
				}
			}
		}
		List<Song> songs = this.getBaseContentRecommendSongList(user);
		if(songs != null && songs.size() > 0) {
			SongList songList = new SongList();
			songList.setTitle(user.getUserName() + "个人推荐歌单" + UUID.randomUUID().toString());
			songList.setCover(songs.get(0).getCover());
			songList.setSongCount(songs.size());
			songList.setCreateTime(new Date());
			String songListId = (String)songListService.addSongList(songList).getData();
			List<SongListSong> songListSongs = songs.stream().map(song->new SongListSong(null,songListId,song.getId())).collect(Collectors.toList());
			songListService.addSongs(songListSongs);
		}
		return songs;
	}
	
	public SongList getPersonSongListTodayForUser(User user) {
		if(user == null)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		List<SongList> songLists = JSONArray.parseArray(JSONArray.toJSONString(songListService.getSongListsByTitleLike(user.getUserName() + "个人推荐歌单").getData()), SongList.class);
		if(songLists == null || songLists.size() <= 0) {
			return null;
		}else {
			songLists = songLists.stream().sorted((songListA,songListB)->(int)(songListB.getCreateTime().getTime() - songListA.getCreateTime().getTime())).collect(Collectors.toList());
			SongList songList = songLists.get(0);
			Date createDate = songList.getCreateTime();
			Date today = new Date();
			if(createDate.getYear() == today.getYear() && createDate.getMonth() == today.getMonth() && createDate.getDate() == today.getDate()) {
				return (SongList)songListService.getSongListById(songList.getId()).getData();
			}
		}
		
		return null;
	}
	
	public List<SongList> getBanner(){
		return JSONArray.parseArray(JSONArray.toJSONString(songListService.getSongListsOfTopSevenNew().getData()), SongList.class);
	}
	
	public Set<Song> getSongsByLikedSinger(Integer userId){
		if(userId == null || userId <= 0)throw new FLException(ResponseStatus.PARAM_IS_EMPTY.code(),ResponseStatus.PARAM_IS_EMPTY.message());
		QueryWrapper<UserSong> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userId);
		List<UserSong> userSongs = userSongMapper.selectList(queryWrapper);
		if(userSongs == null || userSongs.size() <= 0)return null;
		Map<String, Integer> singerScores = new HashMap<>();
		List<String> songIds = userSongs.stream().map(userSong->userSong.getSongId()).collect(Collectors.toList());
		List<Song> songsByIds = JSONArray.parseArray(JSONArray.toJSONString(songService.getSongsByIds(songIds).getData()), Song.class);
		songsByIds.forEach(song->{
			singerScores.put(song.getSingerName(), singerScores.getOrDefault(song.getSingerName(),0)+1);
		});
		List<String> singerNames = singerScores.entrySet().stream().sorted((entryA,entryB)->entryB.getValue() - entryA.getValue()).limit(10).map(entry->entry.getKey()).collect(Collectors.toList());
		List<Song> songsBySingerNames = JSONArray.parseArray(JSONArray.toJSONString(songService.getSongsBySingerNames(singerNames).getData()), Song.class);
		Set<Song> resultSongs = new HashSet<>();
		if(songsBySingerNames != null && songsBySingerNames.size() > 0) {
			Collections.shuffle(songsBySingerNames);
			resultSongs.addAll(songsBySingerNames.stream().limit(10).collect(Collectors.toList()));
		}
		List<Singer> singers= JSONArray.parseArray(JSONArray.toJSONString(singerService.getSingersBySingerNames(singerNames).getData()), Singer.class);
		if(singers != null && singers.size() > 0)	{
			List<Integer> singerIds = singers.stream().limit(10).map(singer->singer.getId()).collect(Collectors.toList());
			List<SongList> songLists= JSONArray.parseArray(JSONArray.toJSONString(songListService.getSongListsBySingerIds(singerIds).getData()), SongList.class);
			if(songLists != null && songLists.size() > 0) {
				songLists.forEach(songList->{
					List<Song> songs = songList.getSongs();
					Collections.shuffle(songs);
					resultSongs.addAll(songs.stream().limit(3).collect(Collectors.toList()));
				});
			}
		}
		return resultSongs;
	}
}
