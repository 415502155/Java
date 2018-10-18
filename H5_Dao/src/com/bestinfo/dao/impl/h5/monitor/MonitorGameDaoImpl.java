/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.impl.h5.monitor;

import com.bestinfo.bean.h5.monitor.HMonitorBetsByCity;
import com.bestinfo.bean.h5.monitor.HMonitorGame;
import com.bestinfo.bean.h5.monitor.HMonitorTmnInfo;
import com.bestinfo.dao.h5.monitor.IMonitorGameDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.page.Pagination;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 游戏监控实现类
 * 监控-业务-投注机实时监控
 * @author Administrator
 */
@Repository
public class MonitorGameDaoImpl extends BaseDaoImpl implements IMonitorGameDao {

    private static final String GET_MONITOR_GAME_DATA = "SELECT AA.GAME_ID AS GAME_ID,"
            + " AA.GAME_NAME AS GAME_NAME,"
            + " AA.DRAW_ID AS DRAW_ID,"
            + " AA.DRAW_NAME AS DRAW_NAME,"
            + " AA.SALES_END AS SALES_END,"
            + " DECODE(TRIM(AA.PROCESS_STATUS_NAME),'当前期','正在销售',AA.PROCESS_STATUS_NAME) AS PROCESS_STATUS_NAME,"
            + " SUM(AA.TP_MONEY) AS SALE_MONEY,"
            + " AA.PROCESS_STATUS AS PROCESS_STATUS"
            + "  FROM ( SELECT A.GAME_ID AS GAME_ID,"
            + " A.GAME_NAME AS GAME_NAME,"
            + " B.DRAW_ID AS DRAW_ID,"
            + " B.DRAW_NAME AS DRAW_NAME,"
            + " to_char(B.SALES_END, 'MM-DD HH24:MI') AS SALES_END,"
            + " C.PROCESS_STATUS_NAME AS PROCESS_STATUS_NAME,"
            + " NVL(SUM(D.SALE_MONEY),0) AS TP_MONEY,"
            + " B.PROCESS_STATUS,"
            + " CASE B.PROCESS_STATUS WHEN 30 THEN 0 ELSE B.PROCESS_STATUS END AS TP_ORDER"
            + " FROM term.T_GAME_DRAW_INFO B, term.T_DRAW_PROCESS_STATUS C,"
            + " term.T_GAME_INFO A, term.T_CURRENT_TMN_DRAW_STAT D"
            + " WHERE A.USED_MARK = 1"
            + " AND A.GAME_ID = B.GAME_ID"
            + " AND B.PROCESS_STATUS = C.PROCESS_STATUS"
            + " AND B.PROCESS_STATUS >= 30"
            + " AND B.PROCESS_STATUS <= 430"
            + " AND D.GAME_ID = B.GAME_ID"
            + " AND D.DRAW_ID = B.DRAW_ID"
            + " GROUP BY A.GAME_ID, A.GAME_NAME,"
            + "            B.DRAW_ID, B.DRAW_NAME,"
            + "            B.SALES_END, C.PROCESS_STATUS_NAME, B.PROCESS_STATUS"
            + "    UNION"
            + "        SELECT A.GAME_ID AS GAME_ID,"
            + "            A.GAME_NAME AS GAME_NAME,"
            + "            B.DRAW_ID AS DRAW_ID,"
            + "            B.DRAW_NAME AS DRAW_NAME,"
            + "            to_char(B.SALES_END, 'MM-DD HH24:MI') AS SALES_END,"
            + "            C.PROCESS_STATUS_NAME AS PROCESS_STATUS_NAME,"
            + "            NVL(SUM(D.SALE_MONEY),0) AS TP_MONEY,"
            + "            B.PROCESS_STATUS,"
            + "            CASE B.PROCESS_STATUS WHEN 30 THEN 0 ELSE B.PROCESS_STATUS END AS TP_ORDER"
            + "        FROM term.T_GAME_DRAW_INFO B, term.T_DRAW_PROCESS_STATUS C,"
            + "            term.T_GAME_INFO A, gamb.T_CURRENT_TMN_DRAW_STAT D"
            + "        WHERE A.USED_MARK = 1"
            + "            AND A.GAME_ID = B.GAME_ID"
            + "            AND B.PROCESS_STATUS = C.PROCESS_STATUS"
            + "            AND B.PROCESS_STATUS >= 30"
            + "            AND B.PROCESS_STATUS <= 430"
            + "            AND D.GAME_ID = B.GAME_ID"
            + "            AND D.DRAW_ID = B.DRAW_ID"
            + "        GROUP BY A.GAME_ID, A.GAME_NAME,"
            + "            B.DRAW_ID, B.DRAW_NAME,"
            + "            B.SALES_END, C.PROCESS_STATUS_NAME, B.PROCESS_STATUS"
            + "        ) AA"
            + " GROUP BY AA.GAME_ID, AA.GAME_NAME,"
            + "    AA.DRAW_ID, AA.DRAW_NAME,"
            + "    AA.SALES_END, AA.PROCESS_STATUS_NAME, AA.PROCESS_STATUS, AA.TP_ORDER "
            + "ORDER BY AA.TP_ORDER ,AA.PROCESS_STATUS  DESC, AA.GAME_ID ASC, AA.DRAW_ID ASC";
        private static final String BETS_BY_CITY="SELECT '全省' as city_name, SUM(SALE_MONEY) AS SALE_MONEY, -999 AS CITY_ID,0 AS CITY_ORDER\n" +
            "FROM ( SELECT NVL(SUM(sale_money),0) AS SALE_MONEY \n" +
            "        FROM term.T_current_tmn_draw_stat  \n" +
            "        WHERE game_id = ? \n" +
            "            AND draw_id = ? \n" +
            "    UNION\n" +
            "        SELECT NVL(SUM(sale_money),0) AS SALE_MONEY \n" +
            "        FROM gamb.T_current_tmn_draw_stat \n" +
            "        WHERE game_id = ? \n" +
            "            AND draw_id = ? )\n" +
            "union            \n" +
            "(SELECT AA.city_name AS CITY_NAME, SUM(NVL(BB.SALE_MONEY,0)) AS SALE_MONEY, \n" +
            "    AA.CITY_ID AS CITY_ID,DECODE(AA.CITY_ID,0,888,AA.CITY_ID) AS CITY_ORDER\n" +
            "FROM term.T_city_info AA \n" +
            "    LEFT JOIN \n" +
            "        ( SELECT SUM(A.sale_money) AS SALE_MONEY, B.city_id AS CITY_ID \n" +
            "            FROM term.T_current_tmn_draw_stat A, \n" +
            "                term.T_tmn_info B \n" +
            "            WHERE A.game_id = ? \n" +
            "                AND A.draw_id = ? \n" +
            "                AND A.terminal_id = B.terminal_id \n" +
            "            GROUP BY B.city_id \n" +
            "        UNION\n" +
            "            SELECT SUM(A.sale_money) AS SALE_MONEY, B.city_id AS CITY_ID \n" +
            "            FROM gamb.T_current_tmn_draw_stat A, \n" +
            "                gamb.T_tmn_info B \n" +
            "            WHERE A.game_id = ? \n" +
            "                AND A.draw_id = ? \n" +
            "                AND A.terminal_id = B.terminal_id \n" +
            "            GROUP BY B.city_id \n" +
            "        ) BB \n" +
            "        ON AA.city_id = BB.city_id \n" +
            "GROUP BY AA.city_name, AA.CITY_ID )\n" +
            "ORDER BY CITY_ORDER ASC";
        private static final String TMN_INFO_BY_CITY_GAMEID_DRAWID="SELECT TERMINAL_ID,\n" +
            "  TERMINAL_ADDRESS,\n" +
            "  TERMINAL_TYPE_NAME,\n" +
            "  SUM(SALE_MONEY) AS SALE_MONEY,\n" +
            "  SUM(UNDO_MONEY) AS UNDO_MONEY,\n" +
            "  SUM(CASH_MONEY) AS CASH_MONEY,\n" +
            "  GAME_PERMIT,\n" +
            "  SALE_PERMIT,\n" +
            "  CASH_PERMIT,\n" +
            "  TERMINAL_STATUS\n" +
            "FROM\n" +
            "  (SELECT A.TERMINAL_ID,\n" +
            "    A.TERMINAL_ADDRESS,\n" +
            "    B.TERMINAL_TYPE_NAME,\n" +
            "    SUM(D.SALE_MONEY) AS SALE_MONEY,\n" +
            "    SUM(D.UNDO_MONEY) AS UNDO_MONEY,\n" +
            "    SUM(D.CASH_MONEY) AS CASH_MONEY,\n" +
            "    E.GAME_PERMIT,\n" +
            "    E.SALE_PERMIT,\n" +
            "    E.CASH_PERMIT,\n" +
            "    A.TERMINAL_STATUS\n" +
            "  FROM term.T_TMN_INFO A,\n" +
            "    term.T_TERMINAL_TYPE B,\n" +
            "    term.T_CURRENT_TMN_DRAW_STAT D,\n" +
            "    term.T_TMN_PRIVILEGE E\n" +
            "  WHERE A.TERMINAL_TYPE = B.TERMINAL_TYPE\n" +
            "  AND A.TERMINAL_ID     = D.TERMINAL_ID\n" +
            "  AND D.GAME_ID         = ?\n" +
            "  AND D.DRAW_ID         = ?\n" +
            "  AND D.GAME_ID         = E.GAME_ID\n" +
            "  AND D.TERMINAL_ID     = E.TERMINAL_ID\n" +
            "  AND ((?    = 0\n" +
            "  AND (A.CITY_ID        = ?\n" +
            "  OR -999               = ?)\n" +
            "  AND ( 0     = 0\n" +
            "  OR (0      <> 0\n" +
            "  AND A.CITY_ID         = 0)))\n" +
            "  OR (?     <> 0\n" +
            "  AND (0      = 0\n" +
            "  OR (0      <> 0\n" +
            "  AND A.CITY_ID         = 0))\n" +
            "  AND A.TERMINAL_ID    >= ?))\n" +
            "  GROUP BY A.TERMINAL_ID,\n" +
            "    A.TERMINAL_ADDRESS,\n" +
            "    B.TERMINAL_TYPE_NAME,\n" +
            "    E.GAME_PERMIT,\n" +
            "    E.SALE_PERMIT,\n" +
            "    E.CASH_PERMIT,\n" +
            "    A.TERMINAL_STATUS\n" +
            "  UNION\n" +
            "  SELECT A.TERMINAL_ID,\n" +
            "    A.TERMINAL_ADDRESS,\n" +
            "    B.TERMINAL_TYPE_NAME,\n" +
            "    SUM(D.SALE_MONEY) AS SALE_MONEY,\n" +
            "    SUM(D.UNDO_MONEY) AS UNDO_MONEY,\n" +
            "    SUM(D.CASH_MONEY) AS CASH_MONEY,\n" +
            "    E.GAME_PERMIT,\n" +
            "    E.SALE_PERMIT,\n" +
            "    E.CASH_PERMIT,\n" +
            "    A.TERMINAL_STATUS\n" +
            "  FROM gamb.T_TMN_INFO A,\n" +
            "    gamb.T_TERMINAL_TYPE B,\n" +
            "    gamb.T_CURRENT_TMN_DRAW_STAT D,\n" +
            "    gamb.T_TMN_PRIVILEGE E\n" +
            "  WHERE A.TERMINAL_TYPE = B.TERMINAL_TYPE\n" +
            "  AND A.TERMINAL_ID     = D.TERMINAL_ID\n" +
            "  AND D.GAME_ID         = ?\n" +
            "  AND D.DRAW_ID         = ?\n" +
            "  AND D.GAME_ID         = E.GAME_ID\n" +
            "  AND D.TERMINAL_ID     = E.TERMINAL_ID\n" +
            "  AND ((?    = 0\n" +
            "  AND (A.CITY_ID        = ?\n" +
            "  OR -999               = ?)\n" +
            "  AND (0      = 0\n" +
            "  OR (0      <> 0\n" +
            "  AND A.CITY_ID         = 0)))\n" +
            "  OR (?     <> 0\n" +
            "  AND (0      = 0\n" +
            "  OR (0      <> 0\n" +
            "  AND A.CITY_ID         = 0))\n" +
            "  AND A.TERMINAL_ID    >= ?))\n" +
            "  GROUP BY A.TERMINAL_ID,\n" +
            "    A.TERMINAL_ADDRESS,\n" +
            "    B.TERMINAL_TYPE_NAME,\n" +
            "    E.GAME_PERMIT,\n" +
            "    E.SALE_PERMIT,\n" +
            "    E.CASH_PERMIT,\n" +
            "    A.TERMINAL_STATUS\n" +
            "  )\n" +
            "\n" +
            "GROUP BY TERMINAL_ID,\n" +
            "  TERMINAL_ADDRESS,\n" +
            "  TERMINAL_TYPE_NAME,\n" +
            "  GAME_PERMIT,\n" +
            "  SALE_PERMIT,\n" +
            "  CASH_PERMIT,\n" +
            "  TERMINAL_STATUS\n" +
            "ORDER BY ";
        private static final String TMN_INFO_BY_CITY_GAMEID_DRAWID_COUNT = "select count(1) from ( SELECT TERMINAL_ID,\n" +
            "  TERMINAL_ADDRESS,\n" +
            "  TERMINAL_TYPE_NAME,\n" +
            "  SUM(SALE_MONEY) AS SALE_MONEY,\n" +
            "  SUM(UNDO_MONEY) AS UNDO_MONEY,\n" +
            "  SUM(CASH_MONEY) AS CASH_MONEY,\n" +
            "  GAME_PERMIT,\n" +
            "  SALE_PERMIT,\n" +
            "  CASH_PERMIT,\n" +
            "  TERMINAL_STATUS\n" +
            "  FROM\n" +
            "  (SELECT A.TERMINAL_ID,\n" +
            "    A.TERMINAL_ADDRESS,\n" +
            "    B.TERMINAL_TYPE_NAME,\n" +
            "    SUM(D.SALE_MONEY) AS SALE_MONEY,\n" +
            "    SUM(D.UNDO_MONEY) AS UNDO_MONEY,\n" +
            "    SUM(D.CASH_MONEY) AS CASH_MONEY,\n" +
            "    E.GAME_PERMIT,\n" +
            "    E.SALE_PERMIT,\n" +
            "    E.CASH_PERMIT,\n" +
            "    A.TERMINAL_STATUS\n" +
            "  FROM term.T_TMN_INFO A,\n" +
            "    term.T_TERMINAL_TYPE B,\n" +
            "    term.T_CURRENT_TMN_DRAW_STAT D,\n" +
            "    term.T_TMN_PRIVILEGE E\n" +
            "  WHERE A.TERMINAL_TYPE = B.TERMINAL_TYPE\n" +
            "  AND A.TERMINAL_ID     = D.TERMINAL_ID\n" +
            "  AND D.GAME_ID         = ?\n" +
            "  AND D.DRAW_ID         = ?\n" +
            "  AND D.GAME_ID         = E.GAME_ID\n" +
            "  AND D.TERMINAL_ID     = E.TERMINAL_ID\n" +
            "  AND ((?    = 0\n" +
            "  AND (A.CITY_ID        = ?\n" +
            "  OR -999               = ?)\n" +
            "  AND ( 0     = 0\n" +
            "  OR (0      <> 0\n" +
            "  AND A.CITY_ID         = 0)))\n" +
            "  OR (?     <> 0\n" +
            "  AND (0      = 0\n" +
            "  OR (0      <> 0\n" +
            "  AND A.CITY_ID         = 0))\n" +
            "  AND A.TERMINAL_ID    >= ?))\n" +
            "  GROUP BY A.TERMINAL_ID,\n" +
            "    A.TERMINAL_ADDRESS,\n" +
            "    B.TERMINAL_TYPE_NAME,\n" +
            "    E.GAME_PERMIT,\n" +
            "    E.SALE_PERMIT,\n" +
            "    E.CASH_PERMIT,\n" +
            "    A.TERMINAL_STATUS\n" +
            "  UNION\n" +
            "  SELECT A.TERMINAL_ID,\n" +
            "    A.TERMINAL_ADDRESS,\n" +
            "    B.TERMINAL_TYPE_NAME,\n" +
            "    SUM(D.SALE_MONEY) AS SALE_MONEY,\n" +
            "    SUM(D.UNDO_MONEY) AS UNDO_MONEY,\n" +
            "    SUM(D.CASH_MONEY) AS CASH_MONEY,\n" +
            "    E.GAME_PERMIT,\n" +
            "    E.SALE_PERMIT,\n" +
            "    E.CASH_PERMIT,\n" +
            "    A.TERMINAL_STATUS\n" +
            "  FROM gamb.T_TMN_INFO A,\n" +
            "    gamb.T_TERMINAL_TYPE B,\n" +
            "    gamb.T_CURRENT_TMN_DRAW_STAT D,\n" +
            "    gamb.T_TMN_PRIVILEGE E\n" +
            "  WHERE A.TERMINAL_TYPE = B.TERMINAL_TYPE\n" +
            "  AND A.TERMINAL_ID     = D.TERMINAL_ID\n" +
            "  AND D.GAME_ID         = ?\n" +
            "  AND D.DRAW_ID         = ?\n" +
            "  AND D.GAME_ID         = E.GAME_ID\n" +
            "  AND D.TERMINAL_ID     = E.TERMINAL_ID\n" +
            "  AND ((?    = 0\n" +
            "  AND (A.CITY_ID        = ?\n" +
            "  OR -999               = ?)\n" +
            "  AND (0      = 0\n" +
            "  OR (0      <> 0\n" +
            "  AND A.CITY_ID         = 0)))\n" +
            "  OR (?     <> 0\n" +
            "  AND (0      = 0\n" +
            "  OR (0      <> 0\n" +
            "  AND A.CITY_ID         = 0))\n" +
            "  AND A.TERMINAL_ID    >= ?))\n" +
            "  GROUP BY A.TERMINAL_ID,\n" +
            "    A.TERMINAL_ADDRESS,\n" +
            "    B.TERMINAL_TYPE_NAME,\n" +
            "    E.GAME_PERMIT,\n" +
            "    E.SALE_PERMIT,\n" +
            "    E.CASH_PERMIT,\n" +
            "    A.TERMINAL_STATUS\n" +
            "  )\n" +
            "\n" +
            "GROUP BY TERMINAL_ID,\n" +
            "  TERMINAL_ADDRESS,\n" +
            "  TERMINAL_TYPE_NAME,\n" +
            "  GAME_PERMIT,\n" +
            "  SALE_PERMIT,\n" +
            "  CASH_PERMIT,\n" +
            "  TERMINAL_STATUS\n" +
            "ORDER BY TERMINAL_ID)";
        private static final String QUERY_CUR_DRAWID = "select draw_id from T_GAME_DRAW_INFO where game_id=5 and process_status=30";
    @Override
    public List<HMonitorGame> getMonitorGameData(JdbcTemplate jdbcTemplate) {
        return queryForList(jdbcTemplate,GET_MONITOR_GAME_DATA, new Object[]{},HMonitorGame.class);
    }

