package com.bestinfo.controller.game;

import com.bestinfo.service.game.INewDrawsCheckService;
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
 * 新期数检查
 *
 * @author lvchangrong
 */
@Controller
@RequestMapping(value = "/newDrawsCheck")
public class NewDrawsCheck {

    private final Logger logger = Logger.getLogger(NewDrawsCheck.class);

    @Resource
    private INewDrawsCheckService newDrawsCheckService;

    /**
     * 新期数检查
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/doCheck")
    public @ResponseBody
    Map<String, Object> doCheck(HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        try {
            int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
            //int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
            String draw_name = ServletRequestUtils.getStringParameter(request, "draw_name", "0");
            logger.info("game_id:" + game_id + ",draw_name:" + draw_name);
            if (game_id <= 0 || "0".equals(draw_name)) {
                logger.error("data error,game_id:" + game_id + ",draw_name:" + draw_name);
                resMap.put("code", "-2");
                resMap.put("msg", "参数错误！");
                return resMap;
            }
            int re = newDrawsCheckService.doCheck(game_id, draw_name,resMap);
            switch(re){
                case -3:
                    resMap.put("code", -3);
                    resMap.put("msg", "期信息不存在!");   
                    break;
                case -4:
                    resMap.put("code", -4);
                    resMap.put("msg", "期状态不为当前期!");
                    break;
                case -5:
                    resMap.put("code", -5);
                    resMap.put("msg", "游戏信息不存在!");
                    break;
                case -6:
                    resMap.put("code", -6);
                    resMap.put("msg", "快开期信息不存在!");
                    break;
                case -7:
//                    resMap.put("code", -7);
//                    resMap.put("msg", "快开期条数不等于游戏快开期数！");
                    break;
                case -8:
                    resMap.put("code", -8);
                    resMap.put("msg", "修改游戏当前期失败！");
                    break;
                case -9:
                    resMap.put("code", -9);
                    resMap.put("msg", "流水号记录数为空！");
                    break;
                case -10:
                    resMap.put("code", -10);
                    resMap.put("msg", "更新期信息缓存失败！");
                    break;
                case -11:
                    resMap.put("code", -11);
                    resMap.put("msg", "开新期失败!");
                    break;
                case -12:
                    resMap.put("code", -12);
                    resMap.put("msg", "启动ETL协议失败!");
                    break;
                case -13:
//                    resMap.put("code", -13);
//                    resMap.put("msg", "流水号记录数与终端记录数不相等！");
                    break;
                case -14:
                    break;
                default:
                    resMap.put("code", 0);
                    resMap.put("msg", "检查无误！");
                    break;
            }
            logger.info("=============end");
            return resMap;
        } catch (Exception ex) {
            logger.error("'ex :", ex);
            resMap.put("code", -1);
            resMap.put("msg", "检查失败！");
            return resMap;
        }
    }
}
