package com.bestinfo.dao.impl.h5.comparison;

import com.bestinfo.bean.h5.comparison.WeekReportGameAndSales;
import com.bestinfo.dao.h5.comparison.IGameContrastDao;
import com.bestinfo.dao.impl.BaseDaoImpl;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 票种销量对比
 * 周和年销量对比
 * @author Administrator
 */
@Repository
public class GameContrastDaoImpl extends BaseDaoImpl implements IGameContrastDao {

    /**
     * 查询电脑票、刮刮乐、在线即开的年销售量(当前年)
     */
    private static final String QUERY_WeekReportGameAndSalesList_LIST = "SELECT '刮刮乐' AS GAME_NAME,NVL(SUM(S.SALE_MONEY),0) AS SALE_MONEY1   \n" +
            "            FROM T_WEEKREPORT_SALES S,T_WEEKREPORT_GAME G \n" +
            "            WHERE YEAR_ID=(select  to_char(sysdate, 'yyyy' )  from dual)  AND   S.GAME_ID = G.GAME_ID   AND G.HAND_INPUT = 1  AND G.GAME_ID =101\n" +
            "             UNION  ALL\n" +
            "SELECT '在线即开' AS GAME_NAME,NVL(SUM(S.SALE_MONEY),0) AS SALE_MONEY1   \n" +
            "            FROM T_WEEKREPORT_SALES S,T_WEEKREPORT_GAME G \n" +
            "            WHERE YEAR_ID=(select  to_char(sysdate, 'yyyy' )  from dual)  AND   S.GAME_ID = G.GAME_ID   AND G.HAND_INPUT = 1  AND G.GAME_ID =102 \n" +
            "            UNION ALL\n" +
            "SELECT '电脑票' AS GAME_NAME,NVL(SUM(S.SALE_MONEY),0) AS SALE_MONEY1   \n" +
            "            FROM T_WEEKREPORT_SALES S,T_WEEKREPORT_GAME G \n" +
            "            WHERE YEAR_ID=(select  to_char(sysdate, 'yyyy' )  from dual) AND S.GAME_ID = G.GAME_ID   AND G.HAND_INPUT = 0 ";
    /***
     * 查询电脑票、刮刮乐、在线即开的年销售量(上一年第一周到当年的最大周数)
     */
    private static final String QUERY_WeekReportGameAndSalesList_LIST_LASTYEAR="SELECT '刮刮乐' AS GAME_NAME,NVL(SUM(S.SALE_MONEY),0) AS SALE_MONEY1   \n" +
            "                        FROM T_WEEKREPORT_SALES S,T_WEEKREPORT_GAME G \n" +
            "                        WHERE YEAR_ID=(select  to_char(sysdate, 'yyyy' )-1  from dual)  AND   S.GAME_ID = G.GAME_ID   AND G.HAND_INPUT = 1  AND G.GAME_ID =101 AND WEEK_ID between 0 and (SELECT MAX(week_id) \n" +
            "                        FROM T_WEEKREPORT_SALES \n" +
            "                        WHERE year_id = \n" +
            "                        (SELECT TO_CHAR(sysdate, 'yyyy' ) FROM dual \n" +
            "                        ) \n" +
            "                        )  \n" +
            "                        UNION ALL\n" +
            "SELECT '在线即开' AS GAME_NAME,NVL(SUM(S.SALE_MONEY),0) AS SALE_MONEY1   \n" +
            "                        FROM T_WEEKREPORT_SALES S,T_WEEKREPORT_GAME G \n" +
            "                        WHERE YEAR_ID=(select  to_char(sysdate, 'yyyy' )-1  from dual)  AND   S.GAME_ID = G.GAME_ID   AND G.HAND_INPUT = 1  AND G.GAME_ID =102 AND WEEK_ID between 0 and (SELECT MAX(week_id) \n" +
            "                        FROM T_WEEKREPORT_SALES \n" +
            "                        WHERE year_id = \n" +
            "                        (SELECT TO_CHAR(sysdate, 'yyyy' ) FROM dual \n" +
            "                        ) \n" +
            "                        )\n" +
            "                        UNION ALL \n" +
            "SELECT '电脑票' AS GAME_NAME,NVL(SUM(S.SALE_MONEY),0) AS SALE_MONEY1   \n" +
            "                        FROM T_WEEKREPORT_SALES S,T_WEEKREPORT_GAME G \n" +
            "                        WHERE YEAR_ID=(select  to_char(sysdate, 'yyyy' )-1  from dual) AND S.GAME_ID = G.GAME_ID   AND G.HAND_INPUT = 0 AND WEEK_ID between 0 and (SELECT MAX(week_id) \n" +
            "                        FROM T_WEEKREPORT_SALES \n" +
            "                        WHERE year_id = \n" +
            "                        (SELECT TO_CHAR(sysdate, 'yyyy' ) FROM dual \n" +
            "                        ) \n" +
            "                        ) ";
    private static final String QUERY_WEEKREPORTGAMEANDSALESLIST_LASTWEEK = "SELECT '刮刮乐'               AS GAME_NAME, \n" +
            "            NVL(SUM(S.SALE_MONEY),0) AS SALE_MONEY1, \n" +
            "            MAX(s.week_id)           AS week_id \n" +
            "            FROM T_WEEKREPORT_SALES S, \n" +
            "            T_WEEKREPORT_GAME G \n" +
            "            WHERE YEAR_ID= \n" +
            "            (SELECT TO_CHAR(sysdate, 'yyyy' ) \n" +
            "            FROM dual \n" +
            "            ) \n" +
            "            AND S.GAME_ID    = G.GAME_ID \n" +
            "            AND G.HAND_INPUT = 1 \n" +
            "            AND G.GAME_ID    =101 \n" +
            "            AND s.week_id =  \n" +
            "            (SELECT MAX(week_id) \n" +
            "            FROM T_WEEKREPORT_SALES \n" +
            "            WHERE year_id = \n" +
            "            (SELECT TO_CHAR(sysdate, 'yyyy' ) FROM dual \n" +
            "            ) \n" +
            "            )\n" +
            "UNION ALL\n" +
            "SELECT '在线即开'              AS GAME_NAME, \n" +
            "            NVL(SUM(S.SALE_MONEY),0) AS SALE_MONEY1, \n" +
            "            MAX(s.week_id)           AS week_id \n" +
            "            FROM T_WEEKREPORT_SALES S, \n" +
            "            T_WEEKREPORT_GAME G \n" +
            "            WHERE YEAR_ID= \n" +
            "              (SELECT TO_CHAR(sysdate, 'yyyy' ) \n" +
            "              FROM dual \n" +
            "              ) \n" +
            "            AND S.GAME_ID    = G.GAME_ID \n" +
            "            AND G.HAND_INPUT = 1 \n" +
            "            AND G.GAME_ID    =102 \n" +
            "            AND s.week_id =( \n" +
            "              SELECT MAX(week_id) \n" +
            "              FROM T_WEEKREPORT_SALES \n" +
            "              WHERE year_id = \n" +
            "                (SELECT TO_CHAR(sysdate, 'yyyy' ) FROM dual \n" +
            "                ) \n" +
            "              )\n" +
            "UNION ALL\n" +
            "SELECT '电脑票'               AS GAME_NAME,\n" +
            "            NVL(SUM(S.SALE_MONEY),0) AS SALE_MONEY1,\n" +
            "            MAX(s.week_id)           AS week_id \n" +
            "            FROM T_WEEKREPORT_SALES S, \n" +
            "            T_WEEKREPORT_GAME G \n" +
            "            WHERE s.YEAR_ID= \n" +
            "            (SELECT TO_CHAR(sysdate, 'yyyy' ) \n" +
            "            FROM dual \n" +
            "            ) \n" +
            "            AND S.GAME_ID    = G.GAME_ID \n" +
            "            AND G.HAND_INPUT = 0 \n" +
            "            AND s.week_id = \n" +
            "            (SELECT MAX(week_id) \n" +
            "            FROM T_WEEKREPORT_SALES \n" +
            "            WHERE year_id = \n" +
            "            (SELECT TO_CHAR(sysdate, 'yyyy' ) FROM dual \n" +
            "            ) \n" +
            "            )";

