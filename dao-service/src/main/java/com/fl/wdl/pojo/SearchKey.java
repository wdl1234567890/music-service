package com.fl.wdl.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchKey implements Serializable{
	@TableId(type=IdType.AUTO)
	private Integer id;
	private String oneKey;
	private Integer searchCount;
}
