package com.bestinfo.dao.stat;

import com.bestinfo.bean.stat.StatCityDistribution;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 结算统计表-游戏地市中奖分布(T_stat_city_distribution)
 *
 */
public interface IStatCityDistributionDao {

    /**
     * 获取地市中奖分布
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    public List<StatCityDistribution> getStatCityDistribution(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int keno_draw_id);

    /**
     * 批量插入地市中奖分布
     *
     * @param jdbcTemplate
     * @param statList
     * @return
     */
    public int batchInsertDistribution(JdbcTemplate jdbcTemplate, List<StatCityDistribution> statList);

    /**
     * 批量merge插入地市中奖分布
     *
     * @param jdbcTemplate
     * @param statList
     * @return
     */
    public int mergeDistribution(JdbcTemplate jdbcTemplate, final List<StatCityDistribution> statList);

    /**
     * 删除地市中奖分布
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    public int deleteStatCityDistribution(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int keno_draw_id);
}
