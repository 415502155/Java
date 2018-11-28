package cn.edugate.esb.service;

import java.util.List;

import cn.edugate.esb.authentication.FPSet;
import cn.edugate.esb.entity.Right;
import cn.edugate.esb.entity.Role;
import cn.edugate.esb.entity.RoleLabel;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.util.Paging;
/**
 * 权限Service接口
 * Title:RoleService
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年4月24日下午1:40:18
 */
public interface RoleService {
	/**
	 * 创建新的角色
	 * @param role 新角色实体
	 */
	public abstract void add(Role role);
	/**
	 * 根据角色主键删除角色信息
	 * @param roleId 要删除的角色主键
	 * @return 删除的条数
	 */
	public abstract Integer deleteById(Integer roleId);
	/**
	 * 更新角色
	 * @param role 要跟新的角色实体
	 * @return 更新的条数
	 */
	public abstract Integer update(Role role);
	/**
	 * 根据主键获取角色
	 * @param role 角色主键
	 * @return Role实体
	 */
	public abstract Role getById(Integer roleId);
	/**
	 * 获取角色列表
	 * @param role 含有查询条件的角色实体
	 * @return
	 */
	public abstract List<Role> getAll();
	
	public abstract List<Role> getRoleListByOrgID(int orgID);
	
	/**
	 * 获取角色列表(带分页)
	 * @param roleName 角色名称
	 * @return
	 */
	public abstract Paging<Role> getAllWithPaging(Paging<Role> paging,String roleName);
	/**
	 * 获取用户所有的权限
	 * @param userId 用户主键
	 * @return
	 */
	public abstract List<Role> getByUserId(Integer userId,Integer org_id);
	/**
	 * 获取用户所有的权限(带分页)
	 * @param userId 用户主键
	 * @return
	 */
	public abstract Paging<Role> getByUserIdWithPaging(Paging<Role> paging,Integer userId);
	/**
	 * 检查角色名是否可用
	 * @param roleName 角色名
	 * @param id 角色主键
	 * @return 是否可用
	 */
	public abstract boolean checkRoleName(String roleName,String id);
	
	public abstract List<FPSet> getFPSetsByRole(Integer roleId);
	
	public abstract FPSet getFPSetByRole(Integer roleId, String authority);
	
	public abstract void setRoleAuthority(Integer roleId, String authority,
			int code);
	
	public abstract void getAllRightWithPaging(Paging<Right> paging,
			String searchName);
	
	public abstract void addright(Right right);
	
	public abstract Right getRightById(Integer rid);
	/**
	 * 获取角色标签
	 * @param type 
	 * @return
	 */
	public abstract List<RoleLabel> getRoleLabels(Integer type);
	/**
	 * 根据机构获取角色集合
	 * @param org_id
	 * @return
	 */
	public abstract List<Role> getByOrgId(Integer org_id);
	/**
	 * 通过主键获取权限
	 * @param role_id
	 * @return
	 */
	public abstract Role getRoleById(Integer role_id);
	/**
	 * 删除权限
	 * @param role
	 */
	public abstract void removeRole(Role role);
	/**
	 * 获取管理员权限主键
	 * @return
	 */
	public abstract String createManagerRole(Integer orgId,Integer orgType);
	/**
	 * 获取机构下所有的可用权限编码
	 * @param org_id
	 * @return
	 */
	public abstract List<Object> getOrgRoleCode(Integer org_id);
	/**
	 * 获取机构下已选的权限
	 * @param org_id
	 * @return
	 */
	public abstract List<Object> getOrgRole(Integer org_id);
	/**
	 * 通过角色名称获取
	 * @param role_name
	 * @param org_id
	 * @return
	 */
	public abstract List<Role> getByRoleName(String role_name, Integer org_id);
	/**
	 * 通过角色id获取所有老师
	 * @param roleId
	 * @return
	 */
	public abstract List<Teacher> getTeachersByRoleId(Integer roleId);
	
	
	
}
