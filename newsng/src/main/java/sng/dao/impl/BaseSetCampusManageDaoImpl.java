package sng.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import sng.constant.Consts;
import sng.dao.BaseSetCampusManageDao;
import sng.entity.Campus;
import sng.pojo.ParamObj;
import sng.util.Constant;
import sng.util.Paging;

/**
 * @desc 基础设置模块-校区管理dao实现
 * @author magq
 * @data 2018-10-26
 * @version 1.0
 */
@Repository
public class BaseSetCampusManageDaoImpl extends BaseDaoImpl<Campus> implements BaseSetCampusManageDao {

	// 日志
	private Logger log = LoggerFactory.getLogger(BaseSetCampusManageDaoImpl.class);

	/**
	 * 校区管理列表查新
	 */
	@Override
	public Paging<Campus> queryCampusListInfo(ParamObj paramObj) {

		long starTime = System.currentTimeMillis();
		Paging<Campus> paging = new Paging<Campus>();
		Session session = this.factory.getCurrentSession();
		StringBuffer sql = new StringBuffer();
		StringBuffer countSql = new StringBuffer();
		List<Object> paramList = new ArrayList<>();
		sql.append("select cam_id,cam_name,cam_mobile,cam_address,navigation_info,note from campus where is_del=0");
		if (paramObj != null) {
			if (paramObj.getOrg_id() != null) {
				sql.append(" and org_id= ?");
				paramList.add(paramObj.getOrg_id());
			}
			
			if(paramObj.isExits()) {
				if(paramObj.getCam_id()!=null) {
					sql.append(" and cam_id <>?");
					paramList.add(paramObj.getCam_id());
				}
			}else {
				String camId = paramObj.getCam_ids();
				if(StringUtils.isNotBlank(camId)) {
					if(camId.indexOf(",")>0) {
						sql.append(" and cam_id in (");
						String[] camIds = camId.split(",");
						for(int i=0;i<camIds.length;i++) {
							if(i==camIds.length-1) {
								sql.append("?");
							}else {
								sql.append("?,");
							}
							paramList.add(camIds[i]);
						}
						sql.append(")");
						
					}else if(!Constant.ALL_CAMPUS.equals(camId)){
						sql.append(" and cam_id=?");
						paramList.add(camId);
					}
				}
			}
			if (StringUtils.isNotBlank(paramObj.getCam_name())) {
				if (paramObj.isBlurQuery()) {
					sql.append(" and cam_name like ?");
					paramList.add("%" + paramObj.getCam_name() + "%");
				} else {
					sql.append(" and cam_name=?");
					paramList.add(paramObj.getCam_name());
				}
			}
			
		}
		sql.append(" order by convert(cam_name using gbk)");
		int count = 0;
		if (paramObj.isNeedCount()) {

			countSql.append("SELECT count(0) FROM (" + sql.toString() + ") T");
			Query queryCount = session.createSQLQuery(countSql.toString());
			for (int i = 0; i < paramList.size(); i++) {
				queryCount.setParameter(i, paramList.get(i));
			}
			count = Integer.parseInt(queryCount.uniqueResult().toString());
			sql.append(" LIMIT ?,?");
			paramList.add((paramObj.getPage() - 1) * paramObj.getLimit());
			paramList.add(paramObj.getLimit());
		}

		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < paramList.size(); i++) {
			query.setParameter(i, paramList.get(i));
		}

		query.setResultTransformer(Transformers.aliasToBean(Campus.class));
		List<Campus> list = query.list();
		paging.setLimit(paramObj.getLimit());
		paging.setPage(paramObj.getPage());
		paging.setTotal(count);
		paging.setSize(
				count % paramObj.getLimit() == 0 ? (count / paramObj.getLimit()) : (count / paramObj.getLimit() + 1));
		paging.setData(list);
		paging.setSuccess(true);
		long endTime = System.currentTimeMillis();

		log.info("class BaseSetCampusManageDaoImpl method queryCampusListInfo time:" + (endTime - starTime) + "ms");

		return paging;
	}

	@Override
	public void deleteCampus(ParamObj paramObj) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		sql.append("update campus set is_del=1,update_time=?,del_time=?  where cam_id=?");
		paramList.add(new Date());
		paramList.add(new Date());
		paramList.add(paramObj.getCam_id());
		
		if(paramObj.getOrg_id()!=null) {
			 sql.append(" and org_id=?");
			 paramList.add(paramObj.getOrg_id());
		}
		
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < paramList.size(); i++) {
			query.setParameter(i, paramList.get(i));
		}
		int num = query.executeUpdate();
	}

}