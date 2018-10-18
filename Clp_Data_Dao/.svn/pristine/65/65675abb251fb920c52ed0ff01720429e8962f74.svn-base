package com.bestinfo.dao.impl.stat;

import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.stat.IBalanceQueryDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 查询站点余额
 *
 * @author YangRong
 */
@Repository
public class BalanceQueryDaoImpl extends BaseDaoImpl implements IBalanceQueryDao {

    /**
     * 查询余额
     */
    private static final String SELECT_BALANCE = " select acc_balance from t_account_info where trim(account_id) = ? ";

    /**
     * 根据账户编号获取站点余额信息
     *
     * @param jdbcTemplate
     * @param accountId
     * @return
     */
    @Override
    public Double getBalanceByAccountId(JdbcTemplate jdbcTemplate, String accountId) {
        try {
            return (Double) jdbcTemplate.queryForObject(SELECT_BALANCE, new Object[]{accountId.trim()}, Double.class);
        } catch (org.springframework.dao.DataAccessException e) {
            return null;
        }
    }

}
