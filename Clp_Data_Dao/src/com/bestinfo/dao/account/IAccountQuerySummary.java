package com.bestinfo.dao.account;

import com.bestinfo.bean.account.AccountDetail;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 资金账户查询
 *
 * @author shange
 */
public interface IAccountQuerySummary {

    /**
     * 资金账户统计--电信设备维护费、设备使用费
     *
     * @param jdbcTemplate
     * @param beginTime
     * @param endTime
     * @param tradTypes
     * @param accountId
     * @return
     */
    public List<AccountDetail> querySummary(JdbcTemplate jdbcTemplate, String beginTime, String endTime,
            Object[] tradTypes, String accountId);

    /**
     * 资金账户统计--调整票费用
     *
     * @param jdbcTemplate
     * @param beginTime
     * @param endTime
     * @param accountId
     * @return
     */
    public List<AccountDetail> queryAdjustTicketSummary(JdbcTemplate jdbcTemplate, String beginTime, String endTime,
            String accountId);

}
