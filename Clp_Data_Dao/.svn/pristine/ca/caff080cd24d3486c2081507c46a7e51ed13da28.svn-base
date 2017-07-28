package com.bestinfo.dao.impl.stat;

import com.bestinfo.bean.stat.StatPrizeAnn;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.stat.IStatPrizeAnnDao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 普通游戏中奖汇总
 *
 * @author chenliping
 */
@Repository
public class StatPrizeAnnDaoImpl extends BaseDaoImpl implements IStatPrizeAnnDao {

//    private final Logger logger = Logger.getLogger(StatPrizeAnnDaoImpl.class);
    private static final String INSERT_STATPRIZE = "insert into t_stat_prize_ann(game_id,draw_id,play_id,open_id,class_id, class_name,prize_num,stake_prize,total_num) values(?,?,?,?,?,?,?,?,?)";
    private static final String INSERT_STATPRIZE_MERGE = "merge into t_stat_prize_ann t using (select ? game_id,? draw_id,? class_id from dual) s on (t.game_id=s.game_id and t.draw_id=s.draw_id and t.class_id=s.class_id) when matched then update set play_id=?,open_id=?,class_name=?,prize_num=?,stake_prize=?,total_num=? when not matched then insert (game_id,draw_id,class_id,play_id,open_id, class_name,prize_num,stake_prize,total_num) values(?,?,?,?,?,?,?,?,?)";

    private static final String SELECT_STATPRIZE = "select game_id,draw_id,play_id,open_id,class_id,class_name,prize_num,stake_prize,total_num from t_stat_prize_ann where game_id=? and draw_id=? and open_id=?";

    private static final String SELECT_STATPRIZE_ByOpenIds = "select game_id,draw_id,play_id,open_id,class_id,class_name,prize_num,stake_prize,total_num from t_stat_prize_ann where game_id=? and draw_id=? and open_id in (?)";

    private static final String UPDATE_STATPRIZEANN = "update t_stat_prize_ann set stake_prize=?,total_num=?  where game_id=? and draw_id=? and open_id=? and class_id=?";

    private static final String SELECT_STATPRIZEANN_ByIds = " select game_id,draw_id,play_id,open_id, class_id , class_name , prize_num , stake_prize , total_num from t_stat_prize_ann where game_id = ? and  draw_id = ? order by class_id asc";

    private static final String SELECT_STATPRIZEANN_LIST = "select game_id,draw_id,play_id,open_id,class_id , class_name , prize_num , stake_prize , total_num from t_stat_prize_ann";

    /**
     * 删除某一期的中奖数据
     */
    private static final String DELETE_PRIZE_ANN = "delete from t_stat_prize_ann where game_id=? and draw_id=?";

    private static final String GET_PRIZE_BY_GAME_DRAW_OPENID_DESC = "SELECT * FROM t_stat_prize_ann WHERE game_id=? and draw_id=? and open_id=? ORDER BY class_id DESC";

    private static final String GET_PRIZE_BY_GAME_DRAW_DESC = "SELECT * FROM t_stat_prize_ann WHERE game_id=? and draw_id=? ORDER BY class_id DESC";

    private static final String GET_ALL_SYSTEM_PRIZE = "select g.game_id,"
            + "       g.draw_id,"
            + "       g.play_id,"
            + "       g.open_id,"
            + "       g.class_id,"
            + "       g.class_name,"
            + "       sum(g.prize_num) + sum(s.prize_stakes) + sum(u.prize_num) as prize_num"
            + "  from t_stat_prize_ann g,"
            + "       t_union_stat_prize u,"
            + "       (select game_id, draw_id, class_id, sum(prize_stakes) as prize_stakes"
            + "          from t_stat_winning_bonus"
            + "         where game_id = ?"
            + "           and draw_id = ?"
            + "         group by game_id, draw_id, class_id) s"
            + " where g.game_id = ?"
            + "   and g.draw_id = ?"
            + "   and g.game_id = s.game_id"
            + "   and g.draw_id = s.draw_id"
            + "   and g.class_id = s.class_id"
            + "   and g.game_id = u.game_id"
            + "   and g.draw_id = u.draw_id"
            + "   and g.play_id = u.play_id"
            + "   and g.open_id = u.open_id"
            + "   and g.class_id = u.class_id"
            + " group by g.game_id,"
            + "          g.draw_id,"
            + "          g.play_id,"
            + "          g.open_id,"
            + "          g.class_id,"
            + "          g.class_name"
            + " order by class_id desc";

