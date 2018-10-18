package com.bestinfo.dao.impl.stat;

import com.bestinfo.bean.stat.StatTmnDraw;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.stat.IStatTmnDrawDao;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 结算统计表-站点期统计(T_stat_tmn_draw)
 *
 * @author yangyuefu
 */
@Repository
public class StatTmnDrawDaoImpl extends BaseDaoImpl implements IStatTmnDrawDao {

//    private final Logger logger = Logger.getLogger(StatTmnDrawDaoImpl.class);
    /**
     * 插入StatTmnDraw
     */
    private static final String INSERT_STATTMNDRAW = "insert into t_stat_tmn_draw(terminal_id,game_id,draw_id,city_id,district_id,stat_time,sale_money,sale_ticket_num,sale_stake_num,undo_money,undo_ticket_num,undo_stake_num,cash_money,cash_ticket_num,cash_stake_num,agent_fee_deduct,agent_fee,cash_fee) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String INSERT_STATTMNDRAW_MERGE = "merge into t_stat_tmn_draw stattd using (select ? terminal_id,? game_id,? draw_id from dual) src on (stattd.terminal_id=src.terminal_id and stattd.game_id=src.game_id and stattd.draw_id=src.draw_id) when matched then update set city_id=?,district_id=?,stat_time=?,sale_money=?,sale_ticket_num=?,sale_stake_num=?,undo_money=?,undo_ticket_num=?,undo_stake_num=?,cash_money=?,cash_ticket_num=?,cash_stake_num=?,agent_fee_deduct=?,agent_fee=?,cash_fee=? when not matched then insert (terminal_id,game_id,draw_id,city_id,district_id,stat_time,sale_money,sale_ticket_num,sale_stake_num,undo_money,undo_ticket_num,undo_stake_num,cash_money,cash_ticket_num,cash_stake_num,agent_fee_deduct,agent_fee,cash_fee) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    /**
     * 根据游戏、期次、终端获取StatTmnDraw记录
     */
    private static final String GET_STAT_BY_GAME_DRAW_TERMINAL = "select terminal_id,game_id,draw_id,city_id,district_id,stat_time,sale_money,sale_ticket_num,sale_stake_num,undo_money,undo_ticket_num,undo_stake_num,cash_money,cash_ticket_num,cash_stake_num,agent_fee_deduct,agent_fee,cash_fee from t_stat_tmn_draw  where  game_id=? and draw_id=? and terminal_id=?";

    /**
     * 根据游戏、期、地市统计数据
     */
    private static final String SUM_BY_GAME_DRAW_CITY = "select game_id,draw_id,city_id, sum(sale_money) sale_money,sum(sale_ticket_num) sale_ticket_num,sum(sale_stake_num) sale_stake_num, sum(undo_money) undo_money,sum(undo_ticket_num) undo_ticket_num,sum(undo_stake_num) undo_stake_num, sum(cash_money) cash_money,sum(cash_ticket_num) cash_ticket_num,sum(cash_stake_num) cash_stake_num, sum(agent_fee_deduct) agent_fee_deduct,sum(agent_fee) agent_fee,sum(cash_fee) cash_fee from t_stat_tmn_draw where game_id=? and draw_id=? group by game_id,draw_id,city_id";

    private static final String SUM_FROM_CURRENT_TMN_DRAW = "select stat.terminal_id,"
            + "       game_id,"
            + "       draw_id,"
            + "       tmn.city_id,"
            + "       tmn.district_id,"
            + "       sum(sale_money) sale_money,"
            + "       sum(sale_ticket_num) sale_ticket_num,"
            + "       sum(sale_stake_num) sale_stake_num,"
            + "       sum(undo_money) undo_money,"
            + "       sum(undo_ticket_num) undo_ticket_num,"
            + "       sum(undo_stake_num) undo_stake_num,"
            + "       sum(cash_money) cash_money,"
            + "       sum(cash_ticket_num) cash_ticket_num,"
            + "       sum(cash_stake_num) cash_stake_num,"
            + "       sum(agent_fee_deduct) agent_fee_deduct,"
            + "       sum(cash_fee) cash_fee"
            + "  from t_current_tmn_draw_stat stat, t_tmn_info tmn"
            + " where stat.game_id = ?"
            + "   and stat.draw_id = ?"
            + "   and stat.terminal_id = tmn.terminal_id"
            + " group by stat.terminal_id,"
            + "          stat.game_id,"
            + "          stat.draw_id,"
            + "          tmn.city_id,"
            + "          tmn.district_id"
            + " order by stat.terminal_id";

