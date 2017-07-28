package com.bestinfo.dao.impl.stat;

import com.bestinfo.bean.realTimeStatistics.CurrentKDrawPlayStat;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.stat.ICurrentKDrawPlayStatDao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 实时统计-快开期玩法统计表(T_current_kdraw_play_stat)
 */
@Repository
public class CurrentKDrawPlayDaoImpl extends BaseDaoImpl implements ICurrentKDrawPlayStatDao {

    /**
     * 统计每个小期的销售数据
     */
    private static final String SUM_BY_GAME_DRAW_KDRAW = "select game_id,draw_id,keno_draw_id, sum(sale_money) sale_money,sum(sale_ticket_num) sale_ticket_num,sum(sale_stake_num) sale_stake_num, sum(undo_money) undo_money,sum(undo_ticket_num) undo_ticket_num,sum(undo_stake_num) undo_stake_num from t_current_kdraw_play_stat where game_id=? and draw_id=? and keno_draw_id=? group by game_id,draw_id,keno_draw_id";

    /**
     * 统计每个小期每个玩法的销售数据,列表
     */
    private static final String SUM_BY_GAME_DRAW_KDRAW_PLAY = "select game_id,draw_id,keno_draw_id,play_id, sum(sale_money) sale_money,sum(sale_ticket_num) sale_ticket_num,sum(sale_stake_num) sale_stake_num, sum(undo_money) undo_money,sum(undo_ticket_num) undo_ticket_num,sum(undo_stake_num) undo_stake_num  from t_current_kdraw_play_stat where game_id=? and draw_id=? and keno_draw_id=?  group by game_id,draw_id,keno_draw_id,play_id";
    /**
     * 新增销售信息
     */
    private static final String INIT_CURRENTDRAWSTAT = "insert into t_current_kdraw_play_stat( game_id,draw_id,keno_draw_id,play_id,sale_money, sale_ticket_num,sale_stake_num,undo_money,undo_ticket_num,undo_stake_num)  values(?,?,?,?,?,?,?,?,?,?)";

    /**
     * 删除某一keno期的统计数据
     */
    private static final String DELETE_CURRENT_KDRAW_PLAY = "DELETE FROM t_current_kdraw_play_stat WHERE game_id=? AND draw_id=? AND keno_draw_id=?";

    private static final String MERGE_CURRENT_KDRAW_PLAY = "merge into t_current_kdraw_play_stat stat"
            + " using (select ? game_id, ? keno_draw_id, ? play_id from dual) src"
            + " on (stat.game_id = src.game_id and stat.keno_draw_id = src.keno_draw_id and stat.play_id = src.play_id)"
            + " when matched then"
            + "  update"
            + "         set sale_money  = sale_money + ?,"
            + "         sale_ticket_num = sale_ticket_num + ?,"
            + "         sale_stake_num  = sale_stake_num + ?,"
            + "         undo_money      = undo_money + ?,"
            + "         undo_ticket_num = undo_ticket_num + ?,"
            + "         undo_stake_num  = undo_stake_num + ?"
            + " when not matched then"
            + "  insert"
            + "    (game_id,"
            + "     draw_id,"
            + "     keno_draw_id,"
            + "     play_id,"
            + "     sale_money,"
            + "     sale_ticket_num,"
            + "     sale_stake_num,"
            + "     undo_money,"
            + "     undo_ticket_num,"
            + "     undo_stake_num,"
            + "     db_name)"
            + "  values"
            + "    (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * 从t_ticket_bet中统计每个快开期每个玩法销售数据
     */
    private static final String SUM_SALE_FROM_TICKTBET = "SELECT game_id,"
            + "       draw_id,"
            + "       keno_draw_id,"
            + "       play_id,"
            + "       SUM(bet_money) sale_money,"
            + "       COUNT(*) sale_ticket_num,"
            + "       SUM(bet_money) / 2 sale_stake_num"
            + "  from t_ticket_bet"
            + " WHERE game_id = ?"
            + "   AND draw_id = ?"
            + "   and keno_draw_id = ?"
            + "   AND undo_mark = 0"
            + " GROUP BY game_id, draw_id, keno_draw_id, play_id"
            + " order by play_id";

    /**
     * 从t_ticket_bet中统计每个快开期每个玩法注销数据
     */
    private static final String SUM_UNDO_FROM_TICKTBET = "SELECT game_id,"
            + "       draw_id,"
            + "       keno_draw_id,"
            + "       play_id,"
            + "       SUM(bet_money) undo_money,"
            + "       COUNT(*) undo_ticket_num,"
            + "       SUM(bet_money) / 2 undo_stake_num"
            + "  from t_ticket_bet"
            + " WHERE game_id = ?"
            + "   AND draw_id = ?"
            + "   and keno_draw_id = ?"
            + "   AND undo_mark = 1"
            + " GROUP BY game_id, draw_id, keno_draw_id, play_id"
            + " order by play_id";

    /**
     * 根据game_id，draw_id，keno_draw_id统计每个keno期的销售数据
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @param keno_draw_id
     * @return
     */
    @Override
    public CurrentKDrawPlayStat getSumStatByGameAndDrawAndKDraw(JdbcTemplate jdbcTemplate, int gameid, int drawid, int keno_draw_id) {
        String sql = SUM_BY_GAME_DRAW_KDRAW;
        return queryForObject(jdbcTemplate, sql, new Object[]{gameid, drawid, keno_draw_id}, CurrentKDrawPlayStat.class);
    }

