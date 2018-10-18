package com.bestinfo.dao.stat;

import com.bestinfo.bean.stat.StatCityDraw;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 结算统计表-地市期(T_stat_city_draw)
 *
 * @author yangyuefu
 */
public interface IStatCityDrawDao {

    /**
     * 新增地级市信息
     *
     * @param jdebTemplate
     * @param statCityDraw
     * @return
     */
    public int insertStatCityDraw(JdbcTemplate jdebTemplate, StatCityDraw statCityDraw);

    /**
     * 批量插入地市统计记录
     *
     * @param jdbcTemplate
     * @param statCityDrawList
     * @return
     */
    public int batchInsertStatCityDraw(JdbcTemplate jdbcTemplate, List<StatCityDraw> statCityDrawList);
    /**
     * 批量插入地市统计记录
     *
     * @param jdbcTemplate
     * @param statCityDrawList
     * @return
     */
    public int batchInsertStatCityDrawMerge(JdbcTemplate jdbcTemplate, List<StatCityDraw> statCityDrawList);

    /**
     * 查询地级市信息
     *
     * @param jdebTemplate
     * @param game_id
     * @param draw_id
     * @param city_id
     * @return
     */
    public StatCityDraw getStatById(JdbcTemplate jdebTemplate, int game_id, int draw_id, int city_id);
}
