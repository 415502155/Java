package cn.edugate.esb.dao;

import java.util.List;
import java.util.Map;

import cn.edugate.esb.entity.TechRange;

public interface ITechRangeDao extends BaseDAO<TechRange> {

	String getAdminOrgsByTechId(Integer tech_id);

	String getHeadmasterOrgsByTechId(Integer tech_id);

	List<Map<String, Object>> getTeaching(String org_id, String tech_id, String grade_id, String cour_id, String clas_id);

	/**
	 * 删除范围
	 * 
	 * @param list
	 */
	void deleteRange(TechRange techRange);

	/**
	 * 根据参数类型，删除旧有的教师范围数据
	 * 
	 * @param org_id
	 * @param dep_id
	 * @param grade_id
	 * @param clas_id
	 * @param cour_id
	 */
	void deleteOldTeachRange(String org_id, String dep_id, String grade_id, String clas_id, String cour_id, String group_id,
			String group_type);

	/**
	 * 获取原有数据
	 * 
	 * @param org_id
	 * @param rl_id
	 * @param clas_id
	 * @param grade_id
	 * @param dep_id
	 * @param group_id
	 * @param cour_id
	 * @return
	 */
	TechRange[] getOldTechRange(String org_id, String rl_id, String clas_id, String grade_id, String dep_id, String group_id,
			String cour_id);
	
	public abstract List<TechRange> getTechRangeListByClassId(int classId);
	
	/**
	 * 根据机构，教师id，角色标签，班级ID， 和课程id查询教师在某班级的某课程记录是否已存在
	 * @param org_id
	 * @param tech_id
	 * @param rl_id
	 * @param clas_id
	 * @param cour_id
	 * @return
	 */
	public abstract int checkIsExist(String org_id, String tech_id, int rl_id, String grade_id, String clas_id, int cour_id);
	
	/**
	 * 获取机构中所有的tech_range记录（包含已被删除的）
	 * @param org_id
	 * @return
	 */
	public abstract List<TechRange> getTechRangeListIncludeDeleted(int org_id);
	
	/**
	 * 获取机构中所有的tech_range记录（不包含已被删除的）
	 * @param org_id
	 * @return
	 */
	public abstract List<TechRange> getTechRangeList(int org_id);
	
	/**
	 * 获取机构中，班级相关的所有记录
	 * @param orgId
	 * @param classId
	 * @return
	 */
	public abstract List<TechRange> getTechRangeList(int orgId, int classId);
	
	
	
	/**
	 * 获取  班主任和授课
	 * 
	 * @param org_id
	 * @param rl_id
	 * @param clas_id
	 * @param grade_id
	 * @param dep_id
	 * @param group_id
	 * @param cour_id
	 * @return
	 */
	TechRange[] getOldTechRangeOfClas(String org_id, String clas_id);
	
	/**
	 * 获取任课信息
	 * @param org_id
	 * @param gradeId
	 * @param classId
	 * @return
	 */
	public abstract List<TechRange> getTechRangeList(int org_id, int gradeId, int classId);
	
	/**
	 * 获取教师授课相关的数据范围
	 * @param org_id
	 * @param gradeId
	 * @param classId
	 * @return
	 */
	public List<TechRange> getTechSkRangeListById(int tech_id);
	
	
	/**
	 * 获得所有教师范围
	 * 
	 * @param function
	 * @return
	 */
	public List<TechRange> getTechRangesByOrg(int org_id);
}
