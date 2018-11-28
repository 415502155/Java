package sng.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sng.pojo.SessionInfo;
import sng.service.LoggerService;
@Component
public class LoggerUtil {
	
	@Autowired
	private LoggerService loggerService;
	private static LoggerUtil logUtil;
    @PostConstruct 
	public void init() {       
		 logUtil= this; 
		 logUtil.loggerService= this.loggerService; 
	} 
    
	/***
	 * 
	 *  @Description: 添加操作日志
	 *  @param orgId 机构id NOT NULL
	 *  @param camId 校区id NOT NULL
	 *  @param targetId 班级id/学员id NOT NULL
	 *  @param targetType 操作对象类型（0：班级 1：学员） NOT NULL
	 *  @param targetName 班级名称/学员名称  --- NULL
	 *  @param action 操作动作 NOT NULL
	 *  @param content 操作内容  (action + targetName)  NOT NULL
	 *  @param userType 用户类型 （0：老师 1：家长） NOT NULL
	 *  @param userTypeId 老师id/家长id NOT NULL
	 *  @param userTypeName 老师名称/家长名称  --- NULL
	 */
	public static void save(Integer orgId, Integer camId, Integer targetId, Integer targetType, String targetName, Integer action,
			String content, Integer userType, Integer userTypeId, String userTypeName, SessionInfo sessionInfo) {
		logUtil.loggerService.save(orgId, camId, targetId, targetType, targetName, action, content, userType, userTypeId, userTypeName, sessionInfo);
	}
 
	
}
