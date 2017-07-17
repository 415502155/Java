package com.bestinfo.controller.game;

import com.bestinfo.define.Draw.DrawProStatus;
import com.bestinfo.bean.fs.QueryEtlPrizeProgress;
import com.bestinfo.bean.game.GameDrawInfo;
import com.bestinfo.bean.game.GameInfo;
import com.bestinfo.bean.stat.JackpotPoolInfo;
import com.bestinfo.bean.stat.LuckyNo;
import com.bestinfo.bean.stat.StatPrizeAnn;
import com.bestinfo.dao.game.IGameDrawInfoDao;
import com.bestinfo.dao.game.IGameInfoDao;
import com.bestinfo.info.ManagerConfig;
import com.bestinfo.service.drawLucky.IDrawLuckyCalcService;
import com.bestinfo.service.drawLucky.IDrawLuckyGambService;
import com.bestinfo.service.game.IDrawInfoCreateService;
import com.bestinfo.util.TimeUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 普通游戏开奖流程
 */
@Controller
@RequestMapping(value = "/drawlucky4eb")
public class DrawLuckyGambController4Eb {

    private final Logger logger = Logger.getLogger(DrawLuckyGambController4Eb.class);

    @Resource
    private IGameInfoDao gameInfoDao;

    @Resource
    private IGameDrawInfoDao drawInfoDao;

    @Resource
    private IDrawLuckyGambService drawLuckyGambService;

    @Resource
    private IDrawLuckyCalcService drawLuckyCalcService;

    @Resource
    private IDrawInfoCreateService drawInfoCreateService;

    @Resource
    private JdbcTemplate metaJdbcTemplate;

    /**
     * 获取游戏期状态
     *
     * @param game_id
     * @param draw_id
     * @return
     */
    private int getGameDrawStatus(int game_id, int draw_id) {
        GameDrawInfo gd = drawInfoDao.getDrawByGameIdAndDrawId(metaJdbcTemplate, game_id, draw_id);
        if (gd == null) {
            logger.error("GameDrawInfo from db is null,game_id:" + game_id + ",draw_id:" + draw_id);
            return -1;
        }
        return gd.getProcess_status();
    }

    /**
     * 校验期是否满足开奖条件
     *
     * @param game_id
     * @param draw_id
     * @param status
     * @return
     */
    private int checkGameLuckIf(int game_id, int draw_id, int status) {
        GameDrawInfo drawInfo = drawInfoDao.getDrawByGameIdAndDrawId(metaJdbcTemplate, game_id, draw_id);
        if (drawInfo == null) {
            logger.error("GameDrawInfo from db is null,game_id:" + game_id + ",draw_id:" + draw_id);
            return -1;
        }
        if (drawInfo.getProcess_status() != status) {
            logger.info("draw status is not " + status);
            return -2;
        }
        Date a = new Date();
        Date b = drawInfo.getSales_end();
        //校验期的销售时间        
        int timez = TimeUtil.secondIntervalBetween(a, b);
        if (timez < 0) {//当前开奖的时间在销售结束时间之前
            logger.error("current draw is saling,game_id:" + game_id + "\tdraw_id:" + draw_id + "\tcurrent time:" + TimeUtil.formatDate_YMDT(a) + "\tsale end time:" + TimeUtil.formatDate_YMDT(b));
            return -3;
        }
        return 0;
    }

    //查看期次状态信息，校验是否可开奖
    @RequestMapping(value = "/selectGame")
    public @ResponseBody
    Map<String, Object> selectGame(HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
        if (game_id <= 0 || draw_id <= 0) {
            logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id);
            resMap.put("code", "-1");
            resMap.put("msg", "eb数据错误！");
            return resMap;
        }
        //从db中查询期信息
        GameDrawInfo drawInfo = drawInfoDao.getDrawByGameIdAndDrawId(metaJdbcTemplate, game_id, draw_id);
        if (drawInfo == null) {
            logger.error("GameDrawInfo from db is null,game_id:" + game_id + ",draw_id:" + draw_id);
            resMap.put("code", "-2");
            resMap.put("msg", "查不到期信息！");
            return resMap;
        }
        //校验期信息状态是否为可开奖的期
        if (drawInfo.getProcess_status() < DrawProStatus.SALING || drawInfo.getProcess_status() >= DrawProStatus.TROPHYOVER) {
            logger.error("drawinfo can't draw lucky,game_id:" + game_id + ",draw_id:" + draw_id + ",drawStatus:" + drawInfo.getProcess_status());
            resMap.put("code", "-3");
            resMap.put("msg", "期不允许开奖");
            return resMap;
        }
        Date a = new Date();
        Date b = drawInfo.getSales_end();
        //校验期的销售时间        
        int timez = TimeUtil.secondIntervalBetween(a, b);
        if (timez < 0) {//当前开奖的时间在销售结束时间之前
            logger.error("current draw is saling,game_id:" + game_id + "\tdraw_id:" + draw_id + "\tcurrent time:" + TimeUtil.formatDate_YMDT(a) + "\tsale end time:" + TimeUtil.formatDate_YMDT(b));
            resMap.put("code", "-4");
            resMap.put("msg", "期销售结束时间尚未结束，不允许开奖");
            return resMap;
        }
        resMap.put("code", "0");
        resMap.put("draw_status", drawInfo.getProcess_status());
        return resMap;
    }

    //停止销售，期次状态为 [30]：当前期 时调用此方法
    @RequestMapping(value = "/stopGame")
    public @ResponseBody
    Map<String, Object> stopGame(HttpServletRequest request, Model resModel) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
        if (game_id <= 0 || draw_id <= 0) {
            logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id);
            resMap.put("code", "-4");
            resMap.put("msg", "数据错误！");
            return resMap;
        }
        int a = checkGameLuckIf(game_id, draw_id, DrawProStatus.SALING);
        if (a != 0) {
            resMap.put("code", "-1");
            resMap.put("msg", "期错误！");
            return resMap;
        }
        //终止销售      
        int drawre = drawLuckyGambService.startLucky(game_id, draw_id);
        if (drawre == 0) {
            logger.info("draw lucky process,stop game success");
            resMap.put("code", "0");
            resMap.put("msg", "终止销售成功！");
        } else {
            logger.error("draw lucky process,stop game error,game_id:" + game_id + ",draw_id:" + draw_id);
            resMap.put("code", drawre);
            resMap.put("msg", "终止销售失败！");
        }
        resMap.put("draw_status", getGameDrawStatus(game_id, draw_id));
        return resMap;
    }

    //数据同步，期次状态为 [110]：数据同步 时调用此方法
    @RequestMapping(value = "/dataSync")
    public @ResponseBody
    Map<String, Object> dataSync(HttpServletRequest request, Model resModel
    ) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
        if (game_id <= 0 || draw_id <= 0) {
            logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id);
            resMap.put("code", "-4");
            resMap.put("msg", "数据错误！");
            return resMap;
        }
        int a = checkGameLuckIf(game_id, draw_id, DrawProStatus.SYNCHBACK);
        if (a != 0) {
            resMap.put("code", "-1");
            resMap.put("msg", "期错误！");
            return resMap;
        }
        //数据同步     
        int drawre = drawLuckyGambService.dataSync(game_id, draw_id);
        if (drawre == 0) {
            logger.info("draw lucky process,data sync success");
            resMap.put("code", "0");
            resMap.put("msg", "同步数据成功！");
        } else {
            logger.error("draw lucky process,data sync error,game_id:" + game_id + ",draw_id:" + draw_id);
            resMap.put("code", drawre);
            resMap.put("msg", "同步数据失败！");
        }
        resMap.put("draw_status", getGameDrawStatus(game_id, draw_id));
        return resMap;
    }

    //终端封机
