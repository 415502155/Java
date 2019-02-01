package sng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sng.dao.ChargeRecordDao;
import sng.entity.ChargeRecord;
import sng.service.ChargeRecordService;


/**
 * 支付记录服务接口
 * Title: ChargeRecordServiceImpl
 * Description: 
 * Company: 世纪伟业
 * @author Liuyang
 * @date 2018年1月19日下午2:22:04
 */
@Component
@Transactional("transactionManager")
public class ChargeRecordServiceImpl implements ChargeRecordService {
	
	@Autowired
	private ChargeRecordDao chargeRecordDao;
	
	@Override
	public void save(ChargeRecord cr) {
		chargeRecordDao.save(cr);
	}

	@Override
	public List<ChargeRecord> getList(String[] ids) {
		return chargeRecordDao.getList(ids);
	}

	@Override
	public ChargeRecord getPayRecord(Integer cd_id) {
		return chargeRecordDao.getPayRecord(cd_id);
	}

	@Override
	public ChargeRecord getRefundByCdid(Integer cd_id) {
		return chargeRecordDao.getRefundByCdid(cd_id);
	}

	@Override
	public List<ChargeRecord> getRefundByCdids(String ids) {
		return chargeRecordDao.getRefundByCdid(ids);
	}
	
	@Override
	public ChargeRecord getRefundByCdidForCEB(Integer cd_id) {
		return chargeRecordDao.getRefundByCdidForCEB(cd_id);
	}
	
	@Override
	public ChargeRecord getById(int id) {
		return chargeRecordDao.get(ChargeRecord.class, id);
	}

	@Override
	public void cancel(ChargeRecord cr) {
		chargeRecordDao.cancel(cr);
	}

}
