package com.bestinfo.dao.impl.encoding;

import com.bestinfo.bean.encoding.WorkStatus;
import com.bestinfo.dao.encoding.IWorkStatus;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 工作状态
 *
 * @author chenliping
 */
@Repository
public class WorkStatusImpl extends BaseDaoImpl implements IWorkStatus {

    private static final String GETALL_WORKSTATUS = "select work_status,work_status_name from t_work_status";

    /**
     * 查询所有的工作状态
     *
     * @param jdbcTemplate
     * @return
     */
    @Override
    public List<WorkStatus> getAllWorkStatus(JdbcTemplate jdbcTemplate) {
        String sql = GETALL_WORKSTATUS;
        return this.queryForList(jdbcTemplate, sql, null, WorkStatus.class);
    }

}
