package com.bestinfo.dao.impl.encoding;

import com.bestinfo.bean.encoding.AccountType;
import com.bestinfo.dao.encoding.IAccountType;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 账户类型表
 *
 * @author user
 */
@Repository
public class AccountTypeImpl extends BaseDaoImpl implements IAccountType {

    /**
     * 查询账户类型数据列表sql
     */
    private static final String GET_ACCOUNTTYPE_LIST = "select  account_type_id,account_type_name,work_status  from t_account_type";
    /**
     * 根据账户类型编号更新账户类型sql
     */
    private static final String UPDATE_ACCOUNTTYPE = "update t_account_type set account_type_name = ?,work_status = ? where account_type_id = ?";

    /**
     * 查询账户类型的数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    @Override
    public List<AccountType> selectAccountType(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, GET_ACCOUNTTYPE_LIST, null, AccountType.class);
    }

    /**
     * 修改账户类型数据
     *
     * @param jdbcTemplate
     * @param at
     * @return
     */
    @Override
    public int updateAccountType(JdbcTemplate jdbcTemplate, AccountType at) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_ACCOUNTTYPE, new Object[]{ at.getAccount_type_name(), at.getWork_status(), at.getAccount_type_id() });
        } catch (DataAccessException e) {
            logger.error("Account_type_id:"+at.getAccount_type_id(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }
}
