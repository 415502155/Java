package sng.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import sng.dao.AccountDao;
import sng.dao.ChargeDao;
import sng.dao.ChargeDetailDao;
import sng.dao.ChargeRecordDao;
import sng.dao.ClassDao;
import sng.dao.StudentClassDao;
import sng.entity.Account;
import sng.entity.Charge;
import sng.entity.ChargeDetail;
import sng.entity.ChargeRecord;
import sng.entity.Classes;
import sng.entity.StudentClass;
import sng.service.ChargeService;
import sng.util.Constant;
import sng.util.MoneyUtil;

/**
 * Title: ChargeService
 * Description: 支付接口
 * Company: 世纪伟业
 * @author Liuyang
 * @date 2018年1月4日下午1:22:21
 */
@Component
@Transactional("transactionManager")
public class ChargeServiceImpl implements ChargeService{

	@Autowired
	private ChargeDao chargeDao;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private ChargeDetailDao chargeDetailDao;
	@Autowired
	private ChargeRecordDao chargeRecordDao;
	@Autowired
	private ClassDao classDao;
	@Autowired  
    private ApplicationContext ctx;
	@Autowired  
	private StudentClassDao studentClassDao;
	@Autowired  
	private ApplyServiceImpl applyServiceImpl;
	
	@Override
	public int createChargeForNewClass(Classes classes) {
		Charge charge = new Charge();
		Calendar cal = Calendar.getInstance();
		Account account = accountDao.getAccount(classes.getOrg_id(),classes.getCam_id(),Constant.YES);
		charge.setAccId(account.getAccId());
		charge.setCam_id(classes.getCam_id());
		charge.setClas_id(classes.getClas_id());
		charge.setContent(classes.getClas_name()+"学费");
		cal.setTime(new Date());
		charge.setStart_time(cal.getTime());
		cal.add(Calendar.YEAR, 1);
		charge.setEnd_time(cal.getTime());
		charge.setInsert_time(new Date());
		charge.setIs_del(Constant.IS_DEL_NO);
		charge.setItem(classes.getClas_name()+"学费");
		charge.setNote("系统创建的班级学费项目");
		charge.setOrder_prefix("9"+String.format("%05d", classes.getOrg_id())+classes.getCam_id());
		charge.setOrg_id(classes.getOrg_id());
		charge.setPay_type(Constant.PAY_TYPE_UNIFIED);
		charge.setRefund(Constant.YES);
		charge.setStatus(Constant.CHARGE_STATUS_PUSH_OK);
		charge.setType(Constant.CHARGE_TYPE_TUITION);
		charge.setNopay_num(0);
		charge.setSs_num(0);
		charge.setYs_num(0);
		charge.setSt_num(0);
		charge.setSs_money("0");
		charge.setYs_money("0");
		charge.setSt_money("0");
		charge.setMoney(classes.getTuition_fees());
		try {
			charge.setTxnAmt(MoneyUtil.fromYUANtoFEN(classes.getTuition_fees()));
			chargeDao.save(charge);
			return charge.getCid();
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public Charge updateStatus(List<ChargeRecord> list, Charge charge) {
		try {
			Long ss_money = 0l;
			Integer ss_num = 0;
			Long st_money = 0l;
			Integer st_num = 0;
			for (ChargeRecord cr : list) {
				try {
					switch (cr.getTxnType()) {
						case Constant.TXNTYPE_UNIONPAY_PAY:
							ss_money+=Long.parseLong(cr.getTxnAmt());
							ss_num++;
							break;
						case Constant.TXNTYPE_UNIONPAY_REFUND:
							st_money+=Long.parseLong(cr.getTxnAmt());
							st_num++;
							break;
						case Constant.TXNTYPE_SJWY_OFFLINE_PAY:
							ss_money+=Long.parseLong(cr.getTxnAmt());
							ss_num++;
							break;
						case Constant.TXNTYPE_SJWY_OFFLINE_REFUND_AGREE:
							st_money+=Long.parseLong(cr.getTxnAmt());
							st_num++;
							break;
						default:
							break;
					}
				} catch (Exception e) {
				}
			}
			charge.setSs_money(ss_money.toString());
			charge.setSs_num(ss_num);
			charge.setSt_money(st_money.toString());
			charge.setSt_num(st_num);
			//实收人数等于应收人数
			charge.setYs_money(ss_money.toString());
			charge.setYs_num(ss_num);
			chargeDao.update(charge);
		} catch (Exception e) {
		}
		return charge;
	}

	@Override
	public Charge getById(Integer cid) {
		return chargeDao.get(Charge.class, cid);
	}

	@Override
	public Boolean chargeFinally(Charge charge, ChargeDetail cd, ChargeRecord cr, Integer stu_status, Integer isHold, boolean immediateRelease) {
		HibernateTransactionManager txManager = (HibernateTransactionManager) ctx.getBean("transactionManager");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
		TransactionStatus txStatus = txManager.getTransaction(def);// 获得事务状态
		try {
			chargeRecordDao.save(cr);
			chargeDetailDao.update(cd);
			ChargeRecord query = new ChargeRecord();
			query.setCid(cd.getCid());
			List<ChargeRecord> crlist = chargeRecordDao.getList(query);
			if(0!=charge.getNopay_num()){
				Integer no_pay_num = chargeDetailDao.countNoPay(cd.getCid());
				charge.setNopay_num(no_pay_num);
			}
			updateStatus(crlist,charge);
			Classes clas = classDao.get(Classes.class, charge.getClas_id());
			clas.setSs_money(charge.getSs_money());
			clas.setYs_money(charge.getYs_money());
			clas.setSt_money(charge.getSt_money());
			clas.setYs_num(charge.getYs_num());
			clas.setSt_num(charge.getSt_num());
			clas.setSs_num(charge.getSs_num());
			boolean clasUpdated = false;
			if(null!=stu_status){
				StudentClass sc = studentClassDao.get(StudentClass.class, cd.getStu_class_id());
				sc.setStuStatus(stu_status);
				if(Constant.YES==isHold){
					sc.setQuotaHold(isHold);
				}else if(Constant.NO==isHold&&immediateRelease){
					sc.setQuotaHold(isHold);
				}
				studentClassDao.update(sc);
				if(Constant.YES==isHold){
					clasUpdated = true;
					applyServiceImpl.doUsableNum(clas); 
				}else if(Constant.NO==isHold&&immediateRelease){
					clasUpdated = true;
					applyServiceImpl.doUsableNum(clas); 
				}else if(Constant.NO==isHold){
					applyServiceImpl.addQuotaHoldMq(sc.getCamId(), sc.getClasId(), sc.getStudId());
				}
			}
			if(!clasUpdated){
				classDao.update(clas);
			}
			txManager.commit(txStatus);
			return true;
		} catch (Exception e) {
			txManager.rollback(txStatus);
			return false;
		}
	}


	@Override
	public List<Account> getCEBList() {
		return chargeDao.getCEBList();
	}

	@Override
	public List<Charge> getTList(Integer term_id,Integer teacher_id) {
		List<Charge> list = chargeDao.getTList(term_id,teacher_id);
		for (Charge charge : list) {
			try {
				charge.setSs_money(MoneyUtil.fromFENtoYUAN(charge.getSs_money()));
			} catch (Exception e) {
				charge.setSs_money("--");
			}
			try {
				charge.setSt_money(MoneyUtil.fromFENtoYUAN(charge.getSt_money()));
			} catch (Exception e) {
				charge.setSt_money("--");
			}
		}
		return list;
	}
	
}
