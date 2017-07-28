package com.bestinfo.dao.union;

import com.bestinfo.bean.union.UnionPronviceStatistics;
import org.springframework.jdbc.core.JdbcTemplate;
import java.math.BigDecimal;

/**
 *
 * @author LH
 */
public interface IUnionPronviceStatisticsDao {

    /**
     * 新增记录
     *
     * @param jdbcTemplate
     * @param ups
     * @return 成功-受影响的记录数 失败-(-1)
     */
    public int insert(JdbcTemplate jdbcTemplate, UnionPronviceStatistics ups);
    /**
     * 查询销售额
     * @param jdbcTemplate
     * @param systemId
     * @param gameId
     * @param drawId
     * @return 
     */
    public BigDecimal querySaleMoney(JdbcTemplate jdbcTemplate, int systemId, int gameId, int drawId);
}
