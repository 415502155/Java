package com.bestinfo.controller.game;

import com.bestinfo.service.game.IGameLuckyOpenService;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 同步开奖号码和开奖公告信息
 *
 * @author lvchangrong
 */
@Controller
@RequestMapping(value = "/synLuckyAndOpenPrize")
public class GameLuckyNoAndOpenPrizeCtr {

    private final Logger logger = Logger.getLogger(GameLuckyNoAndOpenPrizeCtr.class);

    @Resource
    private IGameLuckyOpenService gameLuckyOpenService;

    /**
     * 同步开奖号码和开奖公告信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/synDatas")
    public @ResponseBody
    Map<String, Object> synDatas(HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        try {
            int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
            String begin_draw_name = ServletRequestUtils.getStringParameter(request, "begin_draw_name", "0");
            String end_draw_name = ServletRequestUtils.getStringParameter(request, "end_draw_name", "0");
            logger.info("game_id:" + game_id + ",begin_draw_name:" + begin_draw_name + "    end_draw_name:" + end_draw_name);
            if (game_id <= 0 || "0".equals(begin_draw_name) || "0".equals(end_draw_name)) {
                logger.error("data error,game_id:" + game_id + ",begin_draw_name:" + begin_draw_name + "   end_draw_name:" + end_draw_name);
                resMap.put("code", "-2");
                resMap.put("msg", "参数错误！");
                return resMap;
            }
            //同步开奖号码
            int result1 = gameLuckyOpenService.synLuckyNo(game_id, begin_draw_name, end_draw_name);
            if (result1 == 0) {
                resMap.put("code", "0");
                resMap.put("msg", "同步成功！");
            } else {
                resMap.put("code", result1);
                resMap.put("msg", "同步失败！");
            }
            //同步开奖公告
            int re2 = gameLuckyOpenService.synOpenPrize(game_id, begin_draw_name, end_draw_name);
            if (re2 == 0) {
                resMap.put("code", "0");
                resMap.put("msg", "同步成功！");
            } else {
                resMap.put("code", result1);
                resMap.put("msg", "同步失败！");
            }
            return resMap;
        } catch (Exception ex) {
            logger.error("'ex :", ex);
            resMap.put("code", "-1");
            resMap.put("msg", "同步失败！");
            return resMap;
        }

    }
}
