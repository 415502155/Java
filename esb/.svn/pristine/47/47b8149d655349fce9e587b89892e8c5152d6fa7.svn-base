package cn.edugate.esb.service;

import java.util.List;

import cn.edugate.esb.entity.Grade;
import cn.edugate.esb.entity.Grade2Clas;

/**
 * 年级服务接口 Title:GradeService Description: Company:SJWY
 * 
 * @author:Liuy
 * @Date:2017年5月22日上午9:21:36
 */
public interface GradeService {

	/**
	 * 创建新年级
	 * 
	 * @param grade
	 */
	public abstract void add(Grade grade);

	/**
	 * 删除年级
	 * 
	 * @param gradeId
	 * @return
	 */
	public abstract Integer delete(Grade grade);

	/**
	 * 更新年级
	 * 
	 * @param grade
	 * @return
	 */
	public abstract Grade update(Grade grade);

	/**
	 * 根据年级主键获取年级
	 * 
	 * @param gradeId
	 * @return
	 */
	public abstract Grade getById(Integer gradeId);

	/**
	 * 获取全部年级列表
	 * 
	 * @param grade
	 * @return
	 */
	public abstract List<Grade> getAllList(Grade grade);

	/**
	 * 无条件的查询全部数据(For Redis)
	 * 
	 * @return
	 */
	public abstract List<Grade> getAll();

	/**
	 * 根据年级名称查找年级
	 * 
	 * @param org_id
	 * @param grade_name
	 * @return
	 */
	public abstract Grade getByName(Integer org_id, String grade_name);

	public abstract Grade2Clas getGrade2Clas(Integer grade_id, Integer clas_id);

	public abstract void addGrade2Clas(Grade2Clas gc);
	
	public abstract List<Grade> getGradeList(int org_id);

	public abstract List<Grade> getGradeList4AttnMachine(int org_id);

	public abstract List<Grade2Clas> getAllGrade2Clas();
}
