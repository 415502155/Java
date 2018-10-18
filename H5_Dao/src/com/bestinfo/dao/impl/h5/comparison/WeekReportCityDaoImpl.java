/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.dao.impl.h5.comparison;

import com.bestinfo.bean.h5.comparison.HTicketType;
import com.bestinfo.dao.h5.comparison.IWeekReportCityDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 票种同比增长 实现类
 * 
 * @author Administrator
 */
@Repository
public class WeekReportCityDaoImpl extends BaseDaoImpl implements IWeekReportCityDao{

    private static final String GET_WEEK_REPORT_CITY_YEAR = "SELECT A.CITY_NAME AS CITY_NAME,A.INC AS ONE_INC,DECODE(A.INC1,0,'-',TO_CHAR((A.INC-A.INC1)*100/A.INC1,'fm99999999990.00')) AS ONE_RATIO,B.INC AS TOW_INC,DECODE(B.INC1,0,'-',TO_CHAR((B.INC-B.INC1)*100/B.INC1,'fm99999999990.00')) AS TOW_RATIO,C.INC AS THR_INC,DECODE(C.INC1,0,'-',TO_CHAR((C.INC-C.INC1)*100/C.INC1,'fm99999999990.00')) AS THR_RATIO,D.INC AS ALL_INC,DECODE(D.INC1,0,'-',TO_CHAR((D.INC-D.INC1)*100/D.INC1,'fm99999999990.00')) AS ALL_RATIO FROM (SELECT D.CITY_ID,D.CITY_NAME,NVL(C.INC,0) AS INC,NVL(C.INC1,0) AS INC1 FROM (SELECT A.CITY_ID,SUM(DECODE(A.YEAR_ID, ?, A.SALE_MONEY,0)) AS INC,SUM(DECODE(A.YEAR_ID, ?-1, A.SALE_MONEY,0)) AS INC1 FROM T_WEEKREPORT_SALES A,T_WEEKREPORT_GAME B WHERE A.GAME_ID = B.GAME_ID AND B.HAND_INPUT = 0 AND A.YEAR_ID IN (?,?-1) GROUP BY A.CITY_ID) C,T_WEEKREPORT_CITY D WHERE C.CITY_ID(+) = D.CITY_ID) A,(SELECT B.CITY_ID,NVL(C.INC,0) AS INC,NVL(C.INC1,0) AS INC1 FROM T_WEEKREPORT_CITY B,(SELECT CITY_ID,SUM(DECODE(YEAR_ID, ?, SALE_MONEY,0)) AS INC,SUM(DECODE(YEAR_ID, ?-1, SALE_MONEY,0)) AS INC1 FROM T_WEEKREPORT_SALES WHERE GAME_ID = 101 AND YEAR_ID IN(?,?-1) GROUP BY CITY_ID) C WHERE C.CITY_ID(+) = B.CITY_ID) B,(SELECT B.CITY_ID,NVL(C.INC,0) AS INC,NVL(C.INC1,0) AS INC1 FROM T_WEEKREPORT_CITY B,(SELECT CITY_ID,SUM(DECODE(YEAR_ID, ?, SALE_MONEY,0)) AS INC,SUM(DECODE(YEAR_ID, ?-1, SALE_MONEY,0)) AS INC1 FROM T_WEEKREPORT_SALES WHERE GAME_ID = 102 AND YEAR_ID IN(?,?-1) GROUP BY CITY_ID) C WHERE C.CITY_ID(+) = B.CITY_ID) C,(SELECT B.CITY_ID,NVL(C.INC,0) AS INC,NVL(C.INC1,0) AS INC1 FROM T_WEEKREPORT_CITY B,(SELECT CITY_ID,SUM(DECODE(YEAR_ID, ?, SALE_MONEY,0)) AS INC,SUM(DECODE(YEAR_ID, ?-1, SALE_MONEY,0)) AS INC1 FROM T_WEEKREPORT_SALES WHERE YEAR_ID IN(?,?-1) GROUP BY CITY_ID) C WHERE C.CITY_ID(+) = B.CITY_ID) D WHERE A.CITY_ID = B.CITY_ID AND A.CITY_ID = C.CITY_ID AND A.CITY_ID = D.CITY_ID ORDER BY A.CITY_ID";

