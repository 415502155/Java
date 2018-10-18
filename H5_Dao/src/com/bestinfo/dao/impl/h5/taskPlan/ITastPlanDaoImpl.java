/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.impl.h5.taskPlan;

import com.bestinfo.bean.h5.taskPlan.MonitorMinuteSales;
import com.bestinfo.bean.h5.taskPlan.WeekReportSumSales;
import com.bestinfo.bean.h5.taskPlan.YearTaskPlan;
import com.bestinfo.dao.h5.taskPlan.ITastPlanDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 主屏监控
 * @author zhen
 */
@Repository
public class ITastPlanDaoImpl extends BaseDaoImpl implements ITastPlanDao {

    //当前年计划销售任务    奋斗目标为当前额的120%
    private static final String CUR_YEAR_PROVINCE_PLANSALESMONEY = "select year_id,  sum(plansalesmoney)*1000000/100000000 as plan_sales_money from t_weekreport_yearplan where year_id = (select extract(year from sysdate)　from dual) group by year_id";
    //上一周的销量和上上周销量
    private static final String CUR_WEEK_AND_LAST_WEEK_SALESMONEY = "select  year_id,week_id,  week_salemoney"
            + "      from "
            + "         (select year_id,week_id, NVL(sum(week_salemoney),0) as week_salemoney from t_weekreport_sum_sales "
            + "          group by   year_id,week_id"
            + "          order by year_id desc,week_id desc"
            + "          ) "
            + "          where rownum  <=2";
    //当前年份的销售额
    private static final String CUR_YEAR_SALES_MONEY = "select year_id, NVL(sum(week_salemoney),0) as salemoney from t_weekreport_sum_sales where year_id=(select extract(year "
            + "from sysdate)　from dual) group by year_id";
    //当前年当前周销售额
    private static final String CUR_YEAR_WEEK_SALES_MONEY = "";
    //当前年当前周当前期总销售
    private static final String CUR_YEAR_WEEK_DRAW_SALES_MONEY = "";
    //查询当前年份时间中周销量最大的
    private static final String CUR_YEAR_WEEK_MAX_SALES = "select NVL(max(week_salemoney),0) from (select week_id, NVL(sum(week_salemoney),0) as week_salemoney from "
            + "t_weekreport_sum_sales  where year_id=(select extract(year from sysdate)　from dual) group by week_id)";
    //当前年份有多少周数
    private static final String CUR_YEAR_WEEK_ID_LIST = "select week_id,year_id from t_week_info where year_id=(select extract(year from sysdate)　from dual)";
    //截止当前年份的每周销量和年销售量
    private static final String CUR_YEAR_SALES_WEEK_SALES_INFO = "select NVL(sum(week_salemoney),0) as week_salemoney,NVL(sum(year_salemoney),0) as salemoney,week_id from t_weekreport_sum_sales where year_id=(select extract(year from sysdate)　from dual) group by week_id order by week_id";
    //电脑票上一分钟交易额
    private static final String LAST_MINUTE_SALES = "select NVL(sum(allmoney),0) as allmoney  from ("
            + "        select out_money+income_money as allmoney from "
            + "        (select sum(out_money) as out_money,sum(income_money) as income_money from "
            + "        term.t_account_detail where to_char(trade_time,'yyyy-mm-dd hh24:mi:ss')> (SELECT TO_CHAR(SYSDATE - 1 / 24 / 60, 'yyyy-mm-dd hh24:mi:ss') FROM DUAL)"
            + "        and to_char(trade_time,'yyyy-mm-dd hh24:mi:ss') <(SELECT TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss')  FROM DUAL)"
            + "        )"
            + "        union "
            + "        select out_money+income_money as allmoney from "
            + "        (select sum(out_money) as out_money,sum(income_money) as income_money "
            + "        from gamb.t_account_detail where to_char(trade_time,'yyyy-mm-dd hh24:mi:ss')> (SELECT TO_CHAR(SYSDATE - 1 / 24 / 60, 'yyyy-mm-dd hh24:mi:ss') FROM DUAL)"
            + "         and to_char(trade_time,'yyyy-mm-dd hh24:mi:ss') <(SELECT TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss')  FROM DUAL)"
            + "        )"
            + "        )";
    //电脑票上一分钟交易数
    private static final String LAST_MINUTE_SALES_COUNT = "  select NVL(sum(sales_count),0) as sales_count from ("
            + "  select count(*) as sales_count   from "
            + "  term.t_account_detail where  to_char(trade_time,'yyyy-mm-dd hh24:mi:ss')> (SELECT TO_CHAR(SYSDATE - 1 / 24 / 60, 'yyyy-mm-dd hh24:mi:ss') FROM DUAL)"
            + "  and to_char(trade_time,'yyyy-mm-dd hh24:mi:ss') <(SELECT TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss')  FROM DUAL)"
            + "  union"
            + "  select count(*) as sales_count   from "
            + "  gamb.t_account_detail where  to_char(trade_time,'yyyy-mm-dd hh24:mi:ss')> (SELECT TO_CHAR(SYSDATE - 1 / 24 / 60, 'yyyy-mm-dd hh24:mi:ss') FROM DUAL)"
            + "  and to_char(trade_time,'yyyy-mm-dd hh24:mi:ss') <(SELECT TO_CHAR(SYSDATE, 'yyyy-mm-dd hh24:mi:ss')  FROM DUAL)"
            + "  )";
    //电脑票上一分钟交易额（含参）
    private static final String LAST_MINUTE_SALES_DCS = "select NVL(out_money+income_money,0) as allmoney from "
            + "("
            + "select sum(out_money) as out_money,sum(income_money) as income_money from "
            + "t_account_detail where  trade_time >= to_date(?,'yyyy-mm-dd hh24:mi:ss')"
            + "and trade_time < to_date(?,'yyyy-mm-dd hh24:mi:ss')"
            + ")";
    //电脑票上一分钟交易数(含参)
    private static final String LAST_MINUTE_SALES_COUNT_DCS = " select count(*) as sales_count   from "
            + "t_account_detail where  trade_time >= to_date(?,'yyyy-mm-dd hh24:mi:ss')"
            + "and trade_time < to_date(?,'yyyy-mm-dd hh24:mi:ss')";
    //历史周最大销售量
    private static final String QUERY_HOSTORY_MAX_SALES = "select a.week_id, a.year_id, a.begin_date, a.end_date, b.week_salemoney as max_week_saleminey"
            + "  from t_week_info a,"
            + "       (select sum(week_salemoney) as week_salemoney, week_id, year_id"
            + "          from t_weekreport_sum_sales"
            + "         group by week_id, year_id"
            + "         order by week_salemoney desc) b"
            + " where a.week_id = b.week_id"
            + "   and a.year_id = b.year_id"
            + "   and rownum = 1";
   
