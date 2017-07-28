package com.bestinfo.dao.stat;

import com.bestinfo.bean.stat.StatTmnDraw;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 结算统计表-站点期统计(T_stat_tmn_draw)
 *
 * @author yangyuefu
 */
public interface IStatTmnDrawDao {

    /**
     * 插入站点统计记录
     *
     * @param jdebTemplate
     * @param statTmnDraw
     * @return
     */
    public int insertStatTmnDraw(JdbcTemplate jdebTemplate, StatTmnDraw statTmnDraw);

    /**
     * 批量插入站点统计记录
     *
     * @param jdebTemplate
     * @param statTmnDrawList
     * @return
     */
    public int batchInsertStatTmnDraw(JdbcTemplate jdebTemplate, List<StatTmnDraw> statTmnDrawList);

    /**
     * 批量插入站点统计记录
     *
     * @param jdebTemplate
     * @param statTmnDrawList
     * @return
     */
    public int batchInsertStatTmnDrawMerge(JdbcTemplate jdebTemplate, List<StatTmnDraw> statTmnDrawList);

    /**
     * 根据游戏、期、终端获取统计记录
     *
     * @param jdebTemplate
     * @param terminal_id
     * @param gameId
     * @param drawId
     * @return
     */
    public StatTmnDraw getStatTmnDrawByPri(JdbcTemplate jdebTemplate, int terminal_id, int gameId, int drawId);

    /**
     * 根据游戏、期次、地市进行统计
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @return
     */
    public List<StatTmnDraw> sumByGameAndDrawAndCity(JdbcTemplate jdbcTemplate, int game_id, int draw_id);

    /**
     * 从实时统计表中获取终端期统计数据
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @return
     */
    public List<StatTmnDraw> sumFromCurrentTmnDraw(JdbcTemplate jdbcTemplate, int game_id, int draw_id);

    /**
     * 统计当天信息
     *
     * @param jdbcTemplate
     * @param time
     * @return
     */
    public List<StatTmnDraw> getStatTmnDrawByTime(JdbcTemplate jdbcTemplate, String time);
}
