package com.bestinfo.ehcache;

import com.bestinfo.arithmetic.CaTool;
import com.bestinfo.bean.business.DealerInfo;
import com.bestinfo.cache.keys.CacheKeysUtil;
import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.dao.business.IDealerInfoDao;
import com.bestinfo.define.filepath.FilePath;
import com.bestinfo.ehcache.annotation.EhcacheInit;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;
import javax.annotation.Resource;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
/**
 * 代理商证书缓存操作类
 */
@Repository
@EhcacheInit(name = "GamblerPublicKey")
public class GamblerKey extends GetCacheCommon {

    private static final Logger logger = Logger.getLogger(GamblerKey.class);

    @Resource
    private IDealerInfoDao dealerInfoDao;

    @Override
    public int init(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();   //获取缓存
        if (cosmosCache == null) {
            logger.error("no cache can configuration");
            return -3;
        }
        try {
            List<DealerInfo> lb = dealerInfoDao.getAllDealerInfo(jdbcTemplate);
            if (lb.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            CaTool ct = new CaTool();
            for (DealerInfo bc : lb) {
                String key = CacheKeysUtil.getGamblerKeyName(bc.getDealer_id());
                String Path = FilePath.getPublickeypath(Integer.parseInt(bc.getDealer_id()));
                PublicKey pk = ct.getPublicKey(Path);
                Element element = new Element(key, pk);
                cosmosCache.put(element);
            }
            logger.info("public放入缓存的数据条数：" + lb.size());

            String pKey = CacheKeysUtil.getPrivateKeyName();
            String pPath = FilePath.getStorePath();
            String pPWD = FilePath.getStorepwd();
            String pAlias = FilePath.getAlias();
            String pAliasPWD = FilePath.getAliaspwd();
            PrivateKey prk = ct.getPrivateKey(
                    pPath,
                    pPWD,
                    pAlias,
                    pAliasPWD);
            Element ele = new Element(pKey, prk);
            cosmosCache.put(ele);

            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -2;
        }
    }

    /**
     * 根据Key查询对应的缓存数据对象
     *
     * @param dealerid
     * @return 银行编码
     */
    public PublicKey getPublicKeyByDealerId(String dealerid) {
        String key = CacheKeysUtil.getGamblerKeyName(dealerid);
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
            return (PublicKey) e.getObjectValue();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    public PrivateKey getCompanyPrivateKey() {
        String key = CacheKeysUtil.getPrivateKeyName();
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
            return (PrivateKey) e.getObjectValue();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

}
