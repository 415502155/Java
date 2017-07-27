/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.bean.h5.monitor;

import java.math.BigDecimal;

/**
 * 实时缴款账户实体类
 * @author Administrator
 */
public class HRealTimePay {
    private Integer terminal_id;    //终端机Id
    private String station_name;    //站点名称
    private String terminal_type_name;  //终端类型名称
    private String tmn_cash_deduct;     //
    private Integer defalut_credit;     //
    private BigDecimal acc_balance;     //
    private BigDecimal defalut_credit1; //
    private String JG;      //
    private String station_phone;   //
    private String owner_phone;     //
    private BigDecimal income_money;    //
    
    private Integer city_id;
    private String city_name;
    private Integer city_order;
    private Integer user_id;
    private Integer login_city;
    private String user_name;
    private Integer order; //是否排序
    
    

    /**
     * @return the terminal_id
     */
    public Integer getTerminal_id() {
        return terminal_id;
    }

    /**
     * @param terminal_id the terminal_id to set
     */
    public void setTerminal_id(Integer terminal_id) {
        this.terminal_id = terminal_id;
    }

    /**
     * @return the station_name
     */
    public String getStation_name() {
        return station_name;
    }

    /**
     * @param station_name the station_name to set
     */
    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    /**
     * @return the terminal_type_name
     */
    public String getTerminal_type_name() {
        return terminal_type_name;
    }

    /**
     * @param terminal_type_name the terminal_type_name to set
     */
    public void setTerminal_type_name(String terminal_type_name) {
        this.terminal_type_name = terminal_type_name;
    }

    /**
     * @return the tmn_cash_deduct
     */
    public String getTmn_cash_deduct() {
        return tmn_cash_deduct;
    }

    /**
     * @param tmn_cash_deduct the tmn_cash_deduct to set
     */
    public void setTmn_cash_deduct(String tmn_cash_deduct) {
        this.tmn_cash_deduct = tmn_cash_deduct;
    }

    /**
     * @return the defalut_credit
     */
    public Integer getDefalut_credit() {
        return defalut_credit;
    }

    /**
     * @param defalut_credit the defalut_credit to set
     */
    public void setDefalut_credit(Integer defalut_credit) {
        this.defalut_credit = defalut_credit;
    }

    /**
     * @return the acc_balance
     */
    public BigDecimal getAcc_balance() {
        return acc_balance;
    }

    /**
     * @param acc_balance the acc_balance to set
     */
    public void setAcc_balance(BigDecimal acc_balance) {
        this.acc_balance = acc_balance;
    }

    /**
     * @return the defalut_credit1
     */
    public BigDecimal getDefalut_credit1() {
        return defalut_credit1;
    }

    /**
     * @param defalut_credit1 the defalut_credit1 to set
     */
    public void setDefalut_credit1(BigDecimal defalut_credit1) {
        this.defalut_credit1 = defalut_credit1;
    }

    /**
     * @return the JG
     */
    public String getJG() {
        return JG;
    }

    /**
     * @param JG the JG to set
     */
    public void setJG(String JG) {
        this.JG = JG;
    }

    /**
     * @return the station_phone
     */
    public String getStation_phone() {
        return station_phone;
    }

    /**
     * @param station_phone the station_phone to set
     */
    public void setStation_phone(String station_phone) {
        this.station_phone = station_phone;
    }

    /**
     * @return the owner_phone
     */
    public String getOwner_phone() {
        return owner_phone;
    }

    /**
     * @param owner_phone the owner_phone to set
     */
    public void setOwner_phone(String owner_phone) {
        this.owner_phone = owner_phone;
    }

    /**
     * @return the income_money
     */
    public BigDecimal getIncome_money() {
        return income_money;
    }

    /**
     * @param income_money the income_money to set
     */
    public void setIncome_money(BigDecimal income_money) {
        this.income_money = income_money;
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

    /**
     * @return the user_id
     */
    public Integer getUser_id() {
        return user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    /**
     * @return the login_city
     */
    public Integer getLogin_city() {
        return login_city;
    }

    /**
     * @param login_city the login_city to set
     */
    public void setLogin_city(Integer login_city) {
        this.login_city = login_city;
    }

    /**
     * @return the user_name
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * @param user_name the user_name to set
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /**
     * @return the order
     */
    public Integer getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(Integer order) {
        this.order = order;
    }
    
}
