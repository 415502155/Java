package com.bestinfo.dao.impl.business;

import com.bestinfo.bean.business.DealerUser;
import com.bestinfo.dao.business.IDealerUserDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 运营商管理员用户表
 *
 * @author user
 */
@Repository
public class DealerUserDaoImpl extends BaseDaoImpl implements IDealerUserDao {

    /**
     * 获取某一终端上指定角色的用户
     */
    private static final String GET_DealerUser_List = "select user_name,user_pwd  from t_dealer_user where role_id=? and terminal_id=?";
    /**
     * 根据机号、用户名获取信息
     */
    private static final String Get_info_By_TerminalId_AND_UserName = "select user_id,user_name,user_pwd,force_change_pwd,city_id,real_name,regist_date,role_id,work_status,dealer_id,terminal_id,operator_id from t_dealer_user where terminal_id = ? and user_name = ? and role_id=?";
    /**
     * 更改密码
     */
    private static final String update_user_pwd = "update t_dealer_user set user_pwd = ? where user_name = ? and terminal_id = ?";
    /**
     * 根据用户名和密码获取信息
     */
    private static final String Get_Pwd_By_User_Name = "select user_pwd,dealer_id,operator_id from t_dealer_user where user_name = ? and role_id = ? ";
    /**
     * 添加用户信息
     */
    private static final String INSERT_Dealer_user = "insert into t_dealer_user(user_id,user_name,user_pwd,force_change_pwd,real_name,regist_date,role_id,work_status,dealer_id,terminal_id,operator_id,city_id)  values(?,?,?,?,?,?,?,?,?,?,?,?)";
    /**
     * 获取指定角色下的所有用户
     */
    private static final String GETALL_DEALERUSER = "select operator_id,terminal_id from t_dealer_user where role_id=?";
    /**
     * 根据操作员编号获取用户名
     */
    private static final String getDealerUserByOperatorId = "select user_name from t_dealer_user where operator_id = ?";

    /**
     * 添加终端操作员
     *
     * @param jdbcTemplate
     * @param ldu
     * @return
     */
    @Override
    public int addTmnOperator(JdbcTemplate jdbcTemplate, final List<DealerUser> ldu) {
        String sql = INSERT_Dealer_user;
        try {
            //批量操作
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int index) throws SQLException {
                    DealerUser statPrize = (DealerUser) ldu.get(index);
                    ps.setLong(1, statPrize.getUser_id());
                    ps.setString(2, statPrize.getUser_name());
                    ps.setString(3, statPrize.getUser_pwd());
                    ps.setInt(4, statPrize.getForce_change_pwd());
                    ps.setString(5, statPrize.getReal_name());
                    ps.setDate(6, new java.sql.Date(statPrize.getRegist_date().getTime()));
                    ps.setInt(7, statPrize.getRole_id());
                    ps.setInt(8, statPrize.getWork_status());
                    ps.setString(9, statPrize.getDealer_id());
                    ps.setInt(10, statPrize.getTerminal_id());
                    ps.setInt(11, statPrize.getOperator_id());
                    ps.setInt(12, statPrize.getCity_id());
                }

                @Override
                public int getBatchSize() {
                    return ldu.size();
                }
            });
        } catch (DataAccessException dataAccessException) {
            logger.error("batch update error", dataAccessException);
            return -1;
        }
        return 0;
    }

    /**
     * 获取运营商管理员用户列表
     *
     * @param jdbcTemplate
     * @return
     */
    @Override
    public List<DealerUser> getDealerUserList(JdbcTemplate jdbcTemplate, int roleid, int terminalid) {
        return this.queryForList(jdbcTemplate, GET_DealerUser_List, new Object[]{roleid, terminalid}, DealerUser.class);
    }

    /**
     * 获取指定角色下所有的用户
     *
     * @param jdbcTemplate
     * @param roleid
     * @return
     */
    @Override
    public List<DealerUser> getAllRoleUSer(JdbcTemplate jdbcTemplate, int roleid) {
        String sql = GETALL_DEALERUSER;
        return this.queryForList(jdbcTemplate, sql, new Object[]{roleid}, DealerUser.class);
    }

    /**
     * 根据用户名修改密码（必须保证用户名在数据库里是唯一的，这个在插入用户的时候就要判断）
     *
     * @param jdbcTemplate
     * @param operator_name
     * @param operator_new_pwd
     * @return 成功(影响的记录数) -1(失败)
     */
    @Override
    public int modifyPwd(JdbcTemplate jdbcTemplate, String operator_name, String operator_new_pwd, int terminalId) {
        int result = 0;
        try {
            result = jdbcTemplate.update(update_user_pwd, new Object[]{operator_new_pwd, operator_name, terminalId});
        } catch (DataAccessException e) {
            logger.error("modify pwd error,terminalId:"+terminalId+",operator_name:"+operator_name, e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

    /**
     * 根据用户名获取信息
     *
     * @param jdbcTemplate
     * @param operator_name
     * @param terminalId
     * @return 用户对象
     */
    @Override
    public DealerUser getDealerUserByName(JdbcTemplate jdbcTemplate, String operator_name, int terminalId, int roleid) {
        return queryForObject(jdbcTemplate, Get_info_By_TerminalId_AND_UserName, new Object[]{terminalId, operator_name, roleid}, DealerUser.class);
    }

    /**
     * 根据用户名查询是否存在数据
     *
     * @param jdbcTemplate
     * @param name
     * @param roleId
     * @return
     */
    @Override
    public DealerUser bankLoginCheck(JdbcTemplate jdbcTemplate, String name, int roleId) {
        return queryForObject(jdbcTemplate, Get_Pwd_By_User_Name, new Object[]{name, roleId}, DealerUser.class);
    }

    /**
     * 根据操作员编号获取操作员信息
     *
     * @param jdbcTemplate
     * @param operatorId 操作员id(工号)
     * @return
     */
    @Override
    public DealerUser getDealerUserByOperatorId(JdbcTemplate jdbcTemplate, int operatorId) {
        String sql = getDealerUserByOperatorId;
        return queryForObject(jdbcTemplate, sql, new Object[]{operatorId}, DealerUser.class);
    }
}
