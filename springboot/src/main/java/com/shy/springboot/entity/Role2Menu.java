package com.shy.springboot.entity;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Role2Menu implements Serializable {
	
	private static final long serialVersionUID = 8888272405887665597L;
	private Integer role_menu_id; 
	private Integer menu_id; 
	private Integer role_id; 
	private Integer is_del;
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
    private Date create_time;
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
    private Date update_time;
}
