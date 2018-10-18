package com.bestinfo.bean.game;

import java.io.Serializable;

/**
 * 游戏抽奖规则
 *
 * @author chenliping
 */
public class GameRaffleRule implements Serializable {

    /**
     *
     */
    private static long serialVersionUID = -374318849106500715L;

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }
    private Integer game_id;//游戏编号
    private Integer open_id;//开奖次数
    private Integer play_id;//玩法编号
    private Integer class_id;//奖级编号
    private Integer rule_id;//规则编号
    private Integer basic_num;//基本号码个数
    private Integer special_num;//特别号码个数
    private Integer blue_num;//蓝球号码个数
    private Integer no_order;//号码顺序
    private Integer match_pos;//匹配位置
    private Integer match_near;//匹配相邻
    private String raffle_method;//抽奖方法
    private Integer work_status;//工作状态
    private String relate_class; //兼中奖级
    //展示游戏名称中文名
    private String game_name;  //游戏名称
    private String play_name;  //玩法名称
    private String class_name;//奖级名称

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
     * @return the play_id
     */
    public Integer getPlay_id() {
        return play_id;
    }

    /**
     * @param play_id the play_id to set
     */
    public void setPlay_id(Integer play_id) {
        this.play_id = play_id;
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
     * @return the rule_id
     */
    public Integer getRule_id() {
        return rule_id;
    }

    /**
     * @param rule_id the rule_id to set
     */
    public void setRule_id(Integer rule_id) {
        this.rule_id = rule_id;
    }

    /**
     * @return the basic_num
     */
    public Integer getBasic_num() {
        return basic_num;
    }

    /**
     * @param basic_num the basic_num to set
     */
    public void setBasic_num(Integer basic_num) {
        this.basic_num = basic_num;
    }

    /**
     * @return the special_num
     */
    public Integer getSpecial_num() {
        return special_num;
    }

    /**
     * @param special_num the special_num to set
     */
    public void setSpecial_num(Integer special_num) {
        this.special_num = special_num;
    }

    /**
     * @return the blue_num
     */
    public Integer getBlue_num() {
        return blue_num;
    }

    /**
     * @param blue_num the blue_num to set
     */
    public void setBlue_num(Integer blue_num) {
        this.blue_num = blue_num;
    }

    /**
     * @return the no_order
     */
    public Integer getNo_order() {
        return no_order;
    }

    /**
     * @param no_order the no_order to set
     */
    public void setNo_order(Integer no_order) {
        this.no_order = no_order;
    }

    /**
     * @return the match_pos
     */
    public Integer getMatch_pos() {
        return match_pos;
    }

    /**
     * @param match_pos the match_pos to set
     */
    public void setMatch_pos(Integer match_pos) {
        this.match_pos = match_pos;
    }

    /**
     * @return the match_near
     */
    public Integer getMatch_near() {
        return match_near;
    }

    /**
     * @param match_near the match_near to set
     */
    public void setMatch_near(Integer match_near) {
        this.match_near = match_near;
    }

    /**
     * @return the raffle_method
     */
    public String getRaffle_method() {
        return raffle_method;
    }

    /**
     * @param raffle_method the raffle_method to set
     */
    public void setRaffle_method(String raffle_method) {
        this.raffle_method = raffle_method;
    }

    /**
     * @return the work_status
     */
    public Integer getWork_status() {
        return work_status;
    }

    /**
     * @param work_status the work_status to set
     */
    public void setWork_status(Integer work_status) {
        this.work_status = work_status;
    }

    /**
     * @return the relate_class
     */
    public String getRelate_class() {
        return relate_class;
    }

    /**
     * @param relate_class the relate_class to set
     */
    public void setRelate_class(String relate_class) {
        this.relate_class = relate_class;
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
     * @return the play_name
     */
    public String getPlay_name() {
        return play_name;
    }

    /**
     * @param play_name the play_name to set
     */
    public void setPlay_name(String play_name) {
        this.play_name = play_name;
    }

    /**
     * @return the class_name
     */
    public String getClass_name() {
        return class_name;
    }

    /**
     * @param class_name the class_name to set
     */
    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

 
}
