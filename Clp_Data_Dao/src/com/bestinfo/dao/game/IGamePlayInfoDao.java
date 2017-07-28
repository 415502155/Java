package com.bestinfo.dao.game;

import com.bestinfo.bean.game.GamePlayInfo;
import com.bestinfo.dao.page.Pagination;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 游戏玩法
 *
 * @author yangyuefu
 */
public interface IGamePlayInfoDao {

    /**
     * 根据游戏编号获取玩法信息
     *
     * @param jdbcTemplate
     * @param gameId
     * @return
     */
    public List<GamePlayInfo> getGamePlayInfoByGameId(JdbcTemplate jdbcTemplate, int gameId);

    /**
     * 根据玩法编号获取玩法信息
     *
     * @param jdbcTemplate
     * @param playId
     * @return
     */
    public GamePlayInfo getGamePlayInfoByPlayId(JdbcTemplate jdbcTemplate, int playId);

    /**
     * 根据游戏编号和玩法编号获取玩法信息
     *
     * @param jdbcTemplate
     * @param gameId
     * @param playId
     * @return
     */
    public GamePlayInfo getPlayByGameIdAndPlayId(JdbcTemplate jdbcTemplate, int gameId, int playId);

    /**
     * 获取玩法信息数据列表
     *
     * @param jdbcTemplate
     * @return
     */
    public List<GamePlayInfo> selectGamePlayInfo(JdbcTemplate jdbcTemplate);

    /**
     * 新增游戏玩法
     *
     * @param jdbcTemplate
     * @param gamePlayInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    public int insertGamePlayInfo(JdbcTemplate jdbcTemplate, GamePlayInfo gamePlayInfo);

    /**
     * 修改游戏玩法
     *
     * @param jdbcTemplate
     * @param gamePlayInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    public int updateGamePlayInfo(JdbcTemplate jdbcTemplate, GamePlayInfo gamePlayInfo);

    /**
     * 游戏玩法分页信息列表
     *
     * @param jdbcTemplate
     * @param params
     * @return
     */
    public Pagination<GamePlayInfo> getGamePlayInfoPageList(JdbcTemplate jdbcTemplate, Map<String, Object> params);
}
