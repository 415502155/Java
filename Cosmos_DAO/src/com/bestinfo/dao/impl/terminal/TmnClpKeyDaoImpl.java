package com.bestinfo.dao.impl.terminal;

import com.bestinfo.bean.terminal.TmnClpKey;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.terminal.ITmnClpKeyDao;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 投注机--中彩密钥数据
 */
@Repository
public class TmnClpKeyDaoImpl extends BaseDaoImpl implements ITmnClpKeyDao {

    /**
     * 插入
     */
    private static final String INSERT_CLPKEY = "insert into t_tmn_clpkey values(?,?,?,?,?,?)";

    /**
     * 插入
     */
    private static final String UPDATE_CLPKEY = "update t_tmn_clpkey set end_time=? where terminal_id=? and key_type=? and create_time=?";

    /**
     * 查询
     */
    private static final String GET_CLPKEY_BY_TERMINAL_ID = "select * from t_tmn_clpkey where terminal_id = ? order by create_time desc";

    /**
     * 新增分级历史信息
     *
     * @param jdbcTemplate
     * @param clpKey
     * @return
     */
    @Override
    public int addClpKey(JdbcTemplate jdbcTemplate, TmnClpKey clpKey) {
        String sql = INSERT_CLPKEY;
        int result = 0;
        try {
            result = jdbcTemplate.update(sql, new Object[]{
                clpKey.getTerminal_id(),
                clpKey.getKey_type(),
                clpKey.getCreate_time(),
                clpKey.getEnd_time(),
                clpKey.getPublic_key(),
                clpKey.getKey_sign()
            });
        } catch (DataAccessException e) {
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

    /**
     *
     * @param jdbcTemplate
     * @param clpKey
     * @return
     */
    @Override
    public int updateClpKey(JdbcTemplate jdbcTemplate, TmnClpKey clpKey) {
        String sql = UPDATE_CLPKEY;
        int result = 0;
        try {
            result = jdbcTemplate.update(sql, new Object[]{
                clpKey.getEnd_time(),
                clpKey.getTerminal_id(),
                clpKey.getKey_type(),
                clpKey.getCreate_time(),
            });
        } catch (DataAccessException e) {
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

    /**
     *
     * @param jdbcTemplate
     * @param terminal_id
     * @return
     */
    @Override
    public List<TmnClpKey> getClpKeyByTerminalId(JdbcTemplate jdbcTemplate, int terminal_id) {
        return this.queryForList(jdbcTemplate, GET_CLPKEY_BY_TERMINAL_ID, new Object[]{terminal_id}, TmnClpKey.class);
    }

}
