package com.bestinfo.redis.login;

import com.bestinfo.bean.session.SessionInfo;
import com.bestinfo.cache.keys.RedisKeysUtil;
import com.bestinfo.define.returncode.TerminalResultCode;
import com.bestinfo.redis.dao.impl.RedisDaoImpl;
import java.io.IOException;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author YangRong
 */
@Repository
public class TerminalLoginRedis {

    @Resource
    private RedisDaoImpl redisDaoImpl;

    Logger logger = Logger.getLogger(TerminalLoginRedis.class);

    /**
     * 往缓存中新增SessionInfo
     *
     * @param key
     * @param sessionInfo
     * @return 0-成功 -1（失败） -2（失败）
     */
    public int saveSessionInfo(String key, SessionInfo sessionInfo) {
        try {
            boolean re = redisDaoImpl.redisSet(RedisKeysUtil.getIdentityKey(key), new ObjectMapper().writeValueAsString(sessionInfo));
            if (!re) {
                logger.error("redisSet append session info fail,key:" + key);
                return -TerminalResultCode.saveSessionInfo2RedisFail;
            }
            return TerminalResultCode.success;
        } catch (IOException ex) {
            logger.error("", ex);
            return -TerminalResultCode.cacheExceError;
        }
    }

    /**
     * 从缓存读取SessionInfo
     *
     * @param sessionId key
     * @return 成功:　SessionInfo 失败:null
     */
    public SessionInfo getSessionInfo(String sessionId) {
        try {
            String sessionInfo = redisDaoImpl.redisLoad(RedisKeysUtil.getIdentityKey(sessionId));
            if (sessionInfo == null || "".equals(sessionInfo) || sessionInfo.trim().length() == 0) {
                logger.error("sessino info error");
                return null;
            }
            ObjectMapper mapper = new ObjectMapper();
            SessionInfo si = mapper.readValue(sessionInfo, SessionInfo.class);
            return si;
        } catch (IOException e) {
            logger.error("", e);
            return null;
        }
    }

    public List<SessionInfo> getSessionInfoListById(int beginTerminalId, int endTerminalId) {

        List<SessionInfo> silist = new ArrayList();
        int bi, ed;
        if (beginTerminalId <= endTerminalId) {
            bi = beginTerminalId;
            ed = endTerminalId;
        } else {
            bi = endTerminalId;
            ed = beginTerminalId;
        }

        for (int i = bi; i <= ed; i++) {
            try {
                //直接拿terminalId当sessionId,照理应调用算法模块里的LoginSession
                String sessionInfo = redisDaoImpl.redisLoad(RedisKeysUtil.getIdentityKey(Integer.toString(i)));
                if (sessionInfo == null || "".equals(sessionInfo) || sessionInfo.trim().length() == 0) {
//                    logger.error("sessino info load error");
//                    SessionInfo si = new SessionInfo();
//                    si.setLogin_terminal(Integer.toString(i));
//                    silist.add(si);
                    continue;
                }
                ObjectMapper mapper = new ObjectMapper();
                SessionInfo si = mapper.readValue(sessionInfo, SessionInfo.class);
                if(si==null){
//                    logger.error("sessino info mapper error");
//                    si = new SessionInfo();
//                    si.setLogin_terminal(Integer.toString(i));
//                    silist.add(si);
                    continue;
                }
                si.setSession_inv(new byte[0]);
                si.setSession_key(new byte[0]);
                silist.add(si);
            } catch (IOException e) {
                logger.error("", e);
                return Collections.emptyList();
            }
        }
        return silist;

    }

    /**
     * 往缓存中新增随机串
     *
     * @param sessionId 会话id,key
     * @param identity 投注终端发来的字符串经解密后，得到的字符串+服务器产生的随机字符串
     * @return 0-成功 -1（失败） -2（失败）
     */
    public int saveIdentity(String sessionId, String identity) {
        String identityRedisKey = RedisKeysUtil.getLoginStrKey(sessionId);
        try {
            boolean re = redisDaoImpl.redisSet(identityRedisKey, identity);
            if (!re) {
                return -TerminalResultCode.loginSessionSaveRedisFail;
            }
            return TerminalResultCode.success;
        } catch (Exception ex) {
            logger.error("", ex);
            return -TerminalResultCode.cacheExceError;
        }
    }

    /**
     * 从缓存读取读取随机串
     *
     * @param sessionId key
     * @return 成功:　identity 失败:null
     */
    public String getIdentity(String sessionId) {
        String identityRedisKey = RedisKeysUtil.getLoginStrKey(sessionId);
        if ("".equals(identityRedisKey) || identityRedisKey.trim().length() == 0) {
            logger.error("the identityRedisKey is null where sessionId=" + sessionId);
            return StringUtils.EMPTY;
        }
        try {
            String re = redisDaoImpl.redisLoad(identityRedisKey);
            if ("".equals(re) || re.trim().length() == 0) {
                logger.error("the re is null where identityRedisKey=" + identityRedisKey);
                return null;
            }
            return re;
        } catch (Exception ex) {
            logger.error("", ex);
            return null;
        }
    }

    /**
     * 从缓存删除identity
     *
     * @param sessionId 会话id,key
     * @return
     */
//    public boolean deleteIdentity(String sessionId) {
//        String identityRedisKey = RedisKeysUtil.getLoginStrKey(sessionId);
//        if ("".equals(identityRedisKey) || identityRedisKey.trim().length() == 0) {
//            logger.error("the identityRedisKey is null where sessionId=" + sessionId);
//            return false;
//        }
//        try {
//            boolean re = redisDaoImpl.redisDel(identityRedisKey);
//            return re;
//        } catch (Exception ex) {
//            logger.error("", ex);
//            return false;
//        }
//    }
}
