package cn.edugate.esb.imp;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.dao.IIconDao;
import cn.edugate.esb.dao.IOrgIconDao;
import cn.edugate.esb.entity.Icon;
import cn.edugate.esb.entity.OrgIcon;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.redis.dao.RedisOrganizationDao;
import cn.edugate.esb.service.IconService;
import cn.edugate.esb.util.Constant;

/**
 * Title: IconImpl
 * Description:图标 
 * Company: 世纪伟业
 * @author Liu Yang
 * @date 2018年8月14日下午1:47:04
 */
@Component
@Transactional("transactionManager")
public class IconImpl implements IconService {

	@Autowired
	private IIconDao iconDao;
	@Autowired
	private IOrgIconDao orgIconDao;
	
	@Autowired
	private RedisOrganizationDao redisOrganizationDao;

	@Override
	public List<Icon> getOnUsing(Integer org_id,Integer identity) {
		return iconDao.getOnUsing(org_id,identity);
	}

	@Override
	public Map<Integer, List<Icon>> getIconList() {
		return iconDao.getIconList();
	}

	@Override
	public void refreshDB() {
		List<Icon> list = iconDao.getAllList();
		List<Organization> orgList = redisOrganizationDao.getOrgsByType(Constant.FUR_TYPE_SCHOOLS+"");
		List<OrgIcon> oiList = new ArrayList<OrgIcon>();
		for (Icon icon : list) {
			for (Organization org : orgList) {
				OrgIcon oi = new OrgIcon();
				oi.setIcon_id(icon.getIcon_id());
				oi.setInsert_time(new Date());
				oi.setIs_del(Constant.IS_DEL_NO);
				oi.setOrg_id(org.getOrg_id());
				oi.setOrg_order(icon.getOrder());
				oi.setP_show(icon.getP_show());
				oi.setS_show(icon.getS_show());
				oi.setT_show(icon.getT_show());
				oi.setW_show(icon.getW_show());
				oi.setStatus(icon.getStatus());
				oiList.add(oi);
			}
		}
		OrgIcon[] icons = new OrgIcon[oiList.size()];
		orgIconDao.save(oiList.toArray(icons));
	}
}
