package com.bestinfo.dao.stat;

import com.bestinfo.bean.realTimeStatistics.CurrentTmnDateStat;
import com.bestinfo.dao.IBaseDao;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 站点日统计查询
 *
 * @author lvchangrong
 */
public interface IStatisticOperatorQueryDao extends IBaseDao {

    /**
     * 根据identity、开始时间和结束时间获取站点日统计数据
     *
     * @param jdbcTemplate
     * @param identity
     * @param begin_time
     * @param end_time
     * @return
     */
    public List<CurrentTmnDateStat> getOperatorCurrentTmnDateStatList(JdbcTemplate jdbcTemplate, int identity, String begin_time, String end_time);

    /**
     * 根据identity 和 开始时间获取站点日统计数据
     *
     * @param jdbcTemplate
     * @param identity
     * @param begin_time
     * @return
     */
    public List<CurrentTmnDateStat> getOperatorCurrentTmnDateStatListNoEnd(JdbcTemplate jdbcTemplate, int identity, String begin_time);

    /**
     * 查询某个日期区间的所有操作员的分游戏销量
     *
     * @param jdbcTemplate
     * @param identity
     * @param begin_time
     * @param end_time
     * @return
     */
    public List<CurrentTmnDateStat> getGameCurrentStatBetweenDays(JdbcTemplate jdbcTemplate, int identity, String begin_time, String end_time);

    /**
     * 查询某天的所有操作员的分游戏销量
     *
     * @param identity
     * @param jdbcTemplate
     * @param time
     * @return
     */
    public List<CurrentTmnDateStat> getGameCurrentStat4OneDay(JdbcTemplate jdbcTemplate, int identity, String time);

    /**
     * 查询某个日期区间的某个操作员的分游戏销量
     *
     * @param jdbcTemplate
     * @param identity
     * @param operator
     * @param begin_time
     * @param end_time
     * @return
     */
    public List<CurrentTmnDateStat> getOperatorGameCurrentStatBetweenDays(JdbcTemplate jdbcTemplate, int identity,
            int operator, String begin_time, String end_time);

    /**
     * 查询某天的某个操作员的分游戏销量
     *
     * @param identity
     * @param jdbcTemplate
     * @param operator
     * @param time
     * @return
     */
    public List<CurrentTmnDateStat> getOperatorGameCurrentStat4OneDay(JdbcTemplate jdbcTemplate, int identity,
            int operator, String time);
}
