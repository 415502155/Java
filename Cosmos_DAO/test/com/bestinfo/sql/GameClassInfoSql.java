package com.bestinfo.sql;

//package com.bestinfo.dao.sqlmap;
//
///**
// *
// * @author hhhh,YangRong
// */
//public class GameClassInfoSql {
//
//    /**
//     * 查询奖级列表数据sql
//     */
//    public static String GET_GAMECLASSINFO_List = "SELECT * FROM t_game_class_info";
//    /**
//     * 根据gameId查询奖级列表数据sql
//     */
//    public static String GET_GAMECLASSINFO_LIST_BY_GAMEID = "SELECT * FROM t_game_class_info "
//            + "WHERE game_id=? ORDER BY play_id,class_id ";
//
//    /**
//     * 根据gameId,playId查询奖级列表数据sql
//     */
//    public static String GET_GAMECLASSINFO_LIST_BY_GAMEPLAYID = "SELECT * FROM t_game_class_info "
//            + " WHERE game_id = ? AND play_id = ? ORDER BY class_id";
//
//    /**
//     * 根据游戏id、玩法id、奖级id更新奖级sql
//     */
//    public static String UPDATE_GAMECLASSINFO = "UPDATE t_game_class_info SET "
//            + "CLASS_NAME = ?,FIX_MARK = ?,PRIZE_PROPORTION = ?,LAST_RELATION = ?,"
//            + "LAST_DIFF = ?,CLASS_STATUS = ?,TOP_MONEY = ?,OPEN_ID = ?"
//            + " WHERE game_id = ? AND play_id = ? AND class_id = ?";
//
//    /**
//     * 根据游戏id、玩法id、奖级id删除奖级sql
//     */
//    public static String DELETE_GAMECLASSINFO = "DELETE FROM t_game_class_info "
//            + " WHERE game_id = ? AND play_id = ? AND class_id = ?";
//
//    /**
//     * 新增游戏sql
//     */
//    public static String INSERT_GAMECLASSINFO = "INSERT INTO t_game_class_info VALUES("
//            + "?,?,?,?,?,?,?,?,?,?,?)";
//
//    /**
//     * 根据游戏id,玩法id,奖级id获取游戏奖级sql
//     */
//    public static String GET_GAMECLASSINFO_BY_GAMEPLAYCLASS_ID = "SELECT * FROM t_game_class_info "
//            + "WHERE game_id=?  AND play_id=? AND class_id=?";
//
//    /**
//     * 根据游戏id,玩法id获取游戏奖级
//     */
//    public static String GET_CLASS_BY_GAME_CLASS = "SELECT * FROM t_game_class_info WHERE game_id = ? AND class_id = ?";
//
//}
