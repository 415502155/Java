package com.bestinfo.dao.business;

import com.bestinfo.bean.business.DealerUser;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 代销商-运营商管理员用户列表
 *
 * @author lvchangrong
 */
public interface IDealerUserDao {

    /**
     * 添加终端操作员--clp
     *
     * @param jdbcTemplate
     * @param ldu
     * @return
     */
    public int addTmnOperator(JdbcTemplate jdbcTemplate, final List<DealerUser> ldu);

    /**
     * 根据角色编号和投注机终端编号获取运营商管理员用户列表
     *
     * @param jdbcTemplate
     * @param roleid
     * @param terminalid
     * @return
     */
    public List<DealerUser> getDealerUserList(JdbcTemplate jdbcTemplate, int roleid, int terminalid);

    /**
     * 获取指定角色下所有用户
     *
     * @param jdbcTemplate
     * @param roleid
     * @return
     */
    public List<DealerUser> getAllRoleUSer(JdbcTemplate jdbcTemplate, int roleid);

    /**
     * 查询指定投注机指定用户指定角色的用户
     *
     * @param jdbcTemplate
     * @param operator_name
     * @param terminalId
     * @param roleid
     * @return
     */
    public DealerUser getDealerUserByName(JdbcTemplate jdbcTemplate, String operator_name, int terminalId, int roleid);

    /**
     * 修改密码
     *
     * @param jdbcTemplate
     * @param operator_name
     * @param operator_new_pwd
     * @param terminalId
     * @return
     */
    public int modifyPwd(JdbcTemplate jdbcTemplate, String operator_name, String operator_new_pwd, int terminalId);

    /**
     * 银行登录用户名查询
     *
     * @param jdbcTemplate
     * @param name
     * @param roleId
     * @return
     */
    public DealerUser bankLoginCheck(JdbcTemplate jdbcTemplate, String name, int roleId);

    /**
     * 根据操作员id获取管理员信息
     *
     * @param jdbcTemplate
     * @param operatorId 操作员id（工号）
     * @return 运营商管理员
     */
    public DealerUser getDealerUserByOperatorId(JdbcTemplate jdbcTemplate, int operatorId);
    
    public DealerUser getDealerUserByUserName(JdbcTemplate jdbcTemplate, String name);
}
