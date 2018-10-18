/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.dao.h5.monitor;

import com.bestinfo.bean.h5.monitor.HMonitorArea;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 区域监控接口
 * @author Administrator
 */
public interface IMonitorAreaDao {
    //年销量：全年完成任务 完成度
    public List<HMonitorArea> getYearSales(JdbcTemplate jdbcTemplate, Integer yearBegin, Integer yearEnd);
    public List<HMonitorArea> getDaySales(JdbcTemplate jdbcTemplate);
    //證常預警告警
    //獲取當前周  判斷是否是當前年的第一周
    public Integer getCurWeekNum(JdbcTemplate jdbcTemplate);
    //當前周上一周到當年最後一周之差
    public Integer getCurWeekAndMaxWeekSum(JdbcTemplate jdbcTemplate);
    //上周銷量
    public List<HMonitorArea> getLastWeekSales(JdbcTemplate jdbcTemplate);
    //截止上周總銷量
     public List<HMonitorArea> getLastWeekSumSales(JdbcTemplate jdbcTemplate);
    
}
