package com.shy.springboot.entity;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
@Data
public class Account implements Serializable {

	private static final long serialVersionUID = -3448417585151787249L;
	private Integer acc_id;
	private String card_no;
	private String balance;
	private Integer user_id;
	private String deposit_bank;
	private String address;
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
	private Date opening_time;
    /***
     ** 添加时间
     *	返回时按照指定格式转换，pattern样式，locale表示在中国，timezone表示东八区
     */
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
    private Date insert_time;
    
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
	private Date del_time;
    /***
	 * 修改时间
	 */
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
    private Date update_time;
    /***
	 * 是否删除
	 */
    private Integer is_del;
}