package com.bestinfo.redis.gamedraw;

import com.bestinfo.bean.game.GameKDrawInfo;
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
 * @author hhhh
 */
@Repository
public class GameKDrawInfoRedis {

    @Resource
    private RedisDaoImpl redisDaoImpl;

    Logger logger = Logger.getLogger(GameKDrawInfoRedis.class);

    /**
     * 添加快开期信息到缓存中<br>
     * 保存两份数据，一份以draw_id为key，一份以draw_name为key
     *
     * @param kdrawInfo
     * @return 0成功 <0错误
     */
    public int addGameKdrawIntoCache(GameKDrawInfo kdrawInfo) {
        try {
            //得到大期名
//            String drawIdKey = RedisKeysUtil.getGameDrawKey(kdrawInfo.getGame_id(), kdrawInfo.getDraw_id(), 0);
//            String drawRedisValue = redisDaoImpl.redisLoad(drawIdKey);
//            if (drawRedisValue == null || drawRedisValue.trim().length() == 0) {
//                logger.error("there is no game draw info in Redis where gameid = " + kdrawInfo.getGame_id() + " and drawid = " + kdrawInfo.getDraw_id());
//                return -1;
//            }
//            GameDrawInfo drawInfo = new ObjectMapper().readValue(drawRedisValue, GameDrawInfo.class);

            String kdrawIdKey = RedisKeysUtil.getGameDrawKey(kdrawInfo.getGame_id(), kdrawInfo.getDraw_id(), kdrawInfo.getKeno_draw_id());
            //String kdrawNameKey = RedisKeysUtil.getGameDrawKeyByDrawName(kdrawInfo.getGame_id(), drawInfo.getDraw_name(), kdrawInfo.getKeno_draw_name());
            String kdrawNameKey = RedisKeysUtil.getGameKDrawKeyByKDrawName(kdrawInfo.getGame_id(), kdrawInfo.getKeno_draw_name());
            String kdrawRedisValue = new ObjectMapper().writeValueAsString(kdrawInfo);
            //以draw_id为key保存快开期信息
            boolean re = redisDaoImpl.redisSet(kdrawIdKey, kdrawRedisValue);
            if (!re) {
                logger.error("gamekdraw insert into redis error,kdrawIdKey:" + kdrawIdKey);
                return -2;
            }
            //以draw_name为key保存快开期信息
            re = redisDaoImpl.redisSet(kdrawNameKey, kdrawRedisValue);
            if (!re) {
                logger.error("gamekdraw insert into redis error,kdrawNameKey:" + kdrawNameKey);
                return -3;
            }

            return 0;
        } catch (IOException e) {
            logger.error("", e);
            return -4;
        }
    }

