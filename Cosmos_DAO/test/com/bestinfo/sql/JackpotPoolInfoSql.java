package com.bestinfo.sql;

//package com.bestinfo.dao.sqlmap;
//
///**
// * 结算统计表-游戏奖池调节基金(T_jackpot_pool_info)
// *
// * @author yangyuefu
// */
//public class JackpotPoolInfoSql {
//
//    /**
//     * 获取奖池
//     */
//    public static String GET_JACKPOT = "SELECT game_id,play_id,draw_id,keno_draw_id,sales_money,begin_jackpot,"
//            + " return_jackpot,append_jackpot,get_jackpot,prize_ticket_money,not_give_money,forget_money_jackpot,end_jackpot,"
//            + " begin_pool,return_pool,append_pool,get_pool,forget_moeny_pool,round_money,fill_prize,end_pool,note"
//            + " FROM t_stat_jackpot_info"
//            + " WHERE game_id=? AND play_id=? AND draw_id=? AND keno_draw_id=?";
//
//    /**
//     * 插入奖池
//     */
//    public static String INSERT_JACKPOT = "INSERT INTO t_stat_jackpot_info(game_id,play_id,draw_id,keno_draw_id,sales_money,begin_jackpot,"
//            + " return_jackpot,append_jackpot,get_jackpot,prize_ticket_money,not_give_money,forget_money_jackpot,end_jackpot,"
//            + " begin_pool,return_pool,append_pool,get_pool,forget_moeny_pool,round_money,fill_prize,end_pool,note) "
//            + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//    /**
//     * 更新奖池
//     */
//    public static String UPDATE_JACKPOT = "UPDATE t_stat_jackpot_info SET sales_money=?,begin_jackpot=?,"
//            + " return_jackpot=?,append_jackpot=?,get_jackpot=?,prize_ticket_money=?,not_give_money=?,forget_money_jackpot=?,end_jackpot=?,"
//            + " begin_pool=?,return_pool=?,append_pool=?,get_pool=?,forget_moeny_pool=?,round_money=?,fill_prize=?,end_pool=?,note=?"
//            + " WHERE game_id=? And play_id=? AND draw_id=? AND keno_draw_id=?";
//
//    /**
//     * 获取当前期之前的奖池列表t_stat_jackpot_info
//     */
//    public static String GET_FRONT_JACKPOT_LIST = "SELECT *"
//            + "  FROM t_stat_jackpot_info"
//            + " WHERE game_id = ? AND draw_id < ?"
//            + " ORDER BY draw_id DESC";
//    
//    /**
//     * 获取当前期的总投注额
//     */
//    public static String GET_JACKPOTPOOL_ById = " select "
//                                                      + "sales_money , end_pool"
//                                                      + " from t_stat_jackpot_info where "
//                                                      + "game_id = ? "
//                                                      + "and draw_id = ? "
//                                                      + "and keno_draw_id = ? ";
//}
