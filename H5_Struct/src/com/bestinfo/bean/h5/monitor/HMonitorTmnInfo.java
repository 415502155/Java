/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.bean.h5.monitor;

import java.math.BigDecimal;

/**
 * 某彩种某地市下投注机销量详情
 * @author Administrator
 */
public class HMonitorTmnInfo {
    private Integer terminal_id;       //终端id
    private String terminal_address;   //投注机地址
    private String terminal_type_name; //终端类型名称
    private BigDecimal sale_money;     //销售总额
    private BigDecimal undo_money;     //注销金额
    private BigDecimal cash_money;     //总兑奖额
    private Integer game_permit;       //游戏
    private Integer sale_permit;       //销量
    private Integer cash_permit;       //兑奖
    private Integer terminal_status;   //营业特权

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
     * @return the terminal_address
     */
    public String getTerminal_address() {
        return terminal_address;
    }

    /**
     * @param terminal_address the terminal_address to set
     */
    public void setTerminal_address(String terminal_address) {
        this.terminal_address = terminal_address;
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
     * @return the undo_money
     */
    public BigDecimal getUndo_money() {
        return undo_money;
    }

    /**
     * @param undo_money the undo_money to set
     */
    public void setUndo_money(BigDecimal undo_money) {
        this.undo_money = undo_money;
    }

    /**
     * @return the cash_money
     */
    public BigDecimal getCash_money() {
        return cash_money;
    }

    /**
     * @param cash_money the cash_money to set
     */
    public void setCash_money(BigDecimal cash_money) {
        this.cash_money = cash_money;
    }

    /**
     * @return the game_permit
     */
    public Integer getGame_permit() {
        return game_permit;
    }

    /**
     * @param game_permit the game_permit to set
     */
    public void setGame_permit(Integer game_permit) {
        this.game_permit = game_permit;
    }

    /**
     * @return the sale_permit
     */
    public Integer getSale_permit() {
        return sale_permit;
    }

    /**
     * @param sale_permit the sale_permit to set
     */
    public void setSale_permit(Integer sale_permit) {
        this.sale_permit = sale_permit;
    }

    /**
     * @return the cash_permit
     */
    public Integer getCash_permit() {
        return cash_permit;
    }

    /**
     * @param cash_permit the cash_permit to set
     */
    public void setCash_permit(Integer cash_permit) {
        this.cash_permit = cash_permit;
    }

    /**
     * @return the terminal_status
     */
    public Integer getTerminal_status() {
        return terminal_status;
    }

    /**
     * @param terminal_status the terminal_status to set
     */
    public void setTerminal_status(Integer terminal_status) {
        this.terminal_status = terminal_status;
    }

}
