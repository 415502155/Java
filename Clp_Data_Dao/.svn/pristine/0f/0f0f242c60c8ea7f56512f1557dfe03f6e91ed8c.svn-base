package com.bestinfo.dao.impl.ticket;

import com.bestinfo.bean.game.OpenprizeInfo;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.ticket.IOpenPrizeInfoDao;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 公告信息
 */
@Repository
public class OpenPrizeDaoImpl extends BaseDaoImpl implements IOpenPrizeInfoDao {

    private static final String INSERT_OPENPRIZEINFO = "insert into t_openprize_info(game_id,draw_id,keno_draw_id,openprize_info,openprize_md5) values(?,?,?,?,?)";

    private static final String SELECT_OPENPRIZEINFO = "select  game_id, draw_id , keno_draw_id,  openprize_info, openprize_md5 from t_openprize_info where game_id = ? and draw_id = ? and keno_draw_id = ? ";

    private static final String UPDATE_OPENPRIZEINFO = "update  t_openprize_info set openprize_info = ? , openprize_md5= ? where game_id = ? and draw_id = ? and keno_draw_id = ? ";

    private static final String DELETE_OPENPRIZE = "delete from t_openprize_info where game_id=? and draw_id=? and keno_draw_id=?";

    private static final String GET_OPENPRIZEINFO_LIST = "select a.game_id,a.draw_id,a.keno_draw_id , a.openprize_info,a.openprize_md5 from t_openprize_info a , t_game_draw_info b where a.game_id = b.game_id and a.draw_id = b.draw_id and a.game_id =? and b.draw_name between ? and ? ";

    private static final String MERGE_OPENPRIZE = "MERGE INTO t_openprize_info prize USING (SELECT ? game_id, ? draw_id, ? keno_draw_id FROM DUAL) src ON (prize.game_id=src.game_id AND prize.draw_id=src.draw_id AND prize.keno_draw_id=src.keno_draw_id) WHEN MATCHED THEN UPDATE SET openprize_info=?, openprize_md5=? WHEN NOT MATCHED THEN INSERT(game_id,draw_id,keno_draw_id,openprize_info,openprize_md5) VALUES (?,?,?,?,?)";

    /**
     * 新增公告信息
     *
     * @param jdbcTemplate
     * @param openPrizeInfo
     * @return
     */
    @Override
    public int insertOpenPrizeInfo(JdbcTemplate jdbcTemplate, OpenprizeInfo openPrizeInfo) {
        int result = -1;
        try {
            result = jdbcTemplate.update(INSERT_OPENPRIZEINFO, new Object[]{
                openPrizeInfo.getGame_id(),
                openPrizeInfo.getDraw_id(),
                openPrizeInfo.getKeno_draw_id(),
                openPrizeInfo.getOpenprize_info(),
                openPrizeInfo.getOpenprize_md5()});
            result = (result < 0 ? -2 : 0);
            return result;
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
     * 根据游戏、期、keno期获取开奖公告
     *
     * @param jdbcTemplate
     * @param openPrizeInfo
     * @return
     */
    @Override
    public OpenprizeInfo getOpenprizeInfo(JdbcTemplate jdbcTemplate, OpenprizeInfo openPrizeInfo) {
        return this.queryForObject(jdbcTemplate, SELECT_OPENPRIZEINFO,
                new Object[]{openPrizeInfo.getGame_id(), openPrizeInfo.getDraw_id(), openPrizeInfo.getKeno_draw_id()},
                OpenprizeInfo.class);
    }

    /**
     * 修改
     *
     * @param jdbcTemplate
     * @param openPrizeInfo
     * @return
     */
    @Override
    public int updateOpenprizeInfo(JdbcTemplate jdbcTemplate, OpenprizeInfo openPrizeInfo) {
        try {
            int res = -1;
            res = jdbcTemplate.update(UPDATE_OPENPRIZEINFO, new Object[]{
                openPrizeInfo.getOpenprize_info(),
                openPrizeInfo.getOpenprize_md5(),
                openPrizeInfo.getGame_id(),
                openPrizeInfo.getDraw_id(),
                openPrizeInfo.getKeno_draw_id()
            });
            if (res < 0) {
                return -2;
            }
            return 0;
        } catch (Exception ex) {
            logger.error(ex);
            return -1;
        }
    }

    /**
     * 删除某个keno期的开奖公告
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    @Override
    public int deleteOpenprize(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int keno_draw_id) {
        int result = -1;
        try {
            result = jdbcTemplate.update(DELETE_OPENPRIZE, new Object[]{game_id, draw_id, keno_draw_id});
            result = (result < 0 ? -2 : 0);
            return result;
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
     * 获取开奖公告信息列表
     *
     * @param jdbcTemplate
     * @param game_id
     * @param begin_draw_name
     * @param end_draw_name
     * @return
     */
    @Override
    public List<OpenprizeInfo> getOpenprizeInfoList(JdbcTemplate jdbcTemplate, int game_id, String begin_draw_name, String end_draw_name) {
        return this.queryForList(jdbcTemplate, GET_OPENPRIZEINFO_LIST,
                new Object[]{game_id, begin_draw_name, end_draw_name},
                OpenprizeInfo.class);
    }

    /**
     * merge公告信息
     *
     * @param jdbcTemplate
     * @param openPrizeInfo
     * @return
     */
    @Override
    public int mergeOpenprize(JdbcTemplate jdbcTemplate, OpenprizeInfo openPrizeInfo) {
        int result = -1;
        try {
            result = jdbcTemplate.update(MERGE_OPENPRIZE, new Object[]{
                openPrizeInfo.getGame_id(),
                openPrizeInfo.getDraw_id(),
                openPrizeInfo.getKeno_draw_id(),
                openPrizeInfo.getOpenprize_info(),
                openPrizeInfo.getOpenprize_md5(),
                openPrizeInfo.getGame_id(),
                openPrizeInfo.getDraw_id(),
                openPrizeInfo.getKeno_draw_id(),
                openPrizeInfo.getOpenprize_info(),
                openPrizeInfo.getOpenprize_md5()});
            result = (result < 0 ? -2 : 0);
            return result;
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
