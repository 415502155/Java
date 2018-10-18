package com.bestinfo.controller.ehcache;

import com.bestinfo.cache.keys.GetCacheCommon;
import com.bestinfo.ehcache.annotation.EhcacheInit;
import com.bestinfo.redis.business.TmnInfoRedisCache;
import com.bestinfo.redis.business.TmnPrivilegeRedisCache;
import com.bestinfo.redis.gamedraw.GameDrawInfoCache;
import com.bestinfo.redis.gamedraw.GameInfoRedis;
import com.bestinfo.redis.ticket.StatKenoPrizeAnnRedis;
import com.bestinfo.redis.ticket.StatPrizeAnnRedis;
import com.bestinfo.service.game.IGameInfoService;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

/**
 * ehcache数据同步
 *
 * @author YangRong
 */
@Controller
@RequestMapping(value = "/synehcache")
public class EhcacheController {

    private final Logger logger = Logger.getLogger(EhcacheController.class);

    @Resource
    private TmnInfoRedisCache tmnInfoRedisCache;
    @Resource
    private GameInfoRedis gameInfoRedis;
    @Resource
    private TmnPrivilegeRedisCache tmnPrivilegeRedisCache;
    @Resource
    private GameDrawInfoCache gameDrawInfoCache;
    @Resource
    private StatPrizeAnnRedis statPrizeAnnRedis;
    @Resource
    private StatKenoPrizeAnnRedis statKenoPrizeAnnRedis;
    @Resource
    private IGameInfoService igameinfo;

    @RequestMapping(value = "/2dataSynPage")
    public String toDataSynPage(HttpServletRequest request, Model resModel) {
        return "/data/dataSyn";
    }

    @RequestMapping(value = "/initEhcache", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> initEhcache(HttpServletRequest request, Model resModel) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int retn = initAllCache();
        if (retn > 0) {
            logger.error("meta DB syn Ehcache failNum: " + retn);
            resMap.put("code", -1);
            resMap.put("msg", "同步Ehcache失败！");
        } else {
            logger.info("Ehcache syn success.");
            resMap.put("code", 0);
            resMap.put("msg", "同步Ehcache成功！");
        }

        return resMap;
    }

    private int initAllCache() {
        WebApplicationContext wac = ContextLoaderListener.getCurrentWebApplicationContext();
        int failNum = 0;
        Map<String, Object> ehcacheBeans = wac.getBeansWithAnnotation(EhcacheInit.class);
        Set<String> beanNames = ehcacheBeans.keySet();
        for (String beanName : beanNames) {
            //把跟电话投注相关的先注释掉
            if (!beanName.equalsIgnoreCase("GamblerKey") && !beanName.equalsIgnoreCase("DealerInfoCache")
                    && !beanName.equalsIgnoreCase("DealerPrivilegeCache") && !beanName.equalsIgnoreCase("GameBetModeGambCache")
                    && !beanName.equalsIgnoreCase("GamePlayInfoGamblerCache") && !beanName.equalsIgnoreCase("PlayBetModeGamblerCache")) {
                GetCacheCommon ehcacheBean = (GetCacheCommon) ehcacheBeans.get(beanName);
                int retn = ehcacheBean.init((JdbcTemplate) wac.getBean("metaJdbcTemplate"));
                if (retn != 0) {
                    logger.error("init data into cache error:" + beanName + "\tcode:" + retn);
                    failNum++;
                }
            }

        }
        return failNum;
    }

    /**
     * 同步终端 和终端特权
     *
     * @param request
     * @param resModel
     * @return
     */
    @RequestMapping(value = "/initTmnData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> initTmnData(HttpServletRequest request, Model resModel) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int retn = initTmnDataCache();
        if (retn < 0) {
            logger.error("syn tmn info fail.");
            resMap.put("code", -1);
            resMap.put("msg", "同步终端信息失败！");
        } else {
            logger.info("syn tmn info success.");
            resMap.put("code", 0);
            resMap.put("msg", "同步终端信息成功！");
        }
        return resMap;
    }

