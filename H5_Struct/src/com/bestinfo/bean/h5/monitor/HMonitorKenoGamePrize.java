/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.bean.h5.monitor;

import java.math.BigDecimal;

/**
 * 快开游戏监控实体类--快开游戏中大奖区域分布
 *
 * @author Administrator
 */
public class HMonitorKenoGamePrize {

    private Integer city_id;    //地市id
    private String city_name;   //地市名称
    private Integer game_id;    //游戏id
    private Integer draw_id;    //drawid
    private String game_name;   //游戏名称
    private Integer keno_draw_id;   //快开期名
    private Integer terminal_id;   //终端机号
    private BigDecimal total_prize;   //中奖金额

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
     * @return the total_prize
     */
    public BigDecimal getTotal_prize() {
        return total_prize;
    }

    /**
     * @param total_prize the total_prize to set
     */
    public void setTotal_prize(BigDecimal total_prize) {
        this.total_prize = total_prize;
    }

    
}
