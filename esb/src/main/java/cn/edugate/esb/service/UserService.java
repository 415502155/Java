package cn.edugate.esb.service;

import java.util.List;
import java.util.Map;

import org.springframework.integration.Message;

import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.entity.Right;
import cn.edugate.esb.entity.Role;
import cn.edugate.esb.entity.RoleLabel;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.entity.TeacherRole;
import cn.edugate.esb.entity.TechRange;
import cn.edugate.esb.entity.UcUser;
import cn.edugate.esb.entity.UcuserOrguser;
import cn.edugate.esb.entity.UserAccount;
import cn.edugate.esb.exception.EsbException;
import cn.edugate.esb.util.Paging;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

/**
 * 用户服务接口 Title:UserService Description: Company:SJWY
 * 
 * @author:Liuy
 * @Date:2017年4月25日下午1:57:54
 */
public interface UserService {

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @throws MySQLIntegrityConstraintViolationException
	 */
	public abstract void add(UcUser user);

	/**
	 * 删除用户
	 * 
	 * @param userID
	 * @return
	 */
	public abstract int delete(Integer userID);

	/**
	 * 更新用户
	 * 
	 * @param user
	 * @return
	 */
	public abstract int update(UcUser user);

	/**
	 * 根据用户主键获取用户信息
	 * 
	 * @param userID
	 * @return
	 */
	public abstract UcUser getByID(Integer userID);

	/**
	 * 获取用户列表
	 * 
	 * @param user
	 * @return
	 */
	public abstract List<UcUser> getAll(UcUser user);

	/**
	 * 获取用户列表(带分页)
	 * 
	 * @param user
	 * @return
	 */
	public abstract Paging<UcUser> getAllWithPaging(Paging<UcUser> paging, String name, String mobile, String ssoType,
			String mail, String orgType, String udid);

	/**
	 * 根据角色主键获取用户列表
	 * 
	 * @param user
	 * @return
	 */
	public abstract List<UcUser> getAllByRoleId(Integer roleId);

	/**
	 * 根据角色主键获取用户列表(带分页)
	 * 
	 * @param user
	 * @return
	 */
	public abstract Paging<UcUser> getAllByRoleIdWithPaging(Paging<UcUser> paging, String name, String mobile, String mail,
			String type, String roleId, String orgType, String udid);

	/**
	 * 检查登录名是否可用
	 * 
	 * @param name 登录名
	 * @param uid 当前用户主键（update时做判断）
	 * @return 是否可用
	 */
	public abstract boolean checkName(String name, String uid);

	/**
	 * 通过登录名获取用户
	 * 
	 * @param login_name 登录名
	 * @param org_type 组织类型
	 * @return
	 */
	public abstract UcUser getUserByLoginName(String login_name, Integer org_type);

	/**
	 * 用户登录操作
	 * 
	 * @param login_name 登录名
	 * @param login_pass 登陆密码
	 * @param org_type 组织类型
	 * @param version
	 * @return
	 * @throws Exception
	 */
	public abstract OrgUser login(String login_name, String login_pass, Integer org_type) throws EsbException;

	public abstract Map<String, OrgUser> login(String login_name, String login_pass) throws EsbException;

	/**
	 * 中心用户登录
	 * 
	 * @param login_name
	 * @param login_pass
	 * @param version
	 * @return
	 */
	public abstract UcUser uclogin(String login_name, String login_pass, Integer version) throws EsbException;

	/**
	 * 获取所以用户
	 * 
	 * @return
	 */
	public abstract List<OrgUser> getOrgUserList();

	public abstract void deleteById(Integer id);

	public abstract List<UcUser> getUcUserList();

	/**
	 * 获取用户在不同平台的登陆信息
	 * 
	 * @param user_id
	 * @param version
	 * @return
	 */
	public abstract UserAccount getUserAccount(String targetid, String version, String type);

	/**
	 * 添加用户登录信息
	 * 
	 * @param user_id
	 * @param version
	 * @param udid
	 * @param i
	 * @param integer
	 */
	public abstract void addUserAccount(String targetid, Integer version, String udid, Integer user_id, Integer org_id,
			Integer type);

	/**
	 * 生成用户登录token
	 * 
	 * @param user_id
	 * @param user_salt
	 * @param version
	 * @param udid
	 * @param type
	 * @return
	 */
	public abstract String createToken(Integer user_id, String user_salt, Integer version, String udid, String type);

	/**
	 * 获取用户认证关系集合
	 * 
	 * @return
	 */
	public abstract List<UcuserOrguser> getUcuserOrguserList();

	/**
	 * 获取教师与角色的关系
	 * 
	 * @return
	 */
	public abstract List<TeacherRole> getTeacherRoles();

	/**
	 * 获取教师与角色的关系
	 * 
	 * @return
	 */
	public abstract List<TeacherRole> getTeacherRolesOfSql();

	/**
	 * 获取所有角色
	 * 
	 * @return
	 */
	public abstract List<Role> getRoles();

	/**
	 * 获取权限配置列表
	 * 
	 * @return
	 */
	public abstract List<Right> getRights();

	/**
	 * 通过ID获取机构用户
	 * 
	 * @param uid
	 * @return
	 */
	public abstract OrgUser getOrgUserById(Integer uid);

