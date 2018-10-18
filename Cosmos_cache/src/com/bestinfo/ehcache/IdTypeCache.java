package com.bestinfo.ehcache;

import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.bean.encoding.IdType;
import com.bestinfo.dao.encoding.IIdType;
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
 * 证件类型缓存操作类
 *
 * @author user
 */
@Repository
@EhcacheInit(name = "IdTypeCache")
public class IdTypeCache extends GetCacheCommon {

    Logger logger = Logger.getLogger(IdTypeCache.class);
    @Resource
    private IIdType idType;

    /**
     * 查询数据库中证件类型数据列表 并放入缓存中。
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
            List<IdType> lt = idType.selectIdType(jdbcTemplate);
            if (lt == null || lt.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (IdType it : lt) {
                String key = CacheKeysUtil.getIdTypeKey(it.getId_type_id());
                Element element = new Element(key, it);
                cosmosCache.put(element);
            }
            logger.info("放入缓存的数据条数：" + lt.size());
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -2;
        }
    }

    /**
     * 查询数据库中证件类型数据列表 并放入缓存中。
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3
     */
    public int addIdTypeToCache(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();   //获取缓存
        if (cosmosCache == null) {
            logger.error("no cache can configuration");
            return -3;
        }
        try {
            List<IdType> lt = idType.selectIdType(jdbcTemplate);
            if (lt == null || lt.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (IdType it : lt) {
                String key = CacheKeysUtil.getIdTypeKey(it.getId_type_id());
                Element element = new Element(key, it);
                cosmosCache.put(element);
            }
            logger.info("放入缓存的数据条数：" + lt.size());
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -2;
        }
    }

    /**
     * 根据Key查询对应的缓存数据对象
     *
     * @param idTypeId 证件编号
     * @return 证件类型
     */
    public IdType getIdTypeById(int idTypeId) {
        String key = CacheKeysUtil.getIdTypeKey(idTypeId);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get idtype from ehcache error");
                return null;
            }
            return (IdType) e.getObjectValue();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 更新缓存中某Key所对应的数据
     *
     * @param jdbcTemplate
     * @param it
     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int updateItTypeById(JdbcTemplate jdbcTemplate, IdType it) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            //首先更新数据库
            int re = idType.updateIdType(jdbcTemplate, it);
            if (re < 0) {
                logger.error("update kdrawProStatus db error");
                return -1;
            }
            String key = CacheKeysUtil.getIdTypeKey(it.getId_type_id());
            Element e = new Element(key, it);
            //更新缓存
            cosmosCache.put(e);
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 查询缓存中的证件类型的所有数据
     *
     * @return 证件类型数据集合
     */
    public List<IdType> getIdTypeList() {
        try {
            Cache cosmosCache = getCosmosCache();
            if (cosmosCache == null) {
                logger.error("no cache configuration");
                 return Collections.emptyList();
            }
            List<IdType> list = new ArrayList<IdType>();
            List allKeysList = cosmosCache.getKeys();
             if(allKeysList==null || allKeysList.isEmpty()){
                logger.error("the allKeysList is null");
                return Collections.emptyList();
            }
            for (Object key : allKeysList) {
                int skey = ((String) key).indexOf(CacheKeysUtil.idTypeKey);
                if (skey >= 0) {
                    Element e = cosmosCache.get(key);
                    if (e == null) {
                        logger.error("get idtype list from ehcache error");
                         return Collections.emptyList();
                    }
                    IdType it = (IdType) e.getObjectValue();
                    list.add(it);
                }
            }
            return list;
        } catch (Exception e) {
            logger.error("", e);
             return Collections.emptyList();
        }
    }
}
