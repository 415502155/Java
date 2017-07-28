package com.bestinfo.dao.impl.sysUser;

import com.bestinfo.bean.sysUser.SysUser;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.sysUser.IUserDao;
import com.bestinfo.dao.page.Pagination;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author yangyf
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl implements IUserDao {

//    private final Logger logger = Logger.getLogger(UserDaoImpl.class);
    private static final String GET_USER_BY_ID = "select user_id,user_name,user_pwd,force_change_pwd,city_id,real_name,regist_date,department_id,role_id,work_status from t_sys_user where user_id = ?";

    private static final String GET_USER_BY_NAME = "select user_id,user_name,user_pwd,force_change_pwd,city_id,real_name,regist_date,department_id,role_id,work_status from t_sys_user where user_name = ?";

    private static final String GET_USER_LIST = "select user_id,user_name,user_pwd,force_change_pwd,city_id,real_name,regist_date,department_id,role_id,work_status from t_sys_user";

    private static final String INSERT_USER = "insert into t_sys_user(user_id,user_name,user_pwd,force_change_pwd,city_id,real_name,regist_date,department_id,role_id,work_status,user_accno,user_accstatus,user_tel) values (?,?,?,?,?, ?,?,?,?,?)";

    private static final String UPDATE_USER = "update t_sys_user set user_pwd=?, force_change_pwd=?, city_id=?, real_name=?, department_id=?, role_id=?, work_status=? where user_id=?";

    /**
     * 获取用户信息
     *
     * @param jdbcTemplate
     * @param userId
     * @return
     */
    @Override
    public SysUser getUserByUserId(JdbcTemplate jdbcTemplate, Integer userId) {
        return this.queryForObject(jdbcTemplate, GET_USER_BY_ID, new Object[]{userId}, SysUser.class);
    }

    /**
     * 获取用户信息
     *
     * @param jdbcTemplate
     * @param userName
     * @return
     */
    @Override
    public SysUser getUserByUserName(JdbcTemplate jdbcTemplate, String userName) {
        return this.queryForObject(jdbcTemplate, GET_USER_BY_NAME, new Object[]{userName}, SysUser.class);
    }

    /**
     * 获取用户信息列表
     *
     * @param jdbcTemplate
     * @return
     */
    @Override
    public List<SysUser> getUserList(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, GET_USER_LIST, null, SysUser.class);
    }

    /**
     * 新增用户
     *
     * @param jdbcTemplate
     * @param user
     * @return 成功-受影响的记录数 失败-(-1)
     */
    @Override
    public int addUser(JdbcTemplate jdbcTemplate, SysUser user) {
        int result;
        try {
            result = jdbcTemplate.update(INSERT_USER, new Object[]{
                user.getUser_id(),
                user.getUser_name(),
                user.getUser_pwd(),
                user.getForce_change_pwd(),
                user.getCity_id(),
                user.getReal_name(),
                user.getRegist_date(),
                user.getDepartment_id(),
                user.getRole_id(),
                user.getWork_status()});
        } catch (DataAccessException e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

    /**
     * 修改用户
     *
     * @param jdbcTemplate
     * @param user
     * @return 成功-受影响的记录数 失败-(-1)
     */
    @Override
    public int updateUser(JdbcTemplate jdbcTemplate, SysUser user) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_USER, new Object[]{
                user.getUser_pwd(),
                user.getForce_change_pwd(),
                user.getCity_id(),
                user.getReal_name(),
                user.getDepartment_id(),
                user.getRole_id(),
                user.getWork_status(),
                user.getUser_id()});
        } catch (DataAccessException e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }

        return result;
    }

    /**
     * 获取用户分页列表
     *
     * @param jdbcTemplate
     * @param params
     * @return
     */
    @Override
    public Pagination<SysUser> getUserPageList(JdbcTemplate jdbcTemplate, Map<String, Object> params) {
        Map<String, Object> whereMap = getWhereStr(params);

        //sql语句
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM T_SYS_USER WHERE 1=1 ");
        sql.append(whereMap.get("whereSql"));

        StringBuilder countSql = new StringBuilder();
        countSql.append("SELECT COUNT(*) FROM T_SYS_USER WHERE 1=1 ");
        countSql.append(whereMap.get("whereSql"));

        //参数列表
        Object[] args = null;
        String paramStr = "";
        if (whereMap.get("whereParam") != null) {
            paramStr += whereMap.get("whereParam").toString();
        }
        if (paramStr != null && !"".equals(paramStr)) {
            args = paramStr.split(",");
        }

        Pagination<SysUser> page = null;
        try {
            page = queryForPage(
                    jdbcTemplate,
                    sql.toString(),
                    countSql.toString(),
                    Integer.parseInt(params.get("pageNumber").toString()),
                    Integer.parseInt(params.get("pageSize").toString()),
                    args,
                    SysUser.class);
        } catch (NumberFormatException e) {
            logger.error("", e);
        }

        return page;
    }

    /**
     * 根据条件列表拼查询sql
     *
     * @param params
     * @return
     */
    private Map<String, Object> getWhereStr(Map<String, Object> params) {
        StringBuilder whereSql = new StringBuilder("");
        StringBuilder whereParam = new StringBuilder("");

        Object user_name = params.get("user_name");
        if (null != user_name) {
            whereSql.append("AND user_name = ?");
            whereParam.append(user_name).append(",");
        }

        Object real_name = params.get("real_name");
        if (null != real_name) {
            whereSql.append("AND real_name = ?");
            whereParam.append(real_name).append(",");
        }

        Object role_id = params.get("role_id");
        if (null != role_id) {
            whereSql.append("AND role_id = ?");
            whereParam.append(role_id).append(",");
        }

        Object department_id = params.get("department_id");
        if (null != department_id) {
            whereSql.append("AND department_id = ?");
            whereParam.append(department_id).append(",");
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("whereSql", whereSql.toString());
        map.put("whereParam", whereParam.toString());

        return map;
    }
}
