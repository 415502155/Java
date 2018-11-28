package cn.edugate.esb.dao.imp;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IGrade2ClassDao;
import cn.edugate.esb.entity.Grade2Clas;

/**
 * 年级DAO实现类 Title:IGradeImpl Description: Company:SJWY
 * 
 * @author:Liuy
 * @Date:2017年5月25日下午4:25:00
 */
@Repository
public class IGrade2ClassImpl extends BaseDAOImpl<Grade2Clas> implements IGrade2ClassDao {

	@Override
	public List<Grade2Clas> getAllList(Grade2Clas grade2clas) {
		Criterion criterion = Restrictions.conjunction();
		List<Grade2Clas> ls = this.list(Grade2Clas.class, criterion, Order.desc("gra2cls_id"));
		return ls;
	}

	@Override
	public Grade2Clas getGrade2Clas(Integer grade_id, Integer clas_id) {
		Criterion criterion = Restrictions.and(Restrictions.eq("grade_id", grade_id)).add(Restrictions.eq("clas_id", clas_id))
				.add(Restrictions.eq("is_del", false));
		List<Grade2Clas> ls = this.list(Grade2Clas.class, criterion, Order.desc("gra2cls_id"));
		return ls.size() > 0 ? ls.get(0) : null;
	}

	@Override
	public List<Grade2Clas> getGrade2ClassListByClassId(int classId) {
		String sql = "SELECT g2c.* FROM grade2clas g2c WHERE g2c.clas_id=? AND g2c.is_del=0;";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, classId);
		query.setResultTransformer(Transformers.aliasToBean(Grade2Clas.class));
		
		List<Grade2Clas> resultList = query.list();
				
		return resultList;
	}

	@Override
	public List<Grade2Clas> getGrade2ClassListIncludeDeleted(int org_id) {
		String sql = "SELECT g2c.* FROM grade2clas g2c WHERE g2c.grade_id IN (SELECT g.grade_id FROM grade g WHERE g.org_id=?) "
				+ "OR g2c.clas_id IN (SELECT c.clas_id FROM classes c WHERE c.org_id=?);";

		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, org_id);
		query.setInteger(1, org_id);
		query.setResultTransformer(Transformers.aliasToBean(Grade2Clas.class));

		List<Grade2Clas> resultList = query.list();

		return resultList;
	}

	@Override
	public List<Grade2Clas> getDirtyRecords(int gradeId, int classId) {
		String sql = "SELECT g2c.* FROM grade2clas g2c WHERE g2c.clas_id=? AND g2c.grade_id != ? AND g2c.is_del=0;";
		
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, classId);
		query.setInteger(1, gradeId);
		query.setResultTransformer(Transformers.aliasToBean(Grade2Clas.class));
		
		List<Grade2Clas> resultList = query.list();

		return resultList;
	}

}
