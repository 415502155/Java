package com.bestinfo.dao.game;

import com.bestinfo.bean.game.GameInfo;
import com.bestinfo.dao.page.Pagination;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author yangyuefu
 */
public interface IGameInfoDao {

    /**
     * 根据游戏id获取游戏
     *
     * @param jdbcTemplate
     * @param gameId
     * @return
     */
    public GameInfo getGameInfoByGameId(JdbcTemplate jdbcTemplate, int gameId);

    /**
     * 根据游戏code获取游戏
     *
     * @param jdbcTemplate
     * @param gameCode
     * @return
     */
    public GameInfo getGameInfoByGameCode(JdbcTemplate jdbcTemplate, String gameCode);

    /**
     * 查询游戏信息数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据
     */
    public List<GameInfo> selectGameInfo(JdbcTemplate jdbcTemplate);

    /**
     * 新增游戏
     *
     * @param jdbcTemplate
     * @param gameInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    public int insertGameInfo(JdbcTemplate jdbcTemplate, GameInfo gameInfo);

    /**
     * 修改游戏
     *
     * @param jdbcTemplate
     * @param gameInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    public int updateGameInfo(JdbcTemplate jdbcTemplate, GameInfo gameInfo);

    /**
     * 游戏信息分页
     *
     * @param jdbcTemplate
     * @param params
     * @return
     */
    public Pagination<GameInfo> getGameInfoPageList(JdbcTemplate jdbcTemplate, Map<String, Object> params);

    /**
     * 根据游戏Id获取该游戏的keno期数
     *
     * @param jdbcTemplate
     * @param gameId
     * @return keno期数值
     */
    public int selectKenoDrawNumById(JdbcTemplate jdbcTemplate, int gameId);

    /**
     * 修改游戏eb
     *
     * @param jdbcTemplate
     * @param gameInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    public int ebupdateGameInfo(JdbcTemplate jdbcTemplate, GameInfo gameInfo);
    /**
     * 更新游戏的当前期号
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @return 
     */
    public int updateCurDrawId(JdbcTemplate jdbcTemplate, int game_id, int draw_id);
}
