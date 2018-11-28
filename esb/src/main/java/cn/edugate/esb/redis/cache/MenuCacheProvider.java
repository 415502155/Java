package cn.edugate.esb.redis.cache;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.entity.Menu;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisMenuDao;
import cn.edugate.esb.service.MenuService;

/**
 * 菜单缓存
 * Title:OrganizationCacheProvider
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月19日上午9:17:53
 */
@Cache(name="菜单缓存",entities={Menu.class})
public class MenuCacheProvider implements CacheProvider<Menu>,java.io.Serializable {

	private static final long serialVersionUID = 1L;

	static Logger logger=LoggerFactory.getLogger(MenuCacheProvider.class);
	
	private RedisMenuDao redisMenuDao;
	@Autowired
	public void setRedisMenuDao(RedisMenuDao redisMenuDao) {
		this.redisMenuDao = redisMenuDao;
	}
	private MenuService menuService;
	@Autowired
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	@Override
	public void add(Menu menu) {
		logger.info("menuRedis===add====",menu);
		this.redisMenuDao.addMenu(menu);
	}

	@Override
	public void update(Menu menu) {
		logger.info("menuRedis===update====",menu);
		Menu oldMenu = this.redisMenuDao.getMenuById(menu.getMenu_id());
		if(oldMenu!=null)
			this.redisMenuDao.deleteMenu(oldMenu);
		this.redisMenuDao.addMenu(menu);			
	}

	@Override
	public void delete(Menu menu) {
		logger.info("menuRedis===delete====",menu);
		this.redisMenuDao.deleteMenu(menu);
	}

	@Override
	public void refreshAll() {
		this.redisMenuDao.deleteAll();
		List<Menu> menus = this.menuService.getAll();
		this.redisMenuDao.addMenus(menus);		
	}

	@Override
	public void refreshOrg(String org_id) {
		Map<String,Menu> list = redisMenuDao.getMenusByOrgId(Integer.parseInt(org_id));
		if(null!=list&&!list.isEmpty()){
			for (Menu m : list.values()) {  
				redisMenuDao.deleteMenu(m); 
			}
		}
		List<Menu> menus = menuService.getMenusByOgrId(Integer.parseInt(org_id));
		this.redisMenuDao.addMenus(menus);
	}

}
