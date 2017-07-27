package com.bestinfo.bean.h5.taskPlan;

import java.math.BigDecimal;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author zhen
 */
public class YearTaskPlan {

    private Integer year_id; //年度
    private BigDecimal plan_sales_money; //计划销售额
    private BigDecimal strive_sales_money; //奋斗目标
    
    private Date complete_day;
    private BigDecimal m1;  
    private BigDecimal will_money;

    public Integer getYear_id() {
        return year_id;
    }

    public void setYear_id(Integer year_id) {
        this.year_id = year_id;
    }

    /**
     * @return the plan_sales_money
     */
    public BigDecimal getPlan_sales_money() {
        return plan_sales_money;
    }

    /**
     * @param plan_sales_money the plan_sales_money to set
     */
    public void setPlan_sales_money(BigDecimal plan_sales_money) {
        this.plan_sales_money = plan_sales_money;
    }

    /**
     * @return the strive_sales_money
     */
    public BigDecimal getStrive_sales_money() {
        return strive_sales_money;
    }

    /**
     * @param strive_sales_money the strive_sales_money to set
     */
    public void setStrive_sales_money(BigDecimal strive_sales_money) {
        this.strive_sales_money = strive_sales_money;
    }

    /**
     * @return the complete_day
     */
    public Date getComplete_day() {
        return complete_day;
    }

    /**
     * @param complete_day the complete_day to set
     */
    public void setComplete_day(Date complete_day) {
        this.complete_day = complete_day;
    }

    /**
     * @return the m1
     */
    public BigDecimal getM1() {
        return m1;
    }

    /**
     * @param m1 the m1 to set
     */
    public void setM1(BigDecimal m1) {
        this.m1 = m1;
    }

    /**
     * @return the will_money
     */
    public BigDecimal getWill_money() {
        return will_money;
    }

    /**
     * @param will_money the will_money to set
     */
    public void setWill_money(BigDecimal will_money) {
        this.will_money = will_money;
    }

    

}
