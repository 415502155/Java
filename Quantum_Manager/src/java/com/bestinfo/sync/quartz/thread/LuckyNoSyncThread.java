package com.bestinfo.sync.quartz.thread;

import com.bestinfo.define.Draw.KDrawProStatus;
import com.bestinfo.bean.game.GameKDrawInfo;
import com.bestinfo.bean.stat.LuckyNo;
import com.bestinfo.define.filepath.FilePath;
import com.bestinfo.redis.gamedraw.GameKDrawInfoRedis;
import com.bestinfo.redis.stat.LuckyNoRedis;
import com.bestinfo.sync.quartz.SyncInfo;
import com.bestinfo.util.FileUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

public class LuckyNoSyncThread extends Thread {

    private final Logger logger = Logger.getLogger(LuckyNoSyncThread.class);

    private final int game_id;

    private final int draw_id;

    private final int keno_draw_id;

    private final String drawName;

    private final GameKDrawInfoRedis kDrawInfoRedis;

    private final LuckyNoRedis luckyNoRedis;

    public LuckyNoSyncThread(int game_id, int draw_id, int keno_draw_id, String drawName,
            GameKDrawInfoRedis kDrawInfoRedis, LuckyNoRedis luckyNoRedis) {
        this.game_id = game_id;
        this.draw_id = draw_id;
        this.keno_draw_id = keno_draw_id;
        this.kDrawInfoRedis = kDrawInfoRedis;
        this.luckyNoRedis = luckyNoRedis;
        this.drawName = drawName;
    }

    @Override
    public void run() {
        int queryCount = 1;
        logger.warn("------luckyno thread starts running,gameid:" + game_id + ",drawid:" + draw_id + ",keno_draw_id:" + keno_draw_id + "------");
        try {
            while (true) {
                if (queryCount > SyncInfo.MAX_QUERY_COUNT) {
                    logger.error("luckyno thread alerady query " + SyncInfo.MAX_QUERY_COUNT + "times");
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

                //判断快开期状态是否已经生成开奖号码
                if (processStatus >= KDrawProStatus.STOPSELL && processStatus != KDrawProStatus.ELECTRONNUMBER) {
                    //生成文件
                    LuckyNo luckyNo = luckyNoRedis.getLuckyNoByIds(game_id, draw_id, keno_draw_id, 1);
                    if (luckyNo == null) {
                        logger.error("luckyno from redis is null,game_id:" + game_id + ",draw_id:" + draw_id
                                + ",keno_draw_id:" + keno_draw_id);
                        break;
                    }
                    
                    int re = FileUtil.makeNewFile(FilePath.createKenoLuckyFileName(game_id, drawName, kDrawInfo.getKeno_draw_name()),
                            new ObjectMapper().writeValueAsString(luckyNo));
                    if (re < 0) {
                        logger.error("write luckyno into file error,game_id:" + game_id + ",draw_id:" + draw_id
                                + ",keno_draw_id:" + keno_draw_id);
                        break;
                    }
                  
//                    //追加到汇总文件
//                    LuckyNoK3 luckyNoK3 = new LuckyNoK3();
//                    luckyNoK3.setGame_id(luckyNo.getGame_id());
//                    luckyNoK3.setDraw_id(luckyNo.getDraw_id());
//                    luckyNoK3.setDraw_name(luckyNo.getDraw_name());
//                    luckyNoK3.setKeno_draw_id(luckyNo.getKeno_draw_id());
//                    luckyNoK3.setKeno_draw_name(luckyNo.getKeno_draw_name());
//                    luckyNoK3.setOpen_id(luckyNo.getOpen_id());
//                    luckyNoK3.setLucky_no(luckyNo.getLucky_no().replaceAll("\\s", ","));
//                    luckyNoK3.setLucky_no_echo(luckyNo.getLucky_no_echo());
//                    luckyNoK3.setPrize_no_num(luckyNo.getPrize_no_num());
//                    luckyNoK3.setPrize_no(luckyNo.getPrize_no());
//                    luckyNoK3.setSpecial_no_num(luckyNo.getSpecial_no_num());
//                    luckyNoK3.setSpecial_no(luckyNo.getSpecial_no());
//                    luckyNoK3.setBlue_no(luckyNo.getBlue_no());
//                    luckyNoK3.setBlue_no_num(luckyNo.getBlue_no_num());
//                    luckyNoK3.setGenerate_time(luckyNo.getGenerate_time());
//                    luckyNoK3.setSales_begin(kDrawInfo.getSales_begin());
//                    luckyNoK3.setSales_end(kDrawInfo.getSales_end());
//                    int result = FileUtil.fileAppend(FilePath.createKenoLuckyFileName(game_id, drawName, kDrawInfo.getGame_id()+kDrawInfo.getKeno_draw_name().substring(0, 8))+".txt",
//                            new ObjectMapper().writeValueAsString(luckyNoK3).replaceAll(":null", ":\"\""));
//                    if (result < 0) {
//                        logger.error("add write luckynok3 into file error,game_id:" + game_id + ",draw_id:" + draw_id
//                                + ",keno_draw_id:" + keno_draw_id);
//                        break;
//                    }
                    logger.warn("write luckyno into file success,game_id:" + game_id + ",draw_id:" + draw_id
                            + ",keno_draw_id:" + keno_draw_id);
                    break;
                }

                //每次查询休眠时间
                Thread.sleep(SyncInfo.SLEEP_TIME);
            }
        } catch (Exception e) {
            logger.error("", e);
        }

        logger.warn("------luckyno thread running complete,gameid:" + game_id + ",drawid:" + draw_id + ",keno_draw_id:" + keno_draw_id + "------");
    }

}
