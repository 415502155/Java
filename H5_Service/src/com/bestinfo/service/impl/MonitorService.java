package com.bestinfo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bestinfo.bean.h5.monitor.CityTmnCount;
import com.bestinfo.bean.h5.monitor.CurrentTmnDrawStat;
import com.bestinfo.bean.h5.monitor.EveryCityTmnSaleInfo;
import com.bestinfo.bean.h5.monitor.HMonitorArea;
import com.bestinfo.bean.h5.monitor.HMonitorBetsByCity;
import com.bestinfo.bean.h5.monitor.HMonitorGame;
import com.bestinfo.bean.h5.monitor.HRealTimePay;
import com.bestinfo.bean.h5.monitor.HMonitorKenoGame;
import com.bestinfo.bean.h5.monitor.HMonitorTmnInfo;
import com.bestinfo.bean.h5.monitor.TmnMaxSaleCountTop5;
import com.bestinfo.bean.session.SessionInfo;
import com.bestinfo.dao.h5.monitor.IBettingMachineMonitoringDao;
import com.bestinfo.dao.h5.monitor.ICurrentTmnDrawStatDao;
import com.bestinfo.dao.h5.monitor.IMonitorAreaDao;
import com.bestinfo.dao.h5.monitor.IMonitorGameDao;
import com.bestinfo.dao.h5.monitor.IMonitorRealTimePayDao;
import com.bestinfo.dao.h5.monitor.IMonitorKenoGameDao;
import com.bestinfo.dao.page.Pagination;
import com.bestinfo.define.h5.ReturnMsg;
import com.bestinfo.redis.login.TerminalLoginRedis;
import com.bestinfo.redis.monitor.TerminalMonitorRedis;
import com.bestinfo.service.h5.inter.IComponentsService;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 监控Service （CMD 102）
 *
 * @author Administrator
 */
@Service("MonitorService")
public class MonitorService implements IComponentsService {

    private static final Logger logger = Logger.getLogger(MonitorService.class);

    @Resource
    private IMonitorGameDao imonitorDao;    //游戏监控Dao
    @Resource
    private IMonitorKenoGameDao imonitorKenoGameDao;    //快开游戏监控Dao
    @Resource
    private JdbcTemplate termJdbcTemplate;  //term库
    @Resource
    private JdbcTemplate vaniJdbcTemplate;  //vani库
    @Resource
    private IMonitorRealTimePayDao imonitorRealTimePayDao;  //实时缴款账户监控dao
    @Resource
    private IBettingMachineMonitoringDao iBettingMachineMonitoringDao;  //投注机监控dao
    @Resource
    private JdbcTemplate metaJdbcTemplate;  //meta库
    @Resource
    private IMonitorAreaDao imonitorAreaDao;
    @Resource
    private TerminalMonitorRedis tmnMonitorRedis;
    @Resource
    private ICurrentTmnDrawStatDao iCurrentTmnDrawStatDao;
    @Resource
    private TerminalLoginRedis terminalLoginRedis;
    @Override
    public JSONObject execute(JSONObject json, String ip) {
        int cmd = json.getIntValue("CMD");
        int type = json.getIntValue("TYPE");
        logger.info("cmd:" + cmd + "/" + type);

        JSONObject backJson = new JSONObject();
        backJson.put("CMD", cmd);
        backJson.put("TYPE", type);

        switch (type) {
            case 1:
                backJson = monitorArea(json, ip);
                break;
            case 2:
                backJson = monitorGame(json, ip);
                break;
            case 3:
                backJson = monitorBettingMachine(json, ip);
                break;
            case 4:
                backJson = monitorKenoGame(json, ip);
                break;
            case 5:
                backJson = monitorRealTimePay(json, ip);
                break;
            case 6:
                backJson = monitorCurrentDrawSales(json, ip);
                break;
            case 7:
                backJson = monitorTerminalSalesTop5(json, ip);
                break;
            case 8:
                backJson = monitorCityTmnCountInfo(json, ip);
                break;
            case 9:
                backJson = monitorBettingMachineInitCityOne(json, ip);
                break;
            case 10:
                backJson = monitorBettingMachineInitEveryCity(json, ip);
                break;
            case 11:
                backJson = monitorCurDayGameId7And9SumSales(json,ip);
                break;
            case 12:
                backJson = monitorGetCurrentSysTime(json,ip);
                break;
            case 13: //监控-业务-投注机实时监控页面（13-16  17为初始化该页面）
                backJson = monitorCurrentDateInformationForEachColourSpecies(json,ip);
                break;    
           case 14:
                backJson = monitorBettingAmountOfProvinceCityOrCity(json,ip);
                break; 
           case 15:
                backJson = monitorCityAndColorBettingMachinesDetails(json,ip);
                break;
           case 16:
                backJson = monitorCurrentBettingOpportunity(json,ip);
                break;
            case 17:
                backJson = monitorInitBettingMachineRealTime(json,ip);
                break; 
            case 18:
                backJson = monitorBettingMachineInitData(json,ip);
                break;
          
        }
        return backJson;
    }

