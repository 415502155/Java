/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bestinfo.bean.h5.comparison.HTStatProvince;
import com.bestinfo.bean.h5.comparison.HTicketType;
import com.bestinfo.bean.h5.comparison.HWeekSalesComparison;
import com.bestinfo.bean.h5.comparison.StatCountry;
import com.bestinfo.bean.h5.comparison.StatProvince;
import com.bestinfo.bean.h5.comparison.StatProvinceOther;
import com.bestinfo.bean.h5.comparison.TicketType;
import com.bestinfo.bean.h5.comparison.WeekReportGameAndSales;
import com.bestinfo.dao.h5.comparison.IComparisonWelfareFundDao;
import com.bestinfo.dao.h5.comparison.IGameContrastDao;
import com.bestinfo.dao.h5.comparison.IProvincesSalesComparisonDao;
import com.bestinfo.dao.h5.comparison.ITicketTypeDao;
import com.bestinfo.dao.h5.comparison.IWeekReportCityDao;
import com.bestinfo.dao.h5.comparison.IWeekSalesComparisonDao;
import com.bestinfo.dao.h5.comparison.ISalesAndBenefitsDao;
import com.bestinfo.dao.h5.comparison.IStatCountryDao;
import com.bestinfo.define.h5.ReturnMsg;
import com.bestinfo.service.h5.inter.IComponentsService;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 各种对比 （CMD 103）
 *
 * @author Administrator
 */
@Service("ComparisonService")
public class ComparisonService implements IComponentsService {

    private static final Logger logger = Logger.getLogger(ComparisonService.class);

