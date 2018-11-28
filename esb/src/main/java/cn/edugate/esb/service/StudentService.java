package cn.edugate.esb.service;

import java.util.List;
import java.util.Set;

import cn.edugate.esb.entity.Card;
import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.Parent;
import cn.edugate.esb.entity.Student;
import cn.edugate.esb.entity.StudentCard;
import cn.edugate.esb.entity.StudentParent;
import cn.edugate.esb.util.Paging;

public interface StudentService {

	/**
	 * 添加新的学生
	 * 
	 * @param function
	 */
	public abstract void add(Student student);

	/**
	 * 根据主键删除学生
	 * 
	 * @param id
	 * @return
	 */
	public abstract Integer delete(Integer studentId);
	
	public abstract void deleteStuds(String studentIds);

	/**
	 * 更新学生
	 * 
	 * @param function
	 * @return
	 */
	public abstract Student update(Student student);

	/**
	 * 根据主键获取学生
	 * 
	 * @param id
	 * @return
	 */
	public abstract Student getById(Integer studentId);

	/**
	 * 根据机构用户主键获取学生
	 * 
	 * @param id
	 * @return
	 */
	public abstract List<Student> getStudents(Student student);

	/**
	 * 获得所有学生列表(For Redis)
	 * 
	 * @param function
	 * @return
	 */
	public abstract List<Student> getAll();

	/**
	 * 获取班级人数
	 * 
	 * @param clas_id
	 * @return
	 */
	public abstract Integer getStudentsNumber(Integer clas_id);

	/**
	 * 获取组织内学生分页
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

	/**
	 * 获取所有学生家长关系
	 * 
	 * @return
	 */
	public abstract List<StudentParent> getAllStudentParent();

	/**
	 * 获取所以学生家长
	 * 
	 * @param stud_id
	 * @return
	 */
	public abstract List<Parent> getParents(Integer stud_id, Integer org_id);

	/**
	 * 保存学生家长关系
	 * 
	 * @param studentParent
	 */
	public abstract void saveStudentParent(StudentParent studentParent);

	/**
	 * 获取班级所有学生
	 * 
	 * @param clas_id
	 * @return
	 */
	public abstract List<Student> getStudentsByClassId(Integer clas_id);

	public abstract List<Student> getStudentsByGradeId(Integer grade_id);

	public abstract long getStudentCount(Integer org_id);

	/**
	 * 通过姓名查找学生
	 * 
	 * @param org_id
	 * @param stud_name
	 * @param mobiles
	 * @return
	 */
	public abstract Student getByName(Integer org_id, String stud_name, Set<String> mobiles);

	public abstract Student getStudentByUserID(int orgID, int userID);
	
	/**
	 * 根据机构用户获取用户详细信息
	 * @param ou
	 * @return
	 */
	public abstract Student getStudentByOrgUser(OrgUser ou);
	
	/**
	 * 根据主键删除学生和家长之间的关联
	 * @param student2parent_id
	 */
	public abstract void deleteStudent2Parent(int student2parent_id);

	public abstract List<Student> getByName(Integer org_id, String stud_name);
	
	public List<StudentParent> getStudentParentByOrgId(Integer org_id);

	public abstract List<Card> getCardsByStudentId(Integer stud_id);

	public abstract Card getCardById(String card_noid);

	public abstract void saveCard(Card card);

	public abstract List<Student> getStudentByClassIdStudentName(Integer clas_id,
			String stud_name);

	public abstract List<Student> getGradStudentsByName(Integer org_id,
			String stud_name);

	public abstract List<Card> getAllCard();

	/**
	 * 查询机构下所有的学生及其所属的校区、年级、班级信息
	 * @param orgId
	 * @return
	 */
	public abstract List<Object> getStudentsWithGradeClass(Integer orgId);

	public abstract List<StudentCard> getStudentListByOrgId(Integer org_id);
	
	
	public abstract List<StudentCard> getStudsAndClasByOrgId(Integer org_id);

}
