package com.bestinfo.dao.stat;

import com.bestinfo.bean.realTimeStatistics.CurrentKDrawPlayStat;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 实时统计-快开期玩法统计表(T_current_kdraw_play_stat)
 */
public interface ICurrentKDrawPlayStatDao {

    /**
     * 根据game_id，draw_id，keno_draw_id统计每个keno期的销售数据
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @param keno_draw_id
     * @return
     */
    public CurrentKDrawPlayStat getSumStatByGameAndDrawAndKDraw(JdbcTemplate jdbcTemplate, int gameid, int drawid, int keno_draw_id);

    /**
     * 根据game_id，draw_id，keno_draw_id统计每个keno期每个玩法的销售数据
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @param keno_draw_id
     * @return
     */
    public List<CurrentKDrawPlayStat> getSumStatByGameAndDrawAndKDrawAndPlay(JdbcTemplate jdbcTemplate, int gameid,
            int drawid, int keno_draw_id);

    /**
     * 开新期时，批量初始化
     *
     * @param jdbcTemplate
     * @param kdrawPlayStatList
     * @return
     */
    public int batctInitCurrentStat(JdbcTemplate jdbcTemplate, final List<CurrentKDrawPlayStat> kdrawPlayStatList);

    /**
     * 删除实时统计-快开期玩法统计表(T_current_kdraw_play_stat)
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    public int deleteCurrentKdrawPlayStat(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int keno_draw_id);

    /**
     * 统计每个keno期各玩法的销售数据
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @param keno_draw_id
     * @return
     */
    public List<CurrentKDrawPlayStat> getSaleFromTicketBet(JdbcTemplate jdbcTemplate, int gameid, int drawid, int keno_draw_id);

    /**
     * 统计每个keno期各玩法的注销数据
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @param keno_draw_id
     * @return
     */
    public List<CurrentKDrawPlayStat> getUndoFromTicketBet(JdbcTemplate jdbcTemplate, int gameid, int drawid, int keno_draw_id);

    /**
     * 批量插入统计记录
     *
     * @param jdbcTemplate
     * @param kdrawPlayList
     * @param dbName
     * @return
     */
    public int mergeStatTmnDrawPlay(JdbcTemplate jdbcTemplate, final List<CurrentKDrawPlayStat> kdrawPlayList, final String dbName);

}
