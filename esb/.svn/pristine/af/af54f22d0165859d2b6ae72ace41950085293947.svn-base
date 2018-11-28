package cn.edugate.esb.dao.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IMenuDao;
import cn.edugate.esb.entity.Function;
import cn.edugate.esb.entity.Menu;
import cn.edugate.esb.entity.Module;
import cn.edugate.esb.util.ComparatorMenu;
import cn.edugate.esb.util.Constant;
/**
 * 菜单DAO接口实现类
 * Title:IMenuDaoImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月19日上午11:52:42
 */
@Repository
public class IMenuDaoImpl extends BaseDAOImpl<Menu> implements IMenuDao {

	@Override
	public List<Menu> getMenus(Integer orgId,Integer type,Integer status) {
		Session session=this.factory.getCurrentSession();
		Criteria c=session.createCriteria(Menu.class);
		c.add(Restrictions.eq("org_id", orgId));
		if(null!=type){
			c.add(Restrictions.eq("menu_type", type));
		}
		if(null!=status){
			c.add(Restrictions.eq("menu_status", status));
		}
		c.add(Restrictions.eq("is_del", Constant.IS_DEL_NO));
		c.addOrder(Order.asc("menu_order"));
		@SuppressWarnings("unchecked")
		List<Menu> ls = c.list();
		return ls;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void createMenuForOrg(Integer orgId, Integer orgType, Integer schoolType, String gradeNumber) {
		Session session=this.factory.getCurrentSession();
		StringBuffer sql = new StringBuffer();
		boolean isGradeNumberValid = false;
		sql.append(" SELECT DISTINCT(f.fun_code),f.fun_id,f.fun_name,f.fun_note,f.fun_order,m.mod_id,f.fun_url,f.logo,f.category from `function` f ")
			.append(" INNER JOIN fun_use_range fur ON f.fun_id=fur.fun_id ")
			.append(" INNER JOIN module2function mf ON mf.fun_id=f.fun_id ")
			.append(" INNER JOIN module m ON m.mod_id=mf.mod_id ")
			.append(" WHERE f.is_del=0 ")
			.append(" AND fur.is_del=0 ")
			.append(" AND mf.is_del=0 ")
			.append(" AND m.is_del=0 ")
			.append(" AND fur.fur_type=").append(orgType);
		if(null!=schoolType){
			sql.append(" AND fur.org_type=:org_type ");
		}
		if(StringUtils.isNotEmpty(gradeNumber)){
		    String regEx = "([0-9]+[,]?)+";
		    if(Pattern.compile(regEx).matcher(gradeNumber).matches()){
		    	isGradeNumberValid = true;
		    	sql.append(" AND fur.grade_number IN :gradeNumber ");
		    }
		}
		Query query = session.createSQLQuery(sql.toString());
		
		if(null!=schoolType){
			query.setInteger("org_type", orgType);
		}
		if(isGradeNumberValid){
			query.setParameterList("gradeNumber", Arrays.asList(gradeNumber.split(",")));
		}
		
		query.setResultTransformer(Transformers.aliasToBean(Function.class));
		List<Function> list = new ArrayList<Function>(); 
		Set<Integer> set = new HashSet<Integer>();
		Map<Integer,List<Menu>> funMenu = new HashMap<Integer,List<Menu>>();
		List<Menu> tempMenu = new ArrayList<Menu>();
		try {
			list = query.list();
			for (Function f : list) {
				Menu menu = new Menu();
				menu.setFun_id(f.getFun_id());
				menu.setMenu_name(f.getFun_name());
				menu.setMenu_note(f.getFun_note());
				menu.setMenu_order(f.getFun_order());
				menu.setMenu_status(0);//正常
				menu.setMenu_type(1);//未使用
				menu.setOrg_id(orgId);
				menu.setFun_code(f.getFun_code());
				menu.setParent_id(f.getMod_id());
				menu.setInsert_time(new Date());
				menu.setIs_del(Constant.IS_DEL_NO);
				menu.setMenu_url(f.getFun_url());
				menu.setOpen_mode(0);
				menu.setMenu_photo(f.getLogo());
				menu.setCategory(f.getCategory());
				super.save(menu);
				set.add(f.getMod_id());
				if(funMenu.containsKey(menu.getParent_id())){					
					tempMenu = funMenu.get(menu.getParent_id());
				}else{
					tempMenu = new ArrayList<Menu>();
				}
				tempMenu.add(menu);
				funMenu.put(menu.getParent_id(), tempMenu);
			}
		} catch (Exception e) {
			logger.error("===createMenuForOrg===create Menu from function error:"+e.getMessage());
		}
		
		//String modIds = StringUtils.join(set.toArray(), ",");
		String modSql = "SELECT m.mod_id,m.mod_name,m.mod_note,m.mod_order,m.mod_code,m.logo,m.category FROM module m WHERE m.mod_id IN :modIds";
		Query modQuery = session.createSQLQuery(modSql);
		modQuery.setResultTransformer(Transformers.aliasToBean(Module.class));
		modQuery.setParameterList("modIds", set);
		List<Module> modList = new ArrayList<Module>(); 
		Map<Integer,Integer> modMenu = new HashMap<Integer,Integer>();
		try {
			modList = modQuery.list();
			for (Module m : modList) {
				Menu menu = new Menu();
				menu.setMod_id(m.getMod_id());
				menu.setFun_code(m.getMod_code());
				menu.setMenu_name(m.getMod_name());
				menu.setMenu_note(m.getMod_note());
				menu.setMenu_order(m.getMod_order());
				menu.setMenu_status(0);//正常
				menu.setMenu_type(1);//未使用
				menu.setOrg_id(orgId);
				menu.setInsert_time(new Date());
				menu.setIs_del(Constant.IS_DEL_NO);
				menu.setParent_id(0);
				menu.setMenu_photo(m.getLogo());
				menu.setCategory(m.getCategory());
				super.save(menu);
				modMenu.put(m.getMod_id(), menu.getMenu_id());
			}
		} catch (Exception e) {
			logger.error("===createMenuForOrg===create Menu from module error:"+e.getMessage());
		}
		
		for (Map.Entry<Integer, Integer> entry : modMenu.entrySet()) {  
			List<Menu> currentFunMenu = funMenu.get(entry.getKey());
			for (Menu menu : currentFunMenu) {
				menu.setParent_id(entry.getValue());
			}
		}  
	}

	@Override
	public List<Menu> getMenusForOrg(Integer id,boolean isTree) {
		Session session=this.factory.getCurrentSession();
		Criteria c=session.createCriteria(Menu.class);
		c.add(Restrictions.eq("org_id", id));
		c.add(Restrictions.eq("is_del", Constant.IS_DEL_NO));
		c.addOrder(Order.asc("parent_id"));
		c.addOrder(Order.asc("menu_order"));
		@SuppressWarnings("unchecked")
		List<Menu> menus = c.list();
		if(false!=isTree){
			Map<Integer,Menu> map = new HashMap<Integer,Menu>();
			for (Menu menu : menus) {
				if(null==menu.getParent_id()||0==menu.getParent_id()){
					map.put(menu.getMenu_id(), menu);
				}else{
					if(map.containsKey(menu.getParent_id())){
						List<Menu> temp = map.get(menu.getParent_id()).getSmenu();
						if(null==temp){
							List<Menu> newList = new ArrayList<Menu>();
							newList.add(menu);
							map.get(menu.getParent_id()).setSmenu(newList);
						}else{
							temp.add(menu);
							map.get(menu.getParent_id()).setSmenu(temp);
						}
					}
				}
			}
			List<Menu> list = new ArrayList<Menu>(map.values()); 
			return sortMenu(list);
		}else{
			return menus;
		}
	}
	
	private List<Menu> sortMenu(List<Menu> list){
		ComparatorMenu comparator=new ComparatorMenu();
		Collections.sort(list, comparator);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> getAll() {
		Session session=this.factory.getCurrentSession();
		String sql = " SELECT m.menu_photo,m.menu_id,m.mod_id,m.org_id,m.fun_id,m.menu_name,m.menu_note,m.menu_order,m.parent_id,m.menu_type,m.menu_status,m.open_mode,m.menu_url,f.fun_code,m.is_del,m.category FROM menu m INNER JOIN `function` f ON m.fun_id=f.fun_id WHERE m.is_del=0 AND f.is_del=0 "
				+ "union all SELECT m.menu_photo, m.menu_id, m.mod_id, m.org_id, m.fun_id, m.menu_name, m.menu_note, m.menu_order, m.parent_id, m.menu_type, m.menu_status, m.open_mode, m.menu_url, module.mod_code as fun_code, m.is_del,m.category FROM menu m INNER JOIN `module` ON module.mod_id = m.mod_id WHERE m.is_del = 0 AND module.is_del = 0 AND m.fun_id is NULL";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Menu.class));
		List<Menu> list = new ArrayList<Menu>(); 
		list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> getMenusByOgrId(int org_id) {
		Session session=this.factory.getCurrentSession();
		String sql = " SELECT m.menu_photo,m.menu_id,m.mod_id,m.org_id,m.fun_id,m.menu_name,m.menu_note,m.menu_order,m.parent_id,m.menu_type,m.menu_status,m.open_mode,m.menu_url,f.fun_code,m.is_del,m.category FROM menu m INNER JOIN `function` f ON m.fun_id=f.fun_id WHERE m.is_del=0 AND f.is_del=0  AND m.org_id=:org_id "
				+ " union all SELECT m.menu_photo, m.menu_id, m.mod_id, m.org_id, m.fun_id, m.menu_name, m.menu_note, m.menu_order, m.parent_id, m.menu_type, m.menu_status, m.open_mode, m.menu_url, module.mod_code as fun_code, m.is_del,m.category FROM menu m INNER JOIN `module` ON module.mod_id = m.mod_id WHERE m.is_del = 0 AND module.is_del = 0 AND m.fun_id is NULL  AND m.org_id=:org_id ";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Menu.class));
		List<Menu> list = new ArrayList<Menu>(); 
		query.setInteger("org_id", org_id);
		list = query.list();
		return list;
	}
}
