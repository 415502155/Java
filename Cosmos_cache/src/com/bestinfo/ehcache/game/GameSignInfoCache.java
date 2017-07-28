package com.bestinfo.ehcache.game;

import com.bestinfo.bean.game.GameSignInfo;
import com.bestinfo.dao.game.IGameSignInfoDao;
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
 * 投注符号缓存操作类
 *
 * @author hhhh
 */
@Repository
@EhcacheInit(name = "GameSignInfoCache")
public class GameSignInfoCache extends GetCacheCommon {

    Logger logger = Logger.getLogger(GameSignInfoCache.class);

    @Resource
    private IGameSignInfoDao gameSignInfoDao;

    /**
     * 查询投注符号数据，并全部放入缓存
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
            List<GameSignInfo> lgsi = gameSignInfoDao.selectGameSignInfo(jdbcTemplate);
            if (lgsi == null || lgsi.isEmpty()) {
                logger.error("there is no data in db");
                return -1;
            }

            for (GameSignInfo gsi : lgsi) {
                String key = CacheKeysUtil.getGameSignInfoKey(gsi.getGame_id(), gsi.getPlay_id(), gsi.getSign_id());
                Element e = new Element(key, gsi);
                cosmosCache.put(e);
            }
            logger.info("放入缓存的数据条数：" + lgsi.size());
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 查询投注符号数据，并全部放入缓存
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int addGameSignInfoToCache(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            List<GameSignInfo> lgsi = gameSignInfoDao.selectGameSignInfo(jdbcTemplate);
            if (lgsi == null || lgsi.isEmpty()) {
                logger.error("there is no data in db");
                return -1;
            }

            for (GameSignInfo gsi : lgsi) {
                String key = CacheKeysUtil.getGameSignInfoKey(gsi.getGame_id(), gsi.getPlay_id(), gsi.getSign_id());
                Element e = new Element(key, gsi);
                cosmosCache.put(e);
            }
            logger.info("放入缓存的数据条数：" + lgsi.size());
            return 0;
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
     * @param signId 符号编号
     * @return 投注符号
     */
    public GameSignInfo getGameSignInfoByid(int gameId, int playId, int signId) {
        String key = CacheKeysUtil.getGameSignInfoKey(gameId, playId, signId);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get gamesigninfo from ehcache error");
                return null;
            }
            return (GameSignInfo) e.getObjectValue();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 更新缓存中某Key所对应的数据
     *
     * @param jdbcTemplate
     * @param gsi
     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int updateGameSignInfoByid(JdbcTemplate jdbcTemplate, GameSignInfo gsi) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            //首先更新数据库
            int re = gameSignInfoDao.updateGameSignInfo(jdbcTemplate, gsi);
            if (re < 0) {
                logger.error("update GameSignInfo db error");
                return -1;
            }
            String key = CacheKeysUtil.getGameSignInfoKey(gsi.getGame_id(), gsi.getPlay_id(), gsi.getSign_id());
            Element e = new Element(key, gsi);
            //更新缓存
            cosmosCache.put(e);
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 从Ehcache中获取某个游戏某个玩法的所有投注符号信息
     *
     * @param gameid
     * @param playId
     * @return
     */
    public List<GameSignInfo> getGameSignInfoListByIdFrmCache(int gameid, int playId) {
        try {
            Cache cosmosCache = getCosmosCache();
            if (cosmosCache == null) {
                logger.error("no cache configuration");
                 return Collections.emptyList();
            }

            List<GameSignInfo> lg = new ArrayList<GameSignInfo>();
            List listAllkeys = cosmosCache.getKeys();
              if(listAllkeys==null || listAllkeys.isEmpty()){
                logger.error("the allKeysList is null");
                return Collections.emptyList();
            }
            for (Object objKey : listAllkeys) {
                int skey = ((String) objKey).indexOf(CacheKeysUtil.getGameSignInfoKey(gameid, playId, 0));
                if (skey > -1) {
                    Element e = cosmosCache.get(objKey);
                    if (e == null) {
                        logger.error("get dealerinfo list from ehcache error");
                          return Collections.emptyList();
                    }
                    GameSignInfo gi = (GameSignInfo) e.getObjectValue();
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
