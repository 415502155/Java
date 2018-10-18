package com.bestinfo.bean.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author hhhh
 */
public class AccountInfo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4919199262011938583L;
	private String account_id;//账户编号
    private String account_name;//账户名称
    private String owner_name;//户主名
    private Integer account_type;//账户类型编号
    private BigDecimal acc_balance;//账户余额
    private Integer settlement_type;//资金结算类型
    private BigDecimal sum_bet_money;//累计购彩金额
    private BigDecimal sum_undo_money;//累计注销撤单金额
    private BigDecimal sum_prize_money;//累计中奖金额
    private BigDecimal sum_hand_in;//累计充值金额
    private BigDecimal sum_hand_out;//累计抹帐金额
    private BigDecimal sum_pay_out;//累计提取金额
    private BigDecimal sum_agent_fee;//累计销售代销费
    private BigDecimal sum_cash_fee;//累计兑奖代销费
    private Integer prize_method;//奖金处理方式
    private String bank_id;//银行编号
    private String bound_card;//绑定银行卡号
    private String acc_note;//备注
    private BigDecimal prize_balance;//奖金余额
    private BigDecimal freeze_money;//冻结金额
    private Integer account_status;//账户状态
    private BigDecimal default_credit;//默认信誉额度
    private Date credit_time;//临时信用额度有效时间
    private BigDecimal temp_credit;//临时信誉度
    private Long account_serial_no;//当前账户流水号
    private String credit_time_str;//临时信用额度有效时间字符串

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public Integer getAccount_type() {
        return account_type;
    }

    public void setAccount_type(Integer account_type) {
        this.account_type = account_type;
    }

    public BigDecimal getAcc_balance() {
        return acc_balance;
    }

    public void setAcc_balance(BigDecimal acc_balance) {
        this.acc_balance = acc_balance;
    }

    public Integer getSettlement_type() {
        return settlement_type;
    }

    public void setSettlement_type(Integer settlement_type) {
        this.settlement_type = settlement_type;
    }

    public BigDecimal getSum_bet_money() {
        return sum_bet_money;
    }

    public void setSum_bet_money(BigDecimal sum_bet_money) {
        this.sum_bet_money = sum_bet_money;
    }

    public BigDecimal getSum_undo_money() {
        return sum_undo_money;
    }

    public void setSum_undo_money(BigDecimal sum_undo_money) {
        this.sum_undo_money = sum_undo_money;
    }

    public BigDecimal getSum_prize_money() {
        return sum_prize_money;
    }

    public void setSum_prize_money(BigDecimal sum_prize_money) {
        this.sum_prize_money = sum_prize_money;
    }

    public BigDecimal getSum_hand_in() {
        return sum_hand_in;
    }

    public void setSum_hand_in(BigDecimal sum_hand_in) {
        this.sum_hand_in = sum_hand_in;
    }

    public BigDecimal getSum_hand_out() {
        return sum_hand_out;
    }

    public void setSum_hand_out(BigDecimal sum_hand_out) {
        this.sum_hand_out = sum_hand_out;
    }

    public BigDecimal getSum_pay_out() {
        return sum_pay_out;
    }

    public void setSum_pay_out(BigDecimal sum_pay_out) {
        this.sum_pay_out = sum_pay_out;
    }

    public Integer getPrize_method() {
        return prize_method;
    }

    public void setPrize_method(Integer prize_method) {
        this.prize_method = prize_method;
    }

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public String getBound_card() {
        return bound_card;
    }

    public void setBound_card(String bound_card) {
        this.bound_card = bound_card;
    }

    public String getAcc_note() {
        return acc_note;
    }

    public void setAcc_note(String acc_note) {
        this.acc_note = acc_note;
    }

    public BigDecimal getPrize_balance() {
        return prize_balance;
    }

    public void setPrize_balance(BigDecimal prize_balance) {
        this.prize_balance = prize_balance;
    }

    public BigDecimal getFreeze_money() {
        return freeze_money;
    }

    public void setFreeze_money(BigDecimal freeze_money) {
        this.freeze_money = freeze_money;
    }

    public Integer getAccount_status() {
        return account_status;
    }

    public void setAccount_status(Integer account_status) {
        this.account_status = account_status;
    }

    public BigDecimal getDefault_credit() {
        return default_credit;
    }

    public void setDefault_credit(BigDecimal default_credit) {
        this.default_credit = default_credit;
    }

    public Date getCredit_time() {
        return credit_time;
    }

    public void setCredit_time(Date credit_time) {
        this.credit_time = credit_time;
    }

    public BigDecimal getTemp_credit() {
        return temp_credit;
    }

    public void setTemp_credit(BigDecimal temp_credit) {
        this.temp_credit = temp_credit;
    }

    public Long getAccount_serial_no() {
        return account_serial_no;
    }

    public void setAccount_serial_no(Long account_serial_no) {
        this.account_serial_no = account_serial_no;
    }

   

    public BigDecimal getSum_agent_fee() {
        return sum_agent_fee;
    }

    public void setSum_agent_fee(BigDecimal sum_agent_fee) {
        this.sum_agent_fee = sum_agent_fee;
    }

    public BigDecimal getSum_cash_fee() {
        return sum_cash_fee;
    }

    public void setSum_cash_fee(BigDecimal sum_cash_fee) {
        this.sum_cash_fee = sum_cash_fee;
    }

    public String getCredit_time_str() {
        return credit_time_str;
    }

    public void setCredit_time_str(String credit_time_str) {
        this.credit_time_str = credit_time_str;
    }

}
