package cn.edugate.esb.dao;

import java.util.List;

import cn.edugate.esb.entity.GroupMember;
import cn.edugate.esb.util.Paging;

/**
 * 组成员DAO接口
 * Title:IGroupMemberDao
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月22日上午9:13:02
 */
public interface IGroupMemberDao extends BaseDAO<GroupMember>{

	/**
	 * 获取组成员列表
	 * @param groupMember
	 * @return
	 */
	public abstract List<GroupMember> getAllList(GroupMember groupMember);
	/**
	 * 获取组成员列表(带分页)
	 * @param groupMember
	 * @return
	 */
	public abstract Paging<GroupMember> getAllList(GroupMember groupMember,Paging<GroupMember> paging);
	/**
	 * 根据组主键删除下属所有成员
	 * @param groupId
	 */
	public abstract void deleteByGroupId(Integer groupId);
	/**
	 * 查询成员列表 
	 * @return
	 */
	public abstract List<GroupMember> getList();
	/**
	 * 查询成员列表 
	 * @return
	 */
	public abstract List<GroupMember> getListOfOrg(Integer org_id);
	/**
	 * 根据成员主键及类型删除组成员
	 * @param mem_id 可以多个，用逗号连接
	 * @param mem_type
	 */
	public abstract void deleteByMemberId(String mem_ids, Integer mem_type);
	/**
	 * 增加小组成员
	 * @param group_id
	 * @param mem_ids
	 */
	public abstract void addGroupMembers(Integer group_id, String mem_ids,Integer mem_type);
	
	/**
	 * 更新组内成员
	 * @param group_id
	 * @param mem_ids
	 * @param mem_type
	 */
	public abstract void updateGroupMembers(Integer group_id, String mem_ids, Integer group_type);
	
	/**
	 * 删除小组成员
	 * @param group_id
	 * @param mem_ids
	 */
	public abstract void deleteGroupMembers(Integer group_id, String mem_ids,Integer mem_type);
	
	public abstract int getGroupMemberCount(int groupID, int type, int memberID);
	
	public abstract List<GroupMember> getGroupMemberListIncludeDeleted(int org_id);
	
	public abstract List<GroupMember> getGroupMemberByMid(int mem_id);
	
	/**
	 * 查询机构下班级里的学生
	 * @param orgId
	 * @param classId
	 * @return
	 */
	public abstract List<GroupMember> getGroupMemberListByClassId(int orgId, int classId);
} 
