package com.shihy.springboot.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description User Entity
 * @data 2019年3月27日 下午3:30:35
 *
 */
@Data
public class User implements Serializable {
    private Integer user_id;

    private String user_name;

    private String user_pass;

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
    
    @Transient
    private String sexStr;
    
    @Transient
    private String birthdayStr;
    private static final long serialVersionUID = 1L;
}