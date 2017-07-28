//package com.bestinfo.dao.sqlmap;
//
///**
// * 普通游戏中奖汇总
// *
// * @author chenliping
// */
//public class StatPrizeAnnSql {
//
//    public static String INSERT_STATPRIZE = "INSERT INTO T_STAT_PRIZE_ANN(GAME_ID,DRAW_ID,PLAY_ID,OPEN_ID,CLASS_ID,"
//            + "CLASS_NAME,PRIZE_NUM,STAKE_PRIZE,TOTAL_NUM) VALUES(?,?,?,?,?,?,?,?,?)";
//
//    public static String SELECT_STATPRIZE = "SELECT GAME_ID,DRAW_ID,PLAY_ID,OPEN_ID,CLASS_ID,class_name,PRIZE_NUM,STAKE_PRIZE,TOTAL_NUM "
//            + "FROM T_STAT_PRIZE_ANN WHERE GAME_ID=? and DRAW_ID=? and OPEN_ID=?";
//
//    public static String UPDATE_STATPRIZEANN = "UPDATE t_stat_prize_ann SET stake_prize=?,total_num=? "
//            + " WHERE game_id=? and draw_id=? and open_id=? and class_id=?";
//
//    public static String SELECT_STATPRIZEANN_ByIds = " SELECT class_id , "
//            + "class_name , "
//            + "prize_num , "
//            + "stake_prize , "
//            + "total_num "
//            + "FROM T_STAT_PRIZE_ANN where "
//            + "game_id = ? and "
//            + "draw_id = ? order by class_id";
//}
