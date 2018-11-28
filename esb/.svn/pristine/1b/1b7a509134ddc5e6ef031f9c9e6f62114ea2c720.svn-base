package cn.edugate.esb.dao;

import java.util.List;

import cn.edugate.esb.entity.Department;

public interface DepartmentDao  extends BaseDAO<Department>{

	/**
	 * 根据部门主键获取默认部门的主键
	 * @param depId
	 */
	Integer getDefaultId(Integer depId);
	
	public abstract int saveDefaultDP(Department dp);

	/**
	 * 获取部门列表，带管理员和人数信息
	 * @param org_id
	 * @return
	 */
	List<Department> getDepsDetailList(String org_id);
	
	public abstract int checkName(int orgID, String depName);
	
	public abstract Department getDefaultDeptEntity(int orgID);
	
	public abstract Department getNonDefaultDeptEntity(int orgID, String depName);
}
