/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.bean.h5.monitor;

/**
 * 快开游戏监控实体类--游戏基本信息
 *
 * @author Administrator
 */
public class HMonitorKenoGame {

    private Integer game_id;    //游戏id
    private String game_name;   //游戏名称
    private Integer keno_draw_id;    //当前快开期号
    private String keno_draw_name;   //当前快开期名
    private String cash_begin;     //开奖时间
    private String last_lucky_no;     //上期开奖号码
    private Integer draw_id;    //当前天的大期期号

    
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

    public Integer getKeno_draw_id() {
        return keno_draw_id;
    }

    public void setKeno_draw_id(Integer keno_draw_id) {
        this.keno_draw_id = keno_draw_id;
    }

    public String getKeno_draw_name() {
        return keno_draw_name;
    }

    public void setKeno_draw_name(String keno_draw_name) {
        this.keno_draw_name = keno_draw_name;
    }

    public String getCash_begin() {
        return cash_begin;
    }

    public void setCash_begin(String cash_begin) {
        this.cash_begin = cash_begin;
    }

    public String getLast_lucky_no() {
        return last_lucky_no;
    }

    public void setLast_lucky_no(String last_lucky_no) {
        this.last_lucky_no = last_lucky_no;
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

}