    /**
     * 更新缓存中的快开期状态
     *
     * @param gameid
     * @param drawid
     * @param keno_draw_id
     * @param status
     * @return
     */
    public int updateDrawStatus(int gameid, int drawid, int keno_draw_id, int status) {

        try {
            //得到大期名
//            String drawIdKey = RedisKeysUtil.getGameDrawKey(gameid, drawid, 0);
//            String drawRedisValue = redisDaoImpl.redisLoad(drawIdKey);
//            if (drawRedisValue == null || drawRedisValue.trim().length() == 0) {
//                logger.error("there is no game draw info in Redis where gameid = " + gameid + " and drawid = " + drawid);
//                return -1;
//            }
//            GameDrawInfo drawInfo = new ObjectMapper().readValue(drawRedisValue, GameDrawInfo.class);

            String kdrawIdKey = RedisKeysUtil.getGameDrawKey(gameid, drawid, keno_draw_id);
            String oldKDrawRedisValue = redisDaoImpl.redisLoad(kdrawIdKey);
            if (oldKDrawRedisValue == null || oldKDrawRedisValue.trim().length() == 0 || "".equals(oldKDrawRedisValue)) {
                logger.error("there is no key " + kdrawIdKey + " in redis");
                return -2;
            }
            GameKDrawInfo kdrawInfo = new ObjectMapper().readValue(oldKDrawRedisValue, GameKDrawInfo.class);
            if (kdrawInfo == null) {
                logger.error("the GameKDrawInfo is null where drawStr=" + oldKDrawRedisValue);
                return -3;
            }
            kdrawInfo.setKeno_process_status(status);

            ////以draw_id为key保存快开期信息
            String newKDrawRedisValue = new ObjectMapper().writeValueAsString(kdrawInfo);
            boolean re = redisDaoImpl.redisSet(kdrawIdKey, newKDrawRedisValue);
            if (!re) {
                logger.error("gamekdraw insert into redis error,kdrawIdKey:" + kdrawIdKey);
                return -4;
            }

          //  String kdrawNameKey = RedisKeysUtil.getGameDrawKeyByDrawName(gameid, drawInfo.getDraw_name(), kdrawInfo.getKeno_draw_name());
              String kdrawNameKey = RedisKeysUtil.getGameKDrawKeyByKDrawName(gameid, kdrawInfo.getKeno_draw_name());
            //以draw_name为key保存快开期信息
            re = redisDaoImpl.redisSet(kdrawNameKey, newKDrawRedisValue);
            if (!re) {
                logger.error("gamekdraw insert into redis error,kdrawNameKey:" + kdrawNameKey);
                return -5;
            }

            return 0;
        } catch (IOException ex) {
            logger.error("", ex);
            return -6;
        }
    }

    /**
     * 添加快开期列表中所有期信息到缓存中<br>
     * 保存两份数据，一份以draw_id为key，一份以draw_name为key
     *
     * @param kdrawList
     * @return 0成功 <0错误
     */
    public int addGameKdrawListIntoCache(List<GameKDrawInfo> kdrawList) {
        try {
            String drawIdKey = "";
            String drawRedisValue = "";
            String kdrawRedisValue = "";
            for (GameKDrawInfo currentKDraw : kdrawList) {
                //得到大期名
                drawIdKey = RedisKeysUtil.getGameDrawKey(currentKDraw.getGame_id(), currentKDraw.getDraw_id(), 0);
                drawRedisValue = redisDaoImpl.redisLoad(drawIdKey);
                if (drawRedisValue == null || drawRedisValue.trim().length() == 0 || "".equals(drawRedisValue)) {
                    logger.error("there is no game draw info in Redis where gameid = " + currentKDraw.getGame_id() + " and drawid = " + currentKDraw.getDraw_id());
                    return -1;
                }

                String kdrawIdKey = RedisKeysUtil.getGameDrawKey(currentKDraw.getGame_id(), currentKDraw.getDraw_id(), currentKDraw.getKeno_draw_id());
                //String kdrawNameKey = RedisKeysUtil.getGameDrawKeyByDrawName(currentKDraw.getGame_id(), new ObjectMapper().readValue(drawRedisValue, GameDrawInfo.class).getDraw_name(), currentKDraw.getKeno_draw_name());
                String kdrawNameKey = RedisKeysUtil.getGameKDrawKeyByKDrawName(currentKDraw.getGame_id(),  currentKDraw.getKeno_draw_name());
                kdrawRedisValue = new ObjectMapper().writeValueAsString(currentKDraw);
                //以draw_id为key保存快开期信息
                boolean re = redisDaoImpl.redisSet(kdrawIdKey, kdrawRedisValue);
                if (!re) {
                    logger.error("gamekdraw insert into redis error,kdrawIdKey:" + kdrawIdKey);
                    return -2;
                }
                //以draw_name为key保存快开期信息
                re = redisDaoImpl.redisSet(kdrawNameKey, kdrawRedisValue);
                if (!re) {
                    logger.error("gamekdraw insert into redis error,kdrawNameKey:" + kdrawNameKey);
                    return -3;
                }
            }

            return 0;
        } catch (IOException e) {
            logger.error("", e);
            return -4;
        }
    }

