package com.bestinfo.dao.impl.encoding;

import com.bestinfo.bean.encoding.AccountRechargeType;
import com.bestinfo.dao.encoding.IAccountRechargeType;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 账户充值方式
 *
 * @author lvchangrong
 */
@Repository
public class AccountRechargeTypeImpl extends BaseDaoImpl implements IAccountRechargeType {

    /**
     * 查询账户充值方式数据列表sql
     */
    private static final String GET_ACCOUNTRECHARGETYPE_LIST = "select  recharge_type,rechage_name,work_status from t_account_recharge_type";
    /**
     * 根据账户充值方式编号更新证件类型sql
     */
    private static final String UPDATE_ACCOUNTRECHARGETYPE = "update t_account_recharge_type set rechage_name = ?,work_status = ? where recharge_type = ?";

    /**
     * 查询账户充值方式的数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    @Override
    public List<AccountRechargeType> selectAccountRechargeType(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, GET_ACCOUNTRECHARGETYPE_LIST, null, AccountRechargeType.class);
    }

    /**
     * 修改账户充值方式数据
     *
     * @param jdbcTemplate
     * @param ar
     * @return
     */
    @Override
    public int updateAccountRechargeType(JdbcTemplate jdbcTemplate, AccountRechargeType ar) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_ACCOUNTRECHARGETYPE, new Object[]{ar.getRechage_name(), ar.getWork_status(), ar.getRecharge_type()});
        } catch (DataAccessException e) {
            logger.error("Recharge_type:"+ar.getRecharge_type(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }
}
