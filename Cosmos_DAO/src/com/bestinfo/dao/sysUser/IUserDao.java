package com.bestinfo.dao.sysUser;

import com.bestinfo.bean.sysUser.SysUser;
import com.bestinfo.dao.page.Pagination;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author yangyf
 */
public interface IUserDao {

    public SysUser getUserByUserId(JdbcTemplate template, Integer userId);

    /**
     * 指定用户名查询用户
     *
     * @param template
     * @param userName
     * @return
     */
    public SysUser getUserByUserName(JdbcTemplate template, String userName);

    /**
     * 获取用户列表信息
     *
     * @param template
     * @return
     */
    public List<SysUser> getUserList(JdbcTemplate template);

    /**
     * 新增用户
     *
     * @param jdbcTemplate
     * @param user
     * @return 成功-受影响的记录数 失败-(-1)
     */
    public int addUser(JdbcTemplate jdbcTemplate, SysUser user);

    /**
     * 修改用户
     *
     * @param jdbcTemplate
     * @param user
     * @return 成功-受影响的记录数 失败-(-1)
     */
    public int updateUser(JdbcTemplate jdbcTemplate, SysUser user);

    /**
     * 用户分页列表信息
     *
     * @param jdbcTemplate
     * @param params
     * @return
     */
    public Pagination<SysUser> getUserPageList(JdbcTemplate jdbcTemplate, Map<String, Object> params);
}