    @Resource
    private IComparisonWelfareFundDao icomparisonWelfareFundDao;
    @Resource
    private JdbcTemplate vaniJdbcTemplate;
    @Resource
    private IWeekReportCityDao iweekReportCityDao;
    @Resource
    private IWeekSalesComparisonDao iweekSalesComparisonDao;
    @Resource
    private IProvincesSalesComparisonDao iProvincesSalesComparisonDao;
    @Resource
    private IGameContrastDao igcd;
    @Resource
    private ISalesAndBenefitsDao sbd;
    @Resource
    private ITicketTypeDao ticketTypeDao;
    @Resource
    private IStatCountryDao statCountryDao;

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
                backJson = ManyGameColorComparison(json, ip);
                break;
            case 2:
                backJson = weekSalesComparison(json, ip);
                break;
            case 3:
                backJson = comparisonWelfareFund(json, ip);
                break;
            case 4:
                backJson = comparisonProvinceSales(json, ip);
                break;
            case 5:
                backJson = weekReportCityYOY(json, ip);
                break;
            case 6:
                backJson = salesAndBenefitsComparison(json, ip);
                break;
            case 7:
                backJson = timeDimensionYearComparison(json, ip);
                break;
            case 8:
                backJson = timeDimensionWeekComparison(json, ip);
                break;
            case 9:
                backJson = ticketTypeList(json, ip);
                break;//
            case 10:
                backJson = get20WeekSales(json, ip);
                break;
            case 11:
                backJson = StatCountryComparison(json, ip);
                break;
        }
        return backJson;
    }

    /**
     * 公益金对比
     *
     * @param json
     * @param ip
     * @return
     */
    private JSONObject comparisonWelfareFund(JSONObject json, String ip) {
        JSONObject backJson = new JSONObject();
        backJson.put("CMD", json.get("CMD"));
        backJson.put("TYPE", json.get("TYPE"));
        try {
            Integer yearBegin = json.getIntValue("YEAR_BEGIN");
            Integer yearEnd = json.getIntValue("YEAR_END");
            List<HTStatProvince> welfareFundList = icomparisonWelfareFundDao.getComparisonWelfareFund(vaniJdbcTemplate, yearBegin, yearEnd);
            if (welfareFundList == null || welfareFundList.isEmpty()) {
                logger.error("Query welfareFund is null.");
                backJson.put("CODE", ReturnMsg.QUERY_WELFAREFUND_NO_DATA.getCode());
                backJson.put("RESULT", ReturnMsg.QUERY_WELFAREFUND_NO_DATA.getMsg());
                return backJson;
            }
            JSONArray jsonArray = new JSONArray();
            for (HTStatProvince htsp : welfareFundList) {
                JSONObject tempJson = new JSONObject();
                tempJson.put("YEAR_ID", htsp.getYear_id());
                tempJson.put("SALE_MONEY", htsp.getSale_money().intValue());
                tempJson.put("WELFARE_MONEY", htsp.getWelfare_money().intValue());
                jsonArray.add(tempJson);
            }
            backJson.put("DATA", jsonArray);
            backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
            backJson.put("RESULT", ReturnMsg.SUCCESS.getMsg());
            return backJson;
        } catch (Exception e) {
            logger.error("Class ComparisonService's comparisonWelfareFund() has an exception: ", e);
            backJson.put("CODE", ReturnMsg.COMPARISON_EXCEPTION.getCode());
            backJson.put("RESULT", ReturnMsg.COMPARISON_EXCEPTION.getMsg());
        }
        return backJson;
    }

    /**
     * 票种同比增长
     *
     */
    private JSONObject weekReportCityYOY(JSONObject json, String ip) {
        JSONObject backJson = new JSONObject();
        backJson.put("CMD", json.get("CMD"));
        backJson.put("TYPE", json.get("TYPE"));
        try {
            logger.info("json from request: " + json);
            Integer flag = json.getInteger("CHECK");
            Integer year = json.getInteger("YEAR");
            Integer week = json.getInteger("WEEK");
            //如果勾选了年销量
            if (flag == 1) {
                List<HTicketType> weekReportCityYear = iweekReportCityDao.getWeekReportCityYear(vaniJdbcTemplate, year);
                if (weekReportCityYear == null || weekReportCityYear.isEmpty()) {
                    logger.error("WeekReportCityYOY_year is null.");
                    backJson.put("CODE", ReturnMsg.QUERY_WEEKREPORTCITY_YEAR_FAILED.getCode());
                    backJson.put("RESULT", ReturnMsg.QUERY_WEEKREPORTCITY_YEAR_FAILED.getMsg());
                    return backJson;
                }
                JSONArray jsonArray = new JSONArray();
                for (HTicketType htt : weekReportCityYear) {
                    JSONObject tempJson = new JSONObject();
                    tempJson.put("CITY_NAME", htt.getCity_name());
                    tempJson.put("ONE_INC", htt.getOne_inc());
                    tempJson.put("ONE_RATIO", htt.getOne_ratio());
                    tempJson.put("TOW_INC", htt.getTwo_inc());
                    tempJson.put("TOW_RATIO", htt.getTwo_ratio());
                    tempJson.put("THR_INC", htt.getThr_inc());
                    tempJson.put("THR_RATIO", htt.getThr_ratio());
                    tempJson.put("ALL_INC", htt.getAll_inc());
                    tempJson.put("ALL_RATIO", htt.getAll_ratio());
                    jsonArray.add(tempJson);
                }
                backJson.put("YOY_DATA", jsonArray);
                backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
                backJson.put("RESULT", ReturnMsg.SUCCESS.getMsg());
                return backJson;
            }
            if (flag == 2) {
                List<HTicketType> weekReportCityWeek = iweekReportCityDao.getWeekReportCityWeek(vaniJdbcTemplate, year, week);
                if (weekReportCityWeek == null || weekReportCityWeek.isEmpty()) {
                    logger.error("WeekReportCityYOY_year is null.");
                    backJson.put("CODE", ReturnMsg.QUERY_WEEKREPORTCITY_WEEK_FAILED.getCode());
                    backJson.put("RESULT", ReturnMsg.QUERY_WEEKREPORTCITY_WEEK_FAILED.getMsg());
                    return backJson;
                }
                JSONArray jsonArray = new JSONArray();
                for (HTicketType htt : weekReportCityWeek) {
                    JSONObject tempJson = new JSONObject();
                    tempJson.put("CITY_NAME", htt.getCity_name());
                    tempJson.put("ONE_INC", htt.getOne_inc());
                    tempJson.put("ONE_RATIO", htt.getOne_ratio());
                    tempJson.put("TOW_INC", htt.getTwo_inc());
                    tempJson.put("TOW_RATIO", htt.getTwo_ratio());
                    tempJson.put("THR_INC", htt.getThr_inc());
                    tempJson.put("THR_RATIO", htt.getThr_ratio());
                    tempJson.put("ALL_INC", htt.getAll_inc());
                    tempJson.put("ALL_RATIO", htt.getAll_ratio());
                    jsonArray.add(tempJson);
                }
                backJson.put("YOY_DATA", jsonArray);
                backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
                backJson.put("RESULT", ReturnMsg.SUCCESS.getMsg());
                return backJson;
            }
        } catch (Exception e) {
            logger.error("Class ComparisonService's weekReportCityYOY() has an exception: ", e);
            backJson.put("CODE", ReturnMsg.COMPARISON_EXCEPTION.getCode());
            backJson.put("RESULT", ReturnMsg.COMPARISON_EXCEPTION.getMsg());
        }
        return backJson;
    }
    /**
     * 年销量对比/周销量对比（时间维度销量对比）
     *
     * @param json
     * @param ip
     * @return
     */
    private JSONObject weekSalesComparison(JSONObject json, String ip) {
        JSONObject backJson = new JSONObject();
        backJson.put("CMD", json.get("CMD"));
        backJson.put("TYPE", json.get("TYPE"));
        try {
            logger.info("json from request: " + json);
            //json和map 转换一下
            Map<String, Integer> params = new HashMap<>();
            params.put("CHOICE_DATE", json.getInteger("CHOICE_DATE"));//设比较方式参数叫“CHOICE_DATE” == 1是多年比较
            params.put("YEAR_BEGIN", json.getInteger("YEAR_BEGIN"));//设传过来的起始时间参数叫“YEAR_BEGIN”和“YEAR_END”
            params.put("YEAR_END", json.getInteger("YEAR_END"));
            params.put("CHOICE_GAME", json.getInteger("CHOICE_GAME"));//设票种参数叫“CHOICE_GAME” == 1全票、==2电脑票 ==3 China_Fu & GGua

            List<HWeekSalesComparison> list = iweekSalesComparisonDao.getWeekSalesComparisonData(vaniJdbcTemplate, params);
            if (list == null || list.isEmpty()) {
                logger.error("Query weekSalesComparison failed.");
                backJson.put("CODE", ReturnMsg.QUERY_WEEKSALESCOMPARISON_FAILED.getCode());
                backJson.put("RESULT", ReturnMsg.QUERY_WEEKSALESCOMPARISON_FAILED.getMsg());
                return backJson;
            }
            JSONArray jsonArray = new JSONArray();
            for (HWeekSalesComparison hwsc : list) {
                JSONObject tempJson = new JSONObject();
                tempJson.put("YEAR_WEEK", hwsc.getYear_week());
                tempJson.put("SALE_MONEY", hwsc.getSale_money());
                jsonArray.add(tempJson);
            }
            backJson.put("SALES_DATA", jsonArray);
            backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
            backJson.put("RESULT", ReturnMsg.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("Class ComparisonService's weekSalesComparison() has an exception: ", e);
            backJson.put("CODE", ReturnMsg.COMPARISON_EXCEPTION.getCode());
            backJson.put("RESULT", ReturnMsg.COMPARISON_EXCEPTION.getMsg());
        }
        return backJson;
    }

    /**
     * *
     * 多彩种多游戏对比
     *
     * @param json
     * @param ip
     * @return
     */
    private JSONObject ManyGameColorComparison(JSONObject json, String ip) {
        String cmd = String.valueOf(json.get("CMD"));
        Integer curWeek = igcd.getCurWeek(vaniJdbcTemplate);
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        logger.info("today is " + year + " year :" + curWeek + "周");
        logger.info("CMD:" + cmd);
        List<WeekReportGameAndSales> wlist = null;
        List<WeekReportGameAndSales> list = null;
        List<WeekReportGameAndSales> lastlist = null;
        JSONObject backJson = null;
        try {
            wlist = igcd.getWeekReportGameAndSalesListByWeek(vaniJdbcTemplate, curWeek);
            String weekList = JSONArray.toJSONString(wlist);
            logger.info("WLIST:" + weekList);
        } catch (Exception e) {
            logger.info("search ManyGameColorComparison fail:"+e);
        }
        try {
            backJson = new JSONObject();
            list = igcd.getWeekReportGameAndSalesList(vaniJdbcTemplate);
            if (list == null) {
                logger.info(" List<WeekReportGameAndSales> list is null");
                backJson.put("CODE", ReturnMsg.QUERY_WEEKREPORTGAMEANDSALES_FAILED.getCode());
                backJson.put("MSG", ReturnMsg.QUERY_WEEKREPORTGAMEANDSALES_FAILED.getMsg());
                return backJson;
            }
            lastlist=igcd.getWeekReportGameAndSalesListByLastYear(vaniJdbcTemplate);
            if (list == null) {
                logger.info(" List<WeekReportGameAndSales> lastlist is null");
                backJson.put("CODE", ReturnMsg.QUERY_WEEKREPORTGAMEANDSALES_FAILED.getCode());
                backJson.put("MSG", ReturnMsg.QUERY_WEEKREPORTGAMEANDSALES_FAILED.getMsg());
                return backJson;
            }
            if (curWeek == 0) {
                logger.info("getCurWeek  is null");
                backJson.put("CODE", ReturnMsg.QUERY_GETCURWEEK_FAILED.getCode());
                backJson.put("MSG", ReturnMsg.QUERY_GETCURWEEK_FAILED.getMsg());
                return backJson;
            } else if (curWeek == 1) {
                logger.info("getCurWeek  is :" + curWeek);
                backJson.put("CODE", ReturnMsg.QUERY_GETLASTWEEK_FAILED.getCode());
                backJson.put("MSG", ReturnMsg.QUERY_GETLASTWEEK_FAILED.getMsg());
                return backJson;
            } else {
                Integer lastWeek = curWeek - 1;
                logger.info("curWeek is:" + curWeek);
                logger.info("lastWeek is :" + lastWeek);
                List<WeekReportGameAndSales> lastWeekSales = igcd.getLastWeekSales(vaniJdbcTemplate, lastWeek);
                backJson.put("LASTWEEKSALES", lastWeekSales);
            }
            backJson.put("LIST", list);
            backJson.put("LYEARLIST", lastlist);
            backJson.put("CMD", cmd);
            backJson.put("WEEKLIST", wlist);
            backJson.put("YEAR", year);
            backJson.put("WEEK", curWeek);
            backJson.put("CODE", 0);
            backJson.put("MSG", "success");
            String result = JSONArray.toJSONString(list);
            logger.info("result" + result);
        } catch (Exception e) {
            logger.info("search ManyGameColorComparison fail");
            backJson.put("CODE", ReturnMsg.QUERY_MANYGAMECOLORCOMPARISON_EXCEPTION.getCode());
            backJson.put("MSG", ReturnMsg.QUERY_MANYGAMECOLORCOMPARISON_EXCEPTION.getMsg());
        }
        return backJson;
    }

    /**
     * *
     * 兄弟省份销量对比
     *
     * @param json
     * @param ip
     * @return
     */
    private JSONObject comparisonProvinceSales(JSONObject json, String ip) {
        JSONObject backJson = new JSONObject();
        backJson.put("CMD", json.get("CMD"));
        backJson.put("TYPE", json.get("TYPE"));
        String starts = String.valueOf(json.get("start"));
        String ends = String.valueOf(json.get("end"));
        Integer start = Integer.parseInt(starts);
        Integer end = Integer.parseInt(ends);
        List<StatProvinceOther> list = null;
        try {
            list = iProvincesSalesComparisonDao.getStatProvinceOtherList(vaniJdbcTemplate, start, end);
            if (list == null || list.isEmpty()) {
                backJson.put("CODE", ReturnMsg.QUERY_PROVINCESALES_FAILED.getCode());
                backJson.put("MSG", ReturnMsg.QUERY_PROVINCESALES_FAILED.getMsg());
                return backJson;
            }
            backJson.put("CODE", 0);
            backJson.put("LIST", list);
            String result = JSONArray.toJSONString(list);
            logger.info("result" + result);
        } catch (Exception e) {
            logger.info("comparisonProvinceSales ex:" + e);
            backJson.put("CODE", ReturnMsg.QUERY_COMPARISONPROVINCESALES_EXCEPTION.getCode());
            backJson.put("MSG", ReturnMsg.QUERY_COMPARISONPROVINCESALES_EXCEPTION.getMsg());
        }

        return backJson;
    }

    /**
     * *
     * 公益金对比
     *
     * @param json
     * @param ip
     * @return
     */
    private JSONObject salesAndBenefitsComparison(JSONObject json, String ip) {
        String starts = String.valueOf(json.get("start"));
        String ends = String.valueOf(json.get("end"));
        Integer start = Integer.parseInt(starts);
        Integer end = Integer.parseInt(ends);
        List<StatProvince> list = null;
        JSONObject backJson = null;
        try {
            backJson = new JSONObject();
            list = sbd.getStatProvinceList(vaniJdbcTemplate, start, end);
            if (list == null || list.isEmpty()) {
                logger.info(" List<StatProvince> list is null");
                backJson.put("CODE", ReturnMsg.QUERY_SALESANDBENEFITS_FAILED.getCode());
                backJson.put("MSG", ReturnMsg.QUERY_SALESANDBENEFITS_FAILED.getMsg());
                return backJson;
            }
            backJson.put("LIST", list);
            backJson.put("CODE", 0);
            backJson.put("CMD", String.valueOf(json.get("CMD")));
        } catch (Exception e) {
            logger.info("search getStatProvinceList fail");
            backJson.put("CODE", ReturnMsg.QUERY_SALESANDBENEFITS_EXCEPTION.getCode());
            backJson.put("MSG", ReturnMsg.QUERY_SALESANDBENEFITS_EXCEPTION.getMsg());
        }

        return backJson;
    }

    /**
     * *
     * 时间维度年销量对比
     *
     * @param json
     * @param ip
     * @return
     */
    private JSONObject timeDimensionYearComparison(JSONObject json, String ip) {
        String starts = String.valueOf(json.get("start"));//开始年份
        String ends = String.valueOf(json.get("end"));    //截止年份
        String types = String.valueOf(json.get("game"));  //几种彩票类型7
        Integer start = Integer.parseInt(starts);
        Integer end = Integer.parseInt(ends);
        Integer type = Integer.parseInt(types);
        logger.info(start + "...." + end + "...." + type);
        List<WeekReportGameAndSales> list = null;
        JSONObject backJson = null;
        try {
            backJson = new JSONObject();
            list = igcd.getWeekReportGameAndSalesComputerList(vaniJdbcTemplate, start, end, type);
            logger.info("list:" + list);
            if (list == null || list.isEmpty()) {
                logger.info(" getWeekReportGameAndSalesComputerList list is null");
                backJson.put("CODE", ReturnMsg.QUERY_TIMEDIMENSIONYEAR_FAILED.getCode());
                backJson.put("MSG", ReturnMsg.QUERY_TIMEDIMENSIONYEAR_FAILED.getMsg());
                return backJson;
            }
            backJson.put("LIST", list);
            backJson.put("CODE", "0");
            backJson.put("CMD", String.valueOf(json.get("CMD")));
            logger.info("backJson:" + backJson);
        } catch (Exception e) {
            logger.info("search getStatProvinceList fail");
            backJson.put("CODE", ReturnMsg.QUERY_TIMEDIMENSIONYEAR_EXCEPTION.getCode());
            backJson.put("MSG", ReturnMsg.QUERY_TIMEDIMENSIONYEAR_EXCEPTION.getMsg());
        }
        return backJson;
    }

    /**
     * *
     * 时间维度周销量对比
     *
     * @param json
     * @param ip
     * @return
     */
    private JSONObject timeDimensionWeekComparison(JSONObject json, String ip) {
        String starts = String.valueOf(json.get("start"));//开始年份+周数+10
        String ends = String.valueOf(json.get("end"));    //截止年份+周数+10
        String types = String.valueOf(json.get("game"));  //几种彩票类型7
        Integer start = Integer.parseInt(starts);
        Integer end = Integer.parseInt(ends);
        Integer type = Integer.parseInt(types);
        logger.info(start + "...." + end + "...." + type);
        List<WeekReportGameAndSales> list = null;
        JSONObject backJson = null;
        try {
            backJson = new JSONObject();
            list = igcd.getWeekReportGameAndSalesesWeekList(vaniJdbcTemplate, start, end, type);
            logger.info("list:" + list);
            if (list == null || list.isEmpty()) {
                logger.info(" getWeekReportGameAndSalesWeekList list is null");
                backJson.put("CODE", ReturnMsg.QUERY_TIMEDIMENSIONWEEK_FAILED.getCode());
                backJson.put("MSG", ReturnMsg.QUERY_TIMEDIMENSIONWEEK_FAILED.getMsg());
                return backJson;
            }
            backJson.put("LIST", list);
            backJson.put("CODE", 0);
            backJson.put("CMD", String.valueOf(json.get("CMD")));
            logger.info("backJson:" + backJson);
        } catch (Exception e) {
            logger.info("search fail ex");
            backJson.put("CODE", ReturnMsg.QUERY_TIMEDIMENSIONWEEK_EXCEPTION.getCode());
            backJson.put("MSG", ReturnMsg.QUERY_TIMEDIMENSIONWEEK_EXCEPTION.getMsg());
        }
        return backJson;
    }
    /***
     * 获取票种列表
     * @param json
     * @param ip
     * @return 
     */
    private JSONObject ticketTypeList(JSONObject json, String ip) {
        JSONObject backJson = new JSONObject();
        try {
            List<TicketType> list = ticketTypeDao.getTicketTypeList(vaniJdbcTemplate);
            if (list == null || list.isEmpty()) {
                logger.info("TicketType is null");
                backJson.put("CODE", 108);
                backJson.put("MSG", "TicketType is null");
                logger.info("backJson:" + backJson);
                return backJson;
            }
            backJson.put("CODE", 0);
            backJson.put("LIST", list);
            backJson.put("CMD", String.valueOf(json.get("CMD")));
        } catch (Exception e) {
            backJson.put("CODE", 108001);
            backJson.put("MSG", "query TicketType exception");
            logger.info("backJson:" + backJson);
            return backJson;
        }
        return backJson;
    }
    /***
     * 周销量对比
     * 初始化页面查询最近21周的销量
     * @param json
     * @param ip
     * @return 
     */
    private JSONObject get20WeekSales(JSONObject json, String ip) {
        List<WeekReportGameAndSales> list = null;
        JSONObject backJson = null;
        try {
            backJson = new JSONObject();
            list = igcd.getWeekReportGameAndSalese20(vaniJdbcTemplate);
            if (list == null || list.isEmpty()) {
                logger.info(" getWeekReportGameAndSalesWeekList list is null");
                backJson.put("CODE", ReturnMsg.QUERY_TIMEDIMENSIONWEEK_FAILED.getCode());
                backJson.put("MSG", ReturnMsg.QUERY_TIMEDIMENSIONWEEK_FAILED.getMsg());
                return backJson;
            }
            backJson.put("LIST", list);
            backJson.put("CODE", 0);
            backJson.put("CMD", String.valueOf(json.get("CMD")));
            logger.info("backJson:" + backJson);
        } catch (Exception e) {
            logger.info("search fail ex");
            backJson.put("CODE", ReturnMsg.QUERY_TIMEDIMENSIONWEEK_EXCEPTION.getCode());
            backJson.put("MSG", ReturnMsg.QUERY_TIMEDIMENSIONWEEK_EXCEPTION.getMsg());
        }
        return backJson;
    }

    private JSONObject StatCountryComparison(JSONObject json, String ip) {
        JSONObject backJson = new JSONObject();
        try {
            backJson.put("CMD", json.get("CMD"));
            backJson.put("TYPE", json.get("TYPE"));
            String starts = String.valueOf(json.get("SATRTTIME"));
            String ends = String.valueOf(json.get("ENDTIME"));
            Integer start = Integer.parseInt(starts);
            Integer end = Integer.parseInt(ends);
            List<StatCountry> statCountrys = statCountryDao.getStatCountryList(vaniJdbcTemplate, start, end);
            if(null == statCountrys || statCountrys.isEmpty()){
                logger.info("statCountrys is null");
                backJson.put("STATCOUNTRY", 0);
                backJson.put("MSG", ReturnMsg.SUCCESS.getMsg());
                backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
            }else{            
                backJson.put("STATCOUNTRY", statCountrys);
                backJson.put("MSG", ReturnMsg.SUCCESS.getMsg());
                backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
            }
        } catch (Exception e) {
            logger.info("search fail ex");
            backJson.put("CODE", 103222);
            backJson.put("MSG", "Lottery and sport sale query exception");
        }
        
        return backJson;
    }
}
