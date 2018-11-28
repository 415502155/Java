package cn.edugate.esb.redis.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import cn.edugate.esb.entity.GroupMember;
import cn.edugate.esb.entity.Student;
import cn.edugate.esb.entity.Teacher;

/**
 * 组成员DAO接口
 * Title:RedisGroupMemberDao
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月22日上午10:28:42
 */
public interface RedisGroupMemberDao {
	/**
	 * 添加组成员
	 * @param groupMember
	 * @return
	 */
	boolean addGroupMember(GroupMember groupMember);
	/**
	 * 添加全部数据(For Redis)
	 * @param groupMembers
	 */
	boolean addGroupMembers(List<GroupMember> groupMembers);
	/**
	 * 删除组成员
	 * @param groupMember
	 * @return
	 */
	boolean deleteGroupMember(GroupMember groupMember);
	/**
	 * 无条件的删除全部数据(For Redis)
	 */
	boolean deleteAll();
	/**
	 * 根据主键获取组成员
	 * @param GroupMemberId
	 * @return
	 */
	GroupMember getGroupMemberById(Integer GroupMemberId,boolean needOrgInfo);
	/**
	 * 根据主键获取学生组成员
	 * @param GroupMemberId
	 * @return
	 */
	Map<Integer,GroupMember> getStudentGroupMembersById(String groupId);
	/**
	 * 根据查询条件获取组成员
	 * @param orgId
	 * @return
	 */
	Map<String, Object> getMembers(Integer groupId,boolean needOrgInfo);
	/**
	 * 查询学生组成员
	 * @param groupId
	 * @return
	 */
	Map<String, Student> getStudentMembers(String groupId);
	/**
	 * 查询教师组成员
	 * @param groupId
	 * @return
	 */
	LinkedHashMap<String, Teacher> getTeacherMembers(String groupId);
	
	
	/**
	 * 通过人员ID 查询所在的组
	 * @param groupId
	 * @return
	 */
	//List<GroupMember> getGroupMemberByID(int mem_id);
	
	
}
