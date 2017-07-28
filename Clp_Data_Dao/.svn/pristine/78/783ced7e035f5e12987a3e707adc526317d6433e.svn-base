package com.bestinfo.dao.account;

import com.bestinfo.bean.account.AccountDetail;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 资金账户-变动明细信息
 *
 * @author hhhh
 */
public interface IAccountDetailDao {
    
    /**
     * 插入变动明细信息
     * @param jdbcTemplate
     * @param ad
     * @return 
     */
    public int insertAccountDetail(JdbcTemplate jdbcTemplate, AccountDetail ad);
    
    /**
     * 根据账户编号，获取指定时间段内的所有日缴费明细
     * @param jdbcTemplate
     * @param account_id
     * @param begin_time
     * @param end_time
     * @return 
     */
    public List<AccountDetail> getAccountDetailByAccountIdAndTime(JdbcTemplate jdbcTemplate,String account_id,String begin_time,String end_time);
    
    /**
     * 根据缴款日期、银行缴款流水号获取账户变动明细信息
     * @param jdbcTemplate
     * @param tradeDate
     * @param bankSerialNo
     * @param tradeType
     * @param accountName
     * @return 
     */
    public AccountDetail getAccountDetailByTradeDateAndBankSerial(JdbcTemplate jdbcTemplate, String tradeDate, String bankSerialNo, int tradeType, String accountName)throws Exception;
    
    /**
     * 根据银行缴款流水号获取账户变动明细信息
     * @param jdbcTemplate
     * @param bankSerialNo
     * @param accountName
     * @param tradeType
     * @return 
     */
    public AccountDetail getAccountDetailByBankSerial(JdbcTemplate jdbcTemplate, String bankSerialNo, String accountName, int tradeType)throws Exception;
    
    /**
     * 处理银行缴款协议
     * @param jdbcTemplate
     * @param accountName 账户名
     * @param bankAccount 缴款帐号
     * @param payMoney 缴款金额
     * @param payTime 交易时间
     * @param bankSerial 银行缴款流水号
     * @param payType 交款类型
     * @param operatorId 用户工号
     * @param accountDate 账务日期
     * @return 
     */
    public int handleBankPayment(JdbcTemplate jdbcTemplate,final String accountName,final String bankAccount, 
            final BigDecimal payMoney,final String payTime,final String bankSerial,final String payType, 
            final int operatorId, final String accountDate , final String bankId);
    
    /**
     * 处理银行抹账协议
     * @param jdbcTemplate
     * @param accountName 账户名
     * @param oldBankSerial 缴款流水号
     * @param accountDate 账务日期
     * @param bankSerial 抹账流水号
     * @param ountMoney 抹账金额
     * @param undoTime 抹账时间
     * @param operatorId 用户工号
     * @param undoAccountDate 抹账账务日期
     * @return 
     */
    public int handleBankSweepMoney(JdbcTemplate jdbcTemplate,final String accountName, 
            final String oldBankSerial,final String accountDate,final String bankSerial, 
            final BigDecimal ountMoney,final String undoTime, final int operatorId, final String undoAccountDate );
}
