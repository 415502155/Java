package com.bestinfo.bean.h5.taskPlan;

import java.math.BigDecimal;
import java.util.Date;

public class MonitorMinuteSales {

    private Date stat_time; //统计时间
    private BigDecimal sale_money = BigDecimal.ZERO; //交易金额
    private Integer sale_number = 0; //交易笔数

    /**
     * @return the stat_time
     */
    public Date getStat_time() {
        return stat_time;
    }

    /**
     * @param stat_time the stat_time to set
     */
    public void setStat_time(Date stat_time) {
        this.stat_time = stat_time;
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
     * @return the sale_number
     */
    public Integer getSale_number() {
        return sale_number;
    }

    /**
     * @param sale_number the sale_number to set
     */
    public void setSale_number(Integer sale_number) {
        this.sale_number = sale_number;
    }

}
