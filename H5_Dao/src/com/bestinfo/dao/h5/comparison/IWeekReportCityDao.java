/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.dao.h5.comparison;

import com.bestinfo.bean.h5.comparison.HTicketType;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 票种同比增长
 * 
 * @author Administrator
 */
public interface IWeekReportCityDao {
    
    //年销量
    List<HTicketType> getWeekReportCityYear(JdbcTemplate jdbcTemplate, Integer yearId);
    
    //周销量
    List<HTicketType> getWeekReportCityWeek(JdbcTemplate jdbcTemplate, Integer yearId, Integer weekId);
}
