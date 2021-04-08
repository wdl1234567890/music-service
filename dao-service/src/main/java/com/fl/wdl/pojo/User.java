package com.fl.wdl.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@TableId(type=IdType.AUTO)
	private Integer id;
	private String openId;
	private String userName;
	private String avator;
	private Boolean isVip = false;
	
	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof User))return false;
		return ((User)o).id.equals(id) || ((User)o).openId.equals(openId) || ((User)o).userName.equals(userName);
	}
}
