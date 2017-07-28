package com.bestinfo.redis.business;

import com.alibaba.fastjson.JSON;
import com.bestinfo.bean.game.TmnSerialNoG;
import com.bestinfo.cache.keys.RedisKeysUtil;
import com.bestinfo.dao.business.ITmnSerialNoDao;
import com.bestinfo.define.system.CodeCommon;
import com.bestinfo.redis.dao.impl.RedisDaoImpl;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 电话投注终端流水号redis缓存
 *
 */
@Repository
public class TmnSerialNoCache {

    private static final Logger logger = Logger.getLogger(TmnSerialNoCache.class);

    @Resource
    private RedisDaoImpl redisDaoImpl;

    @Resource
    private ITmnSerialNoDao tmnSerialNoDao;

    /**
     * 初始化终端流水号数据
     *
     * @param jdbctemplate 彩民库
     * @param gameid 游戏编号
     * @param drawid 期号
     * @return 0:初始化成功;-1:数据库查询结果为空;-2:初始化失败;-3:初始化异常
     */
    public int init(JdbcTemplate jdbctemplate, int gameid, int drawid) {
        try {
            List<TmnSerialNoG> lt = tmnSerialNoDao.getSerialNoForDealer(jdbctemplate, gameid, drawid);
            if (lt == null || lt.isEmpty()) {
                logger.error("TmnSerialNoG is null from gamb db,game_id:" + gameid + ",draw_id:" + drawid);
                return 0;
            }

            boolean flag = false;
            for (TmnSerialNoG tsn : lt) {
                if (addSerialNoIntoRedis(tsn) < 0) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                return -1;
            }
            logger.info("init gamb TmnSerialNoG into redis,num:" + lt.size() + ",game_id:" + gameid + ",draw_id:" + drawid);
            return 0;
        } catch (Exception ex) {
            logger.error("init gamb TmnSerialNoG into redis error,game_id:" + gameid + ",draw_id:" + drawid, ex);
            return -2;
        }
    }

    /**
     * 根据地市规则从缓存中随机得到某个终端流水号
     *
     * @param gameid 游戏编号
     * @param drawid 期号
     * @param dealerid 代销商编号
     * @param cityid 地市编号
     * @return TmnSerialNoG类(null表示出错或异常)
     */
    public TmnSerialNoG getSerialNoWithCity(int gameid, int drawid, String dealerid, int cityid) {
        try {
            String serialNokey = RedisKeysUtil.getTmnCityKey(gameid, drawid, dealerid, cityid);
            String serialNo = redisDaoImpl.redisSetPop(serialNokey);
            if (serialNo == null || serialNo.trim().length() == 0) {
                logger.error("TmnSerialNoG from redis is null,key:" + serialNokey);
                return null;
            }
            return new ObjectMapper().readValue(serialNo, TmnSerialNoG.class);
        } catch (Exception e) {
            logger.error("get TmnSerialNoG from redis error", e);
            return null;
        }
    }

    /**
     * 根据物理编号规则从缓存中随机得到某个终端流水号
     *
     * @param gameid 游戏编号
     * @param drawid 期号
     * @param dealerid 代销商编号
     * @param physics 终端物理编号
     * @return TmnSerialNoG类(null表示出错或异常)
     */
    public TmnSerialNoG getSerialNoWithPhy(int gameid, int drawid, String dealerid, int physics) {
        try {
            String serialNokey = RedisKeysUtil.getTmnPhyKey(gameid, drawid, dealerid, physics);
            String serialNo = redisDaoImpl.redisSetPop(serialNokey);
            if (serialNo == null || serialNo.trim().length() == 0) {
                logger.error("TmnSerialNoG from redis is null,key:" + serialNokey);
                return null;
            }
            return new ObjectMapper().readValue(serialNo, TmnSerialNoG.class);
        } catch (Exception e) {
            logger.error("get TmnSerialNoG from redis error", e);
            return null;
        }
    }

    /**
     * 终端及彩票流水号放入Redis
     *
     * @param tmnSerialNo 终端及彩票流水号内容
     * @return 0:放入成功;-1:放入失败;-2:放入异常
     */
    public int addSerialNoIntoRedis(TmnSerialNoG tmnSerialNo) {
        if (tmnSerialNo == null) {
            logger.error("TmnSerialNoG is null");
            return -2;
        }

        String serialNokey = null;
        if (tmnSerialNo.getTerminal_condition() == CodeCommon.no) {
            serialNokey = RedisKeysUtil.getTmnCityKey(tmnSerialNo.getGame_id(), tmnSerialNo.getDraw_id(),
                    tmnSerialNo.getDealer_id(), tmnSerialNo.getCity_id());
        } else {
            serialNokey = RedisKeysUtil.getTmnPhyKey(tmnSerialNo.getGame_id(), tmnSerialNo.getDraw_id(),
                    tmnSerialNo.getDealer_id(), tmnSerialNo.getTerminal_phy_id());
        }

        try {
            boolean ret = redisDaoImpl.redisSetSet(serialNokey, JSON.toJSONString(tmnSerialNo));
            if (!ret) {
                logger.error("insert TmnSerialNoG into redis error,key:" + serialNokey + "jsonObject:" + JSON.toJSONString(tmnSerialNo));
                return -1;
            }
            return 0;
        } catch (Exception ex) {
            logger.error("insert TmnSerialNoG into redis error,key:" + serialNokey, ex);
            return -2;
        }
    }

