package cn.edugate.esb.dao;

import java.util.List;

import cn.edugate.esb.entity.Clas2Student;

/**
 * 学生班级关系DAO接口 Title:IClassesDao Description: Company:SJWY
 * 
 * @author:Liuy
 * @Date:2017年5月25日下午1:27:14
 */
public interface IClass2StudentDao extends BaseDAO<Clas2Student> {

	public abstract List<Clas2Student> getClas2StudentList(int studentID, int classID);
	
	public abstract List<Clas2Student> getClas2StudentList();
	
	public abstract List<Clas2Student> getClass2StudentListByStudentId(int studentId);
	
	public abstract List<Clas2Student> getClass2StudentListByClassId(int classId);

	/**
	 * 查询待删除的学生班级关系列表
	 * @param stud_id
	 * @param org_id
	 * @return
	 */
	public abstract List<Clas2Student> getOldForDelete(Integer stud_id,Integer org_id);
	
	/**
	 * 根据机构查询关系
	 * @param org_id
	 * @return
	 */
	public abstract List<Clas2Student> getClass2StudentListIncludeDeleted(int org_id);

	/**
	 * 根据班级主键查询
	 * @param clas_id
	 * @return
	 */
	public abstract List<Clas2Student> getClas2StudentByClassId(Integer clas_id);

}
