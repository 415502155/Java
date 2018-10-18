/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.payment;

import com.bestinfo.bean.payment.EpayTmnDateStat;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 *
 * @author
 */
public interface IPaymentDayStatQueryDao {

    public List<EpayTmnDateStat> getEpayTmnDateStats(JdbcTemplate jdbcTemplate, String beginTime, String endTime, int terminalId);
}
