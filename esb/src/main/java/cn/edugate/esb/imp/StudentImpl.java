package cn.edugate.esb.imp;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.dao.ICardDao;
import cn.edugate.esb.dao.IClass2StudentDao;
import cn.edugate.esb.dao.IOrgUserDao;
import cn.edugate.esb.dao.IParentDao;
import cn.edugate.esb.dao.IStudentDao;
import cn.edugate.esb.dao.IStudentParentDao;
import cn.edugate.esb.entity.Card;
import cn.edugate.esb.entity.Clas2Student;
import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.Parent;
import cn.edugate.esb.entity.Student;
import cn.edugate.esb.entity.StudentCard;
import cn.edugate.esb.entity.StudentParent;
import cn.edugate.esb.service.StudentService;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Paging;

@Service
@Transactional("transactionManager")
public class StudentImpl implements StudentService {

	private static Logger logger = Logger.getLogger(StudentImpl.class);

	private IStudentDao studentDao;

	private IStudentParentDao studentParentDao;

	private IParentDao parentDao;
	
	private ICardDao cardDao;
	
	@Autowired
	public void setCardDao(ICardDao cardDao) {
		this.cardDao = cardDao;
	}

	@Autowired
	public void setParentDao(IParentDao parentDao) {
		this.parentDao = parentDao;
	}

	@Autowired
	public void setStudentParentDao(IStudentParentDao studentParentDao) {
		this.studentParentDao = studentParentDao;
	}

	@Autowired
	public void setStudentDao(IStudentDao studentDao) {
		this.studentDao = studentDao;
	}

	@Override
	public void add(Student student) {
		this.studentDao.saveOrUpdate(student);
	}
	
	@Autowired
	private IOrgUserDao orgUserDao;
	
	@Autowired
	private IClass2StudentDao class2StudentDao;
	
	
	
	

	@Override
	public Integer delete(Integer studentId) {
		Student student = getById(studentId);
		student.setIs_del(Constant.IS_DEL_YES);
		Date currentDate = new Date();
		student.setDel_time(currentDate);
		try {
			studentDao.update(student);
			// 查找学生和家长的关联
			List<StudentParent> studentParentList = studentParentDao.getStudentParentListByStudentId(studentId.intValue());
			if (studentParentList != null && studentParentList.size() > 0) {
				for(StudentParent sp: studentParentList) {
					sp.setIs_del(true);
					sp.setDel_time(currentDate);
					studentParentDao.update(sp);
				}
			}
			
			// 查找学生和班级的关联
			List<Clas2Student> class2StudentList = class2StudentDao.getClass2StudentListByStudentId(studentId.intValue());
			if (class2StudentList != null && class2StudentList.size() > 0) {
				for (Clas2Student c2s : class2StudentList) {
					c2s.setIs_del(true);
					c2s.setDel_time(currentDate);
					class2StudentDao.update(c2s);
				}
			}
			
			// 查找对应的orgUser
			OrgUser orgUser = orgUserDao.get(OrgUser.class, student.getUser_id().intValue());
			if (orgUser != null && orgUser.getOrg_id().intValue() > 0){
				orgUser.setIs_del(true);
				orgUser.setDel_time(currentDate);
				orgUserDao.update(orgUser);
			}
			return studentId;
		} catch (Exception e) {
			logger.error("Student delete error:" + e.getMessage());
			return null;
		}
	}

	@Override
	public Student update(Student student) {
		student.setUpdate_time(new Date());
		try {
			studentDao.update(student);
			return student;
		} catch (Exception e) {
			logger.error("Student update error:" + e.getMessage());
			return null;
		}
	}

	@Override
	public Student getById(Integer studentId) {
		Student student = studentDao.get(Student.class, studentId);
		return student;
	}

