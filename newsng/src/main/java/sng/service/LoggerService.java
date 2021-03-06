package sng.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sng.entity.OperationLog;
import sng.pojo.SessionInfo;
import sng.util.Constant;
import sng.util.Paging;

/***
 * 
 *  @Company sjwy
 *  @Title: LoggerService.java 
 *  @Description: 
 *	@author: shy
 *  @date: 2018年10月23日 下午1:25:55
 */
@Service
@Transactional
public interface LoggerService {
	
	/***
	 * 
	 *  @Description: 分页获取当前机构下的操作日志<OperationLog集合>
	 *  @param orgId
	 *  @param action
	 *  @param startTime
	 *  @param endTime
	 *  @param content
	 *  @param page
	 *  @return
	 */
	Paging<OperationLog> getListWithPaging(Integer orgId, Integer action, String startTime, String endTime, String content,Paging<OperationLog> page, Integer isAll);
	/**
	 * 获取当前机构下的操作日志
	 * @return
	 */
	List<OperationLog> getList(Integer orgId, Integer action, String startTime, String endTime, String content,Paging<OperationLog> page, Integer isAll);

	/***
	 * 
	 *  @Description: 分页获取当前机构下的操作日志<Map集合>
	 *  @param orgId
	 *  @param action
	 *  @param startTime
	 *  @param endTime
	 *  @param content
	 *  @param page
	 *  @return
	 */
	@SuppressWarnings("rawtypes")
	Paging<Map> getListMapWithPaging(Integer orgId, Integer action, String startTime, String endTime, String content, Paging<Map> page);
	/***
	 *  @Description: 添加操作日志
	 *  @param orgId 机构id
	 *  @param camId 校区id
	 *  @param targetId 班级id/学生id
	 *  @param targetType 操作对象类型EnumLog.TARGET_TYPE_***
	 *  @param targetName 【必须】班级名称/学生姓名
	 *  @param action 操作动作 EnumLog.****.getKey()
	 *  @param content 操作内容 EnumLog.****.getValue()
	 *  @param userType 用户身份Constant.IDENTITY_***
	 *  @param userTypeId 老师id/家长id
	 *  @param userTypeName 【必须】老师名称/家长名称 
	 */
	void save(Integer orgId, Integer camId, Integer targetId, Integer targetType, String targetName, Integer action,
			String content, Integer userType, Integer userTypeId, String userTypeName, SessionInfo sessionInfo);
	
	/***
	 * 
	 *  @Description: 
	 *  @param ids
	 *  @param isAll
	 *  @return
	 */
	List<OperationLog> getList(String ids, Integer isAll);
}