    /**
     * 游戏监控
     *
     * @param json
     * @param ip
     * @return
     */
    private JSONObject monitorGame(JSONObject json, String ip) {

        JSONObject backJson = new JSONObject();
        backJson.put("CMD", json.get("CMD"));
        backJson.put("TYPE", json.get("TYPE"));
        try {
            logger.info("json from request: " + json);
            List<HMonitorGame> hmonitorGameList = imonitorDao.getMonitorGameData(termJdbcTemplate);
            if (hmonitorGameList == null || hmonitorGameList.isEmpty()) {
                logger.error("There is no monitorGame data");
                backJson.put("GAME_DATA", ReturnMsg.QUERY_MONITOR_GAME_DATA_FAILED.getCode());
                backJson.put("RESULT", ReturnMsg.QUERY_MONITOR_GAME_DATA_FAILED.getMsg());
            } else {
                backJson.put("GAME_DATA", hmonitorGameList);
            }
            JSONArray jsonArray = new JSONArray();
            for (HMonitorGame monitorGameData : hmonitorGameList) {
                JSONObject tempJson = new JSONObject();
                tempJson.put("GAME_ID", monitorGameData.getGame_id());
                tempJson.put("GAME_NAME", monitorGameData.getGame_name());
                tempJson.put("DRAW_ID", monitorGameData.getDraw_id());
                tempJson.put("DRAW_NAME", monitorGameData.getDraw_name());
                tempJson.put("SALES_END", monitorGameData.getSales_end());
                tempJson.put("PROCESS_STATUS_NAME", monitorGameData.getProcess_status_name());
                tempJson.put("SALE_MONEY", monitorGameData.getSale_money());
                tempJson.put("PROCESS_STATUS", monitorGameData.getProcess_status());
                jsonArray.add(tempJson);
            }

            backJson.put("CODE", 0);
            backJson.put("RESULT", ReturnMsg.SUCCESS.getMsg());
            return backJson;
        } catch (Exception e) {
            logger.error("Class MnitorService's monitorGame() has an exception: ", e);
            backJson.put("CODE", ReturnMsg.MONITOR_GAME_EXCEPTION.getCode());
            backJson.put("RESULT", ReturnMsg.MONITOR_GAME_EXCEPTION.getMsg());
        }
        return backJson;
    }

    /**
     * 区域监控
     *
     * @param json
     * @param ip
     * @return
     */
    private JSONObject monitorArea(JSONObject json, String ip) {
        JSONObject backJson = new JSONObject();
        backJson.put("CMD", json.get("CMD"));
        backJson.put("TYPE", json.get("TYPE"));
        logger.info("start time:"+new Date().getTime());
        try {
            //如果选择年销量，设时间销量标志为TIME_FLAG ==1 为年销量
            if (json.getInteger("TIME_FLAG") == 1) {                
                Integer yearBegin = json.getInteger("YEAR_BEGIN");//设文本框name为YEAR_BEGIN和YEAR_END
                Integer yearEnd = json.getInteger("YEAR_END");
                logger.info("start  yearSales .......................");
                List<HMonitorArea> yearSales = imonitorAreaDao.getYearSales(vaniJdbcTemplate, yearBegin, yearEnd);//截止當前周以前的總銷量
                if (yearSales == null || yearSales.isEmpty()) {
                    backJson.put("AreaMonitorInfo", 0);
                    logger.error("area monitor year sales is null.");
                } else {
                    backJson.put("AreaMonitorInfo", yearSales);
                }
                Integer curWeekNum = imonitorAreaDao.getCurWeekNum(vaniJdbcTemplate);//當前時間是第幾周
                logger.info("curWeekNum:"+curWeekNum);
                if (curWeekNum == 0) {
                    backJson.put("CODE", ReturnMsg.QYERY_CURWEEKNUM_FAILED.getCode());
                    backJson.put("MSG", ReturnMsg.QYERY_CURWEEKNUM_FAILED.getMsg());
                    return backJson;
                } else if (curWeekNum == 1) {
                    Integer curWeekAndMaxWeekSum = 0;
                    backJson.put("curWeekAndMaxWeekSum", curWeekAndMaxWeekSum);
                } else {
                    Integer curWeekAndMaxWeekSum = imonitorAreaDao.getCurWeekAndMaxWeekSum(vaniJdbcTemplate);
                    List<HMonitorArea> lastWeekSales = imonitorAreaDao.getLastWeekSales(vaniJdbcTemplate);
                    if (lastWeekSales == null || lastWeekSales.isEmpty()) {
                        logger.error("lastWeekSales is null.");
                        backJson.put("lastWeekSales", 0);
                        backJson.put("MSG", ReturnMsg.QUERY_LASTWEEKNUM_FAILED.getMsg());
                    }
                    List<HMonitorArea> lastWeekSumSales = imonitorAreaDao.getLastWeekSumSales(vaniJdbcTemplate);
                    if (lastWeekSumSales == null || lastWeekSumSales.isEmpty()) {
                        logger.error("lastWeekSumSales is null.");
                        backJson.put("lastWeekSumSales", 0);
                        //backJson.put("CODE", ReturnMsg.QUERY_LASTWEEKSUMSALES_FAILED.getCode());
                        backJson.put("MSG", ReturnMsg.QUERY_LASTWEEKSUMSALES_FAILED.getMsg());
                        return backJson;
                    }
                    backJson.put("curWeekAndMaxWeekSum", curWeekAndMaxWeekSum);
                    backJson.put("lastWeekSales", lastWeekSales);
                    backJson.put("lastWeekSumSales", lastWeekSumSales);
                }
                backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
                backJson.put("MSG", ReturnMsg.SUCCESS.getMsg());
                logger.info("backJson info:" + backJson);
            }
        } catch (Exception e) {
            logger.error("Class MnitorService's monitorGame() has an exception: ", e);
            backJson.put("CODE", ReturnMsg.MONITOR_GAME_EXCEPTION.getCode());
            backJson.put("RESULT", ReturnMsg.MONITOR_GAME_EXCEPTION.getMsg());
        }
        logger.info("end time:"+new Date().getTime());
        return backJson;
    }

