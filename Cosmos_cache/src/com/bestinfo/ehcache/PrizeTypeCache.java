package com.bestinfo.ehcache;

import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.bean.encoding.PrizeType;
import com.bestinfo.dao.encoding.IPrizeType;
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
 * 证件类型缓存操作类
 *
 * @author user
 */
@Repository
@EhcacheInit(name = "PrizeTypeCache")
public class PrizeTypeCache extends GetCacheCommon {

    Logger logger = Logger.getLogger(PrizeTypeCache.class);
    @Resource
    private IPrizeType prizeType;

    /**
     * 查询数据库中账户奖金返奖类型数据列表 并放入缓存中。
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
            List<PrizeType> lp = prizeType.selectPrizeType(jdbcTemplate);
            if (lp == null || lp.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (PrizeType pt : lp) {
                String key = CacheKeysUtil.getPrizeTypeKey(pt.getPrize_type());
                Element element = new Element(key, pt);
                cosmosCache.put(element);
            }
            logger.info("放入缓存的数据条数：" + lp.size());
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -2;
        }
    }

    /**
     * 查询数据库中账户奖金返奖类型数据列表 并放入缓存中。
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3
     */
    public int addPrizeTypeToCache(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();   //获取缓存
        if (cosmosCache == null) {
            logger.error("no cache can configuration");
            return -3;
        }
        try {
            List<PrizeType> lp = prizeType.selectPrizeType(jdbcTemplate);
            if (lp == null || lp.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (PrizeType pt : lp) {
                String key = CacheKeysUtil.getPrizeTypeKey(pt.getPrize_type());
                Element element = new Element(key, pt);
                cosmosCache.put(element);
            }
            logger.info("放入缓存的数据条数：" + lp.size());
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
    public PrizeType getPrizeTypeById(int prizeType) {
        String key = CacheKeysUtil.getPrizeTypeKey(prizeType);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get prizetype from ehcache error");
                return null;
            }
            return (PrizeType) e.getObjectValue();
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
    public int updatePrizeTypeById(JdbcTemplate jdbcTemplate, PrizeType pt) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            //首先更新数据库
            int re = prizeType.updatePrizeType(jdbcTemplate, pt);
            if (re < 0) {
                logger.error("update kdrawProStatus db error");
                return -1;
            }
            String key = CacheKeysUtil.getPrizeTypeKey(pt.getPrize_type());
            Element e = new Element(key, pt);
            //更新缓存
            cosmosCache.put(e);
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

}
