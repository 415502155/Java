package com.bestinfo.dao.impl.encoding;

import com.bestinfo.bean.encoding.BankCode;
import com.bestinfo.dao.encoding.IBankCode;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 银行编码表信息
 *
 * @author user
 */
@Repository
public class BankCodeImpl extends BaseDaoImpl implements IBankCode {

    /**
     * 查询银行编码表数据列表sql
     */
    private static final String GET_BANKCODE_LIST = "select  bank_id,bank_name,work_status  from t_bank_code";
    /**
     * 根据银行编码表编号更新银行卡类型sql
     */
    private static final String UPDATE_BANKCODE = "update t_bank_code set bank_name = ?,work_status = ? where bank_id = ?";

    /**
     * 根据银行编号获取银行编码信息
     */
    private static final String GET_BANKCODE_BY_BANKID = "select bank_name, work_status from t_bank_code where bank_id = ? ";
    private static final String GET_BANKCODE_NUM_BY_BANKID = "select count(*) from t_bank_code where bank_id = ? ";

    /**
     * 查询银行编码表的数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    @Override
    public List<BankCode> selectBankCode(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, GET_BANKCODE_LIST, null, BankCode.class);
    }

    /**
     * 修改银行编码表数据
     *
     * @param jdbcTemplate
     * @param bc
     * @return
     */
    @Override
    public int updateBankCode(JdbcTemplate jdbcTemplate, BankCode bc) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_BANKCODE, new Object[]{bc.getBank_name(), bc.getWork_status(), bc.getBank_id()});
        } catch (DataAccessException e) {
            logger.error("Bank_id:"+bc.getBank_id(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

    /**
     * 根据银行编码查询数据量
     *
     * @param jdbcTemplate
     * @param bankId
     * @return
     */
    @Override
    public int selectBankCodeNumById(JdbcTemplate jdbcTemplate, String bankId) {
        return this.queryForInteger(jdbcTemplate, GET_BANKCODE_NUM_BY_BANKID, new Object[]{bankId});
    }
}
