//package com.bestinfo.ehcache.game;
//
//import com.bestinfo.bean.game.GameInfo;
//import com.bestinfo.dao.game.IGameInfoDao;
//import com.bestinfo.cache.keys.GetCacheCommon;
//import com.bestinfo.ehcache.annotation.EhcacheInit;
//import com.bestinfo.cache.keys.CacheKeysUtil;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import javax.annotation.Resource;
//import net.sf.ehcache.Cache;
//import net.sf.ehcache.Element;
//import org.apache.log4j.Logger;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
///**
// * 游戏信息缓存操作类
// *
// * @author hhhh
// */
//@Repository
//@EhcacheInit(name = "GameInfoCache")
//public class GameInfoCache extends GetCacheCommon {
//
//    Logger logger = Logger.getLogger(GameInfoCache.class);
//
//    @Resource
//    private IGameInfoDao gameInfoDao;
//
//    /**
//     * 查询游戏信息数据，gameInfoDao并全部放入缓存
//     *
//     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
//     */
//    @Override
//    public int init(JdbcTemplate jdbcTemplate) {
//        Cache cosmosCache = getCosmosCache();
//        if (cosmosCache == null) {
//            logger.error("no cache configuration");
//            return -3;
//        }
//        try {
//            List<GameInfo> lgi = gameInfoDao.selectGameInfo(jdbcTemplate);
//            if (lgi == null || lgi.isEmpty()) {
//                logger.error("there is no data in db");
//                return -1;
//            }
//
//            for (GameInfo gi : lgi) {
//                String key = CacheKeysUtil.getGameInfoKey(gi.getGame_id());
//                Element e = new Element(key, gi);
//                cosmosCache.put(e);
//            }
//            logger.info("放入缓存的数据条数：" + lgi.size());
//            return 0;
//        } catch (Exception e) {
//            logger.error("", e);
//            return -2;
//        }
//    }
//
//    /**
//     * 更新game_info表中的当前期字段
//     *
//     * @param gameId
//     * @param drawid
//     * @return 0正确 <0错误
//     */
//    public int updateCurDraw(int gameId, int drawid) {
//        String key = CacheKeysUtil.getGameInfoKey(gameId);
//        Cache cosmosCache = getCosmosCache();
//        if (cosmosCache == null) {
//            logger.error("no cache configuration");
//            return -1;
//        }
//        try {
//            Element e = cosmosCache.get(key);
//            if (e == null) {
//                logger.error("update gameinfo curdraw from ehcache error");
//                return -3;
//            }
//            GameInfo g = (GameInfo) e.getObjectValue();
//            g.setCur_draw_id(drawid);
//            Element eu = new Element(key, g);
//            //更新缓存
//            cosmosCache.put(eu);
//            return 0;
//        } catch (Exception e) {
//            logger.error("", e);
//            return -2;
//        }
//    }
//
//    /**
//     * 根据Key查询对应的缓存数据对象
//     *
//     * @param gameId 游戏编号
//     * @return 游戏信息
//     */
//    public GameInfo getGameInfoByid(int gameId) {
//        String key = CacheKeysUtil.getGameInfoKey(gameId);
//        Cache cosmosCache = getCosmosCache();
//        if (cosmosCache == null) {
//            logger.error("no cache configuration");
//            return null;
//        }
//        try {
//            Element e = cosmosCache.get(key);
//            if (e == null) {
//                logger.error("can't get gameinfo from ehcache");
//                return null;
//            }
//            return (GameInfo) e.getObjectValue();
//        } catch (Exception e) {
//            logger.error("there is no game info in Ehcache where gameId = " + gameId, e);
//            return null;
//        }
//    }
//
//    /**
//     * 更新缓存中某Key所对应的数据
//     *
//     * @param jdbcTemplate
//     * @param gi
//     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
//     */
//    public int updateGameInfoByid(JdbcTemplate jdbcTemplate, GameInfo gi) {
//        Cache cosmosCache = getCosmosCache();
//        if (cosmosCache == null) {
//            logger.error("no cache configuration");
//            return -3;
//        }
//        try {
//            String key = CacheKeysUtil.getGameInfoKey(gi.getGame_id());
//            Element e = new Element(key, gi);
//            //更新缓存
//            cosmosCache.put(e);
//            return 0;
//        } catch (Exception e) {
//            logger.error("", e);
//            return -2;
//        }
//    }
//
//    /**
//     * 将新增游戏信息添加到缓存中
//     *
//     * @param gameinfo
//     * @return
//     */
//    public int insertGameInfoToCache(GameInfo gameinfo) {
//        Cache cosmosCache = getCosmosCache();
//        if (cosmosCache == null) {
//            logger.error("no cache configuration");
//            return -3;
//        }
//        try {
//            String key = CacheKeysUtil.getGameInfoKey(gameinfo.getGame_id());
//            Element e = new Element(key, gameinfo);
//            cosmosCache.put(e);
//            return 1;
//        } catch (Exception e) {
//            logger.error("", e);
//            return -2;
//        }
//    }
//
//    /**
//     * 查询缓存中的游戏信息的所有数据
//     *
//     * @return 游戏信息数据集合
//     */
//    public List<GameInfo> getGameInfoListFrmCache() {
//        try {
//            Cache cosmosCache = getCosmosCache();
//            if (cosmosCache == null) {
//                logger.error("no cache configuration");
//                return Collections.emptyList();
//            }
//            List<GameInfo> lg = new ArrayList<GameInfo>();
//            List listAllkeys = cosmosCache.getKeys();
//            if(listAllkeys==null || listAllkeys.isEmpty()){
//                logger.error("the allKeysList is null");
//                return Collections.emptyList();
//            }
//            for (Object objKey : listAllkeys) {
//                int skey = ((String) objKey).indexOf(CacheKeysUtil.gameInfoKey);
//                if (skey > -1) {
//                    Element e = cosmosCache.get(objKey);
//                    if (e == null) {
//                        logger.error("get gameinfo list from ehcache error");
//                         return Collections.emptyList();
//                    }
//                    GameInfo gi = (GameInfo) e.getObjectValue();
//                    lg.add(gi);
//                }
//            }
//            return lg;
//        } catch (Exception e) {
//            logger.error("", e);
//             return Collections.emptyList();
//        }
//    }
//}
