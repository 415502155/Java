package com.bestinfo.redis.monitor;

import com.bestinfo.cache.keys.RedisKeysUtil;
import com.bestinfo.redis.dao.impl.RedisDaoImpl;
import com.bestinfo.util.TimeUtil;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 *
 * 终端机监控相关
 */
@Repository
public class TerminalMonitorRedis {

    @Resource
    private RedisDaoImpl redisDaoImpl;

    private static final Logger logger = Logger.getLogger(TerminalMonitorRedis.class);

    /**
     * redis保存某天某个终端登录时间<br>
     * 用hash的方式存储，key为当天时间，name为终端id，value为终端登录时间<br>
     * hash方式，可以查询登录终端数，及每个终端登录时间
     *
     * @param date
     * @param terminalId
     * @return 0-成功 -1（失败） -2（失败）
     */
    public int saveOneTmnLoginTime(Date date, String terminalId) {
        try {
            String key = RedisKeysUtil.getTerminalLoginKey(TimeUtil.formatDate_YMD8(date));
            String value = TimeUtil.formatDate_time(date);
            logger.info("save terminal login info to redis,key---" + key + ",terminalId---" + terminalId + ",time---" + value);
            boolean re = redisDaoImpl.redisHashSet(key, terminalId, value);
            if (!re) {
                logger.error("redis save error");
                return -1;
            }
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -2;
        }
    }

    /**
     * redis获取某天某个终端登录时间<br>
     * 用hash的方式存储，key为当天时间，name为终端id，value为终端登录时间<br>
     * hash方式，可以查询登录终端数，及每个终端登录时间
     *
     * @param date
     * @param terminalId
     * @return
     */
    public String getOneTmnLoginTime(Date date, String terminalId) {
        try {
            String key = RedisKeysUtil.getTerminalLoginKey(TimeUtil.formatDate_YMD8(date));
            return redisDaoImpl.redisHashGet(key, terminalId);
        } catch (Exception ex) {
            logger.error("", ex);
            return StringUtils.EMPTY;
        }
    }

    /**
     * redis获取某天终端登录数量<br>
     * 用hash的方式存储，key为当天时间，name为终端id，value为终端登录时间<br>
     * hash方式，可以查询登录终端数，及每个终端登录时间
     *
     * @param date
     * @return
     */
    public long getTmnLoginNumber(Date date) {
        try {
            String key = RedisKeysUtil.getTerminalLoginKey(TimeUtil.formatDate_YMD8(date));
            return redisDaoImpl.redisHashLen(key);
        } catch (Exception ex) {
            logger.error("", ex);
            return -1;
        }
    }

    /**
     * redis获取某天所有登录的终端id<br>
     * 用hash的方式存储，key为当天时间，name为终端id，value为终端登录时间<br>
     * hash方式，可以查询登录终端数，及每个终端登录时间
     *
     * @param date
     * @return
     */
    public Set<String> getTmnLoginIds(Date date) {
        try {
            String key = RedisKeysUtil.getTerminalLoginKey(TimeUtil.formatDate_YMD8(date));
            return redisDaoImpl.redisHashKeys(key);
        } catch (Exception ex) {
            logger.error("", ex);
            return null;
        }
    }

    /**
     * redis获取某天所有登录的终端id及其登录时间<br>
     * 用hash的方式存储，key为当天时间，name为终端id，value为终端登录时间<br>
     * hash方式，可以查询登录终端数，及每个终端登录时间
     *
     * @param date
     * @return
     */
    public Map<String, String> getTmnLoginIdsAndTime(Date date) {
        try {
            String key = RedisKeysUtil.getTerminalLoginKey(TimeUtil.formatDate_YMD8(date));
            return redisDaoImpl.redisHashGetAll(key);
        } catch (Exception ex) {
            logger.error("", ex);
            return null;
        }
    }

}
