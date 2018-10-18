package com.bestinfo.dao.stat;

import com.bestinfo.bean.game.GameKDrawInfo;
import com.bestinfo.bean.realTimeStatistics.CurrentKDrawStat;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 快开期统计
 *
 * @author chenliping
 */
public interface ICurrentKDrawStatDao {

    public int insertCurrentKdraw(JdbcTemplate jdbcTemplate, final List<GameKDrawInfo> kDrawList);

    public CurrentKDrawStat getCurrentKdraw(JdbcTemplate jdbcTemplate, int gameid, int drawid, int kenodrawid);

    /**
     * 根据game_id和draw_id删除快开期统计
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @return
     */
    public int deleteKDrawStatByGameIdAndDrawId(JdbcTemplate jdbcTemplate, int gameId, int drawId);

    /**
     * 删除实时统计-快开期统计表(T_current_kdraw_stat)
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    public int deleteCurrentKdrawStat(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int keno_draw_id);
}
