package com.bestinfo.dao.encoding;

import com.bestinfo.bean.encoding.WorkStatus;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 工作状态
 *
 * @author chenliping
 */
public interface IWorkStatus {

    /**
     * 查询所有的工作状态
     *
     * @param jdbcTemplate
     * @return
     */
    public List<WorkStatus> getAllWorkStatus(JdbcTemplate jdbcTemplate);

}
