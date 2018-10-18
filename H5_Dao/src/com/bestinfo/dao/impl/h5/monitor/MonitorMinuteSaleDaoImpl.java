/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.dao.impl.h5.monitor;

import com.bestinfo.dao.h5.monitor.IMonitorMinuteSaleDao;
import java.util.Date;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 获取每分钟的销售量和销售笔数进行添加
 * @author Administrator
 */
@Repository
public class MonitorMinuteSaleDaoImpl implements IMonitorMinuteSaleDao{
    private static final Logger logger = Logger.getLogger(MonitorMinuteSaleDaoImpl.class);
    @Override
    public int insertMinuteSale(JdbcTemplate jdbcTemplate, Date stat_time, Integer sale_money, Integer sale_number) {
        int num=-1;
        String sSqlCmdFormat = "INSERT INTO t_monitor_minute_sales (stat_time, sale_money, sale_number) VALUES (?,?,?)";
        try {
            num = jdbcTemplate.update(sSqlCmdFormat, new Object[]{stat_time, sale_money, sale_number});
        } catch (Exception e) {
            logger.error("insert t_monitor_minute_sales info error,error info(" + e.getMessage() + ")!");
        }
        return num;
    }
    
}
