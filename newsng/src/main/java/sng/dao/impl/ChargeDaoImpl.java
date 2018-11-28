package sng.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import sng.comparator.ChargeSorter;
import sng.dao.ChargeDao;
import sng.entity.Account;
import sng.entity.Charge;
import sng.util.Constant;
import sng.util.MoneyUtil;
import sng.util.Paging;


/**
 * Title: ChargeDaoImpl
 * Description: 
 * Company: 世纪伟业
 * @author Liuyang
 * @date 2018年1月4日下午12:05:20
 */
@Repository
public class ChargeDaoImpl extends BaseDaoImpl<Charge> implements ChargeDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Charge> getTList(String classIds, Integer max, Integer min, Integer num) {
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT c.cid,c.item,c.pay_type,c.refund,c.money,c.end_time,cd.insert_time,cd.start_time,c.content FROM charge c INNER JOIN charge_detail cd ON c.cid=cd.cid AND cd.is_del=0 WHERE c.is_del = 0 AND cd.clas_id IN (:clas_id) GROUP BY c.cid ORDER BY c.start_time DESC";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Charge.class));
		query.setParameterList("clas_id", classIds.split(","));
		List<Charge> list = query.list();
		if(null!=list&&list.size()!=0){
			ChargeSorter mc = new ChargeSorter();  
			Collections.sort(list,mc); 
			List<Charge> reList = new ArrayList<Charge>();
			int size = list.size()>num?num:list.size();
			if(null!=max&&max>0){//刷新，查比较新的记录
				int count = 0;
				for (Charge c : list) {
					if(!c.getCid().equals(max)&&count<size){
						reList.add(c);
						count++;
					}else{
						break;
					}
				}
			}else if(null!=min&&min>0){//下拉，查比较旧的记录
				int count = -1;
				for (Charge c : list) {
					if(c.getCid().equals(min)){
						count=0;
					}else if(count>-1&&count<size){
						reList.add(c);
						count++;
					}else if(count>=size){
						break;
					}
				}
			}else{//默认，查最新的num条记录
				for (int i = 0; i < size; i++) {
					reList.add(list.get(i));
				}
			}
			return reList;
		}else{
			return new ArrayList<Charge>();
		}
	}

	@Override
	public Paging<Charge> getTList(Charge charge, Paging<Charge> page) {
		Session session = this.factory.getCurrentSession();
		String sql = "SELECT c.cid, c.`status`, c.item, c.pay_type, c.start_time, c.end_time, c.ys_num, c.tf_num_all, c.tf_num_part, c.ss_num, c.ss_money, c.tf_money_all, c.tf_money_part, c.nopay_num, c.refund, concat((CASE aa.type WHEN "+Constant.ACCOUNT_TYPE_ORG+" THEN '（自收）' WHEN "+Constant.ACCOUNT_TYPE_SJWY+" THEN '（代收）' ELSE '' END),aa .accName,' ',insert(aa.accNo,7,9,'********')) AS accInfo  FROM charge c left join account aa on c.accId=aa.accId WHERE c.is_del=0 ";
		String countSql = "SELECT count(1) FROM charge c left join account aa on c.accId=aa.accId WHERE c.is_del=0 ";
		if(null!=charge.getOrg_id()){
			sql += " AND c.org_id=:org_id ";
			countSql += " AND c.org_id=:org_id ";
		}
		if(null!=charge.getStart_time()){
			sql += " AND c.start_time > :start_time";
			countSql += " AND c.start_time > :start_time";
		}
		if(null!=charge.getEnd_time()){
			sql += " AND c.end_time < :end_time ";
			countSql += " AND c.end_time < :end_time ";
		}
		if(StringUtils.isNotEmpty(charge.getItem())){
			sql += " AND c.item like :item ";
			countSql += " AND c.item like :item ";
		}
		if(null!=charge.getStatus()){
			sql += " AND c.status=:status ";
			countSql += " AND c.status=:status ";
			if(Constant.CHARGE_STATUS_FINISHED.equals(charge.getStatus())){
				sql += " AND c.end_time <=  SYSDATE() ";
				countSql += " AND c.end_time <=  SYSDATE() ";
			}else{
				sql += " AND c.end_time >  SYSDATE() ";
				countSql += " AND c.end_time >  SYSDATE() ";
			}
		}
		sql += " ORDER BY c.start_time, c.cid DESC LIMIT :pagenum,:pagesize ";
		Query query = session.createSQLQuery(sql);
		Query countQuery = session.createSQLQuery(countSql);
		query.setResultTransformer(Transformers.aliasToBean(Charge.class));
		if(null!=charge.getOrg_id()){
			query.setInteger("org_id",charge.getOrg_id());
			countQuery.setInteger("org_id",charge.getOrg_id());
		}
		if(null!=charge.getStart_time()){
			query.setTimestamp("start_time", charge.getStart_time());
			countQuery.setTimestamp("start_time", charge.getStart_time());
		}
		if(null!=charge.getEnd_time()){
			query.setTimestamp("end_time", charge.getEnd_time());
			countQuery.setTimestamp("end_time", charge.getEnd_time());
		}
		if(StringUtils.isNotEmpty(charge.getItem())){
			query.setString("item", "%"+charge.getItem()+"%");
			countQuery.setString("item", "%"+charge.getItem()+"%");
		}
		if(null!=charge.getStatus()){
			if(Constant.CHARGE_STATUS_FINISHED.equals(charge.getStatus())){
				query.setInteger("status", Constant.CHARGE_STATUS_PUSH_OK);
				countQuery.setInteger("status", Constant.CHARGE_STATUS_PUSH_OK);
			}else{
				query.setInteger("status", charge.getStatus());
				countQuery.setInteger("status", charge.getStatus());
			}
		}
		query.setInteger("pagenum",  (page.getPage() - 1) * page.getLimit());
		query.setInteger("pagesize", page.getLimit());
		@SuppressWarnings("unchecked")
		List<Charge> list = query.list();
		for (Charge c : list) {
			try {
				c.setSs_money(MoneyUtil.fromFENtoYUAN(new BigDecimal(c.getSs_money()).subtract(new BigDecimal(c.getSt_money())).toString()));
			} catch (Exception e) {
				c.setSs_money("N/A");
			}
		}
		BigInteger pageTotal = (BigInteger) countQuery.uniqueResult();
		page.setTotal(pageTotal.intValue());
		page.setSize(page.getTotal()%page.getLimit()==0 ? (page.getTotal()/page.getLimit()) : (page.getTotal()/page.getLimit()+1));
		page.setData(list);
		page.setSuccess(true);
		return page;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Charge> getTodayCharge(Integer org_id,Date date) {
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT c.cid, c.item, c.ss_num, c.nopay_num, c.tf_num_all, c.ys_num, c.tf_num_part, c.money, c.pay_type, c.refund, c.status, c.content FROM charge c WHERE to_days(c.start_time) <= to_days( :date ) AND to_days(c.end_time) >= to_days( :date ) AND c.org_id = :org_id ";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Charge.class));
		query.setInteger("org_id", org_id);
		query.setDate("date", date);
		return query.list();
	}

	@Override
	public Integer duplication(String org_id, String item, String money, String range, Integer payType) {
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT '1' AS item FROM charge c WHERE c.item=:item AND c.money=:money AND c.org_id=:org_id AND c.sf_range=:range AND c.pay_type=:pay_type AND c.insert_time > :time ";
		Query query = session.createSQLQuery(sql);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY,-24);
		query.setResultTransformer(Transformers.aliasToBean(Charge.class));
		query.setDate("time", c.getTime());
		query.setInteger("org_id", Integer.parseInt(org_id));
		query.setInteger("pay_type", payType);
		query.setString("item", item);
		query.setString("money", money);
		query.setText("range", range);
		@SuppressWarnings("unchecked")
		List<Charge> list = query.list();
		if(null!=list&&list.size()!=0){
			return list.size();
		}else{
			return 0;
		}
	}

	@Override
	public Charge getCharge(Integer org_id, Integer cam_id, Integer clas_id) {
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT c.cid,c.start_time,c.end_time,c.order_prefix,c.money,c.pay_type,c.item,c.accId FROM charge c WHERE c.org_id=:org_id AND c.cam_id=:cam_id AND c.clas_id=:clas_id AND c.is_del=:is_del ";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Charge.class));
		query.setInteger("org_id", org_id);
		query.setInteger("cam_id", cam_id);
		query.setInteger("clas_id", clas_id);
		query.setInteger("is_del", Constant.IS_DEL_NO);
		@SuppressWarnings("unchecked")
		List<Charge> list = query.list();
		if(null!=list&&list.size()!=0){
			return list.get(0);
		}else{
			return new Charge();
		}
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> getCEBList() {
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT a.org_id,a.billNum,a.fileNum FROM account a WHERE a.accType=:accType AND a.is_del=0 GROUP BY a.fileNum ";
		Query query = session.createSQLQuery(sql);
		query.setInteger("accType", Constant.ACCTYPE_CEB);
		query.setResultTransformer(Transformers.aliasToBean(Account.class));
		return query.list();
	}
}
