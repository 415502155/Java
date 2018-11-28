package cn.edugate.esb.dao.imp;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IGradeDao;
import cn.edugate.esb.entity.Grade;
import cn.edugate.esb.entity.Role;
import cn.edugate.esb.util.Constant;

/**
 * 年级DAO实现类
 * Title:IGradeImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月25日下午4:25:00
 */
@Repository
public class IGradeImpl extends BaseDAOImpl<Grade> implements IGradeDao {
	/**
	 * 刷新缓存  用到此方法
	 * 
	 * **/
	@Override
	public List<Grade> getAllList(Grade grade) {
		Session session=this.factory.getCurrentSession();
		Criteria c=session.createCriteria(Grade.class);
		c.add(Restrictions.eq("org_id", grade.getOrg_id()));
		if(null==grade.getIs_del()){
			c.add(Restrictions.eq("is_del", Constant.IS_DEL_NO));
		}else{
			c.add(Restrictions.eq("is_del", grade.getIs_del()));
		}
		@SuppressWarnings("unchecked")
		List<Grade> ls = c.list();
		return ls;
	}

	@Override
	public int saveGrade(Grade grade) {
		grade.setInsert_time(new Date());
		grade.setUpdate_time(new Date());
		grade.setIs_del(0);
		super.save(grade);
		return grade.getOrg_id().intValue();
	}
	
	@Override
	public List<Grade> getGradeList(int orgID) {
//		Session session = this.factory.getCurrentSession();
//		Criteria c = session.createCriteria(Grade.class);
//		c.add(Restrictions.eq("org_id", orgID));
//		c.add(Restrictions.eq("is_del", Constant.IS_DEL_NO));
//		@SuppressWarnings("unchecked")
//		List<Grade> ls = c.list();
//		return ls;
		Session session = this.factory.getCurrentSession();		
		String sql = "SELECT g.* FROM grade g WHERE g.org_id=? AND g.grade_number % 10 != 8 AND g.grade_number % 10 != 9 AND g.is_del=0;";
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, orgID);
		query.setResultTransformer(Transformers.aliasToBean(Grade.class));			
		List<Grade> resultList = query.list();				
		return resultList;
	}
	

	@Override
	public List<Grade> getGradeList4AttnMachine(int orgID) {
		Session session = this.factory.getCurrentSession();		
		String sql = "SELECT g.* FROM grade g WHERE g.org_id=? AND g.grade_number % 10 != 0  AND g.grade_number % 10 != 8 AND g.grade_number % 10 != 9 AND g.is_del=0;";
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, orgID);
		query.setResultTransformer(Transformers.aliasToBean(Grade.class));			
		List<Grade> resultList = query.list();				
		return resultList;
	}

	@Override
	public Grade getByName(Integer org_id, String grade_name) {
		Session session=this.factory.getCurrentSession();
		String sql="SELECT * FROM grade g WHERE g.org_id=:org_id AND g.grade_name=:grade_name AND g.is_del=0";
		Query query = session.createSQLQuery(sql);
		query.setInteger("org_id", org_id);
		query.setString("grade_name", grade_name);
		query.setResultTransformer(Transformers.aliasToBean(Grade.class));
		@SuppressWarnings("unchecked")
		List<Grade> ls = query.list();
		return ls.size()>0?ls.get(0):null;
	}

	@Override
	public List<Grade> getGradeListIncludeDeleted(int org_id) {
		Session session = this.factory.getCurrentSession();
		Criteria c = session.createCriteria(Grade.class);
		c.add(Restrictions.eq("org_id", org_id));
		@SuppressWarnings("unchecked")
		List<Grade> ls = c.list();
		return ls;
	}

	@Override
	public Grade getGrade(int org_id, int gradeType, int gradeNum) {
		Session session = this.factory.getCurrentSession();
		
		String sql = "SELECT g.* FROM grade g WHERE g.org_id=? AND g.grade_type=? AND g.grade_number=? AND g.is_del=0;";
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, org_id);
		query.setInteger(1, gradeType);
		query.setInteger(2, gradeNum);
		query.setResultTransformer(Transformers.aliasToBean(Grade.class));
		
		Grade g = null;
		List<Grade> resultList = query.list();
		if (resultList != null && resultList.size() > 0) {
			g = resultList.get(0);
		}
		
		return g;
	}

	@Override
	public Grade getGrade(int org_id, String gradeName) {
		String sql = "SELECT *  FROM grade g WHERE   g.org_id=? AND g.grade_name=? AND g.is_del=0";

		Session session = this.factory.getCurrentSession();

		Query q = session.createSQLQuery(sql);
		q.setResultTransformer(Transformers.aliasToBean(Grade.class));
		q.setInteger(0, org_id);
		q.setString(1, gradeName);

		List<Grade> resultList = q.list();
		Grade r = null;

		if (resultList != null && resultList.size() > 0) {
			r = resultList.get(0);
		}
		return r;
	}
}
