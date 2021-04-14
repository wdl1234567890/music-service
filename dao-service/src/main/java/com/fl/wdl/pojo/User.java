package com.fl.wdl.pojo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fl.wdl.validation.Groups;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@TableId(type=IdType.AUTO)
	@Null(message="用户id必须为空",groups=Groups.Add.class)
	@NotNull(message="用户id不能为空",groups=Groups.Update.class)
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
