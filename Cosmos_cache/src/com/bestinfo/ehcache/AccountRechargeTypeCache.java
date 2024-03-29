package com.bestinfo.ehcache;

import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.bean.encoding.AccountRechargeType;
import com.bestinfo.dao.encoding.IAccountRechargeType;
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
 * 账户充值方式缓存操作类
 *
 * @author user
 */
@Repository
@EhcacheInit(name = "AccountRechargeTypeCache")
public class AccountRechargeTypeCache extends GetCacheCommon {

    private static final Logger logger = Logger.getLogger(AccountRechargeTypeCache.class);

    @Resource
    private IAccountRechargeType accountRechargeType;

    /**
     * 查询数据库中账户充值方式数据列表 并放入缓存中。
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
            List<AccountRechargeType> la = accountRechargeType.selectAccountRechargeType(jdbcTemplate);
            if (la == null || la.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (AccountRechargeType ar : la) {
                String key = CacheKeysUtil.getAccountRechargeTypeKey(ar.getRecharge_type());
                Element element = new Element(key, ar);
                cosmosCache.put(element);
            }
            logger.info("放入缓存的数据条数：" + la.size());
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -2;
        }
    }

    /**
     * 查询数据库中账户充值方式数据列表 并放入缓存中。
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3
     */
    public int addAccountRechargeTypeToCache(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();   //获取缓存
        if (cosmosCache == null) {
            logger.error("no cache can configuration");
            return -3;
        }
        try {
            List<AccountRechargeType> la = accountRechargeType.selectAccountRechargeType(jdbcTemplate);
            if (la == null || la.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (AccountRechargeType ar : la) {
                String key = CacheKeysUtil.getAccountRechargeTypeKey(ar.getRecharge_type());
                Element element = new Element(key, ar);
                cosmosCache.put(element);
            }
            logger.info("放入缓存的数据条数：" + la.size());
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -2;
        }
    }

    /**
     * 根据Key查询对应的缓存数据对象
     *
     * @param rechargeType 账户充值方式编号
     * @return 账户充值方式
     */
    public AccountRechargeType getAccountRechargeTypeById(int rechargeType) {
        String key = CacheKeysUtil.getAccountRechargeTypeKey(rechargeType);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get AccountRechargeType from ehcache error");
                return null;
            }
            return (AccountRechargeType) e.getObjectValue();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 更新缓存中某Key所对应的数据
     *
     * @param jdbcTemplate
     * @param ar
     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int updateItTypeById(JdbcTemplate jdbcTemplate, AccountRechargeType ar) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            //首先更新数据库
            int re = accountRechargeType.updateAccountRechargeType(jdbcTemplate, ar);
            if (re < 0) {
                logger.error("update kdrawProStatus db error");
                return -1;
            }
            String key = CacheKeysUtil.getAccountRechargeTypeKey(ar.getRecharge_type());
            Element e = new Element(key, ar);
            //更新缓存
            cosmosCache.put(e);
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

}
