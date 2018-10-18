package com.bestinfo.ehcache;

import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.bean.encoding.CityInfo;
import com.bestinfo.dao.encoding.ICityInfo;
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
 * 二级城市编号缓存操作类
 *
 * @author hhhh
 */
@Repository
@EhcacheInit(name = "CityInfoCache")
public class CityInfoCache extends GetCacheCommon {

    Logger logger = Logger.getLogger(CityInfoCache.class);

    @Resource
    private ICityInfo cityInfo;

    /**
     * 查询二级城市编号数据，并全部放入缓存
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
            List<CityInfo> lci = cityInfo.selectCityInfo(jdbcTemplate);
            if (lci == null || lci.isEmpty()) {
                logger.error("there is no data in db");
                return -1;
            }

            for (CityInfo ci : lci) {
                String key = CacheKeysUtil.getCityInfoKey(ci.getProvince_id(), ci.getCity_id());
                Element e = new Element(key, ci);
                cosmosCache.put(e);
            }
            logger.info("放入缓存的数据条数：" + lci.size());
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 查询二级城市编号数据，并全部放入缓存
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
//    public int addCityInfoToCache(JdbcTemplate jdbcTemplate) {
//        Cache cosmosCache = getCosmosCache();
//        if (cosmosCache == null) {
//            logger.error("no cache configuration");
//            return -3;
//        }
//        try {
//            List<CityInfo> lci = cityInfo.selectCityInfo(jdbcTemplate);
//            if (lci == null || lci.isEmpty()) {
//                logger.error("there is no data in db");
//                return -1;
//            }
//
//            for (CityInfo ci : lci) {
//                String key = CacheKeysUtil.getCityInfoKey(ci.getProvince_id(), ci.getCity_id());
//                Element e = new Element(key, ci);
//                cosmosCache.put(e);
//            }
//            logger.info("放入缓存的数据条数：" + lci.size());
//            return 0;
//        } catch (Exception e) {
//            logger.error("", e);
//            return -2;
//        }
//    }
    /**
     * 根据Key查询对应的缓存数据对象
     *
     * @param provinceId 省编号
     * @param cityId 地市编号
     * @return 二级城市编号对象
     */
    public CityInfo getCityInfoByid(int provinceId, int cityId) {
        String key = CacheKeysUtil.getCityInfoKey(provinceId, cityId);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get cityinfo from ehcache error");
                return null;
            }
            return (CityInfo) e.getObjectValue();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 更新缓存中某Key所对应的数据
     *
     * @param jdbcTemplate
     * @param ci
     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int updateCityInfoByid(JdbcTemplate jdbcTemplate, CityInfo ci) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            //首先更新数据库
            int re = cityInfo.updateCityInfo(jdbcTemplate, ci);
            if (re < 0) {
                logger.error("update CityInfo db error");
                return -1;
            }
            String key = CacheKeysUtil.getCityInfoKey(ci.getProvince_id(), ci.getCity_id());
            Element e = new Element(key, ci);
            //更新缓存
            cosmosCache.put(e);
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 查询缓存中的二级城市的所有数据
     *
     * @return 二级城市数据集合
     */
    public List<CityInfo> getCityInfoList() {
        try {
            Cache cosmosCache = getCosmosCache();
            if (cosmosCache == null) {
                logger.error("no cache configuration");
                return Collections.emptyList();
            }
            List<CityInfo> list = new ArrayList<CityInfo>();
            List allKeysList = cosmosCache.getKeys();
            if(allKeysList==null || allKeysList.isEmpty()){
                logger.error("the allKeysList is null");
                return Collections.emptyList();
            }
            for (Object key : allKeysList) {
                int skey = ((String) key).indexOf(CacheKeysUtil.cityInfoKey);
                if (skey >= 0) {
                    Element e = cosmosCache.get(key);
                    if (e == null) {
                        logger.error("get all cityinfo list from ehcache error");
                        return Collections.emptyList();
                    }
                    CityInfo ci = (CityInfo) e.getObjectValue();
                    list.add(ci);
                }
            }
            return list;
        } catch (Exception e) {
            logger.error("", e);
             return Collections.emptyList();
        }
    }

    /**
     * 根据省系统Id查询缓存中有关的二级城市的数据
     *
     * @return 该省的二级城市数据集合
     */
    public List<CityInfo> getCityInfoListByProvinceId(int provinceId) {
        try {
            Cache cosmosCache = getCosmosCache();
            if (cosmosCache == null) {
                logger.error("no cache configuration");
                return Collections.emptyList();
            }
            List<CityInfo> list = new ArrayList<CityInfo>();
            List allKeysList = cosmosCache.getKeys();
            if(allKeysList==null || allKeysList.isEmpty()){
                logger.error("the allKeysList is null");
                return Collections.emptyList();
            }
            for (Object key : allKeysList) {
//                int skey = ((String) key).indexOf(CacheKeysUtil.cityInfoKey + provinceId + "city:");
                int skey = ((String) key).indexOf(CacheKeysUtil.getCityInfoKey(provinceId, 0));
                if (skey >= 0) {
                    Element e = cosmosCache.get(key);
                    if (e == null) {
                        logger.error("get cityinfo list from ehcache error");
                       return Collections.emptyList();
                    }
                    CityInfo ci = (CityInfo) e.getObjectValue();
                    list.add(ci);
                }
            }
            return list;
        } catch (Exception e) {
            logger.error("", e);
            return Collections.emptyList();
        }
    }
}
