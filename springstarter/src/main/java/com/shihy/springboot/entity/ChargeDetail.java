package com.shihy.springboot.entity;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description
 * @data 2019年3月27日 下午3:30:35
 *
 */
@Data
public class ChargeDetail implements Serializable {

	private static final long serialVersionUID = -3448417585151787249L;
	/***
	 * 主键cd_id
	 */
	private Integer cd_id;
	/***
	 * 被操作对象id
	 */
    private Integer target_id;
    /***
	 * 被操作对象名称
	 */
    private String target_name;
    /***
	 * 交易金额
	 */
    private String money;
    /***
	 * 订单号
	 */
    private String order_no;
    /***
	 * 操作人名称
	 */
    private String operator;
    /***
     * 缴费开始时间
     */
    private Date start_time;
    /***
	 * 缴费结束时间
	 */
    private Date end_time;
    /***
     * 支付时间
     */
    private Date pay_time;
    /***
     * 缴费类型 0：待缴费；1：已缴费；2；已退费；3：缴费超时
     */
    private Integer type;
    /***
	 * 是否删除
	 */
    private Integer is_del;
    /***
     ** 添加时间
     *	返回时按照指定格式转换，pattern样式，locale表示在中国，timezone表示东八区
     */
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
    private Date create_time;
    /***
	 * 修改时间
	 */
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
    private Date update_time;
}