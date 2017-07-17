//package com.bestinfo.controller.game;
//
//import com.bestinfo.bean.game.GameClassInfo;
//import com.bestinfo.bean.game.GameInfo;
//import com.bestinfo.bean.game.GamePlayInfo;
//import com.bestinfo.ehcache.game.GameClassInfoCache;
//import com.bestinfo.ehcache.game.GameInfoCache;
//import com.bestinfo.ehcache.game.GamePlayInfoCache;
//import com.bestinfo.service.game.IGameClassInfoService;
//import java.util.Collections;
//import java.util.Comparator;
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
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * 游戏奖级
// *
// * @author YangRong
// */
//@Controller
//@RequestMapping(value = "/gameClassInfo")
//public class GameClassInfoController {
//
//    private final Logger logger = Logger.getLogger(GameClassInfoController.class);
//
//    @Resource
//    private IGameClassInfoService gameClassInfoService;
//    @Resource
//    private GameClassInfoCache gameClassInfoCache;
//    @Resource
//    private GamePlayInfoCache gamePlayInfoCache;
//    @Resource
//    private GameInfoCache gameInfoCache;
//
//    @RequestMapping(value = "/2select")
//    public String toSelect(HttpServletRequest request, Model resModel) {
//        return "/game/gameClassInfoList";
//    }
//
//    @RequestMapping(value = "/2add")
//    public String toAdd(HttpServletRequest request, Model resModel) {
//        return "/game/gameClassInfoAdd";
//    }
//
//    @RequestMapping(value = "/delGameClassInfo", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> delGameClassInfo(HttpServletRequest request,
//            @RequestParam(value = "gameId", required = true) int gameId,
//            @RequestParam(value = "playId", required = true) int playId,
//            @RequestParam(value = "classId", required = true) int classId) {
//
//        logger.info("delete game class info,gameId:" + gameId + " playId:" + playId + " classId:" + classId);
//        Map<String, Object> resMap = new HashMap<String, Object>();
//
//        int result = gameClassInfoService.deleteGameInfoByGamePlayClassId(gameId, playId, classId);
//        if (result > 0) {
//            resMap.put("result", "success");
//            resMap.put("msg", "删除奖级信息成功！");
//        } else {
//            resMap.put("result", "fail");
//            resMap.put("msg", "删除奖级信息失败！");
//        }
//        logger.info(resMap.get("result"));
//        return resMap;
//    }
//
//    /**
//     * 游戏奖级信息添加
//     *
//     * @param request
//     * @param gameClassInfo
//     * @return
//     */
//    @RequestMapping(value = "/addGameClassInfo", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> addGameClassInfo(HttpServletRequest request, GameClassInfo gameClassInfo) {
//        logger.info("insert game class info ! gameId:" + gameClassInfo.getGame_id() + " playId:" + gameClassInfo.getPlay_id() + " classId:" + gameClassInfo.getClass_id());
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        int result = gameClassInfoService.insertGameClassInfo(gameClassInfo);
//        if (result == 0) {
//            resMap.put("result", "success");
//            resMap.put("msg", "添加奖级信息成功！");
//        } else {
//            resMap.put("result", "fail");
//            resMap.put("msg", "添加奖级信息失败！");
//        }
//        return resMap;
//    }
//
//    /**
//     * 游戏奖级信息修改
//     *
//     * @param request
//     * @param gameClassInfo
//     * @return
//     */
//    @RequestMapping(value = "/modifyGameClassInfo", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> modifyGameClassInfo(HttpServletRequest request, GameClassInfo gameClassInfo) {
//        logger.info("modify game class info !");
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        int result = gameClassInfoService.updateGameClassInfo(gameClassInfo);
//        if (result == 0) {
//            resMap.put("result", "success");
//            resMap.put("msg", "修改奖级信息成功！");
//        } else {
//            resMap.put("result", "fail");
//            resMap.put("msg", "修改奖级信息失败！");
//        }
//
//        return resMap;
//    }
//
//    @RequestMapping(value = "/2modify")
//    public String toModify(HttpServletRequest request, Model resModel) {
//        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
//        int play_id = ServletRequestUtils.getIntParameter(request, "play_id", 0);
//        int class_id = ServletRequestUtils.getIntParameter(request, "class_id", 0);
//        logger.info("to modify game play info");
//        if (game_id > 0 && play_id > 0 && class_id > 0) {
//            GameClassInfo gameClassInfo = gameClassInfoCache.getGameClassInfoByid(game_id, play_id, class_id);
//            if (gameClassInfo != null) {
//                resModel.addAttribute("gameClassInfo", gameClassInfo);
//                GameInfo gi = gameInfoCache.getGameInfoByid(game_id);
//                if (gi != null) {
//                    resModel.addAttribute("gameName", gi.getGame_name());
//                }
//                GamePlayInfo gpi = gamePlayInfoCache.getGamePlayInfoByid(game_id, play_id);
//                if (gpi != null) {
//                    resModel.addAttribute("gamePlayName", gpi.getPlay_name());
//                }
//                return "/game/gameClassInfoModify";
//            }
//        }
//        return "/game/gameClassInfoModify";
////        return "forward:/gameClassInfo/2select";
//
//    }
//
//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    public String getGameClassInfoByGameId(HttpServletRequest request, Model resModel) {
//        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
//        int play_id = ServletRequestUtils.getIntParameter(request, "play_id", 0);
//        logger.info("get gameClassInfo by game_id " + game_id);
//        if (game_id == 0) {
//            logger.error("gameid from jsp page is 0");
//            return "/game/gameClassInfoList";
//        }
//
//        List<GameClassInfo> lgci = gameClassInfoCache.getGameClassListByGameid(game_id, play_id);
//        if(lgci == null){
//            logger.error("can't get game class info from cache");
//            return "/game/gameClassInfoList";
//        }
//        Collections.sort(lgci,new Comparator(){
//            @Override
//            public int compare(Object o1, Object o2) {
//                GameClassInfo arg0 = (GameClassInfo)o1;
//                GameClassInfo arg1 = (GameClassInfo)o2;
//                return arg0.getClass_id().compareTo(arg1.getClass_id());                
//            }
//        });
//        for (GameClassInfo gameClassInfo : lgci) {
//            String game_name = gameInfoCache.getGameInfoByid(gameClassInfo.getGame_id()).getGame_name();
//            String play_name = gamePlayInfoCache.getGamePlayInfoByid(gameClassInfo.getGame_id(), gameClassInfo.getPlay_id()).getPlay_name();
//            gameClassInfo.setGame_name(game_name);
//            gameClassInfo.setPlay_name(play_name);
//        }
//        resModel.addAttribute("gameClassInfoList", lgci);
//        resModel.addAttribute("play_id", play_id);
//        resModel.addAttribute("game_id", game_id);
//        return "/game/gameClassInfoList";
//    }
//}
