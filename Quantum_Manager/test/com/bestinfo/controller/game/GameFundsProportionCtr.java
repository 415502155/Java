//package com.bestinfo.controller.game;
//
//import com.bestinfo.bean.game.GameFundsProportion;
//import com.bestinfo.ehcache.game.GameInfoCache;
//import com.bestinfo.service.game.IGameDrawInfoService;
//import com.bestinfo.service.game.IGameFundsProportionSer;
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
// * 游戏资金比例
// *
// * @author chenliping
// */
//@Controller
//@RequestMapping(value = "/gameFundsPropor")
//public class GameFundsProportionCtr {
//
//    private final Logger logger = Logger.getLogger(GameFundsProportionCtr.class);
//
//    @Resource
//    private IGameFundsProportionSer gfundser;
//    @Resource
//    private GameInfoCache gameInfoCache;
//    @Resource
//    private IGameDrawInfoService gameDrawInfoService;
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
//        return "/game/gameFundsProportionList";
//    }
//
//    /**
//     * 资金分配数据列表
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    public String getGameFundsProportion(HttpServletRequest request, Model resModel) {
//        logger.info("get gameFundsProportion");
//        int gameid = ServletRequestUtils.getIntParameter(request, "gameid", 0);
//        List<GameFundsProportion> listgp;
//        listgp = gfundser.getGameFundsProportionById(gameid);
//        //取得游戏编号对应的游戏名称
//        String game_name;  //游戏名称
//        String draw_name; //期名称
//        for (GameFundsProportion gameFundsProportion : listgp) {
//            game_name = gameInfoCache.getGameInfoByid(gameFundsProportion.getGame_id()).getGame_name();
//            draw_name = gameDrawInfoService.getDrawByGameIdAndDrawId(gameFundsProportion.getGame_id(), gameFundsProportion.getDraw_id()).getDraw_name();
//            gameFundsProportion.setGame_name(game_name);
//            gameFundsProportion.setDraw_name(draw_name);
//        }
//        resModel.addAttribute("listGameFundsProportion", listgp);
//        return "/game/gameFundsProportionList";
//    }
//
//    /**
//     * 跳转到添加页面
//     *
//     * @return
//     */
//    @RequestMapping(value = "/2add", method = RequestMethod.GET)
//    public String toAdd() {
//        return "/game/gameFundsProportionAdd";
//    }
//
//    /**
//     * 添加资金分配信息
//     *
//     * @param request
//     * @param ameFundsProportion
//     * @return
//     */
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> addGameLuckyRule(HttpServletRequest request, GameFundsProportion ameFundsProportion) {
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        int re = gfundser.insertGameFundsProportion(ameFundsProportion);
//        if (re < 0) {
//            resMap.put("result", "error");
//            resMap.put("msg", "添加失败");
//        } else {
//            resMap.put("result", "success");
//            resMap.put("msg", "添加成功");
//        }
//        return resMap;
//    }
//
//    /**
//     * 跳转到编辑页面
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/2modify", method = RequestMethod.GET)
//    public String toModify(HttpServletRequest request, Model resModel) {
//        logger.info("go to modify");
//        int gameid = ServletRequestUtils.getIntParameter(request, "gameid", 0);
//        int drawid = ServletRequestUtils.getIntParameter(request, "drawid", 0);
//        GameFundsProportion gfp = gfundser.getGameFundsProportionByIds(gameid, drawid);
//        //取得游戏编号对应的游戏名称
//        //游戏名称
//        String game_name = gameInfoCache.getGameInfoByid(gfp.getGame_id()).getGame_name();
//        // 期名称
//        String draw_name = gameDrawInfoService.getDrawByGameIdAndDrawId(gfp.getGame_id(), gfp.getDraw_id()).getDraw_name();
//        gfp.setGame_name(game_name);
//        gfp.setDraw_name(draw_name);
//        resModel.addAttribute("gfp", gfp);
//        return "/game/gameFundsProportionModify";
//    }
//
//    /**
//     * 编辑操作
//     *
//     * @param request
//     * @param gameFundsProportion
//     * @return
//     */
//    @RequestMapping(value = "/modify", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> updateGameRaffleRule(HttpServletRequest request, GameFundsProportion gameFundsProportion) {
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        int re = gfundser.updateGameFundsProportion(gameFundsProportion);
//        if (re < 0) {
//            resMap.put("result", "error");
//            resMap.put("msg", "修改失败");
//        } else {
//            resMap.put("result", "success");
//            resMap.put("msg", "修改成功");
//        }
//        return resMap;
//    }
//
////    @RequestMapping(value = "/select", method = RequestMethod.POST)    
////    public String getGameMultiOpen(HttpServletRequest request,Model resModel) {
////        int gameid = ServletRequestUtils.getIntParameter(request, "gameName", 0);
//////        if (gameid == 0) {
//////            resMap.put("result", "请选择游戏");
//////            return resMap;
//////        }
////        String dbSource = "lotteryBaseDB";
////        JdbcTemplate jdbcTemplate = this.findJdbcTemplate(dbSource);
////        GameFundsProportion lgrff = gfundser.getGameFundsProportion(jdbcTemplate, gameid);
////        
////        resModel.addAttribute("gameFunds", lgrff);
//////        if (lgrff == null || lgrff.isEmpty()) {
//////            resMap.put("result", "没有数据");
//////        }else{
//////            resMap.put("gameluckylist", lgrff);
//////        }
////        return "/game/gameFundsProportion";
////    }
//}