    /**
     * 普通游戏中奖汇总批量写入
     *
     * @param jdbcTemplate
     * @param lsp
     */
    @Override
    public int addStatPrizeAnn(JdbcTemplate jdbcTemplate, final List<StatPrizeAnn> lsp) {
        String sql = INSERT_STATPRIZE;
        try {
            //批量操作
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int index) throws SQLException {
                    StatPrizeAnn statPrize = (StatPrizeAnn) lsp.get(index);
                    ps.setInt(1, statPrize.getGame_id());
                    ps.setInt(2, statPrize.getDraw_id());
                    ps.setInt(3, statPrize.getPlay_id());
                    ps.setInt(4, statPrize.getOpen_id());
                    ps.setInt(5, statPrize.getClass_id());
                    ps.setString(6, statPrize.getClass_name());
                    ps.setInt(7, statPrize.getPrize_num());
                    ps.setBigDecimal(8, statPrize.getStake_prize());
                    ps.setInt(9, statPrize.getTotal_num());
                }

                @Override
                public int getBatchSize() {
                    return lsp.size();
                }
            });
        } catch (DataAccessException dataAccessException) {
            logger.error("batch update error", dataAccessException);
            SQLException sqle = (SQLException) dataAccessException.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            return -1;
        }
        return 0;
    }

    /**
     * 普通游戏中奖汇总批量写入
     *
     * @param jdbcTemplate
     * @param lsp
     */
    @Override
    public int addStatPrizeAnnMerge(JdbcTemplate jdbcTemplate, final List<StatPrizeAnn> lsp) {
        String sql = INSERT_STATPRIZE_MERGE;
        try {
            //批量操作
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int index) throws SQLException {
                    StatPrizeAnn statPrize = (StatPrizeAnn) lsp.get(index);
                    ps.setInt(1, statPrize.getGame_id());
                    ps.setInt(2, statPrize.getDraw_id());
                    ps.setInt(3, statPrize.getClass_id());
                    ps.setInt(4, statPrize.getPlay_id());
                    ps.setInt(5, statPrize.getOpen_id());
                    ps.setString(6, statPrize.getClass_name());
                    ps.setInt(7, statPrize.getPrize_num());
                    ps.setBigDecimal(8, statPrize.getStake_prize());
                    ps.setInt(9, statPrize.getTotal_num());
                    ps.setInt(10, statPrize.getGame_id());
                    ps.setInt(11, statPrize.getDraw_id());
                    ps.setInt(12, statPrize.getClass_id());
                    ps.setInt(13, statPrize.getPlay_id());
                    ps.setInt(14, statPrize.getOpen_id());
                    ps.setString(15, statPrize.getClass_name());
                    ps.setInt(16, statPrize.getPrize_num());
                    ps.setBigDecimal(17, statPrize.getStake_prize());
                    ps.setInt(18, statPrize.getTotal_num());
                }

                @Override
                public int getBatchSize() {
                    return lsp.size();
                }
            });
        } catch (DataAccessException dataAccessException) {
            logger.error("batch update error", dataAccessException);
            SQLException sqle = (SQLException) dataAccessException.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            return -1;
        }
        return 0;
    }

    /**
     * 指定游戏，期号，开奖次数，奖级编号，更新单注奖金
     *
     * @param jdbcTemplate
     * @param lsp
     * @return
     */
    @Override
    public int updateStatPrizeAnn(JdbcTemplate jdbcTemplate, final List<StatPrizeAnn> lsp) {
        String sql = UPDATE_STATPRIZEANN;
        try {
            //批量操作
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int index) throws SQLException {
                    StatPrizeAnn statPrize = lsp.get(index);
                    ps.setBigDecimal(1, statPrize.getStake_prize());
                    ps.setInt(2, statPrize.getTotal_num());
                    ps.setInt(3, statPrize.getGame_id());
                    ps.setInt(4, statPrize.getDraw_id());
                    ps.setInt(5, statPrize.getOpen_id());
                    ps.setInt(6, statPrize.getClass_id());
                }

                @Override
                public int getBatchSize() {
                    return lsp.size();
                }
            });
        } catch (DataAccessException dataAccessException) {
            logger.error("batch update error", dataAccessException);
            SQLException sqle = (SQLException) dataAccessException.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            return -1;
        }
        return 0;
    }

    /**
     * 指定游戏指定期指定开奖次数，查询中奖汇总数据
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @param openid
     * @return
     */
    @Override
    public List<StatPrizeAnn> selectStatPrize(JdbcTemplate jdbcTemplate, int gameid, int drawid, int open_id) {
        String sql = SELECT_STATPRIZE;
        return this.queryForList(jdbcTemplate, sql, new Object[]{gameid, drawid, open_id}, StatPrizeAnn.class);
    }

    /**
     * 指定游戏指定期指定开奖次数，查询中奖汇总数据
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @param open_ids
     * @return
     */
    @Override
    public List<StatPrizeAnn> selectStatPrizeByOpenIds(JdbcTemplate jdbcTemplate, int gameid, int drawid, String open_ids) {
        String sql = SELECT_STATPRIZE_ByOpenIds;
        return this.queryForList(jdbcTemplate, sql, new Object[]{gameid, drawid, open_ids}, StatPrizeAnn.class);
    }

    /**
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @return
     */
    @Override
    public List<StatPrizeAnn> getStatPrizeAnnListByIds(JdbcTemplate jdbcTemplate, int game_id, int draw_id) {
        String sql = SELECT_STATPRIZEANN_ByIds;
        return this.queryForList(jdbcTemplate, sql, new Object[]{game_id, draw_id}, StatPrizeAnn.class);
    }

    /**
     * 获取普通游戏中奖汇总数据
     *
     * @param jdbcTemplate
     * @return
     */
    @Override
    public List<StatPrizeAnn> getStatPrizeAnnList(JdbcTemplate jdbcTemplate) {
        String sql = SELECT_STATPRIZEANN_LIST;
        return this.queryForList(jdbcTemplate, sql, new Object[]{}, StatPrizeAnn.class);
    }

    /**
     * 删除某游戏某期的中奖数据
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @return
     */
    @Override
    public int deleteStatPrizeAnnByGameDraw(JdbcTemplate jdbcTemplate, int game_id, int draw_id) {
        int result = 0;
        try {
            result = jdbcTemplate.update(DELETE_PRIZE_ANN, new Object[]{game_id, draw_id});
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
     * 指定游戏指定期指定开奖次数，奖级倒序查询中奖汇总数据
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @param open_id
     * @return
     */
    @Override
    public List<StatPrizeAnn> getPrizeByGameAndDrawAndOpenIdDesc(JdbcTemplate jdbcTemplate, int gameid, int drawid, String open_id) {
        String sql = GET_PRIZE_BY_GAME_DRAW_OPENID_DESC;
        return this.queryForList(jdbcTemplate, sql, new Object[]{gameid, drawid, open_id}, StatPrizeAnn.class);
    }

    /**
     * 指定游戏指定期，奖级倒序查询中奖汇总数据
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @return
     */
    @Override
    public List<StatPrizeAnn> getPrizeByGameAndDrawDesc(JdbcTemplate jdbcTemplate, int gameid, int drawid) {
        String sql = GET_PRIZE_BY_GAME_DRAW_DESC;
        return this.queryForList(jdbcTemplate, sql, new Object[]{gameid, drawid}, StatPrizeAnn.class);
    }

    /**
     * 获取某期所有系统的中奖信息<br>
     * 包括贝英斯、联销省份、维赛特<br>
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @return
     */
    @Override
    public List<StatPrizeAnn> getAllSystemPrize(JdbcTemplate jdbcTemplate, int gameid, int drawid) {
        return this.queryForList(jdbcTemplate, GET_ALL_SYSTEM_PRIZE, new Object[]{gameid, drawid, gameid, drawid}, StatPrizeAnn.class);
    }
}
