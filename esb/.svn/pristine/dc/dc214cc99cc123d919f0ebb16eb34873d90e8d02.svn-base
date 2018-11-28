package cn.edugate.esb.dao.imp;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IClassesDao;
import cn.edugate.esb.entity.Classes;
import cn.edugate.esb.entity.Grade;
import cn.edugate.esb.util.LSHelper;
import cn.edugate.esb.util.Paging;

/**
 * 班级DAO实现类
 * Title:IClassesImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月25日下午1:45:55
 */
@Repository
public class IClassesImpl extends BaseDAOImpl<Classes> implements IClassesDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Classes> getClasses(int orgId,int camId,int isGraduated,int gradeNumber) {
		Session session=this.factory.getCurrentSession();
		StringBuffer sql = new StringBuffer();
			sql.append(" SELECT c.clas_id,c.org_id,c.cam_id,c.is_graduated,c.start_time,c.clas_name,c.clas_card,c.clas_note,c.clas_logo,c.clas_newspaper,g.grade_id FROM classes c ")
				.append(" INNER JOIN campus ca ON ca.cam_id=c.cam_id AND c.org_id=ca.org_id ")
				.append(" INNER JOIN grade2clas gc ON gc.clas_id=c.clas_id ")
				.append(" INNER JOIN grade g ON g.grade_id=gc.grade_id AND g.org_id=c.org_id AND g.org_id=ca.org_id ")
				.append(" WHERE c.is_del=0 AND ca.is_del=0 AND gc.is_del=0 AND g.is_del=0 ");
		if(orgId!=0){
			sql.append(" AND c.org_id="+orgId+" ");
		}
		if(camId!=0){
			sql.append(" AND c.cam_id="+camId+" ");
		}
		if(gradeNumber!=0){
			sql.append(" AND g.grade_number="+gradeNumber+" ");
		}
		if(isGraduated!=0){
			sql.append(" AND c.is_graduated="+isGraduated+" ");
		}
		sql.append(" GROUP BY c.clas_id order by c.clas_name asc ");
		Query query = session.createSQLQuery(sql.toString());
	
		query.setResultTransformer(Transformers.aliasToBean(Classes.class));
		
		List<Classes> list = query.list();
		for (Classes classes : list) {
			LSHelper.processInjection(classes);
		}
		
		return list;
	}

	@Override
	public Long getTotalCount(Integer org_id,Integer is_graduated, Integer cam_id,
			Integer grade_id) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT  COUNT(DISTINCT c.clas_id) FROM classes c LEFT JOIN grade2clas gc ON c.clas_id=gc.clas_id "
				+ "inner JOIN campus cp ON c.cam_id=cp.cam_id "
				+ "AND gc.is_del=0 LEFT JOIN grade g ON gc.grade_id=g.grade_id AND g.is_del=0 "
				+ "where 1=1 AND c.is_del=0 AND cp.is_del = 0";
		if(org_id>0){
			sql += " AND c.org_id=:org_id ";
		}
		//if(is_graduated!=0){
			sql += " AND c.is_graduated=0 ";
		//}
		if(cam_id!=null&&cam_id!=0){
			sql += " AND c.cam_id=:cam_id ";
		}
		if(grade_id!=null&&grade_id!=0){
			sql += " AND g.grade_id=:grade_id ";
		}	
		sql+="ORDER BY c.clas_id DESC";
		
		Query query = session.createSQLQuery(sql.toString());
		if(org_id>0){
			query.setInteger("org_id", org_id);
		}
