package com.bestinfo.bean.h5.monitor;

import java.math.BigDecimal;

/**
 *
 * @author Administrator
 * 9种玩法当前期的销量
 */
public class CurrentTmnDrawStat {
    private BigDecimal sale_money;//单个彩种当期的销量 
    private Integer game_id;      //游戏id
    private String draw_name;     //期名称
    private String game_name;     //游戏名称

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
}
