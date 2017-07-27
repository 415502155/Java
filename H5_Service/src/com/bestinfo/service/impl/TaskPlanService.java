package com.bestinfo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bestinfo.bean.h5.taskPlan.MonitorMinuteSales;
import com.bestinfo.bean.h5.taskPlan.WeekReportSumSales;
import com.bestinfo.bean.h5.taskPlan.YearTaskPlan;
import com.bestinfo.dao.h5.taskPlan.ITastPlanDao;
import com.bestinfo.define.h5.ReturnMsg;
import com.bestinfo.service.h5.inter.IComponentsService;
import com.bestinfo.util.TimeUtil;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author zhen
 */
@Service("TaskPlanService")
public class TaskPlanService implements IComponentsService {

    private static final Logger logger = Logger.getLogger(TaskPlanService.class);

    @Resource
    private ITastPlanDao iTaskPlanDao;
    @Resource
    private JdbcTemplate vaniJdbcTemplate;  //vani库
    @Resource
    private JdbcTemplate metaJdbcTemplate;  //meta库

    @Override
    public JSONObject execute(JSONObject json, String ip) {
        int cmd = json.getIntValue("CMD");
        int type = json.getIntValue("TYPE");
        logger.info("cmd:" + cmd + "/" + type);

        JSONObject backJson = new JSONObject();
        backJson.put("CMD", cmd);
        backJson.put("TYPE", type);

        switch (type) {
            case 1://主屏第一次获取所有的数据
                backJson = provinceYearPlanForMasterScreen(json, ip);
                break;
            case 2://主屏刷新的数据
                backJson = refreshLastMinuteSalesAndCounts(json, ip);
                break;
            default:
                break;

        }
        return backJson;
    }

    /**
     * 主屏全省年计划
     *
     * @param json
     * @param ip
     * @return
     */
    private JSONObject provinceYearPlanForMasterScreen(JSONObject json, String ip) {

        JSONObject backJson = new JSONObject();
        backJson.put("CMD", json.get("CMD"));
        backJson.put("TYPE", json.get("TYPE"));
        try {
            logger.info("json from request: " + json);

            //年任务
            List<YearTaskPlan> yearPlanList = iTaskPlanDao.getProvinceYearPlanSalesMoney(vaniJdbcTemplate);
            if (yearPlanList == null || yearPlanList.isEmpty()) {
                logger.error("year task plan is null");
                backJson.put("TASK", 0);
            } else {
                backJson.put("TASK", yearPlanList);
            }

            //本年彩票销售
            Long tempYearMoney = iTaskPlanDao.getYearSaleMoney(vaniJdbcTemplate);
            Long tempMoneyHaveClear = iTaskPlanDao.getSaleMoneyHaveClear(vaniJdbcTemplate);
            Long tempCurrentMoney = iTaskPlanDao.getCurrentSaleMoney(vaniJdbcTemplate);
            long yearMoney = tempYearMoney == null ? 0 : tempYearMoney;
            long moneyHaveClear = tempMoneyHaveClear == null ? 0 : tempMoneyHaveClear;
            long currentMoney = tempCurrentMoney == null ? 0 : tempCurrentMoney;
            long sumSaleMoney = yearMoney + moneyHaveClear + currentMoney;
            backJson.put("yearMoney", yearMoney);
            backJson.put("NowYearSales", sumSaleMoney);

            //
            YearTaskPlan yearTaskPlan = iTaskPlanDao.getHomeScreenMoney(vaniJdbcTemplate);
            if (yearTaskPlan == null) {
                logger.error("m1 is null.");
                backJson.put("m1", 0);
            } else {
                backJson.put("m1", yearTaskPlan.getM1().doubleValue());
                backJson.put("will_money", yearTaskPlan.getWill_money().doubleValue());
                backJson.put("complete_day", TimeUtil.formatDate_NYR(yearTaskPlan.getComplete_day()));
            }
            //上周、上上周销量
            List<WeekReportSumSales> twoWeekReportSumSalesList = iTaskPlanDao.getTwoWeekWeekReportSumSales(vaniJdbcTemplate);
            if (twoWeekReportSumSalesList == null || twoWeekReportSumSalesList.isEmpty()) {
                logger.error("weekReportSumSalesList is null");
                backJson.put("TwoWeekSales", 0);
            } else {
                backJson.put("TwoWeekSales", twoWeekReportSumSalesList);
            }

            //周列表
            List<WeekReportSumSales> weekIdList = iTaskPlanDao.getWeekIdList(vaniJdbcTemplate);
            if (weekIdList == null || weekIdList.isEmpty()) {
                logger.info("getWeekIdList is null");
                backJson.put("WeekIdList", 0);
            } else {
                backJson.put("WeekIdList", weekIdList);
            }
            //截止当前年份的每周销量和年销售量
            List<WeekReportSumSales> weekSalesYearSales = iTaskPlanDao.getYearSalesWeekSalesList(vaniJdbcTemplate);
            if (weekSalesYearSales == null || weekSalesYearSales.isEmpty()) {
                logger.info("getYearSalesWeekSalesList is null");
                backJson.put("WeekSalesYearSales", 0);
            } else {
                backJson.put("WeekSalesYearSales", weekSalesYearSales);
            }
            //从分钟统计表中获取上一分钟数据
            MonitorMinuteSales lastMinuteData = iTaskPlanDao.getLastMinuteDataFromStat(metaJdbcTemplate);
            if (lastMinuteData == null) {
                lastMinuteData = new MonitorMinuteSales();
            }
            backJson.put("LastMinuteSales", lastMinuteData.getSale_money().intValue());
            backJson.put("lastMinuteSalesCount", lastMinuteData.getSale_number());

            //从分钟统计表中获取历史最大销售金额
            List<MonitorMinuteSales> hostoryMinuteMaxSaleses = iTaskPlanDao.getHostoryOneMinuteMaxSales(metaJdbcTemplate);
            if (hostoryMinuteMaxSaleses == null || hostoryMinuteMaxSaleses.isEmpty()) {
                backJson.put("OneMinuteMaxSale", 0);
            } else {
                backJson.put("OneMinuteMaxSale", hostoryMinuteMaxSaleses);
            }
            //从分钟统计表中获取历史最大销售笔数
            List<MonitorMinuteSales> hostoryMinuteMaxSalesesCount = iTaskPlanDao.getHostoryOneMinuteMaxNumber(metaJdbcTemplate);
            if (hostoryMinuteMaxSalesesCount == null || hostoryMinuteMaxSalesesCount.isEmpty()) {
                backJson.put("OneMinuteMaxNumber", 0);
            } else {
                backJson.put("OneMinuteMaxNumber", hostoryMinuteMaxSalesesCount);
            }
            //历史周最大销量
            WeekReportSumSales hostoryWeekMaxSales = iTaskPlanDao.getHostoryMaxSales(vaniJdbcTemplate);
            backJson.put("HostoryWeekMaxSales", hostoryWeekMaxSales);
            backJson.put("CODE", 0);
            backJson.put("RESULT", ReturnMsg.SUCCESS.getMsg());
            logger.info("back Json:" + backJson);
            return backJson;
        } catch (Exception e) {
            logger.error("Class TaskPlanService's provinceYearPlanForMasterScreen() has an exception: ", e);
            backJson.put("CODE", ReturnMsg.QUERY_PROVINCE_YEAR_PLAN_EXCEPTION.getCode());
            backJson.put("RESULT", ReturnMsg.QUERY_PROVINCE_YEAR_PLAN_EXCEPTION.getMsg());
        }
        return backJson;
    }

