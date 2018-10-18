package com.bestinfo.dao.impl.game;

import com.bestinfo.bean.game.GameClassInfo;
import com.bestinfo.dao.game.IGameClassInfoDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 奖级
 *
 * @author hhhh
 */
@Repository
public class GameClassInfoDaoImpl extends BaseDaoImpl implements IGameClassInfoDao {

//    private final Logger logger = Logger.getLogger(GameClassInfoDaoImpl.class);
    /**
     * 查询奖级列表数据sql
     */
    private static final String GET_GAMECLASSINFO_List = "select game_id,play_id,class_id,class_name,fix_mark,prize_proportion,last_relation,last_diff,top_money,open_id,work_status from t_game_class_info";
    /**
     * 根据gameId查询奖级列表数据sql
     */
    private static final String GET_GAMECLASSINFO_LIST_BY_GAMEID = "select game_id,play_id,class_id,class_name,fix_mark,prize_proportion,last_relation,last_diff,top_money,open_id,work_status from t_game_class_info where game_id=? order by play_id,class_id ";

    /**
     * 根据gameId,playId查询奖级列表数据sql
     */
    private static final String GET_GAMECLASSINFO_LIST_BY_GAMEPLAYID = "select game_id,play_id,class_id,class_name,fix_mark,prize_proportion,last_relation,last_diff,top_money,open_id,work_status from t_game_class_info  where game_id = ? and play_id = ? order by class_id";

    /**
     * 根据游戏id、玩法id、奖级id更新奖级sql
     */
    private static final String UPDATE_GAMECLASSINFO = "update t_game_class_info set class_name = ?,fix_mark = ?,prize_proportion = ?,last_relation = ?,last_diff = ?,work_status = ?,top_money = ?,open_id = ? where game_id = ? and play_id = ? and class_id = ?";

    /**
     * 根据游戏id、玩法id、奖级id删除奖级sql
     */
    private static final String DELETE_GAMECLASSINFO = "delete from t_game_class_info  where game_id = ? and play_id = ? and class_id = ?";

    /**
     * 新增游戏sql
     */
    private static final String INSERT_GAMECLASSINFO = "insert into t_game_class_info(game_id,play_id,class_id,class_name,fix_mark,prize_proportion,last_relation,last_diff,top_money,open_id,work_status) values(?,?,?,?,?,?,?,?,?,?,?)";

    /**
     * 根据游戏id,玩法id,奖级id获取游戏奖级sql
     */
    private static final String GET_GAMECLASSINFO_BY_GAMEPLAYCLASS_ID = "select game_id,play_id,class_id,class_name,fix_mark,prize_proportion,last_relation,last_diff,top_money,open_id,work_status from t_game_class_info where game_id=?  and play_id=? and class_id=?";

    /**
     * 根据游戏id,玩法id获取游戏奖级
     */
    private static final String GET_CLASS_BY_GAME_CLASS = "select game_id,play_id,class_id,class_name,fix_mark,prize_proportion,last_relation,last_diff,top_money,open_id,work_status from t_game_class_info where game_id = ? and class_id = ?";

    /**
     * 删除奖级
     *
     * @param jdbcTemplate
     * @param gameId
     * @param playId
     * @param classId
     * @return 成功-影响记录数 失败-(-1)
     */
    @Override
    public int deleteGameClassInfo(JdbcTemplate jdbcTemplate, int gameId, int playId, int classId) {
        int result;
        try {
            result = jdbcTemplate.update(DELETE_GAMECLASSINFO, new Object[]{gameId, playId, classId});
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
     * 修改奖级
     *
     * @param jdbcTemplate
     * @param gameClassInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    @Override
    public int updateGameClassInfo(JdbcTemplate jdbcTemplate, GameClassInfo gameClassInfo) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_GAMECLASSINFO, new Object[]{
                gameClassInfo.getClass_name(),
                gameClassInfo.getFix_mark(),
                gameClassInfo.getPrize_proportion(),
                gameClassInfo.getLast_relation(),
                gameClassInfo.getLast_diff(),
                gameClassInfo.getWork_status(),
                gameClassInfo.getTop_money(),
                gameClassInfo.getOpen_id(),
                gameClassInfo.getGame_id(),
                gameClassInfo.getPlay_id(),
                gameClassInfo.getClass_id()
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
     * 获取游戏奖级列表
     *
     * @param jdbcTemplate
     * @return 列表
     */
    @Override
    public List<GameClassInfo> selectGameClassInfo(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, GET_GAMECLASSINFO_List, null, GameClassInfo.class);
    }

    /**
     * 根据gameId获取游戏奖级列表
     *
     * @param jdbcTemplate
     * @param gameId
     * @return 列表
     */
    @Override
    public List<GameClassInfo> selectGameClassInfoByGameId(JdbcTemplate jdbcTemplate, int gameId) {
        return this.queryForList(jdbcTemplate,
                GET_GAMECLASSINFO_LIST_BY_GAMEID,
                new Object[]{gameId},
                GameClassInfo.class);
    }

    /**
     * 根据gameId,playId获取游戏奖级列表
     *
     * @param jdbcTemplate
     * @param gameId
     * @return 列表
     */
    @Override
    public List<GameClassInfo> selectGameClassInfoByGamePlayId(JdbcTemplate jdbcTemplate, int gameId, int playId) {
        return this.queryForList(jdbcTemplate,
                GET_GAMECLASSINFO_LIST_BY_GAMEPLAYID,
                new Object[]{gameId, playId},
                GameClassInfo.class);
    }

    /**
     * 新增游戏奖级
     *
     * @param jdbcTemplate
     * @param gameClassInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    @Override
    public int insertGameClassInfo(JdbcTemplate jdbcTemplate, GameClassInfo gameClassInfo) {
        int result;
        try {
            result = jdbcTemplate.update(INSERT_GAMECLASSINFO, new Object[]{
                gameClassInfo.getGame_id(),
                gameClassInfo.getPlay_id(),
                gameClassInfo.getClass_id(),
                gameClassInfo.getClass_name(),
                gameClassInfo.getFix_mark(),
                gameClassInfo.getPrize_proportion(),
                gameClassInfo.getLast_relation(),
                gameClassInfo.getLast_diff(),
                gameClassInfo.getWork_status(),
                gameClassInfo.getTop_money(),
                gameClassInfo.getOpen_id()

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
     * 根据游戏id,玩法id,奖级id获取游戏奖级
     *
     * @param jdbcTemplate
     * @param gameId
     * @param playId
     * @param classId
     * @return GameClassInfo
     */
    @Override
    public GameClassInfo getGameClassInfoByGamePlayClassId(JdbcTemplate jdbcTemplate, int gameId, int playId, int classId) {
        return queryForObject(jdbcTemplate,
                GET_GAMECLASSINFO_BY_GAMEPLAYCLASS_ID,
                new Object[]{gameId, playId, classId},
                GameClassInfo.class);
    }

    /**
     * 根据游戏id,奖级id获取游戏奖级
     *
     * @param jdbcTemplate
     * @param gameId
     * @param classId
     * @return GameClassInfo
     */
    @Override
    public GameClassInfo getClassByGameAndClass(JdbcTemplate jdbcTemplate, int gameId, int classId) {
        return queryForObject(jdbcTemplate,
                GET_CLASS_BY_GAME_CLASS,
                new Object[]{gameId, classId},
                GameClassInfo.class);
    }
}
