package com.bestinfo.dao.impl.account;

import com.bestinfo.bean.account.AccountBankfileDetail;
import com.bestinfo.dao.account.IAccountBankfileDetailDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 资金账户-对账文件明细信息
 *
 * @author zyk
 */
@Repository
public class AccountBankfileDetailDaoImpl extends BaseDaoImpl implements IAccountBankfileDetailDao {

    /**
     * 插入资金账户-对账文件明细信息
     */
    private static final String INSERT_ACCOUNT_BANKFILE_DETAIL = "insert into t_account_bankfile_detail (account_id, pay_money, pay_time, account_date, account_no, bank_id, pay_type, bank_serial_no, terminal_id, refile_time) values(?, ?, ?, ?, ?,  ?, ?, ?, ?, ?)";

    private static final String insert_update = "merge into t_account_bankfile_detail dest using (select ? bank_id, ? bank_serial_no from dual) src on (dest.bank_id = src.bank_id and dest.bank_serial_no = src.bank_serial_no) when matched then update set account_id = ?,pay_money = ?,pay_time = ?,account_date = ?,account_no = ?,pay_type = ?,terminal_id = ?,refile_time = ? when not matched then insert (account_id, pay_money, pay_time, account_date, account_no, bank_id, pay_type, bank_serial_no, terminal_id, refile_time) values(?, ?, ?, ?, ?,  ?, ?, ?, ?, ?)";
    
    /**
     * 批量插入对账文件明细
     *
     * @param jdbcTemplate
     * @param abds
     * @throws Exception
     */
    @Override
    public void insertAccountBankfileDetail(JdbcTemplate jdbcTemplate, final List<AccountBankfileDetail> abds) {
        final int count = abds.size();
        jdbcTemplate.batchUpdate(INSERT_ACCOUNT_BANKFILE_DETAIL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                AccountBankfileDetail abd = abds.get(i);
                ps.setString(1, abd.getAccount_id());
                ps.setBigDecimal(2, abd.getPay_money());
                ps.setTimestamp(3, new Timestamp(abd.getPay_time().getTime()));
                ps.setTimestamp(4, new Timestamp(abd.getAccount_date().getTime()));
                ps.setString(5, abd.getAccount_no());
                ps.setString(6, abd.getBank_id());
                ps.setInt(7, abd.getPay_type());
                ps.setString(8, abd.getBank_serial_no());
                ps.setInt(9, abd.getTerminal_id());
                ps.setTimestamp(10, new Timestamp(abd.getRefile_time().getTime()));
            }

            @Override
            public int getBatchSize() {
                return count;
            }

        });
    }
    
    @Override
    public void insert_updateAccountBankfileDetail(JdbcTemplate jdbcTemplate, final List<AccountBankfileDetail> abds) {
        final int count = abds.size();
        jdbcTemplate.batchUpdate(insert_update, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                AccountBankfileDetail abd = abds.get(i);
                int j = 1;
                ps.setString(j++, abd.getBank_id());
                ps.setString(j++, abd.getBank_serial_no());
                
                ps.setString(j++, abd.getAccount_id());
                ps.setBigDecimal(j++, abd.getPay_money());
                ps.setTimestamp(j++, new Timestamp(abd.getPay_time().getTime()));
                ps.setTimestamp(j++, new Timestamp(abd.getAccount_date().getTime()));
                ps.setString(j++, abd.getAccount_no());
                ps.setInt(j++, abd.getPay_type());
                ps.setInt(j++, abd.getTerminal_id());
                ps.setTimestamp(j++, new Timestamp(abd.getRefile_time().getTime()));
                
                ps.setString(j++, abd.getAccount_id());
                ps.setBigDecimal(j++, abd.getPay_money());
                ps.setTimestamp(j++, new Timestamp(abd.getPay_time().getTime()));
                ps.setTimestamp(j++, new Timestamp(abd.getAccount_date().getTime()));
                ps.setString(j++, abd.getAccount_no());
                ps.setString(j++, abd.getBank_id());
                ps.setInt(j++, abd.getPay_type());
                ps.setString(j++, abd.getBank_serial_no());
                ps.setInt(j++, abd.getTerminal_id());
                ps.setTimestamp(j++, new Timestamp(abd.getRefile_time().getTime()));
            }

            @Override
            public int getBatchSize() {
                return count;
            }

        });
    }

}
