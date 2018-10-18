package com.bestinfo.bean.h5.comparison;

import com.bestinfo.bean.h5.monitor.*;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author Administrator
 */
public class StatProvince {
    private Integer year_id;        //年份
    private BigDecimal sale_money ;    //年销售量
    private BigInteger welfare_money;  //公益金

    /**
     * @return the year_id
     */
    public Integer getYear_id() {
        return year_id;
    }

    /**
     * @param year_id the year_id to set
     */
    public void setYear_id(Integer year_id) {
        this.year_id = year_id;
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
     * @return the welfare_money
     */
    public BigInteger getWelfare_money() {
        return welfare_money;
    }

    /**
     * @param welfare_money the welfare_money to set
     */
    public void setWelfare_money(BigInteger welfare_money) {
        this.welfare_money = welfare_money;
    }
}
