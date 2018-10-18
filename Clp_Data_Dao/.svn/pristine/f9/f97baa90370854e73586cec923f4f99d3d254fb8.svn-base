package com.bestinfo.dao.impl.stat;

import com.bestinfo.bean.stat.StatKenoPrizeAnn;
import com.bestinfo.bean.stat.StatPrizeAnn;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.stat.IStatKenoPrizeAnnDao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 结算统计表-快开游戏中奖汇总(T_stat_keno_prize_ann)
 *
 * @author chenliping
 */
@Repository
public class StatKenoPrizeAnnDaoImpl extends BaseDaoImpl implements IStatKenoPrizeAnnDao {

//    private final Logger logger = Logger.getLogger(StatKenoPrizeAnnDaoImpl.class);
    private static final String INSERT_STATPRIZE = "insert into t_stat_keno_prize_ann(game_id,draw_id,keno_draw_id,play_id,class_id,class_name,prize_num,stake_prize,total_num) values(?,?,?,?,?,?,?,?,?)";

    private static final String SELECT_STATPRIZE = "select game_id,draw_id,keno_draw_id,play_id,class_id,class_name,prize_num,stake_prize,total_num from t_stat_keno_prize_ann where game_id=? and draw_id=? and keno_draw_id=? order by class_id";

    private static final String UPDATE_STATPRIZEANN = "update t_stat_keno_prize_ann set stake_prize=? where game_id=? and draw_id=? and keno_draw_id=? and class_id=?";

    /**
     * 根据game_id、draw_id统计总中奖额
     */
    private static final String SUM_PRIZE_BY_GAME_DRAW = "select game_id, draw_id, sum(prize_num * stake_prize) total_prize  from t_stat_keno_prize_ann where game_id = ? and draw_id = ? group by game_id, draw_id";

    /**
     * 根据game_id、draw_id统计当天所有keno期中奖注数、联合销售中奖数量的总和
     */
    private static final String SUM_PRIZE_SUM_BY_GAME_DRAW = "select game_id,draw_id,play_id,class_id,class_name,sum(prize_num) prize_num,stake_prize,sum(total_num) total_num from t_stat_keno_prize_ann where game_id = ? and draw_id = ? group by game_id,draw_id,play_id,class_id,class_name,stake_prize";

    private static final String SUM_PRIZEBY_GAME_DRAW_KENODRAW = " select sum(prize_num * stake_prize) kenototalmoney from t_stat_keno_prize_ann where game_id = ?  and draw_id=? and keno_draw_id=?";

    /**
     * 删除某一keno期的中奖数据
     */
    private static final String DELETE_PRIZE_ANN = "delete from t_stat_keno_prize_ann where game_id=? and draw_id=? and keno_draw_id=?";

    private static final String SELECT_STATKENOPRIZEANN_LIST = "select game_id,draw_id,keno_draw_id,play_id,class_id,class_name,prize_num,stake_prize,total_num from t_stat_keno_prize_ann";

    /**
     * 普通游戏中奖汇总批量写入
     *
     * @param jdbcTemplate
     * @param lsp
     */
    @Override
    public int addStatPrizeAnn(JdbcTemplate jdbcTemplate, final List<StatKenoPrizeAnn> lsp) {
        String sql = INSERT_STATPRIZE;
        try {
            //批量操作
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int index) throws SQLException {
                    StatKenoPrizeAnn statPrize = (StatKenoPrizeAnn) lsp.get(index);
                    ps.setInt(1, statPrize.getGame_id());
                    ps.setInt(2, statPrize.getDraw_id());
                    ps.setInt(3, statPrize.getKeno_draw_id());
                    ps.setInt(4, statPrize.getPlay_id());
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
     * 指定游戏，期号，开奖次数，奖级编号，更新单注奖金
     *
     * @param jdbcTemplate
     * @param lsp
     * @return
     */
    @Override
    public int updateStatPrizeAnn(JdbcTemplate jdbcTemplate, final List<StatKenoPrizeAnn> lsp) {
        String sql = UPDATE_STATPRIZEANN;

        try {
            //批量操作
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int index) throws SQLException {
                    StatKenoPrizeAnn statPrize = lsp.get(index);
                    ps.setBigDecimal(1, statPrize.getStake_prize());
                    ps.setInt(2, statPrize.getGame_id());
                    ps.setInt(3, statPrize.getDraw_id());
                    ps.setInt(4, statPrize.getKeno_draw_id());
                    ps.setInt(5, statPrize.getClass_id());
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
     * @param keno_draw_id
     * @return
     */
    @Override
    public List<StatKenoPrizeAnn> selectStatPrize(JdbcTemplate jdbcTemplate, int gameid, int drawid, int keno_draw_id) {
        String sql = SELECT_STATPRIZE;
        return this.queryForList(jdbcTemplate, sql, new Object[]{gameid, drawid, keno_draw_id}, StatKenoPrizeAnn.class);
    }

    /**
     * 根据game_id、draw_id统计总中奖额
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @return
     */
    @Override
    public StatKenoPrizeAnn sumPrizeByGameAndDraw(JdbcTemplate jdbcTemplate, int gameid, int drawid) {
        String sql = SUM_PRIZE_BY_GAME_DRAW;
        return this.queryForObject(jdbcTemplate, sql, new Object[]{gameid, drawid}, StatKenoPrizeAnn.class);
    }

    /**
     * 本期风采总中奖金额
     *
     * @param jdbcTemplate
     * @param gameid
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    @Override
    public Integer getKenoTotalMoney(JdbcTemplate jdbcTemplate, int gameid, int draw_id, int keno_draw_id) {
        String sql = SUM_PRIZEBY_GAME_DRAW_KENODRAW;
        return this.queryForInteger(jdbcTemplate, sql, new Object[]{gameid, draw_id, keno_draw_id});
    }

    /**
     * 删除某个keno期的中奖数据
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    @Override
    public Integer deletePrizeAnn(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int keno_draw_id) {
        int result = 0;
        try {
            result = jdbcTemplate.update(DELETE_PRIZE_ANN, new Object[]{game_id, draw_id, keno_draw_id});
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
     * 获取基础库里的普通游戏中奖汇总数据
     *
     * @param jdbcTemplate
     * @return
     */
    @Override
    public List<StatKenoPrizeAnn> getStatKenoPrizeAnnList(JdbcTemplate jdbcTemplate) {
        String sql = SELECT_STATKENOPRIZEANN_LIST;
        return this.queryForList(jdbcTemplate, sql, new Object[]{}, StatKenoPrizeAnn.class);
    }

    /**
     * 将快开游戏中奖汇总数据统计计算到普通游戏中奖汇总数据表中
     *
     * @param jdbcTemplate
     * @param gameid
     * @param draw_id
     * @return
     */
    @Override
    public List<StatPrizeAnn> getStatPrizeAnnList(JdbcTemplate jdbcTemplate, int gameid, int draw_id) {
        return this.queryForList(jdbcTemplate, SUM_PRIZE_SUM_BY_GAME_DRAW, new Object[]{gameid, draw_id}, StatPrizeAnn.class);
    }

}
