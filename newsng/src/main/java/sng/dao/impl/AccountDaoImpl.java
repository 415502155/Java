package sng.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import sng.dao.AccountDao;
import sng.entity.Account;
import sng.util.Constant;

/**
 * 账户信息DAO实现
 * Title: AccountDaoImpl
 * Description: 
 * Company: 世纪伟业
 * @author Liuyang
 * @date 2018年1月24日下午2:38:48
 */
@Repository
public class AccountDaoImpl extends BaseDaoImpl<Account> implements AccountDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> getList(Integer org_id) {
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT a.accId,insert(a.accNo,7,7,'*******') as accNo,a.type,a.bankName,a.accName,a.accType FROM account a WHERE (a.org_id=:org_id OR a.type=:type) ORDER BY a.accId ASC ";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Account.class));
		query.setInteger("org_id", org_id);
		query.setInteger("type", Constant.ACCOUNT_TYPE_SJWY);
		return query.list();
	}

	@Override
	public Account getById(Integer accId) {
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT a.accId,insert(a.accNo,7,7,'*******') as accNo,a.type,a.bankName FROM account a WHERE a.accId=:accId ";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Account.class));
		query.setInteger("accId", accId);
		return (Account) query.list().get(0);
	}

	@Override
	public Account getByCId(Integer cid) {
		Session session = this.factory.getCurrentSession();
		/*************光大退费临时代码 START*************account表加作废字段，org_id从0还原,该SQL语句加a.org_id=c.org_id条件*************/
		String sql = " SELECT a.accType,a.org_id FROM account a INNER JOIN charge c ON a.accId=c.accId WHERE c.cid=:cid ";
		/**************光大退费临时代码 END**************account表加作废字段，org_id从0还原,该SQL语句加a.org_id=c.org_id条件**************/
		Query query = session.createSQLQuery(sql);
		query.setInteger("cid", cid);
		query.setResultTransformer(Transformers.aliasToBean(Account.class));
		return (Account) query.list().get(0);
	}

	@Override
	public Account getAccount(Integer org_id, Integer cam_id, Integer yes) {
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT a.accId FROM account a WHERE a.org_id=:org_id AND a.is_default=:is_default AND a.is_del=:is_del ";
		Query query = session.createSQLQuery(sql);
		query.setInteger("org_id", org_id);
		query.setInteger("is_default", yes);
		query.setInteger("is_del", Constant.IS_DEL_NO);
		query.setResultTransformer(Transformers.aliasToBean(Account.class));
		@SuppressWarnings("unchecked")
		List<Account> list = query.list();
		for (Account acc : list) {
			if(null!=acc.getCam_id()&&acc.getCam_id().intValue()==cam_id.intValue()){
				return acc;
			}
		}
		for (Account acc : list) {
			if(null==acc.getCam_id()||0==acc.getCam_id().intValue()){
				return acc;
			}
		}
		if(null!=list&&list.size()!=0){
			return (Account) query.list().get(0);
		}else{
			return new Account();
		}
	}

	@Override
	public Account getByOrgId(Integer org_id, Integer type, Integer accType) {
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT a.accId,insert(a.accNo,7,7,'*******') as accNo,a.type,a.bankName,a.signCert,a.pwd FROM account a WHERE 1=1 ";
		if(null!=org_id){
			sql += " AND org_id=:org_id ";
		}
		if(null!=type){
			sql += " AND type=:type ";
		}
		if(null!=accType){
			sql += " AND accType=:accType ";
		}
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Account.class));
		if(null!=org_id){
			query.setInteger("org_id", org_id);
		}
		if(null!=type){
			query.setInteger("type", type);
		}else{
			query.setInteger("type", Constant.ACCOUNT_TYPE_ORG);
		}
		if(null!=accType){
			query.setInteger("accType", accType);
		}else{
			query.setInteger("accType", Constant.ACCTYPE_UNIONPAY);
		}
		return (Account) query.list().get(0);
	}
	
	

}
