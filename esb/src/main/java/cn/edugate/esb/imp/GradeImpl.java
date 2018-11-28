package cn.edugate.esb.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.dao.IGrade2ClassDao;
import cn.edugate.esb.dao.IGradeDao;
import cn.edugate.esb.entity.Grade;
import cn.edugate.esb.entity.Grade2Clas;
import cn.edugate.esb.service.GradeService;
import cn.edugate.esb.util.Constant;

/**
 * 菜单服务实现类
 * Title:GradeImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月19日上午9:57:49
 */
@Component
@Transactional("transactionManager")
public class GradeImpl implements GradeService {

	private static Logger logger = Logger.getLogger(GradeImpl.class);
	
	private IGradeDao gradeDao;
	private IGrade2ClassDao grade2ClassDao;
	
	@Autowired
	public void setGrade2ClassDao(IGrade2ClassDao grade2ClassDao) {
		this.grade2ClassDao = grade2ClassDao;
	}

	@Autowired
	public void setGradeDao(IGradeDao gradeDao) {
		this.gradeDao = gradeDao;
	}

	@Override
	public void add(Grade grade) {
		grade.setIs_del(Constant.IS_DEL_NO);
		grade.setInsert_time(new Date());
		try {
			gradeDao.saveOrUpdate(grade);
		} catch (Exception e) {
			logger.error("Grade add error");	
		}
	}

	@Override
	public Integer delete(Grade grade) {
		Grade oldGrade = new Grade();
		try {
			oldGrade = gradeDao.get(Grade.class, grade.getGrade_id());
			oldGrade.setIs_del(Constant.IS_DEL_YES);
			oldGrade.setDel_time(new Date());
			gradeDao.update(oldGrade);
		} catch (Exception e) {
			logger.error("Grade delete error");	
			return null;
		}
		return oldGrade.getGrade_id();
	}

	@Override
	public Grade update(Grade grade) {
		try {
			grade.setUpdate_time(new Date());
			gradeDao.update(grade);
		} catch (Exception e) {
			logger.error("Grade update error");	
			return null;
		}
		return grade;
	}

	@Override
	public Grade getById(Integer id) {
		Grade grade = new Grade();
		try {
			grade = gradeDao.get(Grade.class, id);
		} catch (Exception e) {
			logger.error("Grade getById error");	
			return null;
		}
		return grade;
	}
	/**
	 * 刷新缓存  用到此方法
	 * 
	 * **/
	@Override
	public List<Grade> getAll() {
		//Criterion criterion = Restrictions.conjunction();
		Criterion criterion = Restrictions.and(Restrictions.eq("is_del", Constant.IS_DEL_NO));
		try {
			return gradeDao.list(Grade.class, criterion);
		} catch (Exception e) {
			logger.error("Grade getAll error:"+e.getMessage());
			return null;
		}
	}
	/**
	 * 刷新缓存  用到此方法
	 * 
	 * **/
	@Override
	public List<Grade> getAllList(Grade grade) {
		List<Grade> list = new ArrayList<Grade>();
		try {
			list = gradeDao.getAllList(grade);
		} catch (Exception e) {
			logger.error("Classes getClassess error");	
			return null;
		}
		return list;
	}

	@Override
	public Grade getByName(Integer org_id, String grade_name) {
		// TODO Auto-generated method stub
		Grade grade = this.gradeDao.getByName(org_id,grade_name);
		return grade;
	}

	@Override
	public Grade2Clas getGrade2Clas(Integer grade_id, Integer clas_id) {
		// TODO Auto-generated method stub
		return this.grade2ClassDao.getGrade2Clas(grade_id,clas_id);
	}

	@Override
	public void addGrade2Clas(Grade2Clas gc) {
		// TODO Auto-generated method stub
		this.grade2ClassDao.saveOrUpdate(gc);
	}

	
	@Override
	public List<Grade> getGradeList(int org_id) {
		return gradeDao.getGradeList(org_id);
	}
	
	@Override
	public List<Grade> getGradeList4AttnMachine(int org_id) {
		return gradeDao.getGradeList4AttnMachine(org_id);
	}

	@Override
	public List<Grade2Clas> getAllGrade2Clas() {
		// TODO Auto-generated method stub
		Criterion criterion = Restrictions.eq("is_del", false);
		List<Grade2Clas> ls = this.grade2ClassDao.list(Grade2Clas.class, criterion, Order.desc("gra2cls_id"));
		return ls;
	}

}
