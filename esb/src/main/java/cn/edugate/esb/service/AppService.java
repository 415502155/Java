package cn.edugate.esb.service;

import java.util.List;

import cn.edugate.esb.entity.App;
import cn.edugate.esb.util.Paging;

/**
 * 
 * @Name: 应用操作服务
 * @Description: 
 * @author HuangCf 
 * @date  2014-5-21
 * @version V1.0
 */
public interface AppService {

	void getAppWithPaging(Paging<App> pages);

	void add(App app);

	App getById(Integer appId);

	void update(App app);

	List<App> getAll();



	
}
