package com.bestinfo.bean.encoding;

import java.io.Serializable;

/**
 *
 * @author hhhh
 */
public class KDrawProcessStatus implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5268046960043609276L;
	private Integer keno_process_status;//进度编号
    private String kdraw_status_name;//进度状态名称
    private Integer work_status;//工作状态

    public Integer getKeno_process_status() {
        return keno_process_status;
    }

    public void setKeno_process_status(Integer keno_process_status) {
        this.keno_process_status = keno_process_status;
    }

    public String getKdraw_status_name() {
        return kdraw_status_name;
    }

    public void setKdraw_status_name(String kdraw_status_name) {
        this.kdraw_status_name = kdraw_status_name;
    }

    public Integer getWork_status() {
        return work_status;
    }

    public void setWork_status(Integer work_status) {
        this.work_status = work_status;
    }

}
