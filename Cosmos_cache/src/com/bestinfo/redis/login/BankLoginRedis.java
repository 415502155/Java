package com.bestinfo.redis.login;

import com.bestinfo.bean.session.BankSession;
import com.bestinfo.cache.keys.RedisKeysUtil;
import com.bestinfo.redis.dao.impl.RedisDaoImpl;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author zyk
 */
@Repository
public class BankLoginRedis {

    @Resource
    private RedisDaoImpl redisDaoImpl;

    Logger logger = Logger.getLogger(BankLoginRedis.class);

    /**
     * 根据key获取缓存里的银行session
     *
     * @param key
     * @return
     */
    public BankSession getSession(String key) {
        try {
            String userNameRedisKey = RedisKeysUtil.getBankSessionKey(key);
            if ("".equals(userNameRedisKey) || userNameRedisKey.trim().length() == 0) {
                logger.error("the userNameRedisKey is null where key=" + key);
                return null;
            }
            String session = redisDaoImpl.redisLoad(userNameRedisKey);
            if (session == null || "".equals(session) || session.trim().length() == 0) {
                logger.error("the session is null where userNameRedisKey=" + userNameRedisKey);
                return null;
            }
            return new ObjectMapper().readValue(session, BankSession.class);
        } catch (Exception ex) {
            logger.error("get the bank session error: ", ex);
            return null;
        }
    }

    /**
     * 保存银行session
     *
     * @param key
     * @param session
     * @return
     */
    public int saveSession(String key, BankSession session) {
        String sessionKey = RedisKeysUtil.getBankSessionKey(key);
        try {
            boolean re = redisDaoImpl.redisSet(sessionKey, new ObjectMapper().writeValueAsString(session));
            if (!re) {
                return -1;
            }
            return 0;
        } catch (Exception ex) {
            logger.error("add the bank session key to redis:", ex);
            return -2;
        }
    }

    /**
     * 删除缓存中的用户
     *
     * @param key
     * @return 0:删除成功 -1、-2删除失败
     */
//    public int clearUserName(String key) {
//        String userNameRedisKey = RedisKeysUtil.getBankSessionKey(key);
//        try {
//            logger.info("delete the redis session key, key: " + userNameRedisKey);
//            boolean re = redisDaoImpl.redisDel(userNameRedisKey);
//            if (!re) {
//                return -1;
//            }
//            return 0;
//        } catch (Exception ex) {
//            logger.error("delete the redis session key: ", ex);
//            return -2;
//        }
//    }
}
