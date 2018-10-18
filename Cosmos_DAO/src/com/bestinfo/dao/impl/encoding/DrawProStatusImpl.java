package com.bestinfo.dao.impl.encoding;

import com.bestinfo.bean.encoding.DrawProcessStatus;
import com.bestinfo.dao.encoding.IDrawProStatus;
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
public class DrawProStatusImpl extends BaseDaoImpl implements IDrawProStatus {

    /**
     * 查询游戏期进度状态列表数据sql
     */
    private static final String GET_DRAWPROSTATUS_List = "select process_status,process_status_name,work_status from t_draw_process_status";

    /**
     * 根据进度编号更新游戏期进度状态sql
     */
    private static final String UPDATE_DRAW_Pro_STATUS = "update t_draw_process_status set process_status_name = ?,work_status = ? where process_status = ?";

    /**
     * 查询游戏期进度状态数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据
     */
    @Override
    public List<DrawProcessStatus> selectDrawProStatus(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, GET_DRAWPROSTATUS_List, null, DrawProcessStatus.class);
    }

    /**
     * 修改游戏期进度状态
     *
     * @param jdbcTemplate
     * @param kp
     * @return 成功-影响记录数 失败-(-1)
     */
    @Override
    public int updateDrawProStatus(JdbcTemplate jdbcTemplate, DrawProcessStatus kp) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_DRAW_Pro_STATUS, new Object[]{
                kp.getProcess_status_name(),
                kp.getWork_status(),
                kp.getProcess_status()
            });
        } catch (DataAccessException e) {
            logger.error("Process_status:"+kp.getProcess_status(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

}