//    @RequestMapping(value = "/stopTerminal")
//    public @ResponseBody
//    Map<String, Object> stopTerminal(HttpServletRequest request ) {
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
//        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
//        if (game_id <= 0 || draw_id <= 0) {
//            logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id);
//            resMap.put("code", "-4");
//            resMap.put("msg", "数据错误！");
//            return resMap;
//        }
//        int a = checkGameLuckIf(game_id, draw_id, DrawProStatus.TERMINALGAMEOVER);
//        if (a != 0) {
//            resMap.put("code", "-1");
//            resMap.put("msg", "期错误！");
//            return resMap;
//        }
//
//        //终端封机
//        int tmnre = drawLuckyGambService.stopTerminal(game_id, draw_id);
//        if (tmnre == 0) {
//            logger.info("draw lucky process,stop terminal success");
//            resMap.put("code", "0");
//            resMap.put("msg", "封机成功！");
//        } else {
//            logger.error("draw lucky process,stop terminal error,game_id:" + game_id + ",draw_id:" + draw_id);
//            resMap.put("code", tmnre);
//            resMap.put("msg", "封机失败！");
//        }
//        resMap.put("draw_status", getGameDrawStatus(game_id, draw_id));
//        return resMap;
//    }
    //数据校验
    @RequestMapping(value = "/dataCheck")
    public @ResponseBody
    Map<String, Object> dataCheck(HttpServletRequest request
    ) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
        if (game_id <= 0 || draw_id <= 0) {
            logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id);
            resMap.put("code", "-4");
            resMap.put("msg", "数据错误！");
            return resMap;
        }
        int a = checkGameLuckIf(game_id, draw_id, DrawProStatus.DATACHECK);
        if (a != 0) {
            resMap.put("code", "-1");
            resMap.put("msg", "期错误！");
            return resMap;
        }

        //数据校验
        Map<String, Long> checkMap = drawLuckyGambService.dataCheck(game_id, draw_id,
                ManagerConfig.ETL_LOOP_COUNT, ManagerConfig.ETL_INTERVAL_TIME);
        if (checkMap.get("error") >= 0) {
            logger.info("draw lucky process,data check success");
            resMap.put("code", "0");
            resMap.put("sum_ticket_no", checkMap.get("sale_ticket_num"));
            resMap.put("und_ticket_no", checkMap.get("undo_ticket_num"));
            resMap.put("msg", "数据校验成功！");
        } else {
            logger.error("draw lucky process,data check error,game_id:" + game_id + ",draw_id:" + draw_id);
            resMap.put("code", checkMap.get("error"));
            resMap.put("msg", "数据校验失败！");
        }
        resMap.put("draw_status", getGameDrawStatus(game_id, draw_id));
        return resMap;
    }

    //期结算
    @RequestMapping(value = "/drawStat")
    public @ResponseBody
    Map<String, Object> drawStat(HttpServletRequest request
    ) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
        if (game_id <= 0 || draw_id <= 0) {
            logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id);
            resMap.put("code", "-4");
            resMap.put("msg", "数据错误！");
            return resMap;
        }
        int a = checkGameLuckIf(game_id, draw_id, DrawProStatus.STATISTICS);
        if (a != 0) {
            resMap.put("code", "-1");
            resMap.put("msg", "期错误！");
            return resMap;
        }
        //期结，返回销售额
        long sale_money = drawLuckyGambService.drawStat(game_id, draw_id);
        if (sale_money >= 0) {
            logger.info("draw lucky process,draw stat success");
            resMap.put("code", "0");
            resMap.put("sale_money", sale_money);
            resMap.put("msg", "期结算成功！");
        } else {
            logger.error("draw lucky process,draw stat error,game_id:" + game_id + ",draw_id:" + draw_id);
            resMap.put("code", sale_money);
            resMap.put("msg", "期结算失败！");
        }
        resMap.put("draw_status", getGameDrawStatus(game_id, draw_id));
        return resMap;
    }

    //期结算报表170
//    @RequestMapping(value = "/drawStatReport")
//    public @ResponseBody
//    Map<String, Object> drawStatReport(HttpServletRequest request) {
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
//        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
//        if (game_id <= 0 || draw_id <= 0) {
//            logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id);
//            resMap.put("code", "-4");
//            resMap.put("msg", "数据错误！");
//            return resMap;
//        }
//        int a = checkGameLuckIf(game_id, draw_id, DrawProStatus.STATISTICSEXCEL);
//        if (a != 0) {
//            resMap.put("code", "-1");
//            resMap.put("msg", "期错误！");
//            return resMap;
//        }
//
//        int drawl = drawLuckyGambService.drawStatReport(game_id, draw_id);
//        if (drawl == 0) {
//            logger.info("draw lucky process,draw stat report success");
//            resMap.put("code", "0");
//            resMap.put("msg", "期结算报表成功！");
//        } else {
//            logger.error("draw lucky process,draw stat report error,game_id:" + game_id + ",draw_id:" + draw_id);
//            resMap.put("code", drawl);
//            resMap.put("msg", "期结算报表失败！");
//        }
//        resMap.put("draw_status", getGameDrawStatus(game_id, draw_id));
//        return resMap;
//    }
    //生成中彩数据
//    @RequestMapping(value = "/centerData")
//    public @ResponseBody
//    Map<String, Object> centerData(HttpServletRequest request) {
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
//        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
//        if (game_id <= 0 || draw_id <= 0) {
//            logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id);
//            resMap.put("code", "-4");
//            resMap.put("msg", "数据错误！");
//            return resMap;
//        }
//        int a = checkGameLuckIf(game_id, draw_id, DrawProStatus.CENTERLOTTERYDATA);
//        if (a != 0) {
//            resMap.put("code", "-1");
//            resMap.put("msg", "期错误！");
//            return resMap;
//        }
//
//        int drawl = drawLuckyGambService.centerData(game_id, draw_id);
//        if (drawl == 0) {
//            logger.info("draw lucky process,center data success");
//            resMap.put("code", "0");
//            resMap.put("msg", "生成中彩数据成功！");
//        } else {
//            logger.error("draw lucky process,center data error,game_id:" + game_id + ",draw_id:" + draw_id);
//            resMap.put("code", drawl);
//            resMap.put("msg", "生成中彩数据失败！");
//        }
//        resMap.put("draw_status", getGameDrawStatus(game_id, draw_id));
//        return resMap;
//    }
    //生成中彩报表
