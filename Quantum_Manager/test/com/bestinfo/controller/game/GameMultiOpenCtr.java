//package com.bestinfo.controller.game;
//
//import com.bestinfo.controller.BasicController;
//import com.bestinfo.bean.game.GameMultiOpen;
//import com.bestinfo.ehcache.game.GameInfoCache;
//import com.bestinfo.ehcache.game.GameMultiOpenCache;
//import com.bestinfo.service.game.IGameMultiOpenSer;
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
// * 游戏多次开奖
// *
// * @author chenliping
// */
//@Controller
//@RequestMapping(value = "/gameMultiOpen")
//
//public class GameMultiOpenCtr extends BasicController {
//
//    private final Logger logger = Logger.getLogger(GameMultiOpenCtr.class);
//    @Resource
//    private IGameMultiOpenSer gmlti;
//    @Resource
//    private GameMultiOpenCache gameMultiOpenCache;
//    @Resource
//    private GameInfoCache gameInfoCache;
//
//    /**
//     * 跳转到添加页面
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/2add")
//    public String toadd(HttpServletRequest request, Model resModel) {
//        return "/game/gameMultiOpenAdd";
//    }
//
//    /**
//     * 跳转到编辑页面
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/2modify")
//    public String toModify(HttpServletRequest request, Model resModel) {
//        logger.info("to modify gameMultiOpen");
//        int gameid = ServletRequestUtils.getIntParameter(request, "gameid", 0);
//        int openid = ServletRequestUtils.getIntParameter(request, "openid", 0);
//        GameMultiOpen gameMultiOpen = gameMultiOpenCache.getGameMultiOpenById(gameid, openid);
//        //取得游戏编号对应的游戏名称
//        String game_name; //游戏名称
//        game_name = gameInfoCache.getGameInfoByid(gameid).getGame_name();
//        gameMultiOpen.setGame_name(game_name);
//        resModel.addAttribute("gameMultiOpen", gameMultiOpen);
//        return "/game/gameMultiOpenModify";
//    }
//
//    /**
//     * 添加
//     *
//     * @param request
//     * @param gameMultiOpen
//     * @return
//     */
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> addGameMultiOpen(HttpServletRequest request, GameMultiOpen gameMultiOpen) {
//        logger.info("add GameMultiOpen");
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        int re = gmlti.addGameMultiOpen(gameMultiOpen);
//        if (re < 0) {
//            resMap.put("result", "error");
//            resMap.put("msg", "添加失败！");
//        } else {
//            resMap.put("result", "success");
//            resMap.put("msg", "添加成功!");
//        }
//        return resMap;
//    }
//
//    /**
//     * 跳转到查询页面
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/2select", method = RequestMethod.GET)
//    public String toSelect(HttpServletRequest request, Model resModel) {
//        return "/game/gameMultiOpenList";
//    }
//
//    /**
//     * 获取开奖次数描述数据
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    public String getGameMultiOpenRule(HttpServletRequest request, Model resModel) {
//        logger.info("get gameMultiOpen from cache");
//        int gameid = ServletRequestUtils.getIntParameter(request, "gameid", 0);
//        int openid = ServletRequestUtils.getIntParameter(request, "openid", 0);
//        List<GameMultiOpen> lg = gameMultiOpenCache.getGameMultiOpenListFrmCache(gameid, openid);
//        if(lg == null){
//            logger.error("get game multi open from ehcache error:");
//           return "/game/gameMultiOpenList";
//        }
//        //取得游戏编号对应的游戏名称         
//        for (GameMultiOpen gameMultiOpen : lg) {
//            String game_name = gameInfoCache.getGameInfoByid(gameMultiOpen.getGame_id()).getGame_name();
//            gameMultiOpen.setGame_name(game_name);
//        }
//        resModel.addAttribute("listGameMultiOpen", lg);
//        return "/game/gameMultiOpenList";
//    }
//
//    /**
//     * 修改开奖次数
//     *
//     * @param request
//     * @param gameMultiOpen
//     * @return
//     */
//    @RequestMapping(value = "/modify", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> modifyGameMultiOpen(HttpServletRequest request, GameMultiOpen gameMultiOpen) {
//        logger.info("modify GameMultiOpen");
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        int result = gmlti.updateGameMultiOpen(gameMultiOpen);
//        if (result == 0) {
//            resMap.put("result", "success");
//            resMap.put("msg", "修改成功！");
//        } else {
//            resMap.put("result", "fail");
//            resMap.put("msg", "修改失败！");
//        }
//        return resMap;
//    }
//}
