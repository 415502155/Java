package com.bestinfo.bean.h5.taskPlan;

import java.math.BigDecimal;
import java.util.Date;

public class WeekReportSumSales {

    private Integer year_id; //年度
    private BigDecimal salemoney; //年销售额
    private Integer week_id; //周
    private BigDecimal week_salemoney;//周销售额
    private BigDecimal max_week_saleminey;//周最大销售额  a.begin_date,a.end_date
    private Date begin_date;
    private Date end_date;

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
     * @return the salemoney
     */
    public BigDecimal getSalemoney() {
        return salemoney;
    }

    /**
     * @param salemoney the salemoney to set
     */
    public void setSalemoney(BigDecimal salemoney) {
        this.salemoney = salemoney;
    }

    /**
     * @return the week_id
     */
    public Integer getWeek_id() {
        return week_id;
    }

    /**
     * @param week_id the week_id to set
     */
    public void setWeek_id(Integer week_id) {
        this.week_id = week_id;
    }

    /**
     * @return the week_salemoney
     */
    public BigDecimal getWeek_salemoney() {
        return week_salemoney;
    }

    /**
     * @param week_salemoney the week_salemoney to set
     */
    public void setWeek_salemoney(BigDecimal week_salemoney) {
        this.week_salemoney = week_salemoney;
    }

    /**
     * @return the max_week_saleminey
     */
    public BigDecimal getMax_week_saleminey() {
        return max_week_saleminey;
    }

    /**
     * @param max_week_saleminey the max_week_saleminey to set
     */
    public void setMax_week_saleminey(BigDecimal max_week_saleminey) {
        this.max_week_saleminey = max_week_saleminey;
    }

    /**
     * @return the begin_date
     */
    public Date getBegin_date() {
        return begin_date;
    }

    /**
     * @param begin_date the begin_date to set
     */
    public void setBegin_date(Date begin_date) {
        this.begin_date = begin_date;
    }

    /**
     * @return the end_date
     */
    public Date getEnd_date() {
        return end_date;
    }

    /**
     * @param end_date the end_date to set
     */
    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
    
}