    private static final String GET_WEEK_REPORT_CITY_WEEK = "SELECT A.CITY_NAME AS CITY_NAME,A.INC AS ONE_INC,DECODE(A.INC1,0,'-',TO_CHAR((A.INC-A.INC1)*100/A.INC1,'fm99999999990.00')) AS ONE_RATIO,B.INC AS TOW_INC,DECODE(B.INC1,0,'-',TO_CHAR((B.INC-B.INC1)*100/B.INC1,'fm99999999990.00')) AS TOW_RATIO,C.INC AS THR_INC,DECODE(C.INC1,0,'-',TO_CHAR((C.INC-C.INC1)*100/C.INC1,'fm99999999990.00')) AS THR_RATIO,D.INC AS ALL_INC,DECODE(D.INC1,0,'-',TO_CHAR((D.INC-D.INC1)*100/D.INC1,'fm99999999990.00')) AS ALL_RATIO FROM (SELECT D.CITY_ID,D.CITY_NAME,NVL(C.INC,0) AS INC,NVL(C.INC1,0) AS INC1 FROM (SELECT A.CITY_ID,SUM(DECODE(A.YEAR_ID, ?, A.SALE_MONEY,0)) AS INC,SUM(DECODE(A.YEAR_ID, ?-1, A.SALE_MONEY,0)) AS INC1 FROM T_WEEKREPORT_SALES A,T_WEEKREPORT_GAME B WHERE A.GAME_ID = B.GAME_ID AND B.HAND_INPUT = 0 AND A.YEAR_ID IN (?,?-1) AND A.WEEK_ID = ? GROUP BY A.CITY_ID) C,T_WEEKREPORT_CITY D WHERE C.CITY_ID(+) = D.CITY_ID) A,(SELECT B.CITY_ID,NVL(C.INC,0) AS INC,NVL(C.INC1,0) AS INC1 FROM T_WEEKREPORT_CITY B,(SELECT CITY_ID,SUM(DECODE(YEAR_ID, ?, SALE_MONEY,0)) AS INC,SUM(DECODE(YEAR_ID, ?-1, SALE_MONEY,0)) AS INC1 FROM T_WEEKREPORT_SALES WHERE GAME_ID = 101 AND YEAR_ID IN(?,?-1) AND WEEK_ID = ? GROUP BY CITY_ID) C WHERE C.CITY_ID(+) = B.CITY_ID) B,(SELECT B.CITY_ID,NVL(C.INC,0) AS INC,NVL(C.INC1,0) AS INC1 FROM T_WEEKREPORT_CITY B,(SELECT CITY_ID,SUM(DECODE(YEAR_ID, ?, SALE_MONEY,0)) AS INC,SUM(DECODE(YEAR_ID, ?-1, SALE_MONEY,0)) AS INC1 FROM T_WEEKREPORT_SALES WHERE GAME_ID = 102 AND YEAR_ID IN(?,?-1) AND WEEK_ID = ? GROUP BY CITY_ID) C WHERE C.CITY_ID(+) = B.CITY_ID) C,(SELECT B.CITY_ID,NVL(C.INC,0) AS INC,NVL(C.INC1,0) AS INC1 FROM T_WEEKREPORT_CITY B,(SELECT CITY_ID,SUM(DECODE(YEAR_ID, ?, SALE_MONEY,0)) AS INC,SUM(DECODE(YEAR_ID, ?-1, SALE_MONEY,0)) AS INC1 FROM T_WEEKREPORT_SALES WHERE YEAR_ID IN(?,?-1) AND WEEK_ID = ? GROUP BY CITY_ID) C WHERE C.CITY_ID(+) = B.CITY_ID) D WHERE A.CITY_ID = B.CITY_ID AND A.CITY_ID = C.CITY_ID AND A.CITY_ID = D.CITY_ID ORDER BY A.CITY_ID";
    /**
     * 年销量
     * @param jdbcTemplate
     * @param yearId
     * @return 
     */
    @Override
    public List<HTicketType> getWeekReportCityYear(JdbcTemplate jdbcTemplate, Integer yearId) {
        return queryForList(jdbcTemplate,GET_WEEK_REPORT_CITY_YEAR, new Object[]{
            yearId,
            yearId,
            yearId,
            yearId,
            yearId,
            yearId,
            yearId,
            yearId,
            yearId,
            yearId,
            yearId,
            yearId,
            yearId,
            yearId,
            yearId,
            yearId
        }, HTicketType.class);
    }

    /**
     * 周销量
     * @param jdbcTemplate
     * @param yearId
     * @param weekId
     * @return 
     */
    @Override
    public List<HTicketType> getWeekReportCityWeek(JdbcTemplate jdbcTemplate, Integer yearId, Integer weekId) {
        return queryForList(jdbcTemplate, GET_WEEK_REPORT_CITY_WEEK, new Object[]{
            yearId,
            yearId,
            yearId,
            yearId,
            weekId,
            yearId,
            yearId,
            yearId,
            yearId,
            weekId,
            yearId,
            yearId,
            yearId,
            yearId,
            weekId,
            yearId,
            yearId,
            yearId,
            yearId,
            weekId
        }, HTicketType.class);
    }
    
}
