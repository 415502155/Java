package sng.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import sng.dao.VoucherModelDao;
import sng.entity.Vouchermodel;

/***
 * 
 *  @Company sjwy
 *  @Title: VoucherModelService.java 
 *  @Description: 凭证模板实现类
 *	@author: shy
 *  @date: 2018年11月1日 下午2:56:33
 */
@Repository
public class VoucherModelDaoImpl extends BaseDaoImpl<Vouchermodel> implements VoucherModelDao{

	@Override
	public Integer VouModelNum(Integer orgId) {
		StringBuffer sql = new StringBuffer();
		String ssql = " SELECT COUNT(1) FROM vouchermodel v WHERE v.is_del = 0 ";
		sql.append(ssql);
		List<Object> params = new ArrayList<Object>();
		if (orgId != null) {
			sql.append(" AND v.org_id = ? ");
			params.add(orgId);
		}
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		BigInteger count = (BigInteger) query.uniqueResult();
		return count.intValue();
	}

	@Override
	public void update(Integer orgId, String picName, String voucherContent, Integer voucherModelId, Integer length,
			Integer width) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Vouchermodel> getVouModel(Integer orgId) {
		StringBuffer sql = new StringBuffer(" SELECT v.voucher_model_id, v.org_id, v.voucher_name, v.voucher_content, v.serial_number_format, v.background_length, v.background_width, v.img_url  FROM vouchermodel v WHERE v.is_del = 0  ");
		List<Object> params = new ArrayList<Object>();
		if (orgId != null) {
			sql.append(" AND v.org_id = ?  ");
			params.add(orgId);
		}
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		query.setResultTransformer(Transformers.aliasToBean(Vouchermodel.class));
		List<Vouchermodel> list = query.list();
		return list;
	}
}