    /**
     * 实时缴款账户监控
     *
     * @param json
     * @param ip
     * @return
     */
    private JSONObject monitorRealTimePay(JSONObject json, String ip) {
        JSONObject backJson = new JSONObject();
        backJson.put("CMD", json.get("CMD"));
        backJson.put("TYPE", json.get("TYPE"));
        try {
            logger.info("json from request: " + json);
            String userName = json.getString("USER_NAME");
            Integer terminalId = json.getInteger("TERMINAL_ID");
            Integer cityId = json.getInteger("CITY_ID");
            Integer order = json.getInteger("ORDER");
            if (userName == null || userName.equals("")) {
                logger.error("userName is null " + userName);
                backJson.put("CODE", ReturnMsg.REAL_TIME_PARAMS_ERROR.getCode());
                backJson.put("RESULT", ReturnMsg.REAL_TIME_PARAMS_ERROR.getMsg());
                return backJson;
            }
            //查询当前登录城市，meta库
            Integer loginCity = imonitorRealTimePayDao.getLoginCityId(metaJdbcTemplate, userName);
            if (loginCity == null) {
                logger.error("Query login city failed.");
                backJson.put("CODE", ReturnMsg.QUERY_LOGIN_CITY_FAILED.getCode());
                backJson.put("RESULT", ReturnMsg.QUERY_LOGIN_CITY_FAILED.getMsg());
                return backJson;
            }
            if (cityId == null) {
                cityId = loginCity.intValue();
                logger.info("city_id: " + cityId);
            }
            //查询城市名称，vani库
            List<HRealTimePay> listCityInfo = imonitorRealTimePayDao.getCityName(vaniJdbcTemplate, cityId);
            if (listCityInfo == null || listCityInfo.isEmpty()) {
                logger.error("Query city name failed.");
                backJson.put("CODE", ReturnMsg.QUERY_CITY_NAME_LIST_FAILED.getCode());
                backJson.put("RESULT", ReturnMsg.QUERY_CITY_NAME_LIST_FAILED.getMsg());
                return backJson;
            }

            //查询登录城市实时缴款数据,term库
            HRealTimePay hrealTimePay = new HRealTimePay();
            hrealTimePay.setTerminal_id(terminalId == null ? -9999 : json.getInteger("TERMINAL_ID"));
            hrealTimePay.setCity_id(cityId);
            hrealTimePay.setLogin_city(loginCity);
            hrealTimePay.setOrder(order == null ? 0 : json.getIntValue("ORDER"));
            List<HRealTimePay> listPaymentMonitor = imonitorRealTimePayDao.listBankPaymentMonitor(vaniJdbcTemplate, hrealTimePay);
            if (listPaymentMonitor == null || listPaymentMonitor.isEmpty()) {
                logger.error("Query the list for real-time bank payment failed.");
                backJson.put("CODE", ReturnMsg.QUERY_LIST_BANK_PAYMENT_FAILED.getCode());
                backJson.put("RESULT", ReturnMsg.QUERY_LIST_BANK_PAYMENT_FAILED.getMsg());
                return backJson;
            }
            JSONArray cityArray = new JSONArray();
            for (HRealTimePay hrtp : listCityInfo) {
                JSONObject tempJson = new JSONObject();
                logger.info("listCityInfo cityName : " + hrtp.getCity_name());
                tempJson.put("CITY_NAME", hrtp.getCity_name());
                tempJson.put("CITY_ID", hrtp.getCity_id());
                cityArray.add(tempJson);
            }
            JSONArray bankPayArray = new JSONArray();
            for (HRealTimePay hrtp : listPaymentMonitor) {
                JSONObject tempJson = new JSONObject();
                tempJson.put("TERMINAL_ID", hrtp.getTerminal_id());
                tempJson.put("STATION_NAME", hrtp.getStation_name());
                tempJson.put("TERMINAL_TYPE_NAME", hrtp.getTerminal_type_name());
                tempJson.put("TMN_CASH_DEDUCT", hrtp.getTmn_cash_deduct());
                tempJson.put("DEFAULT_CREDIT", hrtp.getDefalut_credit());
                tempJson.put("ACC_BALANCE", hrtp.getAcc_balance());
                tempJson.put("DEFAULT_CREDIT1", hrtp.getDefalut_credit1());
                tempJson.put("JG", hrtp.getJG());
                tempJson.put("STATION_NAME", hrtp.getStation_name());
                tempJson.put("OWNER_PHONE", hrtp.getOwner_phone());
                tempJson.put("INCOME_MONEY", hrtp.getIncome_money());
                bankPayArray.add(tempJson);
            }
            backJson.put("CITY_DATA", cityArray);
            backJson.put("PAY_DATA", bankPayArray);
            backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
            backJson.put("RESULT", ReturnMsg.SUCCESS.getMsg());
        } catch (Exception e) {
        }
        return backJson;
    }

