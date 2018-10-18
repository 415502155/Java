package com.bestinfo.dao.impl.system;

import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.system.ISystemKeyDao;
import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 读系统密钥
 *
 * @author YangRong
 */
@Repository
public class SystemKeyDaoImpl extends BaseDaoImpl implements ISystemKeyDao {
//    private final Logger logger = Logger.getLogger(SystemKeyDaoImpl.class);

    private static final String SELECT_SYSTEM_KEY = " select system_key from t_system_key where  key_name=?   and key_status=? ";

    public String getKey(JdbcTemplate jdbcTemplate, String keyName, int keyStatus) {
        try {
            return (String) jdbcTemplate.queryForObject(SELECT_SYSTEM_KEY, new Object[]{keyName, keyStatus}, String.class);
        } catch (org.springframework.dao.DataAccessException e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            return null;
        }
    }

}
