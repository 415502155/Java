package com.bestinfo.bean.game;

import java.io.Serializable;

/**
 * 游戏开奖次数
 *
 * @author chenliping
 */
public class GameMultiOpen implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4737609484410442133L;
	private Integer game_id;//游戏编号
    private Integer open_id;//开奖次数
    private String open_name;//开奖名称
    private Integer basic_ball_num;//开奖基本号码个数
    private Integer special_ball_num;//开奖特别号码个数
    private Integer open_blue_num;//开奖蓝球号码个数
    private Integer class_num;//奖级数量
    private Integer work_status;//工作状态
    // 显示游戏名称的中文名称
    private String game_name;

    /**
     * 游戏编号
     *
     * @return
     */
    public Integer getGame_id() {
        return game_id;
    }

    /**
     * 游戏编号
     *
     * @param game_id
     */
    public void setGame_id(Integer game_id) {
        this.game_id = game_id;
    }

    /**
     * 开奖次数
     *
     * @return
     */
    public Integer getOpen_id() {
        return open_id;
    }

    /**
     * 开奖次数
     *
     * @param open_id
     */
    public void setOpen_id(Integer open_id) {
        this.open_id = open_id;
    }

    /**
     * 开奖名称
     *
     * @return
     */
    public String getOpen_name() {
        return open_name;
    }

    /**
     * 开奖名称
     *
     * @param open_name
     */
    public void setOpen_name(String open_name) {
        this.open_name = open_name;
    }

    /**
     * 开奖基本号码个数
     *
     * @return
     */
    public Integer getBasic_ball_num() {
        return basic_ball_num;
    }

    /**
     * 开奖基本号码个数
     *
     * @param basic_ball_num
     */
    public void setBasic_ball_num(Integer basic_ball_num) {
        this.basic_ball_num = basic_ball_num;
    }

    /**
     * 开奖特别号码个数
     *
     * @return
     */
    public Integer getSpecial_ball_num() {
        return special_ball_num;
    }

    /**
     * 开奖特别号码个数
     *
     * @param special_ball_num
     */
    public void setSpecial_ball_num(Integer special_ball_num) {
        this.special_ball_num = special_ball_num;
    }

    /**
     * 中奖注数
     *
     * @return
     */
    public Integer getClass_num() {
        return class_num;
    }

    /**
     * 中奖注数
     *
     * @param class_num
     */
    public void setClass_num(Integer class_num) {
        this.class_num = class_num;
    }

    /**
     * 状态
     *
     * @return
     */
    public Integer getWork_status() {
        return work_status;
    }

    /**
     * 状态
     *
     * @param work_status
     */
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

    /**
     * @return the open_blue_num
     */
    public Integer getOpen_blue_num() {
        return open_blue_num;
    }

    /**
     * @param open_blue_num the open_blue_num to set
     */
    public void setOpen_blue_num(Integer open_blue_num) {
        this.open_blue_num = open_blue_num;
    }
}
