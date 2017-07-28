package com.bestinfo.ehcache;

import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.bean.business.CmsPrivilege;
import com.bestinfo.dao.business.ICmsPrivilegeDao;
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
 * 内容管理缓存操作类
 *
 * @author user
 */
@Repository
@EhcacheInit(name = "CmsPrivilegeCache")
public class CmsPrivilegeCache extends GetCacheCommon {

    Logger logger = Logger.getLogger(CmsPrivilegeCache.class);
    @Resource
    private ICmsPrivilegeDao cmsPrivilegeDao;

    /**
     * 查询数据库中内容发布权限数据列表 并放入缓存中。
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
            //added by yfyang 20170329,不需要加载通知特权到ehcache
//            List<CmsPrivilege> lc = cmsPrivilegeDao.selectCmsPrivilege(jdbcTemplate);
//            if (lc == null || lc.isEmpty()) {
//                logger.error(" there is no data in db");
//                return -1;
//            }
//            for (CmsPrivilege cp : lc) {
//                String key = CacheKeysUtil.getCmsPrivilegeKey(cp.getCms_id());
//                Element element = new Element(key, cp);
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
     * 查询数据库中内容发布权限数据列表 并放入缓存中。
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3
     */
    public int addCmsPrivilegeToCache(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();   //获取缓存
        if (cosmosCache == null) {
            logger.error("no cache can configuration");
            return -3;
        }
        try {
            List<CmsPrivilege> lc = cmsPrivilegeDao.selectCmsPrivilege(jdbcTemplate);
            if (lc == null || lc.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (CmsPrivilege cp : lc) {
                String key = CacheKeysUtil.getCmsPrivilegeKey(cp.getCms_id());
                Element element = new Element(key, cp);
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
     * @param cmsId 内容发布权限编号
     * @return 内容发布权限
     */
    public CmsPrivilege getCmsPrivilegeById(int cmsId) {
        String key = CacheKeysUtil.getCmsPrivilegeKey(cmsId);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get cmsprivilege from ehcache error");
                return null;
            }
            return (CmsPrivilege) e.getObjectValue();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 更新缓存中某Key所对应的数据
     *
     * @param jdbcTemplate
     * @param cp
     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int updateCmsPrivilegeById(JdbcTemplate jdbcTemplate, CmsPrivilege cp) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            //首先更新数据库
            int re = cmsPrivilegeDao.updateCmsPrivilege(jdbcTemplate, cp);
            if (re < 0) {
                logger.error("update kdrawProStatus db error");
                return -1;
            }
            String key = CacheKeysUtil.getCmsPrivilegeKey(cp.getCms_id());
            Element e = new Element(key, cp);
            //更新缓存
            cosmosCache.put(e);
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

}
