package com.bestinfo.ehcache;

import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.bean.encoding.KDrawProcessStatus;
import com.bestinfo.dao.encoding.IKDrawProStatus;
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
 * 快开游戏期进度状态缓存操作类
 *
 * @author hhhh
 */
@Repository
@EhcacheInit(name = "KDrawProStatusCache")
public class KDrawProStatusCache extends GetCacheCommon {

    Logger logger = Logger.getLogger(KDrawProStatusCache.class);

    @Resource
    private IKDrawProStatus kdrawProStatus;

    /**
     * 查询快开期进度状态数据，并全部放入缓存
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
            List<KDrawProcessStatus> lk = kdrawProStatus.selectKDrawProStatus(jdbcTemplate);
            if (lk == null || lk.isEmpty()) {
                logger.error("there is no data in db");
                return -1;
            }

            for (KDrawProcessStatus kd : lk) {
                String key = CacheKeysUtil.getKDrawProStatusKey(kd.getKeno_process_status());
                Element e = new Element(key, kd);
                cosmosCache.put(e);
            }
            logger.info("放入缓存的数据条数：" + lk.size());
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 查询快开期进度状态数据，并全部放入缓存
     *
     * @param jdbcTemplate
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int addKDrawProstatusToCache(JdbcTemplate jdbcTemplate) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            List<KDrawProcessStatus> lk = kdrawProStatus.selectKDrawProStatus(jdbcTemplate);
            if (lk == null || lk.isEmpty()) {
                logger.error("there is no data in db");
                return -1;
            }

            for (KDrawProcessStatus kd : lk) {
                String key = CacheKeysUtil.getKDrawProStatusKey(kd.getKeno_process_status());
                Element e = new Element(key, kd);
                cosmosCache.put(e);
            }
            logger.info("放入缓存的数据条数：" + lk.size());
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 根据Key查询对应的缓存数据对象
     *
     * @param kenoProStatus 进度编号
     * @return 快开期进度状态
     */
    public KDrawProcessStatus getKDrawProstatusByid(int kenoProStatus) {
        String key = CacheKeysUtil.getKDrawProStatusKey(kenoProStatus);
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return null;
        }
        try {
            Element e = cosmosCache.get(key);
            if (e == null) {
                logger.error("get KDrawProcessStatus from ehcache error");
                return null;
            }
            return (KDrawProcessStatus) e.getObjectValue();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 更新缓存中某Key所对应的数据
     *
     * @param jdbcTemplate
     * @param kp
     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int updateKDrawProstatusByid(JdbcTemplate jdbcTemplate, KDrawProcessStatus kp) {
        Cache cosmosCache = getCosmosCache();
        if (cosmosCache == null) {
            logger.error("no cache configuration");
            return -3;
        }
        try {
            //首先更新数据库
            int re = kdrawProStatus.updateKDrawProStatus(jdbcTemplate, kp);
            if (re < 0) {
                logger.error("update kdrawProStatus db error");
                return -1;
            }
            String key = CacheKeysUtil.getKDrawProStatusKey(kp.getKeno_process_status());
            Element e = new Element(key, kp);
            //更新缓存
            cosmosCache.put(e);
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 根据进度编号，从数据库或从缓存中获取快开游戏期进度状态数据
     *
     * @param kenoProStatus 进度编号
     * @return 快开游戏期进度状态
     */
//    public KDrawProcessStatus getKDrawProStatusCacheById(int kenoProStatus) {
//        KDrawProcessStatus kdrawProcessStatus = null;
//        Element e = cosmosCache.get(CacheKeysUtil.getKDrawProStatusKey(kenoProStatus));
//        if (e != null) {
//            //当缓存中存在该元素的时候
//            kdrawProcessStatus = (KDrawProcessStatus) e.getObjectValue();//得到非序列化的结果
//            logger.info("访问缓存拿取数据");
//            logger.info("缓存里的结果：keno_process_status: " + kdrawProcessStatus.getKeno_process_status() + " kdraw_status_name: " + kdrawProcessStatus.getKdraw_status_name() + " work_status: " + kdrawProcessStatus.getWork_status());
//        } else {
//            //缓存中无此数据时，查询数据库，并把结果放入缓存中
//            String dbSource = "lotteryBaseDB";
//            JdbcTemplate jdbcTemplate = this.findJdbcTemplate(dbSource);
//            kdrawProcessStatus = kdrawProStatus.getKDrawProStatusById(jdbcTemplate, kenoProStatus);
//            cosmosCache.put(new Element(CacheKeysUtil.getKDrawProStatusKey(kenoProStatus), kdrawProcessStatus));
//            logger.info("查询数据库拿取数据");
//            logger.info("数据库的结果：keno_process_status: " + kdrawProcessStatus.getKeno_process_status() + " kdraw_status_name: " + kdrawProcessStatus.getKdraw_status_name() + " work_status: " + kdrawProcessStatus.getWork_status());
//        }
//        return kdrawProcessStatus;
//    }
//
//    public List<KDrawProcessStatus> getKDrawProStatusCacheList() {
//        List<KDrawProcessStatus> list = new ArrayList<KDrawProcessStatus>();
//        return list;
//    }
}