    private static final String QUERY_WEEKREPORTGAMEANDSALESLIST_CURWEEK = "SELECT '刮刮乐'               AS GAME_NAME, \n" +
            "            NVL(SUM(S.SALE_MONEY),0) AS SALE_MONEY1, \n" +
            "            MAX(s.week_id)           AS week_id \n" +
            "            FROM T_WEEKREPORT_SALES S, \n" +
            "            T_WEEKREPORT_GAME G \n" +
            "            WHERE YEAR_ID= \n" +
            "            (SELECT TO_CHAR(sysdate, 'yyyy' ) \n" +
            "            FROM dual \n" +
            "            ) \n" +
            "            AND S.GAME_ID    = G.GAME_ID \n" +
            "            AND G.HAND_INPUT = 1 \n" +
            "            AND G.GAME_ID    =101 \n" +
            "            AND s.week_id= \n" +
            "            (SELECT MAX(week_id) \n" +
            "            FROM T_WEEKREPORT_SALES \n" +
            "            WHERE year_id = \n" +
            "            (SELECT TO_CHAR(sysdate, 'yyyy' ) FROM dual \n" +
            "            ) \n" +
            "            AND week_id < \n" +
            "            (SELECT MAX(week_id) \n" +
            "            FROM T_WEEKREPORT_SALES \n" +
            "            WHERE year_id = \n" +
            "            (SELECT TO_CHAR(sysdate, 'yyyy' ) FROM dual \n" +
            "            ) \n" +
            "            ))\n" +
            "UNION ALL\n" +
            "SELECT '在线即开'              AS GAME_NAME, \n" +
            "            NVL(SUM(S.SALE_MONEY),0) AS SALE_MONEY1, \n" +
            "            MAX(s.week_id)           AS week_id \n" +
            "            FROM T_WEEKREPORT_SALES S, \n" +
            "            T_WEEKREPORT_GAME G \n" +
            "            WHERE YEAR_ID= \n" +
            "            (SELECT TO_CHAR(sysdate, 'yyyy' ) \n" +
            "            FROM dual \n" +
            "            ) \n" +
            "            AND S.GAME_ID    = G.GAME_ID \n" +
            "            AND G.HAND_INPUT = 1 \n" +
            "            AND G.GAME_ID    =102 \n" +
            "            AND s.week_id = \n" +
            "            (SELECT MAX(week_id) \n" +
            "            FROM T_WEEKREPORT_SALES \n" +
            "            WHERE year_id = \n" +
            "            (SELECT TO_CHAR(sysdate, 'yyyy' ) FROM dual \n" +
            "            ) \n" +
            "            AND week_id < \n" +
            "            (SELECT MAX(week_id) \n" +
            "            FROM T_WEEKREPORT_SALES \n" +
            "            WHERE year_id = \n" +
            "            (SELECT TO_CHAR(sysdate, 'yyyy' ) FROM dual \n" +
            "            ) \n" +
            "            )) \n" +
            "UNION  ALL           \n" +
            "SELECT '电脑票' AS GAME_NAME, \n" +
            "            NVL(SUM(S.SALE_MONEY),0) AS SALE_MONEY1, \n" +
            "            MAX(s.week_id)           AS week_id \n" +
            "            FROM T_WEEKREPORT_SALES S, \n" +
            "            T_WEEKREPORT_GAME G \n" +
            "            WHERE s.YEAR_ID= \n" +
            "            (SELECT TO_CHAR(sysdate, 'yyyy' ) \n" +
            "            FROM dual \n" +
            "            ) \n" +
            "            AND S.GAME_ID    = G.GAME_ID \n" +
            "            AND G.HAND_INPUT = 0 \n" +
            "            AND s.week_id = \n" +
            "            (SELECT MAX(week_id) \n" +
            "            FROM T_WEEKREPORT_SALES \n" +
            "            WHERE year_id = \n" +
            "            (SELECT TO_CHAR(sysdate, 'yyyy' ) FROM dual \n" +
            "            ) \n" +
            "            AND week_id < \n" +
            "            (SELECT MAX(week_id) \n" +
            "            FROM T_WEEKREPORT_SALES \n" +
            "            WHERE year_id = \n" +
            "            (SELECT TO_CHAR(sysdate, 'yyyy' ) FROM dual \n" +
            "            ) \n" +
            "            ) \n" +
            "            ) ";
    //两个年份之间的电脑票销量纪录
    private static final String QUERY_WEEKREPORTANDSALES_BY_START_END_YEAR_COMPUTER = "SELECT NVL(SUM(S.SALE_MONEY),0) AS SALE_MONEY1 ,s.year_id FROM T_WEEKREPORT_SALES S,T_WEEKREPORT_GAME G "
            + "WHERE   "
            + "s.YEAR_ID between ? and ? AND "
            + "S.GAME_ID = G.GAME_ID   AND G.HAND_INPUT = 0  group by s.year_id order by s.year_id desc";
    //两个年份之间的刮刮乐销量纪录
    private static final String QUERY_WEEKREPORTANDSALES_BY_START_END_YEAR_101 = "SELECT NVL(SUM(S.SALE_MONEY),0) AS SALE_MONEY1,s.year_id  FROM T_WEEKREPORT_SALES S,T_WEEKREPORT_GAME G  WHERE s.YEAR_ID between ? and  "
            + "?  AND   S.GAME_ID = G.GAME_ID   AND G.HAND_INPUT = 1  AND G.GAME_ID =101  group by s.year_id order by s.year_id desc";
    //两个年份之间的在线即开销售记录
    private static final String QUERY_WEEKREPORTANDSALES_BY_START_END_YEAR_102 = "SELECT NVL(SUM(S.SALE_MONEY),0) AS SALE_MONEY1,s.year_id  "
            + "FROM T_WEEKREPORT_SALES S,T_WEEKREPORT_GAME G "
            + "WHERE s.YEAR_ID between ? and ?  AND   S.GAME_ID = G.GAME_ID   AND G.HAND_INPUT = 1  AND G.GAME_ID =102  group by s.year_id order by s.year_id desc";
    //两个年份之间的全票种销售记录
    private static final String QUERY_WEEKREPORTANDSALES_BY_ALLGAME = "SELECT W.YEAR_ID AS YEAR_ID,NVL(SUM(S.SALE_MONEY),0) AS SALE_MONEY1  "
            + "FROM T_YEAR_INFO W JOIN T_WEEKREPORT_SALES S ON (W.YEAR_ID = S.YEAR_ID AND 1=1)  "
            + "WHERE W.YEAR_ID BETWEEN ? AND ?  "
            + "GROUP BY W.YEAR_ID "
            + "ORDER BY W.YEAR_ID DESC ";
    //某年份某周到某年份某周全票种销售记录
    private static final String QUERY_WEEKREPORTANDSALES_BY_START_END_WEEK_ALLGAME = "SELECT sum(s.sale_money) sale_money,s.year_id,s.WEEK_ID, "
            + "to_number(s.year_id||(s.WEEK_ID+10)) d "
            + "FROM T_WEEKREPORT_SALES S,T_WEEKREPORT_GAME G "
            + "WHERE  S.GAME_ID = G.GAME_ID  "
            + "and s.year_id||(s.WEEK_ID+10)>=? and  s.year_id||(s.WEEK_ID+10)<=?  "
            + "group by s.week_id ,s.year_id    order    by s.year_id,s.week_id";
    //某年某周到某年某周电脑票的销量列表
    private static final String QUERY_WEEKREPORTANDSALES_BY_START_END_WEEK_COMPUTER = "SELECT sum(s.sale_money) sale_money,s.year_id,s.WEEK_ID,to_number(s.year_id||(s.WEEK_ID+10)) d FROM T_WEEKREPORT_SALES S,T_WEEKREPORT_GAME G "
            + "WHERE  s.year_id||(s.WEEK_ID+10)>=?   and  s.year_id||(s.WEEK_ID+10)<=? "
            + "AND  S.GAME_ID = G.GAME_ID   AND G.HAND_INPUT = 0  group by s.week_id ,s.year_id order by  s.year_id,s.week_id asc";
    //某年某周到某年某周刮刮乐的销量列表
    private static final String QUERY_WEEKREPORTANDSALES_BY_START_END_WEEK_101 = "SELECT sum(s.sale_money) sale_money,s.year_id,s.WEEK_ID, "
            + "to_number(s.year_id||(s.WEEK_ID+10)) d "
            + "FROM T_WEEKREPORT_SALES S,T_WEEKREPORT_GAME G "
            + "WHERE S.GAME_ID = G.GAME_ID  "
            + "  AND G.HAND_INPUT = 1  AND G.GAME_ID =101  "
            + "and s.year_id||(s.WEEK_ID+10)>=?  and s.year_id||(s.WEEK_ID+10)<=?  "
            + "group by s.week_id ,s.year_id    order    by s.year_id,s.week_id asc";
    //某年某周到某年某周中福在线的销量列表
    private static final String QUERY_WEEKREPORTANDSALES_BY_START_END_WEEK_102 = "SELECT sum(s.sale_money) sale_money,s.year_id,s.WEEK_ID, "
            + "to_number(s.year_id||(s.WEEK_ID+10)) d "
            + "FROM T_WEEKREPORT_SALES S,T_WEEKREPORT_GAME G "
            + "WHERE  S.GAME_ID = G.GAME_ID  "
            + "  AND G.HAND_INPUT = 1  AND G.GAME_ID =102  "
            + "and s.year_id||(s.WEEK_ID+10)>=? and  s.year_id||(s.WEEK_ID+10)<=?  "
            + "group by s.week_id ,s.year_id    order    by s.year_id,s.week_id asc";
    //默认查询当前时间前20周的全票种销售量
    private static final String QUERY_20_WEEK_SALES = "select sale_money,year_id,week_id,j from ( "
            + " select * from ( "
            + "  SELECT sum(s.sale_money) sale_money,s.year_id,s.WEEK_ID, "
            + " to_number(s.year_id||(s.WEEK_ID+10))  j "
            + "  FROM T_WEEKREPORT_SALES S,T_WEEKREPORT_GAME G "
            + " WHERE  S.GAME_ID = G.GAME_ID   "
            + " group by s.week_id ,s.year_id   order    by s.year_id,s.week_id  "
            + "  )  order by j desc   "
            + ")where rownum<=21   ";
    //当前周
    private static final String CUR_WEEK = "select week_id from t_week_info where (select to_char(sysdate,'yyyy-mm-dd') from dual) between to_char(begin_date,'yyyy-mm-dd')  and to_char(end_date,'yyyy-mm-dd') ";

