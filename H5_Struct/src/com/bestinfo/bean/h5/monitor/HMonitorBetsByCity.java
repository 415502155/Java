/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.bean.h5.monitor;

import java.math.BigDecimal;

/**
 * 某彩种各地市投注额
 * @author Administrator
 */
public class HMonitorBetsByCity {
    private String city_name;       //城市名称
    private BigDecimal sale_money;  //销售总额
    private Integer city_id;        //城市id
    private Integer city_order;     

    /**
     * @return the city_name
     */
    public String getCity_name() {
        return city_name;
    }

    /**
     * @param city_name the city_name to set
     */
    public void setCity_name(String city_name) {
        this.city_name = city_name;
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

    /**
     * @return the city_id
     */
    public Integer getCity_id() {
        return city_id;
    }

    /**
     * @param city_id the city_id to set
     */
    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }

    /**
     * @return the city_order
     */
    public Integer getCity_order() {
        return city_order;
    }

    /**
     * @param city_order the city_order to set
     */
    public void setCity_order(Integer city_order) {
        this.city_order = city_order;
    }
 
}
