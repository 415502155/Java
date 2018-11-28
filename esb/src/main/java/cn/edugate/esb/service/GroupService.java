package cn.edugate.esb.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import cn.edugate.esb.entity.Group;
import cn.edugate.esb.util.Paging;

/**
 * 组服务接口
 * Title:GroupService
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月22日上午9:21:36
 */
public interface GroupService {
	/**
	 * 创建新组
	 * @param group
	 */
	public abstract void add(Group group);
	/**
	 * 删除组
	 * @param groupId
	 * @return
	 */
	public abstract Integer delete(Integer groupId);
	/**
	 * 更新组
	 * @param group
	 * @return
	 */
	public abstract Group update(Group group);
	/**
	 * 根据组主键获取组
	 * @param groupId
	 * @return
	 */
	public abstract Group getById(Integer groupId);
	/**
	 * 获取全部组列表
	 * @param group
	 * @return
	 */
	public abstract List<Group> getAllList(Group group);
	/**
	 * 获取全部组列表(带分页)
	 * @param group
	 * @return
	 */
	public abstract Paging<Group> getAllList(Group group,Paging<Group> paging);
	/**
	 * 根据当前用户，获得有查看权限的所有组列表
	 * @param currentUserId
	 * @return
	 */
	public abstract List<Group> getGroupListByUserID(Integer currentUserId);
	/**
	 * 根据当前用户，获得有查看权限的所有组列表(带分页)
	 * @param currentUserId
	 * @return
	 */
	public abstract Paging<Group> getGroupListByUserID(Integer currentUserId,Paging<Group> paging);
	/**
	 * 无条件的查询全部数据(For Redis)
	 * @return
	 */
	public abstract List<Group> getAll();
	/**
	 * 为BaseUI提供组列表查询接口
	 * @param orgId
	 * @param groupType
	 * @return
	 */
	public abstract List<Object> getGroupList(Integer orgId,Integer groupType);
	public abstract void saveGroup(Group group);
	/**
	 *  查询附带组成员信息的组列表
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
	/**
	 * 查询附带组成员信息的组列表
	 * @param user_ids
	 * @return
	 */
	public abstract List<Object> getGroupsWithMembers(String user_ids);
	/**
	 * 查询机构下 教师信息及所在组
	 * @param 
	 * @return
	 */
	public abstract  List<Map<String, String>> getTechGroupsOfOrg(int org_id);
	public abstract Map<String,Object> batchTechGroup(int org_id,MultipartFile multiFile);
	
	public List<String[]> validateBatchTechGroup(Integer orgID,List<String[]> excelRowArray);
}
