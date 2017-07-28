package com.bestinfo.ehcache.business;

import com.bestinfo.bean.business.DealerPrivilege;
import com.bestinfo.dao.business.IDealerPrivilegeDao;
import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.ehcache.annotation.EhcacheInit;
import com.bestinfo.cache.keys.CacheKeysUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 代销商游戏特权
 *
 * @author user
 */
@Repository
@EhcacheInit(name = "DealerPrivilegeCache")
public class DealerPrivilegeCache extends GetCacheCommon {

    Logger logger = Logger.getLogger(DealerPrivilegeCache.class);

    @Resource
    private IDealerPrivilegeDao dealerPrivilegeDao;

    /**
     * 查询数据库中代销商游戏特权数据列表 并放入缓存中。
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    @Override
    public int init(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();   //获取缓存
        if (cosmosCache == null) {
            logger.error("no cache can configuration");
            return -3;
        }
        try {
            List<DealerPrivilege> ld = dealerPrivilegeDao.selectDealerPrivilege(jdbcTemplate);
            if (ld.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (DealerPrivilege dp : ld) {
                String key = CacheKeysUtil.getDealerPrivilegeKey(dp.getDealer_id(), dp.getGame_id());
                Element element = new Element(key, dp);
                cosmosCache.put(element);
            }
            logger.info("放入缓存的数据条数：" + ld.size());
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -2;
        }
    }

    /**
     * 增加指定代销商游戏特权信息到缓存中
     *
     * @param ldp 代销商游戏特权信息
     * @return 成功（0）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int addDealerPrivilegeToCache(List<DealerPrivilege> ldp) {
        Cache cosmosCache = getCosmosCache();   //获取缓存
        if (cosmosCache == null) {
            logger.error("no cache can configuration");
            return -3;
        }
        try {
            for (DealerPrivilege dp : ldp) {
                String key = CacheKeysUtil.getDealerPrivilegeKey(dp.getDealer_id(), dp.getGame_id());
                Element element = new Element(key, dp);
                cosmosCache.put(element);
            }
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -2;
        }
    }

    /**
     * 根据代销商编号查询代销商的游戏特权
     *
     * @param dealerId 代销商编号
     * @return 代销商游戏特权 出错时返回null
     */
    public List<DealerPrivilege> getDealerPrivilegeById(String dealerId) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        String dekey = CacheKeysUtil.dealerPrivilege + dealerId;
        try {
            List<DealerPrivilege> list = new ArrayList<DealerPrivilege>();
            List allKeysList = cosmosCache.getKeys();
            for (Object key : allKeysList) {
                int skey = ((String) key).indexOf(dekey);
                if (skey >= 0) {
                    Element e = cosmosCache.get(key);
                    if (e == null) {
                        logger.error("get dealerprivilege from ehcache error");
                        return null;
                    }
                    DealerPrivilege dt = (DealerPrivilege) e.getObjectValue();
                    list.add(dt);
                }
            }
            return list;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    public DealerPrivilege getDealerPrivilegeByGameId(String dealerId, int gameid) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        String dekey = CacheKeysUtil.getDealerPrivilegeKey(dealerId, gameid);
        try {
            Element e = cosmosCache.get(dekey);
            if (e == null) {
                logger.error("get dealerprivilege from ehcache error");
                return null;
            }
            DealerPrivilege dt = (DealerPrivilege) e.getObjectValue();
            return dt;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 更新代销商游戏特权，更新数据库和缓存
     *
     * @param jdbcTemplate
     * @param dp
     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int updateDealerPrivilegeById(JdbcTemplate jdbcTemplate, DealerPrivilege dp) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            //首先更新数据库
            int re = dealerPrivilegeDao.updateDealerPrivilege(jdbcTemplate, dp);
            if (re < 0) {
                logger.error("update kdrawProStatus db error");
                return -1;
            }
            String key = CacheKeysUtil.getDealerPrivilegeKey(dp.getDealer_id(), dp.getGame_id());
            Element e = new Element(key, dp);
            //更新缓存
            cosmosCache.put(e);
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 删除指定代销商下的所有游戏特权的缓存数据
     *
     * @param dealerId
     * @return 0（成功） -1（未知错误） -2（未配置缓存空间）
     */
    public int deleteDealerPrivilegeCacheByDealerId(String dealerId) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -2;
        }
        String dekey = CacheKeysUtil.dealerPrivilege + dealerId;
        try {
            List allKeysList = cosmosCache.getKeys();
            for (Object key : allKeysList) {
                int skey = ((String) key).indexOf(dekey);
                if (skey >= 0) {
                    cosmosCache.remove(key);
                }
            }
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -1;
        }
    }

}