    private int initTmnDataCache() {
        try {
            int retn = tmnInfoRedisCache.init();
            if (retn != 0) {
                logger.error("Syn tmnInfoRedisCache fail ");
                return -3;
            }
            int re = tmnPrivilegeRedisCache.init();
            if (re != 0) {
                logger.error("Syn tmnPrivilegeRedisCache fail ");
                return -4;
            }
            return 0;
        } catch (Exception ex) {
            logger.error("", ex);
            return -5;
        }
    }

    /**
     * 同步游戏信息
     *
     * @param request
     * @param resModel
     * @return
     */
    @RequestMapping(value = "/initGameInfo")
    @ResponseBody
    public Map<String, Object> initGameInfo(HttpServletRequest request, Model resModel) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int retn = gameInfoRedis.init();
        if (retn < 0) {
            logger.error("syn game info fail.");
            resMap.put("code", -1);
            resMap.put("msg", "同步游戏信息失败！");
        } else {
            logger.info("syn game info success.");
            resMap.put("code", 0);
            resMap.put("msg", "同步游戏信息成功！");
        }
        return resMap;
    }

    @RequestMapping(value = "/initGameDrawInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> initGameDrawInfo(HttpServletRequest request, Model resModel) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int retn = gameDrawInfoCache.GameDrawInfoDataSyn();
        logger.info("draw and kdraw info redis syn result :" + retn);
        if (retn < 0) {
            logger.error("syn game draw info fail.");
            resMap.put("code", -1);
            resMap.put("msg", "同步期信息失败！");
        } else {
            logger.info("syn game draw info success.");
            resMap.put("code", 0);
            resMap.put("msg", "同步期信息成功！");
        }
        return resMap;
    }

    /**
     * 同步普通游戏中奖汇总数据和快开游戏中奖汇总数据
     *
     * @param request
     * @param resModel
     * @return
     */
    @RequestMapping(value = "/initStatPrizeAnn", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> initStatPrizeAnn(HttpServletRequest request, Model resModel) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int re1 = statPrizeAnnRedis.init();
        int re2 = statKenoPrizeAnnRedis.init();
        if (re1 < 0 || re2 < 0) {
            logger.error("syn statPrizeAnn  fail.");
            resMap.put("code", -1);
            resMap.put("msg", "同步游戏中奖汇总数据失败！");
        } else {
            resMap.put("code", 0);
            resMap.put("msg", "同步游戏中奖汇总数据成功！");
        }

        return resMap;
    }

    @RequestMapping(value = "/fsSynGameinfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> fsSynGameinfo(HttpServletRequest request, Model resModel) {
        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        Map<String, Object> resMap = new HashMap<String, Object>();
        int re = igameinfo.fsSynGameInfo(game_id);
        if (re < 0) {
            logger.error("syn game draw info fail.");
            resMap.put("code", -1);
            resMap.put("msg", "同步期信息失败！");
        } else {
            logger.info("syn game draw info success.");
            resMap.put("code", 0);
            resMap.put("msg", "同步期信息成功！");
        }
        return resMap;
    }

    /**
     * 测试redis sentinel
     */
//    @RequestMapping(value = "/getTmninfo")
//    @ResponseBody
//    public void getTmninfo(HttpServletRequest request, Model resModel) {
//        String tmnid = request.getParameter("terminal_id");
//        long firsttime = 0L;
//        long secondtime = 0L;
//        do {
//            TmnInfo tmn = tmnInfoRedisCache.getTmnInfoById(Integer.parseInt(tmnid));
//            if (tmn == null) {
//                if (firsttime == 0L) {
//                    firsttime = System.currentTimeMillis();
//                    logger.error("tmn info error");
//                }
//            } else {
//                if (firsttime != 0L) {
//                    secondtime = System.currentTimeMillis();
//                    System.out.println("secod:" + ((secondtime - firsttime)));
//                }
//            }
//        } while (secondtime == 0L);
//    }
}