    /**
     * 根据game_id，draw_id，keno_draw_id统计每个keno期每个玩法的销售数据
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @param keno_draw_id
     * @return
     */
    @Override
    public List<CurrentKDrawPlayStat> getSumStatByGameAndDrawAndKDrawAndPlay(JdbcTemplate jdbcTemplate, int gameid, int drawid, int keno_draw_id) {
        String sql = SUM_BY_GAME_DRAW_KDRAW_PLAY;
        return queryForList(jdbcTemplate, sql, new Object[]{gameid, drawid, keno_draw_id}, CurrentKDrawPlayStat.class);
    }

    /**
     * 开新期时，批量初始化
     *
     * @param jdbcTemplate
     * @param kdrawPlayStatList
     * @return
     */
    @Override
    public int batctInitCurrentStat(JdbcTemplate jdbcTemplate, final List<CurrentKDrawPlayStat> kdrawPlayStatList) {
        String sql = INIT_CURRENTDRAWSTAT;
        try {
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    CurrentKDrawPlayStat curStat = kdrawPlayStatList.get(i);
                    ps.setInt(1, curStat.getGame_id());
                    ps.setInt(2, curStat.getDraw_id());
                    ps.setInt(3, curStat.getKeno_draw_id());
                    ps.setInt(4, curStat.getPlay_id());
                    ps.setBigDecimal(5, curStat.getSale_money());
                    ps.setInt(6, curStat.getSale_ticket_num());
                    ps.setInt(7, curStat.getSale_stake_num());
                    ps.setBigDecimal(8, curStat.getUndo_money());
                    ps.setInt(9, curStat.getUndo_ticket_num());
                    ps.setInt(10, curStat.getUndo_stake_num());
                }

                @Override
                public int getBatchSize() {
                    return kdrawPlayStatList.size();
                }
            });
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -1;
        }
    }

    /**
     * 删除实时统计-快开期玩法统计表(T_current_kdraw_play_stat)
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    @Override
    public int deleteCurrentKdrawPlayStat(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int keno_draw_id) {
        try {
            return jdbcTemplate.update(DELETE_CURRENT_KDRAW_PLAY, new Object[]{game_id, draw_id, keno_draw_id});
        } catch (Exception e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            return -2;
        }
    }

    /**
     * 统计每个keno期各玩法的销售数据
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @param keno_draw_id
     * @return
     */
    @Override
    public List<CurrentKDrawPlayStat> getSaleFromTicketBet(JdbcTemplate jdbcTemplate, int gameid, int drawid, int keno_draw_id) {
        String sql = SUM_SALE_FROM_TICKTBET;
        return queryForList(jdbcTemplate, sql, new Object[]{gameid, drawid, keno_draw_id}, CurrentKDrawPlayStat.class);
    }

    /**
     * 统计每个keno期各玩法的注销数据
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @param keno_draw_id
     * @return
     */
    @Override
    public List<CurrentKDrawPlayStat> getUndoFromTicketBet(JdbcTemplate jdbcTemplate, int gameid, int drawid, int keno_draw_id) {
        String sql = SUM_UNDO_FROM_TICKTBET;
        return queryForList(jdbcTemplate, sql, new Object[]{gameid, drawid, keno_draw_id}, CurrentKDrawPlayStat.class);
    }

    /**
     * 批量插入统计记录
     *
     * @param jdbcTemplate
     * @param kdrawPlayList
     * @param dbName
     * @return
     */
    @Override
    public int mergeStatTmnDrawPlay(JdbcTemplate jdbcTemplate, final List<CurrentKDrawPlayStat> kdrawPlayList, final String dbName) {
        int result = 0;
        try {
            jdbcTemplate.batchUpdate(MERGE_CURRENT_KDRAW_PLAY, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    int j = 0;
                    CurrentKDrawPlayStat kdrawPlayStat = kdrawPlayList.get(i);
                    ps.setInt(++j, kdrawPlayStat.getGame_id());
                    ps.setInt(++j, kdrawPlayStat.getKeno_draw_id());
                    ps.setInt(++j, kdrawPlayStat.getPlay_id());
                    ps.setBigDecimal(++j, kdrawPlayStat.getSale_money());
                    ps.setLong(++j, kdrawPlayStat.getSale_ticket_num());
                    ps.setLong(++j, kdrawPlayStat.getSale_stake_num());
                    ps.setBigDecimal(++j, kdrawPlayStat.getUndo_money());
                    ps.setLong(++j, kdrawPlayStat.getUndo_ticket_num());
                    ps.setLong(++j, kdrawPlayStat.getUndo_stake_num());
                    ps.setInt(++j, kdrawPlayStat.getGame_id());
                    ps.setInt(++j, kdrawPlayStat.getDraw_id());
                    ps.setInt(++j, kdrawPlayStat.getKeno_draw_id());
                    ps.setInt(++j, kdrawPlayStat.getPlay_id());
                    ps.setBigDecimal(++j, kdrawPlayStat.getSale_money());
                    ps.setLong(++j, kdrawPlayStat.getSale_ticket_num());
                    ps.setLong(++j, kdrawPlayStat.getSale_stake_num());
                    ps.setBigDecimal(++j, kdrawPlayStat.getUndo_money());
                    ps.setLong(++j, kdrawPlayStat.getUndo_ticket_num());
                    ps.setLong(++j, kdrawPlayStat.getUndo_stake_num());
                    ps.setString(++j, dbName);
                }

                @Override
                public int getBatchSize() {
                    return kdrawPlayList.size();
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
}
