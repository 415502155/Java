package com.bestinfo.dao.center;

import com.bestinfo.bean.cash.CashRequest;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 中心兑奖
 *
 * @author liyongjia
 */
public interface ICenterCashDao {

    /**
     * 中心兑奖调用存储过程
     *
     * @param jdbcTemplate
     * @param cashRequest
     * @return
     */
    public Map<String, Object> cash(JdbcTemplate jdbcTemplate, final CashRequest cashRequest);
}