    @Override
    public List<WeekReportGameAndSales> getWeekReportGameAndSalesList(JdbcTemplate jdbcTemplate) {
        try {
            return this.queryForList(jdbcTemplate, QUERY_WeekReportGameAndSalesList_LIST, null, WeekReportGameAndSales.class);
        } catch (Exception e) {
            logger.error("getWeekReportGameAndSalesList ex :", e);
            return null;
        }
    }

    @Override
    public List<WeekReportGameAndSales> getWeekReportGameAndSalesListByWeek(JdbcTemplate jdbcTemplate, int week) {
        try {
            return this.queryForList(jdbcTemplate, QUERY_WEEKREPORTGAMEANDSALESLIST_CURWEEK, null, WeekReportGameAndSales.class);
        } catch (Exception e) {
            logger.error("getWeekReportGameAndSalesListByWeek ex :", e);
            return null;
        }
    }

    @Override
    public List<WeekReportGameAndSales> getWeekReportGameAndSalesComputerList(JdbcTemplate jdbcTemplate, Integer start, Integer end, Integer type) {
        try {
            if (type == 0) {//全票种
                return this.queryForList(jdbcTemplate, QUERY_WEEKREPORTANDSALES_BY_ALLGAME, new Object[]{start, end}, WeekReportGameAndSales.class);
            } else if (type == 100) {//电脑票
                return this.queryForList(jdbcTemplate, QUERY_WEEKREPORTANDSALES_BY_START_END_YEAR_COMPUTER, new Object[]{start, end}, WeekReportGameAndSales.class);
            } else if (type == 101) {//刮刮乐
                return this.queryForList(jdbcTemplate, QUERY_WEEKREPORTANDSALES_BY_START_END_YEAR_101, new Object[]{start, end}, WeekReportGameAndSales.class);
            } else if (type == 102) {//在线即开
                return this.queryForList(jdbcTemplate, QUERY_WEEKREPORTANDSALES_BY_START_END_YEAR_102, new Object[]{start, end}, WeekReportGameAndSales.class);
            }
        } catch (Exception e) {
            logger.error("getWeekReportGameAndSalesListByWeek ex :", e);
            return null;
        }
        return null;
    }

