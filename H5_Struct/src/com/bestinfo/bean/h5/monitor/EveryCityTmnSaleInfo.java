package com.bestinfo.bean.h5.monitor;

import java.math.BigDecimal;

/**
 * 各个地区终端的销售量
 */
public class EveryCityTmnSaleInfo {
     private Integer sale_ticket_num;        //销售票的数量
     private Integer terminal_id;            //终端机ID
     private String terminal_address;       //城市名称
     private BigDecimal sale_money;         //投注机当天销售金额
     private String trade_time;             //交易时间
     private String trade_type;             //交易类型

    /**
     * @return the sale_ticket_num
     */
    public Integer getSale_ticket_num() {
        return sale_ticket_num;
    }

    /**
     * @param sale_ticket_num the sale_ticket_num to set
     */
    public void setSale_ticket_num(Integer sale_ticket_num) {
        this.sale_ticket_num = sale_ticket_num;
    }

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
     * @return the trade_time
     */
    public String getTrade_time() {
        return trade_time;
    }

    /**
     * @param trade_time the trade_time to set
     */
    public void setTrade_time(String trade_time) {
        this.trade_time = trade_time;
    }

    /**
     * @return the trade_type
     */
    public String getTrade_type() {
        return trade_type;
    }

    /**
     * @param trade_type the trade_type to set
     */
    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

   
}
