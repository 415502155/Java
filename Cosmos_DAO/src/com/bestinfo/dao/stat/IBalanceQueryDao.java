package com.bestinfo.dao.stat;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 查询站点余额
 *
 * @author YangRong
 */
public interface IBalanceQueryDao {

    /**
     * 站点余额查询
     *
     * @param jdbcTemplate
     * @param accountId
     * @return
     */
    public Double getBalanceByAccountId(JdbcTemplate jdbcTemplate, String accountId);

}
