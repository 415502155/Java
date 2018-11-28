package cn.edugate.esb.service;

import java.util.List;

import cn.edugate.esb.entity.Clas2Student;
import cn.edugate.esb.entity.Classes;

/**
 * 学生班级关系服务接口
 * Title:Clas2StudentService
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月22日上午9:21:36
 */
public interface Class2StudentService {
	
	/**
	 * 创建新学生班级关系
	 * @param clas2student
	 */
	public abstract void add(Clas2Student clas2student);

	/**
	 * 获取所有数据
	 * @return
	 */
	public abstract List<Clas2Student> getAllList();

	/**
	 * 获取学生班级
	 * @param string
	 * @return
	 */
	public abstract List<Classes> getClassesByStudId(String string);

	/**
	 * 查询该学生所在的班级
	 * @param stud_id
	 * @param org_id 
	 * @return
	 */
	public abstract List<Classes> queryClassesByStudId(Integer stud_id, Integer org_id);

	public abstract Clas2Student getByClas2stud_id(Integer clas2stud_id);

	public abstract void deleteClassStudent(Integer clas2stud_id);
	
	public abstract int batchMoveStudent(String originalClassID, String targetClassID, String[] studentIDArray);

	public abstract Clas2Student getClas2Student(Integer stud_id,
			Integer clas_id);
	public abstract List<Clas2Student> getClas2StudentList();

	/**
	 * 删除废弃的学生班级关系
	 * @param stud_id
	 * @param org_id
	 */
	public abstract void deleteOld(Integer stud_id, Integer org_id);

	/**
	 * 根据机构主键查询关系
	 * @param parseInt
	 * @return
	 */
	public abstract List<Clas2Student> getClas2StudentByOrgId(Integer org_id);

	/**
	 * 根据班级主键查询
	 * @param clas_id
	 * @return
	 */
	public abstract List<Clas2Student> getClas2StudentByClassId(Integer clas_id);
}
