package com.bestinfo.cache.lh;

//package com.bestinfo.cache.proto;
//
//import com.bestinfo.ehcache.*;
//import com.bestinfo.bean.proto.CommPara;
//import com.bestinfo.ehcache.annotation.EhcacheInit;
//import com.bestinfo.cache.keys.CacheKeysUtil;
//import com.bestinfo.dao.lh.ICommParaDao;
//import java.util.ArrayList;
//import java.util.List;
//import javax.annotation.Resource;
//import net.sf.ehcache.Cache;
//import net.sf.ehcache.Element;
//import org.apache.log4j.Logger;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
///**
// * 通讯参数缓存操作类
// *
// * @author hhhh
// */
//@Repository
//@EhcacheInit(name = "CommParaCache")
//public class CommParaCache extends GetCacheCommon {
//
//    Logger logger = Logger.getLogger(CommParaCache.class);
//    @Resource
//    private ICommParaDao commParaDao;
//
//    /**
//     * 查询数据库中通讯参数数据列表 并放入缓存中。
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
//            List<CommPara> cpList = commParaDao.selectCommPara(jdbcTemplate);
//            if (cpList.isEmpty()) {
//                logger.error(" there is no data in db");
//                return -1;
//            }
//            for (CommPara cp : cpList) {
//                String key = CacheKeysUtil.getCommParaKey(cp.getComm_type());
//                Element element = new Element(key, cp);
//                cosmosCache.put(element);
//            }
//            logger.info("放入缓存的数据条数：" + cpList.size());
//            return 0;
//        } catch (Exception ex) {
//            logger.error("", ex);
//            return -2;
//        }
//    }
//
//    /**
//     * 查询数据库中通讯参数数据列表 并放入缓存中。
//     *
//     * @param jdbcTemplate
//     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3
//     */
//    public int addCommParaToCache(JdbcTemplate jdbcTemplate) {
//        Cache cosmosCache = getCosmosCache();   //获取缓存
//        if (cosmosCache == null) {
//            logger.error("no cache can configuration");
//            return -3;
//        }
//        try {
//            List<CommPara> cpList = commParaDao.selectCommPara(jdbcTemplate);
//            if (cpList.isEmpty()) {
//                logger.error(" there is no data in db");
//                return -1;
//            }
//            for (CommPara cp : cpList) {
//                String key = CacheKeysUtil.getCommParaKey(cp.getComm_type());
//                Element element = new Element(key, cp);
//                cosmosCache.put(element);
//            }
//            logger.info("放入缓存的数据条数：" + cpList.size());
//            return 0;
//        } catch (Exception ex) {
//            logger.error("", ex);
//            return -2;
//        }
//    }
//
//      /**
//     * 查询缓存中的通讯参数的所有数据
//     * @return 通讯参数数据集合
//     */
//    public List<CommPara> getCommParaList() {
//        try {
//            Cache cosmosCache = getCosmosCache();
//            if (cosmosCache == null) {
//                logger.error("no cache configuration");
//                return null;
//            }
//            List<CommPara> list = new ArrayList<CommPara>();
//            List allKeysList = cosmosCache.getKeys();
//            for (Object key : allKeysList) {
//                int skey = ((String) key).indexOf(CacheKeysUtil.commParaKey);
//                if (skey >= 0) {
//                    Element e = cosmosCache.get(key);
//                    CommPara cp = (CommPara) e.getObjectValue();
//                    list.add(cp);
//                }
//            }
//            return list;
//        } catch (Exception e) {
//            logger.error("", e);
//            return null;
//        }
//    }
//    
//}
