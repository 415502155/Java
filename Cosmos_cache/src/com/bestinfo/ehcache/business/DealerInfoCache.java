package com.bestinfo.ehcache.business;

import com.bestinfo.bean.business.DealerInfo;
import com.bestinfo.bean.business.DealerPrivilege;
import com.bestinfo.dao.business.IDealerInfoDao;
import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.ehcache.annotation.EhcacheInit;
import com.bestinfo.cache.keys.CacheKeysUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 代销商信息缓存操作类
 */
@Repository
@EhcacheInit(name = "DealerInfoCache")
public class DealerInfoCache extends GetCacheCommon {

    private static final Logger logger = Logger.getLogger(DealerInfoCache.class);

    @Resource
    private IDealerInfoDao dealerInfoDao;

    /**
     * 查询数据库中代销商信息数据列表 并放入缓存中。
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
            List<DealerInfo> ld = dealerInfoDao.getAllDealerInfo(jdbcTemplate);
            if (ld.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (DealerInfo di : ld) {
                String key = CacheKeysUtil.getDealerInfoKey(di.getDealer_id());
                Element element = new Element(key, di);
                cosmosCache.put(element);
            }
            logger.info("放入缓存的数据条数：" + ld.size());
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -2;
        }
    }

    /**
     * 增加代销商信息到缓存中
     *
     * @param di 代销商信息
     * @return 成功（0）-- 未知错误（-2）-- 未配置缓存空间（-3
     */
    public int addDealerInfoToCache(DealerInfo di) {
        Cache cosmosCache = getCosmosCache();   //获取缓存
        if (cosmosCache == null) {
            logger.error("no cache can configuration");
            return -3;
        }
        try {
            String key = CacheKeysUtil.getDealerInfoKey(di.getDealer_id());
            Element element = new Element(key, di);
            cosmosCache.put(element);
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -2;
        }
    }

    /**
     * 根据代销商编号查询缓存中的代销商信息
     *
     * @param dealerId 代销商信息编号
     * @return 代销商信息
     */
    public DealerInfo getDealerInfoById(String dealerId) {
        String key = CacheKeysUtil.getDealerInfoKey(dealerId);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get dealerinfo from ehcache error");
                return null;
            }
            return (DealerInfo) e.getObjectValue();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 根据代销商编号和游戏编号查询代销商的游戏特权
     *
     * @param dealerId 代销商编号
     * @param gameId 游戏编号
     * @return 代销商游戏特权 出错时返回null
     */
    public DealerPrivilege findDealerPrivilege(String dealerId, int gameId) {
        String key = CacheKeysUtil.getDealerPrivilegeKey(dealerId, gameId);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get dealerinfo from ehcache error");
                return null;
            }
            return (DealerPrivilege) e.getObjectValue();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

    }

    /**
     * 根据key更新代销商信息，更新缓存同时，更新数据库
     *
     * @param jdbcTemplate
     * @param di
     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
//    public int updateDealerInfoById(JdbcTemplate jdbcTemplate, DealerInfo di) {
//        Cache cosmosCache = getCosmosCache();
//        if (cosmosCache == null) {
//            logger.error("no cache configuration");
//            return -3;
//        }
//        try {
//            //首先更新数据库
//            int re = dealerInfoDao.updateDealerInfo(jdbcTemplate, di);
//            if (re < 0) {
//                logger.error("update dealerInfo db error");
//                return -1;
//            }
//            String key = CacheKeysUtil.getDealerInfoKey(di.getDealer_id());
//            Element e = new Element(key, di);
//            //更新缓存
//            cosmosCache.put(e);
//            return 0;
//        } catch (Exception e) {
//            logger.error("", e);
//            return -2;
//        }
//    }
    /**
     * 查询缓存中的代销商信息的所有数据
     *
     * @return 代销商信息数据集合
     */
    public List<DealerInfo> getDealerInfoList() {
        try {
            Cache cosmosCache = getCosmosCache();
            if (cosmosCache == null) {
                logger.error("no cache configuration");
                return null;
            }
            List<DealerInfo> list = new ArrayList<DealerInfo>();
            List allKeysList = cosmosCache.getKeys();
            for (Object key : allKeysList) {
                int skey = ((String) key).indexOf(CacheKeysUtil.dealerInfoKey);
                if (skey >= 0) {
                    Element e = cosmosCache.get(key);
                    if (e == null) {
                        logger.error("get dealerinfo list from ehcache error");
                        return null;
                    }
                    DealerInfo di = (DealerInfo) e.getObjectValue();
                    list.add(di);
                }
            }
            return list;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    public List<DealerInfo> getDealerInfoList(String id) {
        try {
            Cache cosmosCache = getCosmosCache();
            if (cosmosCache == null) {
                logger.error("no cache configuration");
                return null;
            }
            List<DealerInfo> list = new ArrayList<DealerInfo>();
            List allKeysList = cosmosCache.getKeys();
            String key = CacheKeysUtil.getCmsInfoKey(Integer.parseInt(id));

            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get dealerinfo list from ehcache error");
                return null;
            }
            DealerInfo di = (DealerInfo) e.getObjectValue();
            return list;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

    }

}
