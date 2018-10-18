package com.bestinfo.ehcache.game;

import com.bestinfo.bean.game.GamePlayInfo;
import com.bestinfo.dao.game.IGamePlayInfoDao;
import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.ehcache.annotation.EhcacheInit;
import com.bestinfo.cache.keys.CacheKeysUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 玩法信息缓存操作类
 */
@Repository
@EhcacheInit(name = "GamePlayInfoCache")
public class GamePlayInfoCache extends GetCacheCommon {

    Logger logger = Logger.getLogger(GamePlayInfoCache.class);

    @Resource
    private IGamePlayInfoDao gamePlayInfoDao;

    /**
     * 查询玩法信息数据，并全部放入缓存
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    @Override
    public int init(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            List<GamePlayInfo> lgpi = gamePlayInfoDao.selectGamePlayInfo(jdbcTemplate);
            if (lgpi == null || lgpi.isEmpty()) {
                logger.error("there is no data in db");
                return -1;
            }

            for (GamePlayInfo gpi : lgpi) {
                String key = CacheKeysUtil.getGamePlayInfoKey(gpi.getGame_id(), gpi.getPlay_id());
                Element e = new Element(key, gpi);
                cosmosCache.put(e);
            }
            logger.info("放入缓存的数据条数：" + lgpi.size());
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 查询玩法信息数据，并全部放入缓存
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
//    public int addGamePlayInfoToCache(JdbcTemplate jdbcTemplate) {
//        Cache cosmosCache = getCosmosCache();
//        if (cosmosCache == null) {
//            logger.error("no cache configuration");
//            return -3;
//        }
//        try {
//            List<GamePlayInfo> lgpi = gamePlayInfoDao.selectGamePlayInfo(jdbcTemplate);
//            if (lgpi == null || lgpi.isEmpty()) {
//                logger.error("there is no data in db");
//                return -1;
//            }
//
//            for (GamePlayInfo gpi : lgpi) {
//                String key = CacheKeysUtil.getGamePlayInfoKey(gpi.getGame_id(), gpi.getPlay_id());
//                Element e = new Element(key, gpi);
//                cosmosCache.put(e);
//            }
//            logger.info("放入缓存的数据条数：" + lgpi.size());
//            return 0;
//        } catch (Exception e) {
//            logger.error("", e);
//            return -2;
//        }
//    }
    /**
     * 根据Key查询对应的缓存数据对象
     *
     * @param gameId 游戏编号
     * @param playId 玩法编号
     * @return 玩法信息
     */
    public GamePlayInfo getGamePlayInfoByid(int gameId, int playId) {
        String key = CacheKeysUtil.getGamePlayInfoKey(gameId, playId);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get gameplayinfo from ehcache error");
                return null;
            }
            return (GamePlayInfo) e.getObjectValue();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 更新缓存中某Key所对应的数据，先更新数据库，再更新缓存
     *
     * @param jdbcTemplate
     * @param gpi
     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int updateGamePlayInfoByid(JdbcTemplate jdbcTemplate, GamePlayInfo gpi) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            //首先更新数据库
            int re = gamePlayInfoDao.updateGamePlayInfo(jdbcTemplate, gpi);
            if (re < 0) {
                logger.error("update GamePlayInfo db error");
                return -1;
            }
            String key = CacheKeysUtil.getGamePlayInfoKey(gpi.getGame_id(), gpi.getPlay_id());
            Element e = new Element(key, gpi);
            //更新缓存
            cosmosCache.put(e);
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 根据游戏id查询该游戏所有玩法信息，游戏id=0，查询所有游戏所有的玩法
     *
     * @param gameid
     * @return
     */
    public List<GamePlayInfo> getGamePlayInfoListByIdFrmCache(int gameid) {
        try {
            Cache cosmosCache = getCosmosCache();
            if (cosmosCache == null) {
                logger.error("no cache configuration");
                return Collections.emptyList();
            }

            List<GamePlayInfo> lg = new ArrayList<GamePlayInfo>();
            List listAllkeys = cosmosCache.getKeys();
            if (listAllkeys == null || listAllkeys.isEmpty()) {
                logger.error("the allKeysList is null");
                return Collections.emptyList();
            }
            for (Object objKey : listAllkeys) {
                int skey = ((String) objKey).indexOf(CacheKeysUtil.getGamePlayInfoKey(gameid, 0));
                if (skey > -1) {
                    Element e = cosmosCache.get(objKey);
                    if (e == null) {
                        logger.error("get dealerinfo list from ehcache error");
                        return Collections.emptyList();
                    }
                    GamePlayInfo gi = (GamePlayInfo) e.getObjectValue();
                    lg.add(gi);
                }
            }
            return lg;
        } catch (Exception e) {
            logger.error("", e);
            return Collections.emptyList();
        }
    }

    /**
     * 查询缓存中的游戏玩法信息的数据列表
     *
     * @param gameid
     * @param playid
     * @return lg
     */
//    public List<GamePlayInfo> getGamePlayInfoListFrmCache(int gameid, int playid) {
//        try {
//            Cache cosmosCache = getCosmosCache();
//            if (cosmosCache == null) {
//                logger.error("no cache configuration");
//                return null;
//            }
//
//            List<GamePlayInfo> lg = new ArrayList<GamePlayInfo>();
//            List listAllkeys = cosmosCache.getKeys();
//            for (Object objKey : listAllkeys) {
//                int skey;
//                if (gameid == 0) {
//                    skey = ((String) objKey).indexOf(CacheKeysUtil.gamePlayInfoKey);
//                } else {
//                    if (playid == 0) {
//                        skey = ((String) objKey).indexOf(CacheKeysUtil.gamePlayInfoKey + gameid + "play:");
//                    } else {
//                        skey = ((String) objKey).indexOf(CacheKeysUtil.gamePlayInfoKey + gameid + "play:" + playid);
//                    }
//                }
//                if (skey > -1) {
//                    Element e = cosmosCache.get(objKey);
//                    GamePlayInfo gi = (GamePlayInfo) e.getObjectValue();
//                    lg.add(gi);
//                }
//            }
//            return lg;
//        } catch (Exception e) {
//            logger.error("", e);
//            return null;
//        }
//    }
    /**
     * 将新增的 游戏玩法 添加到缓存中
     *
     * @param gamePlayInfo
     * @return
     */
    public int insertGamePlayInfoToCache(GamePlayInfo gamePlayInfo) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            String key = CacheKeysUtil.getGamePlayInfoKey(gamePlayInfo.getGame_id(), gamePlayInfo.getPlay_id());
            Element e = new Element(key, gamePlayInfo);
            cosmosCache.put(e);
            return 1;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }
}
