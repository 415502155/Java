package com.bestinfo.dao.impl.stat;

import com.bestinfo.bean.realTimeStatistics.CurrentTmnDateStat;
import com.bestinfo.dao.stat.IStatisticOperatorQueryDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 站点操作员日统计查询
 *
 * @author lvchangrong
 */
@Repository
public class StatisticOpetatorQueryDaoImpl extends BaseDaoImpl implements IStatisticOperatorQueryDao {

    /**
     * 终端操作员日统计数据列表
     */
    private static final String SELECT_OperatorCurrentTmnDateStat_LIST_byDate = " select operator_id, sum(sale_money) as sale_money,sum(undo_money) as undo_money,sum(cash_money) as cash_money,sum(agent_fee_deduct) as agent_fee_deduct,sum(cash_fee) as cash_fee from t_current_tmn_date_stat where terminal_id = ?  and  operator_date between  to_date(?, 'yyyy-mm-dd') and to_date(?, 'yyyy-mm-dd') group by operator_id order by operator_id asc";
    /**
     * 根据日期查询
     */
    private static final String SELECT_OperatorCurrentTmnDateStat_LIST_byNoEndDate = "select operator_id, sum(sale_money) as sale_money,sum(undo_money) as undo_money,sum(cash_money) as cash_money,sum(agent_fee_deduct) as agent_fee_deduct,sum(cash_fee) as cash_fee from t_current_tmn_date_stat where terminal_id = ? and operator_date = to_date(?, 'yyyy-mm-dd') group by operator_id";

    /**
     * 站点操作员日统计查询列表
     *
     * @param jdbcTemplate
     * @param identity
     * @param begin_time
     * @param end_time
     * @return
     */
    @Override
    public List<CurrentTmnDateStat> getOperatorCurrentTmnDateStatList(JdbcTemplate jdbcTemplate, int identity, String begin_time, String end_time) {
        return this.queryForList(jdbcTemplate, SELECT_OperatorCurrentTmnDateStat_LIST_byDate, new Object[]{identity, begin_time, end_time}, CurrentTmnDateStat.class);
    }

    /**
     * 站点操作员日统计查询列表
     *
     * @param identity
     * @param jdbcTemplate
     * @param begin_time
     * @return
     */
    @Override
    public List<CurrentTmnDateStat> getOperatorCurrentTmnDateStatListNoEnd(JdbcTemplate jdbcTemplate, int identity, String begin_time) {
        return this.queryForList(jdbcTemplate, SELECT_OperatorCurrentTmnDateStat_LIST_byNoEndDate, new Object[]{identity, begin_time}, CurrentTmnDateStat.class);
    }

}
