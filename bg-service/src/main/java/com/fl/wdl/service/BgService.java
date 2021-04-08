package com.fl.wdl.service;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fl.wdl.constant.RequestVar;
import com.fl.wdl.pojo.Scene;
import com.fl.wdl.pojo.Singer;
import com.fl.wdl.pojo.Song;
import com.fl.wdl.pojo.SongList;
import com.fl.wdl.pojo.SongListScene;
import com.fl.wdl.pojo.SingerSongList;
import com.fl.wdl.pojo.SongListSong;
import com.fl.wdl.pojo.SongListStyle;
import com.fl.wdl.pojo.SongScene;
import com.fl.wdl.pojo.SongStyle;
import com.fl.wdl.pojo.Style;
import com.fl.wdl.utils.DateUtils;


@Service
public class BgService {
	

	
	@Value("${songlist.category.url}")
	String categoryUrl;
	
	@Value("${songlist.bycategory.url}")
	String byCategoryListUrl;
	
	@Value("${songlist.detail.url}")
	String songListDetailUrl;
	
	@Value("${singer.url}")
	String singerUrl;
	
	@Value("${singer.song.url}")
	String singerSongUrl;
	
	@Value("${song.detail.url}")
	String songDetailUrl;
	
	@Value("${new.recommend.url}")
	String newRecommendUrl;
	
	@Value("${hot.recommend.url}")
	String hotRecommendUrl;
	
	@Autowired
	StyleService styleService;
	
	@Autowired
	SceneService sceneService;
	
	@Autowired
	SongService songService;
	
	@Autowired
	SongListService songListService;
	
	@Autowired
	SingerService singerService;
	
