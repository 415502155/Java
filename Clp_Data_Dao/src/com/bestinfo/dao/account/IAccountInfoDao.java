package com.bestinfo.dao.account;

import com.bestinfo.bean.account.AccountInfo;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 资金帐户统一汇总信息
 *
 * @author hhhh
 */
public interface IAccountInfoDao {

    /**
     * 开户
     *
     * @param jdbcTemplate
     * @param ai
     * @return
     */
    public int insertAccountInfo(JdbcTemplate jdbcTemplate, AccountInfo ai);
    /**
     * 获取资金账户信息
     * @param jdbcTemplate
     * @param account_id
     * @return 
     */
    public AccountInfo getAccountInfoByAccountId(JdbcTemplate jdbcTemplate,String account_id);
    
    /**
     * 根据账号名称和账号类型查询账户信息
     * @param accountName
     * @return 
     */
    public AccountInfo getAccountInfoByAccNameAndAccType(JdbcTemplate jdbcTemplate, String accountName);
    
    /**
     * 根据账号名称查询账户信息
     * @param accountName
     * @return 
     */
    public AccountInfo getAccountInfoByAccName(JdbcTemplate jdbcTemplate, String accountName);
    
    /**
     * 根据账户名称更改账户余额、累计充值金额、当前账户流水号
     * @param jdbcTemplate
     * @param accountInfo 
     * @return 
     */
    public int updateAccountInfoByAccName(JdbcTemplate jdbcTemplate, AccountInfo accountInfo)throws Exception;
    
    /**
     * 根据账户名称更改账户余额、累计抹账金额、当前账户流水号
     * @param jdbcTemplate
     * @param accountInfo 
     * @return 
     */
    public int updateAccountInfoOuntByAccName(JdbcTemplate jdbcTemplate, AccountInfo accountInfo)throws Exception;

    /**
     * 根据id获取账户信息
     * @param jdbcTemplate
     * @param accountId
     * @return 
     */
    public AccountInfo getAccountInfoById(JdbcTemplate jdbcTemplate, String accountId);
    
    /**
     * 根据id更新余额
     * @param jdbcTemplate
     * @param balance
     * @param deduct_money
     * @param accountId
     * @return 
     */
    public int updateAccountInfobyAccId(JdbcTemplate jdbcTemplate, BigDecimal balance,BigDecimal deduct_money, String accountId);
    
}
