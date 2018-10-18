package com.bestinfo.service.sysUser;

import com.bestinfo.bean.sysUser.SysUser;
import com.bestinfo.dao.page.Pagination;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author yangyf
 */
public interface IUserService {

    public int userLogin(String userName, String userPwd, HttpServletRequest request);

    public SysUser getUserByUserId(Integer userId);

    public SysUser getUserByUserName(String userName);

    public List<SysUser> getUserList(JdbcTemplate jdbcTemplate);

    public int addUser(SysUser user);

    public int updateUser(SysUser user);

    public int modifyUserPwd(int userId, String oldPwd, String newPwd);

    public Pagination<SysUser> getUserPageList(Map<String, Object> params);
}
