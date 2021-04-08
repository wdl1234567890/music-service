package com.fl.wdl.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStyle {
	@TableId(type=IdType.AUTO)
	private Integer id;
	private Integer userId;
	private Integer styleId;
	
	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof UserStyle))return false;
		return ((UserStyle)o).id.equals(id) || ((UserStyle)o).userId.equals(userId) && ((UserStyle)o).styleId.equals(styleId);
	}

}