//    @RequestMapping(value = "/centerDataReport")
//    public @ResponseBody
//    Map<String, Object> centerDataReport(HttpServletRequest request) {
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
//        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
//        if (game_id <= 0 || draw_id <= 0) {
//            logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id);
//            resMap.put("code", "-4");
//            resMap.put("msg", "数据错误！");
//            return resMap;
//        }
//        int a = checkGameLuckIf(game_id, draw_id, DrawProStatus.CENTERLOTTERYEXCEL);
//        if (a != 0) {
//            resMap.put("code", "-1");
//            resMap.put("msg", "期错误！");
//            return resMap;
//        }
//
//        int drawl = drawLuckyGambService.centerDataReport(game_id, draw_id);
//        if (drawl == 0) {
//            logger.info("draw lucky process,center data report success");
//            resMap.put("code", "0");
//            resMap.put("msg", "生成中彩报表成功！");
//        } else {
//            logger.error("draw lucky process,center data report error,game_id:" + game_id + ",draw_id:" + draw_id);
//            resMap.put("code", drawl);
//            resMap.put("msg", "生成中彩报表失败！");
//        }
//        resMap.put("draw_status", getGameDrawStatus(game_id, draw_id));
//        return resMap;
//    }
    //输入开奖号码,前台eb根据步骤获取开奖次数信息
    //eb输入的开奖号码用英文逗号“,”分隔
    @RequestMapping(value = "/inputLuckyNo")
    public @ResponseBody
    Map<String, Object> inputLuckyNo(HttpServletRequest request, LuckyNo luckyNo) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
        int open_id = ServletRequestUtils.getIntParameter(request, "open_id", 0);
        if (game_id <= 0 || draw_id <= 0 || open_id <= 0) {
            logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id + ",open_id:" + open_id);
            resMap.put("code", "-1");
            resMap.put("msg", "eb数据错误！");
            return resMap;
        }
        //从db中获取游戏对象
        GameInfo gameInfo = gameInfoDao.getGameInfoByGameId(metaJdbcTemplate, game_id);
        if (gameInfo == null) {
            logger.error("GameInfo from db is null,game_id:" + game_id);
            resMap.put("code", "-2");
            resMap.put("msg", "获取游戏信息失败");
            return resMap;
        }
        String game_code = gameInfo.getGame_code();

        //从db中获取期次信息
        GameDrawInfo drawInfo = drawInfoDao.getDrawByGameIdAndDrawId(metaJdbcTemplate, game_id, draw_id);
        if (drawInfo == null) {
            logger.error("GameDrawInfo from db is null,game_id:" + game_id + ",draw_id:" + draw_id);
            resMap.put("code", "-3");
            resMap.put("msg", "获取期次信息失败");
            return resMap;
        }

        //拼装开奖号码对象
        //基本号码/红球
        try {
            logger.info("luckyNo jsonObj:" + new ObjectMapper().writeValueAsString(luckyNo));
        } catch (IOException ex) {
            logger.error("", ex);
        }
        StringBuilder echoSb = new StringBuilder();//echo
        String page_prize_no = luckyNo.getPrize_no();//页面传来的基本号码
        if (page_prize_no == null || "".equals(page_prize_no)) {
            logger.error("eb data error,page_prize_no is null,game_id:" + game_id + ",draw_id:" + draw_id + ",open_id:" + open_id);
            resMap.put("code", "-4");
            resMap.put("msg", "eb数据错误！");
            return resMap;
        }

        String prize_no = page_prize_no.trim().replace(",", " ");//入库的基本号码，逗号替换为空格，不排序
        String ordered_prize_no = "";
        if ("B001".equals(game_code) || "C730".equals(game_code) || "C515".equals(game_code)) { //双色球、七乐彩、15选5，echo显示号码排序
            String[] prize = page_prize_no.trim().split(",");
            List prizeList = Arrays.asList(prize);
            Collections.sort(prizeList);
            ordered_prize_no = StringUtils.join(prizeList.toArray(), " ");
        } else {
            ordered_prize_no = prize_no;
        }
        if ("B001".equals(game_code)) {//双色球
            echoSb.append("红球:");
        }
        if ("C730".equals(game_code)) {//七乐彩
            echoSb.append("基本号码:");
        }
        if ("P61".equals(game_code)) {  //东方6+1
            echoSb.append("基本号码:");
        }
        echoSb.append(ordered_prize_no);
        echoSb.append(" ");

        //特殊号码/蓝球
        String page_special_no = luckyNo.getSpecial_no();//页面传来的特殊号码
        String special_no = "";
        if (luckyNo.getSpecial_no_num() == null) {
            luckyNo.setSpecial_no_num(0);
        }
        if (page_special_no == null || "".equals(page_special_no)) {
            special_no = " ";
        } else {
            special_no = page_special_no.trim().replace(",", " ");//入库的特殊号码，逗号替换为空格
        }
        if ("B001".equals(game_code)) {//双色球
            echoSb.append("蓝球:");
        }
        if ("C730".equals(game_code)) {//七乐彩
            echoSb.append("特别号码:");
        }
        if ("P61".equals(game_code)) {  //东方6+1
            echoSb.append("生肖码:");
        }
        echoSb.append(special_no);

        luckyNo.setPrize_no(prize_no);//基本号码
        luckyNo.setSpecial_no(special_no);//特殊号码
        luckyNo.setDraw_name(drawInfo.getDraw_name());
        luckyNo.setLucky_no(ordered_prize_no + " " + special_no);//开奖号码，基本号码排序
        luckyNo.setLucky_no_echo(echoSb.toString().trim());//echo开奖号码
        luckyNo.setKeno_draw_id(0);
        luckyNo.setKeno_draw_name("0");
        luckyNo.setGenerate_time(new Date());//开奖号码入库的当前时间

        //输入开奖号码
        int dl = drawLuckyGambService.inputLuckyNo(luckyNo);
        if (dl == 0) {
            logger.info("draw lucky process,input LuckyNo success");
            resMap.put("code", "0");
            resMap.put("msg", "输入开奖号码成功！");
        } else {
            logger.error("draw lucky process,input lucky no error,game_id:" + game_id + ",draw_id:" + draw_id);
            resMap.put("code", dl);
            resMap.put("msg", "输入开奖号码失败！");
        }
        resMap.put("draw_status", getGameDrawStatus(game_id, draw_id));
        return resMap;
    }

    //重复号码
    @RequestMapping(value = "/repeatLuckyNo")
    public @ResponseBody
    Map<String, Object> repeatLuckyNo(HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
        int open_id = ServletRequestUtils.getIntParameter(request, "open_id", 0);
        if (game_id <= 0 || draw_id <= 0 || open_id <= 0) {
            logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id + ",open_id:" + open_id);
            resMap.put("code", "-1");
            resMap.put("msg", "eb数据错误！");
            return resMap;
        }
        int a = checkGameLuckIf(game_id, draw_id, DrawProStatus.REPEATLUCKYNUMBER);
        if (a != 0) {
            resMap.put("code", "-1");
            resMap.put("msg", "期错误！");
            return resMap;
        }

        int dl = drawLuckyGambService.repeatLuckyNo(game_id, draw_id, open_id);
        if (dl == 0) {
            logger.info("draw lucky process,repeat LuckyNo success");
            resMap.put("code", "0");
            resMap.put("msg", "重复号码成功！");
        } else {
            logger.error("draw lucky process,repeat lucky no error,game_id:" + game_id + ",draw_id:" + draw_id);
            resMap.put("code", dl);
            resMap.put("msg", "重复号码失败！");
        }
        resMap.put("draw_status", getGameDrawStatus(game_id, draw_id));
        return resMap;
    }

    //抽奖检索
    @RequestMapping(value = "/drawLucky")
    public @ResponseBody
    Map<String, Object> drawLucky(HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
        int open_id = ServletRequestUtils.getIntParameter(request, "open_id", 0);
        if (game_id <= 0 || draw_id <= 0 || open_id <= 0) {
            logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id + ",open_id:" + open_id);
            resMap.put("code", "-1");
            resMap.put("msg", "eb数据错误！");
            return resMap;
        }
        int a = checkGameLuckIf(game_id, draw_id, DrawProStatus.DRAWLUCKY);
        if (a != 0) {
            resMap.put("code", "-1");
            resMap.put("msg", "期错误！");
            return resMap;
        }

        //向文件系统发送抽奖协议4103---得到中奖结果存入中奖汇总表（T_stat_keno_prize_ann或者T_stat_prize_ann）
        int drre = drawLuckyGambService.drawLucky(game_id, draw_id, open_id);
        if (drre == 0) {
            logger.info("draw lucky process,draw lucky success");
            resMap.put("code", "0");
            resMap.put("msg", "抽奖检索成功！");
        } else {
            logger.error("draw lucky process,draw lucky error,game_id:" + game_id + ",draw_id:" + draw_id);
            resMap.put("code", drre);
            resMap.put("msg", "抽奖检索失败！");
        }
        resMap.put("draw_status", getGameDrawStatus(game_id, draw_id));
        return resMap;
    }

    //打印抽奖结果
