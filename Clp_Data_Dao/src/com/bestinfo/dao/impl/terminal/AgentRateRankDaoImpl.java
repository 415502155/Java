package com.bestinfo.dao.impl.terminal;

import com.bestinfo.bean.terminal.AgentRateRank;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.terminal.IAgentRateRankDao;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 站点等级及代销费比例(T_agent_rate_rank)
 *
 * @author hhhh
 */
@Repository
public class AgentRateRankDaoImpl extends BaseDaoImpl implements IAgentRateRankDao {

    private static final String SELECT_AGENT_RATE_RANK_LIST = "select station_rank,rank_name,agent_fee_rate,work_status,cash_fee_rate from t_agent_rate_rank";

    /**
     * 获取所有记录
     *
     * @param jdbcTemplate
     * @return
     */
    @Override
    public List<AgentRateRank> getAgentRateRankList(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, SELECT_AGENT_RATE_RANK_LIST, null, AgentRateRank.class);
    }

}
