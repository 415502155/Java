/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.bean.encoding;

import java.io.Serializable;

/**
 * 账户扣款类型
 *
 * @author user
 */
public class PayType implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2405839286083249462L;
	private Integer pay_type_id;  //扣款类型编号
    private String pay_type_name; //扣款类型名称
    private Integer work_status; //工作状态

    /**
     * @return the pay_type_id
     */
    public Integer getPay_type_id() {
        return pay_type_id;
    }

    /**
     * @param pay_type_id the pay_type_id to set
     */
    public void setPay_type_id(Integer pay_type_id) {
        this.pay_type_id = pay_type_id;
    }

    /**
     * @return the pay_type_name
     */
    public String getPay_type_name() {
        return pay_type_name;
    }

    /**
     * @param pay_type_name the pay_type_name to set
     */
    public void setPay_type_name(String pay_type_name) {
        this.pay_type_name = pay_type_name;
    }

    /**
     * @return the work_status
     */
    public Integer getWork_status() {
        return work_status;
    }

    /**
     * @param work_status the work_status to set
     */
    public void setWork_status(Integer work_status) {
        this.work_status = work_status;
    }

}
