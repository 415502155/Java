/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.impl.h5.monitor;

import com.bestinfo.bean.h5.monitor.HRealTimePay;
import com.bestinfo.dao.h5.monitor.IMonitorRealTimePayDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator
 */
@Repository
public class MonitorRealTimePayDaoImpl extends BaseDaoImpl implements IMonitorRealTimePayDao {

    private static final Logger logger = Logger.getLogger(MonitorRealTimePayDaoImpl.class);

    private static final String GET_CITY_NAME = "SELECT * FROM ("
            + "    SELECT -999 AS CITY_ID, '全省' AS CITY_NAME, 0 AS CITY_ORDER"
            + "    FROM DUAL"
            + "    WHERE ( ? = 0 OR ? <> 0 AND  0 = ? )"
            + "    UNION"
            + "    SELECT CITY_ID,CITY_NAME,DECODE(CITY_ID,0,888,CITY_ID) AS CITY_ORDER"
            + "    FROM T_CITY_INFO"
            + "    WHERE ( ? = 0 OR ? <> 0 AND  CITY_ID = ? )"
            + ")"
            + " ORDER BY CITY_ORDER ASC";

    private static final String GET_LOGIN_CITY_ID = "select city_id as login_city from t_sys_user where user_name=?";

    /**
     * 各地市缴款账户数据
     *
     * @param jdbcTemplate
     * @param hcityInfo
     * @return
     */
    @Override
    public List<HRealTimePay> listBankPaymentMonitor(JdbcTemplate jdbcTemplate, HRealTimePay hcityInfo) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT B.TERMINAL_ID, B.STATION_NAME, E.TERMINAL_TYPE_NAME,"
            + "    CASE B.TMN_CASH_DEDUCT WHEN 1 THEN '开通' ELSE '未开通' END AS TMN_CASH_DEDUCT,"
            + "    A.DEFAULT_CREDIT,A.ACC_BALANCE,A.DEFAULT_CREDIT+A.ACC_BALANCE AS DEFAULT_CREDIT1,' ' AS JG ,"
            + "    B.STATION_PHONE,B.OWNER_PHONE,A.SUM_HAND_IN AS INCOME_MONEY "
            + "FROM T_TMN_INFO B , T_CITY_INFO C , T_TERMINAL_TYPE E, T_ACCOUNT_INFO A "
            + "WHERE A.ACCOUNT_ID = B.ACCOUNT_ID"
            + "    AND B.CITY_ID = C.CITY_ID"
            + "    AND (( ? = -9999 AND (C.CITY_ID = ? or -999 = ?))"
            + "        OR (? <> -9999 AND ( ? = 0 OR B.TERMINAL_ID LIKE ? || '%' )))"
            + "    AND E.TERMINAL_TYPE= B.TERMINAL_TYPE ");
        switch (hcityInfo.getOrder()) {
            case 0:
                sql.append(" ORDER BY DEFAULT_CREDIT1 ASC");
                break;
            case 1:
                sql.append("ORDER BY B.TERMINAL_ID ASC");
                break;
        }
        return queryForList(jdbcTemplate, sql.toString(), new Object[]{
            hcityInfo.getTerminal_id(), hcityInfo.getCity_id(), hcityInfo.getCity_id(),
            hcityInfo.getTerminal_id(), hcityInfo.getLogin_city(), hcityInfo.getTerminal_id()
        }, HRealTimePay.class);
    }

    /**
     * 城市名
     *
     * @param jdbcTemplate
     * @param login_city
     * @return
     */
    @Override
    public List<HRealTimePay> getCityName(JdbcTemplate jdbcTemplate, Integer login_city) {
        return queryForList(jdbcTemplate, GET_CITY_NAME, new Object[]{login_city, login_city, login_city,
            login_city, login_city, login_city}, HRealTimePay.class);
    }

    
    /**
     * 获取登录用户所在地
     *
     * @param jdbcTemplate
     * @param user_name
     * @return
     */
    @Override
    public Integer getLoginCityId(JdbcTemplate jdbcTemplate, String user_name) {
        return queryForInteger(jdbcTemplate, GET_LOGIN_CITY_ID, new Object[]{user_name});
    }
}
