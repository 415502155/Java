package com.bestinfo.bean.h5.comparison;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author Administrator
 */
public class StatCountry {
    private Integer year_id;          //年份
    private BigDecimal lottery_sale ; //福彩年销售量
    private BigInteger sport_sale;    //体彩年销售量

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
     * @return the lottery_sale
     */
    public BigDecimal getLottery_sale() {
        return lottery_sale;
    }

    /**
     * @param lottery_sale the lottery_sale to set
     */
    public void setLottery_sale(BigDecimal lottery_sale) {
        this.lottery_sale = lottery_sale;
    }

    /**
     * @return the sport_sale
     */
    public BigInteger getSport_sale() {
        return sport_sale;
    }

    /**
     * @param sport_sale the sport_sale to set
     */
    public void setSport_sale(BigInteger sport_sale) {
        this.sport_sale = sport_sale;
    }

}
