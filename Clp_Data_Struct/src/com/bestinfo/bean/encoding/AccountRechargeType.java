package com.bestinfo.bean.encoding;

import java.io.Serializable;

/**
 * 账户充值方式
 *
 * @author user
 */
public class AccountRechargeType implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1759494848091097426L;
	private Integer recharge_type;  //充值类型编号
    private String rechage_name; //充值类型名称
    private Integer work_status; //工作状态

    /**
     * @return the recharge_type
     */
    public Integer getRecharge_type() {
        return recharge_type;
    }

    /**
     * @param recharge_type the recharge_type to set
     */
    public void setRecharge_type(Integer recharge_type) {
        this.recharge_type = recharge_type;
    }

    /**
     * @return the rechage_name
     */
    public String getRechage_name() {
        return rechage_name;
    }

    /**
     * @param rechage_name the rechage_name to set
     */
    public void setRechage_name(String rechage_name) {
        this.rechage_name = rechage_name;
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
}
