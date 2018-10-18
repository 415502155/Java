package com.bestinfo.redis.gamedraw;

import com.bestinfo.bean.game.GameInfo;
import com.bestinfo.bean.game.GameInfoList;
import com.bestinfo.cache.keys.RedisKeysUtil;
import com.bestinfo.dao.game.IGameInfoDao;
import com.bestinfo.redis.dao.impl.RedisDaoImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lvchangrong
 */
@Repository
public class GameInfoRedis {

    @Resource
    private RedisDaoImpl redisDaoImpl;
    @Resource
    private JdbcTemplate metaJdbcTemplate;
    @Resource
    private IGameInfoDao gameInfoDao;
    private final Logger logger = Logger.getLogger(GameInfoRedis.class);

    /**
     * 查询游戏信息数据，gameInfoDao并全部放入缓存
     *
     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int init() {
        try {
            List<GameInfo> listGameInfo = gameInfoDao.selectGameInfo(metaJdbcTemplate);
            if (listGameInfo == null || listGameInfo.isEmpty()) {
                logger.warn(" there is no gameInfo in meta DB");
                return -2;
            }
            for (GameInfo gameInfo : listGameInfo) {
                String key = RedisKeysUtil.getGameInfoKey(gameInfo.getGame_id());
                boolean re = redisDaoImpl.redisSet(key, new ObjectMapper().writeValueAsString(gameInfo));
                if (!re) {
                    logger.error("gameInfo insert into redis error");
                    return -1;
                }
            }
            //将游戏列表添加到缓存中
            //因这里的redis不能用*去匹配模糊查询，当需要获取全部游戏列表信息,又不能去数据库里查询数据。 故这里将游戏List列表作为value存在缓存中。
            GameInfoList gameList = new GameInfoList();
            gameList.setGameInfoList(listGameInfo);
            String gameInfoListkey = RedisKeysUtil.getGameInfoListKey();
            boolean re2 = redisDaoImpl.redisSet(gameInfoListkey, new ObjectMapper().writeValueAsString(gameList));
            if (!re2) {
                logger.error("gameInfoList insert into redis error");
                return -1;
            }

            return 0;
        } catch (Exception e) {
            logger.error("gameinfo from DB syn Redis error", e);
            return -3;
        }
    }

    /**
     * 更新game_info表中的当前期字段
     *
     * @param gameId
     * @param drawid
     * @return 0正确 <0错误
     */
    public int updateCurDraw(int gameId, int drawid) {
        String gameInfokey = RedisKeysUtil.getGameInfoKey(gameId);
        try {
            String gameInfo = redisDaoImpl.redisLoad(gameInfokey);
            if (gameInfo == null || gameInfo.trim().length() == 0) {
                logger.error("there is no data where  key is " + gameInfokey + " in redis");
                return -3;
            }
            GameInfo gi = new ObjectMapper().readValue(gameInfo, GameInfo.class);
            gi.setCur_draw_id(drawid);
            boolean re = redisDaoImpl.redisSet(gameInfokey, new ObjectMapper().writeValueAsString(gi));
            if (!re) {
                logger.error("gameInfo  update redis error:" + re);
                return -1;
            }

            //修改key为gameInfoList:0的缓存信息
            String gameInfoListkey = RedisKeysUtil.getGameInfoListKey();
            String gameInfoListString = redisDaoImpl.redisLoad(gameInfoListkey);
            if (gameInfoListString == null || gameInfoListString.isEmpty()) {
                logger.error("there is no data where  key is " + gameInfoListkey + "  in redis");
                return -4;
            }
            GameInfoList gameInfoList = new ObjectMapper().readValue(gameInfoListString, GameInfoList.class);
            List<GameInfo> listGameInfo = gameInfoList.getGameInfoList();
            for (GameInfo gmi : listGameInfo) {
                if (gmi.getGame_id() == gameId) {
                    gmi.setCur_draw_id(drawid);
                    break;
                }
            }
            gameInfoList.setGameInfoList(listGameInfo);
            boolean re2 = redisDaoImpl.redisSet(gameInfoListkey, new ObjectMapper().writeValueAsString(gameInfoList));
            if (!re2) {
                logger.error("gameInfoList  update redis error:" + re);
                return -6;
            }
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -2;
        }
    }

    /**
     * 根据Key查询对应的缓存数据对象
     *
     * @param gameId 游戏编号
     * @return 游戏信息
     */
    public GameInfo getGameInfoByid(int gameId) {
        String gameInfokey = RedisKeysUtil.getGameInfoKey(gameId);
        try {
            String gameInfo = redisDaoImpl.redisLoad(gameInfokey);
            if (gameInfo == null || gameInfo.trim().length() == 0) {
                logger.error("there is no key " + gameInfokey + " in redis");
                return null;
            }
            GameInfo gi = new ObjectMapper().readValue(gameInfo, GameInfo.class);
            return gi;
        } catch (Exception ex) {
            logger.error("", ex);
            return null;
        }
    }