	@Override
	public List<Student> getStudents(Student student) {
		try {
			if (null != student.getClas_id()) {
				return studentDao.getByClassId(student.getClas_id());
			} else if (null != student.getGrade_id()) {
				return studentDao.getByGradeId(student.getGrade_id());
			} else if (null != student.getOrg_id()) {
				return studentDao.getByOrgId(student.getOrg_id());
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("Student getAllList error:" + e.getMessage());
			return null;
		}
	}
	/**
	 * 刷新缓存  用到此方法
	 * 
	 * **/
	@Override
	public List<Student> getAll() {
		try {
			Criterion criterion = Restrictions.and(Restrictions.eq("is_del", 0));
			List<Student> ls = this.studentDao.list(Student.class, criterion, Order.desc("stud_id"));
			return ls;
			// return studentDao.getAll();
		} catch (Exception e) {
			logger.error("Student getAll error:" + e.getMessage());
			return null;
		}
	}

	@Override
	public Integer getStudentsNumber(Integer clas_id) {
		try {
			return studentDao.getStudentsNumber(clas_id);
		} catch (Exception e) {
			logger.error("Student getStudentsNumber error:" + e.getMessage());
			return null;
		}
	}

	@Override
	public void getPaging(Integer org_id, String stud_name, String parent_name, String user_mobile, Paging<Student> pages) {
		// TODO Auto-generated method stub
		Long total = this.studentDao.getTotalCount(org_id, stud_name, parent_name, user_mobile);
		pages.setTotal(total);
		this.studentDao.getPaging(org_id, stud_name, parent_name, user_mobile, pages);
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Object> getStudentsForSelect(String groupId, String orgId) {
		try {
			return studentDao.getStudentsForSelect(groupId, orgId);
		} catch (Exception e) {
			logger.error("Student getStudentsForSelect error:" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<StudentParent> getAllStudentParent() {
		// TODO Auto-generated method stub
		//Criterion criterion = Restrictions.eq("is_del", false);
		// this.studentParentDao.list(StudentParent.class, criterion, Order.desc("stud2parent_id"));
		return this.studentDao.getAllStudAndParent();
	}

	@Override
	public List<Parent> getParents(Integer stud_id, Integer org_id) {
		// TODO Auto-generated method stub
		return this.parentDao.getParents(stud_id, org_id);
	}

	@Override
	public void saveStudentParent(StudentParent studentParent) {
		// TODO Auto-generated method stub
		this.studentParentDao.saveOrUpdate(studentParent);
	}

	@Override
	public List<Student> getStudentsByClassId(Integer clas_id) {
		// TODO Auto-generated method stub
		return this.studentDao.getStudentsByClassId(clas_id);
	}

	@Override
	public List<Student> getStudentsByGradeId(Integer grade_id) {
		// TODO Auto-generated method stub
		return this.studentDao.getStudentsByGradeId(grade_id);
	}

	@Override
	public long getStudentCount(Integer org_id) {
		// TODO Auto-generated method stub
		long num = this.studentDao.getTotalCount(org_id, "", "", "");
		return num;
	}

	@Override
	public Student getByName(Integer org_id, String stud_name, Set<String> mobiles) {
		// TODO Auto-generated method stub
		return this.studentDao.getByName(org_id, stud_name, mobiles);
	}

	@Override
	public Student getStudentByUserID(int orgID, int userID) {
		return studentDao.getStudentByUserID(orgID, userID);
	}

	@Override
	public Student getStudentByOrgUser(OrgUser ou) {
		return studentDao.getStudentByOrgUser(ou);
	}

	@Override
	public void deleteStudent2Parent(int student2parent_id) {
		StudentParent studentParentEntity = studentParentDao.get(StudentParent.class, student2parent_id);
		if (studentParentEntity != null && studentParentEntity.getStud2parent_id().intValue() > 0) {
			studentParentEntity.setIs_del(true);
			studentParentEntity.setDel_time(new Date());
			studentParentDao.update(studentParentEntity);
		}
	}

	@Override
	public List<Student> getByName(Integer org_id, String stud_name) {
		// TODO Auto-generated method stub
		return this.studentDao.getByName(org_id, stud_name);
	}

	@Override
	public List<StudentParent> getStudentParentByOrgId(Integer org_id) {
		return studentParentDao.getStudentParentListIncludeDeleted(org_id);
	}

	@Override
	public List<Card> getCardsByStudentId(Integer stud_id) {
		// TODO Auto-generated method stub
		return this.cardDao.getCardsByStudentId(stud_id);
	}

	@Override
	public Card getCardById(String card_noid) {
		// TODO Auto-generated method stub
		return this.cardDao.get(Card.class, card_noid);
	}

	@Override
	public void saveCard(Card card) {
		// TODO Auto-generated method stub
		this.cardDao.saveOrUpdate(card);
	}

	@Override
	public List<Student> getStudentByClassIdStudentName(Integer clas_id,
			String stud_name) {
		// TODO Auto-generated method stub
		return this.studentDao.getStudentByClassIdStudentName(clas_id,stud_name);
	}

	@Override
	public List<Student> getGradStudentsByName(Integer org_id, String stud_name) {
		// TODO Auto-generated method stub
		return this.studentDao.getGradStudentsByName(org_id,stud_name);
	}

	@Override
	public List<Card> getAllCard() {
		// TODO Auto-generated method stub
		Criterion criterion = Restrictions.conjunction();		
		return this.cardDao.list(Card.class, criterion, Order.desc("insert_time"));
	}

	@Override
	public List<Object> getStudentsWithGradeClass(Integer orgId) {
		return studentDao.getStudentsWithGradeClass(orgId);
	}

	@Override
	public List<StudentCard> getStudentListByOrgId(Integer org_id) {
		// TODO Auto-generated method stub
		List<StudentCard> ls = this.studentDao.getStudentListByOrgId(org_id);
		return ls;
	}

	@Override
	public List<StudentCard> getStudsAndClasByOrgId(Integer org_id) {
		// TODO Auto-generated method stub
		return this.studentDao.getStudsAndClasByOrgId(org_id);
	}

	@Override
	public void deleteStuds(String studentIds) {
		// TODO Auto-generated method stub
		try {
			String[] ids = studentIds.split(",");
			for (int j = 0; j < ids.length; j++) {
			    Integer id = new Integer(ids[j]); 
			    Student student = getById(id);
				student.setIs_del(Constant.IS_DEL_YES);
				Date currentDate = new Date();
				student.setDel_time(currentDate);
				
				studentDao.update(student);
				// 查找学生和家长的关联
				List<StudentParent> studentParentList = studentParentDao.getStudentParentListByStudentId(id.intValue());
				if (studentParentList != null && studentParentList.size() > 0) {
					for(StudentParent sp: studentParentList) {
						sp.setIs_del(true);
						sp.setDel_time(currentDate);
						studentParentDao.update(sp);
					}
				}
				// 查找学生和班级的关联
				List<Clas2Student> class2StudentList = class2StudentDao.getClass2StudentListByStudentId(id.intValue());
				if (class2StudentList != null && class2StudentList.size() > 0) {
					for (Clas2Student c2s : class2StudentList) {
						c2s.setIs_del(true);
						c2s.setDel_time(currentDate);
						class2StudentDao.update(c2s);
					}
				}
				// 查找对应的orgUser
				OrgUser orgUser = orgUserDao.get(OrgUser.class, student.getUser_id().intValue());
				if (orgUser != null && orgUser.getOrg_id().intValue() > 0){
					orgUser.setIs_del(true);
					orgUser.setDel_time(currentDate);
					orgUserDao.update(orgUser);
				}
			} 
		} catch (Exception e) {
			logger.error("Student delete error:" + e.getMessage());
			
		}
	}

}
