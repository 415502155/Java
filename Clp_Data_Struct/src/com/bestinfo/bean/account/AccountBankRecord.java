package com.bestinfo.bean.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金账户交款记录表
 *
 * @author zyk
 */
public class AccountBankRecord implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5706911900363301398L;
	private String account_id;            //账户编号   
    private String bank_id;               //银行编码
    private String trade_code;            //交易类型
    private BigDecimal pay_money;         //交款金额
    private Date pay_time;                //交款时间
    private Integer pay_type;              //交款类型
    private Date account_date;            //账务日期
    private String account_no;            //交款帐号
    private String bank_serial_no;        //银行流水
    private String undo_bank_serial_no;   //抹帐银行流水
    private Integer undo_mark;                //注销标记
    private Long account_serial_no;       //账户流水
    private Date sys_time;                //系统时间
    private String reserved;              //保留字段

    @Override
    public String toString() {
        return "AccountBankRecord{" + "account_id=" + account_id + ", bank_id=" + bank_id + ", trade_code=" + trade_code + ", pay_money=" + pay_money + ", pay_time=" + pay_time + ", pay_type=" + pay_type + ", account_date=" + account_date + ", account_no=" + account_no + ", bank_serial_no=" + bank_serial_no + ", undo_bank_serial_no=" + undo_bank_serial_no + ", undo_mark=" + undo_mark + ", account_serial_no=" + account_serial_no + ", sys_time=" + sys_time + ", reserved=" + reserved + '}';
    }


    /**
     * @return the account_id
     */
    public String getAccount_id() {
        return account_id;
    }

    /**
     * @param account_id the account_id to set
     */
    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

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
     * @return the trade_code
     */
    public String getTrade_code() {
        return trade_code;
    }

    /**
     * @param trade_code the trade_code to set
     */
    public void setTrade_code(String trade_code) {
        this.trade_code = trade_code;
    }

    /**
     * @return the pay_money
     */
    public BigDecimal getPay_money() {
        return pay_money;
    }

    /**
     * @param pay_money the pay_money to set
     */
    public void setPay_money(BigDecimal pay_money) {
        this.pay_money = pay_money;
    }

    /**
     * @return the pay_time
     */
    public Date getPay_time() {
        return pay_time;
    }

    /**
     * @param pay_time the pay_time to set
     */
    public void setPay_time(Date pay_time) {
        this.pay_time = pay_time;
    }

    /**
     * @return the pay_type
     */
    public Integer getPay_type() {
        return pay_type;
    }

    /**
     * @param pay_type the pay_type to set
     */
    public void setPay_type(Integer pay_type) {
        this.pay_type = pay_type;
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
     * @return the account_no
     */
    public String getAccount_no() {
        return account_no;
    }

    /**
     * @param account_no the account_no to set
     */
    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    /**
     * @return the bank_serial_no
     */
    public String getBank_serial_no() {
        return bank_serial_no;
    }

    /**
     * @param bank_serial_no the bank_serial_no to set
     */
    public void setBank_serial_no(String bank_serial_no) {
        this.bank_serial_no = bank_serial_no;
    }

    /**
     * @return the undo_bank_serial_no
     */
    public String getUndo_bank_serial_no() {
        return undo_bank_serial_no;
    }

    /**
     * @param undo_bank_serial_no the undo_bank_serial_no to set
     */
    public void setUndo_bank_serial_no(String undo_bank_serial_no) {
        this.undo_bank_serial_no = undo_bank_serial_no;
    }

    /**
     * @return the undo_mark
     */
    public Integer getUndo_mark() {
        return undo_mark;
    }

    /**
     * @param undo_mark the undo_mark to set
     */
    public void setUndo_mark(Integer undo_mark) {
        this.undo_mark = undo_mark;
    }

    /**
     * @return the account_serial_no
     */
    public Long getAccount_serial_no() {
        return account_serial_no;
    }

    /**
     * @param account_serial_no the account_serial_no to set
     */
    public void setAccount_serial_no(Long account_serial_no) {
        this.account_serial_no = account_serial_no;
    }

    /**
     * @return the sys_time
     */
    public Date getSys_time() {
        return sys_time;
    }

    /**
     * @param sys_time the sys_time to set
     */
    public void setSys_time(Date sys_time) {
        this.sys_time = sys_time;
    }

    /**
     * @return the reserved
     */
    public String getReserved() {
        return reserved;
    }

    /**
     * @param reserved the reserved to set
     */
    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

  

}
