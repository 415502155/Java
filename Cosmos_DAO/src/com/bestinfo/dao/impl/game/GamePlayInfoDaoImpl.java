package com.bestinfo.dao.impl.game;

import com.bestinfo.bean.game.GamePlayInfo;
import com.bestinfo.dao.game.IGamePlayInfoDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.page.Pagination;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 游戏玩法
 *
 * @author yangyuefu
 */
@Repository
public class GamePlayInfoDaoImpl extends BaseDaoImpl implements IGamePlayInfoDao {

    private final Logger logger = Logger.getLogger(GamePlayInfoDaoImpl.class);

    /**
     * 根据游戏id获取游戏玩法
     */
    private static final String GET_GAMEPLAYINFO_BY_GAME_ID = "select game_id,play_id,play_name,play_type,stakes_price,max_multiple,bet_no_num,bet_min_no,bet_max_no,blue_no_num,blue_min_no,blue_max_no,no_repeat,no_order,sign_num,prize_proportion,work_status from t_game_play_info where game_id = ?";

    /**
     * 根据玩法id获取游戏玩法
     */
    private static final String GET_GAMEPLAYINFO_BY_PLAY_ID = "select game_id,play_id,play_name,play_type,stakes_price,max_multiple,bet_no_num,bet_min_no,bet_max_no,blue_no_num,blue_min_no,blue_max_no,no_repeat,no_order,sign_num,prize_proportion,work_status from t_game_play_info where play_id = ?";

    /**
     * 根据游戏id和玩法id获取玩法记录
     */
    private static final String GET_GAMEPLAYINFO_BY_GAME_ID_AND_PLAY_ID = "select game_id,play_id,play_name,play_type,stakes_price,max_multiple,bet_no_num,bet_min_no,bet_max_no,blue_no_num,blue_min_no,blue_max_no,no_repeat,no_order,sign_num,prize_proportion,work_status  from t_game_play_info where game_id = ? and play_id = ?";

    /**
     * 查询玩法记录列表
     */
    private static final String GET_GAMEPLAYINFO_LIST = "select game_id,play_id,play_name,play_type,stakes_price,max_multiple,bet_no_num,bet_min_no,bet_max_no,blue_no_num,blue_min_no,blue_max_no,no_repeat,no_order,sign_num,prize_proportion,work_status from t_game_play_info";

    /**
     * 新增游戏玩法
     */
    private static final String INSERT_GAMEPLAYINFO = "insert into(game_id,play_id,play_name,play_type,stakes_price,max_multiple,bet_no_num,bet_min_no,bet_max_no,blue_no_num,blue_min_no,blue_max_no,no_repeat,no_order,sign_num,prize_proportion,work_status) t_game_play_info values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    /**
     * 根据游戏id更新游戏玩法
     */
    private static final String UPDATE_GAMEPLAYINFO = "update t_game_play_info set play_name=?,play_type=?,stakes_price=?,max_multiple=?,bet_no_num=?,bet_min_no=?,bet_max_no=?,blue_no_num=?,blue_min_no=?,blue_max_no=?,no_repeat=?,no_order=?,sign_num=?,prize_proportion=?,work_status=? where game_id=? and play_id=?";

    /**
     * 根据游戏id获取游戏玩法
     *
     * @param jdbcTemplate
     * @param gameId
     * @return
     */
    @Override
    public List<GamePlayInfo> getGamePlayInfoByGameId(JdbcTemplate jdbcTemplate, int gameId) {
        return queryForList(
                jdbcTemplate,
                GET_GAMEPLAYINFO_BY_GAME_ID,
                new Object[]{gameId},
                GamePlayInfo.class);
    }

    /**
     * 根据玩法id获取游戏玩法
     *
     * @param jdbcTemplate
     * @param playId
     * @return
     */
    @Override
    public GamePlayInfo getGamePlayInfoByPlayId(JdbcTemplate jdbcTemplate, int playId) {
        return queryForObject(
                jdbcTemplate,
                GET_GAMEPLAYINFO_BY_PLAY_ID,
                new Object[]{playId},
                GamePlayInfo.class);
    }

    /**
     * 根据游戏id和玩法id获取玩法记录
     *
     * @param jdbcTemplate
     * @param gameId
     * @param playId
     * @return
     */
    @Override
    public GamePlayInfo getPlayByGameIdAndPlayId(JdbcTemplate jdbcTemplate, int gameId, int playId) {
        return queryForObject(
                jdbcTemplate,
                GET_GAMEPLAYINFO_BY_GAME_ID_AND_PLAY_ID,
                new Object[]{gameId, playId},
                GamePlayInfo.class);
    }

