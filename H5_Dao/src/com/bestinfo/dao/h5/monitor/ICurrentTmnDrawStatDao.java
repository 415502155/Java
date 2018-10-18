/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.dao.h5.monitor;

import com.bestinfo.bean.h5.monitor.CurrentTmnDrawStat;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 各个彩种当期的销售额
 * @author Administrator
 */
public interface ICurrentTmnDrawStatDao {
    public List<CurrentTmnDrawStat> getCurrentTmnDrawStatSales(JdbcTemplate jdbcTemplate);
    
}
