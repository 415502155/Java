package com.bestinfo.bean.encoding;

import java.io.Serializable;

/**
 * 银行编码表
 *
 * @author user
 */
public class BankCode implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2396730010464162189L;
	private String bank_id;  //银行编码
    private String bank_name; //银行名称
    private Integer work_status; //工作状态

    /**
     * @return the bank_id
     */
    public String getBank_id() {
        return bank_id;
    }

    /**
     * @param bank_id the bank_id to set
     */
    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    /**
     * @return the bank_name
     */
    public String getBank_name() {
        return bank_name;
    }

    /**
     * @param bank_name the bank_name to set
     */
    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
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
