/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.gambler.bet;


//import cn.com.bestinfo.core.entity.TGameInfo;
//import cn.com.bestinfo.core.entity.TGamePlayInfo;
//import cn.com.bestinfo.core.entity.TPlayBetModeGamb;
import com.bestinfo.bean.game.GameInfo;
import com.bestinfo.bean.game.GamePlayInfo;
import com.bestinfo.bean.game.GamePlayInfoGamb;
import com.bestinfo.bean.game.PlayBetModeGamb;
import com.bestinfo.gambler.all.BaseJDBCDao;
import com.bestinfo.gambler.all.OracleJDBCDao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 * 游戏规则查询相关
 * @author chenliping
 */
public class GameInfoGC {
    
    private static Logger logger = Logger.getLogger(GameInfoGC.class);
    
    public static ArrayList<GameInfo> getAllGameInfo() throws Exception {
        ArrayList<GameInfo> atg = new ArrayList<GameInfo>();
        BaseJDBCDao bCDao = new BaseJDBCDao("derby");
        Connection conn = bCDao.getConnection(true, "virtual");
        Statement s = conn.createStatement();
        String sql = "select * from T_GAME_INFO";
        logger.info("获取游戏规则");
        ResultSet rs = s.executeQuery(sql);
        while (rs.next()) {
            GameInfo tpi = new GameInfo();
            tpi.setGame_id(rs.getInt("game_id"));
            tpi.setGame_type(rs.getString("game_type").trim());
            tpi.setGame_name(rs.getString("game_name").trim());
            tpi.setShort_name(rs.getString("short_name").trim());
            tpi.setGame_code(rs.getString("game_code").trim());
            tpi.setPlay_num(rs.getInt("play_num"));
            tpi.setOpen_min_no(rs.getInt("bet_min_no"));
            tpi.setOpen_max_no(rs.getInt("bet_max_no"));
            tpi.setOpen_min_blue_no(rs.getInt("blue_min_no"));
            tpi.setOpen_max_blue_no(rs.getInt("blue_max_no"));
            tpi.setRepeat_select(rs.getInt("repeat_select"));
            tpi.setOpen_basic_num(rs.getInt("open_basic_num"));
            tpi.setOpen_special_num(rs.getInt("open_special_num"));
            tpi.setOpen_blue_num(rs.getInt("open_blue_num"));
            tpi.setPrize_class_number(rs.getInt("prize_class_number"));
            tpi.setFix_prize_class_number(rs.getInt("fix_prize_class_number"));
            tpi.setCenter_max_cash_class(rs.getInt("center_max_cash_class"));
            tpi.setCenter_max_cash_money(rs.getBigDecimal("center_max_cash_money"));
            tpi.setDepartment_max_cash_class(rs.getInt("department_max_cash_class"));
            tpi.setDepartment_max_cash_money(rs.getBigDecimal("department_max_cash_money"));
            tpi.setTerminal_max_cash_class(rs.getInt("terminal_max_cash_class"));
            tpi.setTerminal_max_cash_money(rs.getBigDecimal("terminal_max_cash_money"));
            tpi.setCur_draw_id(rs.getInt("cur_draw_id"));
            tpi.setDraw_period_type(rs.getInt("draw_period_type"));
            tpi.setDraw_period(rs.getString("draw_period"));
            tpi.setDraw_time(rs.getString("draw_time"));
            tpi.setCash_period_day(rs.getInt("cash_period_day"));
            tpi.setMulti_draw_number(rs.getInt("multi_draw_number"));
            tpi.setUnion_type(rs.getInt("union_type"));
            tpi.setUsed_mark(rs.getInt("used_mark"));
            tpi.setUndo_permit(rs.getInt("undo_permit"));
            tpi.setSale_mark(rs.getInt("sale_mark"));
            tpi.setCash_mark(rs.getInt("cash_mark"));
            tpi.setData_save_day(rs.getInt("data_save_day"));
            tpi.setGame_version(rs.getString("game_version"));
            tpi.setTerminal_bet_money(rs.getBigDecimal("terminal_bet_money"));
            tpi.setGame_control_type(rs.getInt("game_control_type"));
            tpi.setBind_game_id(rs.getInt("bind_game_id"));
            tpi.setCash_method(rs.getInt("cash_method"));
            tpi.setPrize_precision(rs.getInt("prize_precision"));
            tpi.setBegin_time(rs.getString("begin_time"));
            tpi.setEnd_time(rs.getString("end_time"));
            tpi.setKeno_game(rs.getInt("keno_game"));
            tpi.setKeno_draw_num(rs.getInt("keno_draw_num"));
            tpi.setDraw_length(rs.getInt("draw_length"));
            tpi.setMulti_keno_num(rs.getInt("multi_keno_num"));
            tpi.setNext_draw_time(rs.getInt("next_draw_time"));
            tpi.setBulletin_time(rs.getInt("bulletin_time"));
            tpi.setRe_bulletin_time(rs.getInt("re_bulletin_time"));
            atg.add(tpi);
        }
        rs.close();
        s.close();
        conn.close();
        return atg;
    }

