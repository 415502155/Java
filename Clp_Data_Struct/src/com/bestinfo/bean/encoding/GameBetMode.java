package com.bestinfo.bean.encoding;

import java.io.Serializable;

/**
 * 游戏投注方式表
 *
 * @author hhhh
 */
public class GameBetMode implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5540364117264720245L;
	private Integer bet_mode;//投注方式
    private String bet_mode_name;//方式名称
    private Integer work_status;//工作状态

    public Integer getBet_mode() {
        return bet_mode;
    }

    public void setBet_mode(Integer bet_mode) {
        this.bet_mode = bet_mode;
    }

    public String getBet_mode_name() {
        return bet_mode_name;
    }

    public void setBet_mode_name(String bet_mode_name) {
        this.bet_mode_name = bet_mode_name;
    }

    public Integer getWork_status() {
        return work_status;
    }

    public void setWork_status(Integer work_status) {
        this.work_status = work_status;
    }
    
}
