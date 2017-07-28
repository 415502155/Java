//package com.bestinfo.dao.sqlmap;
//
///**
// * 投注机彩票投注流水号
// *
// * @author chenliping
// */
//public class TmnSerialNoSql {
//
//    /**
//     * 根据game_id和draw_id获取投注机流水号总和
//     */
//    public static String getSumSerialNo = "select sum(SERIAL_NO) from T_TMN_SERIAL_NO where game_id=? and draw_id=?";
//
//    /**
//     * 根据game_id和draw_id获取投注机流水号记录
//     */
//    public static String getSerialNoByGameIdAndDrawId = "select * from t_tmn_serial_no where game_id = ? and draw_id = ?";
//    
//     /**
//     * 根据game_id、draw_id、terminal_id获取投注机流水号记录
//     */
//    public static String getTmnSerialNoByPrimary = "select * from t_tmn_serial_no where game_id = ? and draw_id = ? and terminal_id = ?";
//    
//     /**
//     * 插入投注机流水号记录
//     */
//    public static String insertTmnSerialNo = "INSERT INTO T_TMN_SERIAL_NO(TERMINAL_ID,GAME_ID,DRAW_ID,SERIAL_NO,PARTION_ID) VALUES(?,?,?,?,?)";
//    
//}
