package cn.edugate.esb.imp;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.dao.IWxConfigDao;
import cn.edugate.esb.entity.WxConfig;
import cn.edugate.esb.service.WxConfigService;

/**
 * WxConfigImpl
 */
@Component
@Transactional("transactionManager")
public class WxConfigImpl implements WxConfigService {
	@SuppressWarnings("unused")
	private IWxConfigDao wxConfigDao;

	@Autowired
	public void setWxConfigDao(IWxConfigDao wxConfigDao) {
		this.wxConfigDao = wxConfigDao;
	}

	@Override
	public List<WxConfig> getAll() {
		// TODO Auto-generated method stub
		Criterion criterion = Restrictions.conjunction();
		List<WxConfig> ls = this.wxConfigDao.list(WxConfig.class, criterion, Order.asc("org_id"));
		return ls;
	}

}
