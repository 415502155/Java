package com.bestinfo.dao.impl.stat;

import com.bestinfo.bean.business.DealerUser;
import com.bestinfo.bean.realTimeStatistics.CurrentTmnDrawStat;
import com.bestinfo.bean.stat.StatTmnDraw;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.stat.ICurrentTmnDrawStatDao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 实时统计--投注机期统计表T_current_tmn_draw_stat
 */
@Repository
public class CurrentTmnDrawStatDaoImpl extends BaseDaoImpl implements ICurrentTmnDrawStatDao {

    /**
     * 查询
     */
    private static final String SUM_BY_GAME_DRAW = "select game_id,draw_id, sum(sale_money) sale_money,sum(sale_ticket_num) sale_ticket_num,sum(sale_stake_num) sale_stake_num, sum(undo_money) undo_money,sum(undo_ticket_num) undo_ticket_num,sum(undo_stake_num) undo_stake_num, sum(cash_money) cash_money,sum(cash_ticket_num) cash_ticket_num,sum(cash_stake_num) cash_stake_num, sum(agent_fee_deduct) agent_fee_deduct,sum(cash_fee) cash_fee from t_current_tmn_draw_stat where game_id=? and draw_id=? group by game_id,draw_id";
    /**
     * 查询
     */
    private static final String SUM_BY_TERM_GAME_DRAW = "select terminal_id,game_id,draw_id, sum(sale_money) sale_money,sum(sale_ticket_num) sale_ticket_num,sum(sale_stake_num) sale_stake_num, sum(undo_money) undo_money,sum(undo_ticket_num) undo_ticket_num,sum(undo_stake_num) undo_stake_num, sum(cash_money) cash_money,sum(cash_ticket_num) cash_ticket_num,sum(cash_stake_num) cash_stake_num, sum(agent_fee_deduct) agent_fee_deduct,sum(cash_fee) cash_fee from t_current_tmn_draw_stat where game_id=? and draw_id=? group by terminal_id,game_id,draw_id";
    /**
     * 查询
     */
    private static final String SELECTTMNDRAW_BYOPERATOR = "select operator_id,sale_money,undo_money,cash_money,agent_fee_deduct,cash_fee  from t_current_tmn_draw_stat   where  terminal_id = ? and game_id=? and draw_id=?";
    /**
     * 新增投注机期信息
     */
    private static final String INIT_CURRENTDRAWSTAT = "insert into t_current_tmn_draw_stat(terminal_id, game_id,draw_id,operator_id,sale_money, sale_ticket_num,sale_stake_num,undo_money,undo_ticket_num,undo_stake_num, cash_money,cash_ticket_num,cash_stake_num, agent_fee_deduct,cash_fee)  values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String SELECT_CURRENTDRAWSTAT_BYDRAWS = "select operator_id,sum(sale_money) as sale_money ,  sum(undo_money) as undo_money ,  sum( cash_money) as cash_money  from t_current_tmn_draw_stat where game_id=?  and  draw_id between ? and ?  and  terminal_id=? group by  operator_id ";

    private static final String SELECT_CURRENTDRAWSTAT_BYDRAWS_NoEndDraw = "select operator_id,sum(sale_money) as sale_money ,  sum(undo_money) as undo_money ,  sum( cash_money) as cash_money  from t_current_tmn_draw_stat  where game_id=?  and  draw_id=?  and  terminal_id=?  group by  operator_id ";

    /**
     * 根据game_id和draw_id删除终端流水号
     */
    private static final String DELETE_TMNSERIALNO_BY_GAME_ID_AND_DRAW_ID = "DELETE FROM t_tmn_serial_no WHERE game_id=? AND draw_id=?";

    /**
     * 根据game_id和draw_id删除终端期统计
     */
    private static final String DELETE_TMNDRAWSTAT_BY_GAME_ID_AND_DRAW_ID = "DELETE FROM t_current_tmn_draw_stat WHERE game_id=? AND draw_id=?";

    private static final String MERGE_SERIAL_NO = "merge into t_tmn_serial_no dest"
            + " using (select ? game_id, ? draw_id, terminal_id"
            + "         from dual, (select terminal_id from t_tmn_info)) src"
            + " on (dest.game_id = src.game_id and dest.draw_id = src.draw_id and dest.terminal_id = src.terminal_id)"
            + " when matched then"
            + "  update set serial_no = 0"
            + " when not matched then"
            + "  insert"
            + "    (game_id, draw_id, terminal_id, serial_no, partion_id)"
            + "  values"
            + "    (?, ?, src.terminal_id, 0, 0)";

