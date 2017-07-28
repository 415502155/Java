/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.dao.exhibition;

import com.bestinfo.bean.exhibition.LuckyNo;
import com.bestinfo.bean.exhibition.WeekReportSales;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *提供各区县本年累计销量
 * @author Administrator
 */
public interface IWeekreportSalesDao {
    public List<WeekReportSales> findWeekReportSaleses(JdbcTemplate jdbcTemplate, String cityId, String yearId);
    
    public List<WeekReportSales> findWeekReportSalesesNearWeek(JdbcTemplate jdbcTemplate, String cityId, String type,String weekId);
    
    public List<LuckyNo> findLuckyNo(JdbcTemplate jdbcTemplate);
}
