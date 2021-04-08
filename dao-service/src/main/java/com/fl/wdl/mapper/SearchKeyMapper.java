package com.fl.wdl.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fl.wdl.pojo.SearchKey;

@Mapper
public interface SearchKeyMapper extends BaseMapper<SearchKey>{
	Boolean addCount(String key);
	Boolean reduceCount(String key);
}
