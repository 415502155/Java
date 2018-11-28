package cn.edugate.esb.imp;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.dao.IAppDao;
import cn.edugate.esb.entity.App;
import cn.edugate.esb.service.AppService;
import cn.edugate.esb.util.Paging;

@Component
@Transactional("transactionManager")
public class AppImpl implements AppService {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(AppImpl.class);
	
	private IAppDao appDao;	
	
	@Autowired
	public void setAppDao(IAppDao appDao) {
		this.appDao = appDao;
	}
	
	@Override
	public void getAppWithPaging(Paging<App> pages) {
		// TODO Auto-generated method stub
		Long totalcount = this.appDao.getTotalCount();
		pages.setTotal(totalcount);
		this.appDao.getAppWithPaging(pages);
	}

	@Override
	public void add(App app) {
		// TODO Auto-generated method stub
		this.appDao.saveOrUpdate(app);
	}

	@Override
	public App getById(Integer appId) {
		// TODO Auto-generated method stub
		return this.appDao.get(App.class, appId);
	}

	@Override
	public void update(App app) {
		// TODO Auto-generated method stub
		this.appDao.saveOrUpdate(app);
	}

	@Override
	public List<App> getAll() {
		// TODO Auto-generated method stub
		Criterion criterion  = Restrictions.conjunction();
		List<App> ls = this.appDao.list(App.class, criterion, Order.asc("a_id"));
		return ls;
	}

}
