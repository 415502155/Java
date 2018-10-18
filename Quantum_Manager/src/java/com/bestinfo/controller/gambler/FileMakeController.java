package com.bestinfo.controller.gambler;

import com.bestinfo.service.gambler.IFileMakeService;
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
 * 电话投注生成文件
 */
@Controller
@RequestMapping(value = "/gambler")
public class FileMakeController {

    private static final Logger logger = Logger.getLogger(FileMakeController.class);

    @Resource
    private IFileMakeService gameblerFileService;

    /**
     * 游戏规则文件
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/game")
    @ResponseBody
    public Map<String, Object> updateGameInfo(HttpServletRequest request) {
        logger.info("create gambler game rule file");
        int game_id = ServletRequestUtils.getIntParameter(request, "gameId", 0);
        logger.info("game_id:" + game_id);

        Map<String, Object> resMap = new HashMap<String, Object>();
        int result = gameblerFileService.ruleFile(game_id);
        if (result < 0) {
            resMap.put("code", -1);
            resMap.put("msg", "生成游戏规则文件失败！");
        } else {
            resMap.put("code", 0);
            resMap.put("msg", "生成游戏规则文件成功！");
        }

        return resMap;
    }

    /**
     * 对账文件
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/account")
    @ResponseBody
    public Map<String, Object> account(HttpServletRequest request) {
        logger.info("create gambler account file");
        int game_id = ServletRequestUtils.getIntParameter(request, "gameId", 0);
        int draw_id = ServletRequestUtils.getIntParameter(request, "drawId", 0);
        String dealer_id = ServletRequestUtils.getStringParameter(request, "dealerId", "");
        logger.info("game_id:" + game_id + ",draw_id:" + draw_id + ",dealer_id:" + dealer_id);
        Map<String, Object> resMap = new HashMap<String, Object>();

        int result = gameblerFileService.accountFile(game_id, draw_id, dealer_id);
        if (result < 0) {
            resMap.put("code", -1);
            resMap.put("msg", "生成对账文件失败！");
        } else {
            resMap.put("code", 0);
            resMap.put("msg", "生成对账文件成功！");
        }

        return resMap;
    }

    /**
     * 中奖文件
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/winning")
    @ResponseBody
    public Map<String, Object> winning(HttpServletRequest request) {
        logger.info("create gambler game rule file");
        int game_id = ServletRequestUtils.getIntParameter(request, "gameId", 0);
        int draw_id = ServletRequestUtils.getIntParameter(request, "drawId", 0);
        String dealer_id = ServletRequestUtils.getStringParameter(request, "dealerId", "");
        logger.info("game_id:" + game_id + ",draw_id:" + draw_id + ",dealer_id:" + dealer_id);

        Map<String, Object> resMap = new HashMap<String, Object>();
        int result = gameblerFileService.winFile(game_id, draw_id, dealer_id);
        if (result < 0) {
            resMap.put("code", -1);
            resMap.put("msg", "生成中奖文件失败！");
        } else {
            resMap.put("code", 0);
            resMap.put("msg", "生成中奖文件成功！");
        }

        return resMap;
    }

    /**
     * 对账文件
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/accountKeno")
    @ResponseBody
    public Map<String, Object> accountKeno(HttpServletRequest request) {
        logger.info("create gambler account file");
        int game_id = ServletRequestUtils.getIntParameter(request, "gameId", 0);
        int draw_id = ServletRequestUtils.getIntParameter(request, "drawId", 0);
        int kdraw_id = ServletRequestUtils.getIntParameter(request, "kDrawId", 0);
        String dealer_id = ServletRequestUtils.getStringParameter(request, "dealerId", "");
        logger.info("game_id:" + game_id + ",draw_id:" + draw_id + ",kdraw_id:" + kdraw_id +",dealer_id:" + dealer_id);
        Map<String, Object> resMap = new HashMap<String, Object>();

        int result = gameblerFileService.accountFileKeno(game_id, draw_id, dealer_id, kdraw_id);
        if (result < 0) {
            resMap.put("code", -1);
            resMap.put("msg", "生成对账文件失败！");
        } else {
            resMap.put("code", 0);
            resMap.put("msg", "生成对账文件成功！");
        }

        return resMap;
    }

    /**
     * 中奖文件
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/winningKeno")
    @ResponseBody
    public Map<String, Object> winningKeno(HttpServletRequest request) {
        logger.info("create gambler game win file");
        int game_id = ServletRequestUtils.getIntParameter(request, "gameId", 0);
        int draw_id = ServletRequestUtils.getIntParameter(request, "drawId", 0);
        int kdraw_id = ServletRequestUtils.getIntParameter(request, "kdrawId", 0);
        String dealer_id = ServletRequestUtils.getStringParameter(request, "dealerId", "");
        logger.info("game_id:" + game_id + ",draw_id:" + draw_id + ",kdraw_id:" + kdraw_id + ",dealer_id:" + dealer_id);

        Map<String, Object> resMap = new HashMap<String, Object>();
        int result = gameblerFileService.winFileKeno(game_id, draw_id, dealer_id, kdraw_id);
        if (result < 0) {
            resMap.put("code", -1);
            resMap.put("msg", "生成中奖文件失败！");
        } else {
            resMap.put("code", 0);
            resMap.put("msg", "生成中奖文件成功！");
        }

        return resMap;
    }
}
