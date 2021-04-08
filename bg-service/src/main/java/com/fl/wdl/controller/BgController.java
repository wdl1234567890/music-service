package com.fl.wdl.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fl.wdl.service.BgService;


@RestController
@RequestMapping("/bg")
public class BgController {
	
	@Autowired
	BgService bgService;
	
	@GetMapping("/setStyleAndScene")
	public Boolean setStyleAndScene() {
		return bgService.setStyleAndScene();
	}
	
	@GetMapping("/setSongsAndSongLists")
	public Boolean setSongsAndSongLists() {
		
		return bgService.setSongsAndSongLists();
	}
	
	@GetMapping("/setSinger")
	public Boolean setSinger() {
		return bgService.setSinger();
	}
	
	@GetMapping("/setRandomSongList")
    public Boolean setRandomSongList() {
		return bgService.setRandomSongList();  
	}

	@GetMapping("/setHotSongList")
    public Boolean setHotSongList() {
    	return bgService.setHotSongList();
    }
    
	@GetMapping("/setNewSongList")
    public Boolean setNewSongList() {
    	return bgService.setNewSongList();
    }
}
