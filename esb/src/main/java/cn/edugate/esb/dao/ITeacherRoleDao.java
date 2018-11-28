package cn.edugate.esb.dao;

import java.util.List;

import cn.edugate.esb.entity.TeacherRole;

public interface ITeacherRoleDao extends BaseDAO<TeacherRole>{
	TeacherRole getTeachRole(Integer role_id, Integer tech_id);
	
	 List<TeacherRole> getTeacherRolesOfSql();
	 
	 public abstract List<TeacherRole> getTeacherRoleListIncludeDeleted(int org_id);
	 
	 List<TeacherRole> getTeachRoles(Integer user_id);
	 
	 List<TeacherRole> getTeacherRoleByOrg(int org_id);
}
