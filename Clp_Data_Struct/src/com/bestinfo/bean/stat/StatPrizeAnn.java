package com.bestinfo.bean.stat;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 结算统计表-普通游戏中奖汇总(T_stat_prize_ann)
 *
 * @author chenliping
 */
public class StatPrizeAnn implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3090671069866655586L;
    private Integer game_id; //游戏编号
    private Integer draw_id; //总期号
    private Integer play_id; //玩法编号
    private Integer open_id; //开奖次数
    private Integer class_id; //奖级编号
    private String class_name; //奖级名称
    private Integer prize_num; //中奖注数
    private BigDecimal stake_prize; //单注奖金
    private Integer total_num; //联合销售中奖数量

    public Integer getGame_id() {
        return game_id;
    }

    public void setGame_id(Integer game_id) {
        this.game_id = game_id;
    }

    public Integer getDraw_id() {
        return draw_id;
    }

    public void setDraw_id(Integer draw_id) {
        this.draw_id = draw_id;
    }

    public Integer getPlay_id() {
        return play_id;
    }

    public void setPlay_id(Integer play_id) {
        this.play_id = play_id;
    }

    public Integer getOpen_id() {
        return open_id;
    }

    public void setOpen_id(Integer open_id) {
        this.open_id = open_id;
    }

    public Integer getClass_id() {
        return class_id;
    }

    public void setClass_id(Integer class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public Integer getPrize_num() {
        return prize_num;
    }

    public void setPrize_num(Integer prize_num) {
        this.prize_num = prize_num;
    }

    public BigDecimal getStake_prize() {
        return stake_prize;
    }

    public void setStake_prize(BigDecimal stake_prize) {
        this.stake_prize = stake_prize;
    }

    public Integer getTotal_num() {
        return total_num;
    }

    public void setTotal_num(Integer total_num) {
        this.total_num = total_num;
    }

}
