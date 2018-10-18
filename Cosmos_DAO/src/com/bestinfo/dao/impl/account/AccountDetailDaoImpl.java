package com.bestinfo.dao.impl.account;

import com.bestinfo.bean.account.AccountDetail;
import com.bestinfo.dao.account.IAccountDetailDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.page.Pagination;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 资金账户-变动明细信息
 *
 * @author hhhh
 */
@Repository
public class AccountDetailDaoImpl extends BaseDaoImpl implements IAccountDetailDao {

    /**
     * 插入资金帐户统一变动明细信息表sql
     */
    private static final String INSERT_ACCOUNT_DETAIL = "insert into t_account_detail(account_serial_no, account_id, operator_id, trade_time, trade_type, source_type, recharge_source, pre_money, income_money, out_money, acc_balance, bank_serial_no, scheme_id, trade_note) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * 根据账户编号，获取指定时间段内的所有日缴费明细
     */
    private static final String GET_ACCOUNTDETAIL_BY_ID_AND_TIME = "select trade_time,income_money,operator_id from t_account_detail where account_id = ? and trade_time between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd')";
    /**
     * 根据缴款日期、银行缴款交易流水号获取银行账号、交易时间、收入金额、支出金额
     */
    private static final String GET_ACCOUNT_DETAIL_BY_TRADE_DATE_BANK_SERAL_NO = "select b.bound_card, a.trade_time, a.income_money, a.out_money from t_account_detail a, t_account_info b where a.account_id=b.account_id and b.account_name = ? and to_char(a.trade_time,'yyyy-mm-dd')=? and a.bank_serial_no=? and a.trade_type = ? ";

    /**
     * 根据银行缴款交易流水号获取银行账号、交易时间、交易金额
     */
    private static final String GET_ACCOUNT_DETAIL_BYBANK_SERAL_NO = "select a.trade_time, a.income_money, a.account_id from t_account_detail a, t_account_info b where a.account_id=b.account_id and b.account_name = ? and a.bank_serial_no=? and a.trade_type = ? ";

    /**
     * 新增资金账户-变动明细信息
     *
     * @param jdbcTemplate
     * @param ad
     * @return
     */
    @Override
    public int insertAccountDetail(JdbcTemplate jdbcTemplate, AccountDetail ad) {
        String sql = INSERT_ACCOUNT_DETAIL;
        int re = jdbcTemplate.update(sql, new Object[]{
            ad.getAccount_serial_no(), ad.getAccount_id(), ad.getOperator_id(),
            ad.getTrade_time(), ad.getTrade_type(), ad.getSource_type(),
            ad.getRecharge_source(), ad.getPre_money(), ad.getIncome_money(),
            ad.getOut_money(), ad.getAcc_balance(), ad.getBank_serail_no(),
            ad.getScheme_id(), ad.getTrade_note()
        });
        return re;
    }

    /**
     * 根据账户编号，获取指定时间段内的所有日缴费明细
     *
     * @param jdbcTemplate
     * @param account_id
     * @param begin_time
     * @param end_time
     * @return
     */
    @Override
    public List<AccountDetail> getAccountDetailByAccountIdAndTime(JdbcTemplate jdbcTemplate, String account_id, String begin_time, String end_time) {
        return this.queryForList(jdbcTemplate, GET_ACCOUNTDETAIL_BY_ID_AND_TIME, new Object[]{account_id, begin_time, end_time}, AccountDetail.class);
    }

    /**
     * 根据缴款日期、银行缴款流水号获取账户变动明细信息
     *
     * @param jdbcTemplate
     * @param tradeDate 缴款日期
     * @param bankSerialNo 缴款流水号
     * @param tradeType
     * @param accountName
     * @return
     * @throws java.lang.Exception
     */
    @Override
    public AccountDetail getAccountDetailByTradeDateAndBankSerial(JdbcTemplate jdbcTemplate, String tradeDate, String bankSerialNo, int tradeType, String accountName) throws Exception {
        return this.queryForObject(jdbcTemplate, GET_ACCOUNT_DETAIL_BY_TRADE_DATE_BANK_SERAL_NO, new Object[]{accountName, tradeDate, bankSerialNo, tradeType}, AccountDetail.class);
    }

    /**
     * 根据银行缴款流水号获取账户变动明细信息
     *
     * @param jdbcTemplate
     * @param bankSerialNo
     * @param accountName
     * @param tradeType
     * @return
     */
    @Override
    public AccountDetail getAccountDetailByBankSerial(JdbcTemplate jdbcTemplate, String bankSerialNo, String accountName, int tradeType) throws Exception {
        return this.queryForObject(jdbcTemplate, GET_ACCOUNT_DETAIL_BYBANK_SERAL_NO, new Object[]{accountName, bankSerialNo, tradeType}, AccountDetail.class);
    }

