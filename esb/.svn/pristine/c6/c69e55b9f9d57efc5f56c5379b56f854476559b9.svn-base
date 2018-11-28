package cn.edugate.esb.dao;

import java.util.List;

import cn.edugate.esb.entity.Grade;


/**
 * 年级DAO接口
 * Title:IGradeDao
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月22日上午9:13:02
 */
public interface IGradeDao extends BaseDAO<Grade>{
	/**
	 * 获取年级列表
	 * @param grade
	 * @return
	 */
	public abstract List<Grade> getAllList(Grade grade);
	
	public abstract int saveGrade(Grade grade);
	
	public abstract List<Grade> getGradeList(int orgID);
	
	public abstract List<Grade> getGradeList4AttnMachine(int orgID);

	public abstract Grade getByName(Integer org_id, String grade_name);
	
	public abstract List<Grade> getGradeListIncludeDeleted(int org_id);
	
	public abstract Grade getGrade(int org_id, int gradeType, int gradeNum);
	

	public abstract Grade getGrade(int org_id, String gradeName);
	
} 
