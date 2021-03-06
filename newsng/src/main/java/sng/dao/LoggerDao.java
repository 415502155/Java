package sng.dao;

import java.util.List;
import java.util.Map;
import sng.entity.OperationLog;
import sng.util.Paging;

/***
 * 
 *  @Company sjwy
 *  @Title: loggerDao.java 
 *  @Description: 
 *	@author: shy
 *  @date: 2018年10月23日 下午1:33:16
 */
public interface LoggerDao  extends BaseDao<OperationLog>{


	/**
	 * 获取当前机构下的所有操作日志
	 * @param org_id
	 * @return
	 */
	Long getListCount(Integer org_id);
	
	/**
	 * 获取当前机构下的操作日志
	 * @param org_id
	 * @return
	 */
	Paging<OperationLog> getListWithPaging(Integer orgId, Integer action, String startTime, String endTime, String content, Paging<OperationLog> page, Integer isAll);
	/**
	 * 获取当前机构下的操作日志
	 * @param org_id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Paging<Map> getListMapWithPaging(Integer orgId, Integer action, String startTime, String endTime, String content,Paging<Map> page);
	/***
	 * 
	 *  @Description:根据日志id获取日志信息 
	 *  @return
	 */
	OperationLog getById(Integer id);
	/***
	 * 
	 *  @Description: 根据条件获取操作日志列表
	 *  @return
	 */
	List<OperationLog> getList(Integer orgId, Integer action, String startTime, String endTime, String content,Paging<OperationLog> page, Integer isAll);

}
