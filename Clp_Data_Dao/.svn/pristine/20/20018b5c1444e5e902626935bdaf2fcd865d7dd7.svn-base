package com.bestinfo.dao.system;

import com.bestinfo.bean.department.DePartMent;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 部门表
 *
 * @author lvchangrong
 */
public interface IDepartmentDao {

    /**
     * 根据id获取部门信息
     *
     * @param template
     * @param departmentId
     * @return
     */
    public DePartMent getDepartmentById(JdbcTemplate template, Integer departmentId);

    /**
     * 获取全部部门信息
     *
     * @param template
     * @return
     */
    public List<DePartMent> getDepartmentList(JdbcTemplate template);
}
