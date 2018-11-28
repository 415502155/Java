package cn.edugate.esb.dao.imp;
 
import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.CampusDAO;
import cn.edugate.esb.entity.Campus;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Utils;
@Repository
public class CampusDAOImpl extends BaseDAOImpl<Campus> implements CampusDAO{

	@Override
	public int saveCampus(Campus campus) {
		int result = 0;
		String campusName = campus.getCam_name();
		if (StringUtils.isNotBlank(campusName)) {
			campusName = campusName.trim();
			if ("主校区".equals(campusName)) {
				// 添加分校区名称为主校区的，需要先查询是否已存在
				String querySQL = "SELECT COUNT(c.cam_id) FROM edugate_base.campus c WHERE c.org_id=? AND c.cam_name='主校区' AND c.is_del=0;";
				Session session = this.factory.getCurrentSession();
				Query query  = session.createSQLQuery(querySQL);
				query.setInteger(0, campus.getOrg_id());
				
				BigInteger count = (BigInteger) query.uniqueResult();
				if (count.intValue() == 0) {
					// 不存在进行新增
					String insertSQL = "INSERT INTO edugate_base.campus (org_id, cam_name, cam_note, insert_time) VALUES (?, ?, ?, NOW());";
					
					query = session.createSQLQuery(insertSQL);
					query.setInteger(0, campus.getOrg_id());
					query.setString(1, campus.getCam_name().trim());
					query.setString(2, Utils.getValue(campus.getCam_note()));
					result = query.executeUpdate();
				} else {
					// 已存在进行更新
					String updateSQL = "UPDATE edugate_base.campus c SET c.cam_note=? WHERE c.org_id=? AND c.cam_name='主校区' AND c.is_del=0;";
					
					query = session.createSQLQuery(updateSQL);
					query.setString(0, Utils.getValue(campus.getCam_note()));
					query.setInteger(1, campus.getOrg_id());
					
					result = query.executeUpdate();
				}
			} else {
				String insertSQL = "INSERT INTO edugate_base.campus (org_id, cam_name, cam_note, insert_time) VALUES (?, ?, ?, NOW());";
				Session session = this.factory.getCurrentSession();
				Query query = session.createSQLQuery(insertSQL);
				query.setInteger(0, campus.getOrg_id());
				query.setString(1, campus.getCam_name().trim());
				query.setString(2, Utils.getValue(campus.getCam_note()));
				result = query.executeUpdate();
			}
		} else {
			result = 1;
		}

		return result;
	}

	@Override
	public List<Campus> getCampusList(int orgID) {
		Session session = this.factory.getCurrentSession();
		Criteria c = session.createCriteria(Campus.class);
		c.add(Restrictions.eq("org_id", orgID));
		c.add(Restrictions.eq("is_del", Constant.IS_DEL_NO));
		@SuppressWarnings("unchecked")
		List<Campus> ls = c.list();
		return ls;
	}
	
	

	@Override
	public int saveOrUpdateCampus(Campus campus) {
		int result = 0;
		if (campus.getCam_id() != null && campus.getCam_id() != 0) {
			// 已存在分校区ID，则进行更新
			result = updateCampus(campus);
		} else {
			// 不存在ID，进行新增
			result = this.saveCampus(campus);
		}
		return result;
	}

	@Override
	public int updateCampus(Campus campus) {
		int result = 0;
		String campusName = campus.getCam_name();
		// 如果名称为空则忽略
		if (StringUtils.isNotBlank(campusName)) {
			campusName = campusName.trim();
			
			String updateSQL = "UPDATE edugate_base.campus c SET c.cam_name=?, c.cam_note=? WHERE c.cam_id=? AND c.org_id=? AND c.is_del=0;";
			if ("主校区".equals(campusName)) {
				String querySQL = "SELECT c.cam_name FROM edugate_base.campus c WHERE c.cam_id=? AND c.org_id=? AND c.is_del=0;";
				Session session = this.factory.getCurrentSession();
				Query query = session.createSQLQuery(querySQL);
				query.setInteger(0, campus.getCam_id());
				query.setInteger(1, campus.getOrg_id());
				
				String queryResult = (String) query.uniqueResult();
				if ("主校区".equals(queryResult)) {
					// 此条记录修改前就是主校区，则只更新note字段
					query = session.createSQLQuery(updateSQL);
					query.setString(0, "主校区");
					query.setString(1, Utils.getValue(campus.getCam_note()));
					query.setInteger(2, campus.getCam_id());
					query.setInteger(3, campus.getOrg_id());
					result = query.executeUpdate();
				} else {
					// 此条记录修改前不是主校区，无法进行更新
					result = 0;
				}
			} else {
				// 直接更新
				Session session = this.factory.getCurrentSession();
				Query query = session.createSQLQuery(updateSQL);
				query.setString(0, campus.getCam_name().trim());
				query.setString(1, Utils.getValue(campus.getCam_note()));
				query.setInteger(2, campus.getCam_id());
				query.setInteger(3, campus.getOrg_id());
				result = query.executeUpdate();
			}
		}
		
		return result;
	}

	@Override
	public List<Campus> getMainCampus(int orgID) {
		String querySQL = "SELECT c.* FROM edugate_base.campus c WHERE c.org_id=? AND c.cam_type=0 AND c.is_del=0;";
		Session session = this.factory.getCurrentSession();
		Query query  = session.createSQLQuery(querySQL);
		query.setInteger(0, orgID);
		query.setResultTransformer(Transformers.aliasToBean(Campus.class));
		List<Campus> list = query.list();
		return list;
	}

}
