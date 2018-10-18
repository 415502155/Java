package com.bestinfo.bean.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金账户-对账文件汇总信息
 *
 * @author zyk
 */
public class AccountBankfileSummary implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3782116320788669103L;
	private String bank_id;       //银行编码  
    private Date account_date;    //账务日期  
    private Integer total_record; //成功总笔数
    private BigDecimal total_money;  //成功总金额
    private String file_sign;     //文件签名  
    private Integer check_mark;   //对账标记

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
     * @return the account_date
     */
    public Date getAccount_date() {
        return account_date;
    }

    /**
     * @param account_date the account_date to set
     */
    public void setAccount_date(Date account_date) {
        this.account_date = account_date;
    }

    /**
     * @return the total_record
     */
    public Integer getTotal_record() {
        return total_record;
    }

    /**
     * @param total_record the total_record to set
     */
    public void setTotal_record(Integer total_record) {
        this.total_record = total_record;
    }

    /**
     * @return the total_money
     */
    public BigDecimal getTotal_money() {
        return total_money;
    }

    /**
     * @param total_money the total_money to set
     */
    public void setTotal_money(BigDecimal total_money) {
        this.total_money = total_money;
    }

    /**
     * @return the file_sign
     */
    public String getFile_sign() {
        return file_sign;
    }

    /**
     * @param file_sign the file_sign to set
     */
    public void setFile_sign(String file_sign) {
        this.file_sign = file_sign;
    }

    /**
     * @return the check_mark
     */
    public Integer getCheck_mark() {
        return check_mark;
    }

    /**
     * @param check_mark the check_mark to set
     */
    public void setCheck_mark(Integer check_mark) {
        this.check_mark = check_mark;
    }
    
}
