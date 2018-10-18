package com.bestinfo.redis.stat;

import com.bestinfo.bean.stat.LuckyNo;
import com.bestinfo.cache.keys.RedisKeysUtil;
import com.bestinfo.redis.dao.impl.RedisDaoImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lvchangrong
 */
@Repository
public class LuckyNoRedis {

    @Resource
    private RedisDaoImpl redisDaoImpl;

    private Logger logger = Logger.getLogger(LuckyNoRedis.class);

    /**
     * 根据key查询开奖号码信息
     *
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @param openNum
     * @return
     */
    public LuckyNo getLuckyNoByIds(int game_id, int draw_id, int keno_draw_id, int openNum) {
        try {
            String luckyNokey = RedisKeysUtil.getLuckyNoKey(game_id, draw_id, keno_draw_id);
            if ("".equals(luckyNokey) || luckyNokey.trim().length() == 0) {
                logger.error("the luckyNokey is null where game_id=" + game_id + "and draw_id=" + draw_id + " and keno_draw_id=" + keno_draw_id);
                return null;
            }
            String luckyNum = redisDaoImpl.redisHashGet(luckyNokey,Integer.toString(openNum));
            if (luckyNum == null || luckyNum.trim().length() == 0) {
                logger.error("there is no key " + luckyNokey + " in redis");
                return null;
            }
            LuckyNo luckyNo = new ObjectMapper().readValue(luckyNum, LuckyNo.class);
            if (luckyNo == null) {
                logger.error("the luckyNo is null  ");
                return null;
            }
            return luckyNo;
        } catch (IOException e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 向redis增加开奖号码信息
     *
     * @param ly
     * @return
     */
    public int addLuckyNoByKey(LuckyNo ly) {
        String luckykey = RedisKeysUtil.getLuckyNoKey(ly.getGame_id(), ly.getDraw_id(), ly.getKeno_draw_id());
        try {
            boolean re = redisDaoImpl.redisHashSet(luckykey,Integer.toString(ly.getOpen_id()), new ObjectMapper().writeValueAsString(ly));
            if (!re) {
                logger.error("lucky no into redis error:" + re);
                return -1;
            }
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -2;
        }
    }

    /**
     * 查询某个游戏、某个期下多次开奖后的所有开奖号码列表
     *
     * @param gameId
     * @param drawId
     * @param kdrawId
     * @return
     */
    public List<LuckyNo> getLucyNoListFromRedis(int gameId, int drawId, int kdrawId) {
        List<LuckyNo> lnList = new ArrayList<LuckyNo>();
        try {
            String luckyNokey = RedisKeysUtil.getLuckyNoKey(gameId, drawId, kdrawId);
            List<String> luckyNum = redisDaoImpl.redisHashVals(luckyNokey);
            if (luckyNum == null || luckyNum.size() == 0) {
                logger.error("there is no key " + luckyNokey + " in redis");
                return Collections.emptyList();
            }
            for (String s : luckyNum) {
                LuckyNo luckyNo = new ObjectMapper().readValue(s, LuckyNo.class);
                lnList.add(luckyNo);
            }
            return lnList;
        } catch (Exception e) {
            logger.error("read lucky no info from redis failed where gameId = " + gameId + " and drawId = " + drawId + " and kdrawId = " + kdrawId, e);
            return Collections.emptyList();
        }
    }
    
}
