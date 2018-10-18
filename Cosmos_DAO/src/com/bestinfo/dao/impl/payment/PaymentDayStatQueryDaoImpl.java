/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.impl.payment;

import com.bestinfo.bean.payment.EpayTmnDateStat;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.payment.IPaymentDayStatQueryDao;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author
 */
@Repository
public class PaymentDayStatQueryDaoImpl extends BaseDaoImpl implements IPaymentDayStatQueryDao {

    private static final Logger loger = Logger.getLogger(PaymentDayStatQueryDaoImpl.class);

    private static final String QUERY_PAYMENT_DAY_STAT = "select operator_id, byname, funds_source, sale_money, undo_money from t_epay_tmn_date_stat where terminal_id = ? and operator_date between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd')";

    @Override
    public List<EpayTmnDateStat> getEpayTmnDateStats(JdbcTemplate jdbcTemplate, String beginTime, String endTime, int terminalId) {
        return this.queryForList(jdbcTemplate, QUERY_PAYMENT_DAY_STAT, new Object[]{terminalId,beginTime,endTime}, EpayTmnDateStat.class);
    }

}
