/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.bean.h5.monitor;

import java.math.BigDecimal;

/**
 * 游戏监控实体类
 * @author Administrator
 */
public class HMonitorGame {
    private Integer game_id;    //游戏id
    private String game_name;   //游戏名称
    private Integer draw_id;    //期id
    private String draw_name;   //期名
    private String sales_end;     //期结时间
    private String process_status_name;     //期状态
    private BigDecimal sale_money;  //销售总额
    private Integer process_status;     //期状态
    
    private String sales_begin;     //期开始时间
    private Integer keno_draw_id;    //快开期id
     

    /**
     * @return the game_id
     */
    public Integer getGame_id() {
        return game_id;
    }

    /**
     * @param game_id the game_id to set
     */
    public void setGame_id(Integer game_id) {
        this.game_id = game_id;
    }

    /**
     * @return the game_name
     */
    public String getGame_name() {
        return game_name;
    }

    /**
     * @param game_name the game_name to set
     */
    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    /**
     * @return the draw_id
     */
    public Integer getDraw_id() {
        return draw_id;
    }

    /**
     * @param draw_id the draw_id to set
     */
    public void setDraw_id(Integer draw_id) {
        this.draw_id = draw_id;
    }

    /**
     * @return the draw_name
     */
    public String getDraw_name() {
        return draw_name;
    }

    /**
     * @param draw_name the draw_name to set
     */
    public void setDraw_name(String draw_name) {
        this.draw_name = draw_name;
    }

    /**
     * @return the process_status_name
     */
    public String getProcess_status_name() {
        return process_status_name;
    }

    /**
     * @param process_status_name the process_status_name to set
     */
    public void setProcess_status_name(String process_status_name) {
        this.process_status_name = process_status_name;
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
     * @return the process_status
     */
    public Integer getProcess_status() {
        return process_status;
    }

    /**
     * @param process_status the process_status to set
     */
    public void setProcess_status(Integer process_status) {
        this.process_status = process_status;
    }

    /**
     * @return the sales_end
     */
    public String getSales_end() {
        return sales_end;
    }

    /**
     * @param sales_end the sales_end to set
     */
    public void setSales_end(String sales_end) {
        this.sales_end = sales_end;
    }

    /**
     * @return the sales_begin
     */
    public String getSales_begin() {
        return sales_begin;
    }

    /**
     * @param sales_begin the sales_begin to set
     */
    public void setSales_begin(String sales_begin) {
        this.sales_begin = sales_begin;
    }

    /**
     * @return the keno_draw_id
     */
    public Integer getKeno_draw_id() {
        return keno_draw_id;
    }

    /**
     * @param keno_draw_id the keno_draw_id to set
     */
    public void setKeno_draw_id(Integer keno_draw_id) {
        this.keno_draw_id = keno_draw_id;
    }
    
    
}