    public static GameInfo getAllGameInfo(int gameid) throws Exception {
        GameInfo tpi = null;
//        BaseJDBCDao bCDao = new BaseJDBCDao("derby");
//        Connection conn = bCDao.getConnection(true, "virtual");
        
        OracleJDBCDao ojdbc = new OracleJDBCDao();
        Connection conn = ojdbc.createConnection();
        Statement s = conn.createStatement();
        String sql = "select * from T_GAME_INFO where game_id=" + gameid;
        logger.info("获取游戏规则" );
        ResultSet rs = s.executeQuery(sql);
        while (rs.next()) {
            tpi = new GameInfo();
            tpi.setGame_id(rs.getInt("game_id"));
            tpi.setGame_type(rs.getString("game_type").trim());
            tpi.setGame_name(rs.getString("game_name").trim());
            tpi.setShort_name(rs.getString("short_name").trim());
            tpi.setGame_code(rs.getString("game_code").trim());
            tpi.setPlay_num(rs.getInt("play_num"));
            tpi.setOpen_min_no(0);
            tpi.setOpen_max_no(36);
            tpi.setOpen_min_blue_no(0);
            tpi.setOpen_max_blue_no(36);
            tpi.setRepeat_select(rs.getInt("repeat_select"));
            tpi.setOpen_basic_num(rs.getInt("open_basic_num"));
            tpi.setOpen_special_num(rs.getInt("open_special_num"));
            tpi.setOpen_blue_num(rs.getInt("open_blue_num"));
            tpi.setPrize_class_number(rs.getInt("prize_class_number"));
            tpi.setFix_prize_class_number(rs.getInt("fix_prize_class_number"));
            tpi.setCenter_max_cash_class(rs.getInt("center_max_cash_class"));
            tpi.setCenter_max_cash_money(rs.getBigDecimal("center_max_cash_money"));
            tpi.setDepartment_max_cash_class(rs.getInt("department_max_cash_class"));
            tpi.setDepartment_max_cash_money(rs.getBigDecimal("department_max_cash_money"));
            tpi.setTerminal_max_cash_class(rs.getInt("terminal_max_cash_class"));
            tpi.setTerminal_max_cash_money(rs.getBigDecimal("terminal_max_cash_money"));
            tpi.setCur_draw_id(rs.getInt("cur_draw_id"));
            tpi.setDraw_period_type(rs.getInt("draw_period_type"));
            tpi.setDraw_period(rs.getString("draw_period"));
            tpi.setDraw_time(rs.getString("draw_time"));
            tpi.setCash_period_day(rs.getInt("cash_period_day"));
            tpi.setMulti_draw_number(rs.getInt("multi_draw_number"));
            tpi.setUnion_type(rs.getInt("union_type"));
            tpi.setUsed_mark(rs.getInt("used_mark"));
            tpi.setUndo_permit(rs.getInt("undo_permit"));
            tpi.setSale_mark(rs.getInt("sale_mark"));
            tpi.setCash_mark(rs.getInt("cash_mark"));
            tpi.setData_save_day(rs.getInt("data_save_day"));
            tpi.setGame_version(rs.getString("game_version"));
            tpi.setTerminal_bet_money(rs.getBigDecimal("terminal_bet_money"));
            tpi.setGame_control_type(rs.getInt("game_control_type"));
            tpi.setBind_game_id(rs.getInt("bind_game_id"));
            tpi.setCash_method(rs.getInt("cash_method"));
            tpi.setPrize_precision(rs.getInt("prize_precision"));
            tpi.setBegin_time(rs.getString("begin_time"));
            tpi.setEnd_time(rs.getString("end_time"));
            tpi.setKeno_game(rs.getInt("keno_game"));
            tpi.setKeno_draw_num(rs.getInt("keno_draw_num"));
            tpi.setDraw_length(rs.getInt("draw_length"));
            tpi.setMulti_keno_num(rs.getInt("multi_keno_num"));
            tpi.setNext_draw_time(rs.getInt("next_draw_time"));
            tpi.setBulletin_time(rs.getInt("bulletin_time"));
            tpi.setRe_bulletin_time(rs.getInt("re_bulletin_time"));
        }
        rs.close();
        s.close();
        conn.close();
        return tpi;
    }

