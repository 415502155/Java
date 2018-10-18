package com.bestinfo.bean.terminal;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 站点等级及代销费比例(T_agent_rate_rank)
 *
 * @author hhhh
 */
public class AgentRateRank implements Serializable {

    private static final long serialVersionUID = 5588676111226159638L;

    /**
     * 站点等级
     */
    private Integer station_rank;
    /**
     * 等级名称
     */
    private String rank_name;
    /**
     * 代销费比例
     */
    private BigDecimal agent_fee_rate;
    /**
     * 使用状态
     */
    private Integer work_status;
    /**
     * 兑奖代销费
     */
    private BigDecimal cash_fee_rate;

    public Integer getStation_rank() {
        return station_rank;
    }

    public void setStation_rank(Integer station_rank) {
        this.station_rank = station_rank;
    }

    public String getRank_name() {
        return rank_name;
    }

    public void setRank_name(String rank_name) {
        this.rank_name = rank_name;
    }

    public Integer getWork_status() {
        return work_status;
    }

    public void setWork_status(Integer work_status) {
        this.work_status = work_status;
    }

    public BigDecimal getAgent_fee_rate() {
        return agent_fee_rate;
    }

    public void setAgent_fee_rate(BigDecimal agent_fee_rate) {
        this.agent_fee_rate = agent_fee_rate;
    }

    public BigDecimal getCash_fee_rate() {
        return cash_fee_rate;
    }

    public void setCash_fee_rate(BigDecimal cash_fee_rate) {
        this.cash_fee_rate = cash_fee_rate;
    }

}
