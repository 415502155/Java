package cn.edugate.esb.dao;

import java.util.List;
import java.util.Set;

import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.Student;
import cn.edugate.esb.entity.StudentCard;
import cn.edugate.esb.entity.StudentParent;
import cn.edugate.esb.util.Paging;

/**
 * 学生DAO接口 Title:IStudentDao Description: Company:SJWY
 * 
 * @author:Liuy
 * @Date:2017年5月26日上午8:42:41
 */
public interface IStudentDao extends BaseDAO<Student> {

	/**
	 * 根据组织主键查询学生列表
	 * 
	 * @param orgId
	 * @return
	 */
	public abstract List<Student> getByOrgId(Integer orgId);
	
	public abstract List<Student> getStudentListIncludeDeleted(int org_id);

	/**
	 * 根据班级主键查询学生列表
	 * 
	 * @param classId
	 * @return
	 */
	public abstract List<Student> getByClassId(Integer classId);

	/**
	 * 根据年级主键查询学生列表
	 * 
	 * @param gradeId
	 * @return
	 */
	public abstract List<Student> getByGradeId(Integer gradeId);

	/**
	 * 根据年级主键查询学生列表
	 * 
	 * @param gradeId
	 * @return
	 */
	//public abstract List<Student> getAll();

	/**
	 * 获取班级人数
	 * 
	 * @param clas_id
	 * @return
	 */
	public abstract Integer getStudentsNumber(Integer clas_id);

	/**
	 * 获取条件查询总数
	 * 
	 * @param org_id
	 * @param stud_name
	 * @param parent_name
	 * @param user_mobile
	 * @return
	 */
	public abstract Long getTotalCount(Integer org_id, String stud_name, String parent_name, String user_mobile);

	/**
	 * 获取条件查询分页
	 * 
	 * @param org_id
	 * @param stud_name
	 * @param parent_name
	 * @param user_mobile
	 * @param pages
	 */
	public abstract void getPaging(Integer org_id, String stud_name, String parent_name, String user_mobile, Paging<Student> pages);

	/**
	 * 带组成员信息的全体学生列表
	 * 
	 * @param groupId
	 * @param orgId
	 * @return
	 */
	@Deprecated
	public abstract List<Object> getStudentsForSelect(String groupId, String orgId);

	public abstract List<Student> getStudentsByClassId(Integer clas_id);

	public abstract List<Student> getStudentsByGradeId(Integer grade_id);
	
	public abstract Student getByName(Integer org_id, String stud_name,
			Set<String> mobiles);
	
	public abstract Student getStudentByUserID(int orgID, int userID);
	
	public abstract List<StudentParent> getAllStudAndParent();

	/**
	 * 根据机构用户获取学生详细信息
	 * @param ou
	 * @return
	 */
	public abstract Student getStudentByOrgUser(OrgUser ou);

	public abstract List<Student> getByName(Integer org_id, String stud_name);

	public abstract List<Student> getStudentByClassIdStudentName(Integer clas_id,
			String stud_name);

	public abstract List<Student> getGradStudentsByName(Integer org_id,
			String stud_name);

	/**
	 * 查询机构下所有的学生及其所属的校区、年级、班级信息
	 * @param orgId
	 * @return
	 */
	public abstract List<Object> getStudentsWithGradeClass(Integer orgId);

	public abstract List<StudentCard> getStudentListByOrgId(Integer org_id);
	
	public abstract List<StudentCard> getStudsAndClasByOrgId(Integer org_id);
}
