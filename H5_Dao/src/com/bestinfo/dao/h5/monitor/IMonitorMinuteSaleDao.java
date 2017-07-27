/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.dao.h5.monitor;

import java.util.Date;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Administrator
 */
public interface IMonitorMinuteSaleDao {
    public int insertMinuteSale(JdbcTemplate jdbcTemplate,Date stat_time, Integer sale_money, Integer sale_number);
}
