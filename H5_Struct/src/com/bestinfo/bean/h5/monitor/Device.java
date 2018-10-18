/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.bean.h5.monitor;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;

/**
 * 管理终端查询用
 * @author YangRong
 */
public class Device {
    @JSONField(name="DEVICE_NUMBER") 
    private Integer device_number;
    @JSONField(name="DEVICE_ID") 
    private String device_id;
    @JSONField(name="DEVICE_BRAND") 
    private String device_brand;
    @JSONField(name="REGIST_DATE",format= "yyyy-MM-dd HH:mm:ss") 
    private Date regist_date;
    @JSONField(name="DEVICE_STATUS") 
    private Integer device_status;

    public Integer getDevice_number() {
        return device_number;
    }

    public void setDevice_number(Integer device_number) {
        this.device_number = device_number;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getDevice_brand() {
        return device_brand;
    }

    public void setDevice_brand(String device_brand) {
        this.device_brand = device_brand;
    }

    public Date getRegist_date() {
        return regist_date;
    }

    public void setRegist_date(Date regist_date) {
        this.regist_date = regist_date;
    }

    

    public Integer getDevice_status() {
        return device_status;
    }

    public void setDevice_status(Integer device_status) {
        this.device_status = device_status;
    }

   
}
