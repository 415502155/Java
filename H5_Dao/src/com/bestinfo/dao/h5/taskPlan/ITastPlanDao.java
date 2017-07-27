/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.h5.taskPlan;

import com.bestinfo.bean.h5.taskPlan.MonitorMinuteSales;
import com.bestinfo.bean.h5.taskPlan.WeekReportSumSales;
import com.bestinfo.bean.h5.taskPlan.YearTaskPlan;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author zhen
 */
public interface ITastPlanDao {

    /**
     * 本年任务：全省年任务设置值。
     *
     * @param jdbcTemplate
     * @return
     */
    public List<YearTaskPlan> getProvinceYearPlanSalesMoney(JdbcTemplate jdbcTemplate);

    public List<WeekReportSumSales> getTwoWeekWeekReportSumSales(JdbcTemplate jdbcTemplate);

    public List<WeekReportSumSales> getNowYearWeekReportSumSales(JdbcTemplate jdbcTemplate);//截止上一周的总销量

    public List<WeekReportSumSales> getNowYearCurWeekReportSumSales(JdbcTemplate jdbcTemplate);//当前周销量

    public List<WeekReportSumSales> getNowYearCurWeekCurDrawReportSumSales(JdbcTemplate jdbcTemplate);//当前期总销量

    public int getWeekMaxSalesWeekReportSumSales(JdbcTemplate jdbcTemplate);

    public List<WeekReportSumSales> getYearSalesWeekSalesList(JdbcTemplate jdbcTemplate);

    public List<WeekReportSumSales> getWeekIdList(JdbcTemplate jdbcTemplate);

    public int getLastMinuteSales(JdbcTemplate jdbcTemplate);

    public int getLastMinuteSalesCount(JdbcTemplate jdbcTemplate);

    public WeekReportSumSales getHostoryMaxSales(JdbcTemplate jdbcTemplate);//获取历史周最大销量

    public List<MonitorMinuteSales> getHostoryOneMinuteMaxSales(JdbcTemplate jdbcTemplate);

    public List<MonitorMinuteSales> getHostoryOneMinuteMaxNumber(JdbcTemplate jdbcTemplate);

    public int getLastMinuteSales(JdbcTemplate jdbcTemplate, String beginTime, String endTime);

    public int getLastMinuteSalesCount(JdbcTemplate jdbcTemplate, String beginTime, String endTime);

    public MonitorMinuteSales getLastMinuteData(JdbcTemplate jdbcTemplate, String beginTime, String endTime);

    public MonitorMinuteSales getLastMinuteDataFromStat(JdbcTemplate jdbcTemplate);

    //年累计
    Long getYearSaleMoney(JdbcTemplate jdbcTemplate);

    //已结算未累计到周
    Long getSaleMoneyHaveClear(JdbcTemplate jdbcTemplate);

    //当前期销量
    Long getCurrentSaleMoney(JdbcTemplate jdbcTemplate);

    YearTaskPlan getHomeScreenMoney(JdbcTemplate jdbcTemplate);
}
