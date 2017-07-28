package com.bestinfo.dao.impl.account;

import com.bestinfo.bean.account.AccountInfo;
import com.bestinfo.dao.account.IAccountInfoDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.math.BigDecimal;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 资金帐户统一汇总信息
 *
 * @author hhhh
 */
@Repository
public class AccountInfoDaoImpl extends BaseDaoImpl implements IAccountInfoDao {

    /**
     * 插入资金帐户统一汇总信息sql
     */
    private static final String INSERT_ACCOUNT_INFO = "insert into t_account_info(account_id, account_name, owner_name, account_type, acc_balance, settlement_type, sum_bet_money, sum_undo_money, sum_prize_money, sum_hand_in, sum_hand_out, sum_pay_out, sum_agent_fee, sum_cash_fee, prize_method, bank_id, bound_card, acc_note, prize_balance, freeze_money, account_status, default_credit, credit_time, temp_credit, account_serial_no) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    /**
     * 查询资金账户统一汇总信息
     */
    private static final String SELECT_ACCOUNTINFO_BYID = "select account_id, account_name, owner_name, account_type, acc_balance, settlement_type, sum_bet_money, sum_undo_money, sum_prize_money, sum_hand_in, sum_hand_out, sum_pay_out, sum_agent_fee, sum_cash_fee, prize_method, bank_id, bound_card, acc_note, prize_balance, freeze_money, account_status,  default_credit, credit_time, temp_credit, account_serial_no from t_account_info where trim(account_id)=?";

    /**
     * 根据帐号名称和帐号类型查询账户信息
     */
    private static final String GET_ACCOUNTINFO_BY_ACCNAME_ACCTYPE = "select owner_name, acc_balance, default_credit, credit_time, temp_credit from t_account_info where account_name=? ";

    /**
     * 根据账号id获取数据
     */
    private static final String GET_ACCOUNTINFO_BY_ACCOUNT_ID = "select account_id,account_name,owner_name,account_type,acc_balance,settlement_type,sum_bet_money,sum_undo_money,sum_prize_money,sum_hand_in,sum_hand_out,sum_pay_out,sum_agent_fee,sum_cash_fee,prize_method,bank_id,bound_card,acc_note,prize_balance,freeze_money,account_status,default_credit,credit_time,temp_credit,account_serial_no from t_account_info where trim(account_id)=? ";

    /**
     * 根据账号查询账户信息
     */
    private static final String GET_ACCOUNTINFO_BY_ACCOUNT_NAME = "select account_id, account_name, acc_balance, sum_hand_in, account_serial_no, bank_id from t_account_info where account_name=? ";

    /**
     * 根据账户名称更改账户余额、累计充值金额、当前账户流水号
     */
    private static final String UPDATE_ACCOUNTINFO_BY_ACCOUNT_NAME = "update t_account_info set acc_balance = ?, sum_hand_in = ?, account_serial_no = ? where account_name = ?";

    /**
     * 根据账户名称更改账户余额、累计抹账金额、当前账户流水号
     */
    private static final String UPDATE_ACCOUNTINFO_OUNT_BY_ACCOUNT_NAME = "update t_account_info set acc_balance = ?, sum_hand_out = ?, account_serial_no = ? where account_name = ?";
    /**
     * 根据账户编号修改账户信息
     */
    private static final String UPDATE_ACCOUNTINFO_BY_ACCID = "update t_account_info set acc_balance = ?, sum_pay_out = sum_pay_out+? , account_serial_no = account_serial_no + 1 where account_id = ?";

    private static final String UPDATE_Sum_Pay_Out_ById = "update T_account_info set sum_pay_out=sum_pay_out+? where trim(account_id)=?";

    /**
     * 查询需要扣款的账户信息
     */
    public static final String getAccountInfoListByTradeTypeSql = "select b.account_id,sum(a.deduct_money) as sum_pay_out from t_tmn_auto_deduction a,t_tmn_info b ,t_account_info c where a.terminal_id = b.terminal_id and c.account_id = b.account_id and a.trade_type = ? and a.work_status = 1 and (c.account_status = 1 or c.account_status = 2) group by b.account_id";

