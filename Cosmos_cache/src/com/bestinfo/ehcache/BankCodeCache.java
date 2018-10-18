package com.bestinfo.ehcache;

import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.bean.encoding.BankCode;
import com.bestinfo.dao.encoding.IBankCode;
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
 * 银行编码缓存操作类
 *
 * @author user
 */
@Repository
@EhcacheInit(name = "BankCodeCache")
public class BankCodeCache extends GetCacheCommon {

    Logger logger = Logger.getLogger(BankCodeCache.class);

    @Resource
    private IBankCode bankCode;

    /**
     * 查询数据库中银行编码数据列表 并放入缓存中。
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
            List<BankCode> lb = bankCode.selectBankCode(jdbcTemplate);
            if (lb == null || lb.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (BankCode bc : lb) {
                String key = CacheKeysUtil.getBankCodeKey(bc.getBank_id());
                Element element = new Element(key, bc);
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
     * 查询数据库中银行编码数据列表 并放入缓存中。
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3
     */
    public int addBankCodeToCache(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();   //获取缓存
        if (cosmosCache == null) {
            logger.error("no cache can configuration");
            return -3;
        }
        try {
            List<BankCode> lb = bankCode.selectBankCode(jdbcTemplate);
            if (lb == null || lb.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            for (BankCode bc : lb) {
                String key = CacheKeysUtil.getBankCodeKey(bc.getBank_id());
                Element element = new Element(key, bc);
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
     * @param bankId 银行编码编号
     * @return 银行编码
     */
    public BankCode getBankCodeById(String bankId) {
        String key = CacheKeysUtil.getBankCodeKey(bankId);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get bankcode from ehcache error");
                return null;
            }
            return (BankCode) e.getObjectValue();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 更新缓存中某Key所对应的数据
     *
     * @param jdbcTemplate
     * @param bc
     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int updateBankCodeById(JdbcTemplate jdbcTemplate, BankCode bc) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            //首先更新数据库
            int re = bankCode.updateBankCode(jdbcTemplate, bc);
            if (re < 0) {
                logger.error("update kdrawProStatus db error");
                return -1;
            }
            String key = CacheKeysUtil.getBankCodeKey(bc.getBank_id());
            Element e = new Element(key, bc);
            //更新缓存
            cosmosCache.put(e);
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 查询缓存中的银行编号的所有数据
     *
     * @return 银行编号数据集合
     */
    public List<BankCode> getBankCodeList() {
        try {
            Cache cosmosCache = getCosmosCache();
            if (cosmosCache == null) {
                logger.error("no cache configuration");
                return Collections.emptyList();
            }
            List<BankCode> list = new ArrayList<BankCode>();
            List allKeysList = cosmosCache.getKeys();
            if (allKeysList == null || allKeysList.isEmpty()) {
                logger.error("the allKeysList is null");
                return Collections.emptyList();
            }
            for (Object key : allKeysList) {
                int skey = ((String) key).indexOf(CacheKeysUtil.bankCodeKey);
                if (skey >= 0) {
                    Element e = cosmosCache.get(key);
                    if (e == null) {
                        logger.error("get bankcode list from ehcache error");
                        return Collections.emptyList();
                    }
                    BankCode bc = (BankCode) e.getObjectValue();
                    list.add(bc);
                }
            }
            return list;
        } catch (Exception e) {
            logger.error("", e);
            return Collections.emptyList();
        }
    }
}