    //历史单分钟最大交易金额
    private static final String QUERY_HOSTORY_ONE_MINUTE_MAX_SALES = "select stat_time,sale_money,sale_number from("
            + "select stat_time,sale_money,sale_number from t_monitor_minute_sales order by sale_money desc"
            + ") where rownum=1";
    //历史单分钟最大交易笔数
    private static final String QUERY_HOSTORY_ONE_MINUTE_MAX_NUMBER = "select stat_time,sale_money,sale_number from("
            + "select stat_time,sale_money,sale_number from t_monitor_minute_sales order by sale_number desc"
            + ") where rownum=1";

    //年累计
    private static final String GET_YEAR_SALE_MONEY = "SELECT NVL(SUM(s.year_salemoney),0) AS m1 "
            + "FROM t_weekreport_sum_sales s, "
            + "  (SELECT year_id, "
            + "    NVL(MAX(week_id),0) AS max_week_id "
            + "  FROM t_weekreport_sum_sales "
            + "  WHERE year_id= "
            + "    (SELECT extract(YEAR FROM sysdate) FROM dual "
            + "    ) "
            + "  GROUP BY year_id "
            + "  ) m "
            + "WHERE s.year_id = m.year_id "
            + "AND s.week_id   = m.max_week_id";
    //已经结算为累计到周
    private static final String GET_SALE_MONEY_HAVE_CLEAR = "SELECT NVL(SUM(t.sale_money),0) AS m2 "
            + "FROM T_stat_game_draw t, "
            + "  T_week_info w, "
            + "  T_game_draw_info d, "
            + "  (SELECT year_id, "
            + "    NVL(MAX(week_id),0) AS max_week_id "
            + "  FROM t_weekreport_sum_sales "
            + "  WHERE year_id= "
            + "    (SELECT extract(YEAR FROM sysdate)　from dual "
            + "    ) "
            + "  GROUP BY year_id "
            + "  ) m "
            + "WHERE t.game_id = d.game_id "
            + "AND t.draw_id   = d.draw_id "
            + "AND d.sales_end > w.end_date "
            + "AND w.year_id   = m.year_id "
            + "AND w.week_id   = m.max_week_id";
    //当前期
    private static final String GET_CURRENT_SALE_MONEY = "SELECT NVL(SUM(c.sale_money),0) AS m3 "
            + "FROM T_current_tmn_draw_stat c, "
            + "  T_game_draw_info d "
            + "WHERE c.game_id      = d.game_id "
            + "AND c.draw_id        = d.draw_id "
            + "AND d.process_status <160";

