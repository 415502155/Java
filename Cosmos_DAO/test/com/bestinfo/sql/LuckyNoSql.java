package com.bestinfo.sql;

//package com.bestinfo.dao.sqlmap;
//
///**
// * 开奖号码
// *
// * @author chenliping
// */
//public class LuckyNoSql {
//
//    /**
//     * 获取开奖号码
//     */
//    public static String GET_LUCKNO = "SELECT game_id,draw_id,draw_name,keno_draw_id,keno_draw_name,open_id,"
//            + " lucky_no,lucky_no_echo,prize_no_num,prize_no,special_no_num,special_no"
//            + " FROM t_lucky_no WHERE game_id=? AND draw_id=? AND keno_draw_id=? AND open_id=?";
//    /**
//     * 插入开奖号码
//     */
//    public static String INSERT_LUCKYNO = "INSERT INTO T_LUCKY_NO(game_id,draw_id,draw_name,keno_draw_id,keno_draw_name,open_id,"
//            + " lucky_no,lucky_no_echo,prize_no_num,prize_no,special_no_num,special_no) "
//            + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
//
//    public static String GET_LUCKYNO_LIST = "SELECT * FROM T_LUCKY_NO  WHERE GAME_ID = ? AND DRAW_ID = ? and keno_draw_id = ? ";
//}
