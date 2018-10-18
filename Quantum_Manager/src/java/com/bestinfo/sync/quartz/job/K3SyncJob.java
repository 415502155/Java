package com.bestinfo.sync.quartz.job;

import com.bestinfo.define.Draw.KDrawProStatus;
import com.bestinfo.bean.game.GameDrawInfo;
import com.bestinfo.bean.game.GameInfo;
import com.bestinfo.bean.game.GameKDrawInfo;
import com.bestinfo.dao.game.IGameDrawInfoDao;
import com.bestinfo.dao.game.IGameInfoDao;
import com.bestinfo.dao.game.IGameKDrawInfoDao;
import com.bestinfo.define.system.WorkStatusDefine;
import com.bestinfo.sync.quartz.SyncInfo;
import com.bestinfo.sync.quartz.thread.LuckyNoSyncThread;
import com.bestinfo.sync.quartz.thread.OpenPrizeSyncThread;
import com.bestinfo.redis.gamedraw.GameKDrawInfoRedis;
import com.bestinfo.redis.stat.LuckyNoRedis;
import com.bestinfo.redis.stat.OpenPrizeRedis;
import com.bestinfo.util.TimeUtil;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class K3SyncJob {

    private final Logger logger = Logger.getLogger(K3SyncJob.class);

    @Resource
    private IGameInfoDao gameInfoDao;

    @Resource
    private IGameDrawInfoDao drawInfoDao;

    @Resource
    private IGameKDrawInfoDao kdrawInfoDao;

    @Resource
    private JdbcTemplate metaJdbcTemplate;

    @Resource
    private GameKDrawInfoRedis kDrawInfoRedis;

    @Resource
    private LuckyNoRedis luckyNoRedis;

    @Resource
    private OpenPrizeRedis openPrizeRedis;

    public synchronized void masterThread() {
        logger.warn("******************** K3 sync job start ********************");
        logger.warn("game_code:" + SyncInfo.K3);

        GameInfo gameInfo = gameInfoDao.getGameInfoByGameCode(metaJdbcTemplate, SyncInfo.K3);
        if (gameInfo == null) {
            logger.error("^^^^^ GameInfo is null,game_code:" + SyncInfo.K3 + " ^^^^^");
            return;
        }
        //判断游戏是否可用
        if (gameInfo.getUsed_mark() != WorkStatusDefine.work) {
            logger.warn("^^^^^ GameInfo is unavailable,game_code:" + SyncInfo.K3 + " ^^^^^");
            return;
        }

        int game_id = gameInfo.getGame_id();
        //当天日期
        String drawName = TimeUtil.formatDate_YMD8(new Date());
        logger.warn("begin creating K3 sync file,today:" + drawName);

        //获取当天的大期
        GameDrawInfo drawInfo = drawInfoDao.getDrawByGameIdAndDrawName(metaJdbcTemplate, game_id, drawName);
        if (drawInfo == null) {//当天未排期
            logger.error("^^^^^ current days draw info is null. ^^^^^");
            return;
        }

        //获取所有的keno期
        List<GameKDrawInfo> kdrawList = kdrawInfoDao.getKDrawList(metaJdbcTemplate, game_id, drawInfo.getDraw_id());
        while (true) {
            if (kdrawList == null || kdrawList.isEmpty()) {
                logger.warn("all keno draw have started K3 sync thread");
                break;
            }
            //获取keno期列表里第一个keno期，按kneo_draw_id升序
            GameKDrawInfo currentKDraw = kdrawList.get(0);

            //查看当前keno期是否已经完成开奖，如果已经完成开奖，直接进行下一期次的处理
            if (currentKDraw.getKeno_process_status() >= KDrawProStatus.GETMONEY) {
                logger.warn("current kdraw has already finished lucky,game_id:" + game_id
                        + ",draw_id:" + drawInfo.getDraw_id() + ",keno_draw_id:" + currentKDraw.getKeno_draw_id());
                kdrawList.remove(0);
                continue;
            }

            //查看当前keno期是否到了销售结束时间
            long sub_time = currentKDraw.getSales_end().getTime() - new Date().getTime();//当前时间到销售结束时间的毫秒值
            if (sub_time > 0) {
                logger.warn("sales is not over,game_id:" + game_id + ",draw_id:"
                        + drawInfo.getDraw_id() + ",keno_draw_id:" + currentKDraw.getKeno_draw_id()
                        + ",sales_end:" + TimeUtil.formatDate_time(currentKDraw.getSales_end()));
                try {
                    logger.warn("sales is not over,K3 quartz needs to sleep:" + sub_time + "ms");
                    Thread.sleep(sub_time);//休眠到销售时间结束
                } catch (InterruptedException ex) {
                    logger.error("", ex);
                }
                continue;
            }

            //开启生成开奖号码线程
            new LuckyNoSyncThread(gameInfo.getGame_id(), currentKDraw.getDraw_id(), currentKDraw.getKeno_draw_id(), drawName,
                    kDrawInfoRedis, luckyNoRedis).start();
            //开启生成开奖公告线程
            new OpenPrizeSyncThread(gameInfo.getGame_id(), currentKDraw.getDraw_id(), currentKDraw.getKeno_draw_id(),drawName,
                    kDrawInfoRedis, openPrizeRedis).start();

            //把当前期次移除keno期列表
            kdrawList.remove(0);
        }
        logger.warn("******************** K3 sync job complete ********************");
    }
}
