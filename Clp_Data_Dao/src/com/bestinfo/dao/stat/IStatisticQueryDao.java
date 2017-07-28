package com.bestinfo.dao.stat;

import com.bestinfo.bean.realTimeStatistics.CurrentTmnDateStat;
import com.bestinfo.dao.IBaseDao;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 终端日统计查询
 *
 * @author lvchangrong
 */
public interface IStatisticQueryDao extends IBaseDao {

    /**
     * 根据终端编号、开始时间和结束时间获取终端日统计数据
     *
     * @param jdbcTemplate
     * @param terminal_id
     * @param begin_time
     * @param end_time
     * @return
     */
    public List<CurrentTmnDateStat> getCurrentTmnDateStatList(JdbcTemplate jdbcTemplate, int terminal_id, String begin_time, String end_time);

    /**
     * 根据终端编号、开始时间获取终端日统计查询
     *
     * @param jdbcTemplate
     * @param terminal_id
     * @param begin_time
     * @return
     */
    public List<CurrentTmnDateStat> getCurrentTmnDateStatListNoEnd(JdbcTemplate jdbcTemplate, int terminal_id, String begin_time);
}
