package com.bestinfo.redis.stat;

import com.bestinfo.bean.game.OpenprizeInfo;
import com.bestinfo.cache.keys.RedisKeysUtil;
import com.bestinfo.redis.dao.impl.RedisDaoImpl;
import java.io.IOException;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Repository;

/**
 * 开奖公告
 *
 * @author lvchangrong
 */
@Repository
public class OpenPrizeRedis {

    @Resource
    private RedisDaoImpl redisDaoImpl;

    private Logger logger = Logger.getLogger(OpenPrizeRedis.class);

    /**
     * 根据key查询开奖公告信息
     *
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    public OpenprizeInfo getOpenprizeInfoByIds(int game_id, int draw_id, int keno_draw_id) {
        try {
            String openPrizekey = RedisKeysUtil.getOpenPrizeKey(game_id, draw_id, keno_draw_id);
            String openPrizeText = redisDaoImpl.redisLoad(openPrizekey);
            if (openPrizeText == null || openPrizeText.trim().length() == 0 || "".equals(openPrizeText)) {
                logger.error("there is no key " + openPrizekey + " in redis");
                return null;
            }
            OpenprizeInfo openprizeInfo = new ObjectMapper().readValue(openPrizeText, OpenprizeInfo.class);
            return openprizeInfo;
        } catch (IOException e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 向redis增加开奖公告信息
     *
     * @param op
     * @return
     */
    public int addOpenPrizeByKey(OpenprizeInfo op) {
        String openPrizekey = RedisKeysUtil.getOpenPrizeKey(op.getGame_id(), op.getDraw_id(), op.getKeno_draw_id());
        try {
            boolean re = redisDaoImpl.redisSet(openPrizekey, new ObjectMapper().writeValueAsString(op));
            if (!re) {
                logger.error("openPrize insert into redis error:" + re);
                return -1;
            }
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -2;
        }
    }

}
