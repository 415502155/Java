package com.bestinfo.ehcache.system;

import com.bestinfo.bean.business.SystemKey;
import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.ehcache.annotation.EhcacheInit;
import com.bestinfo.cache.keys.CacheKeysUtil;
import com.bestinfo.dao.system.ISystemKeyDao;
import javax.annotation.Resource;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * syetem_key ehcache缓存
 */
@Repository
@EhcacheInit(name = "SystemKeyCache")
public class SystemKeyCache extends GetCacheCommon {

    private static final Logger logger = Logger.getLogger(SystemKeyCache.class);

    @Resource
    private ISystemKeyDao systemKeyDao;

    /**
     * 查询system key数据，把中彩二维码key放入缓存
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 数据错误（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    @Override
    public int init(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            SystemKey qrcodeCenterKey = systemKeyDao.getSystemKey(jdbcTemplate, "clp_qrcode");
            if (qrcodeCenterKey == null) {
                logger.error("there is no clp_qrcode SystemKey in db");
                return 0;
            }

            String key = CacheKeysUtil.getSystemKeyKey(qrcodeCenterKey.getKey_name());
            Element e = new Element(key, qrcodeCenterKey);
            cosmosCache.put(e);

            logger.info("放入缓存的数据条数：1");
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 查询SystemKey
     *
     * @param keyName
     * @return
     */
    public SystemKey getSystemKey(String keyName) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            String key = CacheKeysUtil.getSystemKeyKey(keyName);
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get SystemKey from ehcache error,key:" + key);
                return null;
            }
            return (SystemKey) e.getObjectValue();
        } catch (IllegalStateException | CacheException e) {
            logger.error("", e);
            return null;
        }
    }

}
