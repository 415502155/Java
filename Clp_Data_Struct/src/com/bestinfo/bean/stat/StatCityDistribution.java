package com.bestinfo.bean.stat;

import java.io.Serializable;

/**
 * 结算统计表-游戏地市中奖分布(T_stat_city_distribution)
 */
public class StatCityDistribution implements Serializable {

    private static final long serialVersionUID = 2121474633445343616L;
    private Integer game_id;// 游戏编号
    private Integer draw_id;// 期号
    private Integer keno_draw_id;// keno期号
    private Integer city_id;// 地市编号
    private Integer open_id;// 开奖次数
    private Integer class_id;// 奖级编号
    private Integer prize_num;// 中奖注数

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
     * @return the open_id
     */
    public Integer getOpen_id() {
        return open_id;
    }

    /**
     * @param open_id the open_id to set
     */
    public void setOpen_id(Integer open_id) {
        this.open_id = open_id;
    }

    /**
     * @return the class_id
     */
    public Integer getClass_id() {
        return class_id;
    }

    /**
     * @param class_id the class_id to set
     */
    public void setClass_id(Integer class_id) {
        this.class_id = class_id;
    }

    /**
     * @return the prize_num
     */
    public Integer getPrize_num() {
        return prize_num;
    }

    /**
     * @param prize_num the prize_num to set
     */
    public void setPrize_num(Integer prize_num) {
        this.prize_num = prize_num;
    }

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }
}
