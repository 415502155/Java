//package com.bestinfo.ehcache.business;
//
//import com.bestinfo.bean.business.TmnInfo;
//import com.bestinfo.dao.business.ITmnInfoDao;
//import com.bestinfo.cache.keys.GetCacheCommon;
//import com.bestinfo.ehcache.annotation.EhcacheInit;
//import com.bestinfo.cache.keys.CacheKeysUtil;
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
// * 投注终端缓存操作类
// *
// * @author user
// */
//@Repository
//@EhcacheInit(name = "TmnInfoCache")
//public class TmnInfoCache extends GetCacheCommon {
//
//    Logger logger = Logger.getLogger(TmnInfoCache.class);
//    @Resource
//    private ITmnInfoDao tmnInfoDao;
//    
//    @Resource
//    private JdbcTemplate termJdbcTemplate; //终端库，请与项目配置核对此名
//
//    /**
//     * 查询数据库中投注终端数据列表 并放入缓存中。
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
//            List<TmnInfo> lt = tmnInfoDao.selectTmnInfo(termJdbcTemplate);
//            if (lt.isEmpty()) {
//                logger.error(" there is no data in db");
//                return -1;
//            }
//            for (TmnInfo ti : lt) {
//                String key = CacheKeysUtil.getTmnInfoKey(ti.getTerminal_id());
//                Element element = new Element(key, ti);
//                cosmosCache.put(element);
//            }
//            logger.info("放入缓存的数据条数：" + lt.size());
//            return 0;
//        } catch (Exception ex) {
//            logger.error("", ex);
//            return -2;
//        }
//    }
//
//    /**
//     * 查询数据库中投注终端数据列表 并放入缓存中。
//     *
//     * @param jdbcTemplate
//     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3
//     */
////    public int addTmnInfoToCache(JdbcTemplate jdbcTemplate) {
////        Cache cosmosCache = getCosmosCache();   //获取缓存
////        if (cosmosCache == null) {
////            logger.error("no cache can configuration");
////            return -3;
////        }
////        try {
////            List<TmnInfo> lt = tmnInfoDao.selectTmnInfo(jdbcTemplate);
////            if (lt.isEmpty()) {
////                logger.error(" there is no data in db");
////                return -1;
////            }
////            for (TmnInfo ti : lt) {
////                String key = CacheKeysUtil.getTmnInfoKey(ti.getTerminal_id());
////                Element element = new Element(key, ti);
////                cosmosCache.put(element);
////            }
////            logger.info("放入缓存的数据条数：" + lt.size());
////            return 0;
////        } catch (Exception ex) {
////            logger.error("", ex);
////            return -2;
////        }
////    }
//
//    /**
//     * 根据Key查询对应的缓存数据对象
//     *
//     * @param terminalId
//     * @return 投注终端
//     */
//    public TmnInfo getTmnInfoById(int terminalId) {
//        String key = CacheKeysUtil.getTmnInfoKey(terminalId);
//        Cache cosmosCache = getCosmosCache();
//        if (cosmosCache == null) {
//            logger.error("no cache configuration   terminalId:"+terminalId);
//            return null;
//        }
//        try {
//            Element e = cosmosCache.get(key);
//            return (TmnInfo) e.getObjectValue();
//        } catch (Exception e) {
//            logger.error("select tmn info from Ehcache error where terminalId = "+terminalId, e);
//            return null;
//        }
//    }
//
//    /**
//     * 更新缓存中某Key所对应的数据
//     *
//     * @param jdbcTemplate
//     * @param ti
//     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
//     */
//    public int updateTmnInfoById(JdbcTemplate jdbcTemplate, TmnInfo ti) {
//        Cache cosmosCache = getCosmosCache();
//        if (cosmosCache == null) {
//            logger.error("no cache configuration");
//            return -3;
//        }
//        try {
//            //首先更新数据库
//            int re = tmnInfoDao.updateTmnInfo(jdbcTemplate, ti);
//            if (re < 0) {
//                logger.error("update kdrawProStatus db error");
//                return -1;
//            }
//            String key = CacheKeysUtil.getTmnInfoKey(ti.getTerminal_id());
//            Element e = new Element(key, ti);
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
//     * 增加终端信息到缓存中
//     *
//     * @param ti 终端信息
//     * @return 成功（0）-- 未知错误（-2）-- 未配置缓存空间（-3
//     */
//    public int addTmnInfoToCache(TmnInfo ti) {
//        Cache cosmosCache = getCosmosCache();   //获取缓存
//        if (cosmosCache == null) {
//            logger.error("no cache can configuration");
//            return -3;
//        }
//        try {
//            String key = CacheKeysUtil.getTmnInfoKey(ti.getTerminal_id());
//            Element element = new Element(key, ti);
//            cosmosCache.put(element);
//            return 0;
//        } catch (Exception ex) {
//            logger.error("", ex);
//            return -2;
//        }
//    }
//    
//     /**
//     * 查询缓存中的终端信息的所有数据
//     * @return 终端信息数据集合
//     */
//    public List<TmnInfo> getTmnInfoList() {
//        try {
//            Cache cosmosCache = getCosmosCache();
//            if (cosmosCache == null) {
//                logger.error("no cache configuration");
//                return null;
//            }
//            List<TmnInfo> list = new ArrayList<TmnInfo>();
//            List allKeysList = cosmosCache.getKeys();
//            for (Object key : allKeysList) {
//                int skey = ((String) key).indexOf(CacheKeysUtil.tmnInfoKey);
//                if (skey >= 0) {
//                    Element e = cosmosCache.get(key);
//                    TmnInfo ti = (TmnInfo) e.getObjectValue();
//                    list.add(ti);
//                }
//            }
//            return list;
//        } catch (Exception e) {
//            logger.error("", e);
//            return null;
//        }
//    }
//    
//    /**
//     * 更新终端信息中的终端同步字 字段 ----缓存与库都更新了
//     * @param jdbcTemplate
//     * @param tmnInfo
//     * @return 
//     */
//    public int updateTerminalSynNo(JdbcTemplate jdbcTemplate,TmnInfo tmnInfo) {
//        int terminal_syn_no = tmnInfo.getTerminal_syn_no();
//        terminal_syn_no = terminal_syn_no + 1;
//        tmnInfo.setTerminal_syn_no(terminal_syn_no);
//        int re1 = updateTmnInfoById(jdbcTemplate, tmnInfo);
//        if (re1 < 0) {
//            logger.error("update terminal_syn_no failed from Ehcache or DB:" + re1+" where terminal_id = "+tmnInfo.getTerminal_id());
//            return -1;
//        }
//        return 0;
//    }
//    
//}
