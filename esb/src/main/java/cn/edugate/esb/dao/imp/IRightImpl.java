package cn.edugate.esb.dao.imp;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IRightDao;
import cn.edugate.esb.entity.Right;
import cn.edugate.esb.util.Paging;

@Repository
public class IRightImpl extends BaseDAOImpl<Right> implements IRightDao {

	@Override
	public void getAllWithPaging(Paging<Right> paging,
			String searchName) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql="SELECT * FROM `right` WHERE is_del=0";
		if (searchName!=null&!"".equals(searchName)) {
			sql+=" AND right_name like :searchName";
		}
		
		sql+= " ORDER BY right_id ASC";
		Query query = session.createSQLQuery(sql);
		if (searchName!=null&!"".equals(searchName)) {
			query.setString("searchName","%"+searchName+"%");
		}
		query.setFirstResult((paging.getPage()-1)*paging.getLimit()+paging.getStart());
		query.setMaxResults(paging.getLimit());
        query.setResultTransformer(Transformers.aliasToBean(Right.class));
        @SuppressWarnings("unchecked")
		List<Right> ls = query.list();
        paging.setData(ls);
	}

	@Override
	public Long getTotalCountByName(String searchName) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql="SELECT COUNT(*) FROM `right` WHERE is_del=0";
		if (!"".equals(searchName)) {
			sql+=" AND right_name like :searchName";
		}		
		sql+= " ORDER BY right_id ASC";
		Query query = session.createSQLQuery(sql);
		if (!"".equals(searchName)) {
			query.setString("searchName","%"+searchName+"%");
		}		
		BigInteger count = (BigInteger) query.uniqueResult();
		return count.longValue();
	}

}
