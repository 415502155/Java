package com.bestinfo.ehcache.system;

import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.bean.sysUser.SystemInfo;
import com.bestinfo.dao.encoding.ISystemInfo;
import com.bestinfo.ehcache.annotation.EhcacheInit;
import com.bestinfo.cache.keys.CacheKeysUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 省系统参数缓存操作类
 */
@Repository
@EhcacheInit(name = "SystemInfoCache")
public class SystemInfoCache extends GetCacheCommon {

    Logger logger = Logger.getLogger(SystemInfoCache.class);

    @Resource
    private ISystemInfo systemInfo;

    /**
     * 查询省系统参数数据，并全部放入缓存
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
            List<SystemInfo> lsi = systemInfo.selectSystemInfo(jdbcTemplate);
            if (lsi == null || lsi.isEmpty()) {
                logger.error("there is no data in db");
                return -1;
            }

            for (SystemInfo si : lsi) {//key id存为省id
                String key = CacheKeysUtil.getSystemInfoKey(si.getProvince_id());
                Element e = new Element(key, si);
                cosmosCache.put(e);
            }
            for (SystemInfo si : lsi) {//key id存为0
                String key = CacheKeysUtil.getSystemInfoKey();
                Element e = new Element(key, si);
                cosmosCache.put(e);
            }
            logger.info("system info:" + lsi.size());
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 查询省系统参数
     *
     * @return 省系统参数
     */
    public SystemInfo getSystemInfoByid() {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            String key = CacheKeysUtil.getSystemInfoKey();
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get SystemInfo from ehcache error,key:" + key);
                return null;
            }
            return (SystemInfo) e.getObjectValue();
        } catch (IllegalStateException | CacheException e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 根据Key查询对应的缓存数据对象
     *
     * @param provinceId 省系统编号
     * @return 省系统参数
     */
    public SystemInfo getSystemInfoByid(int provinceId) {
        String key = CacheKeysUtil.getSystemInfoKey(provinceId);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get systeminfo from ehcache error");
                return null;
            }
            return (SystemInfo) e.getObjectValue();
        } catch (IllegalStateException | CacheException e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 更新省系统信息中的系统同步字 字段 ----缓存与库都更新了
     *
     * @param jdbcTemplate
     * @return
     */
    public int updateSynSystemInfoByid(JdbcTemplate jdbcTemplate) {
        List<SystemInfo> sysList = getSystemInfoList();
        if (sysList == null || sysList.isEmpty()) {
            logger.error("there is no system info data in Ehcache.");
            return -1;
        }
        SystemInfo si = sysList.get(0);
        int system_syn_no = si.getSystem_syn_no();
        system_syn_no = system_syn_no + 1;
        si.setSystem_syn_no(system_syn_no);
        int re1 = updateSystemInfoByid(jdbcTemplate, si);
        if (re1 < 0) {
            logger.error("update system_syn_no failed from Ehcache or DB:" + re1);
            return -2;
        }
        return 0;
    }

    /**
     * 更新缓存中某Key所对应的数据--先更新库，再更新缓存
     *
     * @param jdbcTemplate
     * @param si
     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int updateSystemInfoByid(JdbcTemplate jdbcTemplate, SystemInfo si) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            //首先更新数据库
            int re = systemInfo.updateSystemInfo(jdbcTemplate, si);
            if (re < 0) {
                logger.error("update SystemInfo db error");
                return -1;
            }
            String key = CacheKeysUtil.getSystemInfoKey(si.getProvince_id());
            Element e = new Element(key, si);
            //更新缓存
            cosmosCache.put(e);
            return 0;
        } catch (IllegalArgumentException | IllegalStateException | CacheException e) {
            logger.error("update SystemInfo from Ehcache or DB error.", e);
            return -2;
        }
    }

    /**
     * 查询缓存中的省系统的所有数据
     *
     * @return 省系统数据集合
     */
    public List<SystemInfo> getSystemInfoList() {
        try {
            Cache cosmosCache = getCosmosCache();
            if (cosmosCache == null) {
                logger.error("no cache configuration");
                return Collections.emptyList();
            }
            List<SystemInfo> list = new ArrayList<SystemInfo>();
            List allKeysList = cosmosCache.getKeys();
            if (allKeysList == null || allKeysList.isEmpty()) {
                logger.error("the allKeysList is null");
                return Collections.emptyList();
            }
            for (Object key : allKeysList) {
                int skey = ((String) key).indexOf(CacheKeysUtil.systemInfoKey);
                if (skey >= 0) {
                    Element e = cosmosCache.get(key);
                    if (e == null) {
                        logger.error("get dealerinfo list from ehcache error");
                        return Collections.emptyList();
                    }
                    SystemInfo si = (SystemInfo) e.getObjectValue();
                    list.add(si);
                }
            }
            return list;
        } catch (IllegalStateException | CacheException e) {
            logger.error("select system info data from Ehcache error.", e);
            return Collections.emptyList();
        }
    }
}
