package com.bestinfo.ehcache;

import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.bean.encoding.DealerType;
import com.bestinfo.dao.encoding.IDealerType;
import com.bestinfo.ehcache.annotation.EhcacheInit;
import com.bestinfo.cache.keys.CacheKeysUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 代销商类型缓存操作类
 *
 * @author user
 */
@Repository
@EhcacheInit(name = "DealerTypeCache")
public class DealerTypeCache extends GetCacheCommon {

    Logger logger = Logger.getLogger(DealerTypeCache.class);
    @Resource
    private IDealerType dealerType;

    /**
     * 查询数据库中代销商类型数据列表 并放入缓存中。
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
            List<DealerType> ld = dealerType.selectDealerType(jdbcTemplate);
            if (ld == null || ld.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (DealerType dt : ld) {
                String key = CacheKeysUtil.getDealerTypeKey(dt.getDealer_type());
                Element element = new Element(key, dt);
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
     * 查询数据库中代销商类型数据列表 并放入缓存中。
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3
     */
    public int addDealerTypeToCache(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();   //获取缓存
        if (cosmosCache == null) {
            logger.error("no cache can configuration");
            return -3;
        }
        try {
            List<DealerType> ld = dealerType.selectDealerType(jdbcTemplate);
            if (ld == null || ld.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (DealerType dt : ld) {
                String key = CacheKeysUtil.getDealerTypeKey(dt.getDealer_type());
                Element element = new Element(key, dt);
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
     * 根据Key查询对应的缓存数据对象
     *
     * @param dealerType
     * @return 交易类型
     */
    public DealerType getDealerTypeById(int dealerType) {
        String key = CacheKeysUtil.getDealerTypeKey(dealerType);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get dealertype from ehcache error");
                return null;
            }
            return (DealerType) e.getObjectValue();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 更新缓存中某Key所对应的数据
     *
     * @param jdbcTemplate
     * @param dt
     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int updateDealerTypeById(JdbcTemplate jdbcTemplate, DealerType dt) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            //首先更新数据库
            int re = dealerType.updateDealerType(jdbcTemplate, dt);
            if (re < 0) {
                logger.error("update kdrawProStatus db error");
                return -1;
            }
            String key = CacheKeysUtil.getDealerTypeKey(dt.getDealer_type());
            Element e = new Element(key, dt);
            //更新缓存
            cosmosCache.put(e);
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 查询缓存中的代销商类型的所有数据
     *
     * @return 代销商类型数据集合
     */
    public List<DealerType> getDealerTypeList() {
        try {
            Cache cosmosCache = getCosmosCache();
            if (cosmosCache == null) {
                logger.error("no cache configuration");
                return Collections.emptyList();
            }
            List<DealerType> list = new ArrayList<DealerType>();
            List allKeysList = cosmosCache.getKeys();
             if(allKeysList==null || allKeysList.isEmpty()){
                logger.error("the allKeysList is null");
                return Collections.emptyList();
            }
            for (Object key : allKeysList) {
                int skey = ((String) key).indexOf(CacheKeysUtil.dealerTypeKey);
                if (skey >= 0) {
                    Element e = cosmosCache.get(key);
                    if (e == null) {
                        logger.error("get dealertype list from ehcache error");
                         return Collections.emptyList();
                    }
                    DealerType dt = (DealerType) e.getObjectValue();
                    list.add(dt);
                }
            }
            return list;
        } catch (Exception e) {
            logger.error("", e);
            return Collections.emptyList();
        }
    }

}
