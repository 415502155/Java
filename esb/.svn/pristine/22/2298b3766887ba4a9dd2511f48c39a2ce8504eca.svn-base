package cn.edugate.esb.imp;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.dao.DepartmentDao;
import cn.edugate.esb.entity.Department;
import cn.edugate.esb.service.DepartmentService;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Paging;

@Component
@Transactional("transactionManager")
public class DepartmentServiceImpl implements DepartmentService{
	
	private static Logger logger = Logger.getLogger(DepartmentServiceImpl.class);
	
	@Autowired
	private DepartmentDao departmentDao;
	@Override
	public void add(Department department) {
		department.setIs_del(0);
		department.setInsert_time(new Date());
        try {
        	departmentDao.save(department);
        } catch (Exception e) {
            logger.error("create Department error");    
        }
	}

	@Override
	public Integer delete(int id) {
		Department department = getDepById(id);
		department.setDel_time(new Date());
		department.setIs_del(Constant.IS_DEL_YES);
        try {
        	departmentDao.update(department);
        } catch (Exception e) {
            logger.error("update Department error");
        }
        return null;
	}

	@Override
	public Department update(Department department) {
		department.setUpdate_time(new Date());
        try {
        	departmentDao.update(department);
        } catch (Exception e) {
            logger.error("update Department error");
        }
        return null;
	}

	@Override
	public Department getDepById(int id) {
		// TODO Auto-generated method stub
		return departmentDao.get(Department.class, id);
	}
 
	@Override
	public List<Department> getAll() {
		// TODO Auto-generated method stub
		//Criterion criterion = Restrictions.conjunction();
		Criterion criterion = Restrictions.and(Restrictions.eq("is_del", 0));
		return departmentDao.list(Department.class, criterion);
	}

	@Override
	public Paging<Department> getDepListWithPaging(Paging<Department> paging,
			Department department, int org_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Department> getListByOrgId(Integer org_id) {
		// TODO Auto-generated method stub
		//Criterion criterion = Restrictions.eq("org_id", org_id);
		Criterion criterion = Restrictions.and(Restrictions.eq("is_del", 0),Restrictions.eq("org_id", org_id));
		return departmentDao.list(Department.class, criterion,Order.desc("dep_id"));
	}

	@Override
	public Integer getDefaultId(Integer depId) {
        try {
        	return departmentDao.getDefaultId(depId);
        } catch (Exception e) {
            logger.error("update getDefaultId error");
            return null;
        }
	}

	@Override
	public List<Department> getDepsDetailList(String org_id) {
		try {
        	return departmentDao.getDepsDetailList(org_id);
        } catch (Exception e) {
            logger.error("DepartmentServiceImpl getDepsDetailList error");
            return null;
        }
	}

}
