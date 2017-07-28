package com.bestinfo.ehcache;

import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.bean.encoding.BankcardType;
import com.bestinfo.dao.encoding.IBankcardType;
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
 * 银行卡类型缓存操作类
 *
 * @author user
 */
@Repository
@EhcacheInit(name = "BankcardTypeCache")
public class BankcardTypeCache extends GetCacheCommon {

    Logger logger = Logger.getLogger(BankcardTypeCache.class);

    @Resource
    private IBankcardType bankcardType;

    /**
     * 查询数据库中银行卡类型数据列表 并放入缓存中。
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
            List<BankcardType> lb = bankcardType.selectBankcardType(jdbcTemplate);
            if (lb == null || lb.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (BankcardType bt : lb) {
                String key = CacheKeysUtil.getBankcardTypeKey(bt.getCard_type());
                Element element = new Element(key, bt);
                cosmosCache.put(element);
            }
            logger.info("放入缓存的数据条数：" + lb.size());
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -2;
        }
    }

    /**
     * 查询数据库中银行卡类型数据列表 并放入缓存中。
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3
     */
    public int addBankcardTypeToCache(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();   //获取缓存
        if (cosmosCache == null) {
            logger.error("no cache can configuration");
            return -3;
        }
        try {
            List<BankcardType> lb = bankcardType.selectBankcardType(jdbcTemplate);
            if (lb == null || lb.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (BankcardType bt : lb) {
                String key = CacheKeysUtil.getBankcardTypeKey(bt.getCard_type());
                Element element = new Element(key, bt);
                cosmosCache.put(element);
            }
            logger.info("放入缓存的数据条数：" + lb.size());
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -2;
        }
    }

    /**
     * 根据Key查询对应的缓存数据对象
     *
     * @param cardType 银行卡类型类型编号
     * @return 银行卡类型
     */
    public BankcardType getBankcardTypeById(int cardType) {
        String key = CacheKeysUtil.getBankcardTypeKey(cardType);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get bankcardtype from ehcache error");
                return null;
            }
            return (BankcardType) e.getObjectValue();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 更新缓存中某Key所对应的数据
     *
     * @param jdbcTemplate
     * @param bt
     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int updateBankcardTypeById(JdbcTemplate jdbcTemplate, BankcardType bt) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            //首先更新数据库
            int re = bankcardType.updateBankcardType(jdbcTemplate, bt);
            if (re < 0) {
                logger.error("update kdrawProStatus db error");
                return -1;
            }
            String key = CacheKeysUtil.getBankcardTypeKey(bt.getCard_type());
            Element e = new Element(key, bt);
            //更新缓存
            cosmosCache.put(e);
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

}
