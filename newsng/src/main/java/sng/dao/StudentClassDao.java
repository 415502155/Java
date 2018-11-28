package sng.dao;

import sng.entity.StudentClass;

/**
 * @类 名： StudentClassDao
 * @功能描述：学生班级关系 
 * @作者信息： LiuYang
 * @创建时间： 2018年11月20日下午2:50:36
 */
public interface StudentClassDao extends BaseDao<StudentClass>{
	
	/**
	 * @Title : getWithNames 
	 * @功能描述:获取带有学生姓名、班级名称的关系对象 
	 * @返回类型：StudentClass 
	 * @throws ：
	 */
	public StudentClass getWithNames(Integer stud_id, Integer clas_id);

	/**
	 * @Title : getInfosById 
	 * @功能描述: 获取学生班级及其关联信息
	 * @返回类型：StudentClass 
	 * @throws ：
	 */
	public StudentClass getInfosById(Integer stu_clas_id);

}
