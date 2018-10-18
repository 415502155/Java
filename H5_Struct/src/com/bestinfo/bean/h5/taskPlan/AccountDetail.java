package com.bestinfo.bean.h5.taskPlan;

import java.math.BigDecimal;

/**
 *
 * @author Administrator
 */
public class AccountDetail {
    
    private BigDecimal allmoney; //上一分钟销售总额
    private Integer sales_count; //上一分钟交易数

    /**
     * @return the allmoney
     */
    public BigDecimal getAllmoney() {
        return allmoney;
    }

    /**
     * @param allmoney the allmoney to set
     */
    public void setAllmoney(BigDecimal allmoney) {
        this.allmoney = allmoney;
    }

    /**
     * @return the sales_count
     */
    public Integer getSales_count() {
        return sales_count;
    }

    /**
     * @param sales_count the sales_count to set
     */
    public void setSales_count(Integer sales_count) {
        this.sales_count = sales_count;
    }
}
