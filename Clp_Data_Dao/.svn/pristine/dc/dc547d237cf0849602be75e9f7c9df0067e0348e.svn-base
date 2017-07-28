package com.bestinfo.dao.impl.stat;

import com.bestinfo.bean.stat.StatCityDistribution;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.stat.IStatCityDistributionDao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 结算统计表-游戏地市中奖分布(T_stat_city_distribution)
 */
@Repository
public class StatCityDistributionDaoImpl extends BaseDaoImpl implements IStatCityDistributionDao {

    private static final String INSERT_CITY_DISTRIBUTION = "INSERT INTO t_stat_city_distribution"
            + " VALUES"
            + " (?,?,?,?,?,?,?)";

    private static final String GET_CITY_DISTRIBUTION = "SELECT stat.game_id,"
            + "       stat.draw_id,"
            + "       stat.keno_draw_id,"
            + "       tmn.city_id,"
            + "       stat.open_id,"
            + "       stat.class_id,"
            + "       SUM(stat.prize_num) prize_num"
            + "  FROM (SELECT game_id,"
            + "               draw_id,"
            + "               keno_draw_id,"
            + "               open_id,"
            + "               class_id,"
            + "               terminal_id,"
            + "               SUM(prize_num) prize_num"
            + "          FROM t_ticket_prize_class"
            + "         WHERE game_id = ?"
            + "           AND draw_id = ?"
            + "       AND keno_draw_id = ?"
            + "         GROUP BY game_id,"
            + "                  draw_id,"
            + "                  keno_draw_id,"
            + "                  open_id,"
            + "                  class_id,"
            + "                  terminal_id"
            + "         ORDER BY class_id) stat,"
            + "       t_tmn_info tmn"
            + " WHERE stat.terminal_id = tmn.terminal_id"
            + " GROUP BY stat.game_id,"
            + "          stat.draw_id,"
            + "          stat.keno_draw_id,"
            + "          tmn.city_id,"
            + "          stat.open_id,"
            + "          stat.class_id ORDER BY tmn.city_id, stat.class_id";

    private static final String MERGE_CITY_DISTRIBUTION = "merge into t_stat_city_distribution t"
            + " using (select ? game_id,"
            + "              ? draw_id,"
            + "              ? city_id,"
            + "              ? keno_draw_id,"
            + "              ? open_id,"
            + "              ? class_id"
            + "         from dual) s"
            + " on (t.game_id = s.game_id and t.draw_id = s.draw_id and t.city_id = s.city_id and t.keno_draw_id = s.keno_draw_id and t.open_id = s.open_id and t.class_id = s.class_id)"
            + " when matched then"
            + "  update set prize_num = prize_num + ?"
            + " when not matched then"
            + "  insert"
            + "    (game_id, draw_id, city_id, keno_draw_id, open_id, class_id, prize_num)"
            + "  values"
            + "    (?, ?, ?, ?, ?, ?, ?)";

    /**
     * 删除某一keno期的统计数据
     */
    private static final String DELETE_CITY_DISTRIBUTION = "DELETE FROM t_stat_city_distribution WHERE game_id=? AND draw_id=? AND keno_draw_id=?";

    /**
     * 获取地市中奖分布
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    @Override
    public List<StatCityDistribution> getStatCityDistribution(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int keno_draw_id) {
        return queryForList(jdbcTemplate, GET_CITY_DISTRIBUTION, new Object[]{game_id, draw_id, keno_draw_id}, StatCityDistribution.class);
    }

    /**
     * 批量插入地市中奖分布
     *
     * @param jdbcTemplate
     * @param statList
     * @return
     */
    @Override
    public int batchInsertDistribution(JdbcTemplate jdbcTemplate, final List<StatCityDistribution> statList) {
        int result = 0;
        try {
            jdbcTemplate.batchUpdate(INSERT_CITY_DISTRIBUTION, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    StatCityDistribution currentStat = statList.get(i);
                    ps.setInt(1, currentStat.getGame_id());
                    ps.setInt(2, currentStat.getDraw_id());
                    ps.setInt(3, currentStat.getCity_id());
                    ps.setInt(4, currentStat.getKeno_draw_id());
                    ps.setInt(5, currentStat.getOpen_id());
                    ps.setInt(6, currentStat.getClass_id());
                    ps.setInt(7, currentStat.getPrize_num());
                }

                @Override
                public int getBatchSize() {
                    return statList.size();
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
     * 批量merge插入地市中奖分布
     *
     * @param jdbcTemplate
     * @param statList
     * @return
     */
    @Override
    public int mergeDistribution(JdbcTemplate jdbcTemplate, final List<StatCityDistribution> statList) {
        int result = 0;
        try {
            jdbcTemplate.batchUpdate(MERGE_CITY_DISTRIBUTION, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    StatCityDistribution currentStat = statList.get(i);
                    ps.setInt(1, currentStat.getGame_id());
                    ps.setInt(2, currentStat.getDraw_id());
                    ps.setInt(3, currentStat.getCity_id());
                    ps.setInt(4, currentStat.getKeno_draw_id());
                    ps.setInt(5, currentStat.getOpen_id());
                    ps.setInt(6, currentStat.getClass_id());
                    ps.setInt(7, currentStat.getPrize_num());
                    ps.setInt(8, currentStat.getGame_id());
                    ps.setInt(9, currentStat.getDraw_id());
                    ps.setInt(10, currentStat.getCity_id());
                    ps.setInt(11, currentStat.getKeno_draw_id());
                    ps.setInt(12, currentStat.getOpen_id());
                    ps.setInt(13, currentStat.getClass_id());
                    ps.setInt(14, currentStat.getPrize_num());
                }

                @Override
                public int getBatchSize() {
                    return statList.size();
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
     * 删除地市中奖分布
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    @Override
    public int deleteStatCityDistribution(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int keno_draw_id) {
        try {
            return jdbcTemplate.update(DELETE_CITY_DISTRIBUTION, new Object[]{game_id, draw_id, keno_draw_id});
        } catch (Exception e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            return -2;
        }
    }

}
