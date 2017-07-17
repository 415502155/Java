//package com.bestinfo.controller.game;
//
//import com.bestinfo.define.Draw.DrawProStatus;
//import com.bestinfo.bean.encoding.DrawProcessStatus;
//import com.bestinfo.bean.game.GameDrawInfo;
//import com.bestinfo.bean.game.GameInfo;
//import com.bestinfo.dao.page.Pagination;
//import com.bestinfo.ehcache.DrawProStatusCache;
//import com.bestinfo.redis.gamedraw.GameInfoRedis;
//import com.bestinfo.service.game.IGameDrawInfoService;
//import com.bestinfo.util.StringUtil;
//import com.bestinfo.util.TimeUtil;
//import java.text.ParseException;
//import java.util.Date;
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
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * 游戏期信息
// *
// * @author yangyuefu
// */
//@Controller
//@RequestMapping(value = "/gamedraw")
//public class GameDrawInfoController {
//
//    private static final Logger logger = Logger.getLogger(GameDrawInfoController.class);
//
//    @Resource
//    private IGameDrawInfoService gameDrawInfoService;
//
//    @Resource
//    private GameInfoRedis gameInfoCache;
//
//    @Resource
//    private DrawProStatusCache drawproStatusCache;
//
//    /**
//     * 跳转到增加游戏期次页面
//     *
//     * @return
//     */
//    @RequestMapping(value = "/2add")
//    public String toAdd() {
//        return "/gamedraw/gameDrawInfoAdd";
//    }
//
//    /**
//     * 游戏信息查询页面
//     *
//     * @return
//     */
//    @RequestMapping(value = "/2select")
//    public String toSelect() {
//        return "/gamedraw/gameDrawInfoSelect";
//    }
//
//    /**
//     * 跳转到修改期次页面,需要把期次的信息传到页面
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/2modify")
//    public String toModify(HttpServletRequest request, Model resModel) {
//        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
//        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
//        logger.info("game_id：" + game_id + ",draw_id:" + draw_id);
//        if (game_id > 0 && draw_id > 0) {
//            GameDrawInfo gameDrawInfo = gameDrawInfoService.getDrawByGameIdAndDrawId(game_id, draw_id);
//            if (gameDrawInfo != null) {
//                resModel.addAttribute("gameDrawInfo", gameDrawInfo);
//                return "/gamedraw/gameDrawInfoModify";
//            }
//        }
//        return "/gamedraw/gameDrawInfoModify";
//    }
//
//    /**
//     * 预排期
//     * 测试url：http://localhost:8080/Quantum_Manager/gamedraw/preSchedule?game_id=4&draw_no=5
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/preSchedule")
//    public @ResponseBody
//    Map<String, Object> preScheduleDraw(HttpServletRequest request) {
//        logger.info("pre schedule game draw");
//
//        Map<String, Object> resMap = new HashMap<String, Object>();
//
//        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
//        int draw_no = ServletRequestUtils.getIntParameter(request, "draw_no", 0);
//        logger.info("game_id:" + game_id + ",draw_no:" + draw_no);
//        if (game_id <= 0 || draw_no <= 0) {
//            logger.error("game_id:" + game_id + ",draw_no:" + draw_no);
//            resMap.put("code", -1);
//            resMap.put("msg", "数据异常");
//            return resMap;
//        }
//
//        int result = gameDrawInfoService.preScheduleDraw(game_id, draw_no);
//        if (result < 0) {
//            resMap.put("code", result);
//            resMap.put("msg", "排期异常");
//        } else {
//            resMap.put("code", 0);
//            resMap.put("msg", "排期成功");
//        }
//
//        return resMap;
//    }
//
//    /**
//     * 快开游戏预排期
//     * 测试url：http://localhost:8080/Quantum_Manager/gamedraw/preScheduleKAuto?game_id=13
//     *
//     * @param request
//     * @return
//     */
////    @RequestMapping(value = "/preScheduleKAuto")    
////    public @ResponseBody Map<String, Object> preScheduleKDrawAuto(HttpServletRequest request) {
////        logger.info("pre schedule game kdraw");
////
////        Map<String, Object> resMap = new HashMap<String, Object>();
////
////        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
////        if (game_id <= 0) {
////            logger.error("data error,game_id:" + game_id);
////            resMap.put("code", -1);
////            resMap.put("msg", "数据异常");
////            return resMap;
////        }
////
////        int result = gameKDrawInfoService.preScheduleKDrawAuto(game_id);
////        if (result == 0) {
////            resMap.put("code", 0);
////            resMap.put("msg", "成功");
////        } else {
////            logger.error("pre schedule game kdraw error,game_id:" + game_id);
////            resMap.put("code", result);
////            resMap.put("msg", "快开游戏排期异常");
////        }
////
////        return resMap;
////    }
//    /**
//     * 开新期 http://localhost:8080/Quantum_Manager/gamedraw/newDraw?game_id=4
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/newDraw")
//    public @ResponseBody
//    Map<String, Object> newDraw(HttpServletRequest request) {
//        logger.info("start a new draw");
//        Map<String, Object> resMap = new HashMap<String, Object>();
//
//        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
//        logger.info("game_id:" + game_id);
//
//        if (game_id <= 0) {
//            logger.error("data error,game_id:" + game_id);
//            resMap.put("code", "-3");
//            resMap.put("msg", "数据错误！");
//            return resMap;
//        }
//
//        int result = gameDrawInfoService.newDraw(game_id);
//        if (result == 0) {
//            resMap.put("code", "0");
//            resMap.put("msg", "开新期成功！");
//        }
//        if (result == -1) {
//            resMap.put("code", result);
//            resMap.put("msg", "last draw doesn't finish data check,can not start a new draw");
//        }
//        if (result == -2) {
//            resMap.put("code", result);
//            resMap.put("msg", "GameInfo is null");
//        }
//        if (result == -11) {
//            resMap.put("code", result);
//            resMap.put("msg", "there is no new draw info");
//        }
//        if (result == -12) {
//            resMap.put("code", result);
//            resMap.put("msg", "exec pro new darw error");
//        }
//        if (result == -13) {
//            resMap.put("code", result);
//            resMap.put("msg", "init T_tmn_serial_no error");
//        }
////        if (result == -14) {
////            resMap.put("code", result);
////            resMap.put("msg", "insert t_tmn_serial_no into Redis error");
////        }
////        if (result == -15) {
////            resMap.put("code", result);
////            resMap.put("msg", "DealerUser is null");
////        }
////        if (result == -16) {
////            resMap.put("code", result);
////            resMap.put("msg", "init T_current_tmn_draw_stat error");
////        }
//        if (result == -17) {
//            resMap.put("code", result);
//            resMap.put("msg", "draw info doesn't exist");
//        }
//        if (result == -18) {
//            resMap.put("code", result);
//            resMap.put("msg", "insert draw into redis error");
//        }
////        if (result == -19) {
////            resMap.put("code", result);
////            resMap.put("msg", "save current draw info into redis error");
////        }
//        if (result == -20) {
//            resMap.put("code", result);
//            resMap.put("msg", "update redis game's cur_draw_id error");
//        }
//        if (result == -21) {
//            resMap.put("code", result);
//            resMap.put("msg", "not enough new draw");
//        }
//        if (result == -22) {
//            resMap.put("code", result);
//            resMap.put("msg", "update draw status in db error");
//        }
//        if (result == -23) {
//            resMap.put("code", result);
//            resMap.put("msg", "update SynList error");
//        }
//        if (result == -24) {
//            resMap.put("code", result);
//            resMap.put("msg", "fs 3713 error");
//        }
//        if (result == -25) {
//            resMap.put("code", result);
//            resMap.put("msg", "fs 3801 error");
//        }
//        if (result == -26) {
//            resMap.put("code", result);
//            resMap.put("msg", "fs 3606 error");
//        }
//        return resMap;
//    }
//
//    /**
//     * 修改大期销售结束时间和兑奖结束时间
//     *
//     * @param request
//     * @param resModel
//     * @param drawInfo
//     * @return
//     */
//    @RequestMapping(value = "/modify")
//    public @ResponseBody
//    Map<String, Object> modifyGameDrawInfo(HttpServletRequest request, Model resModel, GameDrawInfo drawInfo) {
//        logger.info("modify game draw info");
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        try {
//            String draw_name = ServletRequestUtils.getStringParameter(request, "draw_name", "");
//            String sales_begin = ServletRequestUtils.getStringParameter(request, "sales_begin_str", "");
//            String sales_end = ServletRequestUtils.getStringParameter(request, "sales_end_str", "");
//
//            String cash_end = ServletRequestUtils.getStringParameter(request, "cash_end_str", "");
//
//            logger.info("game_id:" + drawInfo.getGame_id() + ",draw_id:" + drawInfo.getDraw_id());
//            logger.info("draw_name:" + draw_name + ",sales_begin:" + sales_begin + ",sales_end:" + sales_end + ",cash_end:" + cash_end);
//            if ("".equals(draw_name) && "".equals(sales_begin) && "".equals(sales_end) && "".equals(cash_end)) {
//                logger.error("draw_name,sales_begin,sales_end,cash_end are all null");
//                resMap.put("code", -1);
//                resMap.put("msg", "数据为空！");
//                return resMap;
//            }
//
//            //获取数据库中的期次信息
//            GameDrawInfo dbDrawInfo = gameDrawInfoService.getDrawByGameIdAndDrawId(drawInfo.getGame_id(), drawInfo.getDraw_id());
//            if (dbDrawInfo == null) {
//                logger.error("GameDrawInfo from db is null,game_id:" + drawInfo.getGame_id() + ",draw_id:" + drawInfo.getDraw_id());
//                resMap.put("code", -2);
//                resMap.put("msg", "没有此期次！");
//                return resMap;
//            }
//
//            int drawStatus = dbDrawInfo.getProcess_status();
//
//            if (!"".equals(draw_name) && !"".equals(sales_begin) && !"".equals(sales_end)) {
//                //校验部分---销售结束时间得大于销售开始时间
//                Date saleBegin = TimeUtil.parseDate_YMDT(sales_begin);
//                Date saleEnd = TimeUtil.parseDate_YMDT(sales_end);
//                if (saleBegin.after(saleEnd)) {
//                    logger.error("saleBegin is after saleEnd,game_id:" + drawInfo.getGame_id() + ",draw_id:" + drawInfo.getDraw_id());
//                    resMap.put("code", -3);
//                    resMap.put("msg", "销售开始时间不能在销售结束时间之后！");
//                    return resMap;
//                }
//
//                //更新期名、销售开始时间、销售结束时间，同步更新兑奖开始时间
//                logger.info("modify game draw's draw_name,sales_begin,sales_end");
//                dbDrawInfo.setDraw_name(draw_name);//期名
//                dbDrawInfo.setSales_begin(TimeUtil.parseDate_YMDT(sales_begin));//销售开始时间
//                dbDrawInfo.setSales_end(TimeUtil.parseDate_YMDT(sales_end));//销售结束时间
//                dbDrawInfo.setCash_begin(TimeUtil.parseDate_YMDT(sales_end));//兑奖开始时间
//            } else if (!"".equals(cash_end)) {//更新兑奖结束时间
//                //校验部分---兑奖结束时间要大于兑奖开始时间
//                //获取兑奖开始时间
//                Date cashBegin = dbDrawInfo.getCash_begin();
//                Date cashEnd = TimeUtil.parseDate_YMDT(cash_end);
//                if (cashBegin.after(cashEnd)) {
//                    logger.error("cashBegin is after cashEnd,game_id:" + drawInfo.getGame_id() + ",draw_id:" + drawInfo.getDraw_id());
//                    resMap.put("code", -4);
//                    resMap.put("msg", "兑奖开始时间不能在兑奖结束时间之后！");
//                    return resMap;
//                }
//
//                logger.info("modify game draw's cash_end");
//                dbDrawInfo.setCash_end(TimeUtil.parseDate_YMDT(cash_end));
//            }
//
//            int result = gameDrawInfoService.updateGameDrawInfo(dbDrawInfo);
//            if (result == 0) {
//                logger.info("mofidy drawinfo success");
//                resMap.put("code", 0);
//                resMap.put("msg", "修改期次成功！");
//            } else {
//                resMap.put("code", -5);
//                resMap.put("msg", "修改期次失败！");
//            }
//        } catch (ParseException e) {
//            resMap.put("code", -6);
//            resMap.put("msg", "时间格式异常！");
//        }
//        return resMap;
//    }
//
//    /**
//     * 兑奖授权
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/cashAuth")
//    public @ResponseBody
//    Map<String, Object> cashAuth(HttpServletRequest request) {
//        logger.info("cash auth for a draw");
//        Map<String, Object> resMap = new HashMap<String, Object>();
//
//        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
//        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
//        logger.info("game_id:" + game_id + ",draw_id:" + draw_id);
//
//        if (game_id <= 0 || draw_id <= 0) {
//            logger.error("data error,game_id:" + game_id + ",draw_id:" + draw_id);
//            resMap.put("code", "-5");
//            resMap.put("msg", "数据错误！");
//            return resMap;
//        }
//
//        //更新期次状态为兑奖期，并打开终端封机
//        int result = gameDrawInfoService.cashAuth(game_id, draw_id);
//        if (result == 0) {
//            resMap.put("code", "0");
//            resMap.put("msg", "兑奖授权成功！");
//        } else {
//            resMap.put("code", result);
//            resMap.put("msg", "兑奖授权错误！");
//        }
//        return resMap;
//    }
//
////    /**
////     * 批量兑奖授权
////     *
////     * @param request
////     * @return
////     */
////    @RequestMapping(value = "/cashBatchAuth")
////    public @ResponseBody
////    Map<String, Object> cashBatchAuth(HttpServletRequest request) {
////        logger.info("batch cash auth");
////        Map<String, Object> resMap = new HashMap<String, Object>();
////        List<GameDrawInfo> gameDrawList = new ArrayList<GameDrawInfo>();
////        String gameDraw = ServletRequestUtils.getStringParameter(request, "gameDraw", "");
////        logger.info("gameDraw:" + gameDraw);
////        if ("".equals(gameDraw)) {
////            logger.error("gameDraw is null");
////            resMap.put("code", "-1");
////            resMap.put("msg", "数据错误！");
////            return resMap;
////        }
////
////        String[] array = gameDraw.split(",");
////        if (array != null) {
////            for (int i = 0; i < array.length; i++) {
////                int game_id = Integer.valueOf(array[i].split("\\|")[0]);
////                int draw_id = Integer.valueOf(array[i].split("\\|")[1]);
////                if (game_id <= 0 || draw_id <= 0) {
////                    logger.error("data error,game_id:" + game_id + ",draw_id:" + draw_id);
////                    resMap.put("code", "-5");
////                    resMap.put("msg", "数据错误！");
////                    return resMap;
////                }
////                GameDrawInfo GameDrawInfo = new GameDrawInfo();
////                GameDrawInfo.setGame_id(game_id);
////                GameDrawInfo.setDraw_id(draw_id);
////                gameDrawList.add(GameDrawInfo);
////            }
////        }
////
////        //更新期次状态为兑奖期，并打开终端封机
////        int result = gameDrawInfoService.cashBatchAuth(gameDrawList);
////        if (result == 0) {
////            resMap.put("code", "0");
////            resMap.put("msg", "兑奖授权成功！");
////        } else {
////            resMap.put("code", result);
////            resMap.put("msg", "兑奖授权错误！");
////        }
////        return resMap;
////    }
//    /**
//     * 查询期信息，分页显示
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/list")
//    public String gameDrawPage(HttpServletRequest request, Model resModel) {
//        logger.info("game draw info page list");
//        Map<String, Object> params = getGameDrawInfoRequestParams(request);
//
//        Pagination<GameDrawInfo> page = gameDrawInfoService.getGameDrawInfoPageList(params);
//        List<GameDrawInfo> gamedrl = page.getRows();
//        for (GameDrawInfo gd : gamedrl) {
//            GameInfo gi = gameInfoCache.getGameInfoByid(gd.getGame_id());
//            DrawProcessStatus dp = drawproStatusCache.getDrawProstatusByid(gd.getProcess_status());
//            gd.setGameName(gi.getGame_name());
//            gd.setDrawProStatusName(dp.getProcess_status_name());
//        }
//
//        resModel.addAttribute("page", page);
//        return "/gamedraw/gameDrawInfoSelect";
//    }
//
//    /**
//     * 根据前台请求获取查询参数列表
//     *
//     * @param request
//     * @return
//     */
//    private Map<String, Object> getGameDrawInfoRequestParams(HttpServletRequest request) {
//        Map<String, Object> params = new HashMap<String, Object>();
//
//        params.put("pageNumber", ServletRequestUtils.getIntParameter(request, "pageNumber", 1));
//        params.put("pageSize", ServletRequestUtils.getIntParameter(request, "pageSize", 10));
//
//        params.put("process_status", DrawProStatus.FIRSTPROCESS);
//
//        //游戏id
//        String game_id = ServletRequestUtils.getStringParameter(request, "game_id", "");
//        if (StringUtil.notNull(game_id)) {
//            params.put("game_id", game_id);
//        }
//
//        //大期id
//        String draw_id = ServletRequestUtils.getStringParameter(request, "draw_id", "");
//        if (StringUtil.notNull(draw_id)) {
//            params.put("draw_id", draw_id);
//        }
//        return params;
//    }
//
//}
