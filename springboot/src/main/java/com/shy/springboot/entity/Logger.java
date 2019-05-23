package com.shy.springboot.entity;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
@Data
public class Logger implements Serializable {
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 7369449163352624487L;
	/***
	 * 主键id
	 */
	private Integer log_id;
	/***
	 * 操作对象id
	 */
	private Integer target_id;
	/***
	 * 操作对象类型
	 */
    private Integer target_type;
    /***
	 * 操作对象id
	 */
    private String target_name;
    /***
	 * 操作人类型
	 */
    private Integer user_type;
    /***
	 * 操作人id
	 */
    private Integer user_id;
    /***
	 * 操作人名称
	 */
    private String user_name;
    /***
     * 操作动作（添加、修改等）
     */
    private Integer action;
    /***
	 * 操作内容
	 */
    private String content;
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
    /***
	 * 删除时间
	 */
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
    private Date del_time;
}