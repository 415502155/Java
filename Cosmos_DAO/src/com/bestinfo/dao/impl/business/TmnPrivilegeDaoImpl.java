package com.bestinfo.dao.impl.business;

import com.bestinfo.bean.business.TmnPrivilege;
import com.bestinfo.dao.business.ITmnPrivilegeDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 终端特权表
 *
 * @author user
 */
@Repository
public class TmnPrivilegeDaoImpl extends BaseDaoImpl implements ITmnPrivilegeDao {

    /**
     * 缓存同步使用
     */
    private static final String SELECT_TMNPRIVILEGE_LIST = "select terminal_id,game_id,cur_draw_id,sale_permit,cash_permit,undo_permit,game_permit,presell_permit,game_stop,agent_fee_rate,min_bet_money,max_bet_money,max_sales_money,cash_fee_rate from t_tmn_privilege";

//    private static final String UPDATE_TMNPRIVILEGE_BY_PRIKEY = "update t_tmn_privilege set sale_permit = ?, cash_permit = ?, undo_permit = ?, game_permit = ?, presell_permit = ?, game_stop = ?, agent_fee_rate = ?, min_bet_money = ?,  max_bet_money = ?, max_sales_money = ?, cash_fee_rate = ? where terminal_id=? and game_id = ? and cur_draw_id = ?";
    private static final String MERGEINTO_TMNPRIVILEGE_BY_PRIKEY = "merge into t_tmn_privilege tp using (select ? terminal_id, ? game_id, ? cur_draw_id from dual) src on (tp.terminal_id = src.terminal_id and tp.game_id = src.game_id and tp.cur_draw_id = src.cur_draw_id) when matched then update set sale_permit = ?, cash_permit = ?, undo_permit = ?, game_permit = ?, presell_permit = ?, game_stop = ?, agent_fee_rate = ?, min_bet_money = ?,  max_bet_money = ?, max_sales_money = ?, cash_fee_rate = ? when not matched then insert  (terminal_id, game_id, cur_draw_id, sale_permit, cash_permit,  undo_permit, game_permit, presell_permit, game_stop, agent_fee_rate, min_bet_money, max_bet_money, max_sales_money, cash_fee_rate)  values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String INSERT_TMNPRIVILEGE = "insert into t_tmn_privilege(terminal_id, game_id, cur_draw_id, sale_permit, cash_permit, undo_permit, game_permit, presell_permit, game_stop, agent_fee_rate, min_bet_money, max_bet_money, max_sales_money, cash_fee_rate) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String DELETE_TMNPRIVILEGE_BY_ID = "delete from t_tmn_privilege where terminal_id = ?";

    private static final String UPDATE_GAMESTOP = "update t_tmn_privilege set game_stop=? where game_id=?";

    /**
     * 根据game_id获取特权列表
     */
    private static final String SELECT_RIVILEGE_BY_GAME = "select terminal_id,game_id,cur_draw_id,sale_permit,cash_permit,undo_permit,game_permit,presell_permit,game_stop,agent_fee_rate,min_bet_money,max_bet_money,max_sales_money,cash_fee_rate from t_tmn_privilege where game_id=?";

    /**
     * 根据terminal_id获取特权列表
     */
    private static final String SELECT_RIVILEGE_BY_TERMINAL = "select terminal_id,game_id,cur_draw_id,sale_permit,cash_permit,undo_permit,game_permit,presell_permit,game_stop,agent_fee_rate,min_bet_money,max_bet_money,max_sales_money,cash_fee_rate from t_tmn_privilege where terminal_id=?";

    /**
     * 根据game_id、terminal_id获某个终端某个游戏的特权信息
     */
    private static final String GET_RIVILEGE_BY_GAME_TERMINAL = "select terminal_id,game_id,cur_draw_id,sale_permit,cash_permit,undo_permit,game_permit,presell_permit,game_stop,agent_fee_rate,min_bet_money,max_bet_money,max_sales_money,cash_fee_rate from t_tmn_privilege where game_id=? and terminal_id=?";

