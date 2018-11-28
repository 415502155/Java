package cn.edugate.esb.dao;

import java.util.List;

import cn.edugate.esb.entity.Role;
import cn.edugate.esb.util.Paging;

/**
 * 角色DAO接口 Title:IRoleDao Description: Company:SJWY
 * 
 * @author:Liuy
 * @Date:2017年4月24日上午8:37:32
 */
public interface IRoleDao extends BaseDAO<Role> {

	/**
	 * 获取带分页的角色列表
	 * 
	 * @param roleName 角色名称
	 * @return
	 */
	public abstract Paging<Role> getAllWithPaging(Paging<Role> paging, String roleName);

	/**
	 * 获取用户所有的权限
	 * 
	 * @param userId 用户主键
	 * @return
	 */
	public abstract List<Role> getByUserId(Integer userId, Integer orgId);

	/**
	 * 获取机构下的角色集合
	 * 
	 * @param orgID
	 * @return
	 */
	public abstract List<Role> getRoleListByOrgID(int orgID);

	/**
	 * 获取用户所有的权限(带分页)
	 * 
	 * @param userId 用户主键
	 * @return
	 */
	public abstract Paging<Role> getByUserIdWithPaging(Integer userId, Paging<Role> paging);

	/**
	 * 获取管理员权限主键集合
	 * 
	 * @return
	 */
	public abstract String createManagerRole(Integer orgId, Integer orgType);

	/**
	 * 获取机构下所有的可用权限编码
	 * 
	 * @param org_id
	 * @return
	 */
	public abstract List<Object> getOrgRoleCode(Integer org_id);

	/**
	 * 获取机构下已选的权限
	 * 
	 * @param org_id
	 * @return
	 */
	public abstract List<Object> getOrgRole(Integer org_id);

	/**
	 * 根据机构ID和角色名称查询
	 * 
	 * @param orgID
	 * @param roleName
	 * @return
	 */
	public abstract Role getRole(int orgID, String roleName);

}
