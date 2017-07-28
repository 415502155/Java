package com.bestinfo.dao.impl.game;

import com.bestinfo.bean.game.GameSignInfo;
import com.bestinfo.dao.game.IGameSignInfoDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 投注符号
 *
 * @author hhhh
 */
@Repository
public class GameSignInfoDaoImpl extends BaseDaoImpl implements IGameSignInfoDao {

//    private final Logger logger = Logger.getLogger(GameSignInfoDaoImpl.class);
    /**
     * 查询投注符号列表数据sql
     */
    private static final String GET_GAMESIGNINFO_List = "select game_id,draw_id,play_id,sign_id,bet_sign,no_string,no_num,no_len,bet_min_no,bet_max_no,no_diff,work_status,bet_area from t_game_sign_info";

    /**
     * 根据游戏id、玩法id、期号id、符号id更新投注符号sql
     */
    private static final String UPDATE_GAMESIGNINFO = "update t_game_sign_info set bet_sign = ?,no_string = ?,no_num = ?,no_len = ?, bet_min_no = ?,bet_max_no = ?,no_diff = ?,work_status = ?,bet_area=?  where game_id = ? and draw_id = ? and play_id = ? and sign_id = ?";

    /**
     * 修改投注符号
     *
     * @param jdbcTemplate
     * @param gameSignInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    @Override
    public int updateGameSignInfo(JdbcTemplate jdbcTemplate, GameSignInfo gameSignInfo) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_GAMESIGNINFO, new Object[]{
                gameSignInfo.getBet_sign(),
                gameSignInfo.getNo_string(),
                gameSignInfo.getNo_num(),
                gameSignInfo.getNo_len(),
                gameSignInfo.getBet_min_no(),
                gameSignInfo.getBet_max_no(),
                gameSignInfo.getNo_diff(),
                gameSignInfo.getWork_status(),
                gameSignInfo.getBet_area(),
                gameSignInfo.getGame_id(),
                gameSignInfo.getDraw_id(),
                gameSignInfo.getPlay_id(),
                gameSignInfo.getSign_id()
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
     * 获取投注符号信息列表
     *
     * @param jdbcTemplate
     * @return
     */
    @Override
    public List<GameSignInfo> selectGameSignInfo(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, GET_GAMESIGNINFO_List, null, GameSignInfo.class);
    }

}