    private static final String GET_LAST_MINUTE_DATA = "select NVL(out_money + income_money, 0) as sale_money,"
            + "       NVL(c1,0 ) as sale_number"
            + "  from (select sum(out_money) as out_money,"
            + "               sum(income_money) as income_money,"
            + "               count(*） as c1"
            + "                from t_account_detail"
            + "         where trade_time >="
            + "               to_date(?, 'yyyy-mm-dd hh24:mi:ss')"
            + "           and trade_time <"
            + "               to_date(?, 'yyyy-mm-dd hh24:mi:ss'))";

    private static final String GET_LAST_MINUTE_DATA_FROM_STAT = "select stat_time, sale_money, sale_number"
            + "  from (select stat_time, sale_money, sale_number"
            + "          from t_monitor_minute_sales"
            + "         order by stat_time desc)"
            + " where rownum = 1";
    //获取当前销售量和预计销售量和当年预计完成时间
    private static final String GET_HOME_SCREEN_MONEY = "SELECT m.m1     AS m1,"
            + "  m.m1    /(TRUNC(f2.max1,'DDD')-TRUNC(f1.min1,'DDD') - 6) * (TRUNC(f3.max2,'DDD')-TRUNC(f1.min1,'DDD') - 6 )AS will_money, "
            + "  f1.min1 + p.plan / ( m.m1/(TRUNC(f2.max1,'DDD')-TRUNC(f1.min1,'DDD') - 6) )                                AS complete_day "
            + "FROM "
            + "  (SELECT NVL(SUM(s.year_salemoney),0) AS m1 "
            + "  FROM t_weekreport_sum_sales s, "
            + "    (SELECT year_id, "
            + "      NVL(MAX(week_id),0) AS max_week_id "
            + "    FROM t_weekreport_sum_sales "
            + "    WHERE year_id= "
            + "      (SELECT extract(YEAR FROM sysdate)　from dual "
            + "      ) "
            + "    GROUP BY year_id "
            + "    ) m "
            + "  WHERE s.year_id = m.year_id "
            + "  AND s.week_id   = m.max_week_id "
            + "  ) m, "
            + "  (SELECT MIN(begin_date) AS min1 "
            + "  FROM T_week_info "
            + "  WHERE year_id= "
            + "    (SELECT extract(YEAR FROM sysdate)　from dual "
            + "    ) "
            + "  ) f1, "
            + "  (SELECT MAX(end_date) AS max1 "
            + "  FROM T_week_info w2, "
            + "    (SELECT year_id, "
            + "      NVL(MAX(week_id),0) AS max_week_id "
            + "    FROM t_weekreport_sum_sales "
            + "    WHERE year_id= "
            + "      (SELECT extract(YEAR FROM sysdate)　from dual "
            + "      ) "
            + "    GROUP BY year_id "
            + "    ) m "
            + "  WHERE w2.year_id= "
            + "    (SELECT extract(YEAR FROM sysdate)　from dual "
            + "    ) "
            + "  AND w2.week_id = m.max_week_id "
            + "  ) f2, "
            + "  (SELECT MAX(end_date) AS max2 "
            + "  FROM T_week_info "
            + "  WHERE year_id= "
            + "    (SELECT extract(YEAR FROM sysdate)　from dual "
            + "    ) "
            + "  ) f3 , "
            + "  (SELECT SUM(plansalesmoney) * 1000000 AS plan "
            + "  FROM t_Weekreport_Yearplan "
            + "  WHERE year_id= "
            + "    (SELECT extract(YEAR FROM sysdate)　from dual "
            + "    ) "
            + "  ) p";

    @Override
    public List<YearTaskPlan> getProvinceYearPlanSalesMoney(JdbcTemplate jdbcTemplate) {
        return queryForList(jdbcTemplate, CUR_YEAR_PROVINCE_PLANSALESMONEY, new Object[]{}, YearTaskPlan.class);
    }

    @Override
    public List<WeekReportSumSales> getTwoWeekWeekReportSumSales(JdbcTemplate jdbcTemplate) {
        return queryForList(jdbcTemplate, CUR_WEEK_AND_LAST_WEEK_SALESMONEY, null, WeekReportSumSales.class);
    }

