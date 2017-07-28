package com.bestinfo.sql;

//package com.bestinfo.dao.sqlmap;
//
///**
// *
// * @author yangyuefu
// */
//public class GameInfoSql {
//
//    /**
//     * 根据游戏id获取游戏sql
//     */
//    public static String GET_GAMEINFO_BY_GAME_ID ="SELECT * "
//            + " FROM t_game_info WHERE game_id=?";
//    
//    /**
//     * 查询游戏信息列表sql 缓存同步使用
//     */
//    public static String GET_GAMEINFO_LIST ="SELECT * FROM t_game_info order by game_id ASC";
//    
//    /**
//     * 新增游戏sql
//     */
//    public static String INSERT_GAMEINFO = "INSERT INTO t_game_info VALUES("
//            + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
//            + ")";
//    
//    /**
//     * 根据游戏id更新游戏sql
//     */
//    public static String UPDATE_GAMEINFO = "UPDATE t_game_info SET "
//            + "game_type=?,game_name=?,short_name=?,game_code=?,play_num=?,repeat_select=?,open_min_no=?,open_max_no=?,"
//            + "open_min_blue_no=?,open_max_blue_no=?,open_basic_num=?,open_special_num=?,open_blue_num=?,lucky_no_group=?,"
//            + "open_num=?,prize_class_number=?,fix_prize_class_number=?,center_max_cash_class=?,center_max_cash_money=?,"
//            + "department_max_cash_class=?,department_max_cash_money=?,terminal_max_cash_class=?,terminal_max_cash_money=?,"
//            + "cur_draw_id=?,draw_period_type=?,draw_period=?,draw_time=?,cash_period_day=?,bet_line_way=?,single_stake_num=?,multi_draw_number=?,union_type=?,"
//            + "used_mark=?,undo_permit=?,sale_mark=?,cash_mark=?,data_save_day=?,game_version=?,terminal_bet_money=?,game_control_type=?,"
//            + "control_group_num=?,bind_game_id=?,cash_method=?,prize_precision=?,init_time=?,stat_time=?,begin_time=?,end_time=?,"
//            + "keno_game=?,keno_draw_num=?,draw_length=?,multi_keno_num=?,next_draw_time=?,bulletin_time=?,re_bulletin_time=?,"
//            + "calc_method=?,jackpot_method=?,openprize_method=?,prep_draw_num=?,open_configure_id=?"
//            + " WHERE game_id=?";
//    
//    /**
//     * 根据游戏Id获取keno期数sql
//     */
//    public static String GET_KENO_DRAW_NUM_BYID ="SELECT KENO_DRAW_NUM FROM T_GAME_INFO where GAME_ID = ?";
//
//    /**
//     * EB修改游戏规则sql
//     */
//    public static String EB_MODIFY_GAME_INFO = "update T_GAME_INFO set terminal_max_cash_money = ?,draw_period=?,draw_time=?,cash_period_day=?,multi_draw_number=?,data_save_day=?,terminal_bet_money=?,prep_draw_num=?,luckyno_time=? where GAME_ID = ?";
//}
