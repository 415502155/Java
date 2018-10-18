package com.bestinfo.redis.cms;

import com.bestinfo.bean.business.TCmsInfo;
import com.bestinfo.cache.keys.RedisKeysUtil;
import com.bestinfo.redis.dao.impl.RedisDaoImpl;
import java.io.IOException;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Repository;

/**
 * 内容管理缓存操作类
 *
 * @author lvchangrong
 */
@Repository
public class TcmsInfoRedis {

    @Resource
    private RedisDaoImpl redisDaoImpl;

    Logger logger = Logger.getLogger(TcmsInfoRedis.class);

    /**
     * 根据cms_id获取redis缓存里内容管理信息
     *
     * @param cms_id
     * @return
     */
    public TCmsInfo getTcmsInfoByCmsId(long cms_id) {
        try {
            String tcmsInfoKey = RedisKeysUtil.getTcmsInfoKey(cms_id);
            String tcms = redisDaoImpl.redisLoad(tcmsInfoKey);
            if (tcms == null || tcms.trim().length() == 0) {
                logger.error("there is no key " + tcmsInfoKey + " in redis");
                return null;
            }
            TCmsInfo tcmsInfo = new ObjectMapper().readValue(tcms, TCmsInfo.class);
            return tcmsInfo;
        } catch (IOException e) {
            logger.error("", e);
            return null;
        }
    }
}