    /**
     * 根据gameId,drawId,kdrawId从缓存中取出快开期对象
     *
     * @param gameid
     * @param drawid
     * @param kdrawId
     * @return 快开期对象
     */
    public GameKDrawInfo getGameKDrawInfoFromCache(int gameid, int drawid, int kdrawId) {
        try {
            String kdrawIdKey = RedisKeysUtil.getGameDrawKey(gameid, drawid, kdrawId);
            String redisValue = redisDaoImpl.redisLoad(kdrawIdKey);
            if (redisValue == null || redisValue.trim().length() == 0 || "".equals(redisValue)) {
                logger.error("there is no GameKDrawInfo in redis,kdrawIdKey:" + kdrawIdKey);
                return null;
            }

            return new ObjectMapper().readValue(redisValue, GameKDrawInfo.class);
        } catch (IOException e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 根据draw_name从缓存中取出快开期对象
     *
     * @param gameid
     * @param drawName
     * @param kdrawName
     * @return 快开期对象
     */
    public GameKDrawInfo getGameKDrawInfoByDrawName(int gameid, String drawName, String kdrawName) {
        try {
          //  String kdrawNameKey = RedisKeysUtil.getGameDrawKeyByDrawName(gameid, drawName, kdrawName);
            String kdrawNameKey = RedisKeysUtil.getGameKDrawKeyByKDrawName(gameid, kdrawName);
            String redisValue = redisDaoImpl.redisLoad(kdrawNameKey);
            if (redisValue == null || redisValue.trim().length() == 0 || "".equals(redisValue)) {
                logger.error("there is no GameKDrawInfo in redis,kdrawNameKey:" + kdrawNameKey);
                return null;
            }

            return new ObjectMapper().readValue(redisValue, GameKDrawInfo.class);
        } catch (IOException e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 获取begin_draw和end_draw之间的快开游戏列表 此时 kdraw_id 是递增的
     *
     * @param game_id 游戏编号
     * @param begin_draw 期号（这里的begin_draw就是draw_id）
     * @param begin_keno_draw 开始快开期号
     * @param draw_num 期数
     * @return
     */
    public List<GameKDrawInfo> getGameKDrawInfoForKdId(int game_id, int begin_draw, int begin_keno_draw, int draw_num) {
        List<GameKDrawInfo> gkdiList = new ArrayList();
        draw_num = draw_num <= 0 ? 1 : draw_num;
        int end_draw = begin_keno_draw + draw_num - 1;
        for (int i = begin_keno_draw; i <= end_draw; i++) {
            try {
                String gameKDrawKey = RedisKeysUtil.getGameDrawKey(game_id, begin_draw, i);
                if (gameKDrawKey == null || "".equals(gameKDrawKey) || gameKDrawKey.trim().length() == 0) {
                    logger.error("the gameKDrawKey is null where game_id=" + game_id + " and begin_draw = " + begin_draw + "  keno_draw_id:" + i);
                    continue;
                }
                String gameKDraw = redisDaoImpl.redisLoad(gameKDrawKey);
                if (gameKDraw == null || "".equals(gameKDraw) || gameKDraw.trim().length() == 0) {  //YR add null
                    logger.error(" the gameKDraw is null where gameKDrawKey=" + gameKDrawKey);
                    continue;  //YR add
                }
                GameKDrawInfo gameKDrawInfo = new ObjectMapper().readValue(gameKDraw, GameKDrawInfo.class);
                if (gameKDrawInfo == null) {
                    logger.error("readValue is error");
                    continue;
                }
                gkdiList.add(gameKDrawInfo);
            } catch (Exception e) {  //YR modify  IoException 
                logger.error("", e);
                return Collections.emptyList();
            }
        }
        return gkdiList;
    }
}
