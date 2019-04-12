package com.shihy.springboot.excelpojo;

import java.util.Date;
import lombok.Data;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月27日 下午3:30:35
 *
 */
@Data
public class UserPojoPoi {
	
    @ExcelColumn(value = "用户名称", col = 1)
    private String user_name;

    @ExcelColumn(value = "性别", col = 2)
    private String sex;

    @ExcelColumn(value = "生日", col = 3)
    private String birthday;
    
    private String user_pass;

    private Integer is_del;
    
    private Date create_time;
}
