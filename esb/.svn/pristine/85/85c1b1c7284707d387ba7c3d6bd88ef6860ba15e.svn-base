package cn.edugate.esb.service;

import java.util.List;

import cn.edugate.esb.entity.GroupMember;
import cn.edugate.esb.util.Paging;

/**
 * 组成员服务接口
 * Title:GroupMemberService
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月22日上午9:21:36
 */
public interface GroupMemberService {
	/**
	 * 创建新组成员【注意】调用时需要为orgId赋值
	 * @param groupMember
	 */
	public abstract void add(GroupMember groupMember);
	/**
	 * 删除组成员
	 * @param groupMemberId
	 * @return
	 */
	public abstract Integer delete(Integer groupMemberId);
	/**
	 * 根据组成员主键获取组成员
	 * @param groupMemberId
	 * @return
	 */
	public abstract GroupMember getById(Integer groupMemberId);
	/**
	 * 获取全部组成员列表
	 * @param groupMember
	 * @return
	 */
	public abstract List<GroupMember> getAllList(GroupMember groupMember);
	/**
	 * 获取全部组成员列表(带分页)
	 * @param groupMember
	 * @return
	 */
	public abstract Paging<GroupMember> getAllList(GroupMember groupMember,Paging<GroupMember> paging);
	/**
	 * 查询全部(For Redis)
	 * @return
	 */
	public abstract List<GroupMember> getAll();
	
	/**
	 * 查询机构全部(For Redis)
	 * @return
	 */
	public abstract List<GroupMember> getAllOfOrg(Integer orgId);
	/**
	 * 替换某机构下的全体成员
	 * @param groupId
	 * @param types
	 * @param memIds
	 * @param orgId
	 */
	public abstract void replaceMembersOfGroup(Integer groupId, Integer types, String memIds, Integer orgId);
	/**
	 * 根据成员主键删除全部组成员信息
	 * 用于删除学生或删除老师
	 * @param mem_ids 可以多个，用逗号连接
	 * @param mem_type
	 */
	public abstract void deleteByMemberId(String mem_ids,Integer mem_type);
	/**
	 * 添加组成员
	 * @param group_id
	 * @param mem_ids
	 * @param mem_type 
	 */
	public abstract void addGroupMembers(Integer group_id, String mem_ids, Integer mem_type);
	
	/**
	 * 更新组内成员
	 * @param group_id
	 * @param mem_ids
	 * @param mem_type
	 */
	public abstract void updateGroupMembers(Integer group_id, String mem_ids, Integer group_type);
	
	/**
	 * 删除组成员
	 * @param group_id
	 * @param mem_ids
	 * @param mem_type 
	 */
	public abstract void deleteGroupMembers(Integer group_id, String mem_ids, Integer mem_type);
	
}
