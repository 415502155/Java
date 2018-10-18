package com.bestinfo.bean.encoding;

import java.io.Serializable;

/**
 * 账户类型
 *
 * @author user
 */
public class AccountType implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -158277632269850628L;
	private Integer account_type_id;  //账户类型编号
    private String account_type_name; //账户类型名称
    private Integer work_status; //工作状态

    /**
     * @return the account_type_id
     */
    public Integer getAccount_type_id() {
        return account_type_id;
    }

    /**
     * @param account_type_id the account_type_id to set
     */
    public void setAccount_type_id(Integer account_type_id) {
        this.account_type_id = account_type_id;
    }

    /**
     * @return the account_type_name
     */
    public String getAccount_type_name() {
        return account_type_name;
    }

    /**
     * @param account_type_name the account_type_name to set
     */
    public void setAccount_type_name(String account_type_name) {
        this.account_type_name = account_type_name;
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