    /**
     * 开户
     *
     * @param jdbcTemplate
     * @param ai
     * @return
     */
    @Override
    public int insertAccountInfo(JdbcTemplate jdbcTemplate, AccountInfo ai) {
        String sql = INSERT_ACCOUNT_INFO;
        int re = -1;
        try {
            re = jdbcTemplate.update(sql, new Object[]{
                ai.getAccount_id(),
                ai.getAccount_name(),
                ai.getOwner_name(),
                ai.getAccount_type(),
                ai.getAcc_balance(),
                ai.getSettlement_type(),
                ai.getSum_bet_money(),
                ai.getSum_undo_money(),
                ai.getSum_prize_money(),
                ai.getSum_hand_in(),
                ai.getSum_hand_out(),
                ai.getSum_pay_out(),
                ai.getSum_agent_fee(),
                ai.getSum_cash_fee(),
                ai.getPrize_method(),
                ai.getBank_id(),
                ai.getBound_card(),
                ai.getAcc_note(),
                ai.getPrize_balance(),
                ai.getFreeze_money(),
                ai.getAccount_status(),
                ai.getDefault_credit(),
                ai.getCredit_time(),
                ai.getTemp_credit(),
                ai.getAccount_serial_no()
            });
            return re;
        } catch (DataAccessException e) {
            logger.error("insert account info into DB error, accountId:" + ai.getAccount_id(), e);
            return -2;
        }
    }

    /**
     * 获取资金信息
     *
     * @param jdbcTemplate
     * @param account_id
     * @return
     */
    @Override
    public AccountInfo getAccountInfoByAccountId(JdbcTemplate jdbcTemplate, String account_id) {
        return this.queryForObject(jdbcTemplate, SELECT_ACCOUNTINFO_BYID, new Object[]{account_id}, AccountInfo.class);
    }

    @Override
    public AccountInfo getAccountInfoByAccNameAndAccType(JdbcTemplate jdbcTemplate, String accountName) {
        return this.queryForObject(jdbcTemplate, GET_ACCOUNTINFO_BY_ACCNAME_ACCTYPE, new Object[]{accountName}, AccountInfo.class);
    }

    /**
     * 根据账号名称查询账户信息
     *
     * @param accountName
     * @return
     */
    @Override
    public AccountInfo getAccountInfoByAccName(JdbcTemplate jdbcTemplate, String accountName) {
        return this.queryForObject(jdbcTemplate, GET_ACCOUNTINFO_BY_ACCOUNT_NAME, new Object[]{accountName}, AccountInfo.class);
    }

    /**
     * 根据账户名称更改账户余额、累计充值金额、当前账户流水号
     *
     * @param jdbcTemplate
     * @param accountInfo
     * @return
     */
    @Override
    public int updateAccountInfoByAccName(JdbcTemplate jdbcTemplate, AccountInfo accountInfo) throws Exception {
        Object[] obj = new Object[]{accountInfo.getAcc_balance(), accountInfo.getSum_hand_in(), accountInfo.getAccount_serial_no(), accountInfo.getAccount_name()};
        return jdbcTemplate.update(UPDATE_ACCOUNTINFO_BY_ACCOUNT_NAME, obj);
    }

    /**
     * 根据账户名称更改账户余额、累计抹账金额、当前账户流水号
     *
     * @param jdbcTemplate
     * @param accountInfo
     * @return
     */
    @Override
    public int updateAccountInfoOuntByAccName(JdbcTemplate jdbcTemplate, AccountInfo accountInfo) throws Exception {
        Object[] obj = new Object[]{accountInfo.getAcc_balance(), accountInfo.getSum_hand_out(),
            accountInfo.getAccount_serial_no(), accountInfo.getAccount_name()};
        return jdbcTemplate.update(UPDATE_ACCOUNTINFO_OUNT_BY_ACCOUNT_NAME, obj);
    }

    /**
     * 根据账户编号获取资金账户信息
     *
     * @param jdbcTemplate
     * @param accountId
     * @return
     */
    @Override
    public AccountInfo getAccountInfoById(JdbcTemplate jdbcTemplate, String accountId) {
        return this.queryForObject(jdbcTemplate, GET_ACCOUNTINFO_BY_ACCOUNT_ID, new Object[]{accountId}, AccountInfo.class);
    }

    /**
     * 根据id更新余额
     *
     * @param jdbcTemplate
     * @param balance
     * @param accountId
     * @return
     */
    @Override
    public int updateAccountInfobyAccId(JdbcTemplate jdbcTemplate, BigDecimal balance,BigDecimal deduct_money, String accountId) {
        return jdbcTemplate.update(UPDATE_ACCOUNTINFO_BY_ACCID, new Object[]{balance,deduct_money, accountId});
    }
}
