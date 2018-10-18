package com.bestinfo.service.impl.game;

import com.bestinfo.bean.game.GamePlayInfo;
import com.bestinfo.dao.game.IGamePlayInfoDao;
import com.bestinfo.dao.page.Pagination;
import com.bestinfo.ehcache.game.GamePlayInfoCache;
import com.bestinfo.service.game.IGamePlayInfoService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 游戏玩法
 *
 * @author yangyuefu
 */
@Service
public class GamePlayInfoServiceImpl implements IGamePlayInfoService {
    
    private final Logger logger = Logger.getLogger(IGamePlayInfoService.class);
    
    @Resource
    private IGamePlayInfoDao gamePlayInfoDao;
    
    @Resource
    private GamePlayInfoCache gamePlayInfoCache;
    
    @Resource
    private JdbcTemplate metaJdbcTemplate;

    /**
     * 根据游戏id获取游戏玩法
     *
     * @param gameId
     * @return
     */
    @Override
    public List<GamePlayInfo> getGamePlayInfoByGameId(int gameId) {
        return gamePlayInfoDao.getGamePlayInfoByGameId(metaJdbcTemplate, gameId);
    }

    /**
     * 根据玩法id获取游戏玩法
     *
     * @param playId
     * @return
     */
    @Override
    public GamePlayInfo getGamePlayInfoByPlayId(int playId) {
        return gamePlayInfoDao.getGamePlayInfoByPlayId(metaJdbcTemplate, playId);
    }

    /**
     * 根据游戏id和玩法id获取玩法记录
     *
     * @param gameId
     * @param playId
     * @return
     */
    @Override
    public GamePlayInfo getPlayByGameIdAndPlayId(int gameId, int playId) {
        return gamePlayInfoDao.getPlayByGameIdAndPlayId(metaJdbcTemplate, gameId, playId);
    }

    /**
     * 新增游戏玩法
     *
     * @param gamePlayInfo
     * @return 0成功 -1游戏玩法已存在 -3添加游戏玩法到缓存出错 -2未知错误
     */
    @Override
    public int insertGamePlayInfo(GamePlayInfo gamePlayInfo) {
        try {
            //首先添加数据库
            int result = gamePlayInfoDao.insertGamePlayInfo(metaJdbcTemplate, gamePlayInfo);
            if (result < 0) {
                logger.error("insert gamePlayInfo to db error");
                return -1;
            }
            //然后添加到缓存
            int re = gamePlayInfoCache.insertGamePlayInfoToCache(gamePlayInfo);
            if (re < 0) {
                logger.error("insert gamePlayInfo to cache error");
                return -3;
            }
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 修改游戏玩法
     *
     * @param gamePlayInfo
     * @return 0成功 -1游戏玩法不存在 -2未知错误
     */
    @Override
    public int updateGamePlayInfo(GamePlayInfo gamePlayInfo) {
        //修改数据库信息
        int result = gamePlayInfoCache.updateGamePlayInfoByid(metaJdbcTemplate, gamePlayInfo);
        if (result < 0) {
            logger.error("update gameplayinfo error:" + result);
            return -1;
        }
        return 0;
//        return result >= 0 ? 0 : -2;
    }

    /**
     * 获取游戏分页列表
     *
     * @param params
     * @return
     */
    @Override
    public Pagination<GamePlayInfo> getGamePlayInfoPageList(Map<String, Object> params) {
        return gamePlayInfoDao.getGamePlayInfoPageList(metaJdbcTemplate, params);
    }
}
