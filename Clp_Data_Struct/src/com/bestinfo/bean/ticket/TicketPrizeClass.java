package com.bestinfo.bean.ticket;

import java.io.Serializable;

/**
 * 彩票-中奖数据奖级明细(T_ticket_prize_class)
 *
 * @author lvchangrong
 */
public class TicketPrizeClass implements Serializable {

    private static final long serialVersionUID = 5691191075459140911L;
    private String cipher ;  //彩票密码
    private Integer sequence_id; //序列号
    private Integer game_id;   //游戏编号
    private Integer draw_id ; //总期号
    private Integer keno_draw_id ; //快开期号
    private Integer  terminal_id ; //投注机号
    private Integer serial_no ; //彩票流水号
    private Integer open_id; // 开奖次数
    private Integer stake_no; //注行号
    private Integer class_id; //奖级编号
    private Integer prize_num; //中奖注数
    private Integer total_prize; //总奖金

    /**
     * @return the cipher
     */
    public String getCipher() {
        return cipher;
    }

    /**
     * @param cipher the cipher to set
     */
    public void setCipher(String cipher) {
        this.cipher = cipher;
    }

    /**
     * @return the sequence_id
     */
    public Integer getSequence_id() {
        return sequence_id;
    }

    /**
     * @param sequence_id the sequence_id to set
     */
    public void setSequence_id(Integer sequence_id) {
        this.sequence_id = sequence_id;
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
     * @return the serial_no
     */
    public Integer getSerial_no() {
        return serial_no;
    }

    /**
     * @param serial_no the serial_no to set
     */
    public void setSerial_no(Integer serial_no) {
        this.serial_no = serial_no;
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
     * @return the stake_no
     */
    public Integer getStake_no() {
        return stake_no;
    }

    /**
     * @param stake_no the stake_no to set
     */
    public void setStake_no(Integer stake_no) {
        this.stake_no = stake_no;
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

    /**
     * @return the total_prize
     */
    public Integer getTotal_prize() {
        return total_prize;
    }

    /**
     * @param total_prize the total_prize to set
     */
    public void setTotal_prize(Integer total_prize) {
        this.total_prize = total_prize;
    }
}
