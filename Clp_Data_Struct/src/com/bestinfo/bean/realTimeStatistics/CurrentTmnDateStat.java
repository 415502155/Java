package com.bestinfo.bean.realTimeStatistics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实时统计-投注机日统计
 *
 * @author lvchangrong
 */
public class CurrentTmnDateStat implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7934235031959913711L;
	private Integer terminal_id;//投注机号
    private Integer game_id;//游戏编号
    private Date operator_date;//操作时间
    private Integer operator_id;//操作用户
    private BigDecimal sale_money = BigDecimal.ZERO;//总投注额
    private Integer sale_ticket_num;//投注票数
    private Integer sale_stake_num;//投注注数
    private BigDecimal undo_money = BigDecimal.ZERO;//注销金额
    private Integer undo_ticket_num;//注销票数
    private Integer undo_stake_num;//注销注数
    private BigDecimal cash_money = BigDecimal.ZERO;//总兑奖额
    private Integer cash_ticket_num;  //兑奖票数
    private Integer cash_stake_num;  //兑奖注数
    private BigDecimal agent_fee_deduct = BigDecimal.ZERO;  //内扣代销费
    private BigDecimal cash_fee = BigDecimal.ZERO;  //兑奖代销费

    public Integer getGame_id() {
        return game_id;
    }

    public void setGame_id(Integer game_id) {
        this.game_id = game_id;
    }

    /**
     * @return the terminal_id
     */
    public Integer getTerminal_id() {
        return terminal_id;
    }

    /**
     * @param terminal_id the terminal_id to set
     */
    public void setTerminal_id(Integer terminal_id) {
        this.terminal_id = terminal_id;
    }

    /**
     * @return the operator_date
     */
    public Date getOperator_date() {
        return operator_date;
    }

    /**
     * @param operator_date the operator_date to set
     */
    public void setOperator_date(Date operator_date) {
        this.operator_date = operator_date;
    }

    /**
     * @return the sale_money
     */
    public BigDecimal getSale_money() {
        return sale_money;
    }

    /**
     * @param sale_money the sale_money to set
     */
    public void setSale_money(BigDecimal sale_money) {
        this.sale_money = sale_money;
    }

    /**
     * @return the sale_ticket_num
     */
    public Integer getSale_ticket_num() {
        return sale_ticket_num;
    }

    /**
     * @param sale_ticket_num the sale_ticket_num to set
     */
    public void setSale_ticket_num(Integer sale_ticket_num) {
        this.sale_ticket_num = sale_ticket_num;
    }

    /**
     * @return the sale_stake_num
     */
    public Integer getSale_stake_num() {
        return sale_stake_num;
    }

    /**
     * @param sale_stake_num the sale_stake_num to set
     */
    public void setSale_stake_num(Integer sale_stake_num) {
        this.sale_stake_num = sale_stake_num;
    }

    /**
     * @return the undo_money
     */
    public BigDecimal getUndo_money() {
        return undo_money;
    }

    /**
     * @param undo_money the undo_money to set
     */
    public void setUndo_money(BigDecimal undo_money) {
        this.undo_money = undo_money;
    }

    /**
     * @return the undo_ticket_num
     */
    public Integer getUndo_ticket_num() {
        return undo_ticket_num;
    }

    /**
     * @param undo_ticket_num the undo_ticket_num to set
     */
    public void setUndo_ticket_num(Integer undo_ticket_num) {
        this.undo_ticket_num = undo_ticket_num;
    }

    /**
     * @return the undo_stake_num
     */
    public Integer getUndo_stake_num() {
        return undo_stake_num;
    }

    /**
     * @param undo_stake_num the undo_stake_num to set
     */
    public void setUndo_stake_num(Integer undo_stake_num) {
        this.undo_stake_num = undo_stake_num;
    }

    /**
     * @return the cash_money
     */
    public BigDecimal getCash_money() {
        return cash_money;
    }

    /**
     * @param cash_money the cash_money to set
     */
    public void setCash_money(BigDecimal cash_money) {
        this.cash_money = cash_money;
    }

    /**
     * @return the cash_ticket_num
     */
    public Integer getCash_ticket_num() {
        return cash_ticket_num;
    }

    /**
     * @param cash_ticket_num the cash_ticket_num to set
     */
    public void setCash_ticket_num(Integer cash_ticket_num) {
        this.cash_ticket_num = cash_ticket_num;
    }

    /**
     * @return the cash_stake_num
     */
    public Integer getCash_stake_num() {
        return cash_stake_num;
    }

    /**
     * @param cash_stake_num the cash_stake_num to set
     */
    public void setCash_stake_num(Integer cash_stake_num) {
        this.cash_stake_num = cash_stake_num;
    }

    /**
     * @return the operator_id
     */
    public Integer getOperator_id() {
        return operator_id;
    }

    /**
     * @param operator_id the operator_id to set
     */
    public void setOperator_id(Integer operator_id) {
        this.operator_id = operator_id;
    }

    public BigDecimal getAgent_fee_deduct() {
        return agent_fee_deduct;
    }

    public void setAgent_fee_deduct(BigDecimal agent_fee_deduct) {
        this.agent_fee_deduct = agent_fee_deduct;
    }

    public BigDecimal getCash_fee() {
        return cash_fee;
    }

    public void setCash_fee(BigDecimal cash_fee) {
        this.cash_fee = cash_fee;
    }

}
