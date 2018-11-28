package cn.edugate.esb.dao.imp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IModuleDao;
import cn.edugate.esb.entity.Module;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Paging;
/**
 * 模块DAO实现类
 * Title:IModuleImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月11日下午4:14:11
 */
@Repository
public class IModuleImpl extends BaseDAOImpl<Module> implements IModuleDao {

	@Override
	public List<Module> getList(Module module) {
		Session session=this.factory.getCurrentSession();
		Criteria c=session.createCriteria(Module.class);
		if(StringUtils.isNotEmpty(module.getMod_name())){
			c.add(Restrictions.like("mod_name", module.getMod_name()));
		}
		if(StringUtils.isNotEmpty(module.getMod_code())){
			c.add(Restrictions.like("mod_code", module.getMod_code()));
		}
		if(null==module.getIs_del()){
			c.add(Restrictions.eq("is_del", 0));
		}else{
			c.add(Restrictions.eq("is_del", module.getIs_del()));
		}
		@SuppressWarnings("unchecked")
		List<Module> ls = c.list();
		
		return ls;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Paging<Module> getListWithPaging(Paging<Module> paging, Module module) {
		Session session=this.factory.getCurrentSession();
		int limitFrom = (paging.getPage()-1)*paging.getLimit();
		String limit =  " limit " + limitFrom +"," + paging.getLimit();
		String order = " order by m.mod_order asc";
		StringBuffer where = new StringBuffer(" where m.is_del=:is_del");
		if(StringUtils.isNotEmpty(module.getMod_name())){
			where.append(" and m.mod_name like :mod_name ");
		}
		if(StringUtils.isNotEmpty(module.getMod_code())){
			where.append(" and m.mod_code like :mod_code ");
		}
		StringBuffer countTotal = new StringBuffer("select count(1) from module m ");
		StringBuffer sql = new StringBuffer("select m.mod_id,m.mod_code,m.mod_name,m.mod_note,m.mod_order,m.insert_time,m.update_time,m.del_time,m.is_del from module m ");
		Query countQuery = session.createSQLQuery(countTotal.append(where).append(order).toString());
		Query sqlQuery = session.createSQLQuery(sql.append(where).append(order).append(limit).toString());
		if(null==module.getIs_del()){
			module.setIs_del(0);
		}
		countQuery.setInteger("is_del", module.getIs_del());
		sqlQuery.setInteger("is_del", module.getIs_del());
		if(StringUtils.isNotEmpty(module.getMod_name())){
			countQuery.setString("mod_name", "%"+module.getMod_name()+"%");
			sqlQuery.setString("mod_name", "%"+module.getMod_name()+"%");
		}
		if(StringUtils.isNotEmpty(module.getMod_code())){
			countQuery.setString("mod_code", "%"+module.getMod_code()+"%");
			sqlQuery.setString("mod_code", "%"+module.getMod_code()+"%");
		}
		BigInteger pageTotal = (BigInteger) countQuery.uniqueResult();
		paging.setTotal(pageTotal.intValue());
		sqlQuery = sqlQuery.setResultTransformer(Transformers.aliasToBean(Module.class));
		List<Module> list = new ArrayList<Module>();
		list = sqlQuery.list();
		paging.setData(list);
		return paging;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Paging<Module> getQueryListWithPaging(Paging<Module> paging,
			Module module) {
		Session session=this.factory.getCurrentSession();
		int limitFrom = (paging.getPage()-1)*paging.getLimit();
		String limit =  " limit " + limitFrom +"," + paging.getLimit();
		String order = " order by m.insert_time desc";
		StringBuffer where = new StringBuffer(" where m.is_del=0 and f.is_del=0 and mf.is_del=0 ");
		if(StringUtils.isNotEmpty(module.getMod_name())){
			where.append(" and (m.mod_name like :mod_name or f.fun_name like :mod_name) ");
		}
		if(StringUtils.isNotEmpty(module.getMod_code())){
			where.append(" and (m.mod_code like :mod_code or f.fun_code like :mod_code) ");
		}
		StringBuffer countTotal = new StringBuffer("select count(DISTINCT(m.mod_id)) from module m INNER join module2function mf on m.mod_id = mf.mod_id INNER JOIN `function` f on f.fun_id = mf.fun_id ");
		StringBuffer sql = new StringBuffer("select DISTINCT(m.mod_id),m.mod_code,m.mod_name,m.mod_note,m.mod_order,m.insert_time,m.update_time,m.del_time,m.is_del  from module m INNER join module2function mf on m.mod_id = mf.mod_id INNER JOIN `function` f on f.fun_id = mf.fun_id ");
		Query countQuery = session.createSQLQuery(countTotal.append(where).append(order).toString());
		Query sqlQuery = session.createSQLQuery(sql.append(where).append(order).append(limit).toString());
		if(StringUtils.isNotEmpty(module.getMod_name())){
			countQuery.setString("mod_name", "%"+module.getMod_name()+"%");
			sqlQuery.setString("mod_name", "%"+module.getMod_name()+"%");
		}
		if(StringUtils.isNotEmpty(module.getMod_code())){
			countQuery.setString("mod_code", "%"+module.getMod_code()+"%");
			sqlQuery.setString("mod_code", "%"+module.getMod_code()+"%");
		}
		BigInteger pageTotal = (BigInteger) countQuery.uniqueResult();
		paging.setTotal(pageTotal.intValue());
		sqlQuery = sqlQuery.setResultTransformer(Transformers.aliasToBean(Module.class));
		List<Module> list = new ArrayList<Module>();
		list = sqlQuery.list();
		paging.setData(list);
		return paging;
	}

	@Override
	public boolean checkName(String name) {
		Session session=this.factory.getCurrentSession();
		Criteria c=session.createCriteria(Module.class);
		c.add(Restrictions.eq("mod_name", name));
		@SuppressWarnings("unchecked")
		List<Module> ls = c.list();
		return null==ls?true:ls.size()==0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateOrder() {
		Session session=this.factory.getCurrentSession();
		Criteria c=session.createCriteria(Module.class);
		c.add(Restrictions.ge("is_del", Constant.IS_DEL_NO));
		c.addOrder(Order.asc("mod_order"));
		c.addOrder(Order.desc("insert_time"));
		List<Module> ls = c.list();
		for (int i = 0; i < ls.size(); i++) {
			Module m = ls.get(i);
			m.setMod_order(i+1);
			super.update(m);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Module> getModuleForMenu(Integer fun_id) {
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT DISTINCT	m.mod_id,m.mod_name,m.mod_order,o.org_id,m.logo,m.category FROM `function` f "
				+ " INNER JOIN fun_use_range fur ON f.fun_id = fur.fun_id "
				+ " INNER JOIN organization o ON o.type = fur.fur_type "
				+ " LEFT JOIN grade g ON g.org_id = o.org_id AND fur.grade_number = g.grade_number AND g.is_del = 0 "
				+ " INNER JOIN module2function mf ON mf.fun_id = f.fun_id "
				+ " INNER JOIN module m ON m.mod_id = mf.mod_id "
				+ " WHERE f.is_del = 0 AND fur.is_del = 0 AND o.is_del = 0 AND mf.is_del = 0 AND m.is_del = 0 AND f.fun_id = :fun_id "
				+ " AND NOT EXISTS (SELECT 1 FROM menu WHERE menu.mod_id = m.mod_id AND menu.org_id = o.org_id AND menu.parent_id = 0 AND menu.is_del = 0)";
		Query query = session.createSQLQuery(sql);
		query.setInteger("fun_id", fun_id);
		query.setResultTransformer(Transformers.aliasToBean(Module.class));
		List<Module> list = new ArrayList<Module>(); 
		list = query.list();
		return list;
	}

}
