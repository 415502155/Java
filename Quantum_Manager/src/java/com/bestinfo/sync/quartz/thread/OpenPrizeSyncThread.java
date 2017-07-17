package com.bestinfo.sync.quartz.thread;

import com.bestinfo.define.Draw.KDrawProStatus;
import com.bestinfo.bean.game.GameKDrawInfo;
import com.bestinfo.bean.game.OpenprizeInfo;
import com.bestinfo.define.filepath.FilePath;
import com.bestinfo.redis.gamedraw.GameKDrawInfoRedis;
import com.bestinfo.redis.stat.OpenPrizeRedis;
import com.bestinfo.sync.quartz.SyncInfo;
import com.bestinfo.util.FileUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

public class OpenPrizeSyncThread extends Thread {

    private final Logger logger = Logger.getLogger(OpenPrizeSyncThread.class);

    private final int game_id;

    private final int draw_id;

    private final int keno_draw_id;
    private final String drawName;

    private final GameKDrawInfoRedis kDrawInfoRedis;

    private final OpenPrizeRedis openPrizeRedis;

    public OpenPrizeSyncThread(int game_id, int draw_id, int keno_draw_id, String drawName,
            GameKDrawInfoRedis kDrawInfoRedis, OpenPrizeRedis openPrizeRedis) {
        this.game_id = game_id;
        this.draw_id = draw_id;
        this.keno_draw_id = keno_draw_id;
        this.kDrawInfoRedis = kDrawInfoRedis;
        this.openPrizeRedis = openPrizeRedis;
        this.drawName = drawName;
    }

    @Override
    public void run() {
        int queryCount = 1;
        logger.warn("------openprize thread starts running,gameid:" + game_id + ",drawid:" + draw_id + ",keno_draw_id:" + keno_draw_id + "------");
        try {
            while (true) {
                if (queryCount > SyncInfo.MAX_QUERY_COUNT) {
                    logger.error("openprize thread alerady query " + SyncInfo.MAX_QUERY_COUNT + "times");
                    break;
                }
                queryCount++;

                //获取快开期次状态
                GameKDrawInfo kDrawInfo = kDrawInfoRedis.getGameKDrawInfoFromCache(game_id, draw_id, keno_draw_id);
                if (kDrawInfo == null) {
                    logger.error("kdrawinfo is null,gameid:" + game_id + ",drawid:" + draw_id + ",keno_draw_id:" + keno_draw_id);
                    break;
                }
                int processStatus = kDrawInfo.getKeno_process_status();
                logger.info("current kdraw status:" + processStatus + ",game_id:" + game_id + ",draw_id:" + draw_id
                        + ",keno_draw_id:" + keno_draw_id);

                //判断快开期状态是否已经生成开奖公告
                if (processStatus > KDrawProStatus.ANNOUNCEMENT) {
                    //生成文件
                    OpenprizeInfo openPrize = openPrizeRedis.getOpenprizeInfoByIds(game_id, draw_id, keno_draw_id);
                    if (openPrize == null) {
                        logger.error("openprize from redis is null,game_id:" + game_id + ",draw_id:" + draw_id
                                + ",keno_draw_id:" + keno_draw_id);
                        break;
                    }

                    int re = FileUtil.makeNewFile(FilePath.createKenoBulletinFileName(game_id, drawName, kDrawInfo.getKeno_draw_name()),
                            new ObjectMapper().writeValueAsString(openPrize));
                    if (re < 0) {
                        logger.error("write openprize into file error,game_id:" + game_id + ",draw_id:" + draw_id
                                + ",keno_draw_id:" + keno_draw_id);
                        break;
                    }

                    logger.warn("write openprize into file success,game_id:" + game_id + ",draw_id:" + draw_id
                            + ",keno_draw_id:" + keno_draw_id);
                    break;
                }

                //每次查询休眠时间
                Thread.sleep(SyncInfo.SLEEP_TIME);
            }
        } catch (Exception e) {
            logger.error("", e);
        }

        logger.warn("------openprize thread running complete,gameid:" + game_id + ",drawid:" + draw_id + ",keno_draw_id:" + keno_draw_id + "------");
    }

}
