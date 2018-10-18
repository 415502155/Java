package com.bestinfo.service.game;

import com.bestinfo.bean.game.GameClassInfo;
import java.util.List;

/**
 * 游戏奖级
 *
 * @author YangRong
 */
public interface IGameClassInfoService {

    /**
     * 新增游戏奖级
     *
     * @param gameClassInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    public int insertGameClassInfo(GameClassInfo gameClassInfo);

    /**
     * 根据游戏id,玩法id,奖级id删除游戏奖级
     *
     * @param gameId
     * @param playId
     * @param classId
     * @return 1:成功
     */
    public int deleteGameInfoByGamePlayClassId(int gameId, int playId, int classId);

    /**
     * 修改游戏奖级
     *
     * @param gameClassInfo
     * @return 0成功 -1游戏玩法不存在 -2未知错误
     */

    public int updateGameClassInfo(GameClassInfo gameClassInfo);

    /**
     * 根据gameId获取游戏奖级列表
     *
     * @param gameId
     * @return 列表
     */
//    public List<GameClassInfo> selectGameClassInfoByGameId(int gameId);

    /**
     * 根据gameId,playId获取游戏奖级列表
     *
     * @param gameId
     * @param playId
     * @return 列表
     */
    public List<GameClassInfo> selectGameClassInfoByGamePlayId(int gameId, int playId);

    /**
     * 获取游戏奖级列表
     *
     *
     * @return 列表
     */
//    public List<GameClassInfo> selectGameClassInfo();

    /**
     * 根据游戏id,玩法id,奖级id获取游戏奖级
     *
     * @param gameId
     * @param playId
     * @param classId
     * @return GameClassInfo
     */
//    public GameClassInfo getGameInfoByGamePlayClassId(int gameId, int playId, int classId);

}
