package com.bestinfo.dao.impl.account;

import com.bestinfo.dao.account.IAccountHandRechargeDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 投注机手工充值dao层实现
 *
 * @author shange
 */
@Repository
public class AccountHandRechargeImpl extends BaseDaoImpl implements IAccountHandRechargeDao {

    /**
     * 投注机手工充值
     *
     * @param jdbcTemplate
     * @param terminalId
     * @param rechargeMoney
     * @return
     */
    @Override
    public int procHandRecharge(JdbcTemplate jdbcTemplate, final String terminalId, final double rechargeMoney) {
        String proc = "{call recharge(?,?,?,?)}";
        Integer flag = (Integer) jdbcTemplate.execute(proc, new CallableStatementCallback() {
            @Override
            public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                cs.setInt(1, Integer.parseInt(terminalId));
                cs.setDouble(2, rechargeMoney);
                cs.registerOutParameter(3, Types.INTEGER);
                cs.registerOutParameter(4, Types.VARCHAR);
                cs.execute();
                int code = cs.getInt(3);
                String msg = cs.getString(4);
                if (code != 0) {
                    logger.error("terminal handRecharge error,code:" + code + "\n" + msg + ", terminalId:" + terminalId);
                }
                return Integer.valueOf(code);
            }
        });
        return flag;
    }

}
