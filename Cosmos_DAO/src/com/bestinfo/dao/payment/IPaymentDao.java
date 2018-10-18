/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.payment;

import com.bestinfo.bean.payment.EpayOrder;
import java.math.BigDecimal;
import java.sql.Date;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author
 */
public interface IPaymentDao {

    public int pWeChatPayInfoSave(JdbcTemplate jdbcTemplate, String byname, String out_order_no, String terminal_id,
            int amount, BigDecimal total_fee, BigDecimal autual_fee, Date order_time, int business, String account_id,
            String auth_code, String list_games, String operator, String funds_source, String ccyType);

    public int updateWeChatPayInfo(JdbcTemplate jdbcTemplate, String out_trade_no, String bank_type, String fee_type, String time_end, String trade_state, String web_transaction_id, String return_code, String return_msg, String out_order_no, String settle_amt, String settle_ccy_type, String settle_date);

    public EpayOrder getInfoFromEpayOrder(JdbcTemplate jdbcTemplate, String outOrderNo) throws Exception;

    public int pWeChatPayInfoStat(JdbcTemplate jdbcTemplate, String input_json_str);
}
