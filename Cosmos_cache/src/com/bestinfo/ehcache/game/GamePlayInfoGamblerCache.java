package com.bestinfo.ehcache.game;

import com.bestinfo.bean.game.GamePlayInfoGamb;
import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.ehcache.annotation.EhcacheInit;
import com.bestinfo.cache.keys.CacheKeysUtil;
import com.bestinfo.dao.game.IGamePlayInfoGamblerDao;
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
@EhcacheInit(name = "GamePlayInfoGamblerCache")
public class GamePlayInfoGamblerCache extends GetCacheCommon {

    Logger logger = Logger.getLogger(GamePlayInfoGamblerCache.class);

    @Resource
    private IGamePlayInfoGamblerDao gamePlayInfoGamblerDao;

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
            List<GamePlayInfoGamb> lgpi = gamePlayInfoGamblerDao.selectGamePlayInfo(jdbcTemplate);
            if (lgpi == null || lgpi.isEmpty()) {
                logger.error("there is no data in db");
                return -1;
            }

            for (GamePlayInfoGamb gpi : lgpi) {
                String key = CacheKeysUtil.getGamePlayInfoGamblerKey(gpi.getGame_id(), gpi.getPlay_id());
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
     * 根据Key查询对应的缓存数据对象
     *
     * @param gameId 游戏编号
     * @param playId 玩法编号
     * @return 玩法信息
     */
    public GamePlayInfoGamb getGamePlayInfoByid(int gameId, int playId) {
        String key = CacheKeysUtil.getGamePlayInfoGamblerKey(gameId, playId);
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
            return (GamePlayInfoGamb) e.getObjectValue();
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
    public int updateGamePlayInfoByid(JdbcTemplate jdbcTemplate, GamePlayInfoGamb gpi) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            //首先更新数据库
            int re = gamePlayInfoGamblerDao.updateGamePlayInfo(jdbcTemplate, gpi);
            if (re < 0) {
                logger.error("update GamePlayInfoGamb db error");
                return -1;
            }
            String key = CacheKeysUtil.getGamePlayInfoGamblerKey(gpi.getGame_id(), gpi.getPlay_id());
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
    public List<GamePlayInfoGamb> getGamePlayInfoListByIdFrmCache(int gameid) {
        try {
            Cache cosmosCache = getCosmosCache();
            if (cosmosCache == null) {
                logger.error("no cache configuration");
                return Collections.emptyList();
            }

            List<GamePlayInfoGamb> lg = new ArrayList<GamePlayInfoGamb>();
            List listAllkeys = cosmosCache.getKeys();
            if (listAllkeys == null || listAllkeys.isEmpty()) {
                logger.error("the allKeysList is null");
                return Collections.emptyList();
            }
            for (Object objKey : listAllkeys) {
                int skey = ((String) objKey).indexOf(CacheKeysUtil.getGamePlayInfoGamblerKey(gameid, 0));
                if (skey > -1) {
                    Element e = cosmosCache.get(objKey);
                    if (e == null) {
                        logger.error("get dealerinfo list from ehcache error");
                        return Collections.emptyList();
                    }
                    GamePlayInfoGamb gi = (GamePlayInfoGamb) e.getObjectValue();
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
