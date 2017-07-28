//package com.bestinfo.dao.impl.plsql;
//
//import com.bestinfo.dao.impl.BaseDaoImpl;
//import com.bestinfo.dao.plsql.IDrawStatisticsDao;
//import java.sql.CallableStatement;
//import java.sql.SQLException;
//import org.apache.log4j.Logger;
//import org.springframework.dao.DataAccessException;
//import org.springframework.jdbc.core.CallableStatementCallback;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
///**
// *
// * @author chenliping
// */
//@Repository
//public class DrawStatisticsDaoImpl  extends BaseDaoImpl implements IDrawStatisticsDao{
//
//    private final Logger logger = Logger.getLogger(DrawStatisticsDaoImpl.class);
//    
//    /**
//     * 期统计
//     * @param jdbcTemplate
//     * @param gameid
//     * @param drawid
//     * @return 
//     */
//    @Override
//    public int drawstatistics(JdbcTemplate jdbcTemplate, final int gameid, final int drawid) {
//        try {
//            String sql = "{call drawStatistics(?,?)}";
//            int re = (Integer) jdbcTemplate.execute(sql, new CallableStatementCallback() {
//                @Override
//                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
//                    cs.setInt(1, gameid);
//                    cs.setInt(2, drawid);
//                    cs.execute();
//                    return 0;
//                }
//            });
//            return re;
//        } catch (Exception e) {
//            logger.error("", e);
//            return -2;
//        }
//    }
//    
//}
