package com.bestinfo.dao.impl.system;

import com.bestinfo.bean.department.DePartMent;
import com.bestinfo.dao.system.IDepartmentDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lvchangrong
 */
@Repository
public class DepartmentDaoImpl extends BaseDaoImpl implements IDepartmentDao {

    /**
     * 根据部门ID查询部门信息
     */
    private static final String GET_DEPARTMENT_BYID = "select department_id,department_name,work_status from t_sys_department where department_id = ?";
    /**
     * 查询部门列表信息
     */
    private static final String GET_DEPARTMENT_LIST = "select department_id,department_name,work_status from t_sys_department ";

    /**
     * 根据id获取部门信息
     *
     * @param template
     * @param departmentId
     * @return
     */
    @Override
    public DePartMent getDepartmentById(JdbcTemplate template, Integer departmentId) {
        return this.queryForObject(template, GET_DEPARTMENT_BYID, new Object[]{departmentId}, DePartMent.class);
    }

    /**
     * 获取全部部门信息
     *
     * @param template
     * @return
     */
    @Override
    public List<DePartMent> getDepartmentList(JdbcTemplate template) {
        return this.queryForList(template, GET_DEPARTMENT_LIST, null, DePartMent.class);
    }
}
