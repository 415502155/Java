package cn.edugate.esb.dao.imp;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import cn.edugate.esb.dao.ICardDao;
import cn.edugate.esb.entity.Card;

@Repository
public class ICardImpl extends BaseDAOImpl<Card> implements ICardDao {
	
	@Override
	public List<Card> getCardsByStudentId(Integer stud_id) {
		// TODO Auto-generated method stub
		Session session = this.factory.getCurrentSession();
		String sql = "SELECT * FROM card WHERE stud_id=:stud_id";
		Query query = session.createSQLQuery(sql);
		query.setInteger("stud_id", stud_id);
		query.setResultTransformer(Transformers.aliasToBean(Card.class));
		@SuppressWarnings("unchecked")
		List<Card> list = query.list();
		return list;
	}

	@Override
	public List<Card> getCardListByClassId(int classId) {
		String sql = "SELECT c.* FROM card c WHERE c.stud_id IN (SELECT c2s.stud_id FROM class2student c2s WHERE c2s.clas_id=? AND c2s.is_del=0)";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, classId);
		query.setResultTransformer(Transformers.aliasToBean(Card.class));

		List<Card> list = query.list();
		return list;
	}

	
}
