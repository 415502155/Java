package com.bestinfo.service.impl.sysUser;

import com.bestinfo.define.sysUser.UserStatic;
import com.bestinfo.bean.sysUser.SysUser;
import com.bestinfo.dao.sysUser.IUserDao;
import com.bestinfo.dao.page.Pagination;
import com.bestinfo.service.sysUser.IUserService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author yangyf
 */
@Service
public class UserServiceImpl implements IUserService {
    
    private final Logger logger = Logger.getLogger(IUserService.class);
    
    @Resource
    private IUserDao userDao;
    
    @Resource
    private JdbcTemplate metaJdbcTemplate;

    /**
     * 用户登录
     *
     * @param userName
     * @param userPwd
     * @param request
     * @return 0成功 -1用户不存在 -2用户密码不正确 -3用户修改密码
     */
    public int userLogin(String userName, String userPwd, HttpServletRequest request) {
        SysUser user = userDao.getUserByUserName(metaJdbcTemplate, userName);
        if (user == null) {
            logger.info("user doesn't exist");
            return -1;
        }
        
        if (!user.getUser_pwd().equals(userPwd)) {
            logger.info("password doesn't equals");
            return -2;
        }
        request.getSession().setAttribute("user", user);

        //不是超级用户并且需要强制修改密码
        if (user.getRole_id() != 1 && (user.getForce_change_pwd() == UserStatic.FORCE_CHANGE_PWD_YES)) {
            logger.info("user needs to modify pwd");
            return -3;
        }
        
        return 0;
    }

    /**
     * 根据用户id获取用户
     *
     * @param userId
     * @return
     */
    @Override
    public SysUser getUserByUserId(Integer userId) {
        return userDao.getUserByUserId(metaJdbcTemplate, userId);
    }

    /**
     * 根据用户账号获取用户
     *
     * @param userName
     * @return
     */
    @Override
    public SysUser getUserByUserName(String userName) {
        return userDao.getUserByUserName(metaJdbcTemplate, userName);
    }

    /**
     * 获取所有用户
     *
     * @return
     */
    @Override
    public List<SysUser> getUserList(JdbcTemplate metaJdbcTemplate) {
        return userDao.getUserList(metaJdbcTemplate);
    }

    /**
     * 新增用户
     *
     * @param user
     * @return 0成功 -1用户已经存在 -2未知错误
     */
    @Override
    public int addUser(SysUser user) {
        SysUser dbUser = userDao.getUserByUserName(metaJdbcTemplate, user.getUser_name());
        if (dbUser != null) {//用户已经存在
            logger.info("user already exists,user_id:" + dbUser.getUser_id());
            return -1;
        }
        
        int result = userDao.addUser(metaJdbcTemplate, user);
        return result > 0 ? 0 : -2;
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @return 0成功 -1用户不存在 -2未知错误
     */
    @Override
    public int updateUser(SysUser user) {
        SysUser dbUser = userDao.getUserByUserId(metaJdbcTemplate, user.getUser_id());
        if (dbUser == null) {
            logger.info("user doesn't exist");
            return -1;
        }
        
        int result = userDao.updateUser(metaJdbcTemplate, user);
        return result > 0 ? 0 : -2;
    }

    /**
     * 修改密码
     *
     * @param userId
     * @param oldPwd
     * @param newPwd
     * @return 0成功 -1用户不存在 -2旧密码不正确 -3未知错误
     */
    @Override
    public int modifyUserPwd(int userId, String oldPwd, String newPwd) {
        SysUser dbUser = userDao.getUserByUserId(metaJdbcTemplate, userId);
        if (dbUser == null) {
            logger.info("user doesn't exist");
            return -1;
        }
        if (!oldPwd.equals(dbUser.getUser_pwd())) {
            logger.info("old pwd doesn't equal");
            return -2;
        }
        
        dbUser.setUser_pwd(newPwd);
        dbUser.setForce_change_pwd(UserStatic.FORCE_CHANGE_PWD_NO);//改为不再强制修改密码

        int result = userDao.updateUser(metaJdbcTemplate, dbUser);
        return result > 0 ? 0 : -3;
    }

    /**
     * 获取用户分页列表
     *
     * @param params
     * @return
     */
    @Override
    public Pagination<SysUser> getUserPageList(Map<String, Object> params) {
        return userDao.getUserPageList(metaJdbcTemplate, params);
    }
}
