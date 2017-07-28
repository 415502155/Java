//package com.bestinfo.ehcache.game;
//
//import com.bestinfo.bean.game.GameMultiOpen;
//import com.bestinfo.dao.game.IGameMultiOpenDao;
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
// * 开奖次数描述缓存操作类
// *
// * @author user
// */
//@Repository
//@EhcacheInit(name = "GameMultiOpenCache")
//public class GameMultiOpenCache extends GetCacheCommon {
//
//    Logger logger = Logger.getLogger(GameMultiOpenCache.class);
//    @Resource
//    private IGameMultiOpenDao gameMultiOpenDao;
//
//    /**
//     * 查询数据库中开奖次数描述数据列表 并放入缓存中。
//     *
//     * @param jdbcTemplate
//     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3
//     */
//    @Override
//    public int init(JdbcTemplate jdbcTemplate) {
//        Cache cosmosCache = getCosmosCache();   //获取缓存
//        if (cosmosCache == null) {
//            logger.error("no cache can configuration");
//            return -3;
//        }
//        try {
//            List<GameMultiOpen> lg = gameMultiOpenDao.selectGameMultiOpenList(jdbcTemplate);
//            if (lg == null || lg.isEmpty()) {
//                logger.error(" there is no data in db");
//                return -1;
//            }
//            for (GameMultiOpen gm : lg) {
//                String key = CacheKeysUtil.getGameMultiOpenKey(gm.getGame_id(), gm.getOpen_id());
//                Element element = new Element(key, gm);
//                cosmosCache.put(element);
//            }
//            logger.info("放入缓存的数据条数：" + lg.size());
//            return 0;
//        } catch (Exception ex) {
//            logger.error("", ex);
//            return -2;
//        }
//    }
//
//    /**
//     * 根据Key查询对应的缓存数据对象
//     *
//     * @param gameId 游戏编号
//     * @param openId 开奖次数
//     * @return 资金交易类型
//     */
//    public GameMultiOpen getGameMultiOpenById(int gameId, int openId) {
//        String key = CacheKeysUtil.getGameMultiOpenKey(gameId, openId);
//        Cache cosmosCache = getCosmosCache();
//        if (cosmosCache == null) {
//            logger.error("no cache configuration");
//            return null;
//        }
//        try {
//            Element e = cosmosCache.get(key);
//            if (e == null) {
//                logger.error("get gamemultiopen from ehcache error");
//                return null;
//            }
//            return (GameMultiOpen) e.getObjectValue();
//        } catch (Exception e) {
//            logger.error("", e);
//            return null;
//        }
//    }
//
//    /**
//     * 更新缓存中某Key所对应的数据，先更新数据库，再更新缓存
//     *
//     * @param jdbcTemplate
//     * @param gm
//     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
//     */
//    public int updateGameMultiOpenById(JdbcTemplate jdbcTemplate, GameMultiOpen gm) {
//        Cache cosmosCache = getCosmosCache();
//        if (cosmosCache == null) {
//            logger.error("no cache configuration");
//            return -3;
//        }
//        try {
//            //首先更新数据库
//            int re = gameMultiOpenDao.updateGameMultiOpen(jdbcTemplate, gm);
//            if (re < 0) {
//                logger.error("update kdrawProStatus db error");
//                return -1;
//            }
//            String key = CacheKeysUtil.getGameMultiOpenKey(gm.getGame_id(), gm.getOpen_id());
//            Element e = new Element(key, gm);
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
//     * gameid=0查询所有游戏的所有开奖次数描述信息，gameid!=0&&openid=0查询指定游戏所有的开奖次数，
//     * gameid!=0&&openid!=0查询指定游戏指定开奖次数描述信息
//     *
//     * @param gameid
//     * @param openid
//     * @return
//     */
//    public List<GameMultiOpen> getGameMultiOpenListFrmCache(int gameid, int openid) {
//        try {
//            Cache cosmosCache = getCosmosCache();
//            if (cosmosCache == null) {
//                logger.error("no cache configuration");
//                return Collections.emptyList();
//            }
//            List<GameMultiOpen> lg = new ArrayList<GameMultiOpen>();
//            List listAllkeys = cosmosCache.getKeys();
//            if(listAllkeys==null || listAllkeys.isEmpty()){
//                logger.error("the allKeysList is null");
//                return Collections.emptyList();
//            }
//            for (Object objKey : listAllkeys) {
//                int skey = ((String) objKey).indexOf(CacheKeysUtil.getGameMultiOpenKey(gameid, openid));
//                if (skey > -1) {
//                    Element e = cosmosCache.get(objKey);
//                    if (e == null) {
//                        logger.error("get gamemultiopen list from ehcache error");
//                        return Collections.emptyList();
//                    }
//                    GameMultiOpen gi = (GameMultiOpen) e.getObjectValue();
//                    lg.add(gi);
//                }
//            }
//            return lg;
//        } catch (Exception e) {
//            logger.error("", e);
//            return Collections.emptyList();
//        }
//    }
//
//    /**
//     * 将新增的 开奖次数 添加到缓存中
//     *
//     * @param gameMultiOpen
//     * @return
//     */
//    public int insertGameMultiOpenToCache(GameMultiOpen gameMultiOpen) {
//        Cache cosmosCache = getCosmosCache();
//        if (cosmosCache == null) {
//            logger.error("no cache configuration");
//            return -3;
//        }
//        try {
//            String key = CacheKeysUtil.getGameMultiOpenKey(gameMultiOpen.getGame_id(), gameMultiOpen.getOpen_id());
//            Element e = new Element(key, gameMultiOpen);
//            cosmosCache.put(e);
//            return 0;
//        } catch (Exception e) {
//            logger.error("", e);
//            return -2;
//        }
//    }
//}
