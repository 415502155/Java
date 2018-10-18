package com.bestinfo.ehcache;

import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.bean.encoding.TradeType;
import com.bestinfo.dao.encoding.ITradeType;
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
 * 账户扣款类型缓存操作类
 *
 * @author user
 */
@Repository
@EhcacheInit(name = "TradeTypeCache")
public class TradeTypeCache extends GetCacheCommon {

    Logger logger = Logger.getLogger(TradeTypeCache.class);
    @Resource
    private ITradeType tradeType;

    /**
     * 查询数据库中资金交易类型数据列表 并放入缓存中。
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
            List<TradeType> lt = tradeType.selectTradeType(jdbcTemplate);
            if (lt == null || lt.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (TradeType tt : lt) {
                String key = CacheKeysUtil.getTradeTypeKey(tt.getTrade_type());
                Element element = new Element(key, tt);
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
     * 查询数据库中资金交易类型数据列表 并放入缓存中。
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3
     */
    public int addTradeTypeToCache(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();   //获取缓存
        if (cosmosCache == null) {
            logger.error("no cache can configuration");
            return -3;
        }
        try {
            List<TradeType> lt = tradeType.selectTradeType(jdbcTemplate);
            if (lt == null || lt.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (TradeType tt : lt) {
                String key = CacheKeysUtil.getTradeTypeKey(tt.getTrade_type());
                Element element = new Element(key, tt);
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
     * @param tradeType 资金交易类型编号
     * @return 资金交易类型
     */
    public TradeType getTradeTypeById(int tradeType) {
        String key = CacheKeysUtil.getTradeTypeKey(tradeType);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get tradetype from ehcache error");
                return null;
            }
            return (TradeType) e.getObjectValue();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 更新缓存中某Key所对应的数据
     *
     * @param jdbcTemplate
     * @param tt
     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int updateTradeTypeById(JdbcTemplate jdbcTemplate, TradeType tt) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            //首先更新数据库
            int re = tradeType.updateTradeType(jdbcTemplate, tt);
            if (re < 0) {
                logger.error("update kdrawProStatus db error");
                return -1;
            }
            String key = CacheKeysUtil.getTradeTypeKey(tt.getTrade_type());
            Element e = new Element(key, tt);
            //更新缓存
            cosmosCache.put(e);
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

}
