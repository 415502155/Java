package com.shy.springboot.entity;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
@Data
public class ChargeRecord implements Serializable {

	private static final long serialVersionUID = -974195204876780750L;
	/***
	 * 主键cd_id
	 */
	private Integer cd_id;
	/***
	 * 交易记录表cr_id
	 */
	private Integer cr_id;
	/***
	 * 交易流水号
	 */
    private String trace_no;
    /***
	 * 交易类型 0:待缴费；1：缴费；2：退费；/(1:存钱； 2:取钱)
	 */
    private Integer type;
    /***
	 * 交易金额
	 */
    private String money;
    
    /***
     * 交易时间
     */
    private Date trace_time;

    /***
	 * 是否删除
	 */
    private Integer is_del;
    /***
     ** 添加时间
     *	返回时按照指定格式转换，pattern样式，locale表示在中国，timezone表示东八区
     */
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
    private Date insert_time;
    /***
	 * 修改时间
	 */
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
    private Date update_time;
    //用户
    private Integer user_id;
    //账户
    private String card_no;
}