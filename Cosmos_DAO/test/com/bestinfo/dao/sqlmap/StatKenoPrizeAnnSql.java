//package com.bestinfo.dao.sqlmap;
//
///**
// * 结算统计表-快开游戏中奖汇总(T_stat_keno_prize_ann)
// *
// * @author chenliping
// */
//public class StatKenoPrizeAnnSql {
//
//    public static String INSERT_STATPRIZE = "INSERT INTO T_STAT_KENO_PRIZE_ANN(GAME_ID,DRAW_ID,KENO_DRAW_ID,PLAY_ID,CLASS_ID,"
//            + "CLASS_NAME,PRIZE_NUM,STAKE_PRIZE,TOTAL_NUM) VALUES(?,?,?,?,?,?,?,?,?)";
//
//    public static String SELECT_STATPRIZE = "SELECT GAME_ID,DRAW_ID,KENO_DRAW_ID,PLAY_ID,CLASS_ID,CLASS_NAME,PRIZE_NUM,STAKE_PRIZE,TOTAL_NUM "
//            + "FROM T_STAT_KENO_PRIZE_ANN WHERE GAME_ID=? and DRAW_ID=? and KENO_DRAW_ID=? ORDER BY CLASS_ID";
//
//    public static String UPDATE_STATPRIZEANN = "UPDATE T_STAT_KENO_PRIZE_ANN SET STAKE_PRIZE=? "
//            + "where GAME_ID=? and DRAW_ID=? and KENO_DRAW_ID=? and CLASS_ID=?";
//
//    /**
//     * 根据game_id、draw_id统计总中奖额
//     */
//    public static String SUM_PRIZE_BY_GAME_DRAW = "SELECT game_id, draw_id, SUM(prize_num * stake_prize) total_prize"
//            + "  FROM t_stat_keno_prize_ann"
//            + " WHERE game_id = ? AND draw_id = ?"
//            + " GROUP BY game_id, draw_id";
//
//    public static String SUM_PRIZEBY_GAME_DRAW_KENODRAW = " select sum(prize_num * stake_prize) KenoTotalMoney "
//            + "from T_STAT_KENO_PRIZE_ANN "
//            + "where game_id = ? "
//            + " and DRAW_ID=? "
//            + "and KENO_DRAW_ID=?";
//
//    /**
//     * 删除某一keno期的中奖数据
//     */
//    public static String DELETE_PRIZE_ANN = "DELETE FROM t_stat_keno_prize_ann WHERE game_id=? AND draw_id=? AND keno_draw_id=?";
//}