    /**
     * 清除电话投注终端流水号数据
     *
     * @param jdbctemplate 彩民库
     * @param gameid 游戏编号
     * @param drawid 期号
     * @return
     */
    public int clearGambSerialNo(JdbcTemplate jdbctemplate, int gameid, int drawid) {
        try {
            List<TmnSerialNoG> serialNoGList = tmnSerialNoDao.getSerialNoForDealer(jdbctemplate, gameid, drawid);
            if (serialNoGList == null || serialNoGList.isEmpty()) {
                logger.error("TmnSerialNoG is null from gamb db,game_id:" + gameid + ",draw_id:" + drawid);
                return 0;
            }

            int count = 0;
            for (TmnSerialNoG tmnSerialNo : serialNoGList) {
                String serialNokey = null;
                if (tmnSerialNo.getTerminal_condition() == CodeCommon.no) {
                    serialNokey = RedisKeysUtil.getTmnCityKey(tmnSerialNo.getGame_id(), tmnSerialNo.getDraw_id(),
                            tmnSerialNo.getDealer_id(), tmnSerialNo.getCity_id());
                } else {
                    serialNokey = RedisKeysUtil.getTmnPhyKey(tmnSerialNo.getGame_id(), tmnSerialNo.getDraw_id(),
                            tmnSerialNo.getDealer_id(), tmnSerialNo.getTerminal_phy_id());
                }

                //删除
                Set<String> serialNos = redisDaoImpl.redisSetLoad(serialNokey);
                for (String str : serialNos) {
                    if (redisDaoImpl.redisSetRem(serialNokey, str) == 1) {
                        count++;
                    }
                }
            }
            logger.info("clear gamb TmnSerialNoG from redis,serialNoGList size:" + serialNoGList.size() + ",successful size:" + count
                    + ",game_id:" + gameid + ",draw_id:" + drawid);
            return 0;
        } catch (Exception ex) {
            logger.error("init gamb TmnSerialNoG into redis error,game_id:" + gameid + ",draw_id:" + drawid, ex);
            return -1;
        }
    }

    /**
     * 手动同步电话投注终端流水号数据
     *
     * @param jdbctemplate 彩民库
     * @param gameid 游戏编号
     * @param drawid 期号
     * @return
     */
    public int syncGambSerialNo(JdbcTemplate jdbctemplate, int gameid, int drawid) {
        try {
            List<TmnSerialNoG> serialNoGList = tmnSerialNoDao.getSerialNoForDealer(jdbctemplate, gameid, drawid);
            if (serialNoGList == null || serialNoGList.isEmpty()) {
                logger.error("TmnSerialNoG is null from gamb db,game_id:" + gameid + ",draw_id:" + drawid);
                return 0;
            }

            boolean flag = false;
            int addCount = 0;
            for (TmnSerialNoG tmnSerialNo : serialNoGList) {
                String serialNokey = null;
                if (tmnSerialNo.getTerminal_condition() == CodeCommon.no) {
                    serialNokey = RedisKeysUtil.getTmnCityKey(tmnSerialNo.getGame_id(), tmnSerialNo.getDraw_id(),
                            tmnSerialNo.getDealer_id(), tmnSerialNo.getCity_id());
                } else {
                    serialNokey = RedisKeysUtil.getTmnPhyKey(tmnSerialNo.getGame_id(), tmnSerialNo.getDraw_id(),
                            tmnSerialNo.getDealer_id(), tmnSerialNo.getTerminal_phy_id());
                }

                if (!redisDaoImpl.redisSetSet(serialNokey, JSON.toJSONString(tmnSerialNo))) {
                    logger.error("insert TmnSerialNoG into redis error,key:" + serialNokey + "jsonObject:" + JSON.toJSONString(tmnSerialNo));
                    flag = true;
                    break;
                } else {
                    addCount++;
                }
            }
            if (flag) {
                return -1;
            }
            logger.info("init gamb TmnSerialNoG into redis,serialNoGList size:" + serialNoGList.size() + ",successful size:" + addCount
                    + ",game_id:" + gameid + ",draw_id:" + drawid);
            return 0;
        } catch (Exception ex) {
            logger.error("init gamb TmnSerialNoG into redis error,game_id:" + gameid + ",draw_id:" + drawid, ex);
            return -2;
        }
    }

}