    private static final String GET_STAT_TMN_DRAW_BY_TIME = "select a.terminal_id,"
            + "       a.game_id,"
            + "       a.draw_id,"
            + "       b.draw_name,"
            + "       a.city_id,"
            + "       a.district_id,"
            + "       a.stat_time,"
            + "       a.sale_money,"
            + "       a.sale_ticket_num,"
            + "       a.sale_stake_num,"
            + "       a.undo_money,"
            + "       a.undo_ticket_num,"
            + "       a.undo_stake_num,"
            + "       a.cash_money,"
            + "       a.cash_ticket_num,"
            + "       a.cash_stake_num,"
            + "       a.agent_fee_deduct,"
            + "       a.agent_fee,"
            + "       a.cash_fee"
            + "  from t_stat_tmn_draw a, t_game_draw_info b"
            + " where a.game_id = b.game_id"
            + "   and a.draw_id = b.draw_id"
            + "   and a.stat_time = to_date(?, 'yyyy/mm/dd')"
            + " order by a.terminal_id asc, a.game_id asc, a.city_id asc";

    /**
     * 新增站点期信息
     *
     * @param jdbcTemplate
     * @param statTmnDraw
     * @return
     */
    @Override
    public int insertStatTmnDraw(JdbcTemplate jdbcTemplate, StatTmnDraw statTmnDraw) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 获取站点期信息列表
     *
     * @param jdbcTemplate
     * @param statTmnDrawList
     * @return
     */
    @Override
    public int batchInsertStatTmnDraw(JdbcTemplate jdbcTemplate, final List<StatTmnDraw> statTmnDrawList) {
        int result = 0;
        try {
            jdbcTemplate.batchUpdate(INSERT_STATTMNDRAW, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    StatTmnDraw statTmnDraw = statTmnDrawList.get(i);
                    ps.setInt(1, statTmnDraw.getTerminal_id());
                    ps.setInt(2, statTmnDraw.getGame_id());
                    ps.setInt(3, statTmnDraw.getDraw_id());
                    ps.setInt(4, statTmnDraw.getCity_id());
                    ps.setInt(5, statTmnDraw.getDistrict_id());
                    ps.setDate(6, new Date(statTmnDraw.getStat_time().getTime()));
                    ps.setBigDecimal(7, statTmnDraw.getSale_money());
                    ps.setLong(8, statTmnDraw.getSale_ticket_num());
                    ps.setLong(9, statTmnDraw.getSale_stake_num());
                    ps.setBigDecimal(10, statTmnDraw.getUndo_money());
                    ps.setLong(11, statTmnDraw.getUndo_ticket_num());
                    ps.setLong(12, statTmnDraw.getUndo_stake_num());
                    ps.setBigDecimal(13, statTmnDraw.getCash_money());
                    ps.setLong(14, statTmnDraw.getCash_ticket_num());
                    ps.setLong(15, statTmnDraw.getCash_stake_num());
                    ps.setBigDecimal(16, statTmnDraw.getAgent_fee_deduct());
                    ps.setBigDecimal(17, statTmnDraw.getAgent_fee());
                    ps.setBigDecimal(18, statTmnDraw.getCash_fee());
                }

                @Override
                public int getBatchSize() {
                    return statTmnDrawList.size();
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
     * 获取站点期信息列表
     *
     * @param jdbcTemplate
     * @param statTmnDrawList
     * @return
     */
    @Override
    public int batchInsertStatTmnDrawMerge(JdbcTemplate jdbcTemplate, final List<StatTmnDraw> statTmnDrawList) {
        int result = 0;
        final java.util.Date now = new java.util.Date();
        try {
            jdbcTemplate.batchUpdate(INSERT_STATTMNDRAW_MERGE, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    StatTmnDraw statTmnDraw = statTmnDrawList.get(i);
                    ps.setInt(1, statTmnDraw.getTerminal_id());
                    ps.setInt(2, statTmnDraw.getGame_id());
                    ps.setInt(3, statTmnDraw.getDraw_id());
                    ps.setInt(4, statTmnDraw.getCity_id());
                    ps.setInt(5, statTmnDraw.getDistrict_id());
                    ps.setDate(6, new Date(now.getTime()));
                    ps.setBigDecimal(7, statTmnDraw.getSale_money());
                    ps.setLong(8, statTmnDraw.getSale_ticket_num());
                    ps.setLong(9, statTmnDraw.getSale_stake_num());
                    ps.setBigDecimal(10, statTmnDraw.getUndo_money());
                    ps.setLong(11, statTmnDraw.getUndo_ticket_num());
                    ps.setLong(12, statTmnDraw.getUndo_stake_num());
                    ps.setBigDecimal(13, statTmnDraw.getCash_money());
                    ps.setLong(14, statTmnDraw.getCash_ticket_num());
                    ps.setLong(15, statTmnDraw.getCash_stake_num());
                    ps.setBigDecimal(16, statTmnDraw.getAgent_fee_deduct());
                    ps.setBigDecimal(17, statTmnDraw.getAgent_fee());
                    ps.setBigDecimal(18, statTmnDraw.getCash_fee());
                    ps.setInt(19, statTmnDraw.getTerminal_id());
                    ps.setInt(20, statTmnDraw.getGame_id());
                    ps.setInt(21, statTmnDraw.getDraw_id());
                    ps.setInt(22, statTmnDraw.getCity_id());
                    ps.setInt(23, statTmnDraw.getDistrict_id());
                    ps.setDate(24, new Date(now.getTime()));
                    ps.setBigDecimal(25, statTmnDraw.getSale_money());
                    ps.setLong(26, statTmnDraw.getSale_ticket_num());
                    ps.setLong(27, statTmnDraw.getSale_stake_num());
                    ps.setBigDecimal(28, statTmnDraw.getUndo_money());
                    ps.setLong(29, statTmnDraw.getUndo_ticket_num());
                    ps.setLong(30, statTmnDraw.getUndo_stake_num());
                    ps.setBigDecimal(31, statTmnDraw.getCash_money());
                    ps.setLong(32, statTmnDraw.getCash_ticket_num());
                    ps.setLong(33, statTmnDraw.getCash_stake_num());
                    ps.setBigDecimal(34, statTmnDraw.getAgent_fee_deduct());
                    ps.setBigDecimal(35, statTmnDraw.getAgent_fee());
                    ps.setBigDecimal(36, statTmnDraw.getCash_fee());
                }

                @Override
                public int getBatchSize() {
                    return statTmnDrawList.size();
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
     * 根据游戏、期、终端获取统计记录
     *
     * @param jdebTemplate
     * @param terminal_id
     * @param gameId
     * @param drawId
     * @return
     */
    @Override
    public StatTmnDraw getStatTmnDrawByPri(JdbcTemplate jdebTemplate, int terminal_id, int gameId, int drawId) {
        return this.queryForObject(jdebTemplate,
                GET_STAT_BY_GAME_DRAW_TERMINAL,
                new Object[]{gameId, drawId, terminal_id},
                StatTmnDraw.class);
    }

    /**
     * 根据游戏、期次、地市进行统计
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @return
     */
    @Override
    public List<StatTmnDraw> sumByGameAndDrawAndCity(JdbcTemplate jdbcTemplate, int game_id, int draw_id) {
        return queryForList(jdbcTemplate,
                SUM_BY_GAME_DRAW_CITY,
                new Object[]{game_id, draw_id},
                StatTmnDraw.class);
    }

    /**
     * 从实时统计表中获取终端期统计数据
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @return
     */
    @Override
    public List<StatTmnDraw> sumFromCurrentTmnDraw(JdbcTemplate jdbcTemplate, int game_id, int draw_id) {
        return queryForList(jdbcTemplate,
                SUM_FROM_CURRENT_TMN_DRAW,
                new Object[]{game_id, draw_id},
                StatTmnDraw.class);
    }

    /**
     * 获取某一天的数据
     *
     * @param jdbcTemplate
     * @param time
     * @return
     */
    @Override
    public List<StatTmnDraw> getStatTmnDrawByTime(JdbcTemplate jdbcTemplate, String time) {
        return queryForList(jdbcTemplate,
                GET_STAT_TMN_DRAW_BY_TIME,
                new Object[]{time},
                StatTmnDraw.class);
    }
}
