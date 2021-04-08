package com.fl.wdl.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scene implements Serializable{
	private Integer id;
	private String name;
	
	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof Scene))return false;
		return ((Scene)o).id.equals(id) || ((Scene)o).name.equals(name);
	}
}
