package com.bestinfo.bean.union;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 联合销售期统计表(T_UNION_DRAW_STATISTICS)
 *
 */
public class UnionDrawStat implements Serializable {

    private Integer game_id;// 游戏编号
    private Integer draw_id;// 总期号
    private BigDecimal sale_money = BigDecimal.ZERO;// 总投注额
    private Long sale_ticket_num;// 投注票数
    private Long sale_stake_num;// 投注注数
    private BigDecimal undo_money = BigDecimal.ZERO;// 注销金额
    private Long undo_ticket_num;// 注销票数
    private Long undo_stake_num;// 注销注数
    private BigDecimal cash_money = BigDecimal.ZERO;// 总兑奖额
    private Long cash_ticket_num;// 兑奖票数
    private Long cash_stake_num;// 兑奖注数
    private BigDecimal agent_fee_deduct = BigDecimal.ZERO;// 内扣代销费
    private BigDecimal agent_fee = BigDecimal.ZERO;// 结算代销费
    private BigDecimal cash_fee = BigDecimal.ZERO;// 兑奖代销费

    public Integer getGame_id() {
        return game_id;
    }

    public void setGame_id(Integer game_id) {
        this.game_id = game_id;
    }

    public Integer getDraw_id() {
        return draw_id;
    }

    public void setDraw_id(Integer draw_id) {
        this.draw_id = draw_id;
    }

    public BigDecimal getSale_money() {
        return sale_money;
    }

    public void setSale_money(BigDecimal sale_money) {
        this.sale_money = sale_money;
    }

    public Long getSale_ticket_num() {
        return sale_ticket_num;
    }

    public void setSale_ticket_num(Long sale_ticket_num) {
        this.sale_ticket_num = sale_ticket_num;
    }

    public Long getSale_stake_num() {
        return sale_stake_num;
    }

    public void setSale_stake_num(Long sale_stake_num) {
        this.sale_stake_num = sale_stake_num;
    }

    public BigDecimal getUndo_money() {
        return undo_money;
    }

    public void setUndo_money(BigDecimal undo_money) {
        this.undo_money = undo_money;
    }

    public Long getUndo_ticket_num() {
        return undo_ticket_num;
    }

    public void setUndo_ticket_num(Long undo_ticket_num) {
        this.undo_ticket_num = undo_ticket_num;
    }

    public Long getUndo_stake_num() {
        return undo_stake_num;
    }

    public void setUndo_stake_num(Long undo_stake_num) {
        this.undo_stake_num = undo_stake_num;
    }

    public BigDecimal getCash_money() {
        return cash_money;
    }

    public void setCash_money(BigDecimal cash_money) {
        this.cash_money = cash_money;
    }

    public Long getCash_ticket_num() {
        return cash_ticket_num;
    }

    public void setCash_ticket_num(Long cash_ticket_num) {
        this.cash_ticket_num = cash_ticket_num;
    }

    public Long getCash_stake_num() {
        return cash_stake_num;
    }

    public void setCash_stake_num(Long cash_stake_num) {
        this.cash_stake_num = cash_stake_num;
    }

    public BigDecimal getAgent_fee_deduct() {
        return agent_fee_deduct;
    }

    public void setAgent_fee_deduct(BigDecimal agent_fee_deduct) {
        this.agent_fee_deduct = agent_fee_deduct;
    }

    public BigDecimal getAgent_fee() {
        return agent_fee;
    }

    public void setAgent_fee(BigDecimal agent_fee) {
        this.agent_fee = agent_fee;
    }

    public BigDecimal getCash_fee() {
        return cash_fee;
    }

    public void setCash_fee(BigDecimal cash_fee) {
        this.cash_fee = cash_fee;
    }

}
