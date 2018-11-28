package cn.edugate.esb.imp;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.dao.IApplicationDao;
import cn.edugate.esb.service.ApplicationService;

@Component
@Transactional("transactionManager")
public class ApplicationImpl implements ApplicationService {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ApplicationImpl.class);
	
	@SuppressWarnings("unused")
	private IApplicationDao applicationDao;
	@Autowired
	public void setApplicationDao(IApplicationDao applicationDao) {
		this.applicationDao = applicationDao;
	}

}
