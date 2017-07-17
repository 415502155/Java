package com.bestinfo.sync.quartz.job;

import com.bestinfo.dao.game.IGameDrawInfoDao;
import com.bestinfo.dao.game.IGameInfoDao;
import com.bestinfo.service.drawLucky.IDrawLuckyGambService;
import com.bestinfo.sync.quartz.SyncInfo;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class WstFileJob {

    private static final Logger logger = Logger.getLogger(WstFileJob.class);

    @Resource
    private IGameInfoDao gameInfoDao;

    @Resource
    private IGameDrawInfoDao drawInfoDao;

    @Resource
    private JdbcTemplate metaJdbcTemplate;

    @Resource
    private IDrawLuckyGambService drawLuckyService;

    public synchronized void masterThread() {
        logger.warn("******************** wst file job start ********************");
        logger.warn("game_code:" + SyncInfo.S4);

        //added by yfyang 20170511，无纸化数据接入系统，在开奖过程中生成无纸化的中奖文件（跟带fc的中奖文件内容一样）
        //此定时任务不再进行无纸化文件的读取生成操作
//        GameInfo gameInfo = gameInfoDao.getGameInfoByGameCode(metaJdbcTemplate, SyncInfo.S4);
//        if (gameInfo == null) {
//            logger.error("^^^^^ GameInfo is null,game_code:" + SyncInfo.S4 + " ^^^^^");
//            return;
//        }
//        //判断游戏是否可用
//        if (gameInfo.getUsed_mark() != WorkStatusDefine.work) {
//            logger.warn("^^^^^ GameInfo is unavailable,game_code:" + SyncInfo.S4 + " ^^^^^");
//            return;
//        }
//
//        int game_id = gameInfo.getGame_id();
//        //获取所有开奖结束的期次
//        List<GameDrawInfo> drawInfoList = drawInfoDao.getDrawByGameIdAndDrawStatus(metaJdbcTemplate, game_id, DrawProStatus.GETMONEY);
//        if (drawInfoList == null || drawInfoList.isEmpty()) {
//            logger.error("draw info is null,game_id:" + game_id + ",process_status:" + DrawProStatus.GETMONEY);
//            return;
//        }
//        //获取最新的开奖结束的期次
//        GameDrawInfo drawInfo = drawInfoList.get(drawInfoList.size() - 1);
//        String drawName = drawInfo.getDraw_name();
//        logger.info("wst file job,game_id:" + game_id + ",draw_id:" + drawInfo.getDraw_id()
//                + ",draw_name:" + drawName + ",system_id:" + SyncInfo.WST_SYSTEM_ID);
//
//        //读无纸化接入销售统计文件
//        logger.info("read wst sale stat file");
//        int re = drawLuckyService.loadSystemSaleStatFile(game_id, SyncInfo.WST_SYSTEM_ID, drawName);
//        if (re == 0) {
//            logger.info("read wst sale stat file success");
//        } else {
//            switch (re) {
//                case -1:
//                    logger.error("file name is null");
//                    break;
//                case -2:
//                    logger.error("get draw info error");
//                    break;
//                case -3:
//                    logger.error("file does not exist");
//                    break;
//                case -4:
//                    logger.error("drawName or systemId do not same with file content");
//                    break;
//                case -5:
//                    logger.error("insert into db error");
//                    break;
//                case -6:
//                    logger.error("read file error");
//                    break;
//                default:
//                    logger.error("unknown error");
//            }
//            return;
//        }
//
//        //读无纸化接入中奖统计文件
//        logger.info("read wst prize stat file");
//        re = drawLuckyService.loadSystemPrizeStatFile(game_id, SyncInfo.WST_SYSTEM_ID, drawName);
//        if (re == 0) {
//            logger.info("read wst prize stat file success");
//        } else {
//            switch (re) {
//                case -1:
//                    logger.error("file name is null");
//                    break;
//                case -2:
//                    logger.error("get draw info error");
//                    break;
//                case -3:
//                    logger.error("file does not exist");
//                    break;
//                case -4:
//                    logger.error("drawName or systemId do not same with file content");
//                    break;
//                case -5:
//                    logger.error("class info is null");
//                    break;
//                case -6:
//                    logger.error("insert into db error");
//                    break;
//                case -7:
//                    logger.error("read file error");
//                    break;
//                default:
//                    logger.error("unknown error");
//            }
//            return;
//        }
//
//        //生成无纸化中奖统计文件
//        logger.info("create wst total prize stat file");
//        re = drawLuckyService.CreatePlasPrizeStatFile(game_id, drawName, SyncInfo.WST_SYSTEM_ID);
//        if (re == 0) {
//            logger.info("create wst total prize stat file success");
//        } else {
//            switch (re) {
//                case -1:
//                    logger.error("get draw info error");
//                    break;
//                case -2:
//                    logger.error("get prize info from db error");
//                    break;
//                case -3:
//                    logger.error("get wst sale data from db error");
//                    break;
//                case -4:
//                    logger.error("get wst prize data from db error");
//                    break;
//                case -5:
//                    logger.error("create file error");
//                    break;
//                case -6:
//                    logger.error("write into file error");
//                    break;
//                case -7:
//                    logger.error("unknown error");
//                    break;
//                default:
//                    logger.error("unknown error");
//            }
//            return;
//        }
        logger.warn("******************** wst file job complete ********************");
    }
}
