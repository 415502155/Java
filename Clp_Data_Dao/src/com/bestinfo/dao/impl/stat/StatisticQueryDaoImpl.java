package com.bestinfo.dao.impl.stat;

import com.bestinfo.bean.realTimeStatistics.CurrentTmnDateStat;
import com.bestinfo.dao.stat.IStatisticQueryDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 站点日统计查询
 *
 * @author lvchangrong
 */
@Repository
public class StatisticQueryDaoImpl extends BaseDaoImpl implements IStatisticQueryDao {

    /**
     * 终端游戏日统计数据列表
     */
    private static final String SELECT_CurrentTmnDateStat_LIST_byDate = "select game_id, sum(sale_money) as sale_money,sum(undo_money) as undo_money,sum(cash_money) as cash_money,sum(agent_fee_deduct) as agent_fee_deduct,sum(cash_fee) as cash_fee from t_current_tmn_date_stat where terminal_id = ?  and  operator_date between  to_date(?, 'yyyy-mm-dd') and to_date(?, 'yyyy-mm-dd') group by game_id order by game_id asc";
    /**
     * 查询
     */
    private static final String SELECT_CurrentTmnDateStat_LIST_byNoEndDate = "select game_id, sum(sale_money) as sale_money,sum(undo_money) as undo_money,sum(cash_money) as cash_money,sum(agent_fee_deduct) as agent_fee_deduct,sum(cash_fee) as cash_fee from t_current_tmn_date_stat where terminal_id = ? and operator_date = to_date(?, 'yyyy-mm-dd') group by game_id ";

    /**
     * 站点日统计查询列表
     *
     * @param jdbcTemplate
     * @param terminal_id
     * @param begin_time
     * @param end_time
     * @return
     */
    @Override
    public List<CurrentTmnDateStat> getCurrentTmnDateStatList(JdbcTemplate jdbcTemplate, int terminal_id, String begin_time, String end_time) {
        return this.queryForList(jdbcTemplate, SELECT_CurrentTmnDateStat_LIST_byDate, new Object[]{terminal_id, begin_time, end_time}, CurrentTmnDateStat.class);
    }

    /**
     * 站点日统计查询列表
     *
     * @param terminal_id
     * @param jdbcTemplate
     * @param begin_time
     * @return
     */
    @Override
    public List<CurrentTmnDateStat> getCurrentTmnDateStatListNoEnd(JdbcTemplate jdbcTemplate, int terminal_id, String begin_time) {
        return this.queryForList(jdbcTemplate, SELECT_CurrentTmnDateStat_LIST_byNoEndDate, new Object[]{terminal_id, begin_time}, CurrentTmnDateStat.class);
    }

}
