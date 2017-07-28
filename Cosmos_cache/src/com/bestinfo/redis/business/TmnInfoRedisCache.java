package com.bestinfo.redis.business;

import com.alibaba.fastjson.JSON;
import com.bestinfo.bean.business.TmnInfo;
import com.bestinfo.cache.keys.RedisKeysUtil;
import com.bestinfo.dao.business.ITmnInfoDao;
import com.bestinfo.define.returncode.TerminalResultCode;
import com.bestinfo.redis.dao.impl.RedisDaoImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 终端注册
 *
 * @author hhhh
 */
@Repository
public class TmnInfoRedisCache {

    @Resource
    private RedisDaoImpl redisDaoImpl;

    @Resource
    private ITmnInfoDao tmnInfoDao;

    @Resource
    private JdbcTemplate termJdbcTemplate;

    private static final Logger logger = Logger.getLogger(TmnInfoRedisCache.class);

    /**
     * 往缓存初始化终端信息数据
     *
     * @return
     */
    public int init() {
        try {
            List<TmnInfo> lt = tmnInfoDao.selectTmnInfo(termJdbcTemplate);
            if (lt == null || lt.isEmpty()) {
                logger.error(" there is no data in db");
                return -1;
            }
            boolean flag = false;
            for (TmnInfo ti : lt) {
                int re = addTmnInfoIntoCache(ti);
                int rt = addPhyTmnInfoIntoCache(ti);
                if (re < 0 || rt < 0) {
                    logger.error("tmn info into redis error:" + re + " terminalId: " + ti.getTerminal_id());
                    flag = true;
                    break;
                }
            }

            if (flag) {
                logger.error("tmn info into redis fail.");
                return -3;
            }

            logger.info("放入缓存的数据条数：" + lt.size());
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -4;
        }
    }

    /**
     * 往缓存中添加终端信息
     *
     * @param tmnInfo
     * @return 0-成功 -1（tmnInfo失败） -2（失败）
     */
    public int addTmnInfoIntoCache(TmnInfo tmnInfo) {
        String tmnInfoRedisKey = RedisKeysUtil.getTmnInfoKey(tmnInfo.getTerminal_id());
        try {
            boolean re = redisDaoImpl.redisSet(tmnInfoRedisKey, JSON.toJSONString(tmnInfo));
            if (!re) {
                logger.error("tmn info into redis error, tmnInfoRedisKey:" + tmnInfoRedisKey + "," + re);
                return -TerminalResultCode.tmnForRedisUpdateFail;
            }
            return TerminalResultCode.success;
        } catch (Exception ex) {
            logger.error("", ex);
            return -TerminalResultCode.cacheExceError;
        }
    }

    /**
     * 根据终端编号从Redis缓存中取出对应终端信息
     *
     * @param terminalId
     * @return
     */
    public TmnInfo getTmnInfoById(int terminalId) {
        try {
            String tmnInfokey = RedisKeysUtil.getTmnInfoKey(terminalId);
            String tmnInfo = redisDaoImpl.redisLoad(tmnInfokey);
            if (tmnInfo == null || tmnInfo.trim().length() == 0) {
                logger.warn("there is no tmn info in redis tmnInfokey:" + tmnInfokey);
                return null;
            }
            TmnInfo ti = new ObjectMapper().readValue(tmnInfo, TmnInfo.class);
            return ti;
        } catch (Exception e) {
            logger.error(java.lang.Thread.currentThread().getId() + "\t" + "get tmn info from Redis failed where terminalId=" + terminalId, e);
            return null;
        }
    }

    /**
     * 将终端列表放入缓存（可用于修改覆盖）
     *
     * @param tiList
     * @return
     */
    public int batchAddTmnList(List<TmnInfo> tiList) {
        boolean flag = false;
        for (TmnInfo ti : tiList) {
            int re = addTmnInfoIntoCache(ti);
            if (re < 0) {
                logger.error("tmn info into redis error:" + re);
                flag = true;
                break;
            }
        }

        if (flag) {
            logger.error("tmn info list into redis error.");
            return -1;
        }
        return 0;
    }

    /**
     * 从Redis中查询指定终端号的终端列表
     *
     * @param terminal_id_str
     * @return
     */
    public List<TmnInfo> getTmnListFromRedisBySpecificId(String terminal_id_str) {
        List<TmnInfo> list = new ArrayList<TmnInfo>();
        String[] arr = terminal_id_str.split(",");
        boolean flag = false;
        for (String tmnId : arr) {
            int terminalId = Integer.parseInt(tmnId);
            TmnInfo ti = getTmnInfoById(terminalId);
            if (ti == null) {
                flag = true;
                logger.error("get tmn info from Redis failed where terminalId=" + terminalId);
                break;
            }
            list.add(ti);
        }

        if (flag) {
            return null;
        }
        return list;
    }

    /**
     * 从Redis中查询指定终端号的终端列表，并且只返回可封机的终端
     *
     * @param terminal_id_str
     * @param tmnStatus
     * @return
     */
    public List<TmnInfo> getTmnListFromRedisBySpecificIdStatus(String terminal_id_str, int tmnStatus) {
        List<TmnInfo> list = new ArrayList<TmnInfo>();
        String[] arr = terminal_id_str.split(",");
        boolean flag = false;
        for (String tmnId : arr) {
            int terminalId = Integer.parseInt(tmnId);
            TmnInfo ti = getTmnInfoById(terminalId);
            if (ti == null) {
                flag = true;
                logger.error("get tmn info from Redis failed where terminalId=" + terminalId);
                break;
            }
            if (ti.getTerminal_status() == tmnStatus) {
                //若为撤销状态，则不可封机
                logger.info("this tmn has already been repealed.");
                continue;
            }
            list.add(ti);
        }

        if (flag) {
            return Collections.emptyList();
        }
        return list;
    }

    /**
     * 根据物理终端编号从Redis缓存中取出对应终端信息
     *
     * @param terminalphyId
     * @return
     */
    public TmnInfo getPhyTmnInfoById(int terminalphyId) {
        try {
            String tmnInfokey = RedisKeysUtil.getPhyTmnInfokey(terminalphyId);
            String tmnInfo = redisDaoImpl.redisLoad(tmnInfokey);
            if (tmnInfo == null || tmnInfo.trim().length() == 0) {
                logger.warn("there is no tmn info in redis,tmnInfokey:" + tmnInfokey);
                return null;
            }
            TmnInfo ti = new ObjectMapper().readValue(tmnInfo, TmnInfo.class);
            return ti;
        } catch (Exception e) {
            logger.error("get tmn info from redis error,terminalId:" + terminalphyId, e);
            return null;
        }
    }

    /**
     * 往缓存中添加物理终端信息
     *
     * @param tmnInfo
     * @return
     */
    public int addPhyTmnInfoIntoCache(TmnInfo tmnInfo) {
        String tmnInfoRedisKey = RedisKeysUtil.getPhyTmnInfokey(tmnInfo.getTerminal_phy_id());
        try {
            boolean re = redisDaoImpl.redisSet(tmnInfoRedisKey, JSON.toJSONString(tmnInfo));
            if (!re) {
                logger.error("insert tmn info into redis error,key:" + tmnInfoRedisKey + ",jsonObject:" + JSON.toJSONString(tmnInfo));
                return -TerminalResultCode.tmnForRedisUpdateFail;
            }
            return TerminalResultCode.success;
        } catch (Exception ex) {
            logger.error("", ex);
            return -TerminalResultCode.cacheExceError;
        }
    }
}
