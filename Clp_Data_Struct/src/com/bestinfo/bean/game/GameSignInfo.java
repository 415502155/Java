package com.bestinfo.bean.game;

import java.io.Serializable;

/**
 * 投注符号表
 *
 * @author hhhh
 */
public class GameSignInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4319796412333524470L;
    private Integer game_id;//游戏编号
    private Integer play_id;//玩法编号
    private Integer draw_id;//期号
    private Integer sign_id;//符号编号
    private String bet_sign;//符号名称
    private String no_string;//符号内容
    private Integer no_num;//内容号码个数
    private Integer no_len;//号码串个数
    private Integer bet_min_no;//开奖最小号码
    private Integer bet_max_no;//开奖最大号码
    private Integer no_diff;//数字递增差
    private Integer work_status;//工作状态
    private Integer bet_area;  //投注区

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

    public Integer getSign_id() {
        return sign_id;
    }

    public void setSign_id(Integer sign_id) {
        this.sign_id = sign_id;
    }

    public String getBet_sign() {
        return bet_sign;
    }

    public void setBet_sign(String bet_sign) {
        this.bet_sign = bet_sign;
    }

    public String getNo_string() {
        return no_string;
    }

    public void setNo_string(String no_string) {
        this.no_string = no_string;
    }

    public Integer getNo_num() {
        return no_num;
    }

    public void setNo_num(Integer no_num) {
        this.no_num = no_num;
    }

    public Integer getNo_len() {
        return no_len;
    }

    public void setNo_len(Integer no_len) {
        this.no_len = no_len;
    }

    public Integer getBet_min_no() {
        return bet_min_no;
    }

    public void setBet_min_no(Integer bet_min_no) {
        this.bet_min_no = bet_min_no;
    }

    public Integer getBet_max_no() {
        return bet_max_no;
    }

    public void setBet_max_no(Integer bet_max_no) {
        this.bet_max_no = bet_max_no;
    }

    public Integer getNo_diff() {
        return no_diff;
    }

    public void setNo_diff(Integer no_diff) {
        this.no_diff = no_diff;
    }

    public Integer getWork_status() {
        return work_status;
    }

    public void setWork_status(Integer work_status) {
        this.work_status = work_status;
    }

    /**
     * @return the bet_area
     */
    public Integer getBet_area() {
        return bet_area;
    }

    /**
     * @param bet_area the bet_area to set
     */
    public void setBet_area(Integer bet_area) {
        this.bet_area = bet_area;
    }

}