	public JSONObject getRequest(String url) {
		
		ResponseEntity<String> exchange = null;
		do{
			try {
				RestTemplate restTemplate = new RestTemplate();
				
				while(RequestVar.ip == null || RequestVar.port == null) {
					String[] ipaddr = restTemplate.getForObject(RequestVar.ipsUrl, String.class).split(":");
					if(ipaddr.length == 2) {
						RequestVar.ip = ipaddr[0];
						RequestVar.port = Integer.parseInt(ipaddr[1]);
					}
				}
				SimpleClientHttpRequestFactory reqfac = new SimpleClientHttpRequestFactory();
				reqfac.setConnectTimeout(3000);
				reqfac.setReadTimeout(4000);
				reqfac.setProxy(new Proxy(Type.HTTP, new InetSocketAddress(RequestVar.ip, RequestVar.port)));
				restTemplate.setRequestFactory(reqfac);
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.add("referer", "https://y.qq.com");
				httpHeaders.add("origin", "https://y.qq.com");
				httpHeaders.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.66 Safari/537.36");
				exchange = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(httpHeaders), String.class);
				if(exchange.getStatusCode()!=HttpStatus.OK) {
					RequestVar.ip = null;
					RequestVar.port = null;
				}
			}catch(Exception e) {
				RequestVar.ip = null;
				RequestVar.port = null;
			}
		}while(RequestVar.ip == null || RequestVar.port == null);
		return JSONObject.parseObject(exchange.getBody());
	}
	
	public Boolean setStyleAndScene() {
	
		JSONObject jsonObject = this.getRequest(categoryUrl);
		JSONArray categories = jsonObject.getJSONObject("data").getJSONArray("categories");
		JSONArray language = categories.getJSONObject(1).getJSONArray("items");
		JSONArray genre = categories.getJSONObject(2).getJSONArray("items");
		JSONArray theme = categories.getJSONObject(3).getJSONArray("items");
		JSONArray mood = categories.getJSONObject(4).getJSONArray("items");
	
		JSONArray scene = categories.getJSONObject(5).getJSONArray("items");
		
		int languageSize = language.size();
		int genreSize = genre.size();
		int themeSize = theme.size();
		int moodSize = mood.size();
		
		int sceneSize = scene.size();
		
		for(int i = 0; i < languageSize; i++) {
			JSONObject item = language.getJSONObject(i);
			Integer categoryId = item.getInteger("categoryId");
			String categoryName = item.getString("categoryName");
			styleService.addStyle(new Style(categoryId,categoryName));
		}
		
		for(int i = 0; i < moodSize; i++) {
			JSONObject item = mood.getJSONObject(i);
			Integer categoryId = item.getInteger("categoryId");
			String categoryName = item.getString("categoryName");
			styleService.addStyle(new Style(categoryId,categoryName));
		}
		
		for(int i = 0; i < themeSize; i++) {
			JSONObject item = theme.getJSONObject(i);
			Integer categoryId = item.getInteger("categoryId");
			String categoryName = item.getString("categoryName");
			styleService.addStyle(new Style(categoryId,categoryName));
		}
		
		for(int i = 0; i < genreSize; i++) {
			JSONObject item = genre.getJSONObject(i);
			Integer categoryId = item.getInteger("categoryId");
			String categoryName = item.getString("categoryName");
			styleService.addStyle(new Style(categoryId,categoryName));
		}
		
		
		
		for(int i = 0; i < sceneSize; i++) {
			JSONObject item = scene.getJSONObject(i);
			Integer categoryId = item.getInteger("categoryId");
			String categoryName = item.getString("categoryName");
			sceneService.addScene(new Scene(categoryId,categoryName));
		}
		return true;
	}
	
	
	public Boolean setSongsAndSongLists() {
//		List<Style> styles = JSONArray.parseArray(JSONArray.toJSONString(styleService.getList().getData()), Style.class);
//		Map<String,Integer> mapp = new HashMap<>();
//		mapp.put("count", -1);
//		styles.stream().forEach(style->{
//			mapp.put("count", mapp.get("count") + 1);
//			if(mapp.get("count") < 43)return;
//			
//			JSONObject jsonObject = this.getRequest(String.format(byCategoryListUrl, style.getId()));
//			JSONArray songLists = jsonObject.getJSONObject("data").getJSONArray("list");
//			int size = songLists.size();
//			for(int i = 0; i < size; i++) {
//				JSONObject songList = songLists.getJSONObject(i);
//				String songListId = songList.getString("dissid");
//				String listCover = songList.getString("imgurl");
//				String listName = songList.getString("dissname");
//				JSONObject jsonObject1 = this.getRequest(String.format(songListDetailUrl, songListId));
//				Integer songNum = jsonObject1.getInteger("songnum");
//				
//				SongList newSongList = new SongList();
//				newSongList.setId(songListId);
//				newSongList.setTitle(listName);
//				newSongList.setCover(listCover);
//				newSongList.setSongCount(songNum);
//				songListService.addSongList(newSongList);
//				
//				JSONArray songs = jsonObject1.getJSONArray("Body");
//				int size1 = songs.size();
//				for(int j = 0; j < size1; j++) {
//					JSONObject song = songs.getJSONObject(j);
//					String singerName = song.getString("author");
//					String cover = song.getString("pic");
//					String songName = song.getString("title");
//					String url = song.getString("url");
//					String lyricId = song.getString("mid");
//					
//					Time songTime = DateUtils.getTimeInMillis(song.getString("lrc"));
//					Boolean isVip = Math.round(Math.random()*20) < 6;
//					Song newSong = new Song(null,cover,songName,singerName,isVip,songTime,0,lyricId,url);
//					String songId = (String)songService.addSong(newSong).getData();
//					SongStyle songStyle = new SongStyle();
//					songStyle.setSongId(songId);
//					songStyle.setStyleId(style.getId());
//					songService.addStyle(songStyle);
//					SongListSong songListSong = new SongListSong();
//					songListSong.setSongListId(newSongList.getId());
//					songListSong.setSongId(songId);
//					newSongList.setCreateTime(new Date());
//					songListService.addSong(songListSong);
//					
//					
//				}
//				
//				SongListStyle songListStyle = new SongListStyle();
//				songListStyle.setSongListId(newSongList.getId());
//				songListStyle.setStyleId(style.getId());
//				songListService.addStyle(songListStyle);
//			}
//		});
		Map<String,Integer> mapp1 = new HashMap<>();
		mapp1.put("count", -1);
		List<Scene> scenes = JSONArray.parseArray(JSONArray.toJSONString(sceneService.getList().getData()), Scene.class);
		scenes.stream().forEach(scene->{
			mapp1.put("count", mapp1.get("count") + 1);
			if(mapp1.get("count") < 3)return;
			JSONObject jsonObject = this.getRequest(String.format(byCategoryListUrl, scene.getId()));
			JSONArray songLists = jsonObject.getJSONObject("data").getJSONArray("list");
			int size = songLists.size();
			for(int i = 0; i < size; i++) {
				JSONObject songList = songLists.getJSONObject(i);
				String songListId = songList.getString("dissid");
				String listCover = songList.getString("imgurl");
				String listName = songList.getString("dissname");
				JSONObject jsonObject1 = this.getRequest(String.format(songListDetailUrl, songListId));
				Integer songNum = jsonObject1.getInteger("songnum");
				
				SongList newSongList = new SongList();
				newSongList.setId(songListId);
				newSongList.setTitle(listName);
				newSongList.setCover(listCover);
				newSongList.setSongCount(songNum);
				newSongList.setCreateTime(new Date());
				songListService.addSongList(newSongList);
				
				JSONArray songs = jsonObject1.getJSONArray("Body");
				int size1 = songs.size();
				for(int j = 0; j < size1; j++) {
					JSONObject song = songs.getJSONObject(j);
					String singerName = song.getString("author");
					String cover = song.getString("pic");
					String songName = song.getString("title");
					String url = song.getString("url");
					String lyricId = song.getString("mid");
					
					Time songTime = DateUtils.getTimeInMillis(song.getString("lrc"));
					
					Boolean isVip = Math.round(Math.random()*20) < 6;
					Song newSong = new Song(null,cover,songName,singerName,isVip,songTime,0,lyricId,url);
					String songId = (String)songService.addSong(newSong).getData();
					
					SongScene songScene = new SongScene();
					songScene.setSongId(songId);
					songScene.setSceneId(scene.getId());
					songService.addScene(songScene);
					
					SongListSong songListSong = new SongListSong();
					songListSong.setSongListId(newSongList.getId());
					songListSong.setSongId(songId);
					songListService.addSong(songListSong);
				
					
				}
				
				SongListScene songListScene = new SongListScene();
				songListScene.setSongListId(newSongList.getId());
				songListScene.setSceneId(scene.getId());
				songListService.addScene(songListScene);
			}
		});
		
		return true;
	}
	
	
	public Boolean setSinger() {
		JSONObject jsonObject = this.getRequest(singerUrl);
		JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("list");
		int size = jsonArray.size();
		System.out.println(size);
		for(int i = 74; i < size; i++) {
			String singerId = jsonArray.getJSONObject(i).getString("Fsinger_mid");
			String name = jsonArray.getJSONObject(i).getString("Fsinger_name");
			String singerCover = jsonArray.getJSONObject(i).getString("Fsinger_mid");
			Singer singer = new Singer();
			singer.setName(name);
			singer.setCover(singerCover);
			Integer singerId1 = (Integer)singerService.addSinger(singer).getData();
			
			SongList songList = new SongList();
			songList.setId(UUID.randomUUID().toString());
			songList.setCover("cover");
			songList.setCreateTime(new Date());
			songList.setTitle("歌手:" + name + "的歌曲");
			
			String songListId = (String)songListService.addSongList(songList).getData();
			
			SingerSongList singerSongList = new SingerSongList();
			singerSongList.setSingerId(singerId1);
			singerSongList.setSongListId(songListId);
			singerService.addSongList(singerSongList);
			
			JSONObject jsonObject1 = this.getRequest(String.format(singerSongUrl, singerId));
			JSONArray songs = jsonObject1.getJSONObject("data").getJSONArray("list");
			int size2 = songs.size();
			int j = 0;
			for(j = 0; j < size2; j++) {

				String songMid = songs.getJSONObject(j).getJSONObject("musicData").getString("songmid");
				JSONObject jsonObject2 = this.getRequest(String.format(songDetailUrl, songMid));
				String singerName = jsonObject2.getString("author");
				String songName = jsonObject2.getString("title");
				String url = jsonObject2.getString("url");
				String lyric = songMid;
				String cover = jsonObject2.getString("pic");
				Time songTime = DateUtils.getTimeInMillis(jsonObject2.getString("lrc"));
				Song song = new Song();
				song.setCover(cover);
				song.setIsVip(Math.round(Math.random() * 20) < 6);
				song.setLyric(lyric);
				song.setSingerName(singerName);
				song.setSongName(songName);
				song.setSongTime(songTime);
				song.setUrl(url);
				
				String songId = (String)songService.addSong(song).getData();
				
				SongListSong songListSong = new SongListSong();
				songListSong.setSongListId(songList.getId());
				songListSong.setSongId(songId);

				songListService.addSong(songListSong);
				if(j == 0)songList.setCover(cover);
			}
			
			songList.setSongCount(j+1);
			songListService.updateSongList(songList);
			
			SingerSongList SongListSinger = new SingerSongList();
			SongListSinger.setSongListId(songList.getId());
			SongListSinger.setSingerId(singer.getId());
			singerService.addSongList(SongListSinger);
		}
		return true;
	}
	
    public Boolean setRandomSongList() {
    	List<Song> songs = JSONArray.parseArray(JSONArray.toJSONString(songService.getList().getData()), Song.class);
  
		List<Song> randomSongs = new LinkedList<>();
        
		songs.stream().forEach(song->{
			if(randomSongs.size() > 30)return;
			Boolean isOk = Math.round(Math.random()*20) < 6;
			if(isOk)randomSongs.add(song);
		});
		
		SongList songList = new SongList();
		songList.setTitle("随机歌单");
		songList.setCover(randomSongs.get(0).getCover());
		songList.setSongCount(randomSongs.size());
		songListService.addSongList(songList);
		
		randomSongs.stream().forEach(song->{
			songService.addSong(song);
			SongListSong songListSong = new SongListSong();
			songListSong.setSongListId(songList.getId());
			songListSong.setSongId(song.getId());
			songListService.addSong(songListSong);
		});
		
		return true;
	}

    public Boolean setHotSongList() {
    	SongList songList = new SongList();
    	songList.setTitle("热门推荐");
    	songListService.addSongList(songList);
    	JSONObject Jsonbject = this.getRequest(hotRecommendUrl);
    	JSONArray songListJsonArray = Jsonbject.getJSONArray("songlist");
    	int size = songListJsonArray.size();
    	int i = 0;
    	for(i = 0; i < size; i++) {
    		JSONObject songJsonObject = songListJsonArray.getJSONObject(i).getJSONObject("data");
    		String mid = songJsonObject.getString("songmid");
    		JSONObject JsonObject1 = this.getRequest(String.format(singerUrl, mid));
    		String songName = JsonObject1.getString("title");
    		String singerName = JsonObject1.getString("author");
    		String url = JsonObject1.getString("url");
    		String cover = JsonObject1.getString("pic");
    		Song song = new Song();
    		song.setCover(cover);;
    		song.setIsVip(Math.round(Math.random()*20) < 6);
    		song.setLyric(mid);
    		song.setSingerName(singerName);;
    		song.setSongName(songName);;
    		song.setUrl(url);
    		songService.addSong(song);
    		SongListSong songListSong = new SongListSong();
			songListSong.setSongListId(songList.getId());
			songListSong.setSongId(song.getId());
    		songListService.addSong(songListSong);
    		if(i == 0)songList.setCover(cover);
    	}
    	
    	songList.setSongCount(i + 1);;
    	songListService.updateSongList(songList);
    	return true;
    }
    
    public Boolean setNewSongList() {
    	JSONObject JsonObject = this.getRequest(newRecommendUrl);
    	JSONArray newSongList = JsonObject.getJSONObject("new_song").getJSONObject("data").getJSONArray("song_list");
    	SongList songList = new SongList();
    	songList.setTitle("热门歌单");
    	songListService.addSongList(songList);
    	int size = newSongList.size();
    	int i = 0;
    	for(i = 0; i < size; i++) {
    		JSONObject songJSONObject = newSongList.getJSONObject(i);
    		String mid = songJSONObject.getString("mid");
    		JSONObject songDetailJSONObject = this.getRequest(String.format(singerUrl, mid));
    		Song song = new Song();
    		String songName = songDetailJSONObject.getString("title");
    		String singerName = songDetailJSONObject.getString("author");
    		String url = songDetailJSONObject.getString("url");
    		String cover = songDetailJSONObject.getString("pic");
    		String lyric = mid;
    		song.setCover(cover);
    		song.setIsVip(Math.round(Math.random()*20) < 6);
    		song.setLyric(lyric);
    		song.setSingerName(singerName);
    		song.setUrl(url);
    		song.setSongName(songName);
    		songService.addSong(song);
    		
    		SongListSong songListSong = new SongListSong();
			songListSong.setSongListId(songList.getId());
			songListSong.setSongId(song.getId());
    		songListService.addSong(songListSong);
    		if(i == 0)songList.setCover(cover);;
    	}
    	songList.setSongCount(i+1);
    	songListService.updateSongList(songList);
    	return true;
    }
}
