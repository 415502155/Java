/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.ehcache.game;

import com.bestinfo.bean.game.GameBetModeGamb; 
import com.bestinfo.cache.keys.CacheKeysUtil;
import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.dao.game.IGameBetModeGambDao;
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
 *
 * @author jone
 */
@Repository
@EhcacheInit(name = "GameBetModeGambCache")
public class GameBetModeGambCache extends GetCacheCommon {

    private final Logger logger = Logger.getLogger(GameBetModeGambCache.class);
    @Resource
    private IGameBetModeGambDao gameBetModeGambDao;

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
            List<GameBetModeGamb> lg = gameBetModeGambDao.findAllGameBetMode(jdbcTemplate);
            if (lg == null || lg.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (GameBetModeGamb gb : lg) {
                String key = CacheKeysUtil.genGameBetModeGamb(gb.getBet_mode());
                Element element = new Element(key, gb);
                cosmosCache.put(element);
            }
            logger.info("放入缓存的数据条数：" + lg.size());
            return 0;
        } catch (Exception ex) {
            logger.error("GameBetModeGambCache init ex :", ex);
            return -2;
        }
    }

    /**
     * 根据Key查询对应的缓存数据对象
     *
     * @param betMode
     * @return
     */
    public GameBetModeGamb findGameBetModeBybetModeId(int betMode) {
        String key = CacheKeysUtil.genGameBetModeGamb(betMode);
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
            return (GameBetModeGamb) e.getObjectValue();
        } catch (IllegalStateException | CacheException e) {
            logger.error("GameBetModeGambCache findGameBetModeBybetModeId ex :", e);
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
    public int updateGameBetModeGambByid(JdbcTemplate jdbcTemplate, GameBetModeGamb gpi) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            //首先更新数据库
            int re = gameBetModeGambDao.updatePlayBetMode(jdbcTemplate, gpi);
            if (re < 0) {
                logger.error("update GamePlayInfo db error");
                return -1;
            }
            String key = CacheKeysUtil.genGameBetModeGamb(gpi.getBet_mode());
            Element e = new Element(key, gpi);
            //更新缓存
            cosmosCache.put(e);
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }


}
