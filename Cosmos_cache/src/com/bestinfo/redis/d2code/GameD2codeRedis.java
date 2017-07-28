package com.bestinfo.redis.d2code;

import com.bestinfo.bean.ticket.d2code.D2codeInfo;
import com.bestinfo.bean.ticket.d2code.GameAddInfo;
import com.bestinfo.cache.keys.RedisKeysUtil;
import com.bestinfo.dao.d2code.IGameD2codeInfoDao;
import com.bestinfo.redis.dao.impl.RedisDaoImpl;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GameD2codeRedis {

    private static final Logger logger = Logger.getLogger(GameD2codeRedis.class);

    @Resource
    private RedisDaoImpl redisDaoImpl;

    @Resource
    private JdbcTemplate metaJdbcTemplate;

    @Resource
    private IGameD2codeInfoDao gameD2codeInfoDao;

    /**
     * 查询游戏附加信息数据，gameAddInfoDao并全部放入缓存
     *
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int initGameAddInfo() {
        try {
            List<GameAddInfo> gameAddList = gameD2codeInfoDao.selectGameAddInfo(metaJdbcTemplate);
            if (gameAddList == null || gameAddList.isEmpty()) {
                logger.warn("there is no GameAddInfo in db");
                return -2;
            }
            for (GameAddInfo gameAddInfo : gameAddList) {
                String key = RedisKeysUtil.getGameAddInfoKey(gameAddInfo.getGame_id(),gameAddInfo.getBegin_draw_id());
                boolean re = redisDaoImpl.redisSet(key, new ObjectMapper().writeValueAsString(gameAddInfo));

                if (!re) {
                    logger.error("insert GameAddInfo into redis error,key" + key);
                    return -1;
                }
            }

            return 0;
        } catch (Exception e) {
            logger.error("GameAddInfo from DB syn Redis error", e);
            return -3;
        }
    }

    /**
     * 根据Key查询对应的缓存数据对象
     *
     * @param gameId 游戏编号
     * @param drawId
     * @return 游戏附加信息
     */
    public GameAddInfo getGameAddInfoByid(int gameId, int drawId) {
        String gameAddInfokey = RedisKeysUtil.getGameAddInfoKey(gameId, drawId);
        try {
            String gameAddInfo = redisDaoImpl.redisLoad(gameAddInfokey);
            if (gameAddInfo == null || gameAddInfo.trim().length() == 0) {
                logger.error("gameAddInfo from redis is null,key:" + gameAddInfokey);
                return null;
            }
            logger.info("redis de gameAddInfo : " + gameAddInfo);
            return new ObjectMapper().readValue(gameAddInfo, GameAddInfo.class);
        } catch (Exception ex) {
            logger.error("Exception is:", ex);
            return null;
        }
    }

    /**
     * 更新缓存中某Key所对应的数据
     *
     * @param gi
     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int updateGameAddInfoByid(GameAddInfo gi) {
        String gameAddInfokey = RedisKeysUtil.getGameAddInfoKey(gi.getGame_id(),gi.getBegin_draw_id());
        try {
            boolean re = redisDaoImpl.redisSet(gameAddInfokey, new ObjectMapper().writeValueAsString(gi));
            if (!re) {
                logger.error("gameaddinfo insert into redis error");
                return -3;
            }

            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -1;
        }
    }

    /**
     * 将新增游戏附加信息添加到缓存中
     *
     * @param gameaddinfo
     * @return
     */
    public int insertGameAddInfoToCache(GameAddInfo gameaddinfo) {
        String gameAddInfokey = RedisKeysUtil.getGameAddInfoKey(gameaddinfo.getGame_id(),gameaddinfo.getBegin_draw_id());
        logger.info("gameaddinfo gameAddInfokey: "+ gameAddInfokey);
        try {
            boolean re = redisDaoImpl.redisSet(gameAddInfokey, new ObjectMapper().writeValueAsString(gameaddinfo));
            if (!re) {
                logger.error("gameaddinfo insert into redis error");
                return -2;
            }
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -1;
        }
    }

    /**
     * 查询二维码信息数据，d2codeInfoDao并全部放入缓存
     *
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int initD2codeInfo() {
        try {
            List<D2codeInfo> d2codeInfoList = gameD2codeInfoDao.selectD2codeInfo(metaJdbcTemplate);
            if (d2codeInfoList == null || d2codeInfoList.isEmpty()) {
                logger.warn("there is no d2code info in db");
                return -2;
            }
            for (D2codeInfo d2codeInfo : d2codeInfoList) {
                String key = RedisKeysUtil.getD2codeInfoKey(d2codeInfo.getD2code_id());
                boolean re = redisDaoImpl.redisSet(key, new ObjectMapper().writeValueAsString(d2codeInfo));

                if (!re) {
                    logger.error("insert d2codeInfo into redis error,key" + key);
                    return -1;
                }
            }

            return 0;
        } catch (Exception e) {
            logger.error("d2codeInfo from DB syn Redis error", e);
            return -3;
        }
    }

    /**
     * 根据Key查询对应的缓存数据对象
     *
     * @param d2code_id 编码编号
     * @return 二维码信息
     */
    public D2codeInfo getD2codeInfoByid(int d2code_id) {
        String d2codeInfokey = RedisKeysUtil.getD2codeInfoKey(d2code_id);
        try {
            String d2codeInfo = redisDaoImpl.redisLoad(d2codeInfokey);
            if (d2codeInfo == null || d2codeInfo.trim().length() == 0) {
                logger.error("d2codeInfo from redis is null,key:" + d2codeInfokey);
                return null;
            }
            return new ObjectMapper().readValue(d2codeInfo, D2codeInfo.class);
        } catch (Exception ex) {
            logger.error("Exception is:", ex);
            return null;
        }
    }

    /**
     * 更新缓存中某Key所对应的数据
     *
     * @param gi
     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int updateD2codeInfoByid(D2codeInfo gi) {
        String d2codeInfokey = RedisKeysUtil.getD2codeInfoKey(gi.getD2code_id());
        try {
            boolean re = redisDaoImpl.redisSet(d2codeInfokey, new ObjectMapper().writeValueAsString(gi));
            if (!re) {
                logger.error("d2codeInfo insert into redis error");
                return -3;
            }

            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -1;
        }
    }

    /**
     * 将新增二维码信息添加到缓存中
     *
     * @param d2codeInfo
     * @return
     */
    public int insertD2codeInfoToCache(D2codeInfo d2codeInfo) {
        String d2codeInfokey = RedisKeysUtil.getD2codeInfoKey(d2codeInfo.getD2code_id());
        try {
            boolean re = redisDaoImpl.redisSet(d2codeInfokey, new ObjectMapper().writeValueAsString(d2codeInfo));
            if (!re) {
                logger.error("d2codeInfo insert into redis error");
                return -2;
            }
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -1;
        }
    }

}
