package com.bestinfo.dao.impl.app;

import com.bestinfo.bean.game.GameAutoCash;
import com.bestinfo.bean.game.GameKDrawInfo;
import com.bestinfo.dao.app.IAppGameAutoCashDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.define.Draw.DrawProStatus;
import com.bestinfo.define.returncode.TerminalResultCode;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 大厅游戏自动兑奖
 */
@Repository
public class AppGameAutoCashDaoImpl extends BaseDaoImpl implements IAppGameAutoCashDao {

    private static final String UPDATE_GAME_AUTO_CASH = "update t_game_autocash_process_app set complete_status=1,complete_time=sysdate where game_id=? and draw_id=? and keno_draw_id=?";

    private static final String MERGE_GAME_AUTO_CASH = " MERGE INTO t_game_autocash_process_app cash"
            + " USING (SELECT ? game_id, ? draw_id,? keno_draw_id FROM DUAL) src"
            + " ON (cash.game_id = src.game_id AND cash.draw_id = src.draw_id AND cash.keno_draw_id = src.keno_draw_id)"
            + " WHEN MATCHED THEN UPDATE SET complete_status = ?"
            + " WHEN NOT MATCHED THEN INSERT (game_id,draw_id,keno_draw_id,complete_status) VALUES(?,?,?,?)";

    //获取开奖结束但未完成自动兑奖的大期期次
    private static final String GET_UNFINISHED_AUTO_CASH = "SELECT cash.*"
            + " FROM t_game_autocash_process_app cash,t_game_draw_info draw"
            + " WHERE cash.game_id = draw.game_id"
            + "   AND cash.draw_id = draw.draw_id"
            + "   AND draw.process_status = " + DrawProStatus.GETMONEY
            + "   AND cash.complete_status = 0"
            + "   AND draw.keno_draw_num = 0"
            + "   ORDER BY cash.game_id,cash.draw_id";

    //获取某个游戏开奖结束但未完成自动兑奖的快开期次
    private static final String GET_GAME_KENO_UNFINISHED_AUTO_CASH = "SELECT game_id,draw_id,keno_draw_id,complete_status,complete_time"
            + " FROM t_game_autocash_process_app cash"
            + " WHERE cash.game_id = ?"
            + "   AND cash.complete_status = 0"
            + "   ORDER BY cash.game_id,cash.draw_id,cash.keno_draw_id";

    //获取自动兑奖的记录
    private static final String GET_GAME_AUTO_CASH = "SELECT * FROM t_game_autocash_process_app where game_id=? and draw_id=? and keno_draw_id=?";

    /**
     * merge新增兑奖期信息
     *
     * @param jdbcTemplate
     * @param autoCash
     * @return 成功-影响记录数 失败-(-1)
     */
    @Override
    public int mergeGameAutoCash(JdbcTemplate jdbcTemplate, GameAutoCash autoCash) {
        int result;
        try {
            result = jdbcTemplate.update(MERGE_GAME_AUTO_CASH, new Object[]{
                autoCash.getGame_id(),
                autoCash.getDraw_id(),
                autoCash.getKeno_draw_id(),
                0,
                autoCash.getGame_id(),
                autoCash.getDraw_id(),
                autoCash.getKeno_draw_id(),
                0
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
     * 批量merge新增兑奖期信息，针对快开游戏
     *
     * @param jdbcTemplate
     * @param kdrawList
     * @return
     */
    @Override
    public int batchMergeGameAutoCash(JdbcTemplate jdbcTemplate, final List<GameKDrawInfo> kdrawList) {
        try {
            jdbcTemplate.batchUpdate(MERGE_GAME_AUTO_CASH, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    GameKDrawInfo kdraw = kdrawList.get(i);
                    int j = 1;
                    ps.setInt(j++, kdraw.getGame_id());
                    ps.setInt(j++, kdraw.getDraw_id());
                    ps.setInt(j++, kdraw.getKeno_draw_id());
                    ps.setInt(j++, 0);
                    ps.setInt(j++, kdraw.getGame_id());
                    ps.setInt(j++, kdraw.getDraw_id());
                    ps.setInt(j++, kdraw.getKeno_draw_id());
                    ps.setInt(j++, 0);
                }

                @Override
                public int getBatchSize() {
                    return kdrawList.size();
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
     * 更新兑奖期状态为已完成
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @param kenoDrawId
     * @return 成功-影响记录数 失败-(-1)
     */
    @Override
    public int updateAutoCashStatus(JdbcTemplate jdbcTemplate, int gameId, int drawId, int kenoDrawId) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_GAME_AUTO_CASH, new Object[]{gameId, drawId, kenoDrawId});
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
     * 获取未完成自动兑奖的大期信息
     *
     * @param jdbcTemplate
     * @return
     */
    @Override
    public List<GameAutoCash> getUnFinishedCashAuto(JdbcTemplate jdbcTemplate) {
        return queryForList(jdbcTemplate, GET_UNFINISHED_AUTO_CASH, null, GameAutoCash.class);
    }

    /**
     * 获取某个游戏未完成自动兑奖的快开期信息
     *
     * @param jdbcTemplate
     * @param gameId
     * @return
     */
    @Override
    public List<GameAutoCash> getGameKenoUnFinishedCashAuto(JdbcTemplate jdbcTemplate, int gameId) {
        return queryForList(jdbcTemplate, GET_GAME_KENO_UNFINISHED_AUTO_CASH, new Object[]{gameId}, GameAutoCash.class);
    }

    /**
     * App小奖自动兑奖
     *
     * @param jdbcTemplate
     * @param gameID
     * @param drawID
     * @param kenoID
     * @return
     */
    @Override
    public int appAutoCash(JdbcTemplate jdbcTemplate, final int gameID, final int drawID, final int kenoID) {
        String sql = "{call P_H_AUTOMATIC_CASH(?,?,?,?,?)}";
        try {
            Integer errorcode = (Integer) jdbcTemplate.execute(sql, new CallableStatementCallback() {
                @Override
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                    cs.setInt(1, gameID);
                    cs.setInt(2, drawID);
                    cs.setInt(3, kenoID);
                    cs.registerOutParameter(4, Types.VARCHAR);
                    cs.registerOutParameter(5, Types.INTEGER);
                    cs.execute();
                    int errorCode = cs.getInt(5);
                    if (errorCode == 8000) {//没有中奖纪录，返回成功0
                        logger.info("there is no prize record,game_id:" + gameID + ",draw_id:" + drawID + ",keno_draw_id:" + kenoID);
                        errorCode = 0;
                    } else if (errorCode != 0) {
                        logger.error("call P_H_AUTOMATIC_CASH error,error_code:" + errorCode + ",error_msg:" + cs.getString(4));
                    }

                    return Integer.valueOf(errorCode);
                }
            });
            return errorcode;
        } catch (DataAccessException e) {
            logger.error("call P_H_AUTOMATIC_CASH exception", e);
            return TerminalResultCode.daoExceError;
        }
    }

    /**
     * 获取自动兑奖的期信息
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @param kenoDrawId
     * @return
     */
    @Override
    public GameAutoCash getCashAuto(JdbcTemplate jdbcTemplate, final int gameId, final int drawId, final int kenoDrawId) {
        return queryForObject(jdbcTemplate, GET_GAME_AUTO_CASH, new Object[]{gameId, drawId, kenoDrawId}, GameAutoCash.class);
    }
}
