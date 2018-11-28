package cn.edugate.esb.dao.imp;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IExcelResDao;
import cn.edugate.esb.entity.ExcelRes;

@Repository
public class IExcelResImpl extends BaseDAOImpl<ExcelRes> implements IExcelResDao {

	@Override
	public List<ExcelRes> getExcelResList(int type, int user_id, int org_id, int proce_type) {
		String sql = "SELECT re.* FROM edugate_base.res_excel re WHERE re.org_id=:org_id AND re.proce_type=:proce_type AND re.deleted=0 ORDER BY re.created_time DESC;";
		
		Session session=this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
//		query.setInteger(0, type);
//		query.setInteger(1, user_id);
		query.setInteger("org_id", org_id);
		query.setInteger("proce_type", proce_type);
		query.setResultTransformer(Transformers.aliasToBean(ExcelRes.class));
		
		@SuppressWarnings("unchecked")
		List<ExcelRes> list = query.list();
		return list;
	}

}