    /**
     * 从玩法投注方式表中获取游戏规则
     *
     * @param gameid 游戏编号
     * @param playid 玩法编号
     * @return 规则对象
     * @throws Exception
     */
    public static ArrayList<PlayBetModeGamb> getPlayBetModeInfo(int gameid, int playid, int betmode) throws Exception {
        ArrayList<PlayBetModeGamb> atp = new ArrayList<PlayBetModeGamb>();
//        BaseJDBCDao bCDao = new BaseJDBCDao("derby");
//        Connection conn = bCDao.getConnection(true, "virtual");
        OracleJDBCDao ojdbc = new OracleJDBCDao();
        Connection conn = ojdbc.createConnection();
        Statement s = conn.createStatement();
        String sql = "select * from T_PLAY_BET_MODE_GAMB where game_id=" + gameid + " and play_id=" + playid + " and bet_mode=" + betmode;
//        logger.info(sql);
        ResultSet rs = s.executeQuery(sql);
        while (rs.next()) {
            PlayBetModeGamb tpi = new PlayBetModeGamb();
            tpi.setGame_id(rs.getInt("game_id"));
            tpi.setPlay_id(rs.getInt("play_id"));
            tpi.setBet_mode(rs.getInt("bet_mode"));
            tpi.setWork_status(rs.getInt("work_status"));
            atp.add(tpi);
        }
        rs.close();
        s.close();
        conn.close();
        return atp;
    }

    /**
     * 从玩法投注方式表中获取游戏规则
     *
     * @param gameid 游戏编号
     * @param playid 玩法编号
     * @return 规则对象
     * @throws Exception
     */
    public static  ArrayList<PlayBetModeGamb> getPlayBetModeInfo(int gameid, int playid) throws Exception {
        ArrayList<PlayBetModeGamb> atp = new ArrayList<PlayBetModeGamb>();
//        BaseJDBCDao bCDao = new BaseJDBCDao("derby");
//        Connection conn = bCDao.getConnection(true, "virtual");
        OracleJDBCDao ojdbc = new OracleJDBCDao();
        Connection conn = ojdbc.createConnection();
        Statement s = conn.createStatement();
        String sql = "select * from T_PLAY_BET_MODE_Gamb where game_id=" + gameid + " and play_id=" + playid;
//        logger.info(sql);
        ResultSet rs = s.executeQuery(sql);
        while (rs.next()) {
            PlayBetModeGamb tpi = new PlayBetModeGamb();
            tpi.setGame_id(rs.getInt("game_id"));
            tpi.setPlay_id(rs.getInt("play_id"));
            tpi.setBet_mode(rs.getInt("bet_mode"));
            tpi.setWork_status(rs.getInt("work_status"));
            atp.add(tpi);
        }
        rs.close();
        s.close();
        conn.close();
        return atp;
    }

