//package com.bestinfo.controller.game;
//
//import com.bestinfo.bean.game.GamePlayInfo;
//import com.bestinfo.ehcache.game.GameInfoCache;
//import com.bestinfo.ehcache.game.GamePlayInfoCache;
//import com.bestinfo.service.game.IGamePlayInfoService;
//import java.util.ArrayList;
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
// * 游戏玩法信息操作controller
// *
// * @author yangyuefu
// */
//@Controller
//@RequestMapping(value = "/gameplayinfo")
//public class GamePlayInfoController {
//
//    private final Logger logger = Logger.getLogger(GamePlayInfoController.class);
//
//    @Resource
//    private IGamePlayInfoService gamePlayInfoService;
//    @Resource
//    private GamePlayInfoCache gamePlayInfoCache;
//    @Resource
//    private GameInfoCache gameInfoCache;
//
//    /**
//     * 跳转到增加游戏玩法页面
//     *
//     * @return
//     */
//    @RequestMapping(value = "/2add")
//    public String toAdd() {
//        return "/game/gamePlayInfoAdd";
//    }
//
//    /**
//     * 跳转到查询页面
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/2select")
//    public String toSelect(HttpServletRequest request, Model resModel) {
//        return "/game/gamePlayInfoList";
//    }
//
//    /**
//     * 获取玩法列表 游戏id必填
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/list")
//    public String gamePlayInfoListFrmCache(HttpServletRequest request, Model resModel) {
//        logger.info("game play info  list from cache");
//        int gameid = ServletRequestUtils.getIntParameter(request, "game_id", 0);
//        int playid = ServletRequestUtils.getIntParameter(request, "play_id", 0);
//        if (gameid == 0) {
//            logger.error("gameid from jsp is 0");
//            //少了一名 出错提示
//            return "/game/gamePlayInfoList";
//        }
////        List<GamePlayInfo> listGamePlayInfo = gamePlayInfoCache.getGamePlayInfoListFrmCache(gameid, playid);
//        List<GamePlayInfo> listGamePlayInfo = null;
//        if (playid == 0) {
//            listGamePlayInfo = gamePlayInfoCache.getGamePlayInfoListByIdFrmCache(gameid);
//        } else {
//            GamePlayInfo gp = gamePlayInfoCache.getGamePlayInfoByid(gameid, playid);
//            listGamePlayInfo = new ArrayList<GamePlayInfo>();
//            listGamePlayInfo.add(gp);
//        }
//
//        //取得游戏编号对应的游戏名称
//        String game_name;
//        for (GamePlayInfo gamePlayInfo : listGamePlayInfo) {
//            game_name = gameInfoCache.getGameInfoByid(gamePlayInfo.getGame_id()).getGame_name();
//            gamePlayInfo.setGame_name(game_name);
//        }
//        resModel.addAttribute("listGamePlayInfo", listGamePlayInfo);
//        return "/game/gamePlayInfoList";
//    }
//
//    /**
//     * 跳转到修改玩法页面,需要把玩法的信息传到页面
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/2modify")
//    public String toModify(HttpServletRequest request, Model resModel) {
//        logger.info("to modify game play info");
//        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
//        int play_id = ServletRequestUtils.getIntParameter(request, "play_id", 0);
//        // GamePlayInfo gamePlayInfo = gamePlayInfoService.getGamePlayInfoByPlayId(play_id);
////        GamePlayInfo gamePlayInfo = gamePlayInfoService.getPlayByGameIdAndPlayId(game_id, play_id);
//        if (game_id == 0 || play_id == 0) {
//            logger.error("(gameid from jsp page is 0) or (playid from jsp page is 0)");
//            //给用户提示
//            return "/game/gamePlayInfoModify";
//        }
//
//        GamePlayInfo gamePlayInfo = gamePlayInfoCache.getGamePlayInfoByid(game_id, play_id);
//        //从缓存中获取游戏编码对应的中文名称
//        String game_name = gameInfoCache.getGameInfoByid(game_id).getGame_name();
//        gamePlayInfo.setGame_name(game_name);
//        resModel.addAttribute("gamePlayInfo", gamePlayInfo);
//        return "/game/gamePlayInfoModify";
//    }
//
//    /**
//     * 跳转到详情页面,需要把玩法的信息传到页面
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
////    @RequestMapping(value = "/2details")
////    public String toDetails(HttpServletRequest request, Model resModel) {
////        logger.info("to modify game play info");
////        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
////        int play_id = ServletRequestUtils.getIntParameter(request, "play_id", 0);
////        // GamePlayInfo gamePlayInfo = gamePlayInfoService.getGamePlayInfoByPlayId(play_id);
////        //从缓存中获取游戏编码对应的中文名称
////        GamePlayInfo gamePlayInfo = gamePlayInfoService.getPlayByGameIdAndPlayId(game_id, play_id);
////        String game_name = gameInfoCache.getGameInfoByid(game_id).getGame_name();
////        gamePlayInfo.setGame_name(game_name);
////        resModel.addAttribute("gamePlayInfo", gamePlayInfo);
////        return "/game/gamePlayInfoDetails";
////    }
//    /**
//     * 新增玩法
//     *
//     * @param request
//     * @param gamePlayInfo
//     * @return
//     */
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> addGamePlayInfo(HttpServletRequest request, GamePlayInfo gamePlayInfo) {
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        int result = gamePlayInfoService.insertGamePlayInfo(gamePlayInfo);
//        if (result == 0) {
//            resMap.put("result", "success");
//            resMap.put("msg", "添加成功！");
//        } else if (result == -1) {
//            resMap.put("result", "fail");
//            resMap.put("msg", "添加失败！");
//        } else if (result == -2) {
//            resMap.put("result", "fail");
//            resMap.put("msg", "添加失败！");
//        }
//        return resMap;
//    }
//
//    /**
//     * 修改玩法
//     *
//     * @param request
//     * @param gamePlayInfo
//     * @return
//     */
//    @RequestMapping(value = "/modify", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> modifyGamePlayInfo(HttpServletRequest request, GamePlayInfo gamePlayInfo) {
//        logger.info("modify game play info");
//
//        Map<String, Object> resMap = new HashMap<String, Object>();
//
//        int result = gamePlayInfoService.updateGamePlayInfo(gamePlayInfo);
//        if (result == 0) {
//            resMap.put("result", "success");
//            resMap.put("msg", "修改成功！");
//        } else if (result == -1) {
//            resMap.put("result", "fail");
//            resMap.put("msg", "修改失败！");
//        } else if (result == -2) {
//            resMap.put("result", "fail");
//            resMap.put("msg", "修改失败！");
//        }
//
//        return resMap;
//    }
//
//    /**
//     * 从cache缓存中获取游戏玩法名称（id）下拉列表信息
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/getGamePlayInfoSelect", method = RequestMethod.POST)
//    @ResponseBody
//    public List<GamePlayInfo> getGamePlayInfoSelect(HttpServletRequest request) {
//        logger.info("select gamePlayInfo list from cache!");
//        int gameid = ServletRequestUtils.getIntParameter(request, "game_id", 0);
//        List<GamePlayInfo> listGameInfo = gamePlayInfoCache.getGamePlayInfoListByIdFrmCache(gameid);
//        return listGameInfo;
//    }
//}
