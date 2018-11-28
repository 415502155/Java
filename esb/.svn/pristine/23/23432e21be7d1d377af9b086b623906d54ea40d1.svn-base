package cn.edugate.esb.redis.dao;

import java.util.List;
import cn.edugate.esb.entity.App;

/**
 * sso应用DAO接口
 * Title:RedisClassesDao
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月22日上午10:28:42
 */
public interface RedisAppDao {
	/**
	 * 添加班级
	 * @param classes
	 * @return
	 */
	boolean addApp(App app);
	/**
	 * 批量添加班级(For Redis)
	 * @param classess
	 * @return 
	 */
	boolean addApp(List<App> apps);
	/**
	 * 删除班级
	 * @param classes
	 * @return
	 */
	boolean deleteApp(App app);
	/**
	 * 无条件的删除全部(For Redis)
	 * @return 
	 */
	boolean deleteAllApp();
	/**
	 * 根据主键获取班级
	 * @param ClassesId
	 * @return
	 */
	App getAppByKey(String appkey);
	
	App getAppById(String appId);
	
}
