package com.bestinfo.dao.sysUser;

import com.bestinfo.bean.sysUser.UserRole;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author lvchangrong
 */
public interface IUserRoleDao {

    /**
     * 根据id获取角色信息
     *
     * @param template
     * @param roleId
     * @return
     */
    public UserRole getUserRoleById(JdbcTemplate template, Integer roleId);

    /**
     * 获取全部角色信息
     *
     * @param template
     * @return
     */
    public List<UserRole> getUserRoleList(JdbcTemplate template);
}
