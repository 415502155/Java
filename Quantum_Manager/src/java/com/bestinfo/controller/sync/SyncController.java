package com.bestinfo.controller.sync;

import com.bestinfo.bean.game.GameKDrawInfo;
import com.bestinfo.bean.game.OpenprizeInfo;
import com.bestinfo.bean.stat.LuckyNo;
import com.bestinfo.define.Draw.KDrawProStatus;
import com.bestinfo.define.filepath.FilePath;
import com.bestinfo.redis.gamedraw.GameKDrawInfoRedis;
import com.bestinfo.redis.stat.LuckyNoRedis;
import com.bestinfo.redis.stat.OpenPrizeRedis;
import com.bestinfo.util.FileUtil;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/sync")
public class SyncController {

    private final Logger logger = Logger.getLogger(SyncController.class);

    @Resource
    private GameKDrawInfoRedis kDrawInfoRedis;

    @Resource
    private LuckyNoRedis luckyNoRedis;

    @Resource
    private OpenPrizeRedis openPrizeRedis;

    //eb界面手动生成开奖号码
    @RequestMapping(value = "/luckyno")
    @ResponseBody
    public Map<String, Object> createLuckyNo(HttpServletRequest request) {
        logger.info("manual create sync luckyno file");
        Map<String, Object> resMap = new HashMap<String, Object>();
        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
        int keno_draw_id = ServletRequestUtils.getIntParameter(request, "keno_draw_id", 0);
        if (game_id <= 0 || draw_id <= 0 || keno_draw_id <= 0) {
            logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id + ",keno_draw_id:" + keno_draw_id);
            resMap.put("code", "-1");
            resMap.put("msg", "eb数据错误！");
            return resMap;
        }

        try {
            GameKDrawInfo kDrawInfo = kDrawInfoRedis.getGameKDrawInfoFromCache(game_id, draw_id, keno_draw_id);
            if (kDrawInfo == null) {
                logger.error("kdrawinfo is null,gameid:" + game_id + ",drawid:" + draw_id + ",keno_draw_id:" + keno_draw_id);
                resMap.put("code", "-2");
                resMap.put("msg", "没有期信息！");
                return resMap;
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
                    resMap.put("code", "-3");
                    resMap.put("msg", "没有开奖号码信息！");
                    return resMap;
                }

                int re = FileUtil.makeNewFile(FilePath.createKenoLuckyFileName(game_id, kDrawInfo.getKeno_draw_name().substring(0, 8),
                        kDrawInfo.getKeno_draw_name()), new ObjectMapper().writeValueAsString(luckyNo));
                if (re < 0) {
                    logger.error("write luckyno into file error,game_id:" + game_id + ",draw_id:" + draw_id
                            + ",keno_draw_id:" + keno_draw_id);
                    resMap.put("code", "-4");
                    resMap.put("msg", "生成号码文件失败！");
                    return resMap;
                }

                logger.warn("write luckyno into file success,game_id:" + game_id + ",draw_id:" + draw_id
                        + ",keno_draw_id:" + keno_draw_id);

                resMap.put("code", "0");
                resMap.put("msg", "生成号码文件成功！");
                return resMap;
            } else {
                resMap.put("code", "-5");
                resMap.put("msg", "自动开奖还未生成开奖号码！");
                return resMap;
            }
        } catch (Exception e) {
            logger.error("", e);
        }

        return resMap;
    }

    //eb界面手动生成开奖公告
    @RequestMapping(value = "/prize")
    @ResponseBody
    public Map<String, Object> createPrize(HttpServletRequest request) {
        logger.info("manual create sync prize file");
        Map<String, Object> resMap = new HashMap<String, Object>();
        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
        int keno_draw_id = ServletRequestUtils.getIntParameter(request, "keno_draw_id", 0);
        if (game_id <= 0 || draw_id <= 0 || keno_draw_id <= 0) {
            logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id + ",keno_draw_id:" + keno_draw_id);
            resMap.put("code", "-1");
            resMap.put("msg", "eb数据错误！");
            return resMap;
        }

        try {
            GameKDrawInfo kDrawInfo = kDrawInfoRedis.getGameKDrawInfoFromCache(game_id, draw_id, keno_draw_id);
            if (kDrawInfo == null) {
                logger.error("kdrawinfo is null,gameid:" + game_id + ",drawid:" + draw_id + ",keno_draw_id:" + keno_draw_id);
                resMap.put("code", "-2");
                resMap.put("msg", "没有期信息！");
                return resMap;
            }

            int processStatus = kDrawInfo.getKeno_process_status();
            logger.info("current kdraw status:" + processStatus + ",game_id:" + game_id + ",draw_id:" + draw_id
                    + ",keno_draw_id:" + keno_draw_id);

            //判断快开期状态是否已经生成开奖号码
            if (processStatus > KDrawProStatus.ANNOUNCEMENT) {
                //生成文件
                OpenprizeInfo openPrize = openPrizeRedis.getOpenprizeInfoByIds(game_id, draw_id, keno_draw_id);
                if (openPrize == null) {
                    logger.error("openprize from redis is null,game_id:" + game_id + ",draw_id:" + draw_id
                            + ",keno_draw_id:" + keno_draw_id);
                    resMap.put("code", "-3");
                    resMap.put("msg", "redis没有开奖公告信息！");
                    return resMap;
                }

                int re = FileUtil.makeNewFile(FilePath.createKenoBulletinFileName(game_id, kDrawInfo.getKeno_draw_name().substring(0, 8),
                        kDrawInfo.getKeno_draw_name()), new ObjectMapper().writeValueAsString(openPrize));
                if (re < 0) {
                    logger.error("write openprize into file error,game_id:" + game_id + ",draw_id:" + draw_id
                            + ",keno_draw_id:" + keno_draw_id);
                    resMap.put("code", "-4");
                    resMap.put("msg", "生成开奖公告文件失败！");
                    return resMap;
                }

                logger.warn("write openprize into file success,game_id:" + game_id + ",draw_id:" + draw_id
                        + ",keno_draw_id:" + keno_draw_id);

                resMap.put("code", "0");
                resMap.put("msg", "生成开奖公告文件成功！");
                return resMap;
            } else {
                resMap.put("code", "-5");
                resMap.put("msg", "自动开奖还未生成开奖公告！");
                return resMap;
            }
        } catch (Exception e) {
            logger.error("", e);
        }

        return resMap;
    }

}
