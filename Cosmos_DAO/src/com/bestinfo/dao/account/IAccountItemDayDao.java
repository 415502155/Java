package com.bestinfo.dao.account;

import org.springframework.jdbc.core.JdbcTemplate;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 查T_ACCOUNT_ITEM_DAY表
 *
 * @author YangRong
 */
public interface IAccountItemDayDao {

    /**
     * 根据交易类型,账户,起始终止日期查收入总和
     *
     * @param jdbcTemplate
     * @param accountId
     * @param tradeType
     * @param beginDay
     * @param endDay
     * @return
     */
    public Double getIncomeMoneySumByTradeTypeAndIdAndDay(JdbcTemplate jdbcTemplate, String accountId, Integer tradeType, String beginDay, String endDay);

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
    public Double getPayoutMoneySumByTradeTypeAndIdAndDay(JdbcTemplate jdbcTemplate, String accountId, Integer tradeType, String beginDay, String endDay);

}
