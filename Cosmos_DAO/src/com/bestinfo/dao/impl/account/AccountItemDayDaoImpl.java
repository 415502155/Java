package com.bestinfo.dao.impl.account;

import com.bestinfo.dao.account.IAccountItemDayDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.util.Date;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author YangRong
 */
@Repository
public class AccountItemDayDaoImpl extends BaseDaoImpl implements IAccountItemDayDao {

    private static final String GET_INCOME_MONEY_SUM_BY_TYPE_ID_DAY = "select  sum(income_money) from t_account_item_day where account_id=? and trade_type=?  and natural_day between to_date(?, 'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd') ";

    private static final String GET_PAYOUT_MONEY_SUM_BY_TYPE_ID_DAY = "select  sum(payout_money) from t_account_item_day where account_id=? and trade_type=?  and natural_day between to_date(?, 'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd') ";

    @Override
    public Double getIncomeMoneySumByTradeTypeAndIdAndDay(JdbcTemplate jdbcTemplate, String accountId, Integer tradeType, String beginDay, String endDay) {
        try {
            return (Double) jdbcTemplate.queryForObject(GET_INCOME_MONEY_SUM_BY_TYPE_ID_DAY, new Object[]{accountId, tradeType, beginDay, endDay}, Double.class);
        } catch (org.springframework.dao.DataAccessException e) {
            return null;
        }
    }

    /**
     * 根据交易类型,账户,起始终止日期查支出总和
     *
     * @param jdbcTemplate
     * @param accountId
     * @param tradeType
     * @param beginDay
     * @param endDay
     * @return
     */
    @Override
    public Double getPayoutMoneySumByTradeTypeAndIdAndDay(JdbcTemplate jdbcTemplate, String accountId, Integer tradeType, String beginDay, String endDay) {
        try {
            return (Double) jdbcTemplate.queryForObject(GET_PAYOUT_MONEY_SUM_BY_TYPE_ID_DAY, new Object[]{accountId, tradeType, beginDay, endDay}, Double.class);
        } catch (org.springframework.dao.DataAccessException e) {
            return null;
        }
    }

}
