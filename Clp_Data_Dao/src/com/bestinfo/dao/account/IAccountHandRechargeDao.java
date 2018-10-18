package com.bestinfo.dao.account;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 投注机手工充值
 *
 * @author shange
 */
public interface IAccountHandRechargeDao {

    /**
     * 手工充值
     *
     * @param jdbcTemplate
     * @param terminalId
     * @param rechargeMoney
     * @return
     */
    public int procHandRecharge(JdbcTemplate jdbcTemplate, String terminalId, double rechargeMoney);
}
