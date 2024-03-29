package sng.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import sng.dao.ChargeRecordDao;
import sng.entity.ChargeRecord;
import sng.util.Constant;


/**
 * Title: Charge4TeacherDaoImpl
 * Description: 
 * Company: 世纪伟业
 * @author Liuyang
 * @date 2018年1月4日下午12:08:03
 */
@Repository
public class ChargeRecordDaoImpl extends BaseDaoImpl<ChargeRecord> implements ChargeRecordDao {


	@SuppressWarnings("unchecked")
	@Override
	public List<ChargeRecord> getList(ChargeRecord cr) {
		Session session = this.factory.getCurrentSession();
		String sql = "SELECT cr.*, cd.money FROM charge_record cr INNER JOIN charge_detail cd ON cr.cd_id = cd.cd_id AND cd.cid = cr.cid WHERE cd.is_del=0 ";
		if(null!=cr.getCid()){
			sql+=" AND cr.cid = :cid ";
		}
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(ChargeRecord.class));
		if(null!=cr.getCid()){
			query.setInteger("cid", cr.getCid());
		}
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChargeRecord> getList(String[] ids) {
		Session session = this.factory.getCurrentSession();
		String sql = "SELECT cr.* FROM charge_record cr WHERE cr.cd_id in ( :ids ) AND (cr.txnType = :SQYLTF OR cr.txnType = :SQXXTF) ";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(ChargeRecord.class));
		query.setParameterList("ids", ids);
		query.setString("SQYLTF", Constant.TXNTYPE_SJWY_ONLINE_REFUND_APPLY);
		query.setString("SQXXTF", Constant.TXNTYPE_SJWY_OFFLINE_REFUND_APPLY);
		return query.list();
	}

	@Override
	public ChargeRecord getPayRecord(Integer cd_id) {
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT cr.queryId FROM charge_record cr WHERE cr.cd_id=:cd_id AND cr.txnType = :txnType ";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(ChargeRecord.class));
		query.setInteger("cd_id", cd_id);
		query.setString("txnType", Constant.TXNTYPE_UNIONPAY_PAY);
		try {
			return (ChargeRecord) query.list().get(0);
		} catch (Exception e) {
			return new ChargeRecord();
		}
	}

	@Override
	public ChargeRecord getRefundByCdid(Integer cd_id) {
		Session session = this.factory.getCurrentSession();
		String sql = "SELECT cr.cr_id,cr.cid,cr.cd_id,cr.txnType,pay.txnAmt,cr.txnTime,pay.queryId,cr.org_user_id,cr.user_identify,cr.user_type_id,cr.user_type_name,cd.money FROM charge_record cr INNER JOIN charge_detail cd ON cr.cid=cd.cid AND cr.cd_id=cd.cd_id AND cd.is_del=0 INNER JOIN charge_record pay ON pay.cd_id=cd.cd_id AND pay.cd_id=cr.cd_id AND pay.cid=cd.cid AND pay.cid=cr.cid AND ( pay.txnType='01' OR pay.txnType='XXJF') WHERE cr.cd_id=:cd_id AND (cr.txnType=:SQYLTF or cr.txnType=:SQXXTF)";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(ChargeRecord.class));
		query.setInteger("cd_id", cd_id);
		query.setString("SQYLTF", Constant.TXNTYPE_SJWY_ONLINE_REFUND_APPLY);
		query.setString("SQXXTF", Constant.TXNTYPE_SJWY_OFFLINE_REFUND_APPLY);
		try {
			return (ChargeRecord) query.list().get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return new ChargeRecord();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChargeRecord> getRefundByCdid(String ids) {
		Session session = this.factory.getCurrentSession();
		String sql = "SELECT cd.cam_id,cr.cr_id,cr.cid,cr.cd_id,cr.txnType,pay.txnAmt,cr.txnTime,pay.queryId,cr.org_user_id,cr.user_identify,cr.user_type_id,cr.user_type_name,cd.money FROM charge_record cr INNER JOIN charge_detail cd ON cr.cid=cd.cid AND cr.cd_id=cd.cd_id AND cd.is_del=0 INNER JOIN charge_record pay ON pay.cd_id=cd.cd_id AND pay.cd_id=cr.cd_id AND pay.cid=cd.cid AND pay.cid=cr.cid AND ( pay.txnType='01' OR pay.txnType='XXJF') WHERE cr.cd_id IN ( :cd_id )  AND (cr.txnType=:SQYLTF or cr.txnType=:SQXXTF)";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(ChargeRecord.class));
		query.setParameterList("cd_id", ids.split(","));
		query.setString("SQYLTF", Constant.TXNTYPE_SJWY_ONLINE_REFUND_APPLY);
		query.setString("SQXXTF", Constant.TXNTYPE_SJWY_OFFLINE_REFUND_APPLY);
		try {
			return query.list();
		} catch (Exception e) {
			return new ArrayList<ChargeRecord>();
		}
	}
	
	@Override
	public ChargeRecord getRefundByCdidForCEB(Integer cd_id) {
		Session session = this.factory.getCurrentSession();
		String sql = "SELECT cr.cr_id,cr.cid,cr.cd_id,cr.txnType,cr.txnAmt,pay.txnTime,pay.queryId,cr.org_user_id,cr.user_identify,cr.user_type_id,cr.user_type_name,cd.money FROM charge_record cr INNER JOIN charge_detail cd ON cr.cid=cd.cid AND cr.cd_id=cd.cd_id AND cd.is_del=0 INNER JOIN charge_record pay ON pay.cd_id=cd.cd_id AND pay.cd_id=cr.cd_id AND pay.cid=cd.cid AND pay.cid=cr.cid AND ( pay.txnType='01' OR pay.txnType='XXJF') WHERE cr.cd_id=:cd_id AND (cr.txnType=:SQYLTF or cr.txnType=:SQXXTF)";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(ChargeRecord.class));
		query.setInteger("cd_id", cd_id);
		query.setString("SQYLTF", Constant.TXNTYPE_SJWY_ONLINE_REFUND_APPLY);
		query.setString("SQXXTF", Constant.TXNTYPE_SJWY_OFFLINE_REFUND_APPLY);
		try {
			return (ChargeRecord) query.list().get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return new ChargeRecord();
		}
	}

	@Override
	public void cancel(ChargeRecord cr) {
		Session session = this.factory.getCurrentSession();
		String sql = "UPDATE charge_record cr SET cr.is_del=1 AND cr.del_time=:del_time AND cr.del_user_id=:del_user_id WHERE cr.cr_id=:cr_id";
		Query query = session.createSQLQuery(sql);
		query.setInteger("cr_id", cr.getCr_id());
		query.setInteger("del_user_id", cr.getDel_user_id());
		query.setTimestamp("del_time", cr.getDel_time());
		query.executeUpdate();
	}

}
