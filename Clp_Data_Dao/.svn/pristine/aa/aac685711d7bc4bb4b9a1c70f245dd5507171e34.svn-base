/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.impl.encoding;

import com.bestinfo.bean.encoding.PrizeType;
import com.bestinfo.dao.encoding.IPrizeType;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author user
 */
@Repository
public class PrizeTypeImpl extends BaseDaoImpl implements IPrizeType {

    /**
     * 查询账户奖金返奖类型数据列表sql
     */
    private static final String GET_PRIZETYPE_LIST = "select  prize_type,prize_type_name,work_status  from t_prize_type";
    /**
     * 根据账户奖金返奖类型编号更新证件类型sql
     */
    private static final String UPDATE_PRIZETYPE = "update t_prize_type set prize_type_name = ?,work_status = ? where prize_type = ?";

    /**
     * 查询账户奖金返奖类型的数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    @Override
    public List<PrizeType> selectPrizeType(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, GET_PRIZETYPE_LIST, null, PrizeType.class);
    }

    /**
     * 修改账户奖金返奖类型数据
     *
     * @param jdbcTemplate
     * @param pt
     * @return
     */
    @Override
    public int updatePrizeType(JdbcTemplate jdbcTemplate, PrizeType pt) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_PRIZETYPE, new Object[]{
                pt.getPrize_type_name(),
                pt.getWork_status(),
                pt.getPrize_type()
            });
        } catch (DataAccessException e) {
            logger.error("prize_type:"+pt.getPrize_type(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }
}
