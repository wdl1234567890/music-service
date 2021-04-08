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
public class Singer implements Serializable{
	
	@TableId(type=IdType.AUTO)
	private Integer id;
	private String name;
	private String cover;
	
	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof Singer))return false;
		return ((Singer)o).id.equals(id) || ((Singer)o).name.equals(name);
	}
}
