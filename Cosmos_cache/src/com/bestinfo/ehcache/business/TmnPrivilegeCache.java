//package com.bestinfo.ehcache.business;
//
//import com.bestinfo.bean.business.TmnPrivilege;
//import com.bestinfo.dao.business.ITmnPrivilegeDao;
//import com.bestinfo.cache.keys.GetCacheCommon;
//import com.bestinfo.ehcache.annotation.EhcacheInit;
//import com.bestinfo.cache.keys.CacheKeysUtil;
//import java.util.ArrayList;
//import java.util.List;
//import javax.annotation.Resource;
//import net.sf.ehcache.Cache;
//import net.sf.ehcache.CacheException;
//import net.sf.ehcache.Element;
//import org.apache.log4j.Logger;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
///**
// * 投注终端特权缓存操作类
// *
// * @author user
// */
//@Repository
//@EhcacheInit(name = "TmnPrivilegeCache")
//public class TmnPrivilegeCache extends GetCacheCommon {
//
//    Logger logger = Logger.getLogger(TmnPrivilegeCache.class);
//    
//    @Resource
//    private ITmnPrivilegeDao tmnPrivilegeDao;
//    
//    @Resource
//    private JdbcTemplate termJdbcTemplate; //终端库，请与项目配置核对此名
//
//    /**
//     * 查询数据库中投注终端特权数据列表 并放入缓存中。
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
//            List<TmnPrivilege> lt = tmnPrivilegeDao.selectTmnPrivilege(termJdbcTemplate);
//            if (lt.isEmpty()) {
//                logger.error(" there is no data in db");
//                return -1;
//            }
//            for (TmnPrivilege tp : lt) {
//                String key = CacheKeysUtil.getTmnPrivilegeKey(tp.getTerminal_id(), tp.getGame_id());
//                Element element = new Element(key, tp);
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
//     * 增加指定终端特权信息到缓存中
//     *
//     * @param ltp 终端特权信息
//     * @return 成功（0）-- 未知错误（-2）-- 未配置缓存空间（-3）
//     */
//    public int addTmnPrivilegeToCache(List<TmnPrivilege> ltp) {
//        Cache cosmosCache = getCosmosCache();   //获取缓存
//        if (cosmosCache == null) {
//            logger.error("no cache can configuration");
//            return -3;
//        }
//        try {
//            for (TmnPrivilege tp : ltp) {
//                String key = CacheKeysUtil.getTmnPrivilegeKey(tp.getTerminal_id(), tp.getGame_id());
//                Element element = new Element(key, tp);
//                cosmosCache.put(element);
//            }
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
//     * @param terminalId
//     * @param gameId
//     * @return
//     */
//    public TmnPrivilege getTmnPrivilegeById(int terminalId, int gameId) {
//        String key = CacheKeysUtil.getTmnPrivilegeKey(terminalId, gameId);
//        Cache cosmosCache = getCosmosCache();
//        if (cosmosCache == null) {
//            logger.error("no cache configuration");
//            return null;
//        }
//        try {
//            Element e = cosmosCache.get(key);
//            return (TmnPrivilege)e.getObjectValue();
//        } catch (Exception e) {
//            logger.error("", e);
//            return null;
//        }
//    }
//
//    /**
//     * 更新缓存中的开奖封机字段
//     *
//     * @param gameid
//     * @param stopgame
//     * @return 0成功，<0失败
//     */
//    public int updateTmnPrivilegeByKey(int gameid, int stopgame) {
//        String key = CacheKeysUtil.getTmnPrivilegeKey(0, 0);
//        Cache cosmosCache = getCosmosCache();
//        if (cosmosCache == null) {
//            logger.error("no cache configuration");
//            return -1;
//        }
//        try {
//            List allKeysList = cosmosCache.getKeys();
//            for (Object okey : allKeysList) {
//                int skey = ((String) okey).indexOf(key);
//                if (skey < 0) {
//                    continue;
//                }
//                int tkey = ((String) okey).indexOf(Integer.toString(gameid));
//                if (tkey < 0) {
//                    continue;
//                }
//                logger.warn("get tmn privileges:" + okey);
//                Element e = cosmosCache.get(okey);
//                TmnPrivilege tp = (TmnPrivilege) e.getObjectValue();
//                tp.setGame_stop(stopgame);
//                Element eup = new Element(key, tp);
//                cosmosCache.put(eup);
//            }
//        } catch (Exception e) {
//            logger.error("", e);
//            return -2;
//        }
//        return 0;
//    }
//
//    /**
//     * 更新缓存中某Key所对应的数据
//     *
//     * @param jdbcTemplate
//     * @param tp
//     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
//     */
//    public int updateTmnPrivilegeById(JdbcTemplate jdbcTemplate, TmnPrivilege tp) {
//        Cache cosmosCache = getCosmosCache();
//        if (cosmosCache == null) {
//            logger.error("no cache configuration");
//            return -3;
//        }
//        try {
//            //首先更新数据库
//            int re = tmnPrivilegeDao.updateTmnPrivilege(jdbcTemplate, tp);
//            if (re < 0) {
//                logger.error("update tmn privilege failed from DB where terminalId = "+tp.getTerminal_id() +" and gameid = "+tp.getGame_id()+" and cur_draw_id = "+tp.getCur_draw_id());
//                return -1;
//            }
//            String key = CacheKeysUtil.getTmnPrivilegeKey(tp.getTerminal_id(), tp.getGame_id());
//            Element e = new Element(key, tp);
//            //更新缓存
//            cosmosCache.put(e);
//            return 0;
//        } catch (Exception e) {
//            logger.error("update tmn privilege error from DB or Ehcache where terminalId = "+tp.getTerminal_id()+" and gameId = "+tp.getGame_id(), e);
//            return -2;
//        }
//    }
//
//    /**
//     * 根据投注机号从缓存中查询出对应的特权信息
//     *
//     * @param tmnId
//     * @return
//     */
//    public List<TmnPrivilege> getTmnPrivilegeList(int tmnId) {
//        try {
//            Cache cosmosCache = getCosmosCache();
//            if (cosmosCache == null) {
//                logger.error("no cache configuration");
//                return null;
//            }
//            List<TmnPrivilege> list = new ArrayList<TmnPrivilege>();
//            List allKeysList = cosmosCache.getKeys();
//            for (Object key : allKeysList) {
//                String tkey = CacheKeysUtil.getTmnPrivilegeKey(tmnId, 0);
//                int skey = ((String) key).indexOf(tkey);
//                if (skey >= 0) {
//                    Element e = cosmosCache.get(key);
//                    TmnPrivilege tp = (TmnPrivilege) e.getObjectValue();
//                    list.add(tp);
//                }
//            }
//            return list;
//        } catch (IllegalStateException | CacheException e) {
//            logger.error("", e);
//            return null;
//        }
//    }
//
//    /**
//     * 删除指定投注机下的所有特权的缓存数据
//     *
//     * @param tmnId
//     * @return 0（成功） -1（未知错误） -2（未配置缓存空间）
//     */
//    public int deleteTmnPrivilegeCacheByTmnId(int tmnId) {
//        Cache cosmosCache = getCosmosCache();
//        if (cosmosCache == null) {
//            logger.error("no cache configuration");
//            return -2;
//        }
//        String dekey = CacheKeysUtil.getTmnPrivilegeKey(tmnId, 0);
//        try {
//            List allKeysList = cosmosCache.getKeys();
//            for (Object key : allKeysList) {
//                int skey = ((String) key).indexOf(dekey);
//                if (skey >= 0) {
//                    cosmosCache.remove(key);
//                }
//            }
//            return 0;
//        } catch (Exception e) {
//            logger.error("", e);
//            return -1;
//        }
//    }
//}
