package com.bestinfo.redis.login;

import com.bestinfo.bean.sysUser.DealerLogin;
import com.bestinfo.cache.keys.RedisKeysUtil;
import com.bestinfo.define.returncode.TerminalResultCode;
import com.bestinfo.redis.dao.impl.RedisDaoImpl;
import java.io.IOException;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class DealerLoginRedis {

    @Resource
    private RedisDaoImpl redisDaoImpl;

    private static final Logger logger = Logger.getLogger(TerminalLoginRedis.class);

    /**
     * 往缓存中新增DealerLogin
     *
     * @param dealerid
     * @param terminalid
     * @param logininfo
     * @return 0-成功 -1（失败） -2（失败）
     */
    public int setDealerLoginInfo(int dealerid, int terminalid, DealerLogin logininfo) {
        try {
            boolean re = redisDaoImpl.redisSet(RedisKeysUtil.getDealerLogin(dealerid, terminalid), new ObjectMapper().writeValueAsString(logininfo));
            if (!re) {
                logger.error("redisSet append session info fail,dealerid:" + dealerid + " terminal:" + terminalid);
                return -TerminalResultCode.saveSessionInfo2RedisFail;
            }
            return TerminalResultCode.success;
        } catch (IOException ex) {
            logger.error("", ex);
            return -TerminalResultCode.cacheExceError;
        }
    }

    /**
     * 从缓存读取DealerLoginInfo
     *
     * @param dealerid
     * @param terminalid
     * @return 成功:　SessionInfo 失败:null
     */
    public DealerLogin getDealerLoginInfo(int dealerid, int terminalid) {
        try {
            String loginInfo = redisDaoImpl.redisLoad(RedisKeysUtil.getDealerLogin(dealerid, terminalid));
            if (loginInfo == null || "".equals(loginInfo) || loginInfo.trim().length() == 0) {
                logger.error("sessino info error");
                return null;
            }
            ObjectMapper mapper = new ObjectMapper();
            DealerLogin si = mapper.readValue(loginInfo, DealerLogin.class);
            return si;
        } catch (IOException e) {
            logger.error("", e);
            return null;
        }
    }

    public int delDealerLoginInfo(int dealerid, int terminalid) {
        try {
            Long loginInfo = redisDaoImpl.redisDel(RedisKeysUtil.getDealerLogin(dealerid, terminalid));
            if (loginInfo < 1) {
                logger.error("delete session info error");
                return -1;
            }
            return TerminalResultCode.success;
        } catch (Exception e) {
            logger.error("", e);
            return -TerminalResultCode.cacheExceError;
        }
    }
}
