package com.bestinfo.dao.account;

import com.bestinfo.bean.bank.BankCommKey;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 信息-银行通讯密钥
 *
 * @author zyk
 */
public interface IBankCommKeyDao {

    /**
     * 根据bankid获取有效的通讯密钥信息
     * @param jdbcTemplate
     * @param bankId
     * @return 
     */
    public BankCommKey getBankCommKeyByBankId(JdbcTemplate jdbcTemplate, String bankId);
    
    /**
     * 获取有效的通讯密钥信息
     * @param jdbcTemplate
     * @return 
     */
    public List<BankCommKey> getBankCommKey(JdbcTemplate jdbcTemplate);
}
