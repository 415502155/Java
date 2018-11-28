package cn.edugate.esb.dao;

import java.util.List;
import java.util.Map;

import cn.edugate.esb.entity.Group;
import cn.edugate.esb.util.Paging;

/**
 * 组DAO接口
 * Title:IGroupDao
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月22日上午9:13:02
 */
public interface IGroupDao extends BaseDAO<Group> {

	/**
	 * 获取组列表
	 * 
	 * @param group
	 * @return
	 */
	public abstract List<Group> getAllList(Group group);

	/**
	 * 获取组列表(带分页)
	 * 
	 * @param group
	 * @return
	 */
	public abstract Paging<Group> getAllList(Group group, Paging<Group> paging);

	/**
	 * 根据当前用户，获得有查看权限的所有组列表
	 * 
	 * @param currentUserId
	 * @return
	 */
	public abstract List<Group> getGroupListByUserID(Integer currentUserId);

	/**
	 * 根据当前用户，获得有查看权限的所有组列表(带分页)
	 * 
	 * @param currentUserId
	 * @return
	 */
	public abstract Paging<Group> getGroupListByUserID(Integer currentUserId, Paging<Group> paging);

	/**
	 * 为BaseUI提供组列表接口
	 */
	public abstract List<Object> getGroupList(Integer orgId, Integer groupType);
	
	public abstract int checkName(int orgID, String groupName);
	
	public abstract Group getGroupEntity(int orgID, String name);

	/**
	 * 查询包括组成员在内的组列表
	 * @param orgId
	 * @param groupType
	 * @param memberId
	 * @param type
	 * @return
	 */
	public abstract List<Object> getGroupsWithMembers(Integer orgId, Integer groupType, Integer memberId, Integer type);

	/**
	 * 查询附带机构简称的组列表（机构简称用于排序）
	 * @param orgId
	 * @param groupType
	 * @param type
	 * @return
	 */
	public abstract List<Object> getGroupsWithoutMembers(Integer orgId, Integer groupType);
	
	public abstract List<Group> getGroupListIncludeDeleted(int org_id);

	/**
	 * 查询包括组成员在内的组列表
	 * @param user_ids
	 * @return
	 */
	public abstract List<Object> getGroupsWithMembers(String user_ids);
	
	/**
	 * 查询机构下 教师信息及所在组
	 * @param 
	 * @return
	 */
	public abstract List<Map<String, String>> getTechGroupsOfOrg(int org_id);
}
