package com.bestinfo.dao.impl.game;

import com.bestinfo.bean.game.OpenConfigureStep;
import com.bestinfo.dao.game.IOpenConfigureStepDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 游戏--开奖步骤配置(T_open_configure_step)
 *
 * @author yangyuefu
 */
@Repository
public class OpenConfigureStepDaoImpl extends BaseDaoImpl implements IOpenConfigureStepDao {

    private static final String GET_STEP = "select open_configure_id,step_id,process_status,next_process_status,work_status from t_open_configure_step where open_configure_id=? and process_status=?";

    /**
     * 获取开奖步骤
     *
     * @param jdbcTemplate
     * @param open_configure_id
     * @param process_status
     * @return
     */
    @Override
    public OpenConfigureStep getStep(JdbcTemplate jdbcTemplate, int open_configure_id, int process_status) {
        return queryForObject(jdbcTemplate, GET_STEP,
                new Object[]{open_configure_id, process_status}, OpenConfigureStep.class);
    }
}
