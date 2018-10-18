package com.bestinfo.ehcache;

import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.bean.encoding.DistrictInfo;
import com.bestinfo.dao.encoding.IDistrictInfo;
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
 * 三级区县编号缓存操作类
 *
 * @author hhhh
 */
@Repository
@EhcacheInit(name = "DistrictInfoCache")
public class DistrictInfoCache extends GetCacheCommon {

    Logger logger = Logger.getLogger(DistrictInfoCache.class);

    @Resource
    private IDistrictInfo districtInfo;

    /**
     * 查询三级区县编号数据，并全部放入缓存
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
            List<DistrictInfo> ldi = districtInfo.selectDistrictInfo(jdbcTemplate);
            if (ldi == null || ldi.isEmpty()) {
                logger.error("there is no data in db");
                return -1;
            }

            for (DistrictInfo di : ldi) {
                String key = CacheKeysUtil.getDistrictInfoKey(di.getProvince_id(), di.getCity_id(), di.getDistrict_id());
                Element e = new Element(key, di);
                cosmosCache.put(e);
            }
            logger.info("放入缓存的数据条数：" + ldi.size());
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 查询三级区县编号数据，并全部放入缓存
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int addDistrictInfoToCache(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            List<DistrictInfo> ldi = districtInfo.selectDistrictInfo(jdbcTemplate);
            if (ldi == null || ldi.isEmpty()) {
                logger.error("there is no data in db");
                return -1;
            }

            for (DistrictInfo di : ldi) {
                String key = CacheKeysUtil.getDistrictInfoKey(di.getProvince_id(), di.getCity_id(), di.getDistrict_id());
                Element e = new Element(key, di);
                cosmosCache.put(e);
            }
            logger.info("放入缓存的数据条数：" + ldi.size());
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 根据Key查询对应的缓存数据对象
     *
     * @param provinceId 省编号
     * @param cityId 地市编号
     * @param districtId 区县编号
     * @return 三级区县编号对象
     */
    public DistrictInfo getDistrictInfoByid(int provinceId, int cityId, int districtId) {
        String key = CacheKeysUtil.getDistrictInfoKey(provinceId, cityId, districtId);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get districtinfo from ehcache error");
                return null;
            }
            return (DistrictInfo) e.getObjectValue();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 更新缓存中某Key所对应的数据
     *
     * @param jdbcTemplate
     * @param di
     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int updateDistrictInfoByid(JdbcTemplate jdbcTemplate, DistrictInfo di) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            //首先更新数据库
            int re = districtInfo.updateDistrictInfo(jdbcTemplate, di);
            if (re < 0) {
                logger.error("update DistrictInfo db error");
                return -1;
            }
            String key = CacheKeysUtil.getDistrictInfoKey(di.getProvince_id(), di.getCity_id(), di.getDistrict_id());
            Element e = new Element(key, di);
            //更新缓存
            cosmosCache.put(e);
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 查询缓存中三级区县的所有数据
     *
     * @return 三级区县数据集合
     */
    public List<DistrictInfo> getDistrictInfoList() {
        try {
            Cache cosmosCache = getCosmosCache();
            if (cosmosCache == null) {
                logger.error("no cache configuration");
               return Collections.emptyList();
            }
            List<DistrictInfo> list = new ArrayList<DistrictInfo>();
            List allKeysList = cosmosCache.getKeys();
              if(allKeysList==null || allKeysList.isEmpty()){
                logger.error("the allKeysList is null");
                return Collections.emptyList();
            }
            for (Object key : allKeysList) {
                int skey = ((String) key).indexOf(CacheKeysUtil.districtInfoKey);
                if (skey >= 0) {
                    Element e = cosmosCache.get(key);
                    if (e == null) {
                        logger.error("get all districtinfo list from ehcache error");
                        return Collections.emptyList();
                    }
                    DistrictInfo di = (DistrictInfo) e.getObjectValue();
                    list.add(di);
                }
            }
            return list;
        } catch (Exception e) {
            logger.error("", e);
           return Collections.emptyList();
        }
    }

    /**
     * 根据省编号、城市编号，从缓存中查出对应的三级区县的信息
     *
     * @param provinceId
     * @param cityId
     * @return
     */
    public List<DistrictInfo> getDistrictInfoListByProvinceIdAndCityId(int provinceId, int cityId) {
        try {
            Cache cosmosCache = getCosmosCache();
            if (cosmosCache == null) {
                logger.error("no cache configuration");
                return Collections.emptyList();
            }
            List<DistrictInfo> list = new ArrayList<DistrictInfo>();
            List allKeysList = cosmosCache.getKeys();
             if(allKeysList==null || allKeysList.isEmpty()){
                logger.error("the allKeysList is null");
                return Collections.emptyList();
            }
            for (Object key : allKeysList) {
                int skey = ((String) key).indexOf(CacheKeysUtil.districtInfoKey + provinceId + ":city:" + cityId + ":district:");
                if (skey >= 0) {
                    Element e = cosmosCache.get(key);
                    if (e == null) {
                        logger.error("get districtinfo list from ehcache error");
                        return Collections.emptyList();
                    }
                    DistrictInfo di = (DistrictInfo) e.getObjectValue();
                    list.add(di);
                }
            }
            return list;
        } catch (Exception e) {
            logger.error("", e);
            return Collections.emptyList();
        }
    }
}
