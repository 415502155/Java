/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.bean.h5.comparison;

import java.math.BigDecimal;

/**
 * 票种实体类
 * @author Administrator
 */
public class TicketType {
    private Integer ticket_type;   //票种类型
    private String ticket_type_name;//票种类型名称

    /**
     * @return the ticket_type
     */
    public Integer getTicket_type() {
        return ticket_type;
    }

    /**
     * @param ticket_type the ticket_type to set
     */
    public void setTicket_type(Integer ticket_type) {
        this.ticket_type = ticket_type;
    }

    /**
     * @return the ticket_type_name
     */
    public String getTicket_type_name() {
        return ticket_type_name;
    }

    /**
     * @param ticket_type_name the ticket_type_name to set
     */
    public void setTicket_type_name(String ticket_type_name) {
        this.ticket_type_name = ticket_type_name;
    }

}