    /**
     * 快开游戏监控
     *
     * @param json
     * @param ip
     * @return
     */
    private JSONObject monitorKenoGame(JSONObject json, String ip) {

        JSONObject backJson = new JSONObject();
        backJson.put("CMD", json.get("CMD"));
        backJson.put("TYPE", json.get("TYPE"));
        Integer game_id_7 = 7;
        Integer game_id_9 = 9;
        Integer curDayDrawId7=0;
        Integer curDayDrawId9=0;
        try {
            logger.info("json from request: " + json);
            //当前期信息
            List<HMonitorKenoGame> monitorKenoGame = imonitorKenoGameDao.getMonitorKenoGameCurDrawData(vaniJdbcTemplate);
            if (monitorKenoGame == null || monitorKenoGame.isEmpty()) {
                backJson.put("GAME_DATA", 0);
                backJson.put("GAME_DATAMSG", "getMonitorKenoGameData is null");
                backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
                backJson.put("RESULT", ReturnMsg.SUCCESS.getMsg());
                return backJson;
            } else {                
                backJson.put("GAME_DATA", monitorKenoGame);
            }            
            //上一期开奖号码
            List<HMonitorKenoGame> lastDrawLuckyNo7 = imonitorKenoGameDao.getMonitorKenoGameLuckyNo(vaniJdbcTemplate, game_id_7);
            List<HMonitorKenoGame> lastDrawLuckyNo9 = imonitorKenoGameDao.getMonitorKenoGameLuckyNo(vaniJdbcTemplate, game_id_9);
            if (lastDrawLuckyNo7 == null || lastDrawLuckyNo7.isEmpty()) {
                backJson.put("LUCKYNO7", 0);
                backJson.put("MSG7", "getMonitorKenoGameLuckyNo7 is null");
            } else {
                backJson.put("LUCKYNO7", lastDrawLuckyNo7);
            }
            if (lastDrawLuckyNo9 == null || lastDrawLuckyNo9.isEmpty()) {
                backJson.put("LUCKYNO9", 0);
                backJson.put("MSG9", "getMonitorKenoGameLuckyNo9 is null");
            } else {
                backJson.put("LUCKYNO9", lastDrawLuckyNo9);
            }
            for(HMonitorKenoGame mkGame : monitorKenoGame){
                Integer gameId=mkGame.getGame_id();
                if(gameId == 7){
                    curDayDrawId7 = mkGame.getDraw_id();
                }else if(gameId == 9){
                    curDayDrawId9=mkGame.getDraw_id();
                }
            }
            logger.info("curDayDrawId7:"+curDayDrawId7+"curDayDrawId9"+curDayDrawId9);
            //当天快乐彩或快乐十分每期销售详情列表
            List<HMonitorGame> kenoGames7List =imonitorKenoGameDao.getHMonitorGameList(vaniJdbcTemplate, curDayDrawId7, game_id_7);
            List<HMonitorGame> kenoGames9List =imonitorKenoGameDao.getHMonitorGameList(vaniJdbcTemplate, curDayDrawId9, game_id_9);
            if(kenoGames7List == null||kenoGames7List.isEmpty()){
                backJson.put("GAME7LIST", 0);
            }else{
                backJson.put("GAME7LIST", kenoGames7List);
            }
            if(kenoGames9List == null||kenoGames9List.isEmpty()){
                backJson.put("GAME9LIST", 0);
            }else{
                backJson.put("GAME9LIST", kenoGames9List);
            }
            logger.info("GAME7LIST:"+kenoGames7List);
            Date dt= new Date();
            Long time= dt.getTime();
            backJson.put("SYSTIME", time);
            backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
            backJson.put("RESULT", ReturnMsg.SUCCESS.getMsg());
            return backJson;
        } catch (Exception e) {
            logger.error("Class MnitorService's monitorGame() has an exception: ", e);
            backJson.put("CODE", ReturnMsg.MONITOR_GAME_EXCEPTION.getCode());
            backJson.put("RESULT", ReturnMsg.MONITOR_GAME_EXCEPTION.getMsg());
        }
        return backJson;
    }
    /***
     * 获取当日销量（快乐十分和快乐彩）
     * @param json
     * @param ip
     * @return 
     */
    private JSONObject monitorCurDayGameId7And9SumSales(JSONObject json, String ip) {
        JSONObject backJson = new JSONObject();
        backJson.put("CMD", json.get("CMD"));
        backJson.put("TYPE", json.get("TYPE"));
        Integer game_id_7 = 7;
        Integer game_id_9 = 9;
        Integer curDayDrawId7=0;
        Integer curDayDrawId9=0;
        try {
            //当前期信息
            List<HMonitorKenoGame> monitorKenoGame = imonitorKenoGameDao.getMonitorKenoGameCurDrawData(vaniJdbcTemplate);
            if (monitorKenoGame == null || monitorKenoGame.isEmpty()) {
                backJson.put("GAME_DATA", 0);
                backJson.put("GAME_DATAMSG", "getMonitorKenoGameData is null");
                return backJson;
            } else {                
                backJson.put("GAME_DATA", monitorKenoGame);
            }   
            for(HMonitorKenoGame mkGame : monitorKenoGame){
                Integer gameId=mkGame.getGame_id();
                if(gameId == 7){
                    curDayDrawId7=mkGame.getDraw_id();
                }else if(gameId == 9){
                    curDayDrawId9=mkGame.getDraw_id();
                }
            }
            Integer sumSaleGameId7 = imonitorKenoGameDao.getHMonitorGameCurDaySumSale(vaniJdbcTemplate, curDayDrawId7, game_id_7);
            Integer sumSaleGameId9 = imonitorKenoGameDao.getHMonitorGameCurDaySumSale(vaniJdbcTemplate, curDayDrawId9, game_id_9);        
            logger.info("GameId7 Info Cur Draw:"+curDayDrawId7+"7 Cur Day Sale:"+sumSaleGameId7+"GameId9 Info Cur Draw:"+curDayDrawId9+"9 Cur Day Sale:"+sumSaleGameId9);
            Date dt= new Date();
            Long time= dt.getTime();
            backJson.put("SYSTIME", time);
            backJson.put("SUMSALEGAMEID7", sumSaleGameId7);
            backJson.put("SUMSALEGAMEID9", sumSaleGameId9);
            backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
            backJson.put("RESULT", ReturnMsg.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("Class MnitorService's monitorCurDayGameId7And9SumSales() has an exception: ", e);
            backJson.put("CODE", ReturnMsg.MONITOR_GAME_EXCEPTION.getCode());
            backJson.put("RESULT", ReturnMsg.MONITOR_GAME_EXCEPTION.getMsg());
        }
        
        return backJson;
    }
    /**
     * *
     * 投注机监控
     *
     * @param json
     * @param ip
     * @return
     */
    private JSONObject monitorBettingMachine(JSONObject json, String ip) {
        JSONObject backJson = new JSONObject();
        //获取每个城市终端销售量
        for (int i = 1; i < 23; i++) {
            List<EveryCityTmnSaleInfo> everyCityTmnSaleInfos = null;
            String key = "citySale" + i;
            try {
                everyCityTmnSaleInfos = iBettingMachineMonitoringDao.getEveryCityTmnSaleInfos(termJdbcTemplate, i);
                if (everyCityTmnSaleInfos == null || everyCityTmnSaleInfos.isEmpty()) {
                    backJson.put(key, 0);
                } else {
                    backJson.put(key, everyCityTmnSaleInfos);
                }

            } catch (Exception e) {
                logger.info("getEveryCityTmnSaleInfos ex" + e);
            }
        }
        try {
            backJson.put("CMD", json.get("CMD"));
            backJson.put("TYPE", json.get("TYPE"));
            backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
            backJson.put("MSG", ReturnMsg.SUCCESS.getMsg());
            logger.info("backJson:" + backJson);
        } catch (Exception e) {
            logger.info("cityTmnCountslist ex:" + e);
        }
        return backJson;
    }
    /***
     * 游戏当期销量
     * @param json
     * @param ip
     * @return 
     */
    private JSONObject monitorCurrentDrawSales(JSONObject json, String ip) {
        JSONObject backJson = new JSONObject();
        backJson.put("CMD", json.get("CMD"));
        backJson.put("TYPE", json.get("TYPE"));
        List<CurrentTmnDrawStat> currentTmnDrawStats = null;
        try {
            currentTmnDrawStats = iCurrentTmnDrawStatDao.getCurrentTmnDrawStatSales(vaniJdbcTemplate);
            if (currentTmnDrawStats == null || currentTmnDrawStats.isEmpty()) {
                backJson.put("LIST", 0);
                logger.info("backJson:" + backJson);
            } else {
                backJson.put("LIST", currentTmnDrawStats);
            }
            backJson.put("CODE", 0);
            backJson.put("MSG", "success");
            backJson.put("CMD", json.get("CMD"));
            backJson.put("TYPE", json.get("TYPE"));
        } catch (Exception e) {
            backJson.put("CODE", 102062);
            backJson.put("MSG", "currentTmnDrawStats ex");
            backJson.put("CMD", json.get("CMD"));
            backJson.put("TYPE", json.get("TYPE"));
            logger.info("backJson:" + backJson);
            return backJson;
        }
        return backJson;
    }
    /***
     * 全省终端销售量top5
     * @param json
     * @param ip
     * @return 
     */
    private JSONObject monitorTerminalSalesTop5(JSONObject json, String ip) {
        JSONObject backJson = new JSONObject();
        List<TmnMaxSaleCountTop5> top5 = null;
        top5 = iBettingMachineMonitoringDao.getTmnMaxSaleCountTop5(termJdbcTemplate);
        if (top5 == null || top5.isEmpty()) {
            //backJson.put("code", 102405);
            backJson.put("Top5", 0);
            backJson.put("TopMsg", "getTmnMaxSaleCountTop5 is null");
            backJson.put("CMD", json.get("CMD"));
            backJson.put("TYPE", json.get("TYPE"));
            logger.info("backJson:" + backJson);
            //return backJson;
        } else {
            backJson.put("Top5", top5);
        }
        backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
        backJson.put("RESULT", ReturnMsg.SUCCESS.getMsg());
        return backJson;
    }
    /***
     * 每个地市终端在线数量
     * @param json
     * @param ip
     * @return 
     */
    private JSONObject monitorCityTmnCountInfo(JSONObject json, String ip) {
        JSONObject backJson = new JSONObject();
        int totalTmnCount = iBettingMachineMonitoringDao.getTotalTmnCount(termJdbcTemplate);
        List<CityTmnCount> cityTmnCountslist = null;
        Date date = new Date();
        long cityTmnCount = 0;
        cityTmnCountslist = iBettingMachineMonitoringDao.getCityTmnCount(termJdbcTemplate);
        long number = tmnMonitorRedis.getTmnLoginNumber(new Date());//统计终端在线数等监控信息
        //Map<String, String> resMapInfo = tmnMonitorRedis.getTmnLoginIdsAndTime(new Date());//终端登录详情（终端机号+时间）
        String[] city = {"00", "01", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22"};
        for (int i = 0; i < city.length; i++) {
            cityTmnCount = tmnMonitorRedis.getCityTmnLoginNumber(date, city[i]);
            backJson.put("cityTmn" + i, cityTmnCount);
        }
        if (cityTmnCountslist == null || cityTmnCountslist.isEmpty()) {
            backJson.put("TmnCountMsg", "cityTmnCountslist is null");
            backJson.put("cityTmnCountslist", 0);
            backJson.put("CMD", json.get("CMD"));
            backJson.put("TYPE", json.get("TYPE"));
        } else {
            backJson.put("cityTmnCountslist", cityTmnCountslist);
        }
        //获取终端5分钟内的活跃数量
        int tmnactivecount = iBettingMachineMonitoringDao.getActiveTmnCount(termJdbcTemplate);
        backJson.put("TMNACTIVECOUNT", tmnactivecount);
        backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
        backJson.put("RESULT", ReturnMsg.SUCCESS.getMsg());
        backJson.put("totalTmnCount", totalTmnCount);
        backJson.put("tmnTotalCount", number);
        return backJson;
    }
    /***
     * 初始化中心销量信息
     * @param json
     * @param ip
     * @return 
     */
    private JSONObject monitorBettingMachineInitCityOne(JSONObject json, String ip) {
        JSONObject backJson = new JSONObject();
        int num = 1;
        //for(int i=1;i<23;i++){
        List<EveryCityTmnSaleInfo> everyCityTmnSaleInfos = null;
        String key = "citySale" + num;
        try {
            everyCityTmnSaleInfos = iBettingMachineMonitoringDao.getEveryCityTmnSaleInfos(termJdbcTemplate, num);
            if (everyCityTmnSaleInfos == null || everyCityTmnSaleInfos.isEmpty()) {
                backJson.put(key, 0);
            } else {
                backJson.put(key, everyCityTmnSaleInfos);
            }

        } catch (Exception e) {
            logger.info("getEveryCityTmnSaleInfos ex" + e);
        }
        //}
        try {
            backJson.put("CMD", json.get("CMD"));
            backJson.put("TYPE", json.get("TYPE"));
            backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
            backJson.put("MSG", ReturnMsg.SUCCESS.getMsg());
            logger.info("backJson:" + backJson);
        } catch (Exception e) {
            logger.info("cityTmnCountslist ex:" + e);
        }
        return backJson;
    }
    /***
     * 根据CITY_ID查询每个地市销售信息
     * @param json
     * @param ip
     * @return 
     */
    private JSONObject monitorBettingMachineInitEveryCity(JSONObject json, String ip) {
        JSONObject backJson = new JSONObject();
        int num = json.getInteger("CITY_ID");
        //for(int i=1;i<23;i++){
        List<EveryCityTmnSaleInfo> everyCityTmnSaleInfos = null;
        String key = "citySale";
        try {
            everyCityTmnSaleInfos = iBettingMachineMonitoringDao.getEveryCityTmnSaleInfos(termJdbcTemplate, num);
            if (everyCityTmnSaleInfos == null || everyCityTmnSaleInfos.isEmpty()) {
                backJson.put(key, 0);
            } else {
                backJson.put(key, everyCityTmnSaleInfos);
            }

        } catch (Exception e) {
            logger.info("getEveryCityTmnSaleInfos ex" + e);
        }
        //}
        try {
            backJson.put("CMD", json.get("CMD"));
            backJson.put("TYPE", json.get("TYPE"));
            backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
            backJson.put("MSG", ReturnMsg.SUCCESS.getMsg());
            logger.info("backJson:" + backJson);
        } catch (Exception e) {
            logger.info("cityTmnCountslist ex:" + e);
        }
        return backJson;
    }

    private JSONObject monitorGetCurrentSysTime(JSONObject json, String ip) {
        JSONObject backJson = new JSONObject();
        backJson.put("CMD", json.get("CMD"));
        backJson.put("TYPE", json.get("TYPE"));
        Date dt= new Date();
        Long time= dt.getTime();
        backJson.put("SYSTIME", time);
        backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
        backJson.put("MSG", ReturnMsg.SUCCESS.getMsg());
        return backJson;
    }
    /***
     * 各彩种当前时间游戏信息
     * @param json
     * @param ip
     * @return 
     */
    private JSONObject monitorCurrentDateInformationForEachColourSpecies(JSONObject json, String ip) {
        JSONObject backJson = new JSONObject();
        backJson.put("CMD", json.get("CMD"));
        backJson.put("TYPE", json.get("TYPE"));
        try {
            logger.info("json from request: " + json);
            List<HMonitorGame> hmonitorGameList = imonitorDao.getMonitorGameData(termJdbcTemplate);
            if (hmonitorGameList == null || hmonitorGameList.isEmpty()) {
                logger.error("There is no monitorGame data");
                backJson.put("GAME_DATA", 0);
            } else {
                backJson.put("GAME_DATA", hmonitorGameList);                
            }
            backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
            backJson.put("MSG", ReturnMsg.SUCCESS.getMsg());
            return backJson;
        } catch (Exception e) {
            logger.error("monitorGame exception: ", e);
            backJson.put("CODE", ReturnMsg.MONITOR_GAME_EXCEPTION.getCode());
            backJson.put("MSG", ReturnMsg.MONITOR_GAME_EXCEPTION.getMsg());
        }
        return backJson;
    }
    /***
     * 各地市销售信息
     * @param json
     * @param ip
     * @return 
     */
    private JSONObject monitorBettingAmountOfProvinceCityOrCity(JSONObject json, String ip) {
        JSONObject backJson = new JSONObject();
        backJson.put("CMD", json.get("CMD"));
        backJson.put("TYPE", json.get("TYPE"));
        int gameId = json.getInteger("GAMEID");
        int drawId = json.getInteger("DRAWID");
        try {
            logger.info("json from request: " + json);
            List<HMonitorBetsByCity> hMonitorBetsByCitys = imonitorDao.getBetsByCity(termJdbcTemplate,gameId,drawId);
            if (hMonitorBetsByCitys == null || hMonitorBetsByCitys.isEmpty()) {
                logger.error("There is no hMonitorBetsByCitys data");
                backJson.put("CITYBETS", 0);
            } else {
                backJson.put("CITYBETS", hMonitorBetsByCitys);               
            }
            backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
            backJson.put("MSG", ReturnMsg.SUCCESS.getMsg());
            return backJson;
        } catch (Exception e) {
            logger.error("hMonitorBetsByCitys exception: ", e);
            backJson.put("CODE", ReturnMsg.MONITOR_GAME_EXCEPTION.getCode());
            backJson.put("MSG", ReturnMsg.MONITOR_GAME_EXCEPTION.getMsg());
        }
        return backJson;
    }
    /***
     * 某城市某游戏某彩种终端信息
     * @param json
     * @param ip
     * @return 
     */
    private JSONObject monitorCityAndColorBettingMachinesDetails(JSONObject json, String ip) {
        JSONObject backJson = new JSONObject();
        backJson.put("CMD", json.get("CMD"));
        backJson.put("TYPE", json.get("TYPE"));
        Integer gameId = json.getInteger("GAMEID");
        Integer drawId = json.getInteger("DRAWID");
        Integer cityId = json.getInteger("CITYID");
        Integer terminalId = json.getInteger("TERMINALID");
        Integer sortField = json.getInteger("SORTFIELD");//0为默认排序查询（terminalId asc）  其他为按销量查询（sale_money desc）
        Integer pageSize = json.getInteger("PAGESIZE");
        Integer pageNum = json.getInteger("PAGENUM");
        if(gameId == null || drawId == null || cityId == null || terminalId == null || sortField == null || pageSize == null || pageNum == null){
            logger.info("request param  is  null ，json from request: " + json);
            backJson.put("CODE", ReturnMsg.QUERY_MONITOR_GAME_DATA_FAILED.getCode());
            backJson.put("MSG", ReturnMsg.QUERY_MONITOR_GAME_DATA_FAILED.getMsg());
            return backJson;
        }
        try {
            logger.info("json from request: " + json);
            Pagination<HMonitorTmnInfo> hMonitorTmnInfos = imonitorDao.getTmnInfoByCityAndGameIdAndDrawId(termJdbcTemplate, gameId, drawId, cityId, terminalId, sortField, pageSize, pageNum);
            if (hMonitorTmnInfos == null) {
                logger.error("There is no hMonitorTmnInfos data");
                backJson.put("TMNINFOS", 0);
            } else {
                backJson.put("TMNINFOS", hMonitorTmnInfos);
            }
            backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
            backJson.put("MSG", ReturnMsg.SUCCESS.getMsg());
            return backJson;
        } catch (Exception e) {
            logger.error("hMonitorTmnInfos exception: ", e);
            backJson.put("CODE", ReturnMsg.MONITOR_GAME_EXCEPTION.getCode());
            backJson.put("MSG", ReturnMsg.MONITOR_GAME_EXCEPTION.getMsg());
        }
        return backJson;
    }
    /***
     * 投注机会话
     * @param json
     * @param ip
     * @return 
     */
    private JSONObject monitorCurrentBettingOpportunity(JSONObject json, String ip) {
        JSONObject backJson = new JSONObject();
        backJson.put("CMD", json.get("CMD"));
        backJson.put("TYPE", json.get("TYPE"));
        int beginTerminalId = json.getInteger("BEGINTMN");
        int endTerminalId = json.getInteger("ENDTMN");
        logger.info("get session info list,beginTerminalId:" + beginTerminalId + ",endTerminalId:" + endTerminalId);
        if (beginTerminalId <= 0 || endTerminalId <= 0) {
            logger.info("beginTerminalId,endTerminalId Both less than 0"+",beginTerminalId:"+beginTerminalId+",endTerminalId:"+endTerminalId);
            backJson.put("CODE", ReturnMsg.MONITOR_GAME_EXCEPTION.getCode());
            backJson.put("MSG", ReturnMsg.MONITOR_GAME_EXCEPTION.getMsg());
            return  backJson;
        }
        List<SessionInfo> sessionList = terminalLoginRedis.getSessionInfoListById(beginTerminalId, endTerminalId);
        if (sessionList == null || sessionList.isEmpty()) {
            logger.error("get session info list fail,beginTerminalId:" + beginTerminalId + ",endTerminalId:" + endTerminalId);
            backJson.put("OPPORTUNITY", 0);
        } else {
            backJson.put("OPPORTUNITY", sessionList);  
        }
            backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
            backJson.put("MSG", ReturnMsg.SUCCESS.getMsg());
        return backJson;
    }
    /***
     * 定时刷新投注机实时监控页面
     * @param json
     * @param ip
     * @return 
     */
    private JSONObject monitorInitBettingMachineRealTime(JSONObject json, String ip) {
        JSONObject backJson = new JSONObject();
        backJson.put("CMD", json.get("CMD"));
        backJson.put("TYPE", json.get("TYPE"));
        int gameId = json.getInteger("GAMEID");
        int drawId = json.getInteger("DRAWID");
        int cityId = json.getInteger("CITYID");
        int terminalId = json.getInteger("TERMINALID");
        int sortField = json.getInteger("SORTFIELD");//0为默认查询（terminalId）  1为按销量查询（sale_money）
        int pageSize = json.getInteger("PAGESIZE");
        int pageNum = json.getInteger("PAGENUM");
        logger.info("json from request: " + json);
        try {
            //1. 初始化查询各彩种当前时间游戏信息
            List<HMonitorGame> hmonitorGameList = imonitorDao.getMonitorGameData(termJdbcTemplate);
            if (hmonitorGameList == null || hmonitorGameList.isEmpty()) {
                logger.error("There is no monitorGame data");
                backJson.put("GAME_DATA", 0);
            } else {
                backJson.put("GAME_DATA", hmonitorGameList);
            }
            //2. 各地市销售信息 
            List<HMonitorBetsByCity> hMonitorBetsByCitys = imonitorDao.getBetsByCity(termJdbcTemplate,gameId,drawId);
            if (hMonitorBetsByCitys == null || hMonitorBetsByCitys.isEmpty()) {
                logger.error("There is no hMonitorBetsByCitys data");
                backJson.put("CITYBETS", 0);
            } else {
                backJson.put("CITYBETS", hMonitorBetsByCitys);
            }
            // 3. 某城市某游戏某彩种终端信息
            Pagination<HMonitorTmnInfo> hMonitorTmnInfos = imonitorDao.getTmnInfoByCityAndGameIdAndDrawId(termJdbcTemplate, gameId, drawId, cityId, terminalId, sortField, pageSize, pageNum);
            if (hMonitorTmnInfos == null) {
                logger.error("There is no hMonitorTmnInfos data");
                backJson.put("TMNINFOS", 0);
            } else {
                backJson.put("TMNINFOS", hMonitorTmnInfos);                
            }
            backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
            backJson.put("MSG", ReturnMsg.SUCCESS.getMsg());
            return backJson;
        } catch (Exception e) {
            logger.error("hMonitorTmnInfos exception: ", e);
            backJson.put("CODE", ReturnMsg.MONITOR_GAME_EXCEPTION.getCode());
            backJson.put("MSG", ReturnMsg.MONITOR_GAME_EXCEPTION.getMsg());
        }
        return backJson;
    }
    /***
     * 初始化投注机实时监控页面
     * @param json
     * @param ip
     * @return 
     */
    private JSONObject monitorBettingMachineInitData(JSONObject json, String ip) {
        JSONObject backJson = new JSONObject();
        backJson.put("CMD", json.get("CMD"));
        backJson.put("TYPE", json.get("TYPE"));
        int gameId = 5;
        //int drawId = json.getInteger("DRAWID");
        int cityId = 1;
        int terminalId = json.getInteger("TERMINALID");
        int sortField = json.getInteger("SORTFIELD");//0为默认查询（terminalId）  1为按销量查询（sale_money）
        int pageSize = json.getInteger("PAGESIZE");
        int pageNum = json.getInteger("PAGENUM");
        logger.info("json from request: " + json);
        try {
            //0. 获取双色球的当前期
            int drawId = imonitorDao.getCurDraw(termJdbcTemplate);
            logger.info("gameId:"+gameId+",drawID:"+drawId+",cityId:"+cityId);
            backJson.put("GAMEID", gameId);
            backJson.put("DRAWID", drawId);
            backJson.put("CITYID", cityId);
            //1. 初始化查询各彩种当前时间游戏信息
            List<HMonitorGame> hmonitorGameList = imonitorDao.getMonitorGameData(termJdbcTemplate);
            if (hmonitorGameList == null || hmonitorGameList.isEmpty()) {
                logger.error("There is no monitorGame data");
                backJson.put("GAME_DATA", 0);
            } else {
                backJson.put("GAME_DATA", hmonitorGameList);
            }
            //2. 各地市销售信息 
            List<HMonitorBetsByCity> hMonitorBetsByCitys = imonitorDao.getBetsByCity(termJdbcTemplate,gameId,drawId);
            if (hMonitorBetsByCitys == null || hMonitorBetsByCitys.isEmpty()) {
                logger.error("There is no hMonitorBetsByCitys data");
                backJson.put("CITYBETS", 0);
            } else {
                backJson.put("CITYBETS", hMonitorBetsByCitys);
            }
            // 3. 某城市某游戏某彩种终端信息
            Pagination<HMonitorTmnInfo> hMonitorTmnInfos = imonitorDao.getTmnInfoByCityAndGameIdAndDrawId(termJdbcTemplate, gameId, drawId, cityId, terminalId, sortField, pageSize, pageNum);
            if (hMonitorTmnInfos == null) {
                logger.error("There is no hMonitorTmnInfos data");
                backJson.put("TMNINFOS", 0);
            } else {
                backJson.put("TMNINFOS", hMonitorTmnInfos);                
            }
            backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
            backJson.put("MSG", ReturnMsg.SUCCESS.getMsg());
            return backJson;
        } catch (Exception e) {
            logger.error("hMonitorTmnInfos exception: ", e);
            backJson.put("CODE", ReturnMsg.MONITOR_GAME_EXCEPTION.getCode());
            backJson.put("MSG", ReturnMsg.MONITOR_GAME_EXCEPTION.getMsg());
        }
        return backJson;
    }

}
