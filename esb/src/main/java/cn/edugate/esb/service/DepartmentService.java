package cn.edugate.esb.service;

import java.util.List;

import cn.edugate.esb.entity.Department;
import cn.edugate.esb.util.Paging;

public interface DepartmentService {
	/**
	 * 添加新的部门
	 * @param function
	 */
	public abstract void add(Department department);
	/**
	 * 根据主键删除部门
	 * @param id
	 * @return
	 */
	public abstract Integer delete(int id);
	/**
	 * 更新部门
	 * @param function
	 * @return
	 */
	public abstract Department update(Department department);
	/**
	 * 根据主键获取部门
	 * @param id
	 * @return
	 */
	public abstract Department getDepById(int id);
	/**
	 * 获得所有部门列表
	 * @param function
	 * @return
	 */
	public abstract List<Department> getAll();
	/**
	 * 获取部门列表（带分页）
	 * @param paging
	 * @param function
	 * @return
	 */
	public abstract Paging<Department> getDepListWithPaging(Paging<Department> paging,Department department,int org_id);
	/**
	 * 通过机构id获取部门
	 * @param org_id
	 * @return
	 */
	public abstract List<Department> getListByOrgId(Integer org_id);
	/**
	 * 根据部门主键获取默认部门的主键
	 * @param depId
	 * @return
	 */
	public abstract Integer getDefaultId(Integer depId);
	/**
	 * 查询机构列表，带管理员和成员信息
	 * @param org_id
	 * @return
	 */
	public abstract List<Department> getDepsDetailList(String org_id);
	
}