    /**
     * 从游戏玩法表中获取游戏规则
     *
     * @param gameid 游戏编号
     * @param playid 玩法编号
     * @return 规则对象
     * @throws Exception
     */
    public static ArrayList<GamePlayInfoGamb> getGamePlayInfo(int gameid) throws Exception {
        ArrayList<GamePlayInfoGamb> atpi = new ArrayList<GamePlayInfoGamb>();
        BaseJDBCDao bCDao = new BaseJDBCDao("derby");
        Connection conn = bCDao.getConnection(true, "virtual");
        Statement s = conn.createStatement();
        String sql = "select * from TEST.T_GAME_PLAY_INFO_Gamb where game_id=" + gameid;
//        logger.info(sql);
        ResultSet rs = s.executeQuery(sql);
        while (rs.next()) {
            GamePlayInfoGamb tpi = new GamePlayInfoGamb();
            tpi.setGame_id(rs.getInt("game_id"));
            tpi.setPlay_id(rs.getInt("play_id"));
            tpi.setPlay_name(rs.getString("play_name"));
            tpi.setStakes_price(rs.getBigDecimal("stakes_price"));
            tpi.setMax_multiple(rs.getInt("max_multiple"));
            tpi.setBet_no_num(rs.getInt("bet_no_num"));
            tpi.setBet_min_no(rs.getInt("bet_min_no"));
            tpi.setBet_max_no(rs.getInt("bet_max_no"));
            tpi.setBlue_no_num(rs.getInt("blue_no_num"));
            tpi.setBlue_min_no(rs.getInt("blue_min_no"));
            tpi.setBlue_max_no(rs.getInt("blue_max_no"));
            tpi.setNo_order(rs.getInt("no_order"));
            tpi.setWork_status(rs.getInt("work_status"));
            atpi.add(tpi);
        }
        rs.close();
        s.close();
        conn.close();
        return atpi;
    }

    /**
     * 从游戏玩法表中获取游戏规则
     *
     * @param gameid 游戏编号
     * @param playid 玩法编号
     * @return 规则对象 查不到信息返回为空
     * @throws Exception
     */
    public static GamePlayInfoGamb getGamePlayInfo(int gameid, int playid) throws Exception {
        GamePlayInfoGamb tpi = new GamePlayInfoGamb();
//        BaseJDBCDao bCDao = new BaseJDBCDao("derby");
//        Connection conn = bCDao.getConnection(true, "virtual");
        OracleJDBCDao ojdbc = new OracleJDBCDao();
        Connection conn = ojdbc.createConnection();
        Statement s = conn.createStatement();
        String sql = "select * from T_GAME_PLAY_INFO_GAMB where game_id=" + gameid + " and play_id=" + playid;
//        logger.info(sql);
        ResultSet rs = s.executeQuery(sql);
        int count = 0;
        while (rs.next()) {
            count += 1;
            tpi.setGame_id(rs.getInt("game_id"));
            tpi.setPlay_id(rs.getInt("play_id"));
            tpi.setPlay_name(rs.getString("play_name"));
            tpi.setStakes_price(rs.getBigDecimal("stakes_price"));
            tpi.setMax_multiple(rs.getInt("max_multiple"));
            tpi.setBet_no_num(rs.getInt("bet_no_num"));
            tpi.setBet_min_no(rs.getInt("bet_min_no"));
            tpi.setBet_max_no(rs.getInt("bet_max_no"));
            tpi.setBlue_no_num(rs.getInt("blue_no_num"));
            logger.error("Blue_min_no is: "+rs.getInt("blue_min_no")+" Blue_max_no: "+rs.getInt("blue_max_no"));
            tpi.setBlue_min_no(rs.getInt("blue_min_no"));
            tpi.setBlue_max_no(rs.getInt("blue_max_no"));
            tpi.setNo_order(rs.getInt("no_order"));
            tpi.setWork_status(rs.getInt("work_status"));
        }
        rs.close();
        s.close();
        conn.close();
        if (count == 0) {
            return null;
        } else {
            return tpi;
        }
    }
}
