package sng.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sng.dao.CertificationRecordDao;
import sng.entity.CertifiationRecord;
import sng.service.CertificationRecordService;

@Component
@Transactional("transactionManager")
public class CertificationRecordServiceImpl implements CertificationRecordService {

	@Autowired
	private CertificationRecordDao certificationRecordDao;

	@Override
	public void savee(CertifiationRecord cfr) {
		certificationRecordDao.save(cfr);
	}

}
