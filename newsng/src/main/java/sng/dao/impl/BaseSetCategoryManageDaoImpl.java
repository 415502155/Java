package sng.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import sng.dao.BaseSetCategoryManageDao;
import sng.entity.Category;
import sng.pojo.ParamObj;

@Repository
public class BaseSetCategoryManageDaoImpl extends BaseDaoImpl<Category> implements BaseSetCategoryManageDao {

	private Logger log = LoggerFactory.getLogger(BaseSetCategoryManageDaoImpl.class);

	/**
	 * 1：判断某机构下类目名字是否有相同的
	 *  2：判断所有机构下类目名字是否有相同的
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Category> queryCategoryListInfo(ParamObj paramObj) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		sql.append("select c.* from category c where c.is_del=0");
		if (paramObj != null) {
			if (paramObj.getOrg_id() != null) {
				sql.append(" and c.org_id=?");
				paramList.add(paramObj.getOrg_id());
			}
			if(paramObj.isExits()) {
				if(paramObj.getCategory_id()!=null) {
					sql.append(" and c.category_id<>?");
					paramList.add(paramObj.getCategory_id());
				}
			}
			if (StringUtils.isNotBlank(paramObj.getCategory_name())) {
				if (paramObj.isBlurQuery()) {
					sql.append(" and c.category_name like ?");
					paramList.add("%" + paramObj.getCategory_name() + "%");
				} else {
					sql.append(" and c.category_name = ?");
					paramList.add(paramObj.getCategory_name());
				}
			}
		}

		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < paramList.size(); i++) {
			query.setParameter(i, paramList.get(i));
		}
		query.setResultTransformer(Transformers.aliasToBean(Category.class));
		List<Category> list = query.list();
		return list;
	}

	

	
}
