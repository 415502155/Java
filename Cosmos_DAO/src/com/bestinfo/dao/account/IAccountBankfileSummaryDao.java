package com.bestinfo.dao.account;

import com.bestinfo.bean.account.AccountBankfileSummary;
import java.util.Date;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 资金账户-对账文件汇总信息
 *
 * @author zyk
 */
public interface IAccountBankfileSummaryDao {
    
    /**
     * 插入对账文件汇总信息
     * @param jdbcTemplate
     * @param abs
     * @return
     * @throws Exception 
     */
    public int insertAccountBankfileSummary(JdbcTemplate jdbcTemplate, AccountBankfileSummary abs);
    
    public int deleteAccountBankfileSummary(JdbcTemplate jdbcTemplate, AccountBankfileSummary abs);
    
    /**
     * 查询此对帐文件是否已经对帐
     * @param jdbcTemplate
     * @param bankid
     * @param accountdate
     * @return 
     */
    public int selectCheckMark(JdbcTemplate jdbcTemplate, String bankid, Date accountdate);
  
}
