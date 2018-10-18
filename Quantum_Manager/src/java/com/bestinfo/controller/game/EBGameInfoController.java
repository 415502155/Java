package com.bestinfo.controller.game;

import com.bestinfo.bean.game.GameInfo;
import com.bestinfo.service.game.IGameInfoService;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 游戏信息操作controller
 *
 * @author hhhh
 */
@Controller
@RequestMapping(value = "/ebGameInfo")
public class EBGameInfoController {

    private final Logger logger = Logger.getLogger(EBGameInfoController.class);

    @Resource
    private IGameInfoService gameInfoService;

    /**
     * 修改游戏信息
     *
     * @param request
     * @param gameInfo
     * @return
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modifyGameInfo(HttpServletRequest request, GameInfo gameInfo) {
        logger.info("modify game info");
        Map<String, Object> resMap = new HashMap<String, Object>();

        int result = gameInfoService.ebmodifyGameInfo(gameInfo);
        if (result < 0) {
            resMap.put("code", -1);
            resMap.put("msg", "保存 失败！");
        } else {
            resMap.put("code", 0);
            resMap.put("msg", "保存 成功！");
        }

        return resMap;
    }

}
