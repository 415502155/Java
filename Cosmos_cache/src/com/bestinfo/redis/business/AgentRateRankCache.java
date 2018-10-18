package com.bestinfo.redis.business;

import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.bean.terminal.AgentRateRank;
import com.bestinfo.ehcache.annotation.EhcacheInit;
import com.bestinfo.cache.keys.CacheKeysUtil;
import com.bestinfo.dao.terminal.IAgentRateRankDao;
import java.util.List;
import javax.annotation.Resource;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 站点等级缓存操作类
 *
 * @author hhhh
 */
@Repository
@EhcacheInit(name = "AgentRateRankCache")
public class AgentRateRankCache extends GetCacheCommon {

    Logger logger = Logger.getLogger(AgentRateRankCache.class);

    @Resource
    private IAgentRateRankDao agentRateRankDao;

    /**
     * 查询站点等级数据，并全部放入缓存
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    @Override
    public int init(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            List<AgentRateRank> arrList = agentRateRankDao.getAgentRateRankList(jdbcTemplate);
            if (arrList == null || arrList.isEmpty()) {
                logger.error("there is no data in db");
                return -1;
            }

            for (AgentRateRank arr : arrList) {
                String key = CacheKeysUtil.getAgentRateRankKey(arr.getStation_rank());
                Element e = new Element(key, arr);
                cosmosCache.put(e);
            }
            logger.info("agent rate rank size:" + arrList.size());
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 根据Key查询对应的缓存数据对象
     *
     * @param stationRank 站点等级编号
     * @return 站点等级
     */
    public AgentRateRank getAgentRateRankByid(int stationRank) {
        String key = CacheKeysUtil.getAgentRateRankKey(stationRank);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get agent rate rank from ehcache error");
                return null;
            }
            return (AgentRateRank) e.getObjectValue();
        } catch (IllegalStateException | CacheException e) {
            logger.error("", e);
            return null;
        }
    }

}
