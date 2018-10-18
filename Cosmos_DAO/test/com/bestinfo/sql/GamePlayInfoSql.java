package com.bestinfo.sql;

//package com.bestinfo.dao.sqlmap;
//
///**
// * 游戏玩法
// *
// * @author yangyuefu
// */
//public class GamePlayInfoSql {
//
//    /**
//     * 根据游戏id获取游戏玩法
//     */
//    public static String GET_GAMEPLAYINFO_BY_GAME_ID = "SELECT * FROM t_game_play_info WHERE game_id = ?";
//
//    /**
//     * 根据玩法id获取游戏玩法
//     */
//    public static String GET_GAMEPLAYINFO_BY_PLAY_ID = "SELECT * FROM t_game_play_info WHERE play_id = ?";
//
//    /**
//     * 根据游戏id和玩法id获取玩法记录
//     */
//    public static String GET_GAMEPLAYINFO_BY_GAME_ID_AND_PLAY_ID = "SELECT * FROM t_game_play_info WHERE game_id = ? AND play_id = ?";
//
//    /**
//     * 查询玩法记录列表
//     */
//    public static String GET_GAMEPLAYINFO_LIST = "SELECT game_id,play_id,play_name,play_type,stakes_price,max_multiple,bet_no_num,bet_min_no,bet_max_no,blue_no_num,"
//            + "blue_min_no,blue_max_no,no_repeat,no_order,sign_num,prize_proportion,work_status from t_game_play_info";
//    
//    /**
//     * 新增游戏玩法
//     */
//    public static String INSERT_GAMEPLAYINFO = "INSERT INTO t_game_play_info VALUES("
//            + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
//            + ")";
//
//    /**
//     * 根据游戏id更新游戏玩法
//     */
//    public static String UPDATE_GAMEPLAYINFO = "UPDATE t_game_play_info SET "
//            + "play_name=?,play_type=?,stakes_price=?,max_multiple=?,bet_no_num=?,bet_min_no=?,"
//            + "bet_max_no=?,blue_no_num=?,blue_min_no=?,blue_max_no=?,no_repeat=?,no_order=?,"
//            + "sign_num=?,prize_proportion=?,work_status=? "
//            + "WHERE game_id=? AND play_id=?";
//}
