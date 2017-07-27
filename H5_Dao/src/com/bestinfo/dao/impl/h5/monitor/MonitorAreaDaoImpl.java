/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.impl.h5.monitor;

import com.bestinfo.bean.h5.monitor.HMonitorArea;
import com.bestinfo.dao.h5.monitor.IMonitorAreaDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 区域监控实现类
 *
 * @author Administrator
 */
@Repository
public class MonitorAreaDaoImpl extends BaseDaoImpl implements IMonitorAreaDao {
    //各个地市销量任务和年任务列表
    private static final String GET_AREA_MONITORING_INFO = "SELECT C.CITY_ID,C.CITY_NAME,A.SALE_MONEY,B.PALN_MONEY,DECODE(B.PALN_MONEY,0,0,ROUND(A.SALE_MONEY*100/B.PALN_MONEY)) AS FINISH_RATIO,E.AVG_RATIO FROM "
            + "                                                            (SELECT B1.CITY_ID,NVL(SUM(A1.SALE_MONEY),0) AS SALE_MONEY  "
            + "                                                            FROM T_WEEKREPORT_SALES A1,T_WEEKREPORT_CITY B1 "
            + "                                                             WHERE  B1.CITY_ID = A1.CITY_ID(+) and a1.year_id=(select  to_char(sysdate, 'yyyy' )  from dual)"
            + "                                                             GROUP BY B1.CITY_ID) A,"
            + "                                                            (SELECT B1.CITY_ID,NVL(SUM(A1.PLANSALESMONEY),0)*1000000 AS PALN_MONEY "
            + "                                                             FROM T_WEEKREPORT_YEARPLAN A1,T_WEEKREPORT_CITY B1 "
            + "                                                             WHERE B1.CITY_ID = A1.CITY_ID(+) and year_id=(select  to_char(sysdate, 'yyyy' )  from dual)"
            + "                                                             GROUP BY B1.CITY_ID) B,T_WEEKREPORT_CITY C,"
            + "                                                            (SELECT DECODE(B1.PALN_MONEY,0,0,ROUND(A1.SALE_MONEY*100/B1.PALN_MONEY)) AS AVG_RATIO FROM "
            + "                                                             (SELECT NVL(SUM(SALE_MONEY),0) AS SALE_MONEY FROM T_WEEKREPORT_SALES ) A1,"
            + "                                                             (SELECT NVL(SUM(PLANSALESMONEY),0)*1000000 AS PALN_MONEY FROM T_WEEKREPORT_YEARPLAN ) B1) E "
            + "                                                            WHERE C.CITY_ID = A.CITY_ID AND C.CITY_ID = B.CITY_ID "
            + "                                                            ORDER BY C.CITY_ID";
    //获取各个区域当日销量
    private static final String GET_DAY_SALES = "SELECT CITY_ID,SUM(SALE_MONEY) as sale_money FROM ("
            + " SELECT B1.CITY_ID,SUM(A1.SALE_MONEY) AS SALE_MONEY "
            + " FROM term.T_CURRENT_TMN_DATE_STAT A1,term.T_TMN_INFO B1 "
            + " WHERE TO_CHAR(A1.OPERATOR_DATE,'YYYYMMDD') = TO_CHAR(SYSDATE,'YYYYMMDD') "
            + "   AND A1.TERMINAL_ID  = B1.TERMINAL_ID "
            + " GROUP BY B1.CITY_ID"
            + " UNION "
            + " SELECT B1.CITY_ID,SUM(A1.SALE_MONEY) AS SALE_MONEY "
            + " FROM gamb.T_CURRENT_TMN_DATE_STAT A1,gamb.T_TMN_INFO B1 "
            + " WHERE TO_CHAR(A1.OPERATOR_DATE,'YYYYMMDD') = TO_CHAR(SYSDATE,'YYYYMMDD') "
            + "   AND A1.TERMINAL_ID  = B1.TERMINAL_ID "
            + " GROUP BY B1.CITY_ID) GROUP BY CITY_ID order by city_id";
    //獲取當前周  判斷是否是當前年的第一周
    private static final String CUR_YEAR_WEEK_NUMBER = "select week_id from t_week_info "
            + "where (select to_char(sysdate,'yyyy-mm-dd') from dual) "
            + "between to_char(begin_date,'yyyy-mm-dd')  and to_char(end_date,'yyyy-mm-dd') ";
    //當前周上一周到當年最後一周之差
    private static final String CUR_YEAR_WEEK_AND_MAXWEEK_SUM = "select ( select max(week_id) as week_id from t_week_info where year_id=( select to_char(sysdate,'yyyy') from dual))"
            + "-(select week_id-1 from t_week_info where (select to_char(sysdate,'yyyy-mm-dd') from dual) between to_char(begin_date,'yyyy-mm-dd')  and to_char(end_date,'yyyy-mm-dd') ) as week_id"
            + " from dual";
    //上周銷量
    private static final String LAST_WEEK_SALES = "select NVL(sum(sale_money),0) as sale_money ,city_id from t_weekreport_sales where\n" +
            "            year_id=( select to_char(sysdate,'yyyy') from dual) \n" +
            "            and \n" +
            "            week_id=(             \n" +
            "            select nvl(max(week_id),0) as max_week_id from    t_weekreport_sales \n" +
            "            where year_id=(select extract(year from sysdate)　from dual)\n" +
            "            group by year_id\n" +
            "            )\n" +
            "            group by city_id order by  city_id";
    //截止上周總銷量
    private static final String LAST_WEEK_SALES_AND_MAXWEEK_SUM = " select NVL(sum(sale_money),0) as sale_money  ,city_id from t_weekreport_sales where"
            + "  year_id=( select to_char(sysdate,'yyyy') from dual) "
            + " and week_id<( select week_id-1 from t_week_info where (select to_char(sysdate,'yyyy-mm-dd') from dual) between to_char(begin_date,'yyyy-mm-dd')  and to_char(end_date,'yyyy-mm-dd') ) and city_id<>0 and city_id<>30"
            + " group by city_id order by  city_id";

    @Override
    public List<HMonitorArea> getYearSales(JdbcTemplate jdbcTemplate, Integer yearBegin, Integer yearEnd) {
        return queryForList(jdbcTemplate, GET_AREA_MONITORING_INFO, new Object[]{}, HMonitorArea.class);
    }

    @Override
    public List<HMonitorArea> getDaySales(JdbcTemplate jdbcTemplate) {
        return queryForList(jdbcTemplate, GET_DAY_SALES, null, HMonitorArea.class);

    }

    @Override
    public Integer getCurWeekNum(JdbcTemplate jdbcTemplate) {
        return this.queryForInteger(jdbcTemplate, CUR_YEAR_WEEK_NUMBER, null);
    }

    @Override
    public Integer getCurWeekAndMaxWeekSum(JdbcTemplate jdbcTemplate) {
        return this.queryForInteger(jdbcTemplate, CUR_YEAR_WEEK_AND_MAXWEEK_SUM, null);
    }

    @Override
    public List<HMonitorArea> getLastWeekSales(JdbcTemplate jdbcTemplate) {
        return queryForList(jdbcTemplate, LAST_WEEK_SALES, null, HMonitorArea.class);
    }

    @Override
    public List<HMonitorArea> getLastWeekSumSales(JdbcTemplate jdbcTemplate) {
        return queryForList(jdbcTemplate, LAST_WEEK_SALES_AND_MAXWEEK_SUM, null, HMonitorArea.class);
    }

}
