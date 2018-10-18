//package com.bestinfo.dao.sqlmap;
//
///**
// * 实时统计-快开期玩法统计表(T_current_kdraw_play_stat)
// *
// * @author chenliping
// */
//public class CurrentKDrawPlayStatSql {
//
//    /**
//     * 统计每个小期的销售数据
//     */
//    public static String SUM_BY_GAME_DRAW_KDRAW = "SELECT GAME_ID,DRAW_ID,KENO_DRAW_ID,"
//            + " sum(SALE_MONEY) SALE_MONEY,sum(SALE_TICKET_NUM) SALE_TICKET_NUM,sum(SALE_STAKE_NUM) SALE_STAKE_NUM,"
//            + " sum(UNDO_MONEY) UNDO_MONEY,sum(UNDO_TICKET_NUM) UNDO_TICKET_NUM,sum(UNDO_STAKE_NUM) UNDO_STAKE_NUM"
//            + " FROM t_current_kdraw_play_stat WHERE game_id=? AND draw_id=? AND keno_draw_id=? "
//            + " GROUP BY game_id,draw_id,keno_draw_id";
//
//    /**
//     * 统计每个小期每个玩法的销售数据,列表
//     */
//    public static String SUM_BY_GAME_DRAW_KDRAW_PLAY = "SELECT GAME_ID,DRAW_ID,KENO_DRAW_ID,play_id,"
//            + " sum(SALE_MONEY) SALE_MONEY,sum(SALE_TICKET_NUM) SALE_TICKET_NUM,sum(SALE_STAKE_NUM) SALE_STAKE_NUM,"
//            + " sum(UNDO_MONEY) UNDO_MONEY,sum(UNDO_TICKET_NUM) UNDO_TICKET_NUM,sum(UNDO_STAKE_NUM) UNDO_STAKE_NUM"
//            + " FROM t_current_kdraw_play_stat WHERE game_id=? AND draw_id=? AND keno_draw_id=? "
//            + " GROUP BY game_id,draw_id,keno_draw_id,play_id";
//    /**
//     * 新增销售信息
//     */
//    public static String INIT_CURRENTDRAWSTAT = "INSERT INTO t_current_kdraw_play_stat("
//            + " GAME_ID,DRAW_ID,KENO_DRAW_ID,PLAY_ID,SALE_MONEY,"
//            + " SALE_TICKET_NUM,SALE_STAKE_NUM,UNDO_MONEY,UNDO_TICKET_NUM,UNDO_STAKE_NUM) "
//            + " VALUES(?,?,?,?,?,?,?,?,?,?)";
//
//}
