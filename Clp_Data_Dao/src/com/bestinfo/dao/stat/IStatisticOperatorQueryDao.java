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
}
