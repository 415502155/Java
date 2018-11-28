package cn.edugate.esb.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.dao.IMenuDao;
import cn.edugate.esb.entity.Function;
import cn.edugate.esb.entity.Menu;
import cn.edugate.esb.entity.Module;
import cn.edugate.esb.redis.dao.RedisMenuDao;
import cn.edugate.esb.service.MenuService;
import cn.edugate.esb.util.Constant;
/**
 * 菜单服务实现类
 * Title:MenuImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月19日上午9:57:49
 */
@Component
@Transactional("transactionManager")
public class MenuImpl implements MenuService {

	private static Logger logger = Logger.getLogger(MenuImpl.class);
	
	@Autowired
	private IMenuDao menuDao;
	@Autowired
	private RedisMenuDao redisMenuDao;

	@Override
	public void add(Menu menu) {
		menu.setIs_del(Constant.IS_DEL_NO);
		menu.setInsert_time(new Date());
		try {
			menuDao.saveOrUpdate(menu);
		} catch (Exception e) {
			logger.error("Menu add error");	
		}
	}

	@Override
	public Integer delete(Integer id) {
		try {
			Menu menu = menuDao.get(Menu.class, id);
			menu.setIs_del(Constant.IS_DEL_YES);
			menu.setDel_time(new Date());
			menuDao.update(menu);
		} catch (Exception e) {
			logger.error("Menu delete error");	
			return null;
		}
		return id;
	}

	@Override
	public Menu update(Menu menu) {
		try {
			menu.setUpdate_time(new Date());
			menuDao.update(menu);
		} catch (Exception e) {
			logger.error("Menu update error");	
			return null;
		}
		return menu;
	}

	@Override
	public Menu getById(Integer id) {
		Menu menu = new Menu();
		try {
			menu = menuDao.get(Menu.class, id);
		} catch (Exception e) {
			logger.error("Menu getById error");	
			return null;
		}
		return menu;
	}

	@Override
	public List<Menu> getMenus(Integer orgId, Integer type, Integer status) {
		List<Menu> list = new ArrayList<Menu>();
		try {
			list = menuDao.getMenus(orgId,type,status);
		} catch (Exception e) {
			logger.error("Menu getMenus error");	
			return null;
		}
		return list;
	}

	@Override
	public List<Menu> getAll() {
		try {
			return menuDao.getAll();
		} catch (Exception e) {
			logger.error("Menu getAll error:"+e.getMessage());
			return null;
		}
	}

	@Override
	public Integer createMenuForOrg(Integer orgId, Integer orgType, Integer schoolType, String gradeNumber) {
		try {
			menuDao.createMenuForOrg(orgId,orgType,schoolType,gradeNumber);
		} catch (Exception e) {
			logger.error("Menu createMenuForOrg error:"+e.getMessage());
			return null;
		}
		return orgId;
	}

	@Override
	public Integer createMenuForOrg(Integer orgId, Integer orgType, Integer schoolType) {
		return createMenuForOrg(orgId,orgType,schoolType,null);
	}

	@Override
	public Integer createMenuForOrg(Integer orgId, Integer orgType) {
		return createMenuForOrg(orgId,orgType,null,null);
	}

	@Override
	public List<Menu> getMenusForOrg(Integer orgId,boolean isTree) {
		try {
			return menuDao.getMenusForOrg(orgId, isTree);
		} catch (Exception e) {
			logger.error("Menu getMenusForOrg error:"+e.getMessage());
			return null;
		}
	}

	@Override
	public List<Menu> getAllParent(Integer orgId) {
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("parent_id", 0);
		map.put("is_del", Constant.IS_DEL_NO);
		if(null!=orgId){
			map.put("org_id", orgId);
		}
		Criterion criterion = Restrictions.allEq(map);
		try {
			return menuDao.list(Menu.class, criterion);
		} catch (Exception e) {
			logger.error("Menu getAllParent error:"+e.getMessage());
			return null;
		}
	}

	@Override
	public void createModuleMenus(List<Module> orgModuleList) {
		for (Module module : orgModuleList) {
			Menu mmenu = new Menu();
			mmenu.setOrg_id(module.getOrg_id());
			mmenu.setMod_id(module.getMod_id());
			mmenu.setOpen_mode(Constant.MENU_OPEN_MODE_DEFAULT);
			mmenu.setParent_id(0);
			mmenu.setMenu_name(module.getMod_name());
			mmenu.setMenu_note("系统自动创建菜单");
			mmenu.setMenu_photo(module.getLogo());
			mmenu.setMenu_order(module.getMod_order());
			mmenu.setMenu_type(Constant.MENU_TYPE_STAYING);
			mmenu.setMenu_status(Constant.MENU_STATUS_NORMAL);
			mmenu.setInsert_time(new Date());
			mmenu.setIs_del(Constant.IS_DEL_NO);
			mmenu.setCategory(module.getCategory());
			mmenu.setFun_code(module.getMod_code());
			menuDao.save(mmenu);
		}
	}

