package com.fl.wdl.utils;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.sql.Time;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fl.wdl.constant.RequestVar;

public class DateUtils {
	public static Time getTimeInMillis(String lyricUrl) {
		Time time = null;	
		ResponseEntity<String> exchange = null;
		do {
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
				exchange = restTemplate.exchange(lyricUrl, HttpMethod.GET, null, String.class);
				if(exchange.getStatusCode()!=HttpStatus.OK) {
					RequestVar.ip = null;
					RequestVar.port = null;
				}
				String lyric = exchange.getBody();
				System.out.println();
				System.out.println(lyric);
				System.out.println();
				int start = lyric.lastIndexOf("[")+1;
				if(start == 0)return null;
				lyric = lyric.substring(start, lyric.lastIndexOf("]"));
				int endIndex = lyric.lastIndexOf(".");
				if(endIndex == -1)endIndex = lyric.lastIndexOf(":");
				if(endIndex == -1)return null;
				String[] timeStr = lyric.substring(0, endIndex).split(":");
				
				if(timeStr == null || timeStr.length != 2)return null;
				for(String aa:timeStr) {
					System.out.println("*** "+aa+" ***");
				}
				
				time = new Time(0, Integer.parseInt(timeStr[0]), Integer.parseInt(timeStr[1]));
				time.setHours(0);
			}catch(Exception e) {
				RequestVar.ip = null;
				RequestVar.port = null;
			}
		}while(RequestVar.ip == null || RequestVar.port == null);
		
     
		return time;
	}

}
