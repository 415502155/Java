package com.bestinfo.dao.impl.stat;

import com.bestinfo.bean.stat.StatCityDraw;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.stat.IStatCityDrawDao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 结算统计表-地市期(T_stat_city_draw)
 *
 * @author yangyuefu
 */
@Repository
public class StatCityDrawDaoImpl extends BaseDaoImpl implements IStatCityDrawDao {

//    private final Logger logger = Logger.getLogger(StatCityDrawDaoImpl.class);
    /**
     * 插入StatCityDraw
     */
    private static final String INSERT_STAT = "insert into t_stat_city_draw (game_id,draw_id,city_id,sale_money,sale_ticket_num,sale_stake_num,undo_money,undo_ticket_num,undo_stake_num,cash_money,cash_ticket_num,cash_stake_num,agent_fee_deduct,agent_fee,welfare_money_clp,welfare_money_center,welfare_money_city,issue_money_clp,issue_money_center,issue_money_city,return_money,reserve_money,return_fee,cash_fee) values(?,?,?,?, ?,?,?,?, ?,?,?,?, ?,?,?,?, ?,?,?,?, ?,?,?,?)";
    private static final String INSERT_STAT_MERGE = "merge into t_stat_city_draw t using (select ? game_id,? draw_id,? city_id from dual) s on (t.game_id=s.game_id and t.draw_id=s.draw_id and t.city_id=s.city_id) when matched then update set sale_money=?,sale_ticket_num=?,sale_stake_num=?,undo_money=?,undo_ticket_num=?,undo_stake_num=?,cash_money=?,cash_ticket_num=?,cash_stake_num=?,agent_fee_deduct=?,agent_fee=?,welfare_money_clp=?,welfare_money_center=?,welfare_money_city=?,issue_money_clp=?,issue_money_center=?,issue_money_city=?,return_money=?,reserve_money=?,return_fee=?,cash_fee=? when not matched then insert (game_id,draw_id,city_id,sale_money,sale_ticket_num,sale_stake_num,undo_money,undo_ticket_num,undo_stake_num,cash_money,cash_ticket_num,cash_stake_num,agent_fee_deduct,agent_fee,welfare_money_clp,welfare_money_center,welfare_money_city,issue_money_clp,issue_money_center,issue_money_city,return_money,reserve_money,return_fee,cash_fee) values(?,?,?,?, ?,?,?,?, ?,?,?,?, ?,?,?,?, ?,?,?,?, ?,?,?,?)";
    
    /**
     * 根据主键获取记录
     */
    private static final String GET_STAT_BY_GAME_DRAW_CITY = "select game_id,draw_id,city_id,sale_money,sale_ticket_num,sale_stake_num,undo_money,undo_ticket_num,undo_stake_num,cash_money,cash_ticket_num,cash_stake_num,agent_fee_deduct,agent_fee,welfare_money_clp,welfare_money_center,welfare_money_city,issue_money_clp,issue_money_center,issue_money_city,return_money,reserve_money,return_fee,cash_fee from t_stat_city_draw  where game_id = ? and draw_id = ? and city_id = ?";

