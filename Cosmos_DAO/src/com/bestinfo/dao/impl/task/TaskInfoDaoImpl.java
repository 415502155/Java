package com.bestinfo.dao.impl.task;

import com.bestinfo.bean.task.TaskInfo;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.task.ITaskInfoDao;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 结算统计-任务及任务规则列表
 *
 * @author zyk
 */
@Repository
public class TaskInfoDaoImpl extends BaseDaoImpl implements ITaskInfoDao {

//    private final Logger logger = Logger.getLogger(TaskInfoDaoImpl.class);
    private static final String SELECT_TASKINFO_LIST = "select task_id,task_name,trade_type,period_type,period_date,auto_task,cur_plan_id,work_status from t_task_info where work_status = 1 and auto_task = 1";

    /**
     * 查询定时任务数据
     *
     * @param jdbcTemplate
     * @return
     */
    @Override
    public List<TaskInfo> getTaskInfoByStatus(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate,
                SELECT_TASKINFO_LIST,
                new Object[]{},
                TaskInfo.class);
    }

}
