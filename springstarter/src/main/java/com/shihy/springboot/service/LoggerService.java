package com.shihy.springboot.service;

import java.util.List;
import java.util.Map;
import com.shihy.springboot.datasource.DS;
import com.shihy.springboot.entity.Logger;
import com.shihy.springboot.utils.Page;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月27日 下午3:30:35
 *
 */
public interface LoggerService {
	@DS("slave")
	void save(Logger logger);
	
	@DS("slave")
	List<Logger> getLoggerList(String serchName, Integer action, int page, int size);
	
	@DS("slave")
	Integer getLoggerTotalPage(String serchName, Integer action);
	
	@DS("slave")
	Integer delLoggerByIds(String ids);
	
	@DS("slave")
	Integer delLoggerByIdList(String ids);
	
	@DS("slave")
	int batchAddLogger(List<Logger> logger);
	
	@DS("slave")
	List<Map<String, Object>> getLoggerInfoList(String targetName, Integer action, Page page);
}
