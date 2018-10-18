package com.bestinfo.redis.ticket;

import com.bestinfo.bean.stat.StatKenoPrizeAnn;
import com.bestinfo.cache.keys.RedisKeysUtil;
import com.bestinfo.dao.stat.IStatKenoPrizeAnnDao;
import com.bestinfo.redis.dao.impl.RedisDaoImpl;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 结算统计表-快开游戏中奖汇总
 *
 * @author lvchangrong
 */
@Repository
public class StatKenoPrizeAnnRedis {

    @Resource
    private RedisDaoImpl redisDaoImpl;
    @Resource
    private IStatKenoPrizeAnnDao statKenoPrizeAnnDao;
    @Resource
    private JdbcTemplate metaJdbcTemplate;  //基础库

    private Logger logger = Logger.getLogger(StatKenoPrizeAnnRedis.class);

    /**
     * 同步基础库里快开游戏中奖汇总数据到Redis
     *
     * @return -2:数据库数据为空 -3：同步出错
     */
    public int init() {
        try {
            List<StatKenoPrizeAnn> list = statKenoPrizeAnnDao.getStatKenoPrizeAnnList(metaJdbcTemplate);
            if (list == null || list.isEmpty()) {
                logger.error("the StatKenoPrizeAnn from DB is null");
                return -2;
            }
            int re = synStatKenoPrize2Redis(list);
            if(re < 0){
                logger.error(" syn StatKenoPrizeAnn from DB to Redis error");
                return -3;
            }
           logger.info(" syn StatKenoPrizeAnn  success  size:"+list.size());
            return 0;
        } catch (Exception ex) {
            logger.error("ex ", ex);
            return -1;
        }
    }

    /**
     * 根据key查询快开游戏中奖数据
     *
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @param class_id
     * @return
     */
    public StatKenoPrizeAnn getStatKenoPrizeAnnById(int game_id, int draw_id, int keno_draw_id, int class_id) {
        try {
            String key = RedisKeysUtil.getStatKenoPrizeAnnKey(game_id, draw_id, keno_draw_id, class_id);
            String statKenoPrizeAnnText = redisDaoImpl.redisLoad(key);
            if (statKenoPrizeAnnText == null || statKenoPrizeAnnText.isEmpty()) {
                logger.error("there is no key " + key + " in redis");
                return null;
            }
            StatKenoPrizeAnn statKenoPrizeAnn = new ObjectMapper().readValue(statKenoPrizeAnnText, StatKenoPrizeAnn.class);
            return statKenoPrizeAnn;
        } catch (IOException e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 向redis增加快开游戏中奖数据
     *
     * @param sp
     * @return
     */
    public int addStatKenoPrizeAnn(StatKenoPrizeAnn sp) {
        String key = RedisKeysUtil.getStatKenoPrizeAnnKey(sp.getGame_id(), sp.getDraw_id(), sp.getKeno_draw_id(), sp.getClass_id());
        try {
            boolean re = redisDaoImpl.redisSet(key, new ObjectMapper().writeValueAsString(sp));
            if (!re) {
                logger.error("StatKenoPrizeAnn insert into redis error:" + re);
                return -1;
            }
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -2;
        }
    }

//    /**
//     * 批量增加快开游戏中奖数据
//     *
//     * @param lsp
//     * @return
//     */
//    public int addStatKenoPrizeAnnBatch(List<StatKenoPrizeAnn> lsp) {
//        try {
//            for (StatKenoPrizeAnn sp : lsp) {
//                addStatKenoPrizeAnn(sp);
//            }
//            return 0;
//        } catch (Exception ex) {
//            logger.error("ex :" + ex);
//            return -1;
//        }
//    }

    /**
     * 将快开游戏中奖汇总数据添加到Redis缓存
     *
     * @param lsp
     * @return
     */
    public int synStatKenoPrize2Redis(List<StatKenoPrizeAnn> lsp) {
        try {
            for (StatKenoPrizeAnn sp : lsp) {
                String key = RedisKeysUtil.getStatKenoPrizeAnnKey(sp.getGame_id(), sp.getDraw_id(), sp.getKeno_draw_id(), sp.getClass_id());
                boolean re = redisDaoImpl.redisSet(key, new ObjectMapper().writeValueAsString(sp));
                if (!re) {
                    logger.error("Syn  StatKenoPrizeAnn  to redis error");
                    return -2;
                }
            }
            return 0;
        } catch (Exception ex) {
            logger.error("ex ", ex);
            return -1;
        }

    }
}
