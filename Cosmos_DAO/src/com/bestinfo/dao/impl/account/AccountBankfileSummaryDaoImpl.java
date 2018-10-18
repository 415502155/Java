package com.bestinfo.dao.impl.account;

import com.bestinfo.bean.account.AccountBankfileSummary;
import com.bestinfo.dao.account.IAccountBankfileSummaryDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.util.Date;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 资金账户-对账文件汇总信息
 *
 * @author zyk
 */
@Repository
public class AccountBankfileSummaryDaoImpl extends BaseDaoImpl implements IAccountBankfileSummaryDao {

    /**
     * 插入资金账户-对账文件汇总信息
     */
    private static final String INSERT_ACCOUNT_BANKFILE_SUMMARY = "insert into t_account_bankfile_summary  (bank_id, account_date, total_record, total_money, file_sign, check_mark) values(?, ?, ?, ?, ?,  ?)";

    private static final String delete_account_bankfile_summary = "delete from t_account_bankfile_summary where bank_id=? and account_date=?";

    private static final String selectCheckMark = "select check_mark from t_account_bankfile_summary where bank_id=? and account_date=?";

    /**
     * 新增资金账户-对账文件汇总信息
     *
     * @param jdbcTemplate
     * @param abs
     * @return
     * @throws Exception
     */
    @Override
    public int insertAccountBankfileSummary(JdbcTemplate jdbcTemplate, AccountBankfileSummary abs) {
        try {
            return jdbcTemplate.update(INSERT_ACCOUNT_BANKFILE_SUMMARY, new Object[]{abs.getBank_id(), abs.getAccount_date(), abs.getTotal_record(), abs.getTotal_money(), abs.getFile_sign(), abs.getCheck_mark()});
        } catch (Exception e) {
            logger.error("", e);
            return -1;
        }
    }

    @Override
    public int deleteAccountBankfileSummary(JdbcTemplate jdbcTemplate, AccountBankfileSummary abs) {
        try {
            return jdbcTemplate.update(delete_account_bankfile_summary, new Object[]{abs.getBank_id(), abs.getAccount_date()});
        } catch (Exception e) {
            logger.error("", e);
            return -1;
        }
    }

    @Override
    public int selectCheckMark(JdbcTemplate jdbcTemplate, String bankid, Date accountdate) {
        try {
            return jdbcTemplate.queryForObject(selectCheckMark, new Object[]{bankid, accountdate}, Integer.class).intValue();
        } catch (Exception e) {
            logger.error("", e);
            return -1;
        }
    }

}
