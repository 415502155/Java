package sng.dao;

import sng.pojo.StudentPojo;
import sng.pojo.base.Student;
import sng.util.Paging;

/**
 * @类 名： StudentDao
 * @功能描述：学生Dao接口 
 * @作者信息： LiuYang
 * @创建时间： 2018年11月20日下午4:02:11
 */
public interface StudentDao extends BaseDao<Student>{

	/**
	 * @Title : queryList 
	 * @功能描述:根据条件查询学生列表 
	 * @返回类型：List<Student> 
	 * @throws ：
	 */
	Paging<StudentPojo> queryList(String term_id, String category_id, String subject_id, String clas_type, String cam_id, String stu_status, String pay_method, String is_print, String keyword, Paging<StudentPojo> page);

	/**
	 * @Title : getStudentInfos 
	 * @功能描述: 根据学员班级关系表主键获取学生详细信息
	 * @返回类型：StudentPojo 
	 * @throws ：
	 */
	public StudentPojo getStudentInfos(Integer stu_class_id);
}
