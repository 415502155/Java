package com.bestinfo.sync.quartz.job;

import com.alibaba.fastjson.JSON;
import com.bestinfo.bean.game.GameAutoCash;
import com.bestinfo.dao.game.IGameAutoCashDao;
import com.bestinfo.service.gambler.IFileMakeService;
import com.bestinfo.service.gambler.IGameAutoCashService;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AutoCashJob {

    private static final Logger logger = Logger.getLogger(AutoCashJob.class);

    @Resource
    private JdbcTemplate metaJdbcTemplate;

    @Resource
    private IGameAutoCashService autoCashService;

    @Resource
    private IFileMakeService gameblerFileService;

    @Resource
    private IGameAutoCashDao autoCashDao;

    public synchronized void masterThread() {
        logger.warn("******************** gambler auto cash job start ********************");

        try {
            List<GameAutoCash> autoCashList = autoCashDao.getUnFinishedCashAuto(metaJdbcTemplate);
            logger.info("autoCashList size:" + autoCashList.size());
            int i = 1;
            for (GameAutoCash autoCash : autoCashList) {
                logger.info("current size:" + i++ + ",current GameAutoCash:" + JSON.toJSONString(autoCash));
                //自动兑奖
                int re = autoCashService.autoCash(autoCash.getGame_id(), autoCash.getDraw_id(), autoCash.getKeno_draw_id());
                if (re < 0) {
                    logger.error("gambler auto cash error,autoCash:" + JSON.toJSONString(autoCash));
                    continue;
                }
                //生成对账文件
                re = gameblerFileService.accountFile(autoCash.getGame_id(), autoCash.getDraw_id(), "");
                if (re < 0) {
                    logger.error("gambler account file error,autoCash:" + JSON.toJSONString(autoCash));
                }
                //生成中奖文件
                re = gameblerFileService.winFile(autoCash.getGame_id(), autoCash.getDraw_id(), "");
                if (re < 0) {
                    logger.error("gambler win file error,autoCash:" + JSON.toJSONString(autoCash));
                }
            }
        } catch (Exception ex) {
            logger.error("gambler auto cash quartz exception:" + ex);
        }

        logger.warn("******************** gambler auto cash job complete ********************");
    }
}