	@Override
	public void createFunctionMenus(List<Function> orgFunctionList) {
		for (Function function : orgFunctionList) {
			Menu fmenu = new Menu();
			fmenu.setOrg_id(function.getOrg_id());
			fmenu.setFun_id(function.getFun_id());
			fmenu.setMod_id(function.getMod_id());
			fmenu.setMenu_name(function.getFun_name());
			fmenu.setMenu_note("系统自动创建菜单");
			fmenu.setMenu_order(function.getFun_order());
			fmenu.setMenu_type(Constant.MENU_TYPE_STAYING);
			fmenu.setMenu_status(Constant.MENU_STATUS_NORMAL);
			fmenu.setMenu_photo(function.getLogo());
			fmenu.setParent_id(function.getParent_id());
			fmenu.setOpen_mode(Constant.MENU_OPEN_MODE_DEFAULT);
			fmenu.setMenu_url(function.getFun_url());
			fmenu.setInsert_time(new Date());
			fmenu.setIs_del(Constant.IS_DEL_NO);
			fmenu.setCategory(function.getCategory());
			fmenu.setFun_code(function.getFun_code());
			menuDao.save(fmenu);
		}
	}

	@Override
	public void deleteModuleMenu(Module module) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteFunctionMenu(Function function) {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateModuleMenu(Module module,String oldName) {
		try {
			List<Menu> list = redisMenuDao.getAllMenus();
			if(null!=list&&list.size()!=0){
				for (Menu menu : list) {
					if(menu.getMod_id().equals(module.getMod_id())){
					//if(menu.getMod_id().equals(module.getMod_id())&&menu.getMenu_name().equals(oldName)){
						//menu.setMenu_order(module.getMod_order());
						//menu.setMenu_name(module.getMod_name());
						//menu.setMenu_photo(module.getLogo());
						menu.setFun_code(module.getMod_code());
						menuDao.update(menu);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Menu updateModuleMenu error:"+e.getMessage());
		}
	}

	@Override
	public void updateFunctionMenu(Function function,String oldName) {
		try {
			List<Menu> list = redisMenuDao.getAllMenus();
			if(null!=list&&list.size()!=0){
				for (Menu menu : list) {
					if(null!=menu.getFun_id()&&menu.getFun_id().equals(function.getFun_id())){
					//if(menu.getFun_id().equals(function.getFun_id())&&menu.getMenu_name().equals(oldName)){
						//menu.setMenu_name(function.getFun_name());
						//menu.setMenu_order(function.getFun_order());
						menu.setMenu_url(function.getFun_url());
						//menu.setMenu_photo(function.getLogo());
						menu.setFun_code(function.getFun_code());
						//不能修改父级ID，因为还要对父级做复杂的判断
						//menu.setParent_id(function.getParent_id());
						menuDao.update(menu);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Menu updateFunctionMenu error:"+e.getMessage());
		}
	}

	@Override
	public void additionalModuleMenus(List<Module> orgModuleList, List<String> orgids) {
		for (Module module : orgModuleList) {
			if(!orgids.contains(module.getOrg_id())){
				Menu mmenu = new Menu();
				mmenu.setOrg_id(module.getOrg_id());
				mmenu.setMod_id(module.getMod_id());
				mmenu.setOpen_mode(Constant.MENU_OPEN_MODE_DEFAULT);
				mmenu.setParent_id(0);
				mmenu.setMenu_name(module.getMod_name());
				mmenu.setMenu_note("系统自动创建菜单");
				mmenu.setMenu_photo(module.getLogo());
				mmenu.setMenu_order(module.getMod_order());
				mmenu.setMenu_type(Constant.MENU_TYPE_STAYING);
				mmenu.setMenu_status(Constant.MENU_STATUS_NORMAL);
				mmenu.setInsert_time(new Date());
				mmenu.setIs_del(Constant.IS_DEL_NO);
				mmenu.setCategory(module.getCategory());
				mmenu.setFun_code(module.getMod_code());
				menuDao.save(mmenu);
			}
		}
	}

	@Override
	public void additionalFunctionMenus(List<Function> orgFunctionList, List<String> orgids) {
		for (Function function : orgFunctionList) {
			if(!orgids.contains(function.getOrg_id().toString())){
				Menu fmenu = new Menu();
				fmenu.setOrg_id(function.getOrg_id());
				fmenu.setFun_id(function.getFun_id());
				fmenu.setMod_id(function.getMod_id());
				fmenu.setMenu_name(function.getFun_name());
				fmenu.setMenu_note("系统自动创建菜单");
				fmenu.setMenu_order(function.getFun_order());
				fmenu.setMenu_type(Constant.MENU_TYPE_STAYING);
				fmenu.setMenu_status(Constant.MENU_STATUS_NORMAL);
				fmenu.setMenu_photo(function.getLogo());
				fmenu.setParent_id(function.getParent_id());
				fmenu.setOpen_mode(Constant.MENU_OPEN_MODE_DEFAULT);
				fmenu.setMenu_url(function.getFun_url());
				fmenu.setInsert_time(new Date());
				fmenu.setIs_del(Constant.IS_DEL_NO);
				fmenu.setCategory(function.getCategory());
				fmenu.setFun_code(function.getFun_code());
				menuDao.save(fmenu);
			}
		}
	}

	@Override
	public List<Menu> getMenusByOgrId(int org_id) {
		return menuDao.getMenusByOgrId(org_id);
	}

}
