package com.bestinfo.dao.game;

import com.bestinfo.bean.game.GameClassInfo;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 奖级
 *
 * @author hhhh,YangRong
 */
public interface IGameClassInfoDao {

    /**
     * 获取游戏奖级列表
     *
     * @param jdbcTemplate
     * @return 列表
     */
    public List<GameClassInfo> selectGameClassInfo(JdbcTemplate jdbcTemplate);

    /**
     * 删除奖级
     *
     * @param jdbcTemplate
     * @param gameId
     * @param playId
     * @param classId
     * @return 成功-影响记录数 失败-(-1)
     */
    public int deleteGameClassInfo(JdbcTemplate jdbcTemplate, int gameId, int playId, int classId);

    /**
     * 根据gameId,playId获取游戏奖级列表
     *
     * @param jdbcTemplate
     * @param gameId
     * @param playId
     * @return 列表
     */
    public List<GameClassInfo> selectGameClassInfoByGamePlayId(JdbcTemplate jdbcTemplate, int gameId, int playId);

    /**
     * 修改奖级
     *
     * @param jdbcTemplate
     * @param gameClassInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    public int updateGameClassInfo(JdbcTemplate jdbcTemplate, GameClassInfo gameClassInfo);

    /**
     * 新增游戏奖级
     *
     * @param jdbcTemplate
     * @param gameInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    public int insertGameClassInfo(JdbcTemplate jdbcTemplate, GameClassInfo gameInfo);

    /**
     * 根据游戏id,玩法id,奖级id获取游戏奖级
     *
     * @param jdbcTemplate
     * @param gameId
     * @param playId
     * @param classId
     * @return GameClassInfo
     */
    public GameClassInfo getGameClassInfoByGamePlayClassId(JdbcTemplate jdbcTemplate, int gameId, int playId, int classId);

    /**
     * 根据gameId获取游戏奖级列表
     *
     * @param jdbcTemplate
     * @param gameId
     * @return 列表
     */
    public List<GameClassInfo> selectGameClassInfoByGameId(JdbcTemplate jdbcTemplate, int gameId);

    /**
     * 根据游戏id,奖级id获取游戏奖级
     *
     * @param jdbcTemplate
     * @param gameId
     * @param classId
     * @return GameClassInfo
     */
    public GameClassInfo getClassByGameAndClass(JdbcTemplate jdbcTemplate, int gameId, int classId);
}
