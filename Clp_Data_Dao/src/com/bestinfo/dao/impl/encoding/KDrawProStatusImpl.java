package com.bestinfo.dao.impl.encoding;

import com.bestinfo.bean.encoding.KDrawProcessStatus;
import com.bestinfo.dao.encoding.IKDrawProStatus;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hhhh
 */
@Repository
public class KDrawProStatusImpl extends BaseDaoImpl implements IKDrawProStatus {

    /**
     * 根据进度编号获取快开游戏期进度状态sql
     */
    private static final String GET_KDRAWPROSTATUS_BY_ID = "select keno_process_status,kdraw_status_name,work_status  from t_kdraw_process_status where keno_process_status = ?";

    /**
     * 查询快开游戏期进度状态列表数据sql
     */
    private static final String GET_KDRAWPROSTATUS_List = "select keno_process_status,kdraw_status_name,work_status from t_kdraw_process_status";

    /**
     * 根据进度编号更新快开游戏期进度状态sql
     */
    private static final String UPDATE_KDRAW_Pro_STATUS = "update t_kdraw_process_status set kdraw_status_name = ?,work_status = ?  where keno_process_status = ?";

    /**
     * 新增快开游戏期进度状态sql
     */
    private static final String INSERT_KDRAW_Pro_STATUS = "insert into t_kdraw_process_status(keno_process_status,kdraw_status_name,work_status) values(?,?,?)";

    /**
     * 查询快开游戏期进度状态数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据
     */
    @Override
    public List<KDrawProcessStatus> selectKDrawProStatus(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, GET_KDRAWPROSTATUS_List, null, KDrawProcessStatus.class);
    }

    /**
     * 修改快开游戏期进度状态
     *
     * @param jdbcTemplate
     * @param kp
     * @return 成功-影响记录数 失败-(-1)
     */
    @Override
    public int updateKDrawProStatus(JdbcTemplate jdbcTemplate, KDrawProcessStatus kp) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_KDRAW_Pro_STATUS, new Object[]{
                kp.getKdraw_status_name(),
                kp.getWork_status(),
                kp.getKeno_process_status()
            });
        } catch (DataAccessException e) {
            logger.error("Keno_process_status:"+kp.getKeno_process_status(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }
}