    @Override
    public List<WeekReportSumSales> getNowYearWeekReportSumSales(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, CUR_YEAR_SALES_MONEY, null, WeekReportSumSales.class);
    }

    @Override
    public int getWeekMaxSalesWeekReportSumSales(JdbcTemplate jdbcTemplate) {
        return this.queryForInteger(jdbcTemplate, CUR_YEAR_WEEK_MAX_SALES, null);
    }

    @Override
    public List<WeekReportSumSales> getYearSalesWeekSalesList(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, CUR_YEAR_SALES_WEEK_SALES_INFO, null, WeekReportSumSales.class);
    }

    @Override
    public List<WeekReportSumSales> getWeekIdList(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, CUR_YEAR_WEEK_ID_LIST, null, WeekReportSumSales.class);
    }

    @Override
    public int getLastMinuteSales(JdbcTemplate jdbcTemplate) {
        return this.queryForInteger(jdbcTemplate, LAST_MINUTE_SALES, null);
    }

    @Override
    public int getLastMinuteSalesCount(JdbcTemplate jdbcTemplate) {
        return this.queryForInteger(jdbcTemplate, LAST_MINUTE_SALES_COUNT, null);
    }

    @Override
    public WeekReportSumSales getHostoryMaxSales(JdbcTemplate jdbcTemplate) {
        return this.queryForObject(jdbcTemplate, QUERY_HOSTORY_MAX_SALES, null, WeekReportSumSales.class);
    }

    @Override
    public List<MonitorMinuteSales> getHostoryOneMinuteMaxSales(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, QUERY_HOSTORY_ONE_MINUTE_MAX_SALES, null, MonitorMinuteSales.class);
    }

    @Override
    public List<MonitorMinuteSales> getHostoryOneMinuteMaxNumber(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, QUERY_HOSTORY_ONE_MINUTE_MAX_NUMBER, null, MonitorMinuteSales.class);
    }

    @Override
    public int getLastMinuteSales(JdbcTemplate jdbcTemplate, String beginTime, String endTime) {
        return this.queryForInteger(jdbcTemplate, LAST_MINUTE_SALES_DCS, new Object[]{beginTime, endTime});
    }

    @Override
    public int getLastMinuteSalesCount(JdbcTemplate jdbcTemplate, String beginTime, String endTime) {
        return this.queryForInteger(jdbcTemplate, LAST_MINUTE_SALES_COUNT_DCS, new Object[]{beginTime, endTime});
    }

    @Override
    public List<WeekReportSumSales> getNowYearCurWeekReportSumSales(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, CUR_YEAR_WEEK_SALES_MONEY, null, WeekReportSumSales.class);
    }

    @Override
    public List<WeekReportSumSales> getNowYearCurWeekCurDrawReportSumSales(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, CUR_YEAR_WEEK_DRAW_SALES_MONEY, null, WeekReportSumSales.class);
    }

    //年累计销量
    @Override
    public Long getYearSaleMoney(JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.queryForObject(GET_YEAR_SALE_MONEY, null, Long.class);
    }

    //已经结算为累计到周
    @Override
    public Long getSaleMoneyHaveClear(JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.queryForObject(GET_SALE_MONEY_HAVE_CLEAR, null, Long.class);
    }

    //当前期
    @Override
    public Long getCurrentSaleMoney(JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.queryForObject(GET_CURRENT_SALE_MONEY, null, Long.class);
    }

    //从detail表中获取上一分钟数据
    @Override
    public MonitorMinuteSales getLastMinuteData(JdbcTemplate jdbcTemplate, String beginTime, String endTime) {
        return this.queryForObject(jdbcTemplate, GET_LAST_MINUTE_DATA, new Object[]{beginTime, endTime}, MonitorMinuteSales.class);
    }

    //从分钟统计表中获取上一分钟数据，第一条记录
    @Override
    public MonitorMinuteSales getLastMinuteDataFromStat(JdbcTemplate jdbcTemplate) {
        return this.queryForObject(jdbcTemplate, GET_LAST_MINUTE_DATA_FROM_STAT, null, MonitorMinuteSales.class);
    }

    @Override
    public YearTaskPlan getHomeScreenMoney(JdbcTemplate jdbcTemplate) {
        return this.queryForObject(jdbcTemplate, GET_HOME_SCREEN_MONEY, null, YearTaskPlan.class);
    }
}
