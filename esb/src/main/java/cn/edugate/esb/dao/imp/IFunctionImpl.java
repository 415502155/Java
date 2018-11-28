package cn.edugate.esb.dao.imp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IFunctionDao;
import cn.edugate.esb.entity.Function;
import cn.edugate.esb.entity.Module;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.LSHelper;
import cn.edugate.esb.util.Paging;

/**
 * 功能模块DAO实现类
 * Title:IFunctionImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月11日下午3:26:51
 */
@Repository
public class IFunctionImpl extends BaseDAOImpl<Function> implements IFunctionDao{

	@Override
	public List<Function> getList(Function function) {
		Session session=this.factory.getCurrentSession();
		Criteria c=session.createCriteria(Function.class);
		if(StringUtils.isNotEmpty(function.getFun_name())){
			c.add(Restrictions.like("fun_name", function.getFun_name()));
		}
		if(StringUtils.isNotEmpty(function.getFun_code())){
			c.add(Restrictions.like("fun_code", function.getFun_code()));
		}
		if(StringUtils.isNotEmpty(function.getFun_url())){
			c.add(Restrictions.like("fun_url", function.getFun_url()));
		}
		if(null==function.getIs_del()){
			c.add(Restrictions.eq("is_del", 0));
		}else{
			c.add(Restrictions.eq("is_del", function.getIs_del()));
		}
		@SuppressWarnings("unchecked")
		List<Function> ls = c.list();
		
		for (Function function1 : ls) {
			LSHelper.processInjection(function1);
		}
		return ls;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Paging<Function> getListWithPaging(Paging<Function> paging, Function function) {
		Session session=this.factory.getCurrentSession();
		int limitFrom = (paging.getPage()-1)*paging.getLimit();
		String limit =  " limit " + limitFrom +"," + paging.getLimit();
		String order = " order by m.insert_time desc ";
		StringBuffer where = new StringBuffer(" where m.is_del=:is_del ");
		if(StringUtils.isNotEmpty(function.getFun_name())){
			where.append(" and m.fun_name like :fun_name ");
		}
		if(StringUtils.isNotEmpty(function.getFun_code())){
			where.append(" and m.fun_code like :fun_code ");
		}
		if(StringUtils.isNotEmpty(function.getFun_url())){
			where.append(" and m.fun_url like :fun_url ");
		}
		StringBuffer countTotal = new StringBuffer("select count(1) from `function` f ");
		StringBuffer sql = new StringBuffer("select f.fun_id,f.fun_code,f.fun_name,f.fun_url,f.fun_note,f.fun_order,f.fun_version,f.fun_status,f.insert_time,f.update_time,f.del_time,f.is_del from `function` f ");
		Query countQuery = session.createSQLQuery(countTotal.append(where).append(order).toString());
		Query sqlQuery = session.createSQLQuery(sql.append(where).append(order).append(limit).toString());
		if(null==function.getIs_del()){
			function.setIs_del(0);
		}
		countQuery.setInteger("is_del", function.getIs_del());
		sqlQuery.setInteger("is_del", function.getIs_del());
		if(StringUtils.isNotEmpty(function.getFun_name())){
			countQuery.setString("fun_name", "%"+function.getFun_name()+"%");
			sqlQuery.setString("fun_name", "%"+function.getFun_name()+"%");
		}
		if(StringUtils.isNotEmpty(function.getFun_code())){
			countQuery.setString("fun_code", "%"+function.getFun_code()+"%");
			sqlQuery.setString("fun_code", "%"+function.getFun_code()+"%");
		}
		if(StringUtils.isNotEmpty(function.getFun_url())){
			countQuery.setString("fun_url", "%"+function.getFun_url()+"%");
			sqlQuery.setString("fun_url", "%"+function.getFun_url()+"%");
		}
		BigInteger pageTotal = (BigInteger) countQuery.uniqueResult();
		paging.setTotal(pageTotal.intValue());
		sqlQuery = sqlQuery.setResultTransformer(Transformers.aliasToBean(Function.class));
		List<Function> list = new ArrayList<Function>();
		list = sqlQuery.list();
		for (Function function1 : list) {
			LSHelper.processInjection(function1);
		}
		paging.setData(list);
		return paging;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Function> getListByModule(Module module) {
		Session session=this.factory.getCurrentSession();
		String sql = "select"
				+ "	(select Group_concat(DISTINCT ff.fur_type) from fun_use_range ff where ff.fun_id=f.fun_id AND ff.is_del=0) as userRange,"
				+ " (select count(1) from menu m where m.fun_id=f.fun_id and m.is_del=0 AND m.menu_status=:using) as useingNumber,"
				+ " f.fun_id,f.fun_code,f.fun_name,f.fun_url,f.fun_note,f.fun_order,f.fun_version,f.fun_status,f.insert_time,f.update_time,f.del_time,f.is_del,f.logo from `function` f INNER JOIN module2function mf on mf.fun_id=f.fun_id INNER JOIN module m on m.mod_id=mf.mod_id where f.is_del=0 and mf.is_del=0 and m.is_del=0 and m.mod_id=:mod_id order by f.fun_order asc";
		Query query = session.createSQLQuery(sql);
		query.setInteger("mod_id", module.getMod_id());
		query.setInteger("using", Constant.MENU_TYPE_USING);
		query.setResultTransformer(Transformers.aliasToBean(Function.class));
		List<Function> list = new ArrayList<Function>(); 
		list = query.list();
		for (Function function : list) {
			LSHelper.processInjection(function);
		}
		return list;
	}

	@Override
	public boolean checkName(String name, String version) {
		Session session=this.factory.getCurrentSession();
		Criteria c=session.createCriteria(Function.class);
		c.add(Restrictions.eq("fun_name", name));
		c.add(Restrictions.eq("fun_version", version));
		@SuppressWarnings("unchecked")
		List<Function> ls = c.list();
		
		for (Function function : ls) {
			LSHelper.processInjection(function);
		}
		
		
		return null==ls?true:ls.size()==0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateOrder(Integer moduleID){
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT f.* FROM `function` f INNER JOIN module2function mf ON f.fun_id=mf.fun_id AND mf.is_del=0 INNER JOIN module m ON m.mod_id=mf.mod_id AND m.is_del=0 WHERE f.is_del=0 AND m.mod_id=:mod_id ORDER BY f.fun_order ASC,f.insert_time DESC";
		Query query = session.createSQLQuery(sql);
		query.setInteger("mod_id", moduleID);
		query.setResultTransformer(Transformers.aliasToBean(Function.class));
		List<Function> list = new ArrayList<Function>(); 
		list = query.list();
		for (int i = 0; i < list.size(); i++) {
			Function f = list.get(i);
			f.setFun_order(i+1);
			super.update(f);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Function> getFunctionForMenu(Integer fun_id) {
		Session session=this.factory.getCurrentSession();
		String sql = " SELECT DISTINCT menu.org_id,f.fun_id,f.fun_name,f.fun_order,menu.menu_id as parent_id,f.fun_url,m.mod_id,f.logo,f.category,f.fun_code FROM `function` f "
				+ " INNER JOIN module2function mf ON mf.fun_id=f.fun_id "
				+ " INNER JOIN module m ON m.mod_id=mf.mod_id "
				+ " INNER JOIN menu ON menu.mod_id=m.mod_id AND menu.fun_id is NULL "
				+ " INNER JOIN fun_use_range fur ON fur.fun_id=f.fun_id AND fur.fun_id=mf.fun_id AND fur.is_del=0 "
				+ " INNER JOIN organization o ON o.type=fur.fur_type AND o.org_id=menu.org_id AND o.is_del=0 "
				+ " WHERE f.is_del = 0 AND mf.is_del = 0 AND m.is_del = 0 AND menu.is_del = 0 "
				+ " AND f.fun_id = :fun_id ";
		Query query = session.createSQLQuery(sql);
		query.setInteger("fun_id", fun_id);
		query.setResultTransformer(Transformers.aliasToBean(Function.class));
		List<Function> list = new ArrayList<Function>(); 
		list = query.list();
		
		for (Function function : list) {
			LSHelper.processInjection(function);
		}
		
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Function> getFunctionForOrg(String org_id) {
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT f.* FROM function f left JOIN (SELECT DISTINCT fur.fun_id FROM fun_use_range fur WHERE fur.grade_number IN (SELECT g.grade_number FROM grade g   WHERE g.org_id = :org_id AND g.is_del=0 AND fur.is_del=0)) fo ON f.fun_id = fo.fun_id WHERE f.is_del=0";
		Query query = session.createSQLQuery(sql);
		query.setString("org_id", org_id);
		query.setResultTransformer(Transformers.aliasToBean(Function.class));
		List<Function> list = new ArrayList<Function>(); 
		list = query.list();
		for (Function function : list) {
			LSHelper.processInjection(function);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Function getByid(Integer id) {
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT f.*,m.mod_id FROM `function` f INNER JOIN module2function mf ON f.fun_id=mf.fun_id AND mf.is_del=0 INNER JOIN module m ON m.mod_id=mf.mod_id AND m.is_del=0 WHERE f.is_del=0 AND f.fun_id=:fun_id";
		Query query = session.createSQLQuery(sql);
		query.setInteger("fun_id", id);
		query.setResultTransformer(Transformers.aliasToBean(Function.class));
		List<Function> list = query.list();
		if(null!=list&&list.size()!=0){
			return list.get(0);
		}else{
			return new Function();
		}
	}

}
