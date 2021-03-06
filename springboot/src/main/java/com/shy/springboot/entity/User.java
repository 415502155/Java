package com.shy.springboot.entity;

import java.io.Serializable;
import java.util.Date;
//import javax.validation.constraints.Max;
//import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
@Data
public class User implements Serializable {
    private Integer user_id;
    //@NotBlank(message = "用戶名不能為空")
    private String user_name;
    
    private String user_pass;
    //@Max(value = 2)
    private Integer sex;
    
    private String token;
    /***
     **返回时按照指定格式转换，pattern样式，locale表示在中国，timezone表示东八区
     */
    @JsonFormat(pattern="yyyy-MM-dd",locale="zh",timezone="GMT+8")
    private Date birthday;

    private Integer is_del;
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
    
    private Date create_time;
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
    private Date update_time;
    
    private static final long serialVersionUID = 1L;
}