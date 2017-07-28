package com.bestinfo.dao.heartbeat;

import com.bestinfo.bean.heartbeat.AppPluse;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AppPluseDaoImpl extends BaseDaoImpl implements IAppPluseDao {

    public static final String GET_APPPLUSE_BY_ID = "SELECT * FROM t_app_pluse WHERE app_id=?";

    public static final String UPDATE_APPPLUSE_BY_ID = "UPDATE t_app_pluse SET last_time=? WHERE app_id=?";

    @Override
    public AppPluse getAppPluseById(JdbcTemplate jdbcTemplate, int appId) {
        return queryForObject(jdbcTemplate, GET_APPPLUSE_BY_ID, new Object[]{appId}, AppPluse.class);
    }

    @Override
    public int updateAppPluseById(JdbcTemplate jdbcTemplate, AppPluse appPluse) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_APPPLUSE_BY_ID, new Object[]{
                appPluse.getLast_time(),
                appPluse.getApp_id()});
        } catch (DataAccessException e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }

        return result;
    }

}