    /**
     * 更新缓存中某Key所对应的数据
     *
     * @param jdbcTemplate
     * @param gi
     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int updateGameInfoByid(GameInfo gi) {
        String gameInfokey = RedisKeysUtil.getGameInfoKey(gi.getGame_id());
        try {
//            String gameInfo = redisDaoImpl.redisLoad(gameInfokey);
//            if (gameInfo == null || gameInfo.trim().length() == 0) {
//                logger.error("there is no key " + gameInfokey + " in redis");
//                return -2;
//            }
            boolean re = redisDaoImpl.redisSet(gameInfokey, new ObjectMapper().writeValueAsString(gi));
            if (!re) {
                logger.error("gameinfo insert into redis error");
                return -3;
            }

            //修改key为gameInfoList:0的缓存信息
            String gameInfoListkey = RedisKeysUtil.getGameInfoListKey();
            String gameInfoListString = redisDaoImpl.redisLoad(gameInfoListkey);
            if (gameInfoListString == null || gameInfoListString.isEmpty()) {
                logger.error("there is no data where  key is " + gameInfoListkey + "  in redis");
                return -4;
            }
            GameInfoList gameInfoList = new ObjectMapper().readValue(gameInfoListString, GameInfoList.class);
            if (gameInfoList == null) {
                logger.error("there is no data where  key is " + gameInfoListkey + "  in redis");
                return -4;
            }
            List<GameInfo> listGameInfo = gameInfoList.getGameInfoList();
            List<GameInfo> listGameInfo2 = new ArrayList();
            for (GameInfo gio : listGameInfo) {
                String gameInfokey_ = RedisKeysUtil.getGameInfoKey(gio.getGame_id());
                String gameInfo_ = redisDaoImpl.redisLoad(gameInfokey_);
                if (gameInfo_ == null || gameInfo_.trim().length() == 0) {
                    logger.error("there is no key " + gameInfokey_ + " in redis");
                    return -6;
                }
                GameInfo gi_ = new ObjectMapper().readValue(gameInfo_, GameInfo.class);
                listGameInfo2.add(gi_);
            }
            gameInfoList.setGameInfoList(listGameInfo2);
            boolean re2 = redisDaoImpl.redisSet(gameInfoListkey, new ObjectMapper().writeValueAsString(gameInfoList));
            if (!re2) {
                logger.error("gameInfoList  update redis error:" + re);
                return -7;
            }

            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -1;
        }
    }

    /**
     * 将新增游戏信息添加到缓存中
     *
     * @param gameinfo
     * @return
     */
    public int insertGameInfoToCache(GameInfo gameinfo) {
        String gameInfokey = RedisKeysUtil.getGameInfoKey(gameinfo.getGame_id());
        try {
            boolean re = redisDaoImpl.redisSet(gameInfokey, new ObjectMapper().writeValueAsString(gameinfo));
            if (!re) {
                logger.error("gamekdraw insert into redis error");
                return -2;
            }

            //修改key为gameInfoList:0的缓存信息
            String gameInfoListkey = RedisKeysUtil.getGameInfoListKey();
            String gameInfoListString = redisDaoImpl.redisLoad(gameInfoListkey);
            if (gameInfoListString == null || gameInfoListString.isEmpty()) {
                logger.error("there is no data where  key is " + gameInfoListkey + "  in redis");
                return -4;
            }
            GameInfoList gameInfoList = new ObjectMapper().readValue(gameInfoListString, GameInfoList.class);
            if (gameInfoList == null) {
                logger.error("there is no data where  key is " + gameInfoListkey + "  in redis");
                return -6;
            }
            List<GameInfo> listGameInfo = gameInfoList.getGameInfoList();
            listGameInfo.add(gameinfo);
            gameInfoList.setGameInfoList(listGameInfo);
            boolean re2 = redisDaoImpl.redisSet(gameInfoListkey, new ObjectMapper().writeValueAsString(listGameInfo));
            if (!re2) {
                logger.error("gameInfoList  update redis error:" + re);
                return -7;
            }

            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -1;
        }
    }

    /**
     * 查询缓存中的游戏信息的所有数据
     *
     * @return 游戏信息数据集合
     */
     public List<GameInfo> getGameInfoListFrmCache() {
        List<GameInfo> listGameInfo ;
        try {
            //修改key为gameInfoList:0的缓存信息
            String gameInfoListkey = RedisKeysUtil.getGameInfoListKey();
            String gameInfoListString = redisDaoImpl.redisLoad(gameInfoListkey);
            if (gameInfoListString == null || gameInfoListString.isEmpty()) {
                logger.error("there is no data where  key is " + gameInfoListkey + "  in redis");
                return Collections.emptyList();
            }
            GameInfoList gameInfoList = new ObjectMapper().readValue(gameInfoListString, GameInfoList.class);
            if (gameInfoList == null) {
                logger.error("there is no data where  key is " + gameInfoListkey + "  in redis");
                return Collections.emptyList();
            }
            listGameInfo = gameInfoList.getGameInfoList();
            return listGameInfo;
        } catch (java.io.IOException e) {
            logger.error("", e);
            return Collections.emptyList();
        }
//        public List<GameInfo> getGameInfoListFrmCache() {
//        List<GameInfo> listGameInfo = new ArrayList<GameInfo>();
//        try {
//           // String gameInfokey = RedisKeysUtil.getGameInfoKey(0);
//            String keys =RedisKeysUtil.getGameInfoKey(0) + "*";
//           // List<String> list = redisDaoImpl.getAllValues(keys);
//            Set set = redisDaoImpl.getKeys(keys);
//            Iterator it = set.iterator();
//            while (it.hasNext()) {
//                String key = (String) it.next();
//                logger.error(" key :"+key);
//                String gameInfo = redisDaoImpl.redisLoad(key);
//                if (gameInfo == null || gameInfo.trim().length() == 0) {
//                    logger.error("there is no key " + key + " in redis");
//                    continue;
//                }
//                GameInfo gi = new ObjectMapper().readValue(gameInfo, GameInfo.class);
//                listGameInfo.add(gi);
//            }
//            return listGameInfo;
//        } catch (Exception e) {
//            logger.error("", e);
//            return Collections.emptyList();
//        }
//    }
    }
}
