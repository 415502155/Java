//package com.bestinfo.dao.sqlmap;
//
///**
// * 结算统计表-站点期统计(T_stat_tmn_draw)
// *
// * @author yangyuefu
// */
//public class StatTmnDrawSql {
//
//    /**
//     * 插入StatTmnDraw
//     */
//    public static String INSERT_STATTMNDRAW = "INSERT INTO t_stat_tmn_draw VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//
//    /**
//     * 根据游戏、期次、终端获取StatTmnDraw记录
//     */
//    public static String GET_STAT_BY_GAME_DRAW_TERMINAL = "SELECT * FROM T_STAT_TMN_DRAW "
//            + " WHERE  game_id=? and draw_id=? AND terminal_id=?";
//
//    /**
//     * 根据游戏、期、地市统计数据
//     */
//    public static String SUM_BY_GAME_DRAW_CITY = "SELECT GAME_ID,DRAW_ID,CITY_ID,"
//            + " sum(SALE_MONEY) SALE_MONEY,sum(SALE_TICKET_NUM) SALE_TICKET_NUM,sum(SALE_STAKE_NUM) SALE_STAKE_NUM,"
//            + " sum(UNDO_MONEY) UNDO_MONEY,sum(UNDO_TICKET_NUM) UNDO_TICKET_NUM,sum(UNDO_STAKE_NUM) UNDO_STAKE_NUM,"
//            + " sum(CASH_MONEY) CASH_MONEY,sum(CASH_TICKET_NUM) CASH_TICKET_NUM,sum(CASH_STAKE_NUM) CASH_STAKE_NUM,"
//            + " sum(AGENT_FEE_DEDUCT) AGENT_FEE_DEDUCT,sum(agent_fee) agent_fee,sum(CASH_FEE) CASH_FEE"
//            + " FROM t_stat_tmn_draw WHERE game_id=? AND draw_id=? GROUP BY game_id,draw_id,city_id";
//}
