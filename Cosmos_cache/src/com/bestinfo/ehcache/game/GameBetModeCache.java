package com.bestinfo.ehcache.game;

import com.bestinfo.bean.encoding.GameBetMode;
import com.bestinfo.cache.keys.CacheKeysUtil;
import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.dao.game.IGameBetModeDao;
import com.bestinfo.ehcache.annotation.EhcacheInit;
import java.util.List;
import javax.annotation.Resource;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 游戏投注方式缓存操作类
 *
 * @author shange
 */
@Repository
@EhcacheInit(name = "GameBetModeCache")
public class GameBetModeCache extends GetCacheCommon {

    private final Logger logger = Logger.getLogger(GameBetModeCache.class);
    @Resource
    private IGameBetModeDao gameBetModeDao;

    /**
     * 初始化游戏投注方式缓存数据
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3)
     */
    @Override
    public int init(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();   //获取缓存
        if (cosmosCache == null) {
            logger.error("no cache can configuration");
            return -3;
        }
        try {
            List<GameBetMode> lg = gameBetModeDao.findAllGameBetMode(jdbcTemplate);
            if (lg == null || lg.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (GameBetMode gb : lg) {
                String key = CacheKeysUtil.genGameBetMode(gb.getBet_mode());
                Element element = new Element(key, gb);
                cosmosCache.put(element);
            }
            logger.info("放入缓存的数据条数：" + lg.size());
            return 0;
        } catch (Exception ex) {
            logger.error("GameBetModeCache init ex :", ex);
            return -2;
        }
    }

    /**
     * 根据Key查询对应的缓存数据对象
     *
     * @param betMode
     * @return
     */
    public GameBetMode findGameBetModeBybetModeId(int betMode) {
        String key = CacheKeysUtil.genGameBetMode(betMode);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("no game bet mode find in ehcache");
                return null;
            }
            return (GameBetMode) e.getObjectValue();
        } catch (IllegalStateException | CacheException e) {
            logger.error("GameBetModeCache findGameBetModeBybetModeId ex :", e);
            return null;
        }
    }

}
