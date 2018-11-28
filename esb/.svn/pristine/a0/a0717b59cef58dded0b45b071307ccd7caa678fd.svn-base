package cn.edugate.esb.dao;

import java.util.List;

import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.util.Paging;

/**
 * 用户DAO接口 Title:IUserDao Description: Company:SJWY
 * 
 * @author:Liuy
 * @Date:2017年4月24日下午2:23:39
 */
public interface IOrgUserDao extends BaseDAO<OrgUser> {

	/**
	 * 根据角色主键获取所有用户
	 * 
	 * @param roleID
	 * @return
	 */
	public abstract List<OrgUser> getAllByRoleId(Integer roleID);

	/**
	 * 根据角色主键获取所有用户(带分页)
	 * 
	 * @param roleID
	 * @return
	 */
	public abstract Paging<OrgUser> getAllByRoleIdWithPaging(Paging<OrgUser> paging, String name, String mobile, String ssoType,
			String mail, String rid, String orgType, String udid);

	/**
	 * 获取当前条件下的用户列表(带分页)
	 * 
	 * @param criterion 查询条件
	 * @param orders 排序条件
	 * @return
	 */
	public abstract Paging<OrgUser> getAllWithPaging(Paging<OrgUser> paging, String name, String mobile, String ssoType,
			String mail, String orgType, String udid);

	/**
	 * 通过用户名获取用户
	 * 
	 * @param login_name
	 * @param org_type
	 * @return
	 */
	public abstract OrgUser getUserByLoginName(String login_name, Integer org_id);

	/**
	 * 获取所有用户
	 * 
	 * @return
	 */
	public abstract List<OrgUser> getList();

	/**
	 * 获取查询总数
	 * 
	 * @param loginname
	 * @param org_id
	 * @param mobile
	 * @param identity
	 * @return
	 */
	public abstract Long getTotalCount(String loginname, String org_id, String mobile, Integer identity);

	/**
	 * 获取机构用户分页查询
	 * 
	 * @param loginname
	 * @param org_id
	 * @param mobile
	 * @param pages
	 */
	public abstract void getOrgUserWithPaging(String loginname, String org_id, String mobile, Integer identity,
			Paging<OrgUser> pages);

	/**
	 * 通过手机号获取机构用户
	 * 
	 * @param phone
	 * @param org_id
	 * @return
	 */
	public abstract List<OrgUser> getListByPhone(String phone, Integer org_id);

	/**
	 * 通过机构、身份和手机号确定
	 * 
	 * @param orgID
	 * @param identity
	 * @param mobile
	 * @return
	 */
	public abstract OrgUser getOrgUser(int orgID, int identity, String mobile);

	/**
	 * 获取登录名是否存在
	 * 
	 * @param loginName
	 * @param identity
	 * @param org_id
	 * @return
	 */
	public abstract boolean getUserIsExist(String loginName, Integer identity, Integer org_id);

	/**
	 * 批量删除机构用户
	 * 
	 * @param list
	 */
	public abstract void deleteOrgUser(List<OrgUser> list);

	public abstract OrgUser getAdminByOrgid(Integer org_id);

	/**
	 * 根据家长手机号码查询学生信息
	 * 
	 * @param loginname
	 * @param org_id
	 * @param mobile
	 * @param identity
	 * @param pages
	 * @return
	 */
	public abstract Paging<OrgUser> getOrgStudentByParentWithPaging(String card, String org_id, String mobile,
			Paging<OrgUser> pages);
	
	public abstract List<OrgUser> getOrgUserListIncludeDeleted(int org_id);

	/**
	 * 根据机构主键查询机构用户
	 * @param org_id
	 * @return
	 */
	public abstract List<OrgUser> getOrguserByOrgId(int org_id);
	
	
	 Paging<OrgUser> getOrgStudentByCard(String card, String org_id,Paging<OrgUser> pages);
	
}
