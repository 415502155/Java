package com.bestinfo.ehcache.system;

import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.bean.sysUser.SystemInfo;
import com.bestinfo.bean.sysUser.AddressList;
import com.bestinfo.ehcache.annotation.EhcacheInit;
import com.bestinfo.cache.keys.CacheKeysUtil;
import com.bestinfo.dao.encoding.ISystemInfo;
import com.bestinfo.dao.system.IAddressListDao;
import com.bestinfo.define.system.WorkStatusDefine;
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
 * 应用地址缓存操作类
 *
 * @author hhhh
 */
@Repository
@EhcacheInit(name = "ApplicationUrlCache")
public class ApplicationUrlCache extends GetCacheCommon {

    Logger logger = Logger.getLogger(ApplicationUrlCache.class);

    @Resource
    private IAddressListDao addressListDao;

    @Resource
    private ISystemInfo systemInfoDao;

    @Resource
    private SystemInfoCache systemInfoCache;

    /**
     * 查询应用地址数据，并全部放入缓存
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）---没有省系统数据（-4）
     */
    @Override
    public int init(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -1;
        }
        try {
            //查询出启用状态的地址信息
            List<AddressList> addList = addressListDao.selectAddressList(jdbcTemplate);
            if (addList == null || addList.isEmpty()) {
                logger.error("there is no application url data in db where workstatus is work.");
            }

            List<SystemInfo> sysList = systemInfoDao.selectSystemInfo(jdbcTemplate);
            if (sysList == null || sysList.isEmpty()) {
                logger.error("there is no system info data in Ehcache.");
                return -2;
            }
            int provinceId = sysList.get(0).getProvince_id();

            //先把缓存中的所有地址信息删除
            int re = deleteAddressListByProvinceId(provinceId);
            if (re < 0) {
                logger.error("delete all application url list from Ehcache error.");
                return -3;
            }

            //再把启用状态的地址信息放入缓存
            for (AddressList add : addList) {
                String key = CacheKeysUtil.getAddressListKey(provinceId, add.getApp_id());
                Element e = new Element(key, add);
                cosmosCache.put(e);
            }
            logger.info("放入缓存的数据条数：" + addList.size());
            return 0;
        } catch (Exception e) {
            logger.error("put application url list data into Ehcache error", e);
            return -4;
        }
    }

    /**
     * 对象写入缓存
     *
     * @param addList
     * @return
     */
    public int addObject(List<AddressList> addList) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            List<SystemInfo> sysList = systemInfoCache.getSystemInfoList();
            if (sysList == null || sysList.isEmpty()) {
                logger.error("there is no system info data in Ehcache.");
                return -4;
            }
            int provinceId = sysList.get(0).getProvince_id();

            for (AddressList add : addList) {
                if (add.getWork_status() == WorkStatusDefine.work) {
                    //当状态为启用时
                    String key = CacheKeysUtil.getAddressListKey(provinceId, add.getApp_id());
                    Element e = new Element(key, add);
                    cosmosCache.put(e);
                }
            }
            return 0;
        } catch (Exception e) {
            logger.error("put application url list data into Ehcache error", e);
            return -2;
        }
    }

    /**
     * 根据Key查询对应的缓存数据对象
     *
     * @param provinceId
     * @param appId
     * @return 地址对象
     */
    public AddressList getAddressListByProvinceIdAndAppId(int provinceId, int appId) {
        String key = CacheKeysUtil.getAddressListKey(provinceId, appId);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get addresslist from ehcache error");
                return null;
            }
            return (AddressList) e.getObjectValue();
        } catch (Exception e) {
            logger.error("select address info error where key = " + key + " from Ehcache.", e);
            return null;
        }
    }

    /**
     * 查询缓存中某省的地址列表的所有数据（缓存中的地址信息均为启用状态的数据）
     *
     * @return 该省的所有启用的地址列表数据集合
     */
    public List<AddressList> getAddressListByProvinceId(int provinceId) {
        try {
            Cache cosmosCache = getCosmosCache();
            if (cosmosCache == null) {
                logger.error("no cache configuration");
                return Collections.emptyList();
            }
            List<AddressList> list = new ArrayList<AddressList>();
            List allKeysList = cosmosCache.getKeys();
            if (allKeysList == null || allKeysList.isEmpty()) {
                logger.error("the allKeysList is null");
                return Collections.emptyList();
            }
            for (Object key : allKeysList) {
                int skey = ((String) key).indexOf(CacheKeysUtil.addressKey + provinceId + ":app_id:");
                if (skey >= 0) {
                    Element e = cosmosCache.get(key);
                    if (e == null) {
                        logger.error("get addresslist list from ehcache error");
                        return Collections.emptyList();
                    }
                    AddressList add = (AddressList) e.getObjectValue();
                    list.add(add);
                }
            }
            return list;
        } catch (Exception e) {
            logger.error("when select address list data error from Ehcache.", e);
            return Collections.emptyList();
        }
    }

    /**
     * 删除缓存中所有的地址信息
     *
     * @param provinceId
     * @return
     */
    public int deleteAddressListByProvinceId(int provinceId) {
        try {
            Cache cosmosCache = getCosmosCache();
            if (cosmosCache == null) {
                logger.error("no cache configuration");
                return -1;
            }
            List allKeysList = cosmosCache.getKeys();
            if (allKeysList == null || allKeysList.isEmpty()) {
                logger.error("the allKeysList is null");
                return -2;
            }
            for (Object key : allKeysList) {
                int skey = ((String) key).indexOf(CacheKeysUtil.addressKey + provinceId + ":app_id:");
                if (skey >= 0) {
                    cosmosCache.remove(key);
                }
            }
            return 0;
        } catch (Exception e) {
            logger.error("when delete address list data error from Ehcache.", e);
            return -3;
        }
    }

}
