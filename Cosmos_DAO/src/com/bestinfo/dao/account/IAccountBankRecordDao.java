package com.bestinfo.dao.account;

import com.bestinfo.bean.account.AccountBankRecord;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 资金账户-交款记录
 *
 * @author zyk
 */
public interface IAccountBankRecordDao {

    /**
     * 缴款查询（用于投注机）
     *
     * @param jdbcTemplate
     * @param accountId
     * @param bankSerial
     * @param accountDate
     * @param payDate
     * @param payMoney
     * @return
     * @throws Exception
     */
    public AccountBankRecord getRecordByNameSerialDateMoney(JdbcTemplate jdbcTemplate, String accountId,
            String bankSerial, String accountDate, String payDate, BigDecimal payMoney) throws Exception;

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
    public AccountBankRecord getRecordByAccNameSerialAccDateUndoMoney(JdbcTemplate jdbcTemplate,
            String accountId, String undoBankSerial, String bankSerial, String accountDate, BigDecimal undoMoney) throws Exception;

    /**
     * 时间段缴款查询列表
     *
     * @param jdbcTemplate
     * @param account_id
     * @param begin_time
     * @param end_time
     * @return
     */
    public List<AccountBankRecord> getAccountBankRecordByAccountIdAndTime(JdbcTemplate jdbcTemplate, String account_id, String begin_time, String end_time, int undo_mark);

}
