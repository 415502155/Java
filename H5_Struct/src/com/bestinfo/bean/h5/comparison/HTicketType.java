/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.bean.h5.comparison;

import java.math.BigDecimal;

/**
 * 票种同比增长实体类
 * @author Administrator
 */
public class HTicketType {
    private String city_name;   //
    private BigDecimal one_inc;
    private String one_ratio;
    private BigDecimal two_inc;
    private String two_ratio;
    private BigDecimal thr_inc;
    private String thr_ratio;
    private BigDecimal all_inc;
    private String all_ratio;

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
     * @return the one_inc
     */
    public BigDecimal getOne_inc() {
        return one_inc;
    }

    /**
     * @param one_inc the one_inc to set
     */
    public void setOne_inc(BigDecimal one_inc) {
        this.one_inc = one_inc;
    }

    /**
     * @return the two_inc
     */
    public BigDecimal getTwo_inc() {
        return two_inc;
    }

    /**
     * @param two_inc the two_inc to set
     */
    public void setTwo_inc(BigDecimal two_inc) {
        this.two_inc = two_inc;
    }

    /**
     * @return the thr_inc
     */
    public BigDecimal getThr_inc() {
        return thr_inc;
    }

    /**
     * @param thr_inc the thr_inc to set
     */
    public void setThr_inc(BigDecimal thr_inc) {
        this.thr_inc = thr_inc;
    }

    /**
     * @return the all_inc
     */
    public BigDecimal getAll_inc() {
        return all_inc;
    }

    /**
     * @param all_inc the all_inc to set
     */
    public void setAll_inc(BigDecimal all_inc) {
        this.all_inc = all_inc;
    }

    /**
     * @return the one_ratio
     */
    public String getOne_ratio() {
        return one_ratio;
    }

    /**
     * @param one_ratio the one_ratio to set
     */
    public void setOne_ratio(String one_ratio) {
        this.one_ratio = one_ratio;
    }

    /**
     * @return the two_ratio
     */
    public String getTwo_ratio() {
        return two_ratio;
    }

    /**
     * @param two_ratio the two_ratio to set
     */
    public void setTwo_ratio(String two_ratio) {
        this.two_ratio = two_ratio;
    }

    /**
     * @return the thr_ratio
     */
    public String getThr_ratio() {
        return thr_ratio;
    }

    /**
     * @param thr_ratio the thr_ratio to set
     */
    public void setThr_ratio(String thr_ratio) {
        this.thr_ratio = thr_ratio;
    }

    /**
     * @return the all_ratio
     */
    public String getAll_ratio() {
        return all_ratio;
    }

    /**
     * @param all_ratio the all_ratio to set
     */
    public void setAll_ratio(String all_ratio) {
        this.all_ratio = all_ratio;
    }

}
