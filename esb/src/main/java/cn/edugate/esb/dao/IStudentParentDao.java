package cn.edugate.esb.dao;

import java.util.List;

import cn.edugate.esb.entity.StudentParent;

public interface IStudentParentDao extends BaseDAO<StudentParent>{

	public abstract List<StudentParent> getStudentParentListByStudentId(int studentId);
	
	public abstract List<StudentParent> getStudentParentListIncludeDeleted(int org_id);
}
