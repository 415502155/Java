package cn.edugate.esb.service;

import java.util.List;
import java.util.Map;

import cn.edugate.esb.entity.TechRange;
import cn.edugate.esb.util.Paging;

/**
 * 教师范围Service接口 Title:TechRangeService Description: Company:SJWY
 * 
 * @author:Huangcf
 * @Date:2017年5月27日下午1:40:18
 */
public interface TechRangeService {

	void getAdminPaging(Paging<TechRange> paging, String searchName);

	String getAdminOrgsByTechId(Integer tech_id);

	String getHeadmasterOrgsByTechId(Integer tech_id);

	/**
	 * 创建教师范围
	 * 
	 * @param org_id 机构主键
	 * @param tech_id 教师主键
	 * @param rl_id 标签主键
	 * @param clas_id 班级主键(多个班级时用逗号连接)
	 * @param grade_id 年级主键(多个年级时用逗号连接)
	 * @param group_id 组主键(多个组时用逗号连接)
	 * @param dep_id 部门主键(多个部门时用逗号连接)
	 * @param cour_id 课程主键(多个课程时用逗号连接,此时年级班级必须唯一)
	 */
	List<TechRange> createRange(Integer org_id, Integer tech_id, Integer rl_id, String clas_id, String grade_id, String group_id,
			String dep_id, String cour_id);

	/**
	 * 创建教师范围
	 * 
	 * @param org_id 机构主键
	 * @param tech_id 教师主键
	 * @param rl_id 标签主键
	 * @param clas_id 班级主键(多个班级时用逗号连接)
	 * @param grade_id 年级主键(多个年级时用逗号连接)
	 * @param group_id 组主键(多个组时用逗号连接)
	 * @param dep_id 部门主键(多个部门时用逗号连接)
	 * @param cour_id 课程主键(多个课程时用逗号连接,此时年级班级必须唯一)
	 */
	List<TechRange> createRange(String org_id, String tech_id, String rl_id, String clas_id, String grade_id, String group_id,
			String dep_id, String cour_id);

	List<Map<String, Object>> getTeaching(String org_id, String tech_id, String grade_id, String cour_id, String clas_id);

	/**
	 * 删除范围
	 * 
	 * @param techRange
	 */
	void deleteRange(TechRange techRange);

	/**
	 * 根据参数类型，删除旧有的教师范围数据
	 */
	void deleteOldTeachRange(String org_id, String dep_id, String grade_id, String clas_id, String cour_id, String group_id,
			String group_type);

	/**
	 * 根据参数类型，替换原有教师范围数据，添加新的教师范围数据
	 */
	void replaceRange(String tech_ids, String org_id, String dep_id, String grade_id, String clas_id, String cour_id,
			String group_id, String group_type);

	/**
	 * 更新班主任，并为班主任设置课程
	 * 
	 * @param org_id
	 * @param clas_id
	 * @param tech_ids
	 */
	public abstract void updateBZRTeacher(String org_id, String grade_id, String clas_id, String tech_ids);

	/**
	 * 获得原有的记录
	 */
	TechRange[] getOldTechRange(String org_id, String rl_id, String clas_id, String grade_id, String dep_id, String group_id,
			String cour_id);

	void replaceRangeSK(String org_id, String tech_id, String cour_ids, List<TechRange> ranges);
	
	/**
	 * 获得所有教师范围
	 * 
	 * @param function
	 * @return
	 */
	public abstract List<TechRange> getTechRangesByOrg(int org_id);
}
