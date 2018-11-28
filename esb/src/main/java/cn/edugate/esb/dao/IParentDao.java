package cn.edugate.esb.dao;

import java.util.List;

import cn.edugate.esb.entity.Parent;
import cn.edugate.esb.entity.StudentParent;

public interface IParentDao extends BaseDAO<Parent>{

	List<Parent> getByMobile(String mobile, Integer org_id);

	List<Parent> getParents(Integer stud_id,Integer org_id);

	int getCountStudentParent(int stud_id,int parent_id);
	
	int checkParentPhone(String phone, String orgId, String parent_id);
	
	StudentParent getStudentParent(Integer stud_id, Integer parent_id);

	public abstract Parent getParentByUserID(int orgID, int userID);

	/**
	 * 获取学生的所有家长
	 * @param stud_id
	 * @return
	 */
	List<Parent> getParentsByStudId(String stud_id);
	
	public abstract List<Parent> getParentListIncludeDeleted(int org_id);
}
