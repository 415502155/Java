package com.bestinfo.controller.game;

import com.bestinfo.define.filepath.FilePath;
import com.bestinfo.service.game.IGameRuleFileMakeService;
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
 * 游戏规则文件生成controller
 *
 * @author hhhh
 */
@Controller
@RequestMapping(value = "/gameRule")
public class GameRuleFileMakeController {

    private final Logger logger = Logger.getLogger(GameRuleFileMakeController.class);

    @Resource
    private IGameRuleFileMakeService gameRuleFileMakeService;

    /**
     * 跳转到游戏规则文件生成页面
     *
     * @return
     */
    @RequestMapping(value = "/2makeFilePage")
    public String toMakeFilePage() {
        return "/game/gameRuleFileMake";
    }

    /**
     * 根据游戏生成相应的XML文件
     *
     * @param request
     * @param resModel
     * @return
     */
    @RequestMapping(value = "/makeFile", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> makeFile(HttpServletRequest request, Model resModel) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int gameId = ServletRequestUtils.getIntParameter(request, "gameId", 0);
        if (gameId == 0) {
            logger.error("the game id from EB param is null where gameId = " + gameId);
            resMap.put("code", "-2");
            resMap.put("msg", "游戏编号为空！");
            return resMap;
        }

        String filePath = FilePath.getRootPath() + FilePath.getGameParaFileName(gameId);
        logger.info("game rule xml file path: " + filePath);
        filePath = filePath.substring(0, filePath.lastIndexOf("."));
        int re = gameRuleFileMakeService.makeXMLFile(filePath, gameId);
        logger.info("game rule xml file make result : " + re + " where gameId = " + gameId);
        if (re < 0) {
            resMap.put("code", "-1");
            resMap.put("msg", "操作失败！");
        } else {
            resMap.put("code", "0");
            resMap.put("msg", "操作成功！");
        }
        return resMap;
    }

}
