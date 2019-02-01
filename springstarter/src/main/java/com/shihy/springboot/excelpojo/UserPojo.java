package com.shihy.springboot.excelpojo;

import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class UserPojo {
	
	@Excel(name = "用户名称", orderNum = "0")
    private String user_name;
    
    @Excel(name = "性别", replace = {"男_1", "女_2", "保密_0"}, orderNum = "1")
    private Integer sex;
    
    @Excel(name = "生日", exportFormat = "yyyy-MM-dd", orderNum = "2")
    private String birthday;
    
    private String user_pass;

    private Integer is_del;
    
    private Date create_time;
}
