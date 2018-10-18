package com.bestinfo.dao.impl.stat;

import com.bestinfo.bean.game.GameKDrawInfo;
import com.bestinfo.bean.realTimeStatistics.CurrentKDrawStat;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.stat.ICurrentKDrawStatDao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 快开期统计
 *
 * @author chenliping
 */
@Repository
public class CurrentKDrawDaoImpl extends BaseDaoImpl implements ICurrentKDrawStatDao {

    /**
     * 快开游戏新期时，初始化
     */
    private static final String INSERTKDRAW = "insert into t_current_kdraw_stat(game_id,draw_id,keno_draw_id,sale_money,sale_ticket_num,sale_stake_num,undo_money,undo_ticket_num,undo_stake_num) values(?,?,?,0,0,0,0,0,0)";

    private static final String GETKDRAW = "select game_id,draw_id,keno_draw_id,sale_money,sale_ticket_num,sale_stake_num,undo_money,undo_ticket_num,undo_stake_num from t_current_kdraw_stat where game_id=? and draw_id=? and keno_draw_id=?";

    /**
     * 根据game_id和draw_id删除快开期统计
     */
    private static final String DELETE_KDRAWSTAT_BY_GAME_ID_AND_DRAW_ID = "DELETE FROM t_current_kdraw_stat WHERE game_id=? AND draw_id=?";

    /**
     * 删除某一keno期的统计数据
     */
    private static final String DELETE_CURRENT_KDRAW_STAT = "DELETE FROM t_current_kdraw_stat WHERE game_id=? AND draw_id=? AND keno_draw_id=?";

    /**
     * 快开游戏新期时，初始化
     *
     * @param jdbcTemplate
     * @param kDrawList
     * @return
     */
    @Override
    public int insertCurrentKdraw(JdbcTemplate jdbcTemplate, final List<GameKDrawInfo> kDrawList) {
        int result = 0;
        try {
            jdbcTemplate.batchUpdate(INSERTKDRAW, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, kDrawList.get(i).getGame_id());
                    ps.setInt(2, kDrawList.get(i).getDraw_id());
                    ps.setInt(3, kDrawList.get(i).getKeno_draw_id());
                }

                @Override
                public int getBatchSize() {
                    return kDrawList.size();
                }
            });
            return result;
        } catch (Exception e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            return -1;
        }
    }

    @Override
    public CurrentKDrawStat getCurrentKdraw(JdbcTemplate jdbcTemplate, int gameid, int drawid, int kenodrawid) {
        return queryForObject(jdbcTemplate, GETKDRAW, new Object[]{gameid, drawid, kenodrawid}, CurrentKDrawStat.class);
    }

    /**
     * 根据game_id和draw_id删除快开期统计
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @return
     */
    @Override
    public int deleteKDrawStatByGameIdAndDrawId(JdbcTemplate jdbcTemplate, int gameId, int drawId) {
        int result = 0;
        try {
            result = jdbcTemplate.update(DELETE_KDRAWSTAT_BY_GAME_ID_AND_DRAW_ID, new Object[]{gameId, drawId});
        } catch (DataAccessException e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

    /**
     * 删除实时统计-快开期统计表(T_current_kdraw_stat)
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    @Override
    public int deleteCurrentKdrawStat(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int keno_draw_id) {
        try {
            return jdbcTemplate.update(DELETE_CURRENT_KDRAW_STAT, new Object[]{game_id, draw_id, keno_draw_id});
        } catch (Exception e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            return -2;
        }
    }
}
