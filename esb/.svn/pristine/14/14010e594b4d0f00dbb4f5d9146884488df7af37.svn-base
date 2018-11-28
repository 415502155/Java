package cn.edugate.esb.imp;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.dao.CampusDAO;
import cn.edugate.esb.entity.Campus;
import cn.edugate.esb.service.CampusService;
import cn.edugate.esb.util.Constant;

@Component
@Transactional("transactionManager")
public class CampusServiceImpl  implements  CampusService{

	private static Logger logger = Logger.getLogger(CampusServiceImpl.class);
	
	@Autowired
	private CampusDAO campusDAO;
	
	
	@Override
	public void add(Campus campus) {
		// TODO Auto-generated method stub
		try {
			campusDAO.saveOrUpdate(campus);
		} catch (Exception e) {
			logger.error("Campus add error");	
		}
	}

	@Override
	public Integer delete(int id) {
		// TODO Auto-generated method stub
		Campus oldCampus = new Campus();
		try {
			oldCampus = campusDAO.get(Campus.class, id);
			if(oldCampus.getCam_type()!=0){
				oldCampus.setIs_del(Constant.IS_DEL_YES);
				oldCampus.setDel_time(new Date());
				campusDAO.update(oldCampus);
			}else{
				return -1;
			}
		} catch (Exception e) {
			logger.error("Classes delete error");	
			return 0;
		}
		return id;
	}

	@Override
	public Campus update(Campus campus) {
		// TODO Auto-generated method stub
		try {
			campus.setUpdate_time(new Date());
			campusDAO.update(campus);
		} catch (Exception e) {
			logger.error("Campus update error");	
			return null;
		}
		return campus;
	}

	@Override
	public Campus getTechById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Campus> getAll() {
		// TODO Auto-generated method stub
		//Criterion criterion = Restrictions.conjunction();
		Criterion criterion = Restrictions.and(Restrictions.eq("is_del", 0));
		return campusDAO.list(Campus.class, criterion);
	}

	@Override
	public List<Campus> getByOrgId(Integer org_id) {
		// TODO Auto-generated method stub
		Criterion criterion = Restrictions.and(Restrictions.eq("org_id", org_id)).add(Restrictions.eq("is_del", 0));
		List<Campus> ls = this.campusDAO.list(Campus.class, criterion);
		return ls;
	}

	@Override
	public Campus getCampusByType(int org_id) {
		// TODO Auto-generated method stub
		return campusDAO.getMainCampus(org_id).get(0);
	}

}
