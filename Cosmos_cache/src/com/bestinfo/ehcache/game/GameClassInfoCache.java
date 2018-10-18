package com.bestinfo.ehcache.game;

import com.bestinfo.bean.game.GameClassInfo;
import com.bestinfo.dao.game.IGameClassInfoDao;
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
 * 奖级缓存操作类
 *
 * @author hhhh,YangRong
 */
@Repository
@EhcacheInit(name = "GameClassInfoCache")
public class GameClassInfoCache extends GetCacheCommon {

    Logger logger = Logger.getLogger(GameClassInfoCache.class);

    @Resource
    private IGameClassInfoDao gameClassInfoDao;

    /**
     * 查询奖级数据，并全部放入缓存
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
            List<GameClassInfo> lgci = gameClassInfoDao.selectGameClassInfo(jdbcTemplate);
            if (lgci == null || lgci.isEmpty()) {
                logger.error("there is no data in db");
                return -1;
            }

            for (GameClassInfo gci : lgci) {
                String key = CacheKeysUtil.getGameClassInfoKey(gci.getGame_id(), gci.getPlay_id(), gci.getClass_id());
                Element e = new Element(key, gci);
                cosmosCache.put(e);
            }
            logger.info("放入缓存的数据条数：" + lgci.size());
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 查询奖级数据，并全部放入缓存
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
//    public int addGameClassInfoToCache(JdbcTemplate jdbcTemplate) {
//        Cache cosmosCache = getCosmosCache();
//        if (cosmosCache == null) {
//            logger.error("no cache configuration");
//            return -3;
//        }
//        try {
//            List<GameClassInfo> lgci = gameClassInfoDao.selectGameClassInfo(jdbcTemplate);
//            if (lgci == null || lgci.isEmpty()) {
//                logger.error("there is no data in db");
//                return -1;
//            }
//
//            for (GameClassInfo gci : lgci) {
//                String key = CacheKeysUtil.getGameClassInfoKey(gci.getGame_id(), gci.getPlay_id(), gci.getClass_id());
//                Element e = new Element(key, gci);
//                cosmosCache.put(e);
//            }
//            logger.info("放入缓存的数据条数：" + lgci.size());
//            return 0;
//        } catch (Exception e) {
//            logger.error("", e);
//            return -2;
//        }
//    }
    /**
     * 将新增游戏奖级信息添加到缓存中
     *
     * @param gameClassInfo
     * @return
     */
    public int insertGameClassInfoToCache(GameClassInfo gameClassInfo) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            String key = CacheKeysUtil.getGameClassInfoKey(gameClassInfo.getGame_id(), gameClassInfo.getPlay_id(), gameClassInfo.getClass_id());
            Element e = new Element(key, gameClassInfo);
            cosmosCache.put(e);
            return 1;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 根据Key删除对应的缓存数据对象
     *
     * @param gameId 游戏编号
     * @param playId 玩法编号
     * @param classId 奖级编号
     * @return 1:成功
     */
    public int deleteGameClassInfoByid(int gameId, int playId, int classId) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            String key = CacheKeysUtil.getGameClassInfoKey(gameId, playId, classId);
            if (cosmosCache.remove(key)) {
                return 1;
            } else {
                return -1;
            }
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 根据Key查询对应的缓存数据对象
     *
     * @param gameId 游戏编号
     * @param playId 玩法编号
     * @param classId 奖级编号
     * @return 奖级
     */
    public GameClassInfo getGameClassInfoByid(int gameId, int playId, int classId) {
        String key = CacheKeysUtil.getGameClassInfoKey(gameId, playId, classId);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get gameclassinfo from ehcache error");
                return null;
            }
            return (GameClassInfo) e.getObjectValue();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 更新缓存中某Key所对应的数据
     *
     * @param jdbcTemplate
     * @param gci
     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int updateGameClassInfoByid(JdbcTemplate jdbcTemplate, GameClassInfo gci) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            //首先更新数据库
            int re = gameClassInfoDao.updateGameClassInfo(jdbcTemplate, gci);
            if (re < 0) {
                logger.error("update GameClassInfo db error");
                return -1;
            }
            String key = CacheKeysUtil.getGameClassInfoKey(gci.getGame_id(), gci.getPlay_id(), gci.getClass_id());
            Element e = new Element(key, gci);
            //更新缓存
            cosmosCache.put(e);
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * playid=0,查询出某个游戏下所有玩法的所有奖级，否则，查询出某个游戏下某个玩法的所有将级
     *
     * @param gameid
     * @param playid
     * @return
     */
    public List<GameClassInfo> getGameClassListByGameid(int gameid, int playid) {
        try {
            Cache cosmosCache = getCosmosCache();
            if (cosmosCache == null) {
                logger.error("no cache configuration");
                return Collections.emptyList();
            }
            List<GameClassInfo> lg = new ArrayList<GameClassInfo>();
            List listAllkeys = cosmosCache.getKeys();
             if(listAllkeys==null || listAllkeys.isEmpty()){
                logger.error("the allKeysList is null");
                return Collections.emptyList();
            }
            for (Object objKey : listAllkeys) {
                int skey = ((String) objKey).indexOf(CacheKeysUtil.getGameClassInfoKey(gameid, playid, 0));
                if (skey > -1) {
                    Element e = cosmosCache.get(objKey);
                    if (e == null) {
                        logger.error("get dealerinfo list from ehcache error");
                        return Collections.emptyList();
                    }
                    GameClassInfo gi = (GameClassInfo) e.getObjectValue();
                    lg.add(gi);
                }
            }
            return lg;
        } catch (Exception e) {
            logger.error("", e);
            return Collections.emptyList();
        }
    }

}
