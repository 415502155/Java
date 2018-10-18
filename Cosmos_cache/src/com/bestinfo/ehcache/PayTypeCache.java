package com.bestinfo.ehcache;

import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.bean.encoding.PayType;
import com.bestinfo.dao.encoding.IPayType;
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
@EhcacheInit(name = "PayTypeCache")
public class PayTypeCache extends GetCacheCommon {

    Logger logger = Logger.getLogger(PayTypeCache.class);
    @Resource
    private IPayType payType;

    /**
     * 查询数据库中账户扣款类型数据列表 并放入缓存中。
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
            List<PayType> lp = payType.selectPayType(jdbcTemplate);
            if (lp == null || lp.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (PayType pt : lp) {
                String key = CacheKeysUtil.getPayTypeKey(pt.getPay_type_id());
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
     * 查询数据库中账户扣款类型数据列表 并放入缓存中。
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3
     */
    public int addPayTypeToCache(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();   //获取缓存
        if (cosmosCache == null) {
            logger.error("no cache can configuration");
            return -3;
        }
        try {
            List<PayType> lp = payType.selectPayType(jdbcTemplate);
            if (lp == null || lp.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (PayType pt : lp) {
                String key = CacheKeysUtil.getPayTypeKey(pt.getPay_type_id());
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
     * @param payTypeId 账户扣款类型编号
     * @return 账户扣款类型
     */
    public PayType getPayTypeById(int payTypeId) {
        String key = CacheKeysUtil.getPayTypeKey(payTypeId);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get PayType from ehcache error");
                return null;
            }
            return (PayType) e.getObjectValue();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 更新缓存中某Key所对应的数据
     *
     * @param jdbcTemplate
     * @param pt
     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int updatePayTypeById(JdbcTemplate jdbcTemplate, PayType pt) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            //首先更新数据库
            int re = payType.updatePayType(jdbcTemplate, pt);
            if (re < 0) {
                logger.error("update kdrawProStatus db error");
                return -1;
            }
            String key = CacheKeysUtil.getPayTypeKey(pt.getPay_type_id());
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
