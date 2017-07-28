package com.bestinfo.dao.impl.game;

import com.bestinfo.bean.game.GameMultiOpen;
import com.bestinfo.dao.game.IGameMultiOpenDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 游戏开奖次数
 *
 * @author chenliping
 */
@Repository
public class GameMultiOpenDaoImpl extends BaseDaoImpl implements IGameMultiOpenDao {

//    private final Logger logger = Logger.getLogger(GameMultiOpenDaoImpl.class);
    private static final String INSERT_GAME_MULTIOPEN = "insert into t_multiopen_description(game_id,open_id,open_name,basic_ball_num,special_ball_num,open_blue_num,class_num,work_status) values(?,?,?,?,?,?,?,?)";

    private static final String SELECT_GAME_MULTIOPEN_BYGAMEID = "select game_id,open_id,open_name,basic_ball_num,special_ball_num,open_blue_num,class_num,work_status from t_multiopen_description where game_id=?";

    private static final String UPDATE_GAME_MULTIOPEN = "update t_multiopen_description set open_name=?,basic_ball_num=?,special_ball_num=?,open_blue_num,class_num=?,work_status=? where game_id=? and open_id=?";

    private static final String SELECT_GAME_MULTIOPEN_LIST = "select game_id,open_id,open_name,basic_ball_num,special_ball_num,open_blue_num,class_num,work_status from t_multiopen_description";

    private static final String UPDATE_GAMEMULTIOPEN = "update t_multiopen_description set  open_id = ?, open_name = ?, basic_ball_num = ?, special_ball_num = ?, open_blue_num,class_num = ?, work_status = ? where game_id = ?";

    /**
     * 获取游戏开奖次数信息列表
     *
     * @param jdbcTemplate
     * @param gmo
     * @return
     */
    @Override
    public int insertGameMultiOpen(JdbcTemplate jdbcTemplate, GameMultiOpen gmo) {
        int re = -1;
        try {
            re = jdbcTemplate.update(INSERT_GAME_MULTIOPEN, new Object[]{
                gmo.getGame_id(),
                gmo.getOpen_id(),
                gmo.getOpen_name(),
                gmo.getBasic_ball_num(),
                gmo.getSpecial_ball_num(),
                gmo.getOpen_blue_num(),
                gmo.getClass_num(),
                //                gmo.getClass_num(),
                gmo.getWork_status()
            });
            return re;
        } catch (DataAccessException e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            return -2;
        }
    }

    /**
     * 根据游戏编号获取游戏开奖次数列表信息
     *
     * @param jdbcTemplate
     * @param gameid
     * @return
     */
    @Override
    public List<GameMultiOpen> selectGameMultiOpenByGameid(JdbcTemplate jdbcTemplate, int gameid) {
        return this.queryForList(jdbcTemplate, SELECT_GAME_MULTIOPEN_BYGAMEID, new Object[]{gameid}, GameMultiOpen.class);
    }

    /**
     * 修改游戏开奖次数数据
     *
     * @param jdbcTemplate
     * @param gm
     * @return
     */
    @Override
    public int updateGameMultiOpen(JdbcTemplate jdbcTemplate, GameMultiOpen gm) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_GAME_MULTIOPEN, new Object[]{
                gm.getOpen_name(),
                gm.getBasic_ball_num(),
                gm.getSpecial_ball_num(),
                gm.getOpen_blue_num(),
                gm.getClass_num(),
                //                gm.getClass_num(),
                gm.getWork_status(),
                gm.getGame_id(),
                gm.getOpen_id()
            });
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
     * 查询游戏开奖次数数据列表
     *
     * @param jdbcTemplate
     * @return
     */
    @Override
    public List<GameMultiOpen> selectGameMultiOpenList(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, SELECT_GAME_MULTIOPEN_LIST, null, GameMultiOpen.class);
    }
}
