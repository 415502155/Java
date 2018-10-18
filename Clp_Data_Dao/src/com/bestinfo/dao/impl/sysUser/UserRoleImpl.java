package com.bestinfo.dao.impl.sysUser;

import com.bestinfo.bean.sysUser.UserRole;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.sysUser.IUserRoleDao;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lvchangrong
 */
@Repository
public class UserRoleImpl extends BaseDaoImpl implements IUserRoleDao {

    /**
     * 根据角色ID查询角色信息
     */
    private static final String GET_USERROLE_BYID = "select role_id,role_name,work_status from t_sys_user_role where role_id = ?";
    /**
     * 查询角色信息列表
     */
    private static final String GET_USERROLE_LIST = "select role_id,role_name,work_status from t_sys_user_role ";

    /**
     * 根据id获取角色信息
     *
     * @param template
     * @param roleId
     * @return
     */
    @Override
    public UserRole getUserRoleById(JdbcTemplate template, Integer roleId) {
        return this.queryForObject(template, GET_USERROLE_BYID, new Object[]{roleId}, UserRole.class);
    }

    /**
     * 获取全部角色信息
     *
     * @param template
     * @return
     */
    @Override
    public List<UserRole> getUserRoleList(JdbcTemplate template) {
        return this.queryForList(template, GET_USERROLE_LIST, null, UserRole.class);
    }
}