    /**
     * 新增游戏玩法
     *
     * @param jdbcTemplate
     * @param gamePlayInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    @Override
    public int insertGamePlayInfo(JdbcTemplate jdbcTemplate, GamePlayInfo gamePlayInfo) {
        int result;
        try {
            result = jdbcTemplate.update(INSERT_GAMEPLAYINFO, new Object[]{
                gamePlayInfo.getGame_id(),
                gamePlayInfo.getPlay_id(),
                gamePlayInfo.getPlay_name(),
                gamePlayInfo.getPlay_type(),
                gamePlayInfo.getStakes_price(),
                gamePlayInfo.getMax_multiple(),
                gamePlayInfo.getBet_no_num(),
                gamePlayInfo.getBet_min_no(),
                gamePlayInfo.getBet_max_no(),
                gamePlayInfo.getBlue_no_num(),
                gamePlayInfo.getBlue_min_no(),
                gamePlayInfo.getBlue_max_no(),
                gamePlayInfo.getNo_repeat(),
                gamePlayInfo.getNo_order(),
                gamePlayInfo.getSign_num(),
                gamePlayInfo.getPrize_proportion(),
                gamePlayInfo.getWork_status()
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
     * 修改游戏玩法
     *
     * @param jdbcTemplate
     * @param gamePlayInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    @Override
    public int updateGamePlayInfo(JdbcTemplate jdbcTemplate, GamePlayInfo gamePlayInfo) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_GAMEPLAYINFO, new Object[]{
                gamePlayInfo.getPlay_name(),
                gamePlayInfo.getPlay_type(),
                gamePlayInfo.getStakes_price(),
                gamePlayInfo.getMax_multiple(),
                gamePlayInfo.getBet_no_num(),
                gamePlayInfo.getBet_min_no(),
                gamePlayInfo.getBet_max_no(),
                gamePlayInfo.getBlue_no_num(),
                gamePlayInfo.getBlue_min_no(),
                gamePlayInfo.getBlue_max_no(),
                gamePlayInfo.getNo_repeat(),
                gamePlayInfo.getNo_order(),
                gamePlayInfo.getSign_num(),
                gamePlayInfo.getPrize_proportion(),
                gamePlayInfo.getWork_status(),
                gamePlayInfo.getGame_id(),
                gamePlayInfo.getPlay_id()
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
     * 获取游戏分页列表
     *
     * @param jdbcTemplate
     * @param params
     * @return
     */
    @Override
    public Pagination<GamePlayInfo> getGamePlayInfoPageList(JdbcTemplate jdbcTemplate, Map<String, Object> params) {
        Map<String, Object> whereMap = getWhereStr(params);

        //sql语句
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM t_game_play_info WHERE 1=1 ");
        sql.append(whereMap.get("whereSql"));

        StringBuilder countSql = new StringBuilder();
        countSql.append("SELECT COUNT(*) FROM t_game_play_info WHERE 1=1 ");
        countSql.append(whereMap.get("whereSql"));

        //参数列表
        Object[] args = null;
        String paramStr = "";
        if (whereMap.get("whereParam") != null) {
            paramStr += whereMap.get("whereParam").toString();
        }
        if (paramStr != null && !"".equals(paramStr)) {
            args = paramStr.split(",");
        }

        Pagination<GamePlayInfo> page = null;
        try {
            page = queryForPage(
                    jdbcTemplate,
                    sql.toString(),
                    countSql.toString(),
                    Integer.parseInt(params.get("pageNumber").toString()),
                    Integer.parseInt(params.get("pageSize").toString()),
                    args,
                    GamePlayInfo.class);
        } catch (NumberFormatException e) {
            logger.error("", e);
        }

        return page;
    }

    /**
     * 根据条件列表拼查询sql
     *
     * @param params
     * @return
     */
    private Map<String, Object> getWhereStr(Map<String, Object> params) {
        StringBuilder whereSql = new StringBuilder("");
        StringBuilder whereParam = new StringBuilder("");

        Object game_id = params.get("game_id");
        if (null != game_id) {
            whereSql.append(" AND game_id = ?");
            whereParam.append(game_id).append(",");
        }

        Object draw_id = params.get("draw_id");
        if (null != draw_id) {
            whereSql.append(" AND draw_id = ?");
            whereParam.append(draw_id).append(",");
        }

        Object play_id = params.get("play_id");
        if (null != play_id) {
            whereSql.append(" AND play_id = ?");
            whereParam.append(play_id).append(",");
        }

        Object play_name = params.get("play_name");
        if (null != play_name) {
            whereSql.append(" AND play_name = ?");
            whereParam.append(play_name).append(",");
        }

        Object play_type = params.get("play_type");
        if (null != play_type) {
            whereSql.append(" AND play_type = ?");
            whereParam.append(play_type).append(",");
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("whereSql", whereSql.toString());
        map.put("whereParam", whereParam.toString());

        return map;
    }

    /**
     * 获取游戏玩法列表信息
     *
     * @param jdbcTemplate
     * @return
     */
    @Override
    public List<GamePlayInfo> selectGamePlayInfo(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, GET_GAMEPLAYINFO_LIST, null, GamePlayInfo.class);
    }

}
