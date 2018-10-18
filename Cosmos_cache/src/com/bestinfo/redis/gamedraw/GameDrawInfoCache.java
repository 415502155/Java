package com.bestinfo.redis.gamedraw;

import com.bestinfo.define.Draw.DrawProStatus;
import com.bestinfo.bean.game.GameDrawInfo;
import com.bestinfo.bean.game.GameKDrawInfo;
import com.bestinfo.cache.keys.RedisKeysUtil;
import com.bestinfo.dao.impl.game.GameDrawInfoDaoImpl;
import com.bestinfo.dao.impl.game.GameKDrawInfoDaoImpl;
import com.bestinfo.redis.dao.impl.RedisDaoImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hhhh
 */
@Repository
public class GameDrawInfoCache {

    @Resource
    private RedisDaoImpl redisDaoImpl;

    @Resource
    private JdbcTemplate metaJdbcTemplate;

    @Resource
    private GameDrawInfoDaoImpl gameDrawInfoDaoImpl;

    @Resource
    private GameKDrawInfoDaoImpl gameKDrawInfoDaoImpl;

    @Resource
    private GameKDrawInfoRedis gameKDrawInfoRedis;

    private final Logger logger = Logger.getLogger(GameDrawInfoCache.class);

    /**
     * 往缓存中添加期信息<br>
     * 保存两份数据，一份以draw_id为key，一份以draw_name为key
     *
     * @param gameDrawInfo
     * @return 0-成功 -1（gameDrawInfo失败） -2（失败）
     */
    public int addGameDrawInfoIntoCache(GameDrawInfo gameDrawInfo) {
        String drawIdKey = RedisKeysUtil.getGameDrawKey(gameDrawInfo.getGame_id(), gameDrawInfo.getDraw_id(), 0);
        String drawNameKey = RedisKeysUtil.getGameDrawKeyByDrawName(gameDrawInfo.getGame_id(), gameDrawInfo.getDraw_name(), "");
        try {
            String redisValue = new ObjectMapper().writeValueAsString(gameDrawInfo);
            //以draw_id为key保存期信息
            boolean re = redisDaoImpl.redisSet(drawIdKey, redisValue);
            if (!re) {
                logger.error("gamedraw info into redis error:" + re + ",drawIdKey:" + drawIdKey);
                return -1;
            }
            //以draw_name为key保存期信息
            re = redisDaoImpl.redisSet(drawNameKey, redisValue);
            if (!re) {
                logger.error("gamedraw info into redis error:" + re + ",drawNameKey" + drawNameKey);
                return -2;
            }

            return 0;
        } catch (IOException ex) {
            logger.error("", ex);
            return -3;
        }
    }

    /**
     * 从缓存中查询指定游戏指定期的期信息
     *
     * @param gameid
     * @param drawid
     * @return
     */
    public GameDrawInfo getGameDrawInfoFromCache(int gameid, int drawid) {
        try {
            String drawIdKey = RedisKeysUtil.getGameDrawKey(gameid, drawid, 0);
            String gameDraw = redisDaoImpl.redisLoad(drawIdKey);
            if (gameDraw == null || gameDraw.trim().length() == 0) {
                logger.error("there is no game draw info in Redis where gameid = " + gameid + " and drawid = " + drawid);
                return null;
            }
            return new ObjectMapper().readValue(gameDraw, GameDrawInfo.class);
        } catch (IOException e) {
            logger.error("there is no game draw info in Redis where gameid = " + gameid + " and drawid = " + drawid, e);
            return null;
        }
    }

    /**
     * 根据draw_name从缓存中查询指定游戏指定期的期信息
     *
     * @param gameid
     * @param drawName
     * @return
     */
    public GameDrawInfo getGameDrawInfoByDrawName(int gameid, String drawName) {
        try {
            String drawNameKey = RedisKeysUtil.getGameDrawKeyByDrawName(gameid, drawName, "");
            String gameDraw = redisDaoImpl.redisLoad(drawNameKey);
            if (gameDraw == null || gameDraw.trim().length() == 0) {
                logger.error("there is no GameDrawInfo in Redis where gameid = " + gameid + " and drawName = " + drawName);
                return null;
            }
            return new ObjectMapper().readValue(gameDraw, GameDrawInfo.class);
        } catch (IOException e) {
            logger.error("there is no game draw info in Redis where gameid = " + gameid + " and drawName     = " + drawName, e);
            return null;
        }
    }