    public static String getTimeByMinute(int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minute);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }
    /***
     * 用于获取主屏动态刷新数据接口
     * @param json
     * @param ip
     * @return 
     */
    private JSONObject refreshLastMinuteSalesAndCounts(JSONObject json, String ip) {
        JSONObject backJson = new JSONObject();
        backJson.put("CMD", json.get("CMD"));
        backJson.put("TYPE", json.get("TYPE"));
        //本年彩票销售量
        Long tempYearMoney = iTaskPlanDao.getYearSaleMoney(vaniJdbcTemplate);
        Long tempMoneyHaveClear = iTaskPlanDao.getSaleMoneyHaveClear(vaniJdbcTemplate);
        Long tempCurrentMoney = iTaskPlanDao.getCurrentSaleMoney(vaniJdbcTemplate);
        long yearMoney = tempYearMoney == null ? 0 : tempYearMoney;
        long moneyHaveClear = tempMoneyHaveClear == null ? 0 : tempMoneyHaveClear;
        long currentMoney = tempCurrentMoney == null ? 0 : tempCurrentMoney;
        long sumSaleMoney = yearMoney + moneyHaveClear + currentMoney;
        backJson.put("NowYearSales", sumSaleMoney);

        //从分钟统计表中获取上一分钟数据
        MonitorMinuteSales lastMinuteData = iTaskPlanDao.getLastMinuteDataFromStat(metaJdbcTemplate);
        if (lastMinuteData == null) {
            lastMinuteData = new MonitorMinuteSales();
        }
        backJson.put("LastMinuteSales", lastMinuteData.getSale_money().intValue());
        backJson.put("lastMinuteSalesCount", lastMinuteData.getSale_number());

        //从分钟统计表中获取历史最大销售金额
        List<MonitorMinuteSales> hostoryMinuteMaxSaleses = iTaskPlanDao.getHostoryOneMinuteMaxSales(metaJdbcTemplate);
        if (hostoryMinuteMaxSaleses == null || hostoryMinuteMaxSaleses.isEmpty()) {
            backJson.put("OneMinuteMaxSale", 0);
        } else {
            backJson.put("OneMinuteMaxSale", hostoryMinuteMaxSaleses);
        }
        //从分钟统计表中获取历史最大销售笔数
        List<MonitorMinuteSales> hostoryMinuteMaxSalesesCount = iTaskPlanDao.getHostoryOneMinuteMaxNumber(metaJdbcTemplate);
        if (hostoryMinuteMaxSalesesCount == null || hostoryMinuteMaxSalesesCount.isEmpty()) {
            backJson.put("OneMinuteMaxNumber", 0);
        } else {
            backJson.put("OneMinuteMaxNumber", hostoryMinuteMaxSalesesCount);
        }
        backJson.put("CODE", 0);
        backJson.put("RESULT", ReturnMsg.SUCCESS.getMsg());
        return backJson;
    }

}
