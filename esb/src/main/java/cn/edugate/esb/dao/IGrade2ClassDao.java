package cn.edugate.esb.dao;

import java.util.List;

import cn.edugate.esb.entity.Grade2Clas;

/**
 * 年级班级关系DAO接口
 * Title:IGrade2ClasDao
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月22日上午9:13:02
 */
public interface IGrade2ClassDao extends BaseDAO<Grade2Clas>{

	/**
	 * 获取年级班级关系列表
	 * @param grade2clas
	 * @return
	 */
	public abstract List<Grade2Clas> getAllList(Grade2Clas grade2clas);

	public abstract Grade2Clas getGrade2Clas(Integer grade_id, Integer clas_id);
	
	public abstract List<Grade2Clas> getGrade2ClassListByClassId(int classId);
	
	public abstract List<Grade2Clas> getGrade2ClassListIncludeDeleted(int org_id);
	
	public abstract List<Grade2Clas> getDirtyRecords(int gradeId, int classId);
	
} 
