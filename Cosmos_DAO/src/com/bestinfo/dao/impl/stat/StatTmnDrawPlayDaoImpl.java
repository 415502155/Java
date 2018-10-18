package com.bestinfo.dao.impl.stat;

import com.bestinfo.bean.stat.StatTmnDrawPlay;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.stat.IStatTmnDrawPlayDao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 结算统计表-站点玩法期统计(T_stat_tmn_draw_play)
 */
@Repository
public class StatTmnDrawPlayDaoImpl extends BaseDaoImpl implements IStatTmnDrawPlayDao {

    private static final String MERGE_TMN_DRAW_PLAY_STAT = "merge into t_stat_tmn_draw_play stat"
            + " using (select ? terminal_id, ? game_id, ? draw_id, ? play_id from dual) src"
            + " on (stat.terminal_id = src.terminal_id and stat.game_id = src.game_id and stat.draw_id = src.draw_id and stat.play_id = src.play_id)"
            + " when matched then"
            + "  update set sale_money = ?, sale_ticket_num = ?, sale_stake_num = ?"
            + " when not matched then"
            + "  insert"
            + "    (terminal_id,"
            + "     game_id,"
            + "     draw_id,"
            + "     play_id,"
            + "     city_id,"
            + "     district_id,"
            + "     sale_money,"
            + "     sale_ticket_num,"
            + "     sale_stake_num)"
            + "  values"
            + "    (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * 从t_ticket_bet中统计每个终端每个玩法销量
     */
    private static final String SUM_STAT_FROM_TICKTBET = "SELECT ti.terminal_id,"
            + "       ti.game_id,"
            + "       ti.draw_id,"
            + "       ti.play_id,"
            + "       tm.city_id,"
            + "       tm.district_id,"
            + "       SUM(ti.bet_money) sale_money,"
            + "       COUNT(*) sale_ticket_num,"
            + "       SUM(ti.bet_money) / 2 sale_stake_num"
            + "  from t_ticket_bet ti, t_tmn_info tm"
            + " WHERE game_id = ?"
            + "   AND draw_id = ?"
            + "   AND ti.undo_mark = 0"
            + "   AND ti.terminal_id = tm.terminal_id"
            + " GROUP BY ti.game_id,"
            + "          ti.draw_id,"
            + "          ti.terminal_id,"
            + "          tm.city_id,"
            + "          tm.district_id,"
            + "          ti.play_id"
            + " order by terminal_id, play_id";

    /**
     * 批量插入统计记录
     *
     * @param jdbcTemplate
     * @param statTmnDrawPlayList
     * @return
     */
    @Override
    public int mergeStatTmnDrawPlay(JdbcTemplate jdbcTemplate, final List<StatTmnDrawPlay> statTmnDrawPlayList) {
        int result = 0;
        try {
            jdbcTemplate.batchUpdate(MERGE_TMN_DRAW_PLAY_STAT, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    int j = 0;
                    StatTmnDrawPlay statTmnDrawPlay = statTmnDrawPlayList.get(i);
                    ps.setInt(++j, statTmnDrawPlay.getTerminal_id());
                    ps.setInt(++j, statTmnDrawPlay.getGame_id());
                    ps.setInt(++j, statTmnDrawPlay.getDraw_id());
                    ps.setInt(++j, statTmnDrawPlay.getPlay_id());
                    ps.setBigDecimal(++j, statTmnDrawPlay.getSale_money());
                    ps.setLong(++j, statTmnDrawPlay.getSale_ticket_num());
                    ps.setLong(++j, statTmnDrawPlay.getSale_stake_num());
                    ps.setInt(++j, statTmnDrawPlay.getTerminal_id());
                    ps.setInt(++j, statTmnDrawPlay.getGame_id());
                    ps.setInt(++j, statTmnDrawPlay.getDraw_id());
                    ps.setInt(++j, statTmnDrawPlay.getPlay_id());
                    ps.setInt(++j, statTmnDrawPlay.getCity_id());
                    ps.setInt(++j, statTmnDrawPlay.getDistrict_id());
                    ps.setBigDecimal(++j, statTmnDrawPlay.getSale_money());
                    ps.setLong(++j, statTmnDrawPlay.getSale_ticket_num());
                    ps.setLong(++j, statTmnDrawPlay.getSale_stake_num());
                }

                @Override
                public int getBatchSize() {
                    return statTmnDrawPlayList.size();
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
     * 从t_ticket_bet中统计每个终端每个玩法的销量
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @return
     */
    @Override
    public List<StatTmnDrawPlay> getStatFromTicketBet(JdbcTemplate jdbcTemplate, int game_id, int draw_id) {
        return queryForList(jdbcTemplate,
                SUM_STAT_FROM_TICKTBET,
                new Object[]{game_id, draw_id},
                StatTmnDrawPlay.class);
    }

}
