package com.bestinfo.bean.stat;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 奖池附属表，用来记录每个奖级的小数点和补足提取(t_stat_jackpot_attach)
 */
public class StatJackpotAttach implements Serializable {

    private Integer game_id;//游戏编号
    private Integer play_id;//玩法编号
    private Integer draw_id;//总期号
    private Integer keno_draw_id;//快开期号
    private BigDecimal round_money_1 = BigDecimal.ZERO;//奖级1的小数点
    private BigDecimal round_money_2 = BigDecimal.ZERO;//奖级2的小数点
    private BigDecimal fill_prize_1 = BigDecimal.ZERO;//奖级1的补足提取
    private BigDecimal fill_prize_2 = BigDecimal.ZERO;//奖级2的补足提取
    private BigDecimal fill_prize_fix = BigDecimal.ZERO;//固定奖级的补足提取总和
    private String note;//备注

    public Integer getGame_id() {
        return game_id;
    }

    public void setGame_id(Integer game_id) {
        this.game_id = game_id;
    }

    public Integer getPlay_id() {
        return play_id;
    }

    public void setPlay_id(Integer play_id) {
        this.play_id = play_id;
    }

    public Integer getDraw_id() {
        return draw_id;
    }

    public void setDraw_id(Integer draw_id) {
        this.draw_id = draw_id;
    }

    public Integer getKeno_draw_id() {
        return keno_draw_id;
    }

    public void setKeno_draw_id(Integer keno_draw_id) {
        this.keno_draw_id = keno_draw_id;
    }

    public BigDecimal getRound_money_1() {
        return round_money_1;
    }

    public void setRound_money_1(BigDecimal round_money_1) {
        this.round_money_1 = round_money_1;
    }

    public BigDecimal getRound_money_2() {
        return round_money_2;
    }

    public void setRound_money_2(BigDecimal round_money_2) {
        this.round_money_2 = round_money_2;
    }

    public BigDecimal getFill_prize_1() {
        return fill_prize_1;
    }

    public void setFill_prize_1(BigDecimal fill_prize_1) {
        this.fill_prize_1 = fill_prize_1;
    }

    public BigDecimal getFill_prize_2() {
        return fill_prize_2;
    }

    public void setFill_prize_2(BigDecimal fill_prize_2) {
        this.fill_prize_2 = fill_prize_2;
    }

    public BigDecimal getFill_prize_fix() {
        return fill_prize_fix;
    }

    public void setFill_prize_fix(BigDecimal fill_prize_fix) {
        this.fill_prize_fix = fill_prize_fix;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
