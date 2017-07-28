package com.bestinfo.quartz.job;

import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AutoCashJob {

    private static final Logger logger = Logger.getLogger(AutoCashJob.class);

//    @Resource
//    private JdbcTemplate metaJdbcTemplate;
//
//    @Resource
//    private IGameAutoCashService autoCashService;
//
//    @Resource
//    private IFileMakeService gameblerFileService;
//
//    @Resource
//    private IGameAutoCashDao autoCashDao;

    public synchronized void masterThread() {
        logger.warn("******************** gambler auto cash job start ********************");
        System.out.println("ding shi ren wu masterThread already go");
        logger.info("ding shi ren wu masterThread already go");
//        try {
//            //List<GameAutoCash> autoCashList = autoCashDao.getUnFinishedCashAuto(metaJdbcTemplate);
//            //logger.info("autoCashList size:" + autoCashList.size());
//            //int i = 1;
//            for (GameAutoCash autoCash : autoCashList) {
//                logger.info("current size:" + i++ + ",current GameAutoCash:" + new ObjectMapper().writeValueAsString(autoCash));
//                //自动兑奖
//                int re = autoCashService.autoCash(autoCash.getGame_id(), autoCash.getDraw_id(), autoCash.getKeno_draw_id());
//                if (re < 0) {
//                    logger.error("gambler auto cash error,autoCash:" + new ObjectMapper().writeValueAsString(autoCash));
//                    continue;
//                }
//                //生成对账文件
//                re = gameblerFileService.accountFile(autoCash.getGame_id(), autoCash.getDraw_id(), "");
//                if (re < 0) {
//                    logger.error("gambler account file error,autoCash:" + new ObjectMapper().writeValueAsString(autoCash));
//                }
//                //生成中奖文件
//                re = gameblerFileService.winFile(autoCash.getGame_id(), autoCash.getDraw_id(), "");
//                if (re < 0) {
//                    logger.error("gambler win file error,autoCash:" + new ObjectMapper().writeValueAsString(autoCash));
//                }
//            }
//        } catch (Exception ex) {
//            logger.error("gambler auto cash quartz exception:" + ex);
//        }

        logger.warn("******************** gambler auto cash job complete ********************");
    }
}
