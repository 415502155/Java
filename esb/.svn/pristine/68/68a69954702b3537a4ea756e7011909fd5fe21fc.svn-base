package cn.edugate.esb.service;

import java.util.List;

import cn.edugate.esb.entity.Parent;
import cn.edugate.esb.entity.StudentParent;

public interface ParentService {

	List<Parent> getAll();

	void addParent(Parent parent);

	void addStudentParent(StudentParent sp);
	
	void updateStudentParent(StudentParent sp);

	Parent getByMobile(String mobile, Integer org_id);

	void removeStudentParent(int stud2parent_id);
	
	int getCountStudentParent(int stud_id,int parent_id);
	
	int checkParentPhone(String phone,String orgId,String parent_id);
	
	StudentParent getStudentParent(Integer stud_id, Integer parent_id);
	
	public abstract Parent getParentByUserID(int orgID, int userID);
	
	public abstract void updateParent(Parent p);

	/**
	 * 根据学生主键获取所有家长
	 * @param stud_id
	 * @return
	 */
	List<Parent> getByStudId(String stud_id);

	/**
	 * 根据机构主键查询家长
	 * @param parseInt
	 * @return
	 */
	List<Parent> getParentByOrgId(int parseInt);
}
