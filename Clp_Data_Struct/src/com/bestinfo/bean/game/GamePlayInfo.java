package com.bestinfo.bean.game;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 游戏玩法表
 *
 * @author yangyuefu
 */
public class GamePlayInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8277680200585159220L;
    private Integer game_id;//游戏编号
    private Integer play_id;//玩法编号
    private String play_name;//玩法名称
    private String play_type;//玩法类型
    private BigDecimal stakes_price;//单注金额
    private Integer max_multiple;//单注最大倍数
    private Integer bet_no_num;//投注号码个数
    private Integer bet_min_no;//最小号码
    private Integer bet_max_no;//最大号码
    private Integer blue_no_num;//蓝球个数
    private Integer blue_min_no;//最小蓝球
    private Integer blue_max_no;//最大蓝球
    private Integer no_repeat;//号码可重复
    private Integer no_order;//号码有序 1有序--排列;0无序-组合
    private Integer sign_num;//符号个数
    private BigDecimal prize_proportion;//奖金和比例
    private Integer work_status;//工作状态
    //展示游戏名称中文名
    private String game_name;  //游戏名称

    //生成XML文件所用
    private List<PlayBetMode> pbmList;//投注方式列表
    private List<GameSignInfo> gsiList;//投注符号列表

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

    public String getPlay_name() {
        return play_name;
    }

    public void setPlay_name(String play_name) {
        this.play_name = play_name;
    }

    public String getPlay_type() {
        return play_type;
    }

    public void setPlay_type(String play_type) {
        this.play_type = play_type;
    }

    public Integer getMax_multiple() {
        return max_multiple;
    }

    public void setMax_multiple(Integer max_multiple) {
        this.max_multiple = max_multiple;
    }

    public Integer getBet_no_num() {
        return bet_no_num;
    }

    public void setBet_no_num(Integer bet_no_num) {
        this.bet_no_num = bet_no_num;
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

    public Integer getBlue_no_num() {
        return blue_no_num;
    }

    public void setBlue_no_num(Integer blue_no_num) {
        this.blue_no_num = blue_no_num;
    }

    public Integer getBlue_min_no() {
        return blue_min_no;
    }

    public void setBlue_min_no(Integer blue_min_no) {
        this.blue_min_no = blue_min_no;
    }

    public Integer getBlue_max_no() {
        return blue_max_no;
    }

    public void setBlue_max_no(Integer blue_max_no) {
        this.blue_max_no = blue_max_no;
    }

    public Integer getNo_repeat() {
        return no_repeat;
    }

    public void setNo_repeat(Integer no_repeat) {
        this.no_repeat = no_repeat;
    }

    public Integer getNo_order() {
        return no_order;
    }

    public void setNo_order(Integer no_order) {
        this.no_order = no_order;
    }

    public Integer getSign_num() {
        return sign_num;
    }

    public void setSign_num(Integer sign_num) {
        this.sign_num = sign_num;
    }

    public Integer getWork_status() {
        return work_status;
    }

    public void setWork_status(Integer work_status) {
        this.work_status = work_status;
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

    public List<PlayBetMode> getPbmList() {
        return pbmList;
    }

    public void setPbmList(List<PlayBetMode> pbmList) {
        this.pbmList = pbmList;
    }

    public List<GameSignInfo> getGsiList() {
        return gsiList;
    }

    public void setGsiList(List<GameSignInfo> gsiList) {
        this.gsiList = gsiList;
    }

    public BigDecimal getStakes_price() {
        return stakes_price;
    }

    public void setStakes_price(BigDecimal stakes_price) {
        this.stakes_price = stakes_price;
    }

    public BigDecimal getPrize_proportion() {
        return prize_proportion;
    }

    public void setPrize_proportion(BigDecimal prize_proportion) {
        this.prize_proportion = prize_proportion;
    }

}