    /**
     * 查询投注终端特权的数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    @Override
    public List<TmnPrivilege> selectTmnPrivilege(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, SELECT_TMNPRIVILEGE_LIST, null, TmnPrivilege.class);
    }

    /**
     * 根据game_id、terminal_id获取某个终端某个游戏的特权信息
     *
     * @param jdbcTemplate
     * @param game_id
     * @param terminal_id
     * @return 列表数据集合
     */
    @Override
    public TmnPrivilege getPrivilegeByGameAndTerminal(JdbcTemplate jdbcTemplate, int game_id, int terminal_id) {
        return this.queryForObject(jdbcTemplate, GET_RIVILEGE_BY_GAME_TERMINAL, new Object[]{game_id, terminal_id}, TmnPrivilege.class);
    }

    /**
     * 根据game_id查询投注终端特权的数据列表
     *
     * @param jdbcTemplate
     * @param game_id
     * @return 列表数据集合
     */
    @Override
    public List<TmnPrivilege> selectPrivilegeByGame(JdbcTemplate jdbcTemplate, int game_id) {
        return this.queryForList(jdbcTemplate, SELECT_RIVILEGE_BY_GAME, new Object[]{game_id}, TmnPrivilege.class);
    }

    /**
     * 根据terminal_id查询投注终端特权的数据列表
     *
     * @param jdbcTemplate
     * @param terminal_id
     * @return 列表数据集合
     */
    @Override
    public List<TmnPrivilege> selectPrivilegeByTerminal(JdbcTemplate jdbcTemplate, int terminal_id) {
        return this.queryForList(jdbcTemplate, SELECT_RIVILEGE_BY_TERMINAL, new Object[]{terminal_id}, TmnPrivilege.class);
    }

//    /**
//     * 修改投注终端特权数据
//     *
//     * @param jdbcTemplate
//     * @param tp
//     * @return
//     */
//    @Override
//    public int updateTmnPrivilege(JdbcTemplate jdbcTemplate, TmnPrivilege tp) {
//        int result = 0;
//        try {
//            result = jdbcTemplate.update(UPDATE_TMNPRIVILEGE_BY_PRIKEY, new Object[]{
//                tp.getSale_permit(),
//                tp.getCash_permit(),
//                tp.getGame_id(),
//                tp.getPresell_permit(),
//                tp.getGame_stop(),
//                tp.getAgent_fee_rate(),
//                tp.getMin_bet_money(),
//                tp.getMin_bet_money(),
//                tp.getMax_sales_money(),
//                tp.getTerminal_id(),
//                tp.getGame_id(),
//                tp.getCur_draw_id()
//            });
//        } catch (DataAccessException e) {
//            logger.error("modify tmn privilege error where terminalId = " + tp.getTerminal_id() + " and gameid = " + tp.getGame_id() + " and cur_draw_id = " + tp.getCur_draw_id(), e);
//            SQLException sqle = (SQLException) e.getCause();
//            logger.error("Error code: " + sqle.getErrorCode());
//            logger.error("SQL state: " + sqle.getSQLState());
//            result = -1;
//        }
//        return result;
//    }
    /**
     * 批量插入终端特权数据
     *
     * @param jdbcTemplate
     * @param tpList
     * @return 0-成功 -1(失败)
     */
    @Override
    public int insertTmnPrivilege(JdbcTemplate jdbcTemplate, final List<TmnPrivilege> tpList) {
        try {
            jdbcTemplate.batchUpdate(INSERT_TMNPRIVILEGE, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    TmnPrivilege tmnPrivilege = tpList.get(i);
                    ps.setInt(1, tmnPrivilege.getTerminal_id());
                    ps.setInt(2, tmnPrivilege.getGame_id());
                    ps.setInt(3, tmnPrivilege.getCur_draw_id());
                    ps.setInt(4, tmnPrivilege.getSale_permit());
                    ps.setInt(5, tmnPrivilege.getCash_permit());
                    ps.setInt(6, tmnPrivilege.getUndo_permit());
                    ps.setInt(7, tmnPrivilege.getGame_permit());
                    ps.setInt(8, tmnPrivilege.getPresell_permit());
                    ps.setInt(9, tmnPrivilege.getGame_stop());
                    ps.setBigDecimal(10, tmnPrivilege.getAgent_fee_rate());
                    ps.setBigDecimal(11, tmnPrivilege.getMin_bet_money());
                    ps.setBigDecimal(12, tmnPrivilege.getMax_bet_money());
                    ps.setBigDecimal(13, tmnPrivilege.getMax_sales_money());
                    ps.setBigDecimal(14, tmnPrivilege.getCash_fee_rate());
                }

                @Override
                public int getBatchSize() {
                    return tpList.size();
                }
            });
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -1;
        }
    }

    /**
     * 批量修改终端特权数据
     *
     * @param jdbcTemplate
     * @param tpList
     * @return 0-成功 -1(失败)
     */
    @Override
    public int updateBatchTmnPrivilege(JdbcTemplate jdbcTemplate, final List<TmnPrivilege> tpList) {
        try {
            jdbcTemplate.batchUpdate(MERGEINTO_TMNPRIVILEGE_BY_PRIKEY, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    TmnPrivilege tmnPrivilege = tpList.get(i);
                    ps.setInt(1, tmnPrivilege.getTerminal_id());
                    ps.setInt(2, tmnPrivilege.getGame_id());
                    ps.setInt(3, tmnPrivilege.getCur_draw_id());
                    ps.setInt(4, tmnPrivilege.getSale_permit());
                    ps.setInt(5, tmnPrivilege.getCash_permit());
                    ps.setInt(6, tmnPrivilege.getUndo_permit());
                    ps.setInt(7, tmnPrivilege.getGame_permit());
                    ps.setInt(8, tmnPrivilege.getPresell_permit());
                    ps.setInt(9, tmnPrivilege.getGame_stop());
                    ps.setBigDecimal(10, tmnPrivilege.getAgent_fee_rate());
                    ps.setBigDecimal(11, tmnPrivilege.getMin_bet_money());
                    ps.setBigDecimal(12, tmnPrivilege.getMax_bet_money());
                    ps.setBigDecimal(13, tmnPrivilege.getMax_sales_money());
                    ps.setBigDecimal(14, tmnPrivilege.getCash_fee_rate());
                    ps.setInt(15, tmnPrivilege.getTerminal_id());
                    ps.setInt(16, tmnPrivilege.getGame_id());
                    ps.setInt(17, tmnPrivilege.getCur_draw_id());
                    ps.setInt(18, tmnPrivilege.getSale_permit());
                    ps.setInt(19, tmnPrivilege.getCash_permit());
                    ps.setInt(20, tmnPrivilege.getUndo_permit());
                    ps.setInt(21, tmnPrivilege.getGame_permit());
                    ps.setInt(22, tmnPrivilege.getPresell_permit());
                    ps.setInt(23, tmnPrivilege.getGame_stop());
                    ps.setBigDecimal(24, tmnPrivilege.getAgent_fee_rate());
                    ps.setBigDecimal(25, tmnPrivilege.getMin_bet_money());
                    ps.setBigDecimal(26, tmnPrivilege.getMax_bet_money());
                    ps.setBigDecimal(27, tmnPrivilege.getMax_sales_money());
                    ps.setBigDecimal(28, tmnPrivilege.getCash_fee_rate());
                }

                @Override
                public int getBatchSize() {
                    return tpList.size();
                }
            });
            return 0;
        } catch (Exception e) {
            logger.error("when batch modify tmn privilege error where terminalId = " + tpList.get(0).getTerminal_id(), e);
            return -1;
        }
    }

    /**
     * 根据投注机号删除它下面的特权数据
     *
     * @param jdbcTemplate
     * @param tmnId
     * @return >0(成功) -1（失败）
     */
    @Override
    public int deleteTmnPrivilegeByTmnId(JdbcTemplate jdbcTemplate, int tmnId) {
        int result;
        try {
            result = jdbcTemplate.update(DELETE_TMNPRIVILEGE_BY_ID, new Object[]{tmnId});
        } catch (DataAccessException e) {
            logger.error("terminal_id:" + tmnId, e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

    /**
     * 更新开奖封机状态
     *
     * @param jdbcTemplate
     * @param game_id
     * @param gameStop
     * @return
     */
    @Override
    public int updateGameStop(JdbcTemplate jdbcTemplate, int game_id, int gameStop) {
        String sql = UPDATE_GAMESTOP;
        int result;
        try {
            result = jdbcTemplate.update(sql, new Object[]{gameStop, game_id});
        } catch (DataAccessException e) {
            logger.error("game_id:" + game_id, e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

}
