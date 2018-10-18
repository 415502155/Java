package com.bestinfo.ehcache;

import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.bean.encoding.SchemeType;
import com.bestinfo.dao.encoding.ISchemeType;
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
 * 方案类型缓存操作类
 *
 * @author hhhh
 */
@Repository
@EhcacheInit(name = "SchemeTypeCache")
public class SchemeTypeCache extends GetCacheCommon {

    Logger logger = Logger.getLogger(SchemeTypeCache.class);

    @Resource
    private ISchemeType schemeType;

    /**
     * 查询方案类型数据，并全部放入缓存
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    @Override
    public int init(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            List<SchemeType> lst = schemeType.selectSchemeType(jdbcTemplate);
            if (lst == null || lst.isEmpty()) {
                logger.error("there is no data in db");
                return -1;
            }

            for (SchemeType st : lst) {
                String key = CacheKeysUtil.getSchemeTypeKey(st.getScheme_type());
                Element e = new Element(key, st);
                cosmosCache.put(e);
            }
            logger.info("放入缓存的数据条数：" + lst.size());
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 查询方案类型数据，并全部放入缓存
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int addSchemeTypeToCache(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            List<SchemeType> lst = schemeType.selectSchemeType(jdbcTemplate);
            if (lst == null || lst.isEmpty()) {
                logger.error("there is no data in db");
                return -1;
            }

            for (SchemeType st : lst) {
                String key = CacheKeysUtil.getSchemeTypeKey(st.getScheme_type());
                Element e = new Element(key, st);
                cosmosCache.put(e);
            }
            logger.info("放入缓存的数据条数：" + lst.size());
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 根据Key查询对应的缓存数据对象
     *
     * @param schemeType 方案类型编号
     * @return 方案类型
     */
    public SchemeType getSchemeTypeByid(int schemeType) {
        String key = CacheKeysUtil.getSchemeTypeKey(schemeType);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get schemetype from ehcache error");
                return null;
            }
            return (SchemeType) e.getObjectValue();
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
    public int updateSchemeTypeByid(JdbcTemplate jdbcTemplate, SchemeType st) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            //首先更新数据库
            int re = schemeType.updateSchemeType(jdbcTemplate, st);
            if (re < 0) {
                logger.error("update schemeType db error");
                return -1;
            }
            String key = CacheKeysUtil.getSchemeTypeKey(st.getScheme_type());
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
