package cn.edugate.esb.dao;

import java.util.List;
import java.util.Map;

import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.entity.TechRange;
import cn.edugate.esb.util.Paging;

public interface ITeacherDao extends BaseDAO<Teacher> {

	List<Teacher> getTeacherSearch(Integer org_id, String sname);

	List<Teacher> getAll();
	
	List<Teacher> getTeacherListIncludeDeleted(int org_id);

	Teacher getByUserId(Integer user_id);

	int getTotalCount(int org_id, int type, String seach_name);

	void getPaging(int org_id, int type, String seach_name, Paging<Teacher> paging);

	Teacher getTechAndUserId(int tech_id);

	Long getTotalCount(String org_id, String role_id, String tech_name, String user_mobile);

	void getPaging(String org_id, String role_id, String tech_name, String user_mobile, Paging<Teacher> pages);

	/**
	 * 查询机构树下的教师，按机构部门分组，带组成员信息
	 * 
	 * @param org_id
	 * @param group_id
	 * @return
	 */
	List<Object> getOrgTreeTeacherMembers(String org_id, String group_id);
	
	
	public String saveTeacher();
	
	
	public boolean getUserIsExist(String loginName,String phone, int identity, int org_id);

	List<Object> getTeacherMembers(String orgId, String groupId);
	
	List<Map<String,Object>> getTechsOfRlId(String org_id,Integer code);

	/**
	 * 获取通讯录
	 * @param org_id
	 * @return
	 */
	List<Teacher> getPhoneBook(Integer org_id);

	/**
	 * 获取机构下所有教师
	 * @param org_id
	 * @return
	 */
	List<Teacher> getTeachersByOrgId(Integer org_id);
	
	/**
	 * 获得所有教师（授课、班主任、年级组长）范围
	 * @param org_id
	 * @return
	 */
	List<TechRange> getTechRanges(Integer org_id);
	
	 List<Map<String,String>> getTechsOforg(int org_id);
}
