package sng.controller.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sng.constant.Consts;
import sng.controller.common.BaseAction;
import sng.entity.OperationLog;
import sng.pojo.Result;
import sng.pojo.ResultEx;
import sng.pojo.SessionInfo;
import sng.service.LoggerService;
import sng.util.CommonUtils;
import sng.util.Constant;
import sng.util.ExcelExport;
import sng.util.ExcelParam;
import sng.util.Paging;

/***
 * 
 * @Company sjwy
 * @Title: ClassController.java
 * @Description: 操作日志查询、导出
 * @author: shy
 * @date: 2018年10月25日 上午8:24:12
 */
@Controller
@RequestMapping("/logger")
public class LoggerController extends BaseAction {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LoggerService loggerService;
	/***
	 * 
	 *  @Description: 操作日志查询列表
	 *  @param request
	 *  @param page
	 *  @param limit
	 *  @param orgId
	 *  @param action
	 *  @param startTime
	 *  @param endTime
	 *  @param content
	 *  @param response
	 *  @param session
	 *  @return
	 */
	@RequestMapping(value = "/list.htm")
	@ResponseBody
	public Result<?> getList(HttpServletRequest request,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "limit", defaultValue = Constant.NUM_PER_PCPAGE_STRING) Integer limit,
			/* @RequestParam(name = "orgId") */Integer org_id, /* @RequestParam(name = "action") */Integer action,
			/* @RequestParam(name = "startTime") */String startTime,
			/* @RequestParam(name = "endTime") */String endTime, /* @RequestParam(name = "content") */String content,
			HttpServletResponse response, HttpSession session) {
		SessionInfo sessionInfo = this.getSessionInfo(request);
		Integer orgId = org_id;
		if (null == orgId) {
			orgId = sessionInfo.getOrgId();
		}
		@SuppressWarnings("rawtypes")
		Paging<Map> pageMap = new Paging<Map>();
		pageMap.setPage(page);
		pageMap.setLimit(limit);
		@SuppressWarnings("rawtypes")
		Paging<Map> logList = null;
		try {
			logList = loggerService.getListMapWithPaging(orgId, action, startTime, endTime, content, pageMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Exception : " + e);
			return ResultEx.error(Constant.ERROR_CODE, Constant.ERROR_MSG, "");
		}
		return ResultEx.success(logList);
	}
	/***
	 * 
	 *  @Description: 日志导出 导出某一页或全部日志数据
	 *  @param request
	 *  @param pageNum 当前页
	 *  @param pageSize 每页条目数
	 *  @param orgId  机构id
	 *  @param action 操作动作
	 *  @param startTime 开始时间
	 *  @param endTime  结束时间
	 *  @param content  操作内容
	 *  @param isAll 是否全部导出 1：全部导出  其他：否
	 *  @param response
	 *  @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/poi.htm")
	@ResponseBody
	public Result<?> exportExcel(HttpServletRequest request,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "limit", defaultValue = Constant.NUM_PER_PCPAGE_STRING) Integer limit,
			/* @RequestParam(name = "orgId") */Integer org_id, 
			/* @RequestParam(name = "action") */Integer action,
			/* @RequestParam(name = "startTime") */String startTime,
			/* @RequestParam(name = "endTime") */String endTime, 
			/* @RequestParam(name = "content") */String content,
			Integer isAll,
			HttpServletResponse response) throws IOException {
		SessionInfo sessionInfo = this.getSessionInfo(request);
		Integer orgId = org_id;
		if (null == orgId) {
			orgId = sessionInfo.getOrgId();
		}
		Paging<OperationLog> pageLog = new Paging<OperationLog>();
		pageLog.setPage(page);
		pageLog.setLimit(limit);
		String[] heads = { "序号", "操作动作", "操作对象", "操作内容", "操作人", "操作时间" };
		StringBuilder fileName = new StringBuilder();
		fileName.append("操作日志").append(new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString())
				.append(".xls");
		String file = fileName.toString();
		log.info("filename :" + file);
		List<String[]> data = new LinkedList<String[]>();
		int i = 0;
		Paging<OperationLog> logList = null;
		try {
			logList = loggerService.getListWithPaging(orgId, action, startTime, endTime, content, pageLog, isAll);
			List<OperationLog> log = logList.getData();
			if (log != null && log.size() > 0) {
				for (OperationLog l : log) {
					String[] temp = new String[heads.length];
					temp[0] = String.valueOf(i + 1);
					temp[1] = Consts.LOG_ACTION_MAP.get(l.getAction());
					temp[2] = l.getTarget_name();
					temp[3] = l.getContent();
					temp[4] = l.getUser_type_name();
					temp[5] = CommonUtils.dateFormat(l.getOperation_time(), null).toString();
					data.add(temp);
					i++;
				}
			}
			ExcelParam param = new ExcelParam.Builder("操作日志").headers(heads).data(data).build();
			ExcelExport.export(param, file, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("操作日志数据导出异常信息  :" + e);
			return ResultEx.error(Constant.ERROR_CODE, Constant.ERROR_MSG, "");
		}
		return ResultEx.success("");
	}
	
	/***
	 * 
	 *  @Description: 日志导出 导出指定日志id
	 *  @param request
	 *  @param page 当前页
	 *  @param limit 每页条目数
	 *  @param orgId  机构id
	 *  @param action 操作动作
	 *  @param startTime 开始时间
	 *  @param endTime  结束时间
	 *  @param content  操作内容
	 *  @param isAll 是否全部导出 1：全部导出  其他：否
	 *  @param response
	 *  @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/export.htm")
	@ResponseBody
	public Result<?> exportExcelById(HttpServletRequest request,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "limit", defaultValue = Constant.NUM_PER_PCPAGE_STRING) Integer limit,
			/* @RequestParam(name = "orgId") */Integer org_id, 
			/* @RequestParam(name = "action") */Integer action,
			/* @RequestParam(name = "startTime") */String startTime,
			/* @RequestParam(name = "endTime") */String endTime, 
			/* @RequestParam(name = "content") */String content,
			String ids,
			Integer isAll,
			HttpServletResponse response) throws IOException {
		Integer orgId = org_id;
		SessionInfo session = this.getSessionInfo(request);
		if (orgId == null) {
			orgId = session.getOrgId();
		}
		Paging<OperationLog> pageLog = new Paging<OperationLog>();
		pageLog.setPage(page);
		pageLog.setLimit(limit);
		String[] heads = { "序号", "操作动作", "操作对象", "操作内容", "操作人", "操作时间" };
		StringBuilder fileName = new StringBuilder();
		fileName.append("操作日志").append(new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString())
				.append(".xls");
		String file = fileName.toString();
		log.info("filename :" + file);
		List<String[]> data = new LinkedList<String[]>();
		int i = 0;
		List<OperationLog> logList = null;
		try {
			if (isAll != null && isAll == 1) {//是否按照当前条件全部导出 1：是  其他：否
				logList = loggerService.getList(orgId, action, startTime, endTime, content, pageLog, isAll);
			} else {
				logList = loggerService.getList(ids, isAll);
			}
			for (OperationLog l : logList) {
				String[] temp = new String[heads.length];
				temp[0] = String.valueOf(i + 1);
				temp[1] = Consts.LOG_ACTION_MAP.get(l.getAction());
				temp[2] = l.getTarget_name();
				temp[3] = l.getContent();
				temp[4] = l.getUser_type_name();
				temp[5] = CommonUtils.dateFormat(l.getOperation_time(), null).toString();
				data.add(temp);
				i++;
			}
			ExcelParam param = new ExcelParam.Builder("操作日志").headers(heads).data(data).build();
			ExcelExport.export(param, file, request, response);
		} catch (Exception e) {
			log.info("操作日志数据导出异常信息  :" + e);
			e.printStackTrace();
			return ResultEx.error(Constant.ERROR_CODE, Constant.ERROR_MSG, "");
		}
		return ResultEx.success("");
	}
	/***
	 * 
	 *  @Description: 获取当前条件下日志列表所有日志数据id的集合
	 *  @param org_id 机构id
	 *  @param action 操作动作
	 *  @param startTime
	 *  @param endTime
	 *  @param content
	 *  @param response
	 *  @return
	 */
	@RequestMapping(value = "/logIdList.htm")
	@ResponseBody
	public Result<?> getLogIdList(HttpServletRequest request, Integer org_id, 
			Integer action, String startTime, String endTime, String content, HttpServletResponse response) {
		Paging<OperationLog> pageLog = new Paging<OperationLog>();
		SessionInfo session = this.getSessionInfo(request);
		if (org_id == null) {
			org_id = session.getOrgId();
		}
		StringBuffer ids = new StringBuffer("");
		try {
			List<OperationLog> logList = loggerService.getList(org_id, action, startTime, endTime, content, pageLog, 1);
			int i = 0;
			if (logList != null && logList.size() > 0) {
				for (OperationLog log : logList) {
					Integer id = log.getOperation_id();
					if (id != null) {
						if (i == logList.size()-1) {
							ids.append(String.valueOf(id));
						} else {
							ids.append(String.valueOf(id));
							ids.append(",");
						}
						i++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getLogIdList EX :" + e);
			return ResultEx.error(Constant.ERROR_CODE, Constant.ERROR_MSG, "");
		}
		return ResultEx.success(String.valueOf(ids));
	}
}
