//package com.bestinfo.dao.sqlmap;
//
///**
// * 开奖公告
// *
// * @author lvchangrong
// */
//public class OpenPrizeInfoSql {
//
//    public static String INSERT_OPENPRIZEINFO = "insert into t_openprize_info values(?,?,?,?,?)";
//
//    public static String SELECT_OPENPRIZEINFO = "select  game_id, draw_id , keno_draw_id,  openprize_info, openprize_md5  "
//            + " from t_openprize_info where game_id = ? and draw_id = ? and keno_draw_id = ? ";
//
//    public static String UPDATE_OPENPRIZEINFO = "update  t_openprize_info set openprize_info = ? , openprize_md5= ?  "
//            + " where game_id = ? and draw_id = ? and keno_draw_id = ? ";
//
//    public static String DELETE_OPENPRIZE = "DELETE FROM t_openprize_info WHERE game_id=? AND draw_id=? AND keno_draw_id=?";
//}
