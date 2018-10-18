package com.bestinfo.ehcache;

import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.bean.encoding.DrawProcessStatus;
import com.bestinfo.dao.encoding.IDrawProStatus;
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
 * 游戏期进度状态缓存操作类
 *
 * @author hhhh
 */
@Repository
@EhcacheInit(name = "DrawProStatusCache")
public class DrawProStatusCache extends GetCacheCommon {

    Logger logger = Logger.getLogger(DrawProStatusCache.class);

    @Resource
    private IDrawProStatus drawProStatus;

    /**
     * 查询期进度状态数据，并全部放入缓存
     *
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
            List<DrawProcessStatus> ld = drawProStatus.selectDrawProStatus(jdbcTemplate);
            if (ld == null || ld.isEmpty()) {
                logger.error("there is no data in db");
                return -1;
            }

            for (DrawProcessStatus d : ld) {
                String key = CacheKeysUtil.getDrawProStatusKey(d.getProcess_status());
                Element e = new Element(key, d);
                cosmosCache.put(e);
            }
            logger.info("放入缓存的数据条数：" + ld.size());
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 查询期进度状态数据，并全部放入缓存
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int addDrawProstatusToCache(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            List<DrawProcessStatus> ld = drawProStatus.selectDrawProStatus(jdbcTemplate);
            if (ld == null || ld.isEmpty()) {
                logger.error("there is no data in db");
                return -1;
            }

            for (DrawProcessStatus d : ld) {
                String key = CacheKeysUtil.getDrawProStatusKey(d.getProcess_status());
                Element e = new Element(key, d);
                cosmosCache.put(e);
            }
            logger.info("放入缓存的数据条数：" + ld.size());
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 根据Key查询对应的缓存数据对象
     *
     * @param proStatus 进度编号
     * @return 期进度状态
     */
    public DrawProcessStatus getDrawProstatusByid(int proStatus) {
        String key = CacheKeysUtil.getDrawProStatusKey(proStatus);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get drawprocessstatus list from ehcache error");
                return null;
            }
            return (DrawProcessStatus) e.getObjectValue();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 更新缓存中某Key所对应的数据
     *
     * @param jdbcTemplate
     * @param dp
     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int updateDrawProstatusByid(JdbcTemplate jdbcTemplate, DrawProcessStatus dp) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            //首先更新数据库
            int re = drawProStatus.updateDrawProStatus(jdbcTemplate, dp);
            if (re < 0) {
                logger.error("update drawProStatus db error");
                return -1;
            }
            String key = CacheKeysUtil.getDrawProStatusKey(dp.getProcess_status());
            Element e = new Element(key, dp);
            //更新缓存
            cosmosCache.put(e);
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

}
