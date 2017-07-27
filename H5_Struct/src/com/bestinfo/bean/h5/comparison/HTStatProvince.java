/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.bean.h5.comparison;

import java.math.BigDecimal;

/**
 * 公益金对比实体类
 * 
 * @author Administrator
 */
public class HTStatProvince {
    private String year_id;
    private BigDecimal sale_money;
    private BigDecimal welfare_money;


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

    /**
     * @return the welfare_money
     */
    public BigDecimal getWelfare_money() {
        return welfare_money;
    }

    /**
     * @param welfare_money the welfare_money to set
     */
    public void setWelfare_money(BigDecimal welfare_money) {
        this.welfare_money = welfare_money;
    }

    /**
     * @return the year_id
     */
    public String getYear_id() {
        return year_id;
    }

    /**
     * @param year_id the year_id to set
     */
    public void setYear_id(String year_id) {
        this.year_id = year_id;
    }
    
}
