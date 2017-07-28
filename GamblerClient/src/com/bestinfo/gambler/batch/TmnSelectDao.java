/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.gambler.batch;

import com.bestinfo.gambler.all.OracleJDBCDao;
import com.bestinfo.gambler.bet.UserSelect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 用于查找 需要批处理的 终端机号
 * @author liyongjia
 */
public class TmnSelectDao {
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UserSelect.class);
    
    public static List<Terminal> getTerminalAndCity(int dealer_id,int from,int to) throws Exception {

        List<Terminal> list = new ArrayList<>();
        OracleJDBCDao ojdbc = new OracleJDBCDao();
        Connection conn = ojdbc.createConnection();
        PreparedStatement s = null;
        ResultSet rs = null;
        String sql = "select t.terminal_phy_id,t.city_id" +
                    "   from T_TMN_INFO t" +
                    "  where t.dealer_id = ?" +
                    "    and ? <= t.terminal_phy_id" +
                    "    and t.terminal_phy_id <= ?";
        logger.info("查询指定运营商终端机号");
        try {
            s = conn.prepareStatement(sql);
            s.setInt(1, dealer_id);
            s.setInt(2, from);//下限
            s.setInt(3, to);//上限
            rs = s.executeQuery();
            while (rs.next()) {
                Terminal info = new Terminal();
                info.setTerminal_id(new Integer(rs.getInt("terminal_phy_id")));
                info.setCity_id(new Integer(rs.getInt("city_id")));
                list.add(info);
            }
            rs.close();
            s.close();
            conn.close();
        } catch (SQLException e) {
            logger.error("", e);
        } finally {
        }
        return list;
    }
    
    
    public static void getTerminalId(int dealer_id , int from , int to , Set<Integer> set) throws Exception{
        if(from>to) throw new Exception("上下限参数不合法！");
        OracleJDBCDao ojdbc = new OracleJDBCDao();
        Connection conn = ojdbc.createConnection();
        PreparedStatement s = null;
        ResultSet rs = null;
        String sql = "select t.terminal_phy_id" +
                    "   from T_TMN_INFO t" +
                    "  where t.dealer_id = ?" +
                    "    and ? <= t.terminal_phy_id" +
                    "    and t.terminal_phy_id <= ?";
        logger.info("查询指定运营商终端机号");
        try {
            s = conn.prepareStatement(sql);
            s.setInt(1, dealer_id);
            s.setInt(2, from);//下限
            s.setInt(3, to);//上限
            rs = s.executeQuery();
            while (rs.next()) {
                set.add(new Integer(rs.getInt("terminal_phy_id")));
            }
            rs.close();
            s.close();
            conn.close();
        } catch (SQLException e) {
            logger.error("", e);
        }finally{
        }
    }
    
    public static void getAllTerminalId(int dealer_id , Set<Integer> set) throws Exception{
        OracleJDBCDao ojdbc = new OracleJDBCDao();
        Connection conn = ojdbc.createConnection();
        PreparedStatement s = null;
        ResultSet rs = null;
        String sql = "select t.terminal_phy_id" +
                    "   from T_TMN_INFO t" +
                    "  where t.dealer_id = ?"; 
                  
        logger.info("查询指定运营商终端机号");
        try {
            s = conn.prepareStatement(sql);
            s.setInt(1, dealer_id);
            rs = s.executeQuery();
            while (rs.next()) {
                set.add(new Integer(rs.getInt("terminal_phy_id")));
            }
            rs.close();
            s.close();
            conn.close();
        } catch (SQLException e) {
            logger.error("", e);
        }finally{
        }
    }
    
}
