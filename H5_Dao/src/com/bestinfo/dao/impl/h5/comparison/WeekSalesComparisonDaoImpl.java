/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.impl.h5.comparison;

import com.bestinfo.bean.h5.comparison.HWeekSalesComparison;
import com.bestinfo.dao.h5.comparison.IWeekSalesComparisonDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 年销量/周销量对比（时间维度销量对比）实现类
 *
 * @author Administrator
 */
@Repository
public class WeekSalesComparisonDaoImpl extends BaseDaoImpl implements IWeekSalesComparisonDao {

    @Override
    public List<HWeekSalesComparison> getWeekSalesComparisonData(JdbcTemplate jdbcTemplate,Map<String, Integer> params) {
        List<HWeekSalesComparison> list = new ArrayList<>();
        Integer choiceDate = params.get("CHOICE_DATE");
        Integer choiceGame = params.get("CHOICE_GAME");
        //如果多年比较
        if (choiceDate == 1) {
            //如果全部票种
            if (choiceGame == 1) {
                String sql = "SELECT W.YEAR_ID AS YEAR_WEEK,NVL(SUM(S.SALE_MONEY),0) AS SALE_MONEY "
                        + " FROM T_YEAR_INFO W LEFT JOIN T_WEEKREPORT_SALES S ON (W.YEAR_ID = S.YEAR_ID AND 1=1) "
                        + " WHERE W.YEAR_ID BETWEEN ? AND ? "
                        + " GROUP BY W.YEAR_ID"
                        + " ORDER BY W.YEAR_ID";
                list = queryForList(jdbcTemplate, sql, new Object[]{params.get("YEAR_BEGIN"),params.get("YEAR_END")}, HWeekSalesComparison.class);
            }
            //如果电脑票
            if (choiceGame == 2) {
                String sql = "SELECT W.YEAR_ID AS YEAR_WEEK,NVL(SUM(S.SALE_MONEY),0) AS SALE_MONEY "
                        + "FROM T_YEAR_INFO W LEFT JOIN (SELECT YEAR_ID,WEEK_ID,SALE_MONEY FROM T_WEEKREPORT_SALES A,T_WEEKREPORT_GAME G WHERE A.GAME_ID = G.GAME_ID AND G.HAND_INPUT = 0 "
                        + "  AND (? = -999 OR ? <> -999 AND G.GAME_ID = ?)) S ON (W.YEAR_ID = S.YEAR_ID AND 1=1)"
                        + " WHERE W.YEAR_ID BETWEEN ? AND ?"
                        + " GROUP BY W.YEAR_ID "
                        + " ORDER BY W.YEAR_ID";
                list = queryForList(jdbcTemplate, sql, new Object[]{params.get("GAME_ID"),
                    params.get("GAME_ID"),
                    params.get("GAME_ID"),
                    params.get("YEAR_BEGIN"),
                    params.get("YEAR_END")
                }, HWeekSalesComparison.class);
            }
            //如果是China_Fu & GGua
            if (choiceGame == 3) {
                int gameId = 101;
                String sql = "SELECT W.YEAR_ID AS YEAR_WEEK,NVL(SUM(S.SALE_MONEY),0) AS SALE_MONEY "
                        + " FROM T_YEAR_INFO W LEFT JOIN (SELECT YEAR_ID,WEEK_ID,SALE_MONEY FROM T_WEEKREPORT_SALES WHERE GAME_ID = ?) S ON (W.YEAR_ID = S.YEAR_ID AND 1=1)"
                        + " WHERE W.YEAR_ID BETWEEN ? AND ?"
                        + " GROUP BY W.YEAR_ID "
                        + " ORDER BY W.YEAR_ID";
                list = queryForList(jdbcTemplate, sql, new Object[]{
                    gameId,
                    params.get("YEAR_BEGIN"),
                    params.get("YEAR_END")
                }, HWeekSalesComparison.class);
            }
            if (choiceGame == 4) {
                int gameId = 102;
                String sql = "SELECT W.YEAR_ID AS YEAR_WEEK,NVL(SUM(S.SALE_MONEY),0) AS SALE_MONEY "
                        + " FROM T_YEAR_INFO W LEFT JOIN (SELECT YEAR_ID,WEEK_ID,SALE_MONEY FROM T_WEEKREPORT_SALES WHERE GAME_ID = ?) S ON (W.YEAR_ID = S.YEAR_ID AND 1=1)"
                        + " WHERE W.YEAR_ID BETWEEN ? AND ?"
                        + " GROUP BY W.YEAR_ID "
                        + " ORDER BY W.YEAR_ID";
                list = queryForList(jdbcTemplate, sql, new Object[]{
                    gameId,
                    params.get("YEAR_BEGIN"),
                    params.get("YEAR_END")
                }, HWeekSalesComparison.class);
            }
        }else{
            //如果全部票种
            if (choiceGame == 1) {
                String sql = "SELECT W.YEARWEEK_ID AS YEAR_WEEK,NVL(SUM(S.SALE_MONEY),0) AS SALE_MONEY"
                        +" FROM T_WEEK_INFO W LEFT JOIN T_WEEKREPORT_SALES S ON (W.YEAR_ID = S.YEAR_ID AND W.WEEK_ID = S.WEEK_ID)"
                        +" WHERE W.YEARWEEK_ID BETWEEN ? AND ?"
                        +" GROUP BY W.YEARWEEK_ID"
                        +" ORDER BY W.YEARWEEK_ID";
                list = queryForList(jdbcTemplate, sql, new Object[]{
                    params.get("YEAR_BEGIN"),
                    params.get("YEAR_END")
                }, HWeekSalesComparison.class);
            }
            //如果电脑票
            if (choiceGame == 2) {
                String sql = "SELECT W.YEARWEEK_ID AS YEAR_WEEK,NVL(SUM(S.SALE_MONEY),0) AS SALE_MONEY"
                        +" FROM T_WEEK_INFO W LEFT JOIN (SELECT YEAR_ID,WEEK_ID,SALE_MONEY FROM T_WEEKREPORT_SALES A, T_WEEKREPORT_GAME G WHERE A.GAME_ID = G.GAME_ID AND G.HAND_INPUT=0"
                        +" AND (?=-999 OR ? <> -999 AND G.GAME_ID = ?)) S ON (W.YEAR_ID = S.YEAR_ID AND W.WEEK_ID = S.WEEK_ID)"
                        +" WHERE W.YEARWEEK_ID BETWEEN ? AND ?"
                        +" GROUP BY W.YEARWEEK_ID"
                        +" ORDER BY W.YEARWEEK_ID";
                list = queryForList(jdbcTemplate, sql, new Object[]{
                    params.get("GAME_ID"),
                    params.get("GAME_ID"),
                    params.get("GAME_ID"),
                    params.get("YEAR_BEGIN"),
                    params.get("YEAR_END")
                }, HWeekSalesComparison.class);
            }
            //如果是China_Fu & GGua
            if (choiceGame == 3) {
                int gameId = 101;
                String sql = "SELECT W.YEARWEEK_ID AS YEAR_WEEK,NVL(SUM(S.SALE_MONEY),0) AS SALE_MONEY"
                        +" FROM T_WEEK_INFO W LEFT JOIN (SELECT YEAR_ID,WEEK_ID,SALE_MONEY FROM T_WEEKREPORT_SALES WHERE GAME_ID=?) S ON (W.YEAR_ID = S.YEAR_ID AND W.WEEK_ID = S.WEEK_ID)"
                        +" WHERE W.YEARWEEK_ID BETWEEN ? AND ?"
                        +" GROUP BY W.YEARWEEK_ID"
                        +" ORDER BY W.YEARWEEK_ID";
                list = queryForList(jdbcTemplate, sql, new Object[]{
                    gameId,
                    params.get("YEAR_BEGIN"),
                    params.get("YEAR_END")
                }, HWeekSalesComparison.class);
            }
            if (choiceGame == 4) {
                int gameId = 102;
                String sql = "SELECT W.YEARWEEK_ID AS YEAR_WEEK,NVL(SUM(S.SALE_MONEY),0) AS SALE_MONEY"
                        +" FROM T_WEEK_INFO W LEFT JOIN (SELECT YEAR_ID,WEEK_ID,SALE_MONEY FROM T_WEEKREPORT_SALES WHERE GAME_ID=?) S ON (W.YEAR_ID = S.YEAR_ID AND W.WEEK_ID = S.WEEK_ID)"
                        +" WHERE W.YEARWEEK_ID BETWEEN ? AND ?"
                        +" GROUP BY W.YEARWEEK_ID"
                        +" ORDER BY W.YEARWEEK_ID";
                list = queryForList(jdbcTemplate, sql, new Object[]{
                    gameId,
                    params.get("YEAR_BEGIN"),
                    params.get("YEAR_END")
                }, HWeekSalesComparison.class);
            }
        }
        return list;
    }
}
