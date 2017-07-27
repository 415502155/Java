package com.bestinfo.appserver.redis;

import com.bestinfo.redis.monitor.TerminalMonitorRedis;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 终端监控
 */
@Controller
@RequestMapping(value = "/tmnMonitor")
public class TmnMonitorController {

    private static final Logger logger = Logger.getLogger(TmnMonitorController.class);

    //added by yfyang 20170216,统计终端在线数等监控信息
    @Resource
    private TerminalMonitorRedis tmnMonitorRedis;

    /**
     * 终端登录详情，终端机号+时间
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/loginNumber")
    public @ResponseBody
    Map<String, Object> tmnLoginNumber(HttpServletRequest request) {
        logger.info("get tmn login number");

        Map<String, Object> resMap = new HashMap<String, Object>();
        long number = tmnMonitorRedis.getTmnLoginNumber(new Date());
        resMap.put("loginNumber", number);
        return resMap;
    }

    /**
     * 终端登录详情，终端机号+时间
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/loginDetail")
    public @ResponseBody
    Map<String, String> tmnLoginDetail(HttpServletRequest request) {
        logger.info("get tmn login detail");

        Map<String, String> resMap = tmnMonitorRedis.getTmnLoginIdsAndTime(new Date());
        return resMap;
    }

}
