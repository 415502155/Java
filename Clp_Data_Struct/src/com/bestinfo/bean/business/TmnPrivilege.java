package com.bestinfo.bean.business;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 投注终端特权
 *
 * @author chenliping
 */
public class TmnPrivilege implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1889281777697099164L;
	private Integer terminal_id;//投注终端编号
    private Integer game_id;//游戏编号
    private Integer cur_draw_id;//当前期号
    private Integer sale_permit;//销售许可
    private Integer cash_permit;//兑奖许可
    private Integer undo_permit;//注销许可
    private Integer game_permit;//游戏许可
    private Integer presell_permit;//预售许可
    private Integer game_stop;//开奖封机
    private BigDecimal agent_fee_rate;//代销费比例
//    private BigDecimal cur_agent_rate;//内扣代销费比例
    private BigDecimal min_bet_money;//单票最小金额
    private BigDecimal max_bet_money;//单票最大金额
    private BigDecimal max_sales_money;//单期最大金额
    private BigDecimal cash_fee_rate;//兑奖代销费比例
    //终端特权修改页面显示所需字段
    private String game_name;//游戏名称
    private Integer isCheck;//复选框是否选中（0-选中 1-不选中）

    public BigDecimal getCash_fee_rate() {
        return cash_fee_rate;
    }

    public void setCash_fee_rate(BigDecimal cash_fee_rate) {
        this.cash_fee_rate = cash_fee_rate;
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
     * @return the game_id
     */
    public Integer getGame_id() {
        return game_id;
    }

    /**
     * @param game_id the game_id to set
     */
    public void setGame_id(Integer game_id) {
        this.game_id = game_id;
    }

    /**
     * @return the cur_draw_id
     */
    public Integer getCur_draw_id() {
        return cur_draw_id;
    }

    /**
     * @param cur_draw_id the cur_draw_id to set
     */
    public void setCur_draw_id(Integer cur_draw_id) {
        this.cur_draw_id = cur_draw_id;
    }

    /**
     * @return the sale_permit
     */
    public Integer getSale_permit() {
        return sale_permit;
    }

    /**
     * @param sale_permit the sale_permit to set
     */
    public void setSale_permit(Integer sale_permit) {
        this.sale_permit = sale_permit;
    }

    /**
     * @return the cash_permit
     */
    public Integer getCash_permit() {
        return cash_permit;
    }

    /**
     * @param cash_permit the cash_permit to set
     */
    public void setCash_permit(Integer cash_permit) {
        this.cash_permit = cash_permit;
    }

    /**
     * @return the game_permit
     */
    public Integer getGame_permit() {
        return game_permit;
    }

    /**
     * @param game_permit the game_permit to set
     */
    public void setGame_permit(Integer game_permit) {
        this.game_permit = game_permit;
    }

    /**
     * @return the presell_permit
     */
    public Integer getPresell_permit() {
        return presell_permit;
    }

    /**
     * @param presell_permit the presell_permit to set
     */
    public void setPresell_permit(Integer presell_permit) {
        this.presell_permit = presell_permit;
    }

    /**
     * @return the game_stop
     */
    public Integer getGame_stop() {
        return game_stop;
    }

    /**
     * @param game_stop the game_stop to set
     */
    public void setGame_stop(Integer game_stop) {
        this.game_stop = game_stop;
    }

    /**
     * @return the agent_fee_rate
     */
    public BigDecimal getAgent_fee_rate() {
        return agent_fee_rate;
    }

    /**
     * @param agent_fee_rate the agent_fee_rate to set
     */
    public void setAgent_fee_rate(BigDecimal agent_fee_rate) {
        this.agent_fee_rate = agent_fee_rate;
    }

//    /**
//     * @return the cur_agent_rate
//     */
//    public BigDecimal getCur_agent_rate() {
//        return cur_agent_rate;
//    }
//
//    /**
//     * @param cur_agent_rate the cur_agent_rate to set
//     */
//    public void setCur_agent_rate(BigDecimal cur_agent_rate) {
//        this.cur_agent_rate = cur_agent_rate;
//    }

    /**
     * @return the min_bet_money
     */
    public BigDecimal getMin_bet_money() {
        return min_bet_money;
    }

    /**
     * @param min_bet_money the min_bet_money to set
     */
    public void setMin_bet_money(BigDecimal min_bet_money) {
        this.min_bet_money = min_bet_money;
    }

    /**
     * @return the max_bet_money
     */
    public BigDecimal getMax_bet_money() {
        return max_bet_money;
    }

    /**
     * @param max_bet_money the max_bet_money to set
     */
    public void setMax_bet_money(BigDecimal max_bet_money) {
        this.max_bet_money = max_bet_money;
    }

    /**
     * @return the max_sales_money
     */
    public BigDecimal getMax_sales_money() {
        return max_sales_money;
    }

    /**
     * @param max_sales_money the max_sales_money to set
     */
    public void setMax_sales_money(BigDecimal max_sales_money) {
        this.max_sales_money = max_sales_money;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public Integer getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Integer isCheck) {
        this.isCheck = isCheck;
    }

    public Integer getUndo_permit() {
        return undo_permit;
    }

    public void setUndo_permit(Integer undo_permit) {
        this.undo_permit = undo_permit;
    }
    
}
