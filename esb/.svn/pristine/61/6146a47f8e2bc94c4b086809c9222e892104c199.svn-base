package cn.edugate.esb.dao.imp;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IStudentParentDao;
import cn.edugate.esb.entity.StudentParent;

@Repository
public class IStudentParentImpl extends BaseDAOImpl<StudentParent> implements IStudentParentDao {

	@Override
	public List<StudentParent> getStudentParentListByStudentId(int studentId) {
		String sql = "SELECT s2p.* FROM student2parent s2p WHERE s2p.stud_id=? AND s2p.is_del=0;";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, studentId);
		query.setResultTransformer(Transformers.aliasToBean(StudentParent.class));
		
		List<StudentParent> resultList = query.list();
		
		return resultList;
	}
	/**
	 * 刷新缓存  用到此方法
	 * 
	 * **/
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentParent> getStudentParentListIncludeDeleted(int org_id) {
		String sql = " SELECT sp.*,p.org_id FROM student2parent sp INNER JOIN student s ON sp.stud_id=s.stud_id INNER JOIN parent p ON p.parent_id=sp.parent_id AND s.org_id=p.org_id WHERE p.org_id=:org_id AND s.is_del = 0 AND sp.is_del = 0 AND p.is_del = 0";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(StudentParent.class));
		query.setInteger("org_id", org_id);
		return query.list();
	}


}