    /**
     * 处理银行缴款协议
     *
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
    @Override
    public int handleBankPayment(JdbcTemplate jdbcTemplate, final String accountName, final String bankAccount, final BigDecimal payMoney, final String payTime, final String bankSerial, final String payType, final int operatorId, final String accountDate, final String bankId) {
        logger.info(accountName + "--" + bankAccount + "--" + payMoney + "--" + payTime + "--" + bankSerial + "--" + payType + "--" + operatorId + "--" + accountDate);
        try {
            String proc = "{call BankPayment(?,?,?,?,?, ?,?,?,?,?,?)}";
            int flag = 0;
            flag = (int) jdbcTemplate.execute(proc, new CallableStatementCallback() {
                @Override
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                    int c = 1;
                    cs.setInt(c++, Integer.parseInt(accountName));
                    cs.setString(c++, bankAccount);
                    cs.setBigDecimal(c++, payMoney);
                    cs.setString(c++, payTime);
                    cs.setString(c++, bankSerial);
                    cs.setInt(c++, operatorId);
                    cs.setString(c++, accountDate);
                    cs.setInt(c++, Integer.parseInt(payType));
                    cs.registerOutParameter(c++, Types.INTEGER);
                    cs.registerOutParameter(c++, Types.VARCHAR);
                    cs.setString(c++, bankId);
                    cs.execute();
                    int code = cs.getInt(9);
                    String msg = cs.getString(10);
                    if (code != 0) {
                        logger.error("account in money error,code:" + code + " | " + msg + ", tmnId:"+accountName);
                        return code;
                    }
                    return 0;
                }
            });
            return flag;
        } catch (Exception e) {
            logger.error("", e);
            return -1;
        }

    }

    /**
     * 处理银行抹账协议
     *
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
    @Override
    public int handleBankSweepMoney(JdbcTemplate jdbcTemplate, final String accountName, final String oldBankSerial, final String accountDate, final String bankSerial, final BigDecimal ountMoney, final String undoTime, final int operatorId, final String undoAccountDate) {
        String proc = "{call BankSweepMoney(?,?,?,?,?, ?,?,?,?,?)}";
        int flag = 0;
        try {
            flag = (int) jdbcTemplate.execute(proc, new CallableStatementCallback() {
                @Override
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                    cs.setInt(1, Integer.parseInt(accountName));
                    cs.setString(2, oldBankSerial);
                    cs.setString(3, accountDate);
                    cs.setString(4, bankSerial);
                    cs.setBigDecimal(5, ountMoney);
                    cs.setString(6, undoTime);
                    cs.setInt(7, operatorId);
                    cs.setString(8, undoAccountDate);
                    cs.registerOutParameter(9, Types.INTEGER);
                    cs.registerOutParameter(10, Types.VARCHAR);
                    cs.execute();
                    if (cs.getInt(9) != 0) {
                        logger.error(cs.getString(10) + ", tmnId:"+accountName);
                    }
                    return cs.getInt(9);
                }
            });
            return flag;
        } catch (Exception e) {
            logger.error("", e);
            return -10;
        }
    }
     /**
     * 2016-09-20 添加（大厅交易明细查询） 获取指定时间段内的所有日投注、兑奖、缴费明细
     *
     * @param jdbcTemplate
     * @param params
     * @return
     * @author yangyg
     */
    @Override
    public Pagination<AccountDetail> getAccountDetail(JdbcTemplate jdbcTemplate, Map<String, Object> params) {
        Map<String, Object> whereMap = getWhereStr(params);

        //sql语句
        StringBuilder sql = new StringBuilder();
        sql.append("select * from t_account_detail where 1=1 ");
        sql.append(whereMap.get("whereSql"));
        sql.append(" order by trade_time desc");

        StringBuilder countSql = new StringBuilder();
        countSql.append("SELECT COUNT(*) FROM t_account_detail WHERE 1=1 ");
        countSql.append(whereMap.get("whereSql"));

        //参数列表
        Object[] args = null;
        String paramStr = "";
        if (whereMap.get("whereParam") != null) {
            paramStr += whereMap.get("whereParam").toString();
        }
        if (!"".equals(paramStr)) {
            args = paramStr.split(",");
        }

        Pagination<AccountDetail> page = null;
        try {
            page = queryForPage(
                    jdbcTemplate,
                    sql.toString(),
                    countSql.toString(),
                    Integer.parseInt(params.get("PAGE_NUMBER").toString()),
                    Integer.parseInt(params.get("PAGE_SIZE").toString()),
                    args,
                    AccountDetail.class);
        } catch (NumberFormatException e) {
            logger.error("", e);
        }

        return page;

    }
    /**
     * 2016-09-20 添加 根据条件列表拼查询sql
     *
     * @param params
     * @return
     * @author yangyg
     */
    private Map<String, Object> getWhereStr(Map<String, Object> params) {
        StringBuilder whereSql = new StringBuilder("");
        StringBuilder whereParam = new StringBuilder("");

        Object account_Id = params.get("ACCOUNT_ID");
        if (account_Id != null) {
            whereSql.append(" AND account_id = ?");
            whereParam.append(account_Id).append(",");
        }

        Object begin_Time = params.get("BEGIN_TIME");
        if (begin_Time != null) {
            whereSql.append(" AND trade_time>=to_date(?,'yyyy-mm-dd hh24:mi:ss')");
            whereParam.append(begin_Time).append(",");
        }

        Object end_Time = params.get("END_TIME");
        if (end_Time != null) {
            whereSql.append(" AND trade_time<=to_date(?,'yyyy-mm-dd hh24:mi:ss')");
            whereParam.append(end_Time).append(",");
        }

        Object trade_Type = params.get("TRADE_TYPE");
        if (trade_Type != null) {
            whereSql.append(" AND trade_type = ?");
            whereParam.append(trade_Type).append(",");
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("whereSql", whereSql.toString());
        map.put("whereParam", whereParam.toString());

        return map;
    }

}