    /**
     * 更新缓存中的游戏期状态
     *
     * @param gameid
     * @param drawid
     * @param status
     * @return
     */
    public int updateDrawStatus(int gameid, int drawid, int status) {
        String drawIdKey = RedisKeysUtil.getGameDrawKey(gameid, drawid, 0);
        try {
            String oldRedisValue = redisDaoImpl.redisLoad(drawIdKey);
            if (oldRedisValue == null || oldRedisValue.trim().length() == 0) {
                logger.error("there is no key " + drawIdKey + " in redis");
                return -1;
            }
            GameDrawInfo drawInfo = new ObjectMapper().readValue(oldRedisValue, GameDrawInfo.class);
            drawInfo.setProcess_status(status);
            String newRedisValue = new ObjectMapper().writeValueAsString(drawInfo);

            //保存以draw_id为key的数据
            boolean re = redisDaoImpl.redisSet(drawIdKey, newRedisValue);
            if (!re) {
                logger.error("gamedraw info update redis error:" + re + ",drawIdKey:" + drawIdKey);
                return -2;
            }

            //保存以draw_name为key的数据
            String drawNameKey = RedisKeysUtil.getGameDrawKeyByDrawName(gameid, drawInfo.getDraw_name(), "");
            re = redisDaoImpl.redisSet(drawNameKey, newRedisValue);
            if (!re) {
                logger.error("gamedraw info update redis error:" + re + ",drawNameKey:" + drawNameKey);
                return -3;
            }

            return 0;
        } catch (IOException ex) {
            logger.error("", ex);
            return -4;
        }
    }

    /**
     * 将库中的期信息同步到Redis（期状态>=预售期 and 期状态<=兑奖结算）
     *
     * @return
     */
    public int GameDrawInfoDataSyn() {
        List<GameDrawInfo> gameDrawInfoList = gameDrawInfoDaoImpl.synGameinfo(metaJdbcTemplate, DrawProStatus.PRESALE, DrawProStatus.GETMONEYSTA);
        if (gameDrawInfoList == null || gameDrawInfoList.isEmpty()) {
            logger.warn("there is no game draw info in DB where process_status between " + DrawProStatus.PRESALE + " and " + DrawProStatus.GETMONEYSTA);
            return 0;
        }
        int size = gameDrawInfoList.size();
        for (int i = 0; i < size; i++) {
            //先插入大期
            GameDrawInfo currentDrawInfo = gameDrawInfoList.get(i);
            int re = addGameDrawInfoIntoCache(currentDrawInfo);
            if (re < 0) {
                logger.error("the game draw info from DB into Redis fail");
                return -1;
            }

            //如果是快开期，插入所有快开期次
            if (currentDrawInfo.getKeno_draw_num() > 0) {
                List<GameKDrawInfo> kdrawList = gameKDrawInfoDaoImpl.getKDrawList(metaJdbcTemplate, currentDrawInfo.getGame_id(), currentDrawInfo.getDraw_id());
                if (kdrawList == null || kdrawList.isEmpty()) {
                    logger.error("the gameKdrawInfo in DB  is null where game_id = " + currentDrawInfo.getGame_id() + " and draw_id=" + currentDrawInfo.getDraw_id());
                    return -2;
                }
                re = gameKDrawInfoRedis.addGameKdrawListIntoCache(kdrawList);
                if (re < 0) {
                    logger.error("the game kdraw info from DB to Redis fail");
                    return -3;
                }
            }
        }
        return 0;
    }

    /**
     * 获取begin_draw和end_draw之间的普通游戏列表 此时keno_draw_id=0 draw_id是递增的
     *
     * @param game_id 游戏编号
     * @param begin_draw 开始期号
     * @param draw_num 期数
     * @return
     */
    public List<GameDrawInfo> getGameDrawInfoForDrawId(int game_id, int begin_draw, int draw_num) {

        List<GameDrawInfo> gkdiList = new ArrayList();
        String gameDrawKey;
        String gameDraw;
        draw_num = draw_num <= 0 ? 1 : draw_num;
        int end_draw = begin_draw + draw_num - 1;
        for (int i = begin_draw; i <= end_draw; i++) {
            try {
                gameDrawKey = RedisKeysUtil.getGameDrawKey(game_id, i, 0);
                if (gameDrawKey == null || gameDrawKey.trim().length() == 0 || "".equals(gameDrawKey)) {
                    continue;
                }
                gameDraw = redisDaoImpl.redisLoad(gameDrawKey);
                if (gameDraw == null || gameDraw.trim().length() == 0 || "".equals(gameDraw)) {
                    logger.warn("the gameDraw is null where gameDrawKey= " + gameDrawKey);
                    continue;
                }
                GameDrawInfo gameDrawInfo = new ObjectMapper().readValue(gameDraw, GameDrawInfo.class);
                if (gameDrawInfo == null) {
                    continue;
                }
                gkdiList.add(gameDrawInfo);
            } catch (IOException e) {
                logger.error("", e);
                return Collections.emptyList();
            }
        }
        return gkdiList;

    }
}