    private static final String MERGE_CURRENT_TMN_DRAW_STAT = "merge into t_current_tmn_draw_stat dest"
            + " using (select ? game_id, ? draw_id, ? terminal_id, ? operator_id from dual) src"
            + " on (dest.game_id = src.game_id and dest.draw_id = src.draw_id and dest.terminal_id = src.terminal_id and dest.operator_id = src.operator_id)"
            + " when matched then"
            + "  update"
            + "     set sale_money       = ?,"
            + "         sale_ticket_num  = ?,"
            + "         sale_stake_num   = ?,"
            + "         undo_money       = ?,"
            + "         undo_ticket_num  = ?,"
            + "         undo_stake_num   = ?,"
            + "         cash_money       = ?,"
            + "         cash_ticket_num  = ?,"
            + "         cash_stake_num   = ?,"
            + "         agent_fee_deduct = ?,"
            + "         cash_fee         = ?"
            + " when not matched then"
            + "  insert"
            + "    (game_id,"
            + "     draw_id,"
            + "     terminal_id,"
            + "     operator_id,"
            + "     sale_money,"
            + "     sale_ticket_num,"
            + "     sale_stake_num,"
            + "     undo_money,"
            + "     undo_ticket_num,"
            + "     undo_stake_num,"
            + "     cash_money,"
            + "     cash_ticket_num,"
            + "     cash_stake_num,"
            + "     agent_fee_deduct,"
            + "     cash_fee)"
            + "  values"
            + "    (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    //统计终端销售实时统计表
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

    /**
     * 根据游戏和期次进行统计
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @return
     */
    @Override
    public CurrentTmnDrawStat getSumStatByGameAndDraw(JdbcTemplate jdbcTemplate, int gameid, int drawid) {
        String sql = SUM_BY_GAME_DRAW;
        return queryForObject(jdbcTemplate, sql, new Object[]{gameid, drawid}, CurrentTmnDrawStat.class);
    }

    /**
     * 根据终端、游戏、期次进行统计
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @return
     */
    @Override
    public List<CurrentTmnDrawStat> getSumStatByTerminalAndGameAndDraw(JdbcTemplate jdbcTemplate, int gameid, int drawid) {
        String sql = SUM_BY_TERM_GAME_DRAW;
        return queryForList(jdbcTemplate, sql, new Object[]{gameid, drawid}, CurrentTmnDrawStat.class);
    }

    /**
     * 查询该期段时间内各个操作员对应的投注 注销 兑奖信息汇总
     *
     * @param jdbcTemplate
     * @param gameid
     * @param beginDraw
     * @param end_Draw
     * @param terminalId
     * @return
     */
    @Override
    public List<CurrentTmnDrawStat> getCurrentTmnDrawStatList(JdbcTemplate jdbcTemplate, int gameid, int beginDraw, int end_Draw, int terminalId) {
        String sql = SELECT_CURRENTDRAWSTAT_BYDRAWS;
        return queryForList(jdbcTemplate, sql, new Object[]{gameid, beginDraw, end_Draw, terminalId}, CurrentTmnDrawStat.class);
    }

    /**
     * 查询该期内各个操作员对应的投注 注销 兑奖信息汇总
     *
     * @param jdbcTemplate
     * @param gameid
     * @param beginDraw
     * @param terminalId
     * @return
     */
    @Override
    public List<CurrentTmnDrawStat> getCurrentTmnDrawStatListNoEndDraw(JdbcTemplate jdbcTemplate, int gameid, int beginDraw, int terminalId) {
        String sql = SELECT_CURRENTDRAWSTAT_BYDRAWS_NoEndDraw;
        return queryForList(jdbcTemplate, sql, new Object[]{gameid, beginDraw, terminalId}, CurrentTmnDrawStat.class);
    }

