package cn.edugate.esb.dao;

import java.util.List;

import cn.edugate.esb.entity.Classes;
import cn.edugate.esb.util.Paging;

/**
 * 班级DAO接口
 * Title:IClassesDao
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月25日下午1:27:14
 */
public interface IClassesDao extends BaseDAO<Classes>{
	/**
	 * 获取班级列表
	 * @param classes
	 * @return
	 */
	public abstract List<Classes> getClasses(int orgId,int camId,int isGraduated,int gradeNumber);

	public abstract Long getTotalCount(Integer org_id,Integer is_graduated, Integer cam_id,
			Integer grade_id);

	public abstract void getPaging(Integer org_id, Integer is_graduated,
			Integer cam_id, Integer grade_id, Paging<Classes> pages);

	public abstract List<Classes> getClassByOrgIdCamIdGradeId(Integer org_id,
			Integer cam_id, Integer grade_id);

	public abstract List<Classes> queryClassesByStudId(Integer stud_id, Integer org_id);

	public abstract Classes getClassByName(Integer grade_id, String clas_name);

	/**
	 * 查询机构下班级
	 * @param org_id
	 * @return
	 */
	public abstract List<Classes> getClassesOfOrg(Integer org_id);
	
	
	public abstract List<Classes> getClassListIncludeDeleted(int org_id);
	
	/**
	 * 查询机构下班级
	 * @param org_id
	 * @return
	 */
	public abstract List<Classes> getClassesOfOrgForRedis(Integer org_id);
	
	/**
	 * 获取机构下未删除的所有班级（班级升级用，因此需要包含已毕业的班级）
	 * @param org_id
	 * @return
	 */
	public abstract List<Classes> getClassList4ClassUpgrade(int org_id);
	
	
	public abstract Classes getClass(int org_id,String clas_name);
	
	public abstract Classes getClassByNameOrg(Integer org_id,String clas_name);
	
} 
