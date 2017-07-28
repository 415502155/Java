package com.bestinfo.dao.impl.center;

import com.bestinfo.bean.cash.CashRequest;
import com.bestinfo.dao.center.ICenterCashDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 中心兑奖
 *
 * @author liyongjia
 */
@Repository
public class CenterCashDaoImpl extends BaseDaoImpl implements ICenterCashDao {

    /**
     * 兑奖存储过程
     *
     * @param jdbcTemplate
     * @return
     */
    @Override
    public Map<String, Object> cash(JdbcTemplate jdbcTemplate, final CashRequest cashRequest) {
        String sql = "{call p_ticket_centercash(?,?,?,?,?,?,?,?,?,?,?)}";
        Map<String, Object> re = (Map<String, Object>) jdbcTemplate.execute(sql, new CallableStatementCallback() {
            @Override
            public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                Map<String, Object> resMap = new HashMap<>();
                logger.info("请求到达Dao层：" + cashRequest.toString());
                try {
                    //输入参数
                    cs.setString(1, cashRequest.getCipher());
                    cs.setInt(2, cashRequest.getOperator_id());
                    cs.setString(3, cashRequest.getName());
                    cs.setString(4, cashRequest.getId_card());
                    cs.setString(5, cashRequest.getAddress());
                    cs.setString(6, cashRequest.getIdImgStr());
                    cs.setString(7, cashRequest.getLotteryImgStr());
                    cs.setInt(8, cashRequest.getTicket_type());
                    cs.setString(9, cashRequest.getTicket_id());

                    //输出参数                        
                    cs.registerOutParameter(10, Types.INTEGER);
                    cs.registerOutParameter(11, Types.VARCHAR);

                    cs.execute();

                    int returnCode = cs.getInt(10);
                    String msg = cs.getString(11);
                    logger.info("center cash dao,returnCode:" + returnCode + ",returnMsg:" + msg);
                    resMap.put("code", Integer.valueOf(returnCode).toString());
                    resMap.put("msg", msg);
                    return resMap;
                } catch (Exception ex) {
                    logger.error("中心兑奖Repository异常", ex);
                    resMap.put("code", -20);
                    resMap.put("msg", "中心兑奖Repository异常");
                    return resMap;
                }
            }
        });
        return re;
    }
}