//    @RequestMapping(value = "/printDrawLucky")
//    public @ResponseBody
//    Map<String, Object> printDrawLucky(HttpServletRequest request) {
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
//        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
//        if (game_id <= 0 || draw_id <= 0) {
//            logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id);
//            resMap.put("code", "-4");
//            resMap.put("msg", "数据错误！");
//            return resMap;
//        }
//        int a = checkGameLuckIf(game_id, draw_id, DrawProStatus.DRAWRESULT);
//        if (a != 0) {
//            resMap.put("code", "-1");
//            resMap.put("msg", "期错误！");
//            return resMap;
//        }
//        int drre = drawLuckyGambService.printDrawLucky(game_id, draw_id);
//        if (drre == 0) {
//            logger.info("draw lucky process,print draw lucky success");
//            resMap.put("code", "0");
//            resMap.put("msg", "打印抽奖结果成功！");
//        } else {
//            logger.error("draw lucky process,print draw lucky error,game_id:" + game_id + ",draw_id:" + draw_id);
//            resMap.put("code", drre);
//            resMap.put("msg", "打印抽奖结果失败！");
//        }
//        resMap.put("draw_status", getGameDrawStatus(game_id, draw_id));
//        return resMap;
//    }
    //输入多次开奖号码,前台eb根据步骤获取开奖次数信息
    @RequestMapping(value = "/multiInputLuckyNo")
    public @ResponseBody
    Map<String, Object> multiInputLuckyNo(HttpServletRequest request, LuckyNo luckyNo) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
        int open_id = ServletRequestUtils.getIntParameter(request, "open_id", 0);
        if (game_id <= 0 || draw_id <= 0 || open_id <= 0) {
            logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id + ",open_id:" + open_id);
            resMap.put("code", "-1");
            resMap.put("msg", "eb数据错误！");
            return resMap;
        }
        //从db中获取游戏对象
        GameInfo gameInfo = gameInfoDao.getGameInfoByGameId(metaJdbcTemplate, game_id);
        if (gameInfo == null) {
            logger.error("GameInfo from db is null,game_id:" + game_id);
            resMap.put("code", "-2");
            resMap.put("msg", "获取游戏信息失败");
            return resMap;
        }
        String game_code = gameInfo.getGame_code();

        //从db中获取期次信息
        GameDrawInfo drawInfo = drawInfoDao.getDrawByGameIdAndDrawId(metaJdbcTemplate, game_id, draw_id);
        if (drawInfo == null) {
            logger.error("GameDrawInfo from db is null,game_id:" + game_id + ",draw_id:" + draw_id);
            resMap.put("code", "-3");
            resMap.put("msg", "获取期次信息失败");
            return resMap;
        }

        //拼装开奖号码对象
        try {
            logger.info("luckyNo jsonObj:" + new ObjectMapper().writeValueAsString(luckyNo));
        } catch (IOException ex) {
            logger.error("", ex);
        }
        //基本号码/红球
        String page_prize_no = luckyNo.getPrize_no();//页面传来的基本号码
        if (page_prize_no == null || "".equals(page_prize_no)) {
            logger.error("eb data error,page_prize_no is null,game_id:" + game_id + ",draw_id:" + draw_id + ",open_id:" + open_id);
            resMap.put("code", "-4");
            resMap.put("msg", "eb数据错误！");
            return resMap;
        }

        String prize_no = page_prize_no.trim().replace(",", " ");//入库的基本号码，逗号替换为空格，不排序
        StringBuilder echoSb = new StringBuilder();//echo
        String ordered_prize_no = "";
        if ("B001".equals(game_code) || "C730".equals(game_code)) { //双色球和七乐彩，echo号码排序
            String[] prize = page_prize_no.trim().split(",");
            List prizeList = Arrays.asList(prize);
            Collections.sort(prizeList);
            ordered_prize_no = StringUtils.join(prizeList.toArray(), " ");
        } else {
            ordered_prize_no = prize_no;
        }
        if ("B001".equals(game_code)) {//双色球
            echoSb.append("红球:");
        } else {
            echoSb.append("基本号码:");
        }
        echoSb.append(ordered_prize_no);
        echoSb.append(" ");

        //特殊号码/蓝球
        String page_special_no = luckyNo.getSpecial_no();//页面传来的特殊号码
        String special_no = "";
        if (luckyNo.getSpecial_no_num() == null) {
            luckyNo.setSpecial_no_num(0);
        }
        if (page_special_no == null || "".equals(page_special_no)) {
            special_no = " ";
        } else {
            special_no = page_special_no.replace(",", " ");//入库的特殊号码，逗号替换为空格
        }
        if ("B001".equals(game_code)) {//双色球
            echoSb.append("蓝球:");
        } else {
            echoSb.append("特别号码:");
        }
        echoSb.append(special_no);

        luckyNo.setPrize_no(prize_no);//基本号码
        luckyNo.setSpecial_no(special_no);//特殊号码
        luckyNo.setDraw_name(drawInfo.getDraw_name());
        luckyNo.setLucky_no(ordered_prize_no + " " + special_no);//开奖号码，基本号码排序
        luckyNo.setLucky_no_echo(echoSb.toString());//echo开奖号码
        luckyNo.setKeno_draw_id(0);
        luckyNo.setKeno_draw_name("0");

        //输入多次开奖号码
        int dl = drawLuckyGambService.multiInputLuckyNo(luckyNo);
        if (dl == 0) {
            logger.info("draw lucky process,multi input LuckyNo success");
            resMap.put("code", "0");
            resMap.put("msg", "输入多次开奖号码成功！");
        } else {
            logger.error("draw lucky process,multi input LuckyNo error,game_id:" + game_id + ",draw_id:" + draw_id);
            resMap.put("code", dl);
            resMap.put("msg", "输入多次开奖号码失败！");
        }
        resMap.put("draw_status", getGameDrawStatus(game_id, draw_id));
        return resMap;
    }

    //多次抽奖检索
    @RequestMapping(value = "/multidrawLucky")
    public @ResponseBody
    Map<String, Object> multiDrawLucky(HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
        int open_id = ServletRequestUtils.getIntParameter(request, "open_id", 0);
        if (game_id <= 0 || draw_id <= 0 || open_id <= 0) {
            logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id + ",open_id:" + open_id);
            resMap.put("code", "-1");
            resMap.put("msg", "eb数据错误！");
            return resMap;
        }
        int a = checkGameLuckIf(game_id, draw_id, DrawProStatus.MOREDRAW);
        if (a != 0) {
            resMap.put("code", "-1");
            resMap.put("msg", "期错误！");
            return resMap;
        }

        //向文件系统发送抽奖协议4103---得到中奖结果存入中奖汇总表（T_stat_keno_prize_ann或者T_stat_prize_ann）
        int drre = drawLuckyGambService.multiDrawLucky(game_id, draw_id, open_id);
        if (drre == 0) {
            logger.info("draw lucky process,multi draw lucky success");
            resMap.put("code", "0");
            resMap.put("msg", "多次抽奖检索成功！");
        } else {
            logger.error("draw lucky process,multi draw lucky error,game_id:" + game_id + ",draw_id:" + draw_id);
            resMap.put("code", drre);
            resMap.put("msg", "多次抽奖检索失败！");
        }
        resMap.put("draw_status", getGameDrawStatus(game_id, draw_id));
        return resMap;
    }

    //打印抽奖结果
