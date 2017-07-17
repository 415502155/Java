package com.bestinfo.controller.gambler;

import com.bestinfo.bean.game.GameAutoCash;
import com.bestinfo.bean.game.GameDrawInfo;
import com.bestinfo.dao.game.IGameAutoCashDao;
import com.bestinfo.dao.game.IGameDrawInfoDao;
import com.bestinfo.define.Draw.DrawProStatus;
import com.bestinfo.service.gambler.IGameAutoCashService;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 电话投注自动兑奖
 */
@Controller
@RequestMapping(value = "/gambler")
public class AutoCashController {

    private static final Logger logger = Logger.getLogger(AutoCashController.class);

    @Resource
    private IGameAutoCashService autoCashService;

    @Resource
    private IGameAutoCashDao autoCashDao;

    @Resource
    private JdbcTemplate metaJdbcTemplate;

    @Resource
    private IGameDrawInfoDao drawInfoDao;

    @RequestMapping(value = "/cash")
    @ResponseBody
    public Map<String, Object> automaticCash(HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int gameId = ServletRequestUtils.getIntParameter(request, "gameID", 0);
        int drawId = ServletRequestUtils.getIntParameter(request, "drawID", 0);
        int kenoId = ServletRequestUtils.getIntParameter(request, "kenoID", 0);
        logger.info("gambler auto cash,game_id:" + gameId + ",draw_id:" + drawId);

        GameDrawInfo drawInfo = drawInfoDao.getDrawByGameIdAndDrawId(metaJdbcTemplate, gameId, drawId);
        if (drawInfo == null) {
            logger.error("GameDrawInfo is null,game_id:" + gameId + ",draw_id:" + drawId);
            resMap.put("code", -2);
            resMap.put("msg", "没有期数据！");
            return resMap;
        }
        if (drawInfo.getProcess_status() != DrawProStatus.GETMONEY) {
            logger.error("GameDrawInfo status in not 500,game_id:" + gameId + ",draw_id:" + drawId
                    + ",draw status:" + drawInfo.getProcess_status());
            resMap.put("code", -3);
            resMap.put("msg", "期状态不是兑奖状态！");
            return resMap;
        }

        GameAutoCash autoCash = autoCashDao.getCashAuto(metaJdbcTemplate, gameId, drawId, kenoId);
        if (autoCash == null) {
            logger.error("GameAutoCash is null in db,game_id" + gameId + ",draw_id:" + drawId);
            resMap.put("code", -4);
            resMap.put("msg", "没有自动兑奖期数据！");
            return resMap;
        }
        if (autoCash.getComplete_status() == 1) {
            logger.error("GameAutoCash already cashed,game_id:" + gameId + ",draw_id:" + drawId);
            resMap.put("code", -5);
            resMap.put("msg", "已经进行过自动兑奖操作！");
            return resMap;
        }
        int re = autoCashService.autoCash(gameId, drawId, kenoId);
        if (re < 0) {
            logger.error("gambler auto cash error,game_id:" + gameId + ",draw_id:" + drawId);
            resMap.put("code", -1);
            resMap.put("msg", "电话投注自动兑奖失败！");
        } else {
            resMap.put("code", 0);
            resMap.put("msg", "电话投注自动兑奖成功！");
        }
        return resMap;
    }

}