//		if(is_graduated!=0){
//			query.setInteger("is_graduated", is_graduated);
//		}
		if(cam_id!=null&&cam_id!=0){
			query.setInteger("cam_id", cam_id);
		}
		if(grade_id!=null&&grade_id!=0){
			query.setInteger("grade_id", grade_id);
		}
		
		BigInteger count = (BigInteger) query.uniqueResult();
		
		return count.longValue();
	}

	@Override
	public void getPaging(Integer org_id, Integer is_graduated, Integer cam_id,
			Integer grade_id, Paging<Classes> pages) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT c.*,g.grade_name,cp.cam_name FROM classes c LEFT JOIN grade2clas gc ON c.clas_id=gc.clas_id "
				+ "inner JOIN campus cp ON c.cam_id=cp.cam_id "
				+ "AND gc.is_del=0 LEFT JOIN grade g ON gc.grade_id=g.grade_id AND g.is_del=0 "
				+ "where 1=1 AND c.is_del=0 AND cp.is_del = 0 ";
		if(org_id>0){
			sql += " AND c.org_id=:org_id ";
		}
		//if(is_graduated!=0){
			sql += " AND c.is_graduated=0 ";
		//}
		if(cam_id!=null&&cam_id!=0){
			sql += " AND c.cam_id=:cam_id ";
		}
		if(grade_id!=null&&grade_id!=0){
			sql += " AND g.grade_id=:grade_id ";
		}	
		sql+="GROUP BY c.clas_id ORDER BY c.clas_id DESC";
		
		Query query = session.createSQLQuery(sql.toString());
		if(org_id>0){
			query.setInteger("org_id", org_id);
		}
		//if(is_graduated!=0){
		//	query.setInteger("is_graduated", is_graduated);
		//}
		if(cam_id!=null&&cam_id!=0){
			query.setInteger("cam_id", cam_id);
		}
		if(grade_id!=null&&grade_id!=0){
			query.setInteger("grade_id", grade_id);
		}
		query.setFirstResult((pages.getPage()-1)*pages.getLimit()+pages.getStart());
		query.setMaxResults(pages.getLimit());
		query.setResultTransformer(Transformers.aliasToBean(Classes.class));
		@SuppressWarnings("unchecked")
		List<Classes> ls = query.list();
		for (Classes classes : ls) {
			LSHelper.processInjection(classes);
		}
		pages.setData(ls);
	}

	@Override
	public List<Classes> getClassByOrgIdCamIdGradeId(Integer org_id,
			Integer cam_id, Integer grade_id) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT c.* FROM grade2clas gc "
				+ "LEFT JOIN classes c ON gc.clas_id = c.clas_id "
				+ "AND c.is_del = 0 "
				+ "WHERE "
				+ "	gc.is_del = 0 "
				+ "AND gc.grade_id = :grade_id "
				+ "AND c.org_id = :org_id "
				+ "AND c.cam_id = :cam_id ";		
		sql+="ORDER BY c.clas_id DESC";		
		Query query = session.createSQLQuery(sql.toString());		
		query.setInteger("org_id", org_id);
		query.setInteger("cam_id", cam_id);	
		query.setInteger("grade_id", grade_id);		
		query.setResultTransformer(Transformers.aliasToBean(Classes.class));
		@SuppressWarnings("unchecked")
		List<Classes> ls = query.list();
		for (Classes classes : ls) {
			LSHelper.processInjection(classes);
		}
		return ls;
	}

	@Override
	public List<Classes> queryClassesByStudId(Integer stud_id,Integer org_id) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT "
				+ "c.*, gc.grade_id,ct.clas2stud_id "
				+ "FROM"
				+ "	class2student ct "
				+ "LEFT JOIN classes c ON ct.clas_id = c.clas_id "
				+ "AND c.is_del = 0 "
				+ "LEFT JOIN grade2clas gc ON c.clas_id = gc.clas_id "
				+ "AND gc.is_del = 0 "
				+ "WHERE "
				+ "ct.stud_id = :stud_id "
				+ "AND ct.is_del = 0 AND c.org_id=:org_id GROUP BY c.clas_id  ";		
		sql+="ORDER BY c.clas_id DESC";		
		Query query = session.createSQLQuery(sql.toString());		
		query.setInteger("stud_id", stud_id);	
		query.setInteger("org_id", org_id);	
		query.setResultTransformer(Transformers.aliasToBean(Classes.class));
		@SuppressWarnings("unchecked")
		List<Classes> ls = query.list();
		for (Classes classes : ls) {
			LSHelper.processInjection(classes);
		}
		return ls;
	}

	@Override
	public Classes getClassByName(Integer grade_id, String clas_name) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT c.* FROM classes c LEFT JOIN grade2clas gc ON c.clas_id=gc.clas_id AND gc.is_del=0 WHERE gc.grade_id=:grade_id AND c.clas_name=:clas_name AND c.is_del=0";		
		Query query = session.createSQLQuery(sql.toString());		
		query.setInteger("grade_id", grade_id);	
		query.setString("clas_name", clas_name);
		query.setResultTransformer(Transformers.aliasToBean(Classes.class));
		@SuppressWarnings("unchecked")
		List<Classes> ls = query.list();
		for (Classes classes : ls) {
			LSHelper.processInjection(classes);
		}
		return ls.size()>0?ls.get(0):null;
	}

	@Override
	public List<Classes> getClassesOfOrg(Integer org_id) {
		Session session=this.factory.getCurrentSession();
		String sql = " SELECT cs.clas_id,cs.clas_name,c.cam_id,c.cam_name,g.grade_id,g.grade_name FROM classes cs INNER JOIN campus c ON c.cam_id=cs.cam_id AND cs.org_id=c.org_id AND c.is_del=0 AND cs.is_graduated=0 INNER JOIN grade2clas gc ON cs.clas_id=gc.clas_id AND gc.is_del=0 INNER JOIN grade g ON g.grade_id=gc.grade_id AND g.org_id=cs.org_id AND g.org_id=c.org_id AND g.is_del=0 WHERE cs.org_id=:org_id AND cs.is_del=0";
		Query query = session.createSQLQuery(sql.toString());
		query.setResultTransformer(Transformers.aliasToBean(Classes.class));
		query.setInteger("org_id", org_id);
		@SuppressWarnings("unchecked")
		List<Classes> list = query.list();
		return list;
	}

	@Override
	public List<Classes> getClassListIncludeDeleted(int org_id) {
		String sql = "SELECT c.* FROM classes c WHERE c.org_id=?;";
		Session session=this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Classes.class));
		query.setInteger(0, org_id);
		return query.list();
	}

	@Override
	public List<Classes> getClassesOfOrgForRedis(Integer org_id) {
		String sql = "SELECT c.* FROM classes c WHERE c.org_id=?;";
		Session session=this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Classes.class));
		query.setInteger(0, org_id);
		return query.list();
	}

	
	/**
	 * 获取机构下未删除的所有班级（班级升级用，因此需要包含已毕业的班级）
	 * @param org_id
	 * @return
	 */
	@Override
	public List<Classes> getClassList4ClassUpgrade(int org_id) {
		String sql = "SELECT c.* FROM classes c WHERE c.org_id=?  AND c.is_del=0;";
		Session session=this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Classes.class));
		query.setInteger(0, org_id);
		return query.list();
	}

	@Override
	public Classes getClass(int org_id, String clas_name) {
		String sql = "SELECT g.*,g2.grade_id,g2.grade_name FROM classes g INNER  JOIN grade2clas g1 ON g.clas_id=g1.clas_id AND g.is_del=0 AND g1.is_del=0 INNER JOIN grade g2 ON g1.grade_id=g2.grade_id AND g2.is_del=0 WHERE g.org_id = ? AND g.clas_name =? AND g.is_del = 0";

		Session session = this.factory.getCurrentSession();

		Query q = session.createSQLQuery(sql);
		q.setResultTransformer(Transformers.aliasToBean(Classes.class));
		q.setInteger(0, org_id);
		q.setString(1, clas_name);

		List<Classes> resultList = q.list();
		Classes r = null;

		if (resultList != null && resultList.size() > 0) {
			r = resultList.get(0);
		}
		return r;
	}

	@Override
	public Classes getClassByNameOrg(Integer org_id,String clas_name) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT c.* FROM classes c  WHERE c.org_id=:org_id AND c.clas_name=:clas_name AND c.is_del=0";		
		Query query = session.createSQLQuery(sql.toString());	
		query.setInteger("org_id", org_id);
		query.setString("clas_name", clas_name);
		query.setResultTransformer(Transformers.aliasToBean(Classes.class));
		@SuppressWarnings("unchecked")
		List<Classes> ls = query.list();
		for (Classes classes : ls) {
			LSHelper.processInjection(classes);
		}
		return ls.size()>0?ls.get(0):null;
	}

}