//    @RequestMapping(value = "/multiPrintDrawLucky")
//    public @ResponseBody
//    Map<String, Object> multiPrintDrawLucky(HttpServletRequest request) {
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
//        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
//        if (game_id <= 0 || draw_id <= 0) {
//            logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id);
//            resMap.put("code", "-4");
//            resMap.put("msg", "数据错误！");
//            return resMap;
//        }
//        int a = checkGameLuckIf(game_id, draw_id, DrawProStatus.MORERESULT);
//        if (a != 0) {
//            resMap.put("code", "-1");
//            resMap.put("msg", "期错误！");
//            return resMap;
//        }
//
//        int drre = drawLuckyGambService.multiPrintDrawLucky(game_id, draw_id);
//        if (drre == 0) {
//            logger.info("draw lucky process,multi print draw lucky success");
//            resMap.put("code", "0");
//            resMap.put("msg", "打印多次抽奖结果成功！");
//        } else {
//            logger.error("draw lucky process,multi print draw lucky error,game_id:" + game_id + ",draw_id:" + draw_id);
//            resMap.put("code", drre);
//            resMap.put("msg", "打印多次抽奖结果失败！");
//        }
//        resMap.put("draw_status", getGameDrawStatus(game_id, draw_id));
//        return resMap;
//    }
    @RequestMapping(value = "/calculateMoney")
    public @ResponseBody
    Map<String, Object> calculateMoney(HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
//      int open_id = ServletRequestUtils.getIntParameter(request, "open_id", 0);
        String class_prize_str = ServletRequestUtils.getStringParameter(request, "class_prize_str", "");
        // lvchangrong
        String[] class_prize_strArray = class_prize_str.split(",");
        String open_ids = "";
        for (String str : class_prize_strArray) {
            String[] open_id_Array = str.split(":");
            open_ids += open_id_Array[0] + ",";
        }
        open_ids = open_ids.substring(0, open_ids.length() - 1);
        logger.info("game_id:" + game_id + ",draw_id:" + draw_id + ",open_ids:" + open_ids + ",class_prize_str:" + class_prize_str);
        if (game_id <= 0 || draw_id <= 0 || open_ids.length() <= 0) {
            logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id + ",open_ids:" + open_ids);
            resMap.put("code", "-1");
            resMap.put("msg", "数据错误！");
            return resMap;
        }
        int a = checkGameLuckIf(game_id, draw_id, DrawProStatus.CALCULATEMONEY);
        if (a != 0) {
            resMap.put("code", "-1");
            resMap.put("msg", "期错误！");
            return resMap;
        }

        try {
            int re = drawLuckyGambService.calculateMoney(game_id, draw_id, open_ids, class_prize_str);
            if (re == 0) {
                logger.info("draw lucky process,input money success");
                resMap.put("code", "0");
                resMap.put("msg", "计算奖金成功！");
            } else {
                logger.error("draw lucky process,input money error,game_id:" + game_id + ",draw_id:" + draw_id);
                resMap.put("code", re);
                resMap.put("msg", "计算奖金失败！");
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        resMap.put("draw_status", getGameDrawStatus(game_id, draw_id));
        return resMap;
    }

    @RequestMapping(value = "/calculateMoneyUnion")
    public @ResponseBody
    Map<String, Object> calculateMoneyUnion(HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
        logger.info("calculate money union,game_id:" + game_id + ",draw_id:" + draw_id);
        if (game_id <= 0 || draw_id <= 0) {
            logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id);
            resMap.put("code", "-1");
            resMap.put("msg", "数据错误！");
            return resMap;
        }
        int a = checkGameLuckIf(game_id, draw_id, DrawProStatus.INPUTMONEY);
        if (a != 0) {
            resMap.put("code", "-1");
            resMap.put("msg", "期状态错误！");
            return resMap;
        }

        //从db中获取游戏对象
        GameInfo gameInfo = gameInfoDao.getGameInfoByGameId(metaJdbcTemplate, game_id);
        if (gameInfo == null) {
            logger.error("GameInfo from db is null,game_id:" + game_id);
            resMap.put("code", "-2");
            resMap.put("msg", "获取游戏信息失败");
            return resMap;
        }
        String game_code = gameInfo.getGame_code();

        try {
            int re = 0;
            if (game_code.equalsIgnoreCase("C515")) {
                re = drawLuckyCalcService.calculateMoneyC515(game_id, draw_id);
            }
            if (game_code.equalsIgnoreCase("P61")) {
                re = drawLuckyCalcService.calculateMoneyP61(game_id, draw_id);
            }

            if (re == 0) {
                logger.info("draw lucky process,calculate money success");
                resMap.put("code", "0");
                resMap.put("msg", "计算奖金成功！");
            } else {
                logger.error("draw lucky process,calculate money error,game_id:" + game_id + ",draw_id:" + draw_id);
                resMap.put("code", re);
                resMap.put("msg", "计算奖金失败！");
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return resMap;
    }

    //读取奖金
    @RequestMapping(value = "/inputMoney")
    public @ResponseBody
    Map<String, Object> inputMoney(HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
        //     int open_id = ServletRequestUtils.getIntParameter(request, "open_id", 0);
        String class_prize_str = ServletRequestUtils.getStringParameter(request, "class_prize_str", "");
        // lvchangrong
        String[] class_prize_strArray = class_prize_str.split(",");
        String open_ids = "";
        for (String str : class_prize_strArray) {
            String[] open_id_Array = str.split(":");
            open_ids += open_id_Array[0] + ",";
        }
        open_ids = open_ids.substring(0, open_ids.length() - 1);

        logger.info("game_id:" + game_id + ",draw_id:" + draw_id + ",open_ids:" + open_ids + ",class_prize_str:" + class_prize_str);
        if (game_id <= 0 || draw_id <= 0 || open_ids.length() <= 0) {
            logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id
                    + ",open_ids:" + open_ids);
            resMap.put("code", "-1");
            resMap.put("msg", "数据错误！");
            return resMap;
        }
        int a = checkGameLuckIf(game_id, draw_id, DrawProStatus.INPUTMONEY);
        if (a != 0) {
            resMap.put("code", "-1");
            resMap.put("msg", "期错误！");
            return resMap;
        }

        try {
            //奖池对象
            Double sales_money = ServletRequestUtils.getDoubleParameter(request, "sales_money", 0);//当期发型彩票金额---本期投注额
            Double get_jackpot = ServletRequestUtils.getDoubleParameter(request, "get_jackpot", 0);//当期派彩金额---期末奖池
            Double begin_jackpot = ServletRequestUtils.getDoubleParameter(request, "begin_jackpot", 0);//上期奖金滚入金额---期初奖池
            Double end_jackpot = ServletRequestUtils.getDoubleParameter(request, "end_jackpot", 0);//滚入下期奖金金额---期末奖池
            JackpotPoolInfo jackpot = new JackpotPoolInfo();
            jackpot.setSales_money(new BigDecimal(sales_money));//本期投注额
            jackpot.setGet_jackpot(new BigDecimal(get_jackpot));//提取奖池
            jackpot.setBegin_jackpot(new BigDecimal(begin_jackpot));//期初奖池
            jackpot.setEnd_jackpot(new BigDecimal(end_jackpot));//期末奖池

            int re = drawLuckyGambService.inputMoney(game_id, draw_id, open_ids, class_prize_str, jackpot);
            if (re == 0) {
                logger.info("draw lucky process,input money success");
                resMap.put("code", "0");
                resMap.put("msg", "输入奖金成功！");
            } else {
                logger.error("draw lucky process,input money error,game_id:" + game_id + ",draw_id:" + draw_id);
                resMap.put("code", re);
                resMap.put("msg", "输入奖金失败！");
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        resMap.put("draw_status", getGameDrawStatus(game_id, draw_id));
        return resMap;
    }

    //多次输入奖金
    @RequestMapping(value = "/multiInputMoney")
    public @ResponseBody
    Map<String, Object> multiInputMoney(HttpServletRequest request) {
        logger.info("multi input money");
        Map<String, Object> resMap = new HashMap<String, Object>();
        //游戏期信息
        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
        int keno_draw_id = ServletRequestUtils.getIntParameter(request, "keno_draw_id", 0);
        //奖级奖金信息  2:1:2:1000,2:2:10:2345, 开奖次数:奖级编号:注数:奖金
        String class_prize_str = ServletRequestUtils.getStringParameter(request, "class_prize_str", "");
        int a = checkGameLuckIf(game_id, draw_id, DrawProStatus.MOREMONEY);
        if (a != 0) {
            resMap.put("code", "-1");
            resMap.put("msg", "期错误！");
            return resMap;
        }
        try {
            //奖池信息(双色球)
            Double sales_money = ServletRequestUtils.getDoubleParameter(request, "sales_money", 0);//当期发型彩票金额---本期投注额
            Double get_jackpot = ServletRequestUtils.getDoubleParameter(request, "get_jackpot", 0);//当期派彩金额---期末奖池
            Double begin_jackpot = ServletRequestUtils.getDoubleParameter(request, "begin_jackpot", 0);//上期奖金滚入金额---期初奖池
            Double end_jackpot = ServletRequestUtils.getDoubleParameter(request, "end_jackpot", 0);//滚入下期奖金金额---期末奖池

            //added by yfyang for c730 2016 jiajiang,20160511,C730加奖
            String c730_end_jackpot = ServletRequestUtils.getStringParameter(request, "c730_end_jackpot", "");//2016七乐彩加奖，下期派奖金额

            //added by yfyang for b001 2016 jiajiang,20160825
            String b001_1_jackpot = ServletRequestUtils.getStringParameter(request, "b001_1_jackpot", "");//2016双色球加奖一等奖下期派奖金额
            String b001_6_jackpot = ServletRequestUtils.getStringParameter(request, "b001_6_jackpot", "");//2016双色球加奖六等奖下期派奖金额

            logger.info("game_id:" + game_id + ",draw_id:" + draw_id + ",class_prize_str:" + class_prize_str);
            logger.info("sales_money:" + sales_money + ",end_jackpot:" + end_jackpot
                    + ",c730_end_jackpot:" + c730_end_jackpot
                    + ",b001_1_jackpot:" + b001_1_jackpot + ",b001_6_jackpot:" + b001_6_jackpot);
            if (game_id <= 0 || draw_id <= 0 || "".equals(class_prize_str)) {
                logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id
                        + ",class_prize_str:" + class_prize_str);
                resMap.put("code", "-4");
                resMap.put("msg", "数据错误！");
                return resMap;
            }

            //根据奖级奖金串获得普通游戏中奖汇总(T_stat_prize_ann)记录列表
            List<StatPrizeAnn> prizeAnnList = new ArrayList<StatPrizeAnn>();
            String[] class_prize_arr = class_prize_str.split(",");
            for (String class_prize : class_prize_arr) {
                String[] arr = class_prize.split(":");
                StatPrizeAnn statPrizeAnn = new StatPrizeAnn();
                statPrizeAnn.setGame_id(game_id);
                statPrizeAnn.setDraw_id(draw_id);
                statPrizeAnn.setOpen_id(Integer.parseInt(arr[0]));  //开奖次数
                statPrizeAnn.setClass_id(Integer.parseInt(arr[1]));  //奖级编号
                statPrizeAnn.setTotal_num(Integer.parseInt(arr[2]));//全国中奖注数
                statPrizeAnn.setStake_prize(new BigDecimal(arr[3])); //单注金额
                prizeAnnList.add(statPrizeAnn);
            }
            int prizeAnnListSize = prizeAnnList.size();
            if (prizeAnnListSize <= 0) {
                logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id
                        + ",class_prize_str:" + class_prize_str);
                resMap.put("code", "-4");
                resMap.put("msg", "eb数据错误！");
                return resMap;
            }

            //奖池对象
            JackpotPoolInfo jackpot = new JackpotPoolInfo();
            jackpot.setGame_id(game_id);
            jackpot.setPlay_id(0);
            jackpot.setDraw_id(draw_id);
            jackpot.setKeno_draw_id(keno_draw_id);
            jackpot.setSales_money(new BigDecimal(sales_money));
            jackpot.setGet_jackpot(new BigDecimal(get_jackpot));
            jackpot.setBegin_jackpot(new BigDecimal(begin_jackpot));
            jackpot.setEnd_jackpot(new BigDecimal(end_jackpot));

            /**
             * 加奖奖池存储在note字段，两个奖池用@表示。<Br>
             * 当一个加奖奖池为-0.01时表示该奖级加奖结束。<Br>
             * 当两个加奖奖池都结束时，note字段不再存储数据。<Br>
             * note字段数据格式如下： 1000@500 -0.01@500 1000@-0.01 空<Br>
             */
            GameInfo gameInfo = gameInfoDao.getGameInfoByGameId(metaJdbcTemplate, game_id);
            //added by yfyang for c730 2016 jiajiang,20160511,C730加奖
            if ("C730".equalsIgnoreCase(gameInfo.getGame_code()) && gameInfo.getOpen_configure_id() == 13) {//2016七乐彩加奖
                //七乐彩加奖奖池放在note字段，只有一个奖级
                if (!"".equals(c730_end_jackpot)) {
                    c730_end_jackpot = c730_end_jackpot.split("\\.")[0];
                }
                jackpot.setNote(c730_end_jackpot);
            }
            //added by yfyang for b001 2016 jiajiang,20160825
            if ("B001".equalsIgnoreCase(gameInfo.getGame_code()) && gameInfo.getOpen_configure_id() == 13) {//2016双色球加奖
                //双色球加奖奖池放在note字段，有一等奖和六等奖两个奖池，中间用@分隔
                if (!"".equals(b001_1_jackpot) && !"-0.01".equals(b001_1_jackpot)) {
                    b001_1_jackpot = b001_1_jackpot.split("\\.")[0];
                }
                if (!"".equals(b001_6_jackpot) && !"-0.01".equals(b001_6_jackpot)) {
                    b001_6_jackpot = b001_6_jackpot.split("\\.")[0];
                }
                jackpot.setNote(b001_1_jackpot + "@" + b001_6_jackpot);
            }

            int re = drawLuckyGambService.multiInputMoney(game_id, draw_id, prizeAnnList, jackpot);
            if (re == 0) {
                logger.info("draw lucky process,multi input money success");
                resMap.put("code", "0");
                resMap.put("msg", "输入多次奖金成功！");
            } else {
                logger.error("draw lucky process,multi input money error,game_id:" + game_id + ",draw_id:" + draw_id);
                resMap.put("code", -1);
                resMap.put("msg", "输入多次奖金失败！");
            }

            resMap.put("draw_status", getGameDrawStatus(game_id, draw_id));
            return resMap;
        } catch (Exception ex) {
            logger.error("ex", ex);
            resMap.put("draw_status", getGameDrawStatus(game_id, draw_id));
            return resMap;
        }
    }

    //中奖分布
    @RequestMapping(value = "/luckyDistribute")
    public @ResponseBody
    Map<String, Object> luckyDistribute(HttpServletRequest request, Model resModel) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
        int open_id = ServletRequestUtils.getIntParameter(request, "open_id", 0);
        if (game_id <= 0 || draw_id <= 0 || open_id <= 0) {
            logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id + ",open_id:" + open_id);
            resMap.put("code", "-4");
            resMap.put("msg", "eb数据错误");
            return resMap;
        }
        int a = checkGameLuckIf(game_id, draw_id, DrawProStatus.LUCKYDISTRIBUTE);
        if (a != 0) {
            resMap.put("code", "-1");
            resMap.put("msg", "期错误！");
            return resMap;
        }

        //中奖分布
        int re = drawLuckyGambService.luckyDistribute(game_id, draw_id, 0, open_id);
        if (re == 0) {
            logger.info("draw lucky process,lucky distribute success");
            resMap.put("code", 0);
            resMap.put("msg", "中奖分布成功！");
        } else {
            logger.error("draw lucky process,lucky distribute error,game_id:" + game_id + ",draw_id:" + draw_id + ",open_id:" + open_id);
            resMap.put("code", -8);
            resMap.put("msg", "中奖分布失败！");
        }
        resMap.put("draw_status", getGameDrawStatus(game_id, draw_id));
        return resMap;
    }

    //开奖结束
    @RequestMapping(value = "/luckyEnd")
    public @ResponseBody
    Map<String, Object> luckyEnd(HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
        if (game_id <= 0 || draw_id <= 0) {
            logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id);
            resMap.put("code", "-4");
            resMap.put("msg", "eb数据错误");
            return resMap;
        }
        logger.info("draw lucky process,lucky end success");
        resMap.put("code", "0");
        resMap.put("msg", "开奖结束成功！");
        resMap.put("draw_status", getGameDrawStatus(game_id, draw_id));
        return resMap;
    }

    //中奖分布
    @RequestMapping(value = "/multiLuckyDistribute")
    public @ResponseBody
    Map<String, Object> multiLuckyDistribute(HttpServletRequest request, Model resModel) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
        int open_id = ServletRequestUtils.getIntParameter(request, "open_id", 0);

        if (game_id <= 0 || draw_id <= 0 || open_id <= 0) {
            logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id + ",open_id:" + open_id);
            resMap.put("code", "-4");
            resMap.put("msg", "eb数据错误");
            return resMap;
        }
        int a = checkGameLuckIf(game_id, draw_id, DrawProStatus.MORELUCKYDISTRIBUTE);
        if (a != 0) {
            resMap.put("code", "-1");
            resMap.put("msg", "期错误！");
            return resMap;
        }

        //多次分布
        int re = drawLuckyGambService.multiLuckyDistribute(game_id, draw_id, 0, open_id);
        if (re == 0) {
            logger.info("draw lucky process,multi lucky distribute success");
            resMap.put("code", 0);
            resMap.put("msg", "多次分布成功！");
        } else {
            logger.error("draw lucky process,multi lucky distribute error,game_id:" + game_id + ",draw_id:" + draw_id + ",open_id:" + open_id);
            resMap.put("code", -8);
            resMap.put("msg", "多次分布失败！");
        }
        resMap.put("draw_status", getGameDrawStatus(game_id, draw_id));
        return resMap;
    }

    //中奖数据入库
    @RequestMapping(value = "/luckyData")
    public @ResponseBody
    Map<String, Object> luckyData(HttpServletRequest request, Model resModel) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
        int open_id = ServletRequestUtils.getIntParameter(request, "open_id", 0);
        if (game_id <= 0 || draw_id <= 0 || open_id <= 0) {
            logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id + ",open_id:" + open_id);
            resMap.put("code", "-4");
            resMap.put("msg", "eb数据错误");
            return resMap;
        }
        int a = checkGameLuckIf(game_id, draw_id, DrawProStatus.LUCKYDATA);
        if (a != 0) {
            resMap.put("code", "-1");
            resMap.put("msg", "期错误！");
            return resMap;
        }

        int drre = drawLuckyGambService.luckyData(game_id, draw_id, 0, open_id);
        if (drre == 0) {
            logger.info("draw lucky process,lucky data success");
            resMap.put("code", "0");
            resMap.put("msg", "中奖数据入库成功！");
        } else {
            logger.error("draw lucky process,lucky data error,game_id:" + game_id + ",draw_id:" + draw_id + ",open_id:" + open_id);
            resMap.put("code", drre);
            resMap.put("msg", "中奖数据入库失败！");
        }
        resMap.put("draw_status", getGameDrawStatus(game_id, draw_id));
        return resMap;
    }

    //生成开奖公告，包括xml文件和数据库记录
    @RequestMapping(value = "/createPrize")
    public @ResponseBody
    Map<String, Object> createPrize(HttpServletRequest request, Model resModel) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        // int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
        String draw_name = ServletRequestUtils.getStringParameter(request, "draw_name", "0");
        if (game_id <= 0 || "0".equals(draw_name)) {
            logger.error("eb data error,game_id:" + game_id + ",draw_name:" + draw_name);
            resMap.put("code", "-1");
            resMap.put("msg", "eb数据错误");
            return resMap;
        }
        GameDrawInfo gameDrawInfo = drawInfoDao.getDrawByGameIdAndDrawName(metaJdbcTemplate, game_id, draw_name);
        if (gameDrawInfo == null) {
            logger.error("the gameDrawInfo is null in meta DB where game_id = " + game_id + "  draw_name = " + draw_name);
            resMap.put("code", "-3");
            resMap.put("msg", "期信息为空");
            return resMap;
        }
        int draw_id = gameDrawInfo.getDraw_id();
        int a = drawLuckyGambService.createPrizeFile(game_id, draw_id);
        if (a == 0) {
            resMap.put("code", "0");
            resMap.put("msg", "开奖公告成功！");
        } else {
            resMap.put("code", "-1");
            resMap.put("msg", "开奖公告失败！");
        }
        return resMap;
    }

    //查询中奖数据入库进度
    @RequestMapping(value = "/queryEtlPrize")
    public @ResponseBody
    Map<String, Object> queryEtlPrize(HttpServletRequest request, Model resModel) {
        logger.info("draw lucky process,query etl prize progress");
        Map<String, Object> resMap = new HashMap<String, Object>();

        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
        int open_id = ServletRequestUtils.getIntParameter(request, "open_id", 0);
//        if (game_id <= 0 || draw_id <= 0 || open_id <= 0) {
//            logger.error("eb data error,game_id:" + game_id + ",draw_id:" + draw_id + ",open_id:" + open_id);
//            resMap.put("code", "-4");
//            resMap.put("msg", "eb数据错误");
//            return resMap;
//        }

        QueryEtlPrizeProgress etlPrizeProgress = new QueryEtlPrizeProgress();
        drawLuckyGambService.queryEtlPrize(etlPrizeProgress, game_id, draw_id, 0, open_id);
        resMap.put("etlPrizeProgress", etlPrizeProgress);
        return resMap;
    }

    /**
     * 下期新期
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/newDraw")
    public @ResponseBody
    Map<String, Object> newDraw(HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        int draw_id = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
        if (game_id <= 0 || draw_id <= 0) {
            logger.error("data error,game_id:" + game_id + ",draw_id:" + draw_id);
            resMap.put("code", "-3");
            resMap.put("msg", "eb数据错误！");
            return resMap;
        }
        int a = checkGameLuckIf(game_id, draw_id, DrawProStatus.NEWDRAW);
        if (a != 0) {
            resMap.put("code", "-1");
            resMap.put("msg", "期错误！");
            return resMap;
        }

        int result = drawLuckyGambService.newDraw(game_id, draw_id);
        if (result == 0) {
            resMap.put("code", "0");
            resMap.put("msg", "开下期新期成功！");
        } else {
            resMap.put("code", "-1");
            resMap.put("msg", "开下期新期失败！");
        }
        return resMap;
    }

    /**
     * 读无纸化接入销售统计文件
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/loadSystemSaleStatFile")
    public @ResponseBody
    Map<String, Object> loadSystemSaleStatFile(HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int gameId = ServletRequestUtils.getIntParameter(request, "gameId", 0);
        int systemId = ServletRequestUtils.getIntParameter(request, "systemId", 0);
        String drawName = ServletRequestUtils.getStringParameter(request, "drawName", "");
        if (gameId <= 0 || systemId <= 0 || "".equals(drawName)) {
            logger.error("data error,gameId:" + gameId + ",systemId:" + systemId + " ,drawName:" + drawName);
            resMap.put("code", "-10");
            resMap.put("msg", "eb数据错误！");
            return resMap;
        }

        int result = drawLuckyGambService.loadSystemSaleStatFile(gameId, systemId, drawName);
        resMap.put("code", result);
        switch (result) {
            case 0:
                resMap.put("msg", "读系统接入商销售统计文件到数据库成功");
                break;
            case -1:
                resMap.put("msg", "文件名为空");
                break;
            case -2:
                resMap.put("msg", "游戏期信息获取失败");
                break;
            case -3:
                resMap.put("msg", "文件不存在");
                break;
            case -4:
                resMap.put("msg", "期名或系统接入商Id与文件中内容不符合");
                break;
            case -5:
                resMap.put("msg", "保存数据到数据库库失败");
                break;
            case -6:
                resMap.put("msg", "读文件发生异常");
                break;
            default:
                resMap.put("msg", "发生错误");
        }

        return resMap;
    }

    /**
     * 读无纸化接入中奖统计文件
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/loadSystemPrizeStatFile")
    public @ResponseBody
    Map<String, Object> loadSystemPrizeStatFile(HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int gameId = ServletRequestUtils.getIntParameter(request, "gameId", 0);
        int systemId = ServletRequestUtils.getIntParameter(request, "systemId", 0);
        String drawName = ServletRequestUtils.getStringParameter(request, "drawName", "");
        if (gameId <= 0 || systemId <= 0 || "".equals(drawName)) {
            logger.error("data error,gameId:" + gameId + ",systemId:" + systemId + " ,drawName:" + drawName);
            resMap.put("code", "-10");
            resMap.put("msg", "eb数据错误！");
            return resMap;
        }

        int result = drawLuckyGambService.loadSystemPrizeStatFile(gameId, systemId, drawName);
        resMap.put("code", result);
        switch (result) {
            case 0:
                resMap.put("msg", "读系统接入商中奖统计文件到数据库成功");
                break;
            case -1:
                resMap.put("msg", "文件名为空");
                break;
            case -2:
                resMap.put("msg", "游戏期信息获取失败");
                break;
            case -3:
                resMap.put("msg", "文件不存在");
                break;
            case -4:
                resMap.put("msg", "期名或系统接入商Id与文件中内容不符合");
                break;
            case -5:
                resMap.put("msg", "奖级列表数据为空");
                break;
            case -6:
                resMap.put("msg", "保存数据到数据库库失败");
                break;
            case -7:
                resMap.put("msg", "读文件发生异常");
                break;
            default:
                resMap.put("msg", "发生错误");
        }

        return resMap;
    }

    /**
     * 生成无纸化中奖统计文件
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/createPlasPrizeFile")
    public @ResponseBody
    Map<String, Object> createPlasPrizeFile(HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int gameId = ServletRequestUtils.getIntParameter(request, "gameId", 0);
        int systemId = ServletRequestUtils.getIntParameter(request, "systemId", 0);
        String drawName = ServletRequestUtils.getStringParameter(request, "drawName", "");
        if (gameId <= 0 || systemId <= 0 || "".equals(drawName)) {
            logger.error("data error,gameId:" + gameId + ",systemId:" + systemId + " ,drawName:" + drawName);
            resMap.put("code", "-10");
            resMap.put("msg", "eb数据错误！");
            return resMap;
        }

        int result = drawLuckyGambService.CreatePlasPrizeStatFile(gameId, drawName, systemId);
        resMap.put("code", result);
        switch (result) {
            case 0:
                resMap.put("msg", "生成无纸化中奖统计文件成功");
                break;
            case -1:
                resMap.put("msg", "游戏期信息获取失败");
                break;
            case -2:
                resMap.put("msg", "中奖信息查询失败");
                break;
            case -3:
                resMap.put("msg", "无纸化接入商销售金额查询失败");
                break;
            case -4:
                resMap.put("msg", "无纸化接入商中奖数查询失败");
                break;
            case -5:
                resMap.put("msg", "文件生成失败");
                break;
            case -6:
                resMap.put("msg", "写文件失败");
                break;
            case -7:
                resMap.put("msg", "发生异常");
                break;
            default:
                resMap.put("msg", "发生错误");
        }

        return resMap;
    }

    /**
     * 生成无纸化新期文件
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/createDrawInfoFile")
    public @ResponseBody
    Map<String, Object> createDrawInfoFile(HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int gameId = ServletRequestUtils.getIntParameter(request, "gameId", 0);
        if (gameId <= 0) {
            logger.error("data error,gameId:" + gameId);
            resMap.put("code", "-10");
            resMap.put("msg", "eb数据错误！");
            return resMap;
        }

        int result = drawInfoCreateService.generateDrawKDrawInfoFile(gameId);
        resMap.put("code", result);
        switch (result) {
            case 0:
                resMap.put("msg", "生成无纸化新期文件成功");
                break;
            case -1:
                resMap.put("msg", "游戏期信息获取失败");
                break;
            case -2:
                resMap.put("msg", "新期文件路径获取失败");
                break;
            case -3:
                resMap.put("msg", "新期文件生成失败");
                break;
            case -4:
                resMap.put("msg", "发生异常");
                break;
            default:
                resMap.put("msg", "发生错误");
        }

        return resMap;
    }

    public static void main(String[] args) {
        String tt = "0.0000";
        System.out.println(tt.split("\\.")[0]);
    }
}