    @Override
    public List<HMonitorBetsByCity> getBetsByCity(JdbcTemplate jdbcTemplate, int gameId, int drawId) {
        return queryForList(jdbcTemplate, BETS_BY_CITY, new Object[]{gameId,drawId,gameId,drawId,gameId,drawId,gameId,drawId}, HMonitorBetsByCity.class);
    }

    @Override
    public Pagination<HMonitorTmnInfo> getTmnInfoByCityAndGameIdAndDrawId(JdbcTemplate jdbcTemplate, int gameId, int drawId, int cityId, int terminalId, int sortField, int pageSize, int pageNum) {
        StringBuilder sql = new StringBuilder();
        sql.append(TMN_INFO_BY_CITY_GAMEID_DRAWID);
        if(sortField == 0){
            sql.append(" TERMINAL_ID ASC");//
        }else{
            sql.append(" SALE_MONEY DESC");//
        }
        StringBuilder countSql = new StringBuilder();
        countSql.append(TMN_INFO_BY_CITY_GAMEID_DRAWID_COUNT);
        Pagination<HMonitorTmnInfo> page = queryForPage(
                jdbcTemplate, 
                sql.toString(), 
                countSql.toString(), 
                pageNum, 
                pageSize, 
                new Object[]{gameId,drawId,terminalId,cityId,cityId,terminalId,terminalId,gameId,drawId,terminalId,cityId,cityId,terminalId,terminalId}, 
                HMonitorTmnInfo.class
        );
        return page;
    }
    /***
     * 获取双色球的当前期
     * @param jdbcTemplate
     * @return 
     */
    @Override
    public int getCurDraw(JdbcTemplate jdbcTemplate) {
        return queryForInteger(jdbcTemplate, QUERY_CUR_DRAWID, null);
    }

}
