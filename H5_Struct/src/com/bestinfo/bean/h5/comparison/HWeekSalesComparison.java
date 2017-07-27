/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.bean.h5.comparison;

import java.math.BigDecimal;

/**
 * 时间维度销量对比 实体类
 * 
 * @author Administrator
 */
public class HWeekSalesComparison {
   private Integer year_week;   //年周 | 年
   private BigDecimal sale_money;   //钱

    /**
     * @return the year_week
     */
    public Integer getYear_week() {
        return year_week;
    }

    /**
     * @param year_week the year_week to set
     */
    public void setYear_week(Integer year_week) {
        this.year_week = year_week;
    }

    /**
     * @return the sale_money
     */
    public BigDecimal getSale_money() {
        return sale_money;
    }

    /**
     * @param sale_money the sale_money to set
     */
    public void setSale_money(BigDecimal sale_money) {
        this.sale_money = sale_money;
    }
   
   
}
