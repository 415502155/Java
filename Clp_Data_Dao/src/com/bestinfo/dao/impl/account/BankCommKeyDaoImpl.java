package com.bestinfo.dao.impl.account;

import com.bestinfo.bean.bank.BankCommKey;
import com.bestinfo.dao.account.IBankCommKeyDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 信息-银行通讯密钥
 *
 * @author zyk
 */
@Repository
public class BankCommKeyDaoImpl extends BaseDaoImpl implements IBankCommKeyDao {

   /**
     * 根据bankid查询
     */
   private static final String getBankCommKeyByBankId = "select bank_id,encrypt_type,system_key,system_pub_key,bank_pub_key,work_status from t_bank_comm_key where bank_id = ? and work_status = 1 ";
    
    /**
     * 获取全部有效的密钥
     */
    private static final String getBankCommKey = "select bank_id,encrypt_type,system_key,system_pub_key,bank_pub_key,work_status  from t_bank_comm_key where work_status = 1 ";
    /**
     * 根据银行编号获取银行铜须秘钥
     *
     * @param jdbcTemplate
     * @param bankId
     * @return
     */
    @Override
    public BankCommKey getBankCommKeyByBankId(JdbcTemplate jdbcTemplate, String bankId) {
        return this.queryForObject(jdbcTemplate,  getBankCommKeyByBankId,  new Object[]{bankId},  BankCommKey.class);
    }

    /**
     * 获取银行秘钥列表信息
     *
     * @param jdbcTemplate
     * @return
     */
    @Override
    public List<BankCommKey> getBankCommKey(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, getBankCommKey,new Object[]{}, BankCommKey.class);
    }

}
