package com.bestinfo.ehcache;

import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.bean.encoding.SourceType;
import com.bestinfo.dao.encoding.ISourceType;
import com.bestinfo.ehcache.annotation.EhcacheInit;
import com.bestinfo.cache.keys.CacheKeysUtil;
import java.util.List;
import javax.annotation.Resource;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 资金来源类型缓存操作类
 *
 * @author user
 */
@Repository
@EhcacheInit(name = "SourceTypeCache")
public class SourceTypeCache extends GetCacheCommon {

    Logger logger = Logger.getLogger(SourceTypeCache.class);
    @Resource
    private ISourceType sourceType;

    /**
     * 查询数据库中资金来源类型数据列表 并放入缓存中。
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
            List<SourceType> ls = sourceType.selectSourceType(jdbcTemplate);
            if (ls == null || ls.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (SourceType st : ls) {
                String key = CacheKeysUtil.getSourceTypeKey(st.getSource_type());
                Element element = new Element(key, st);
                cosmosCache.put(element);
            }
            logger.info("放入缓存的数据条数：" + ls.size());
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -2;
        }
    }

    /**
     * 查询数据库中资金来源类型数据列表 并放入缓存中。
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3
     */
    public int addSourceTypeToCache(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();   //获取缓存
        if (cosmosCache == null) {
            logger.error("no cache can configuration");
            return -3;
        }
        try {
            List<SourceType> ls = sourceType.selectSourceType(jdbcTemplate);
            if (ls == null || ls.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (SourceType st : ls) {
                String key = CacheKeysUtil.getSourceTypeKey(st.getSource_type());
                Element element = new Element(key, st);
                cosmosCache.put(element);
            }
            logger.info("放入缓存的数据条数：" + ls.size());
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -2;
        }
    }

    /**
     * 根据Key查询对应的缓存数据对象
     *
     * @param sourceType
     * @return 交易类型
     */
    public SourceType getSourceTypeById(String sourceType) {
        String key = CacheKeysUtil.getSourceTypeKey(sourceType);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get sourcetype from ehcache error");
                return null;
            }
            return (SourceType) e.getObjectValue();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 更新缓存中某Key所对应的数据
     *
     * @param jdbcTemplate
     * @param st
     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int updateSourceTypeById(JdbcTemplate jdbcTemplate, SourceType st) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            //首先更新数据库
            int re = sourceType.updateSourceType(jdbcTemplate, st);
            if (re < 0) {
                logger.error("update kdrawProStatus db error");
                return -1;
            }
            String key = CacheKeysUtil.getSourceTypeKey(st.getSource_type());
            Element e = new Element(key, st);
            //更新缓存
            cosmosCache.put(e);
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

}
