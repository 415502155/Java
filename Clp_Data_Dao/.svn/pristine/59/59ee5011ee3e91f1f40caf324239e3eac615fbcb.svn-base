package com.bestinfo.dao.task;

import com.bestinfo.bean.task.NatureTmnDay;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 统计汇总表-自然日站点统计(T_natural_tmn_day)
 *
 * @author LH
 */
public interface INatureTmnDayDao {

    /**
     * 查询自然日站点统计数据集合
     *
     * @param jdbcTemplate
     * @return
     */
    public List<NatureTmnDay> getNatureTmnDayList(JdbcTemplate jdbcTemplate);

    /**
     * 查询某天的站点统计数据集合
     * @param jdbcTemplate
     * @param day_str
     * @return 
     */
    public List<NatureTmnDay> getNatureTmnDayListDay(JdbcTemplate jdbcTemplate, String day_str);

}
