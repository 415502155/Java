//package com.bestinfo.ehcache.game;
//
//import com.bestinfo.bean.game.OpenprizeInfo;
//import com.bestinfo.cache.keys.GetCacheCommon;
//import com.bestinfo.ehcache.annotation.EhcacheInit;
//import com.bestinfo.cache.keys.CacheKeysUtil;
//import net.sf.ehcache.Cache;
//import net.sf.ehcache.CacheException;
//import net.sf.ehcache.Element;
//import org.apache.log4j.Logger;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
///**
// * 开奖公告
// *
// * @author hhhh
// */
//@Repository
//@EhcacheInit(name = "OpenprizeInfoCache")
//public class OpenprizeInfoCache extends GetCacheCommon {
//
//    Logger logger = Logger.getLogger(OpenprizeInfoCache.class);
////    @Resource
////    private IOpenprizeInfoDao openprizeInfoDao;
//
//    /**
//     * 查询数据库中玩法投注方式配置数据列表 并放入缓存中。
//     *
//     * @param jdbcTemplate
//     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3
//     */
//    @Override
//    public int init(JdbcTemplate jdbcTemplate) {
////        Cache cosmosCache = getCosmosCache();   //获取缓存
////        if (cosmosCache == null) {
////            logger.error("no cache can configuration");
////            return -3;
////        }
////        try {
////            List<PlayBetMode> lp = playBetModeDao.selectPlayBetModeList(jdbcTemplate);
////            if (lp.isEmpty()) {
////                logger.error(" there is no data in db");
////                return -1;
////            }
////            for (PlayBetMode pb : lp) {
////                String key = CacheKeysUtil.getPlayBetModeKey(pb.getGame_id(), pb.getPlay_id(), pb.getBet_mode());
////                Element element = new Element(key, pb);
////                cosmosCache.put(element);
////            }
////            logger.info("放入缓存的数据条数：" + lp.size());
////            return 0;
////        } catch (Exception ex) {
////            logger.error("", ex);
////            return -2;
////        }
//        logger.info("未定。。。。。。。");
//        return 0;
//    }
//
//    /**
//     * 根据Key查询对应的开奖公告对象
//     * @param gameId
//     * @param drawId
//     * @param kdrawId
//     * @return 
//     */
//    public OpenprizeInfo getOpenPrizeById(int gameId, int drawId, int kdrawId) {
//        String key = CacheKeysUtil.getOpenPrizeKey(gameId, drawId, kdrawId);
//        Cache cosmosCache = getCosmosCache();
//        if (cosmosCache == null) {
//            logger.error("no cache configuration");
//            return null;
//        }
//        try {
//            Element e = cosmosCache.get(key);
//            if (e == null) {
//                logger.error("no open prize info find in ehcache");
//                return null;
//            }
//            return (OpenprizeInfo) e.getObjectValue();
//        } catch (IllegalStateException | CacheException e) {
//            logger.error("", e);
//            return null;
//        }
//    }
//
//}
