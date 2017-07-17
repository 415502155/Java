package com.bestinfo.service.impl.game;

import com.bestinfo.bean.game.GameClassInfo;
import com.bestinfo.dao.game.IGameClassInfoDao;
import com.bestinfo.ehcache.game.GameClassInfoCache;
import com.bestinfo.service.game.IGameClassInfoService;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 游戏奖级
 *
 * @author YangRong
 */
@Service
public class GameClassInfoServiceImpl implements IGameClassInfoService {
    
    private final Logger logger = Logger.getLogger(IGameClassInfoService.class);
    
    @Resource
    private IGameClassInfoDao gameClassInfoDao;
    
    @Resource
    private GameClassInfoCache gameClassInfoCache;
    
    @Resource
    private JdbcTemplate metaJdbcTemplate;

    /**
     * 修改游戏奖级
     *
     * @param gameClassInfo
     * @return 0成功 -1游戏玩法不存在 -2未知错误
     */
    @Override
    public int updateGameClassInfo(GameClassInfo gameClassInfo) {
        return gameClassInfoCache.updateGameClassInfoByid(metaJdbcTemplate, gameClassInfo);
    }

    /**
     * 新增游戏奖级
     *
     * @param gameClassInfo
     * @return 成功-0 数据库失败-1,缓存失败-2
     */
    @Override
    public int insertGameClassInfo(GameClassInfo gameClassInfo) {        
        int res1 = gameClassInfoDao.insertGameClassInfo(metaJdbcTemplate, gameClassInfo);
        if (res1 <= 0) {
            logger.error("insert gameclass info to db error:" + res1);
            return -1;
        }
        int res2 = gameClassInfoCache.insertGameClassInfoToCache(gameClassInfo);
        if (res2 < 0) {
            logger.error("update gameclass info ehcache error:" + res2);
            return -2;
        }
        return 0;
    }

    /**
     * 根据gameId获取游戏奖级列表
     *
     * @param gameId
     * @return 列表
     */
//    @Override
//    public List<GameClassInfo> selectGameClassInfoByGameId(int gameId) {
//        return gameClassInfoDao.selectGameClassInfoByGameId(metaJdbcTemplate, gameId);
//    }

    /**
     * 根据gameId,playId获取游戏奖级列表
     *
     * @param gameId
     * @param playId
     * @return 列表
     */
    @Override
    public List<GameClassInfo> selectGameClassInfoByGamePlayId(int gameId, int playId) {
        return gameClassInfoDao.selectGameClassInfoByGamePlayId(metaJdbcTemplate, gameId, playId);
    }

    /**
     * 获取游戏奖级列表
     *
     *
     * @return 列表
     */
//    @Override
//    public List<GameClassInfo> selectGameClassInfo() {
//        return gameClassInfoDao.selectGameClassInfo(metaJdbcTemplate);
//    }

    /**
     * 根据游戏id,玩法id,奖级id删除游戏奖级
     *
     * @param gameId
     * @param playId
     * @param classId
     * @return 1:成功
     */
    @Override
    public int deleteGameInfoByGamePlayClassId(int gameId, int playId, int classId) {
        int res1, res2;
        res1 = gameClassInfoDao.deleteGameClassInfo(metaJdbcTemplate, gameId, playId, classId);
        if (res1 <= 0) {
            return res1;
        }
        res2 = gameClassInfoCache.deleteGameClassInfoByid(gameId, playId, classId);
        return res2;
        
    }

    /**
     * 根据游戏id,玩法id,奖级id获取游戏奖级
     *
     * @param gameId
     * @param playId
     * @param classId
     * @return GameClassInfo
     */
//    @Override
//    public GameClassInfo getGameInfoByGamePlayClassId(int gameId, int playId, int classId) {
//        GameClassInfo gci;
//        gci = gameClassInfoCache.getGameClassInfoByid(gameId, playId, classId);
//        if (gci == null) {
//            return gameClassInfoDao.getGameClassInfoByGamePlayClassId(metaJdbcTemplate, gameId, playId, classId);
//        } else {
//            return gci;
//        }
//    }
    
}
