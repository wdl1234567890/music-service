package com.fl.wdl.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Style implements Serializable{
	private Integer id;
	private String name;
	
	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof Style))return false;
		return ((Style)o).id.equals(id) || ((Style)o).name.equals(name);
	}
}
