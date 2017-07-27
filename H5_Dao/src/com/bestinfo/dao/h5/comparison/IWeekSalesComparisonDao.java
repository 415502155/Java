/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.dao.h5.comparison;

import com.bestinfo.bean.h5.comparison.HWeekSalesComparison;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 年销量/周销量对比（时间维度销量对比）
 * 
 * @author Administrator
 */
public interface IWeekSalesComparisonDao {

    List<HWeekSalesComparison> getWeekSalesComparisonData(JdbcTemplate jdbcTemplate, Map<String, Integer> params);
    
}
