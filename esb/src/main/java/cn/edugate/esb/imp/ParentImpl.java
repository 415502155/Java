package cn.edugate.esb.imp;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.dao.IParentDao;
import cn.edugate.esb.dao.IStudentParentDao;
import cn.edugate.esb.entity.Parent;
import cn.edugate.esb.entity.StudentParent;
import cn.edugate.esb.service.ParentService;
import cn.edugate.esb.util.Constant;

@Component
@Transactional("transactionManager")
public class ParentImpl implements ParentService {
	private IParentDao ParentDao;
	private IStudentParentDao studentParentDao;
	@Autowired
	public void setStudentParentDao(IStudentParentDao studentParentDao) {
		this.studentParentDao = studentParentDao;
	}
	@Autowired
	public void setParentDao(IParentDao parentDao) {
		ParentDao = parentDao;
	}
	@Override
	public List<Parent> getAll() {
		// TODO Auto-generated method stub
		//Criterion criterion =Restrictions.conjunction();
		Criterion criterion = Restrictions.and(Restrictions.eq("is_del", false));
		List<Parent> ls = this.ParentDao.list(Parent.class, criterion, Order.desc("parent_id"));
		return ls;
	}
	@Override
	public void addParent(Parent parent) {
		// TODO Auto-generated method stub
		this.ParentDao.saveOrUpdate(parent);
	}
	@Override
	public void addStudentParent(StudentParent sp) {
		// TODO Auto-generated method stub
		this.studentParentDao.saveOrUpdate(sp);
	}
	@Override
	public Parent getByMobile(String mobile, Integer org_id) {
		// TODO Auto-generated method stub
		List<Parent> ls = this.ParentDao.getByMobile(mobile,org_id);
		
		return ls.size()>0?ls.get(0):null;
	}
	@Override
	public void removeStudentParent(int stud2parent_id) {
		// TODO Auto-generated method stub
		this.studentParentDao.deleteById(StudentParent.class, stud2parent_id);
	}
	@Override
	public void updateStudentParent(StudentParent sp) {
		// TODO Auto-generated method stub
		this.studentParentDao.update(sp);
	}
	@Override
	public int getCountStudentParent(int stud_id, int parent_id) {
		// TODO Auto-generated method stub
		return this.ParentDao.getCountStudentParent(stud_id, parent_id);
	}
	@Override
	public int checkParentPhone(String phone, String orgId, String parent_id) {
		// TODO Auto-generated method stub
		return this.ParentDao.checkParentPhone(phone, orgId, parent_id);
	}
	@Override
	public StudentParent getStudentParent(Integer stud_id, Integer parent_id) {
		// TODO Auto-generated method stub
		return this.ParentDao.getStudentParent(stud_id,parent_id);
	}
	@Override
	public Parent getParentByUserID(int orgID, int userID) {
		return ParentDao.getParentByUserID(orgID, userID);
	}
	@Override
	public void updateParent(Parent p) {
		ParentDao.update(p);
	}
	@Override
	public List<Parent> getByStudId(String stud_id) {
		return this.ParentDao.getParentsByStudId(stud_id);
	}
	@Override
	public List<Parent> getParentByOrgId(int org_id) {
		//Criterion criterion = Restrictions.eq("org_id", org_id);
		Criterion criterion = Restrictions.and(Restrictions.eq("is_del", false),Restrictions.eq("org_id", org_id));
		List<Parent> ls = this.ParentDao.list(Parent.class, criterion);
		return ls;
	}
	
}