    /**
     * 新增地级市信息
     *
     * @param jdbcTemplate
     * @param statCityDraw
     * @return
     */
    @Override
    public int insertStatCityDraw(JdbcTemplate jdbcTemplate, StatCityDraw statCityDraw) {
        int result = 0;
        try {
            result = jdbcTemplate.update(INSERT_STAT, new Object[]{
                statCityDraw.getGame_id(),
                statCityDraw.getDraw_id(),
                statCityDraw.getCity_id(),
                statCityDraw.getSale_money(),
                statCityDraw.getSale_ticket_num(),
                statCityDraw.getSale_stake_num(),
                statCityDraw.getUndo_money(),
                statCityDraw.getUndo_ticket_num(),
                statCityDraw.getUndo_stake_num(),
                statCityDraw.getCash_money(),
                statCityDraw.getCash_ticket_num(),
                statCityDraw.getCash_stake_num(),
                statCityDraw.getAgent_fee_deduct(),
                statCityDraw.getAgent_fee(),
                statCityDraw.getWelfare_money_clp(),
                statCityDraw.getWelfare_money_center(),
                statCityDraw.getWelfare_money_city(),
                statCityDraw.getIssue_money_clp(),
                statCityDraw.getIssue_money_center(),
                statCityDraw.getIssue_money_city(),
                statCityDraw.getReturn_money(),
                statCityDraw.getReserve_money(),
                statCityDraw.getReturn_fee(),
                statCityDraw.getCash_fee()
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
     * 批量插入地市统计记录
     *
     * @param jdbcTemplate
     * @param statCityDrawList
     * @return
     */
    @Override
    public int batchInsertStatCityDraw(JdbcTemplate jdbcTemplate, final List<StatCityDraw> statCityDrawList) {
        int result = 0;
        try {
            jdbcTemplate.batchUpdate(INSERT_STAT, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    StatCityDraw statCityDraw = statCityDrawList.get(i);
                    ps.setInt(1, statCityDraw.getGame_id());
                    ps.setInt(2, statCityDraw.getDraw_id());
                    ps.setInt(3, statCityDraw.getCity_id());
                    ps.setBigDecimal(4, statCityDraw.getSale_money());
                    ps.setLong(5, statCityDraw.getSale_ticket_num());
                    ps.setLong(6, statCityDraw.getSale_stake_num());
                    ps.setBigDecimal(7, statCityDraw.getUndo_money());
                    ps.setLong(8, statCityDraw.getUndo_ticket_num());
                    ps.setLong(9, statCityDraw.getUndo_stake_num());
                    ps.setBigDecimal(10, statCityDraw.getCash_money());
                    ps.setLong(11, statCityDraw.getCash_ticket_num());
                    ps.setLong(12, statCityDraw.getCash_stake_num());
                    ps.setBigDecimal(13, statCityDraw.getAgent_fee_deduct());
                    ps.setBigDecimal(14, statCityDraw.getAgent_fee());
                    ps.setBigDecimal(15, statCityDraw.getWelfare_money_clp());
                    ps.setBigDecimal(16, statCityDraw.getWelfare_money_center());
                    ps.setBigDecimal(17, statCityDraw.getWelfare_money_city());
                    ps.setBigDecimal(18, statCityDraw.getIssue_money_clp());
                    ps.setBigDecimal(19, statCityDraw.getIssue_money_center());
                    ps.setBigDecimal(20, statCityDraw.getIssue_money_city());
                    ps.setBigDecimal(21, statCityDraw.getReturn_money());
                    ps.setBigDecimal(22, statCityDraw.getReserve_money());
                    ps.setBigDecimal(23, statCityDraw.getReturn_fee());
                    ps.setBigDecimal(24, statCityDraw.getCash_fee());
                }

                @Override
                public int getBatchSize() {
                    return statCityDrawList.size();
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
     * 批量插入地市统计记录
     *
     * @param jdbcTemplate
     * @param statCityDrawList
     * @return
     */
    @Override
    public int batchInsertStatCityDrawMerge(JdbcTemplate jdbcTemplate, final List<StatCityDraw> statCityDrawList) {
        int result = 0;
        try {
            jdbcTemplate.batchUpdate(INSERT_STAT_MERGE, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    StatCityDraw statCityDraw = statCityDrawList.get(i);
                    ps.setInt(1, statCityDraw.getGame_id());
                    ps.setInt(2, statCityDraw.getDraw_id());
                    ps.setInt(3, statCityDraw.getCity_id());
                    ps.setBigDecimal(4, statCityDraw.getSale_money());
                    ps.setLong(5, statCityDraw.getSale_ticket_num());
                    ps.setLong(6, statCityDraw.getSale_stake_num());
                    ps.setBigDecimal(7, statCityDraw.getUndo_money());
                    ps.setLong(8, statCityDraw.getUndo_ticket_num());
                    ps.setLong(9, statCityDraw.getUndo_stake_num());
                    ps.setBigDecimal(10, statCityDraw.getCash_money());
                    ps.setLong(11, statCityDraw.getCash_ticket_num());
                    ps.setLong(12, statCityDraw.getCash_stake_num());
                    ps.setBigDecimal(13, statCityDraw.getAgent_fee_deduct());
                    ps.setBigDecimal(14, statCityDraw.getAgent_fee());
                    ps.setBigDecimal(15, statCityDraw.getWelfare_money_clp());
                    ps.setBigDecimal(16, statCityDraw.getWelfare_money_center());
                    ps.setBigDecimal(17, statCityDraw.getWelfare_money_city());
                    ps.setBigDecimal(18, statCityDraw.getIssue_money_clp());
                    ps.setBigDecimal(19, statCityDraw.getIssue_money_center());
                    ps.setBigDecimal(20, statCityDraw.getIssue_money_city());
                    ps.setBigDecimal(21, statCityDraw.getReturn_money());
                    ps.setBigDecimal(22, statCityDraw.getReserve_money());
                    ps.setBigDecimal(23, statCityDraw.getReturn_fee());
                    ps.setBigDecimal(24, statCityDraw.getCash_fee());
                    ps.setInt(25, statCityDraw.getGame_id());
                    ps.setInt(26, statCityDraw.getDraw_id());
                    ps.setInt(27, statCityDraw.getCity_id());
                    ps.setBigDecimal(28, statCityDraw.getSale_money());
                    ps.setLong(29, statCityDraw.getSale_ticket_num());
                    ps.setLong(30, statCityDraw.getSale_stake_num());
                    ps.setBigDecimal(31, statCityDraw.getUndo_money());
                    ps.setLong(32, statCityDraw.getUndo_ticket_num());
                    ps.setLong(33, statCityDraw.getUndo_stake_num());
                    ps.setBigDecimal(34, statCityDraw.getCash_money());
                    ps.setLong(35, statCityDraw.getCash_ticket_num());
                    ps.setLong(36, statCityDraw.getCash_stake_num());
                    ps.setBigDecimal(37, statCityDraw.getAgent_fee_deduct());
                    ps.setBigDecimal(38, statCityDraw.getAgent_fee());
                    ps.setBigDecimal(39, statCityDraw.getWelfare_money_clp());
                    ps.setBigDecimal(40, statCityDraw.getWelfare_money_center());
                    ps.setBigDecimal(41, statCityDraw.getWelfare_money_city());
                    ps.setBigDecimal(42, statCityDraw.getIssue_money_clp());
                    ps.setBigDecimal(43, statCityDraw.getIssue_money_center());
                    ps.setBigDecimal(44, statCityDraw.getIssue_money_city());
                    ps.setBigDecimal(45, statCityDraw.getReturn_money());
                    ps.setBigDecimal(46, statCityDraw.getReserve_money());
                    ps.setBigDecimal(47, statCityDraw.getReturn_fee());
                    ps.setBigDecimal(48, statCityDraw.getCash_fee());
                }

                @Override
                public int getBatchSize() {
                    return statCityDrawList.size();
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
     * 获取地级市信息
     *
     * @param jdebTemplate
     * @param game_id
     * @param draw_id
     * @param city_id
     * @return
     */
    @Override
    public StatCityDraw getStatById(JdbcTemplate jdebTemplate, int game_id, int draw_id, int city_id) {
        return this.queryForObject(jdebTemplate,
                GET_STAT_BY_GAME_DRAW_CITY,
                new Object[]{game_id, draw_id, city_id},
                StatCityDraw.class);
    }

}
