package cn.edugate.esb.dao;

import java.util.List;

import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.UcUser;
import cn.edugate.esb.entity.UcuserOrguser;
import cn.edugate.esb.util.Paging;
/**
 * 用户DAO接口
 * Title:IUserDao
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年4月24日下午2:23:39
 */
public interface IUcUserDao extends BaseDAO<UcUser>{
	/**
	 * 根据角色主键获取所有用户
	 * @param roleID
	 * @return
	 */
	public abstract List<UcUser> getAllByRoleId(Integer roleID);
	/**
	 * 根据角色主键获取所有用户(带分页)
	 * @param roleID
	 * @return
	 */
	public abstract Paging<UcUser> getAllByRoleIdWithPaging(Paging<UcUser> paging,String name,
			String mobile,String ssoType,String mail,String rid,String orgType,String udid);
	/**
	 * 获取当前条件下的用户列表(带分页)
	 * @param criterion 查询条件
	 * @param orders 排序条件
	 * @return
	 */
	public abstract Paging<UcUser> getAllWithPaging(Paging<UcUser> paging, String name,
			String mobile, String ssoType, String mail,String orgType,String udid);
	/**
	 * 通过用户名获取用户
	 * @param login_name
	 * @param org_type 
	 * @return
	 */
	public abstract UcUser getUserByLoginName(String login_name, Integer org_type);
	/**
	 * 获取所有用户
	 * @return
	 */
	public abstract List<UcUser> getUserList();
	
	/**
	 * 获取所有用户
	 * @return
	 */
	public abstract List<UcuserOrguser> getOrgByUCID(String uc_id);
	/***
	 * 获取当前机构的管理员用户
	 * @param org_id
	 */
	public abstract OrgUser getUserForManager(Integer org_id);

}
