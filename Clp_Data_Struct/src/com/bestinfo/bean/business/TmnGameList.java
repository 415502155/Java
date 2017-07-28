package com.bestinfo.bean.business;

import java.io.Serializable;

/**
 * 投注机支持游戏表
 *
 * @author hhhh
 */
public class TmnGameList implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 920965987217539036L;
	private Integer software_id;//软件类型
    private Integer game_id;//游戏编号
    private Integer work_status;//工作状态

    public Integer getSoftware_id() {
        return software_id;
    }

    public void setSoftware_id(Integer software_id) {
        this.software_id = software_id;
    }

    public Integer getGame_id() {
        return game_id;
    }

    public void setGame_id(Integer game_id) {
        this.game_id = game_id;
    }

    public Integer getWork_status() {
        return work_status;
    }

    public void setWork_status(Integer work_status) {
        this.work_status = work_status;
    }

}
