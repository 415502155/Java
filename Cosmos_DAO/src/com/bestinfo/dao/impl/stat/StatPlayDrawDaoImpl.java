package com.bestinfo.dao.impl.stat;

import com.bestinfo.bean.stat.StatPlayDraw;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.stat.IStatPlayDrawDao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 结算统计表-玩法销售统计表(T_stat_play_draw)
 *
 */
@Repository
public class StatPlayDrawDaoImpl extends BaseDaoImpl implements IStatPlayDrawDao {

    private static final String MERGE_STATPLAYDRAW = "merge into t_stat_play_draw dest"
            + " using (select ? game_id, ? play_id, ? draw_id from dual) src"
            + " on (dest.game_id = src.game_id and dest.play_id = src.play_id and dest.draw_id = src.draw_id)"
            + " when matched then"
            + "  update"
            + "     set sale_money      = ?,"
            + "         sale_ticket_num = ?,"
            + "         sale_stake_num  = ?,"
            + "         undo_money      = ?,"
            + "         undo_ticket_num = ?,"
            + "         undo_stake_num  = ?,"
            + "         cash_money      = ?,"
            + "         cash_ticket_num = ?,"
            + "         cash_stake_num  = ?"
            + " when not matched then"
            + "  insert"
            + "    (game_id,"
            + "     play_id,"
            + "     draw_id,"
            + "     sale_money,"
            + "     sale_ticket_num,"
            + "     sale_stake_num,"
            + "     undo_money,"
            + "     undo_ticket_num,"
            + "     undo_stake_num,"
            + "     cash_money,"
            + "     cash_ticket_num,"
            + "     cash_stake_num)"
            + "  values"
            + "    (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // 获取期统计
    private static final String GET_STATPLAYDRAW = "select * from t_stat_play_draw where game_id=? and play_id=? and draw_id=?";

    /**
     * merge插入玩法统计信息
     *
     * @param jdbcTemplate
     * @param playStatList
     * @return
     */
    @Override
    public int mergeStatPlayDraw(JdbcTemplate jdbcTemplate, final List<StatPlayDraw> playStatList) {
        int result = 0;
        try {
            jdbcTemplate.batchUpdate(MERGE_STATPLAYDRAW, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    StatPlayDraw statPlayDraw = playStatList.get(i);
                    ps.setInt(1, statPlayDraw.getGame_id());
                    ps.setInt(2, statPlayDraw.getPlay_id());
                    ps.setInt(3, statPlayDraw.getDraw_id());
                    ps.setBigDecimal(4, statPlayDraw.getSale_money());
                    ps.setLong(5, statPlayDraw.getSale_ticket_num());
                    ps.setLong(6, statPlayDraw.getSale_stake_num());
                    ps.setBigDecimal(7, statPlayDraw.getUndo_money());
                    ps.setLong(8, statPlayDraw.getUndo_ticket_num());
                    ps.setLong(9, statPlayDraw.getUndo_stake_num());
                    ps.setBigDecimal(10, statPlayDraw.getCash_money());
                    ps.setLong(11, statPlayDraw.getCash_ticket_num());
                    ps.setLong(12, statPlayDraw.getCash_stake_num());
                    ps.setInt(13, statPlayDraw.getGame_id());
                    ps.setInt(14, statPlayDraw.getPlay_id());
                    ps.setInt(15, statPlayDraw.getDraw_id());
                    ps.setBigDecimal(16, statPlayDraw.getSale_money());
                    ps.setLong(17, statPlayDraw.getSale_ticket_num());
                    ps.setLong(18, statPlayDraw.getSale_stake_num());
                    ps.setBigDecimal(19, statPlayDraw.getUndo_money());
                    ps.setLong(20, statPlayDraw.getUndo_ticket_num());
                    ps.setLong(21, statPlayDraw.getUndo_stake_num());
                    ps.setBigDecimal(22, statPlayDraw.getCash_money());
                    ps.setLong(23, statPlayDraw.getCash_ticket_num());
                    ps.setLong(24, statPlayDraw.getCash_stake_num());
                }

                @Override
                public int getBatchSize() {
                    return playStatList.size();
                }
            });
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
     * 获取玩法统计
     *
     * @param jdebTemplate
     * @param game_id
     * @param draw_id
     * @return
     */
    @Override
    public StatPlayDraw getStatPlayDraw(JdbcTemplate jdebTemplate, int game_id, int play_id, int draw_id) {
        return this.queryForObject(jdebTemplate,
                GET_STATPLAYDRAW,
                new Object[]{game_id, play_id, draw_id},
                StatPlayDraw.class);
    }

}
