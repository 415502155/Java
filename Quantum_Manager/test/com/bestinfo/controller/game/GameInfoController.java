//package com.bestinfo.controller.game;
//
//import com.bestinfo.bean.game.GameInfo;
//import com.bestinfo.ehcache.game.GameInfoCache;
//import com.bestinfo.service.game.IGameInfoService;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import org.apache.log4j.Logger;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.ServletRequestUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * 游戏信息操作controller
// *
// * @author yangyuefu
// */
//@Controller
//@RequestMapping(value = "/gameinfo")
//public class GameInfoController {
//
//    private final Logger logger = Logger.getLogger(GameInfoController.class);
//
//    @Resource
//    private IGameInfoService gameInfoService;
//    @Resource
//    private GameInfoCache gameInfoCache;
//
//    /**
//     * 访问增加游戏页面
//     *
//     * @return
//     */
//    @RequestMapping(value = "/2add")
//    public String toAdd() {
//        return "/game/gameInfoAdd";
//    }
//
//    /**
//     * 访问查询游戏信息页面
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/list")
//    public String gameInfoList(HttpServletRequest request, Model resModel) {
//        logger.info("get gameinfo from cache");
//        int pageIndex = ServletRequestUtils.getIntParameter(request, "pageIndex", 0);
//        List<GameInfo> listGameInfo = gameInfoCache.getGameInfoListFrmCache();
//        int len = listGameInfo.size();
//        pageIndex = (pageIndex <= 0 ? 0 : pageIndex);
//        pageIndex = (pageIndex >= len ? 0 : pageIndex);
//        resModel.addAttribute("gameInfo", listGameInfo.get(pageIndex));
//        resModel.addAttribute("pageIndex", pageIndex);
//        resModel.addAttribute("len", len);
//        return "/game/gameInfoList";
//    }
//
//    /**
//     * 根据指定的游戏id获取缓存中的游戏信息，页面使用
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/getGameInfoByidCache", method = RequestMethod.GET)
//    public String getGameInfoById(HttpServletRequest request, Model resModel) {
//        logger.info("get gameInfo from cache by id");
//        int gameid = ServletRequestUtils.getIntParameter(request, "game_id", 0);
//        GameInfo gameInfo = gameInfoCache.getGameInfoByid(gameid);
//        resModel.addAttribute("gameInfo", gameInfo);
//        resModel.addAttribute("pageIndex", 0);
//        resModel.addAttribute("len", -1);
//        return "/game/gameInfoList";
//    }
//
//    /**
//     * 访问修改游戏页面,需要把游戏的信息传到页面
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/2modify")
//    public String toModify(HttpServletRequest request, Model resModel) {
//        logger.info("to modify game");
//
//        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
//        if (game_id > 0) {
//            //从缓存中获取数据
//            GameInfo gameInfo = gameInfoCache.getGameInfoByid(game_id);
//            if (gameInfo != null) {
//                resModel.addAttribute("gameInfo", gameInfo);
//                return "/game/gameInfoModify";
//            }
//        }
//        return "/game/gameInfoModify";
//    }
//
//    /**
//     * 新增游戏
//     *
//     * @param request
//     * @param gameInfo
//     * @return
//     */
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> addGameInfo(HttpServletRequest request, GameInfo gameInfo) {
//        logger.info("add game info");
//        String draw_period = ServletRequestUtils.getStringParameter(request, "draw_period", "00000000000000");
//        gameInfo.setDraw_period(draw_period);
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        int result = gameInfoService.insertGameInfo(gameInfo);
//        if (result == 0) {
//            resMap.put("result", "success");
//            resMap.put("msg", "添加成功！");
//        }else if (result == -1) {
//            resMap.put("result", "fail");
//            resMap.put("msg", "添加失败！");
//        }else if (result == -2) {
//            resMap.put("result", "fail");
//            resMap.put("msg", "添加失败！");
//        }
//
//        return resMap;
//    }
//
//    /**
//     * 修改游戏
//     *
//     * @param request
//     * @param gameInfo
//     * @return
//     */
//    @RequestMapping(value = "/modify", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> modifyGameInfo(HttpServletRequest request, GameInfo gameInfo) {
//        logger.info("modify game info");
//        String draw_period = ServletRequestUtils.getStringParameter(request, "draw_period", "00000000000000");
//        gameInfo.setDraw_period(draw_period);
//        Map<String, Object> resMap = new HashMap<String, Object>();
//
//        int result = gameInfoService.updateGameInfo(gameInfo);
//        if (result == 0) {
//            resMap.put("result", "success");
//            resMap.put("msg", "修改成功！");
//        }else if (result == -1) {
//            resMap.put("result", "fail");
//            resMap.put("msg", "修改失败！");
//        }else if (result == -2) {
//            resMap.put("result", "fail");
//            resMap.put("msg", "修改失败！");
//        }
//
//        return resMap;
//    }
//
//    /**
//     * 根据指定的游戏id获取缓存中的游戏信息 用于js调用
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/getGameInfoByidCache2", method = RequestMethod.POST)
//    @ResponseBody
//    public GameInfo getGameInfoByIdCache(HttpServletRequest request, Model resModel) {
//        int gameid = ServletRequestUtils.getIntParameter(request, "gameId", 0);
//        GameInfo gameinfo = gameInfoCache.getGameInfoByid(gameid);
//        return gameinfo;
//    }
//
//    /**
//     * 从缓存中获取所有GameInfo 用于下拉列表数据 js调用
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/getGameinfoCache", method = RequestMethod.POST)
//    @ResponseBody
//    public List<GameInfo> getGameInfoFromCache(HttpServletRequest request, Model resModel) {
//        List<GameInfo> listGameInfo = gameInfoCache.getGameInfoListFrmCache();
//        return listGameInfo;
//    }
//}
