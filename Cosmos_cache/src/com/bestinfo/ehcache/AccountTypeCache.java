package com.bestinfo.ehcache;

import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.bean.encoding.AccountType;
import com.bestinfo.dao.encoding.IAccountType;
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
 * 账户扣款类型缓存操作类
 *
 * @author user
 */
@Repository
@EhcacheInit(name = "AccountTypeCache")
public class AccountTypeCache extends GetCacheCommon {

    Logger logger = Logger.getLogger(AccountTypeCache.class);

    @Resource
    private IAccountType accountType;

    /**
     * 查询数据库中账户类型数据列表 并放入缓存中。
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
            List<AccountType> la = accountType.selectAccountType(jdbcTemplate);
            if (la == null || la.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (AccountType at : la) {
                String key = CacheKeysUtil.getAccountTypeKey(at.getAccount_type_id());
                Element element = new Element(key, at);
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
     * 查询数据库中账户类型数据列表 并放入缓存中。
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3
     */
    public int addAccountTypeToCache(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();   //获取缓存
        if (cosmosCache == null) {
            logger.error("no cache can configuration");
            return -3;
        }
        try {
            List<AccountType> la = accountType.selectAccountType(jdbcTemplate);
            if (la == null || la.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (AccountType at : la) {
                String key = CacheKeysUtil.getAccountTypeKey(at.getAccount_type_id());
                Element element = new Element(key, at);
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
     * @param accountTypeId 账户类型编号
     * @return 账户类型
     */
    public AccountType getAccountTypeById(int accountTypeId) {
        String key = CacheKeysUtil.getAccountTypeKey(accountTypeId);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get accounttype from ehcache error");
                return null;
            }
            return (AccountType) e.getObjectValue();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 更新缓存中某Key所对应的数据
     *
     * @param jdbcTemplate
     * @param at
     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int updateAccountTypeById(JdbcTemplate jdbcTemplate, AccountType at) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            //首先更新数据库
            int re = accountType.updateAccountType(jdbcTemplate, at);
            if (re < 0) {
                logger.error("update kdrawProStatus db error");
                return -1;
            }
            String key = CacheKeysUtil.getAccountTypeKey(at.getAccount_type_id());
            Element e = new Element(key, at);
            //更新缓存
            cosmosCache.put(e);
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 查询缓存中的账户类型的所有数据
     *
     * @return 账户类型数据集合
     */
    public List<AccountType> getAccountTypeList() {
        try {
            Cache cosmosCache = getCosmosCache();
            if (cosmosCache == null) {
                logger.error("no cache configuration");
                  return Collections.emptyList();
            }
            List<AccountType> list = new ArrayList<AccountType>();
            List allKeysList = cosmosCache.getKeys();
            if(allKeysList==null || allKeysList.isEmpty()){
                logger.error("the allKeysList is null");
                 return Collections.emptyList();
            }
            for (Object key : allKeysList) {
                int skey = ((String) key).indexOf(CacheKeysUtil.accountInfoKey);
                if (skey >= 0) {
                    Element e = cosmosCache.get(key);
                    if (e == null) {
                        logger.error("get accounttype list from ehcache error");
                          return Collections.emptyList();
                    }
                    AccountType at = (AccountType) e.getObjectValue();
                    list.add(at);
                }
            }
            return list;
        } catch (Exception e) {
            logger.error("", e);
            return Collections.emptyList();
        }
    }
}
