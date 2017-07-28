package com.bestinfo.bean.game;

import java.io.Serializable;

/**
 * 游戏--开奖步骤配置(T_open_configure_step)
 *
 * @author yangyuefu
 */
public class OpenConfigureStep implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8046034852716246110L;
	private Integer open_configure_id;//开奖过程配置编号
    private Integer step_id;//步骤编号
    private Integer process_status;//期状态
    private Integer next_process_status;//下一状态
    private Integer work_status;//使用状态

    public Integer getOpen_configure_id() {
        return open_configure_id;
    }

    public void setOpen_configure_id(Integer open_configure_id) {
        this.open_configure_id = open_configure_id;
    }

    public Integer getStep_id() {
        return step_id;
    }

    public void setStep_id(Integer step_id) {
        this.step_id = step_id;
    }

    public Integer getProcess_status() {
        return process_status;
    }

    public void setProcess_status(Integer process_status) {
        this.process_status = process_status;
    }

    public Integer getNext_process_status() {
        return next_process_status;
    }

    public void setNext_process_status(Integer next_process_status) {
        this.next_process_status = next_process_status;
    }

    public Integer getWork_status() {
        return work_status;
    }

    public void setWork_status(Integer work_status) {
        this.work_status = work_status;
    }

}
