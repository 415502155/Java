package com.bestinfo.ehcache;

import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.bean.encoding.TerminalSoftware;
import com.bestinfo.dao.encoding.ITerminalSoftware;
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
 * 软件类型缓存操作类
 *
 * @author hhhh
 */
@Repository
@EhcacheInit(name = "TerminalSoftwareCache")
public class TerminalSoftwareCache extends GetCacheCommon {

    Logger logger = Logger.getLogger(TerminalSoftwareCache.class);

    @Resource
    private ITerminalSoftware terminalSoftware;

    /**
     * 查询软件类型数据，并全部放入缓存
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
            List<TerminalSoftware> lts = terminalSoftware.selectTerminalSoftware(jdbcTemplate);
            if (lts == null || lts.isEmpty()) {
                logger.error("there is no data in db");
                return -1;
            }

            for (TerminalSoftware ts : lts) {
                String key = CacheKeysUtil.getTerminalSoftwareKey(ts.getSoftware_id());
                Element e = new Element(key, ts);
                cosmosCache.put(e);
            }
            logger.info("放入缓存的数据条数：" + lts.size());
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 查询软件类型数据，并全部放入缓存
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int addTerminalSoftwareToCache(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            List<TerminalSoftware> lts = terminalSoftware.selectTerminalSoftware(jdbcTemplate);
            if (lts == null || lts.isEmpty()) {
                logger.error("there is no data in db");
                return -1;
            }

            for (TerminalSoftware ts : lts) {
                String key = CacheKeysUtil.getTerminalSoftwareKey(ts.getSoftware_id());
                Element e = new Element(key, ts);
                cosmosCache.put(e);
            }
            logger.info("放入缓存的数据条数：" + lts.size());
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 根据Key查询对应的缓存数据对象
     *
     * @param softwareId 软件类型编号
     * @return 软件类型
     */
    public TerminalSoftware getTerminalSoftwareByid(int softwareId) {
        String key = CacheKeysUtil.getTerminalSoftwareKey(softwareId);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get terminalsoftware from ehcache error");
                return null;
            }
            return (TerminalSoftware) e.getObjectValue();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 更新缓存中某Key所对应的数据
     *
     * @param jdbcTemplate
     * @param ts
     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int updateTerminalSoftwareByid(JdbcTemplate jdbcTemplate, TerminalSoftware ts) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            //首先更新数据库
            int re = terminalSoftware.updateTerminalSoftware(jdbcTemplate, ts);
            if (re < 0) {
                logger.error("update TerminalSoftware db error");
                return -1;
            }
            String key = CacheKeysUtil.getTerminalSoftwareKey(ts.getSoftware_id());
            Element e = new Element(key, ts);
            //更新缓存
            cosmosCache.put(e);
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 查询缓存中的软件类型的所有数据
     *
     * @return 软件类型数据集合
     */
    public List<TerminalSoftware> getTerminalSoftwareList() {
        try {
            Cache cosmosCache = getCosmosCache();
            if (cosmosCache == null) {
                logger.error("no cache configuration");
                return Collections.emptyList();
            }
            List<TerminalSoftware> list = new ArrayList<TerminalSoftware>();
            List allKeysList = cosmosCache.getKeys();
             if(allKeysList==null || allKeysList.isEmpty()){
                logger.error("the allKeysList is null");
                return Collections.emptyList();
            }
            for (Object key : allKeysList) {
                int skey = ((String) key).indexOf(CacheKeysUtil.softwareKey);
                if (skey >= 0) {
                    Element e = cosmosCache.get(key);
                    if (e == null) {
                        logger.error("get TerminalSoftware list from ehcache error");
                        return Collections.emptyList();
                    }
                    TerminalSoftware sw = (TerminalSoftware) e.getObjectValue();
                    list.add(sw);
                }
            }
            return list;
        } catch (Exception e) {
            logger.error("", e);
            return Collections.emptyList();
        }
    }

}
