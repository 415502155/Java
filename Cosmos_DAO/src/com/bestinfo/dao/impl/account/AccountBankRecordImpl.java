package com.bestinfo.dao.impl.account;

import com.bestinfo.bean.account.AccountBankRecord;
import com.bestinfo.dao.account.IAccountBankRecordDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 资金账户-缴款记录
 *
 * @author hhhh
 */
@Repository
public class AccountBankRecordImpl extends BaseDaoImpl implements IAccountBankRecordDao {

    /**
     * 缴款查询
     */
    private static final String getRecordByNameSerialDateMoney = "select b.account_name, a.bank_serial_no, a.account_no, a.pay_type, a.account_date, a.pay_time, a.pay_money from T_account_bank_record a, T_account_info b where a.account_id = b.account_id and trim(b.account_id) = ? and a.bank_serial_no = ? and to_char(a.account_date,'yyyyMMdd') = ? and to_char(a.pay_time, 'yyyy-MM-dd') = ? and a.pay_money = ?";

    /**
     * 抹账查询
     */
    private static final String getRecordByAccNameSerialAccDateUndoMoney = "select a.account_no, c.trade_time as pay_time from T_account_bank_record a, T_account_info b, t_account_detail c where a.account_id = b.account_id and a.account_id = c.account_id and trim(b.account_id) = ? and a.undo_bank_serial_no = ? and c.bank_serial_no = ? and a.bank_serial_no = ? and to_char(a.account_date,'yyyyMMdd') = ? and a.pay_money = ?";
    /**
     * 缴款查询
     */
    private static final String getRecordByAccountIdandTime = " select  account_id,bank_id,trade_code,pay_money,pay_time,pay_type,account_date,account_no,bank_serial_no,undo_bank_serial_no,undo_mark,account_serial_no,sys_time,reserved  from T_account_bank_record where account_id=? and pay_time between to_date( ?,'yyyy-mm-dd hh24:mi:ss') and to_date( ?,'yyyy-mm-dd hh24:mi:ss') and undo_mark =?　　order by pay_time desc ";

    @Override
    public AccountBankRecord getRecordByNameSerialDateMoney(JdbcTemplate jdbcTemplate, String accountId, String bankSerial, String accountDate, String payDate, BigDecimal payMoney) throws Exception {
        return this.queryForObject(jdbcTemplate, getRecordByNameSerialDateMoney, new Object[]{accountId, bankSerial, accountDate, payDate, payMoney}, AccountBankRecord.class);
    }

    /**
     * 抹账查询
     *
     * @param jdbcTemplate
     * @param accountId
     * @param undoBankSerial
     * @param bankSerial
     * @param accountDate
     * @param undoMoney
     * @return
     * @throws Exception
     */
    @Override
    public AccountBankRecord getRecordByAccNameSerialAccDateUndoMoney(JdbcTemplate jdbcTemplate, String accountId, String undoBankSerial, String bankSerial, String accountDate, BigDecimal undoMoney) throws Exception {
        return this.queryForObject(jdbcTemplate, getRecordByAccNameSerialAccDateUndoMoney, new Object[]{accountId, undoBankSerial, undoBankSerial, bankSerial, accountDate, undoMoney}, AccountBankRecord.class);
    }

    /**
     * 时间段缴款查询列表
     *
     * @param jdbcTemplate
     * @param account_id
     * @param begin_time
     * @param end_time
     * @param undo_mark
     * @return
     */
    @Override
    public List<AccountBankRecord> getAccountBankRecordByAccountIdAndTime(JdbcTemplate jdbcTemplate, String account_id, String begin_time, String end_time, int undo_mark) {
        return this.queryForList(jdbcTemplate, getRecordByAccountIdandTime, new Object[]{account_id, begin_time, end_time, undo_mark}, AccountBankRecord.class);
    }

}
