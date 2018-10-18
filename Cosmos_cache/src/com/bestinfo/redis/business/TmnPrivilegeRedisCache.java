package com.bestinfo.redis.business;

import com.bestinfo.bean.business.TmnPrivilege;
import com.bestinfo.cache.keys.RedisKeysUtil;
import com.bestinfo.dao.business.ITmnPrivilegeDao;
import com.bestinfo.redis.dao.impl.RedisDaoImpl;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 终端特权注册
 *
 * @author hhhh
 */
@Repository
public class TmnPrivilegeRedisCache {

    @Resource
    private RedisDaoImpl redisDaoImpl;

    @Resource
    private ITmnPrivilegeDao tmnPrivilegeDao;

    @Resource
    private JdbcTemplate termJdbcTemplate; //终端库，请与项目配置核对此名

    private final Logger logger = Logger.getLogger(TmnPrivilegeRedisCache.class);

    /**
     * 初始化终端特权缓存数据
     *
     * @param jdbcTemplate
     * @return
     */
    public int init() {
        try {
            List<TmnPrivilege> lt = tmnPrivilegeDao.selectTmnPrivilege(termJdbcTemplate);
            if (lt == null || lt.isEmpty()) {
                logger.error("there is no data in db");
                return -1;
            }
            int re = addTmnPrivilegeListIntoRedis(lt);
            if (re < 0) {
                logger.error("put tmn privilege into redis error:" + re);
                return -2;
            }
            logger.info("放入缓存的数据条数：" + lt.size());
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -3;
        }
    }

    /**
     * 根据key获取投注终端特权对象
     *
     * @param terminalId
     * @param gameId
     * @return
     */
    public TmnPrivilege getTmnPrivilegeById(int terminalId, int gameId) {
        try {
            String tmnPrivilegekey = RedisKeysUtil.getTmnPrivilegeKey(terminalId, gameId);
            String tmnPrivilege = redisDaoImpl.redisLoad(tmnPrivilegekey);
            if (tmnPrivilege == null || tmnPrivilege.trim().length() == 0) {
                logger.error("there is no tmn privilege info in redis where terminalId =" + terminalId + " and gameId=" + gameId);
                return null;
            }
            TmnPrivilege tp = new ObjectMapper().readValue(tmnPrivilege, TmnPrivilege.class);
            return tp;
        } catch (Exception e) {
            logger.error("get tmn privilege info from Redis failed where terminalId=" + terminalId + " and gameId=" + gameId, e);
            return null;
        }
    }

    /**
     * 往缓存中添加终端特权信息
     *
     * @param tmnPrivilege
     * @return 0-成功 -1（tmnPrivilege失败） -2（失败）
     */
    public int addTmnPrivilegeIntoCache(TmnPrivilege tmnPrivilege) {
        String tmnPrivilegeRedisKey = RedisKeysUtil.getTmnPrivilegeKey(tmnPrivilege.getTerminal_id(), tmnPrivilege.getGame_id());
        try {
            boolean re = redisDaoImpl.redisSet(tmnPrivilegeRedisKey, new ObjectMapper().writeValueAsString(tmnPrivilege));
            if (!re) {
                logger.error("tmn privilege into redis error, tmnPrivilegeRedisKey:" + tmnPrivilegeRedisKey + "," + re);
                return -1;
            }
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -2;
        }
    }

    /**
     * 往redis里批量插入终端特权信息
     *
     * @param tpList
     * @return 0(成功) -1(失败)
     */
    public int addTmnPrivilegeListIntoRedis(List<TmnPrivilege> tpList) {
        boolean flag = false;
        for (TmnPrivilege tmnPrivilege : tpList) {
            int re = addTmnPrivilegeIntoCache(tmnPrivilege);
            if (re < 0) {
                logger.error("insert tmnPrivilege into redis error where terminalId =  " + tmnPrivilege.getTerminal_id() + " and gameId = " + tmnPrivilege.getGame_id());
                flag = true;
                break;
            }
        }
        if (flag) { //插入过程中出错
            logger.error("init tmnPrivilege to Redis error");
            return -1;
        }
        return 0;
    }

    /**
     * 从redis中删除指定终端下的所有特权信息
     *
     * @param tpList
     * @return 0(成功) -1（失败）
     */
//    public int delTmnPrivilegeListByTerminalId(List<TmnPrivilege> tpList) {
//        boolean flag = false;
//        for (TmnPrivilege tmnPrivilege : tpList) {
//            boolean re = redisDaoImpl.redisDel(RedisKeysUtil.getTmnPrivilegeKey(tmnPrivilege.getTerminal_id(), tmnPrivilege.getGame_id()));
//            if (!re) {
//                //删除失败
//                flag = true;
//                break;
//            }
//        }
//        if (flag) {
//            logger.info("delete tmnPrivilege from redis failed where terminalId = " + tpList.get(0).getTerminal_id());
//            return -1;
//        }
//        return 0;
//    }

    /**
     * 更新缓存中开奖封机字段
     *
     * @param gameid
     * @param gameStop
     * @return
     */
//    public int updateTmnPriGameStop(int gameid, int gameStop) {
//        try {
//            String key = RedisKeysUtil.getTmnPrivilegeKey(0, gameid);
//            String tmnpr = redisDaoImpl.redisLoad(key);
//            if (tmnpr == null) {
//                logger.error("can't find key object");
//                return -3;
//            }
//            TmnPrivilege tmn = new ObjectMapper().readValue(tmnpr, TmnPrivilege.class);
//            tmn.setGame_stop(gameStop);
//            boolean re = redisDaoImpl.redisSet(key, new ObjectMapper().writeValueAsString(tmn));
//            if (!re) {
//                logger.error("tmn privilege into redis error:" + re);
//                return -1;
//            }
//            return 0;
//        } catch (Exception e) {
//            logger.error(e);
//            return -2;
//        }
//    }
    /**
     * 根据终端编号查询该终端下所有游戏的特权数据
     *
     * @param terminalId
     * @return
     */
//    public List<TmnPrivilege> getTmnPrivilegeList(int terminalId) {
//        List<TmnPrivilege> tpList = new ArrayList<TmnPrivilege>();
//        try {
//            String key = RedisKeysUtil.getTmnPrivilegeKey(terminalId, 0) + "*";
//            Set set = redisDaoImpl.getKeys(key);
//            logger.info("tmn privilege num in terminal: "+terminalId+"---------"+set.size());
//            Iterator it = set.iterator();
//            while (it.hasNext()) {
//                String key1 = (String) it.next();
//                String tmnPrivilege = redisDaoImpl.redisLoad(key1);
//                if (tmnPrivilege == null || tmnPrivilege.trim().equals("")) {
//                    logger.error("there is no tmn privilege info in redis where key is " + key1);
//                    return Collections.emptyList();
//                }
//                TmnPrivilege tp = new ObjectMapper().readValue(tmnPrivilege, TmnPrivilege.class);
//                tpList.add(tp);
//            }
//        } catch (Exception e) {
//            logger.error("get TmnPrivilege List failed where terminalId=" + terminalId);
//            return Collections.emptyList();
//        }
//        return tpList;
//    }
}
