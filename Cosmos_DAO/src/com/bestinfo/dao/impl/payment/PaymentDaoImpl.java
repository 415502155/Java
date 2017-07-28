/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.impl.payment;

import com.bestinfo.bean.payment.EpayOrder;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.payment.IPaymentDao;
import com.bestinfo.define.returncode.TerminalResultCode;
import com.bestinfo.util.TimeUtil;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author
 */
@Repository
public class PaymentDaoImpl extends BaseDaoImpl implements IPaymentDao {

    private static final Logger loger = Logger.getLogger(PaymentDaoImpl.class);

    private static final String UPDATE_WECHATPAY_ORDER_STATUS = "UPDATE T_Epay_Order\n"
            + "SET out_trade_no     = ?,\n"
            + "  bank_type          = ?,\n"
            + "  fee_type           = ?,\n"
            + "  time_end           = to_date(?,'yyyy-mm-dd hh24:mi:ss'),\n"
            + "  trade_state        = ?,\n"
            + "  Web_Transaction_Id = ?,\n"
            + "  return_code        = ?,\n"
            + "  return_msg         = ?,\n"
            + "  settle_amt         = ?,\n"
            + "  settle_ccy_type    = ?,\n"
            + "  settle_date        = to_date(?,'yyyyMMdd')\n"
            + "WHERE out_order_no   = ?";

    private static final String SELECT_INFO_FROM_EPAY_ORDER = "SELECT out_trade_no,bank_type,fee_type,time_end,trade_state,Web_Transaction_Id,return_code,return_msg,settle_amt,settle_ccy_type,settle_date,operator_id, total_fee, order_time from t_epay_order where out_order_no = ?";

    @Override
    public int pWeChatPayInfoSave(JdbcTemplate jdbcTemplate, final String byname, final String out_order_no, final String terminal_id, final int amount, final BigDecimal total_fee, final BigDecimal autual_fee, final Date order_time, final int business, final String account_id, final String auth_code, final String list_games, final String operator, final String funds_source, final String ccyType) {
        try {
            String sql = "{call P_WECHATPAY_INFO_RECORD(?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?, ?)}";
            Integer errorcode = (Integer) jdbcTemplate.execute(sql, new CallableStatementCallback() {
                @Override
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                    cs.setString(1, byname);
                    cs.setString(2, out_order_no);
                    cs.setString(3, terminal_id);
                    cs.setInt(4, amount);
                    cs.setBigDecimal(5, total_fee);
                    cs.setBigDecimal(6, autual_fee);
                    Timestamp sqlDate = new Timestamp(order_time.getTime());
                    cs.setTimestamp(7, sqlDate);
                    cs.setInt(8, business);
                    cs.setString(9, account_id);
                    cs.setString(10, auth_code);
                    cs.setString(11, list_games);
                    cs.setString(12, operator);
                    cs.setString(13, funds_source);
                    cs.setString(14, ccyType);
                    cs.registerOutParameter(15, Types.INTEGER);
                    cs.registerOutParameter(16, Types.VARCHAR);
                    cs.execute();
                    int errorcode = cs.getInt(15);
                    if (errorcode != 0) {
                        logger.error("wechatpay info into db error,errorCode:" + errorcode + ",errorMsg:" + cs.getString(16)
                                + ",order_time:" + TimeUtil.formatDate_YMDT(order_time));
                    }
                    return Integer.valueOf(errorcode);
                }
            });
            return errorcode;
        } catch (DataAccessException e) {
            loger.error("", e);
            return TerminalResultCode.daoExceError;
        }
    }

    @Override
    public int updateWeChatPayInfo(JdbcTemplate jdbcTemplate, String out_trade_no, String bank_type, String fee_type, String time_end, String trade_state, String web_transaction_id, String return_code, String return_msg, String out_order_no, String settle_amt, String settle_ccy_type, String settle_date) {
        int result;
        try {
            EpayOrder payOrder = new EpayOrder();
            try {
                payOrder = getInfoFromEpayOrder(jdbcTemplate, out_order_no);
            } catch (Exception e) {
                loger.error("getInfoFromEpayOrder: " + e.getMessage());
                return -1;
            }

            if (out_trade_no == null || out_trade_no.equals("") == true) {
                out_trade_no = payOrder.getOut_trade_no();
            }

            if (fee_type == null || fee_type.equals("") == true) {
                fee_type = payOrder.getFee_type();
            }

            if (time_end == null || time_end.equals("") == true) {
                java.util.Date tmpDate = payOrder.getTime_end();
                if (tmpDate == null) {
                    time_end = null;
                } else {
                    time_end = TimeUtil.formatDate_YMDT(payOrder.getTime_end());
                }
            }

//            logger.info("time_end: " + time_end);
            if (trade_state == null || trade_state.equals("") == true) {
                trade_state = payOrder.getTrade_state();
            }

            if (web_transaction_id == null || web_transaction_id.equals("") == true) {
                web_transaction_id = payOrder.getWeb_transaction_id();
            }

            if (settle_date == null || settle_date.equals("") == true) {
                java.util.Date tmpDate = payOrder.getSettle_date();
                if (tmpDate == null) {
                    settle_date = null;
                } else {
                    settle_date = TimeUtil.formatDate_YMDT(payOrder.getSettle_date());
                }
            }

//            logger.info("settle_date: " + settle_date);
            result = jdbcTemplate.update(UPDATE_WECHATPAY_ORDER_STATUS, new Object[]{
                out_trade_no,
                bank_type,
                fee_type,
                time_end,
                trade_state,
                web_transaction_id,
                return_code,
                return_msg,
                settle_amt,
                settle_ccy_type,
                settle_date,
                out_order_no
            });
        } catch (DataAccessException e) {
            loger.error("wechatpay out_order_no:" + out_order_no, e);
            SQLException sqle = (SQLException) e.getCause();
            loger.error("Error code: " + sqle.getErrorCode());
            loger.error("SQL state: " + sqle.getSQLState());
            result = -2;
        }
//        loger.info("before update:return_code is " + return_code);
        return result;
    }

    @Override
    public EpayOrder getInfoFromEpayOrder(JdbcTemplate jdbcTemplate, String outOrderNo) throws Exception {
        return this.queryForObject(jdbcTemplate, SELECT_INFO_FROM_EPAY_ORDER, new Object[]{outOrderNo}, EpayOrder.class);
    }

    @Override
    public int pWeChatPayInfoStat(JdbcTemplate jdbcTemplate, final String input_json_str) {
        try {
            String sql = "{call P_WECHATPAY_INFO_STAT(?,?,?)}";
            Integer errorcode = (Integer) jdbcTemplate.execute(sql, new CallableStatementCallback() {
                @Override
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                    cs.setString(1, input_json_str);
                    cs.registerOutParameter(2, Types.INTEGER);
                    cs.registerOutParameter(3, Types.VARCHAR);
                    cs.execute();
                    int errorcode = cs.getInt(2);
                    if (errorcode != 0) {
                        logger.error("wechatpay info into db error,errorCode:" + errorcode + ",errorMsg:" + cs.getString(3));
                    }
                    return Integer.valueOf(errorcode);
                }
            });
            return errorcode;
        } catch (DataAccessException e) {
            loger.error("", e);
            return TerminalResultCode.daoExceError;
        }
    }

}
