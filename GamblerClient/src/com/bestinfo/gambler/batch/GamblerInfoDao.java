package com.bestinfo.gambler.batch;

import com.bestinfo.gambler.all.OracleJDBCDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class GamblerInfoDao {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(GamblerInfoDao.class);

    public static List<GamblerInfo> getGambInfo(String dealerid) throws Exception {

        List<GamblerInfo> list = new ArrayList<>();
        OracleJDBCDao ojdbc = new OracleJDBCDao();
        Connection conn = ojdbc.createConnection();
        PreparedStatement s = null;
        ResultSet rs = null;
        String sql = "select * from t_gambler_info t where t.dealer_id=" + dealerid + " and t.gambler_status_id=1 and t.gambler_role=11";
        logger.info("t_gambler_info");
        try {
            s = conn.prepareStatement(sql);

            rs = s.executeQuery();
            while (rs.next()) {
                GamblerInfo info = new GamblerInfo();
                String ss = rs.getString("GAMBLER_NAME");
                info.setGambler_name(ss);
                info.setPwd("234");
                logger.info(ss);
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

    public static List<GamblerInfo> getGamblerInfo(String dealerid, int row, int end) throws Exception {

        List<GamblerInfo> list = new ArrayList<>();
        OracleJDBCDao ojdbc = new OracleJDBCDao();
        Connection conn = ojdbc.createConnection();
        PreparedStatement s = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM (select b.*,ROWNUM rn from (select a.* from T_gambler_info a where a.dealer_id=" + dealerid + " and a.gambler_status_id=1 and a.gambler_role=11 order by gambler_id) b) WHERE rn BETWEEN " + row + " AND " + end + "";

        // String sql = "select * from t_gambler_info t where t.dealer_id="+dealerid+" and t.gambler_status_id=1 and t.gambler_role=11 and rownum >="+row+" and rownum <="+end+" order by gambler_id";
        logger.info("t_gambler_info");
        try {
            s = conn.prepareStatement(sql);

            rs = s.executeQuery();
            while (rs.next()) {
                GamblerInfo info = new GamblerInfo();
                String ss = rs.getString("GAMBLER_NAME");
                info.setGambler_name(ss);
                info.setPwd("123");
                logger.info(ss);
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
}
