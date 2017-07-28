package com.bestinfo.cache.keys;

import javax.annotation.Resource;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.apache.log4j.Logger;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author hhhh
 */
public abstract class GetCacheCommon {

    private static final Logger logger = Logger.getLogger(GetCacheCommon.class);
    private final static String COSMOS_CACHE = "cosmosCache";
    private final static String Quantum_CACHE = "QuantumCache";
    
    @Resource
    private EhCacheCacheManager ehCacheCacheManager;

    /**
     * 获取spring容器中的EhCacheCacheManager
     *
     * @return
     */
//    private CacheManager getcachemanager() {
//        WebApplicationContext wac = ContextLoaderListener.getCurrentWebApplicationContext();
//        EhCacheCacheManager ehCacheCacheManager = (EhCacheCacheManager) wac.getBean("ehCacheCacheManager");
//        CacheManager cacheManager = ehCacheCacheManager.getCacheManager();
//        return cacheManager;
//    }

    /**
     * 获取cosmosCache
     *
     * @return
     */
    public Cache getCosmosCache() {
        try {
//            CacheManager cacheManager = getcachemanager();
            CacheManager cacheManager = ehCacheCacheManager.getCacheManager();
            Cache cache = cacheManager.getCache(COSMOS_CACHE);
            return cache;
        } catch (IllegalStateException | ClassCastException e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 获取quantumCache
     *
     * @return
     */
    public Cache getQuantumCache() {
        try {
//            CacheManager cacheManager = getcachemanager();
            CacheManager cacheManager = ehCacheCacheManager.getCacheManager();
            Cache cache = cacheManager.getCache(Quantum_CACHE);
            return cache;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 数据初始化方法
     *
     * @param jdbcTemplate
     * @return
     */
    public abstract int init(JdbcTemplate jdbcTemplate);

}
