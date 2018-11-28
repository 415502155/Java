package cn.edugate.esb.service;

import java.util.List;

import cn.edugate.esb.entity.TeacherRole;
/**
 * 角色权限关系Service接口
 * Title:RoleService
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年4月24日下午1:40:18
 */
public interface TeacherRoleService {
	/**
	 * 更新用户角色关系
	 * @param list 用户角色关系列表
	 */
	public void saveOrUpdateUser2Role(List<TeacherRole> user2roleArray);
	/**
	 * 根据用户主键删除关系
	 * @param UserId
	 */
	public void deleteByUserId(Integer userId);
	
	public TeacherRole getTeachRole(Integer role_id, Integer tech_id);
	/**
	 * 更新用户角色
	 * @param tech_id
	 * @param ids
	 */
	public void saveOrUpdateUser2Role(Integer user_id, String[] ids);
	/**
	 * 移除角色关系
	 * @param trlist
	 */
	public void removetr(TeacherRole... trlist);
	/**
	 * 获取角色所有关系
	 * @param role_id
	 * @return
	 */
	public List<TeacherRole> getTeachRoleByRoleId(Integer role_id);
	
	
	public List<TeacherRole> getTeachRoleByOrg(Integer org_id);
}
