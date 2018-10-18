/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.dao.h5.monitor;

import com.bestinfo.bean.h5.monitor.HRealTimePay;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 实时缴款账户监控 接口
 * 
 * @author Administrator
 */
public interface IMonitorRealTimePayDao {
    
    //各地市实时缴款账户数据
    public List<HRealTimePay> listBankPaymentMonitor(JdbcTemplate jdbcTemplate,HRealTimePay hcityInfo);
    
    //地市名称
    public List<HRealTimePay> getCityName(JdbcTemplate jdbcTemplate, Integer loginCity);
    
    //当前登录地市
    public Integer getLoginCityId(JdbcTemplate jdbcTemplate, String userName);
}
