package com.bestinfo.dao.impl.account;

import com.bestinfo.bean.account.AccountDetail;
import com.bestinfo.dao.account.IAccountQuerySummary;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.define.Account.TradeTypeDefine;
import java.util.Collections;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 资金账户统计
 *
 * @author shange
 */
@Repository
public class AccountQuerySummaryDaoImpl extends BaseDaoImpl implements IAccountQuerySummary {

    /**
     * 统计电信设备维护费、设备使用费
     */
    private final String EXTERNAL_SUMMARY = "select trade_time,income_money,out_money,trade_type from t_account_detail  where trade_time between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') and trade_type in (?,?) and account_id = ?";

    /**
     * 统计调整票费用、代销费调增、代销费调减、市中心补贴 (20161115 yfyang，增加市中心补贴类型)
     */
    private final String ADJUST_SUMMARY = "select trade_time,income_money,out_money,trade_type from t_account_detail  where trade_time between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') and "
            + "trade_type in ("
            + TradeTypeDefine.ADJUST_TICKET + ","
            + TradeTypeDefine.AGENT_FEE_ADD + ","
            + TradeTypeDefine.AGENT_FEE_SUBTRACT + ","
            + TradeTypeDefine.CENTER_SUBSIDY
            + ") and account_id = ?";

    /**
     * 按时间和交易类型统计电信设备维护费、设备使用费
     *
     * @param jdbcTemplate
     * @param beginTime
     * @param endTime
     * @param tradTypes
     * @param accountId
     * @return
     */
    @Override
    public List<AccountDetail> querySummary(JdbcTemplate jdbcTemplate, String beginTime, String endTime,
            Object[] tradTypes, String accountId) {
        try {
            List<AccountDetail> list = this.queryForList(jdbcTemplate, EXTERNAL_SUMMARY,
                    new Object[]{beginTime, endTime, tradTypes[0], tradTypes[1], accountId},
                    AccountDetail.class);
            return list;
        } catch (Exception e) {
            logger.error("AccountQuerySummaryDaoImpl querySummary ex :", e);
            return Collections.emptyList();
        }
    }

    /**
     * 按时间和交易类型统计调整票费用
     *
     * @param jdbcTemplate
     * @param beginTime
     * @param endTime
     * @param accountId
     * @return
     */
    @Override
    public List<AccountDetail> queryAdjustTicketSummary(JdbcTemplate jdbcTemplate, String beginTime, String endTime, String accountId) {
        try {
            List<AccountDetail> list = this.queryForList(jdbcTemplate, ADJUST_SUMMARY,
                    new Object[]{beginTime, endTime, accountId},
                    AccountDetail.class);
            return list;
        } catch (Exception e) {
            logger.error("AccountQuerySummaryDaoImpl querySummary ex :", e);
            return Collections.emptyList();
        }
    }

}