    /**
     * 开新期时，初始化终端库t_tmn_serial_no表
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @return
     */
    @Override
    public int initTmnSerialNo(JdbcTemplate jdbcTemplate, int gameid, int drawid) {
        try {
            int re = jdbcTemplate.update("INSERT ALL INTO  t_tmn_serial_no(game_id, draw_id, terminal_id, serial_no, partion_id)"
                    + "      VALUES(?, ?, terminal_id, 0, 0)"
                    + "      SELECT terminal_id, ?, ?  FROM t_tmn_info", new Object[]{gameid, drawid, gameid, drawid});

            return re;
        } catch (Exception e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            return -2;
        }
    }

    /**
     * 开新期时，初始化 实时统计-投注机期统计表(T_current_tmn_draw_stat)
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @param ldealuser 运营商管理员用户列表，每个终端有5个操作用户
     * @return
     */
    @Override
    public int initCurrentTmnDarwStat(JdbcTemplate jdbcTemplate, final int gameid, final int drawid, final List<DealerUser> ldealuser) {
        String sql = INIT_CURRENTDRAWSTAT;
        try {
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    DealerUser du = ldealuser.get(i);
                    int j = 1;
                    ps.setInt(j++, du.getTerminal_id());
                    ps.setInt(j++, gameid);
                    ps.setInt(j++, drawid);
                    ps.setInt(j++, du.getOperator_id());
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                }

                @Override
                public int getBatchSize() {
                    return ldealuser.size();
                }
            });
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            return -1;
        }
    }

    /**
     * 根据终端编号、游戏编号、期号查询所有操作员的统计信息
     *
     * @param jdbcTemplate
     * @param terminalId
     * @param gameId
     * @param drawId
     * @return 操作员统计信息集合
     */
    @Override
    public List<CurrentTmnDrawStat> selectCurrentTmnDrawStatList(JdbcTemplate jdbcTemplate, int terminalId, int gameId, int drawId) {
        return this.queryForList(jdbcTemplate, SELECTTMNDRAW_BYOPERATOR, new Object[]{terminalId, gameId, drawId}, CurrentTmnDrawStat.class);
    }

    /**
     * 根据game_id和draw_id删除终端流水号
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @return
     */
    @Override
    public int deleteTmnSerialNoByGameIdAndDrawId(JdbcTemplate jdbcTemplate, int gameId, int drawId) {
        int result = 0;
        try {
            result = jdbcTemplate.update(DELETE_TMNSERIALNO_BY_GAME_ID_AND_DRAW_ID, new Object[]{gameId, drawId});
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
     * 根据game_id和draw_id删除终端期统计
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @return
     */
    @Override
    public int deleteTmnDrawStatByGameIdAndDrawId(JdbcTemplate jdbcTemplate, int gameId, int drawId) {
        int result = 0;
        try {
            result = jdbcTemplate.update(DELETE_TMNDRAWSTAT_BY_GAME_ID_AND_DRAW_ID, new Object[]{gameId, drawId});
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
     * 开新期时，merge初始化终端库t_tmn_serial_no表
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @return
     */
    @Override
    public int mergeTmnSerialNo(JdbcTemplate jdbcTemplate, int gameId, int drawId) {
        try {
            return jdbcTemplate.update(MERGE_SERIAL_NO, new Object[]{gameId, drawId, gameId, drawId});
        } catch (Exception e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            return -2;
        }
    }

    /**
     * 开新期时，merge初始化实时统计-投注机期统计表(T_current_tmn_draw_stat)
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @param dealerUserList 运营商管理员用户列表，每个终端有5个操作用户
     * @return
     */
    @Override
    public int mergeCurrentTmnDarwStat(JdbcTemplate jdbcTemplate, final int gameid, final int drawid,
            final List<DealerUser> dealerUserList) {
        String sql = MERGE_CURRENT_TMN_DRAW_STAT;
        try {
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    DealerUser du = dealerUserList.get(i);
                    int j = 1;
                    ps.setInt(j++, gameid);
                    ps.setInt(j++, drawid);
                    ps.setInt(j++, du.getTerminal_id());
                    ps.setInt(j++, du.getOperator_id());
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, gameid);
                    ps.setInt(j++, drawid);
                    ps.setInt(j++, du.getTerminal_id());
                    ps.setInt(j++, du.getOperator_id());
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                    ps.setInt(j++, 0);
                }

                @Override
                public int getBatchSize() {
                    return dealerUserList.size();
                }
            });
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            return -1;
        }
    }
    
    //added by yfyang 20160826
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
}
