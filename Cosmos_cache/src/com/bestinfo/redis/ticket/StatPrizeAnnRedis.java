package com.bestinfo.redis.ticket;

import com.bestinfo.bean.stat.StatPrizeAnn;
import com.bestinfo.cache.keys.RedisKeysUtil;
import com.bestinfo.dao.stat.IStatPrizeAnnDao;
import com.bestinfo.redis.dao.impl.RedisDaoImpl;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 结算统计表-普通游戏中奖汇总
 *
 * @author lvchangrong
 */
@Repository
public class StatPrizeAnnRedis {

    @Resource
    private RedisDaoImpl redisDaoImpl;
    @Resource
    private JdbcTemplate metaJdbcTemplate;  //基础库
    @Resource
    private IStatPrizeAnnDao statPrizeAnnDao;
    private Logger logger = Logger.getLogger(StatPrizeAnnRedis.class);

    /**
     * 同步基础库里普通游戏中奖汇总数据到Redis
     *
     * @return -2:数据库数据为空 -3：同步出错
     */
    public int init() {
        try {
            List<StatPrizeAnn> list = statPrizeAnnDao.getStatPrizeAnnList(metaJdbcTemplate);
            if (list == null || list.isEmpty()) {
                logger.error("the statPrizeAnn from meta DB is null ");
                return -2;
            }
            int res = synStatPrizeAnn2Redis(list);
            if (res < 0) {
                logger.error(" Syn StatPrizeAnn ");
                return -3;
            }
            logger.info(" syn StatPrizeAnn success  size:"+list.size());
            return 0;
        } catch (Exception ex) {
            logger.error("ex", ex);
            return -1;
        }
    }

    /**
     * 根据key查询普通游戏中奖数据
     *
     * @param game_id
     * @param draw_id
     * @param class_id
     * @return
     */
    public StatPrizeAnn getStatPrizeAnnById(int game_id, int draw_id, int class_id) {
        try {
            String key = RedisKeysUtil.getStatPrizeAnnKey(game_id, draw_id, class_id);
            String statPrizeAnnText = redisDaoImpl.redisLoad(key);
            if (statPrizeAnnText == null || statPrizeAnnText.trim().length() == 0 || "".equals(statPrizeAnnText)) {
                logger.error("there is no key " + key + " in redis");
                return null;
            }
            StatPrizeAnn statPrizeAnn = new ObjectMapper().readValue(statPrizeAnnText, StatPrizeAnn.class);
            return statPrizeAnn;
        } catch (IOException e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 向redis增加普通游戏中奖数据
     *
     * @param sp
     * @return
     */
    public int addStatPrizeAnn(StatPrizeAnn sp) {
        String key = RedisKeysUtil.getStatPrizeAnnKey(sp.getGame_id(), sp.getDraw_id(), sp.getClass_id());
        try {
            boolean re = redisDaoImpl.redisSet(key, new ObjectMapper().writeValueAsString(sp));
            if (!re) {
                logger.error("StatPrizeAnn insert into redis error:" + re);
                return -1;
            }
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -2;
        }
    }

//    /**
//     * 批量增加普通游戏中奖数据
//     *
//     * @param lsp
//     * @return
//     */
//    public int addStatPrizeAnnBatch(List<StatPrizeAnn> lsp) {
//        try {
//            for (StatPrizeAnn sp : lsp) {
//                addStatPrizeAnn(sp);
//            }
//            return 0;
//        } catch (Exception ex) {
//            logger.error("ex :" + ex);
//            return -1;
//        }
//    }

    /**
     * 将普通游戏中奖汇总数据同步到Redis
     *
     * @param lsp
     * @return
     */
    public int synStatPrizeAnn2Redis(List<StatPrizeAnn> lsp) {
        try {
            for (StatPrizeAnn sp : lsp) {
//                logger.info("===============class_id :"  +sp.getClass_id()+" class_name :  "+sp.getClass_name());
                String key = RedisKeysUtil.getStatPrizeAnnKey(sp.getGame_id(), sp.getDraw_id(), sp.getClass_id());
                boolean re = redisDaoImpl.redisSet(key, new ObjectMapper().writeValueAsString(sp));
                if (!re) {
                    logger.error("Syn  StatPrizeAnn  to redis error");
                    return -2;
                }
            }
            return 0;
        } catch (Exception ex) {
            logger.error(" syn StatPrizeAnn to Redis error:", ex);
            return -1;
        }
    }

}
