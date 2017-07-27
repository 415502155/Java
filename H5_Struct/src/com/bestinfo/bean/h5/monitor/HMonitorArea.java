/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.bean.h5.monitor;

import java.math.BigDecimal;

/**
 * 区域监控实体类
 * 
 * @author Administrator
 */
public class HMonitorArea {
   private Integer city_id;
   private String city_name;
   private BigDecimal sale_money;
   private BigDecimal paln_money;
   private Integer finish_ratio;
   private Integer dis_ratio;
   private Integer avg_ratio;

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
     * @return the plan_money
     */
    public BigDecimal getPaln_money() {
        return paln_money;
    }

    /**
     * @param paln_money
     */
    public void setPaln_money(BigDecimal paln_money) {
        this.paln_money = paln_money;
    }

    /**
     * @return the finish_ratio
     */
    public Integer getFinish_ratio() {
        return finish_ratio;
    }

    /**
     * @param finish_ratio the finish_ratio to set
     */
    public void setFinish_ratio(Integer finish_ratio) {
        this.finish_ratio = finish_ratio;
    }

    /**
     * @return the dis_ratio
     */
    public Integer getDis_ratio() {
        return dis_ratio;
    }

    /**
     * @param dis_ratio the dis_ratio to set
     */
    public void setDis_ratio(Integer dis_ratio) {
        this.dis_ratio = dis_ratio;
    }

    /**
     * @return the avg_ratio
     */
    public Integer getAvg_ratio() {
        return avg_ratio;
    }

    /**
     * @param avg_ratio the avg_ratio to set
     */
    public void setAvg_ratio(Integer avg_ratio) {
        this.avg_ratio = avg_ratio;
    }
   
}
