package com.bestinfo.ehcache;

import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.bean.business.TCmsInfo;
import com.bestinfo.dao.business.ICmsInfoDao;
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
 * 内容管理缓存操作类
 *
 * @author user
 */
@Repository
@EhcacheInit(name = "CmsInfoCache")
public class CmsInfoCache extends GetCacheCommon {

    Logger logger = Logger.getLogger(CmsInfoCache.class);
    @Resource
    private ICmsInfoDao cmsInfoDao;

    /**
     * 查询数据库中内容管理数据列表 并放入缓存中。
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
            //暂时引掉---clp
//            List<TCmsInfo> lc = cmsInfoDao.selectCmsInfo(jdbcTemplate);
//            if (lc.isEmpty()) {
//                logger.error(" there is no data in db");
//                return -1;
//            }
//            for (TCmsInfo ci : lc) {
//                String key = CacheKeysUtil.getCmsInfoKey(ci.getCms_id());
//                Element element = new Element(key, ci);
//                cosmosCache.put(element);
//            }
//            logger.info("放入缓存的数据条数：" + lc.size());
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -2;
        }
    }

    /**
     * 查询数据库中内容管理数据列表 并放入缓存中。
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3
     */
    public int addCmsInfoToCache(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();   //获取缓存
        if (cosmosCache == null) {
            logger.error("no cache can configuration");
            return -3;
        }
        try {
            List<TCmsInfo> lc = cmsInfoDao.selectCmsInfo(jdbcTemplate);
            if (lc == null || lc.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (TCmsInfo ci : lc) {
                String key = CacheKeysUtil.getCmsInfoKey(ci.getCms_id());
                Element element = new Element(key, ci);
                cosmosCache.put(element);
            }
            logger.info("放入缓存的数据条数：" + lc.size());
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -2;
        }
    }

    /**
     * 根据Key查询对应的缓存数据对象
     *
     * @param cmsId 内容管理编号
     * @return 内容管理
     */
    public TCmsInfo getCmsInfoById(int cmsId) {
        String key = CacheKeysUtil.getCmsInfoKey(cmsId);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get tcmsinfo from ehcache error");
                return null;
            }
            return (TCmsInfo) e.getObjectValue();
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
    public int updateCmsInfoById(JdbcTemplate jdbcTemplate, TCmsInfo ci) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            //首先更新数据库
            int re = cmsInfoDao.updateCmsInfo(jdbcTemplate, ci);
            if (re < 0) {
                logger.error("update kdrawProStatus db error");
                return -1;
            }
            String key = CacheKeysUtil.getCmsInfoKey(ci.getCms_id());
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
     * 查询缓存中的内容管理的所有数据
     *
     * @return 内容管理数据集合
     */
    public List<TCmsInfo> getCmsInfoList() {
        try {
            Cache cosmosCache = getCosmosCache();
            if (cosmosCache == null) {
                logger.error("no cache configuration");
                 return Collections.emptyList();
            }
            List<TCmsInfo> list = new ArrayList<TCmsInfo>();
            List allKeysList = cosmosCache.getKeys();
              if(allKeysList==null || allKeysList.isEmpty()){
                logger.error("the allKeysList is null");
                return Collections.emptyList();
            }
            for (Object key : allKeysList) {
                int skey = ((String) key).indexOf(CacheKeysUtil.cmsInfoKey);
                if (skey >= 0) {
                    Element e = cosmosCache.get(key);
                    if (e == null) {
                        logger.error("get tcmsinfo list from ehcache error");
                         return Collections.emptyList();
                    }
                    TCmsInfo ci = (TCmsInfo) e.getObjectValue();
                    list.add(ci);
                }
            }
            return list;
        } catch (Exception e) {
            logger.error("select cms info from Ehcache error", e);
             return Collections.emptyList();
        }
    }

}