    @Override
    public List<WeekReportGameAndSales> getWeekReportGameAndSalesesWeekList(JdbcTemplate jdbcTemplate, Integer start, Integer end, Integer type) {
        try {
            if (type == 0) {
                return this.queryForList(jdbcTemplate, QUERY_WEEKREPORTANDSALES_BY_START_END_WEEK_ALLGAME, new Object[]{start, end}, WeekReportGameAndSales.class);
            } else if (type == 100) {//电脑票
                return this.queryForList(jdbcTemplate, QUERY_WEEKREPORTANDSALES_BY_START_END_WEEK_COMPUTER, new Object[]{start, end}, WeekReportGameAndSales.class);
            } else if (type == 101) {//刮刮乐
                return this.queryForList(jdbcTemplate, QUERY_WEEKREPORTANDSALES_BY_START_END_WEEK_101, new Object[]{start, end}, WeekReportGameAndSales.class);
            } else if (type == 102) {//在线即开
                return this.queryForList(jdbcTemplate, QUERY_WEEKREPORTANDSALES_BY_START_END_WEEK_102, new Object[]{start, end}, WeekReportGameAndSales.class);
            }
        } catch (Exception e) {
            logger.error("getWeekReportGameAndSalesListByWeek ex :", e);
            return null;
        }
        return null;
    }

    @Override
    public Integer getCurWeek(JdbcTemplate jdbcTemplate) {
        try {
            return this.queryForInteger(jdbcTemplate, CUR_WEEK, null);
        } catch (Exception e) {
            logger.error("getCurWeek ex :", e);
            return null;
        }
    }

    @Override
    public List<WeekReportGameAndSales> getLastWeekSales(JdbcTemplate jdbcTemplate, int week) {
        try {
            logger.info("last week is :" + week);
            return this.queryForList(jdbcTemplate, QUERY_WEEKREPORTGAMEANDSALESLIST_LASTWEEK, null, WeekReportGameAndSales.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<WeekReportGameAndSales> getWeekReportGameAndSalese20(JdbcTemplate jdbcTemplate) {
        try {
            return this.queryForList(jdbcTemplate, QUERY_20_WEEK_SALES, null, WeekReportGameAndSales.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<WeekReportGameAndSales> getWeekReportGameAndSalesListByLastYear(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, QUERY_WeekReportGameAndSalesList_LIST_LASTYEAR, null, WeekReportGameAndSales.class);
    }

}
