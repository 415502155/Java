package com.bestinfo.controller.game;

import com.bestinfo.bean.game.GameDrawInfo;
import com.bestinfo.service.game.ISaleTimeService;
import com.bestinfo.util.TimeUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 调整当前期销售结束时间
 *
 * @author hhhh
 */
@Controller
@RequestMapping(value = "/saleTimeEdit")
public class CurrentSaleTimeEditController {

    private final Logger logger = Logger.getLogger(CurrentSaleTimeEditController.class);

    @Resource
    private ISaleTimeService saleTimeService;

    /**
     * 跳转到当前期销售时间页面
     *
     * @return
     */
    @RequestMapping(value = "/2saleTime")
    public String toSaleTime() {
        return "/gamedraw/currentDrawSaleTime";
    }

    /**
     * 获取所有处于当前期的游戏列表
     *
     * @param request
     * @param resModel
     * @return
     */
//    @RequestMapping(value = "/getGameList", method = RequestMethod.POST)
//    @ResponseBody
//    public List<GameInfo> getGameList(HttpServletRequest request, Model resModel) {
//        logger.info("select game info list from redis where is in current draw");
//        List<GameInfo> list = saleTimeService.getGameListInCurDraw();
//        return list;
//    }
    
//    @RequestMapping(value = "/getGameList", method = RequestMethod.POST)
//    @ResponseBody
//    public List<GameDrawInfo> getGameList(HttpServletRequest request, Model resModel) {
//        logger.info("select game info list from redis where is in current draw");        
//        List<GameDrawInfo> list = saleTimeService.getGameListInCurDraw();
//        return list;
//    }

    /**
     * 根据游戏编号获取当前期的期开始和结束时间
     *
     * @param request
     * @param resModel
     * @return
     */
    @RequestMapping(value = "/getTimeByGameId", method = RequestMethod.POST)
    @ResponseBody
    public GameDrawInfo getTimeByGameId(HttpServletRequest request, Model resModel) {
        int gameId = ServletRequestUtils.getIntParameter(request, "gameId", 0);
        logger.info("select draw time info from DB where is in current draw and gameid = " + gameId);
        GameDrawInfo gdi = saleTimeService.getTimeByGameId(gameId);
        if(gdi == null){
            return null;
        }
        return gdi;
    }

    /**
     * 更新期结束时间
     * @param request
     * @param resModel
     * @return 
     */
    @RequestMapping(value = "/updateDrawTime", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateDrawTime(HttpServletRequest request, Model resModel) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int gameId = ServletRequestUtils.getIntParameter(request, "gameId", 0);
        String end_time_str = ServletRequestUtils.getStringParameter(request, "end_time", "");
        Date end_time = null;
        try {
            end_time = TimeUtil.parseDate_YMDT(end_time_str);
        } catch (Exception e) {
            logger.error("date format conversion error");
            resMap.put("result", "操作失败");
            return resMap;
        }

        int re = saleTimeService.updateDrawTime(gameId, end_time);
        logger.info("update end time result: "+re);
        if (re < 0) {
            resMap.put("result", "操作失败");
        } else {
            resMap.put("result", "操作成功");
        }
        return resMap;
    }

}
