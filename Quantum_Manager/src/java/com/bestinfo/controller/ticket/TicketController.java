package com.bestinfo.controller.ticket;

import com.bestinfo.bean.game.GameDrawInfo;
import com.bestinfo.bean.ticket.TicketBet;
import com.bestinfo.dao.game.IGameDrawInfoDao;
import com.bestinfo.dao.ticket.ITicketBetDao;
import com.bestinfo.util.TimeUtil;
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
 * ticket controller
 */
@Controller
@RequestMapping(value = "/ticket")
public class TicketController {

    private static final Logger logger = Logger.getLogger(TicketController.class);

    @Resource
    private ITicketBetDao ticketBetDao;

    @Resource
    private IGameDrawInfoDao drawInfoDao;

    @Resource
    private JdbcTemplate metaJdbcTemplate;

    @Resource
    private JdbcTemplate termJdbcTemplate;

    /**
     * 查看票注销标记
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/checkUndo")//, method = RequestMethod.POST
    @ResponseBody
    public Map<String, Object> checkUndo(HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int gameId = ServletRequestUtils.getIntParameter(request, "game", 0);
        String drawName = ServletRequestUtils.getStringParameter(request, "draw", "");
        int tmnId = ServletRequestUtils.getIntParameter(request, "tmn", 0);
        int serialNo = ServletRequestUtils.getIntParameter(request, "serial", 0);

        logger.info("qrcode check ticket undo mark,game_id:" + gameId + ",draw_name:" + drawName
                + ",terminal_id:" + tmnId + ",serial_no:" + serialNo);
        if (gameId <= 0 || drawName.equals("") || tmnId <= 0) {
            logger.info("param error");
            resMap.put("code", "-1");
            resMap.put("msg", "param error");
            return resMap;
        }
        if (String.valueOf(tmnId).length() != 8) {
            logger.info("param error");
            resMap.put("code", "-1");
            resMap.put("msg", "param error");
            return resMap;
        }
        if (drawName.length() != 8 && drawName.length() != 11) {
            logger.info("param error");
            resMap.put("code", "-1");
            resMap.put("msg", "param error");
            return resMap;
        }

        GameDrawInfo drawInfo = drawInfoDao.getDrawByGameIdAndDrawName(metaJdbcTemplate, gameId, drawName.substring(0, 8));
        if (drawInfo == null) {
            logger.info("drawInfo is null");
            resMap.put("code", "-2");
            resMap.put("msg", "drawInfo does not exist");
            return resMap;
        }
        TicketBet ticket = ticketBetDao.getTicketBet(termJdbcTemplate, gameId, drawInfo.getDraw_id(), serialNo, tmnId);
        if (ticket == null) {
            logger.info("ticket is null");
            resMap.put("code", "-2");
            resMap.put("msg", "ticket does not exist");
            return resMap;
        }

        logger.info("get ticket success---" + ticket.getUndo_mark() + "," + ticket.getBet_money().intValue() + ","
                + TimeUtil.formatDate_YMDT(ticket.getBet_time()));
        resMap.put("code", "0");
        resMap.put("msg", "success");
        resMap.put("undo", ticket.getUndo_mark() + "");
        resMap.put("money", ticket.getBet_money().intValue() + "");
        resMap.put("time", TimeUtil.formatDate_YMDT(ticket.getBet_time()));

        return resMap;
    }

}
