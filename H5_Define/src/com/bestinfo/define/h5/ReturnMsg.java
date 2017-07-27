/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.define.h5;

/**
 *
 * @author YangRong
 */
public enum ReturnMsg {

    SUCCESS("0", "成功"),
    //101   任务计划
    QUERY_PROVINCE_YEAR_PLANSALESMONEY("10101001", "省年任务计划销售额未设置"),
    
    QUERY_PROVINCE_YEAR_PLAN_EXCEPTION("10101002", "省年任务计划销售额service异常"),
    //102   监控
    QUERY_MONITOR_GAME_DATA_FAILED("10201", "游戏监控数据查询失败"),
    MONITOR_GAME_EXCEPTION("102404", "游戏监控异常"),
    QUERY_LOGIN_CITY_FAILED("10202", "查询登录城市未果"),
    QUERY_CITY_NAME_LIST_FAILED("10203", "查询城市名称列表失败"),
    QUERY_LIST_BANK_PAYMENT_FAILED("10204", "实时缴款账户监控数据没查出来"),
    REAL_TIME_PARAMS_ERROR("10205", "实时缴款账户监控 参数错误"),
    QUERY_YEAR_SALES_ERROR("10206","区域监控年销量查询未果"),
    /******************************************************/
    QUERY_AREAMINITORYEARSALE_FAILED("10207","区域监控年销量没数据"),
    QUERY_DAYSALES_FAILED("10208","区域监控日销量无数据"),
    QYERY_CURWEEKNUM_FAILED("10209","无当前周"),
    QUERY_LASTWEEKNUM_FAILED("10210","当前周为第一周，无上一周"),
    QUERY_LASTWEEKSUMSALES_FAILED("10211","上周周销量数据为空"),
    QUERY_AREAMINITORYEARSALES_EXCEPTION("102001","区域监控查询异常"),
    QUERY_MINITORKENOGAME_FAILED("10212","快开游戏无数据"),
    QUERY_LASTLUCKYNO_FAILED("10213","快开游戏上期开奖号码为空"),
    QUERY_CURTMNDRAWSTATSALES_EXCEPTION("102002","投注机销量查询异常"),
    //103   对比
    COMPARISON_EXCEPTION("103404", "对比异常"),
    QUERY_WELFAREFUND_NO_DATA("10301", "查询公益金对比没有数据"),
    QUERY_WEEKREPORTCITY_YEAR_FAILED("10302", "查询票种同比增长年销量失败"),
    QUERY_WEEKREPORTCITY_WEEK_FAILED("10303", "票种同比增长周销量查询未果"),
    QUERY_WEEKSALESCOMPARISON_FAILED("10304", "时间维度对比数据没查出来"),
    /*******************************************/
    QUERY_WEEKREPORTGAMEANDSALES_FAILED("10305", "无年销售数据"),
    QUERY_GETCURWEEK_FAILED("10306", "无当前周数据"),
    QUERY_GETLASTWEEK_FAILED("10307", "当前周为第一周，无上一周"),
    QUERY_MANYGAMECOLORCOMPARISON_EXCEPTION("103001","多彩种多游戏对比查询异常"),
    QUERY_PROVINCESALES_FAILED("10308", "当前时间无兄弟省份销售数据"),
    QUERY_COMPARISONPROVINCESALES_EXCEPTION("103002","兄弟省份数据查询异常"),
    QUERY_SALESANDBENEFITS_FAILED("10309", "当前时间无销量公益金对比数据"),
    QUERY_SALESANDBENEFITS_EXCEPTION("103003","销量公益金对比数据查询异常"),
    QUERY_TIMEDIMENSIONYEAR_FAILED("10310", "当前时间无时间维度年销量对比数据"),
    QUERY_TIMEDIMENSIONYEAR_EXCEPTION("103004","时间维度年销量对比数据查询异常"),
    QUERY_TIMEDIMENSIONWEEK_FAILED("10311", "当前时间无时间维度周销量对比数据"),
    QUERY_TIMEDIMENSIONWEEK_EXCEPTION("103005","时间维度年周销量对比数据查询异常");
    private String code;
    private String msg;

    private ReturnMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
