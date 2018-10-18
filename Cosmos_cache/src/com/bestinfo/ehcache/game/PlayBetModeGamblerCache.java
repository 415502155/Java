package com.bestinfo.ehcache.game;

import com.bestinfo.bean.game.PlayBetMode;
import com.bestinfo.bean.game.PlayBetModeGamb;
import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.ehcache.annotation.EhcacheInit;
import com.bestinfo.cache.keys.CacheKeysUtil;
import com.bestinfo.dao.game.IPlayBetModeGamblerDao;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 玩法投注方式配置缓存操作类
 *
 * @author J
 */
@Repository
@EhcacheInit(name = "PlayBetModeGamblerCache")
public class PlayBetModeGamblerCache extends GetCacheCommon {

    Logger logger = Logger.getLogger(PlayBetModeGamblerCache.class);
    @Resource
    private IPlayBetModeGamblerDao playBetModeGamblerDao;

    /**
     * 查询数据库中玩法投注方式配置数据列表 并放入缓存中。
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3
     */
    @Override
    public int init(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();   //获取缓存
        if (cosmosCache == null) {
            logger.error("no cache can configuration");
            return -3;
        }
        try {
            List<PlayBetModeGamb> lp = playBetModeGamblerDao.selectPlayBetModeList(jdbcTemplate);
            if ( lp == null || lp.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (PlayBetModeGamb pb : lp) {
                String key = CacheKeysUtil.getPlayBetModeGamblerKey(pb.getGame_id(), pb.getPlay_id(), pb.getBet_mode());
                Element element = new Element(key, pb);
                cosmosCache.put(element);
            }
            logger.info("放入缓存的数据条数：" + lp.size());
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -2;
        }
    }

   
    /**
     * 根据Key查询对应的缓存数据对象
     *
     * @param gameId 玩法投注方式配置游戏编号
     * @param playId 玩法编号
     * @param betMode 投注方式
     * @return 玩法投注方式配置
     */
    public PlayBetModeGamb getBetModeById(int gameId, int playId, int betMode) {
        String key = CacheKeysUtil.getPlayBetModeGamblerKey(gameId, playId, betMode);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("no play bet mode find in ehcache");
                return null;
            }
            return (PlayBetModeGamb) e.getObjectValue();
        } catch (IllegalStateException | CacheException e) {
            logger.error("", e);
            return null;
        }
    }
    
    
    /**
     * 从Ehcache中获取某个游戏某个玩法的所有投注方式信息
     *
     * @param gameid
     * @param playId
     * @return
     */
    public List<PlayBetModeGamb> getPlayBetModeListByIdFrmCache(int gameid, int playId) {
        try {
            Cache cosmosCache = getCosmosCache();
            if (cosmosCache == null) {
                logger.error("no cache configuration");
                return Collections.emptyList();
            }

            List<PlayBetModeGamb> lg = new ArrayList<PlayBetModeGamb>();
            List listAllkeys = cosmosCache.getKeys();
               if(listAllkeys==null || listAllkeys.isEmpty()){
                logger.error("the allKeysList is null");
                return Collections.emptyList();
            }
            for (Object objKey : listAllkeys) {
                int skey = ((String) objKey).indexOf(CacheKeysUtil.getPlayBetModeGamblerKey(gameid, playId, 0));
                if (skey > -1) {
                    Element e = cosmosCache.get(objKey);
                    if (e == null) {
                        logger.error("get playbetmode list from ehcache error");
                        return Collections.emptyList();
                    }
                    PlayBetModeGamb gi = (PlayBetModeGamb) e.getObjectValue();
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
     * 更新缓存中某Key所对应的数据
     *
     * @param jdbcTemplate
     * @param pb
     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int updatePlayBetModeById(JdbcTemplate jdbcTemplate, PlayBetModeGamb pb) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            //首先更新数据库
            int re = playBetModeGamblerDao.updatePlayBetMode(jdbcTemplate, pb);
            if (re < 0) {
                logger.error("update kdrawProStatus db error");
                return -1;
            }
            String key = CacheKeysUtil.getPlayBetModeKey(pb.getGame_id(), pb.getPlay_id(), pb.getBet_mode());
            Element e = new Element(key, pb);
            //更新缓存
            cosmosCache.put(e);
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

}