	/**
	 * 分页获取机构用户
	 * 
	 * @param loginname
	 * @param org_id
	 * @param mobile
	 * @param identity
	 * @param pages
	 */
	public abstract void getOrgUserWithPaging(String loginname, String org_id, String mobile, Integer identity,
			Paging<OrgUser> pages);

	/**
	 * 更新机构用户
	 * 
	 * @param user
	 */
	public abstract void updateOrgUser(OrgUser user);

	/**
	 * 添加机构用户
	 * 
	 * @param user
	 */
	public abstract void SaveOrgUser(OrgUser user);

	/**
	 * 更新教师信息
	 * 
	 * @param t
	 */
	public abstract void updateTeacher(Teacher t);

	/**
	 * 通过登录名和组织ID获取机构用户
	 * 
	 * @param user_loginname
	 * @param org_id
	 * @return
	 */
	public abstract OrgUser getOrgUserByLoginName(String user_loginname, Integer org_id);

	/**
	 * 获取教师范围
	 * 
	 * @return
	 */
	public abstract List<TechRange> getTechRanges();

	/**
	 * 获取角色标签
	 * 
	 * @return
	 */
	public abstract List<RoleLabel> getRoleLabels();

	/**
	 * 发送验证码
	 * 
	 * @param phone
	 * @return
	 */
	public abstract Map<String, String> sendValidCode(String phone);

	/**
	 * 验证手机验证码
	 * 
	 * @param phone
	 * @param code
	 * @throws EsbException
	 */
	public abstract void validCode(String phone, String code) throws EsbException;

	/**
	 * 重置密码
	 * 
	 * @param phone
	 * @param passwd
	 * @param code
	 * @param org_id
	 */
	public abstract void reSetPassword(String phone, String passwd, String code, Integer org_id) throws EsbException;

	/**
	 * 修改密码
	 * 
	 * @param user_id
	 * @param type
	 * @param oldpwd
	 * @param passwd
	 */
	public abstract void modifyPwd(Integer user_id, Integer type, String oldpwd, String passwd) throws EsbException;

	/**
	 * 根据机构用户ID 查找教师信息
	 * 
	 * @param uid
	 * @return
	 */
	public abstract Teacher getTeacherByUserId(Integer uid);

	public abstract List<UcuserOrguser> getOrgByUCID(String uc_id);

	/**
	 * 通过手机号查找机构用户
	 * 
	 * @param phone
	 * @return
	 */
	public abstract List<OrgUser> getOrgUserByMobile(String phone);

	/**
	 * 退出登录
	 * 
	 * @param ua_id
	 */
	public abstract void RemoveUserAccountByUaId(Integer ua_id);

	/**
	 * 删除机构用户
	 * 
	 * @param user_id
	 */
	public abstract void deleteOrgUser(Integer user_id);

	/**
	 * 查询用户 是否存在（登录号 + 手机号 联合验证）
	 * 
	 * @param String phone,int identity,int org_id
	 */
	public boolean getUserIsExist(String loginName, String phone, int identity, int org_id);

	/**
	 * 判断登录名是否存在
	 * 
	 * @param loginName
	 * @param identity
	 * @param org_id
	 * @return
	 */
	public boolean isExist(String loginName, Integer identity, Integer org_id);

	/**
	 * 获取当前机构系统管理员账户
	 * 
	 * @param org_id
	 * @return
	 */
	public abstract OrgUser getUserForManager(Integer org_id);

	/**
	 * 为机构创建默认最高管理员账户
	 * 
	 * @param orgID
	 */
	public abstract OrgUser createManagerForOrg(Organization org);

	/**
	 * 批量删除机构用户
	 * 
	 * @param list
	 */
	public abstract void deleteOrgUser(List<OrgUser> list);

	public abstract void setRoles(OrgUser user);

	/**
	 * 获取用户登录信息
	 * 
	 * @param parseInt
	 * @return
	 */
	public abstract UserAccount getUserAccountById(Integer ua_id);

	public abstract void saveUserAccount(UserAccount ua);

	public abstract OrgUser getAdminByOrgid(Integer org_id);

	public abstract void messageChannelsend(Message<String> message);

	public abstract Map<String, OrgUser> login(String login_name, String login_pass, String identity);

	public abstract OrgUser wxlogin(String login_name, String login_pass, String identity, Integer org_id);

	/**
	 * 发送验证码
	 * 
	 * @param phone
	 * @return
	 */
	public abstract Map<String, String> sendValidCodeWX(String phone);

	/**
	 * 根据家长手机号码查询学生
	 * 
	 * @param loginname
	 * @param org_id
	 * @param mobile
	 * @param pages
	 */
	public abstract Paging<OrgUser> getOrgStudentByParentWithPaging(String loginname, String org_id, String mobile,
			Paging<OrgUser> pages);
	
	/**
	 * 根据机构ID， 身份和手机号码查询org_user
	 * @param org_id
	 * @param identity
	 * @param user_mobile
	 * @return
	 */
	public abstract OrgUser getOrgUser(int org_id, int identity, String user_mobile);

	/**
	 * 根据机构主键查询机构用户
	 * @param parseInt
	 * @return
	 */
	public abstract List<OrgUser> getOrguserByOrgId(int parseInt);
	
	
	
	/**
	 * 根据卡号码查询学生
	 * 
	 * @param loginname
	 * @param org_id
	 * @param mobile
	 * @param pages
	 */
	public abstract Paging<OrgUser> getOrgStudentByCard(String card, String org_id, 
			Paging<OrgUser> pages);

}
