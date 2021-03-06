package sng.controller.web;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sharding.entity.Notice;
import sng.controller.common.BaseAction;
import sng.dao.ClassDao;
import sng.entity.Classes;
import sng.pojo.ClassToTeacher;
import sng.pojo.Result;
import sng.pojo.ResultEx;
import sng.pojo.SessionInfo;
import sng.service.ClassService;
import sng.service.MQService;
import sng.service.NoticeService;
import sng.service.impl.PlanServiceImpl;
import sng.util.CommonUtils;
import sng.util.Paging;

/**
 * 发送学生或教师通知
 * 
 * @author sunwei
 * 
 */
@RestController
@RequestMapping("/notice")
public class NoticeController extends BaseAction {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ClassDao classDao;
	
	@Autowired
	private PlanServiceImpl planServiceImpl;

	@Autowired
	private ClassService classService;
	
	@Autowired
	private MQService mqService;
	
	@Autowired
	private NoticeService noticeService;

	/**
	 * 测试用
	 */
	private SessionInfo getSessionInfo() {
		SessionInfo sessionInfo = new SessionInfo();
		sessionInfo.setLoginName("user1");
		sessionInfo.setOrgId(46);
		sessionInfo.setUserId(11783);
		return sessionInfo;
	}

	/**
	 * 年份列表
	 */
	@RequestMapping("/yearList")
	public Result getYearList(HttpServletRequest request) {
		SessionInfo sessionInfo = this.getSessionInfo(request);
		List<String> yearList = planServiceImpl.getYearList(sessionInfo.getOrgId());
		return ResultEx.success(yearList);
	}

	/**
	 * 显示班级列表
	 */
	@RequestMapping("/getClassList")
	public Result selectCampusClass(HttpServletRequest request, Integer page, Integer limit) {
		SessionInfo sessionInfo = this.getSessionInfo(request);
		Map<String, Object> paramMap = CommonUtils.doParameters(request);
		Paging paging = planServiceImpl.getPageCampusClass(sessionInfo, paramMap, page, limit);
		return ResultEx.success(paging);
	}

	/**
	 * 查询招生计划要选择的班级列表-点击选择全部分页数据
	 */
	@RequestMapping("/selectAllClass")
	public Result selectAllClass(HttpServletRequest request) {
		SessionInfo sessionInfo = this.getSessionInfo(request);
		Map<String, Object> paramMap = CommonUtils.doParameters(request);
		List<String> classIdList = planServiceImpl.selectAllClass(sessionInfo, paramMap);
		return ResultEx.success(classIdList);
	}
	
	
	/**
	 * 查询招生计划要选择的班级列表-列表下面的统计
	 * @param request
	 * @return
	 */
	@RequestMapping("/classData")
	public Result getClassData(HttpServletRequest request) {
		SessionInfo sessionInfo=this.getSessionInfo(request);
		Map<String,Object> paramMap=CommonUtils.doParameters(request);
		Map<String,Object> map=planServiceImpl.getClassData(sessionInfo, paramMap);
		return ResultEx.success(map);
	}

	/**
	 * 提交发送学生通知
	 */
	@RequestMapping(value = "/submitStudentNoticeForm", method=RequestMethod.POST)
	public Result submitStudentNoticeForm(HttpServletRequest request) {
		try {
			SessionInfo sessionInfo = getSessionInfo(request);
			int org_id = sessionInfo.getOrgId();
			//int org_id = 393;
			String classIds = request.getParameter("classIds");
			String studentStatus = request.getParameter("studentStatus");
			String content = request.getParameter("content");
			String sender_id = request.getParameter("sender_id");
			String sender_name = request.getParameter("sender_name");
			
			if (StringUtils.isNoneBlank(String.valueOf(org_id), classIds, studentStatus, content)
					&& Integer.valueOf(studentStatus) >= 0 && Integer.valueOf(studentStatus).intValue() <= 8) {
				// 验证所选班级是否全部存在
				List<String> okClassList = new ArrayList<String>();
				String[] classIdArray = classIds.split(",");
				for (String classId : classIdArray) {
					Classes c = classDao.get(Classes.class, Integer.valueOf(classId));
					if (c != null && c.getClas_id() != null && c.getClas_id().intValue() > 0 && c.getIs_del().intValue() == 0) {
						okClassList.add(classId);
					}
				}
				if (okClassList.isEmpty()) {
					return ResultEx.error(0, "选中的班级全部不存在或者已被删除！", null);
				} else {
					String target = "";
					for (String classIdStr : okClassList) {
						target += (classIdStr + ",");
					}
					target = target.substring(0, target.length() - 1);
					// 存在可以发送班级，先保存到数据库再发送
					Notice notice = new Notice();
					notice.setOrg_id(org_id);
					notice.setType(0);
					notice.setStatus(0);
					notice.setSender_id(Integer.valueOf(sender_id));
					notice.setSender_name(sender_name);
					notice.setTarget(target);
					notice.setTarget_status(Integer.valueOf(studentStatus));
					notice.setTitle("");
					notice.setContent(content);
					notice.setTotal_num(0);
					notice.setTotal_class_num(okClassList.size());
					Date currentDate = new Date();
					notice.setInsert_time(new Timestamp(currentDate.getTime()));
					notice.setUpdate_time(new Timestamp(currentDate.getTime()));
					notice.setIs_del(0);

					noticeService.saveNotice(notice);

					String recordId = notice.getId_str();
					// 有能发送的班级，则放入待拆分队列
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("org_id", String.valueOf(org_id));
					param.put("recordId", recordId);

					// 2分钟10秒钟后进行发送
					//mqService.sendMessage("sendNoticeExchange", "wait4SendNotice", param, 10000);
					mqService.sendMessage("sendNoticeExchange", "wait4SendNotice", param, 130000);

					return ResultEx.success("通知发送成功！", null);
				}
			} else {
				return ResultEx.error(0, "请求参数不完整！", null);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.toString());
			return ResultEx.error(0, "发送通知时发生错误！【"+ex.toString()+"】", null);
		}
	}
	
	
	/**
	 * 查询学生通知发送记录
	 */
	@RequestMapping("/getStudentNoticeSendHistory")
	public Result<Paging<Notice>> getStudentNoticeSendHistory(HttpServletRequest request) {
		Result<Paging<Notice>> result = new Result<Paging<Notice>>();
		Paging<Notice> paging = new Paging<Notice>();
		SessionInfo sessionInfo = getSessionInfo(request);
		
		try {
			int org_id = sessionInfo.getOrgId();
			int userId = sessionInfo.getUserId();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date currentDate = new Date();

			// 最大搜索日期应为当日
			String currentDateStr = sdf.format(currentDate);
			currentDate = sdf.parse(currentDateStr);
			
			String searchBeginDate = request.getParameter("searchBeginDate");
			String searchEndDate = request.getParameter("searchEndDate");
			String searchKeyWord = request.getParameter("searchKeyWord");
			String isAdmin = request.getParameter("isAdmin");
			
			String currentPageNum = request.getParameter("currentPageNum");
			String pageSize = request.getParameter("pageSize");
			
			paging.setPage(Integer.valueOf(currentPageNum));
			paging.setLimit(Integer.valueOf(pageSize));
			
			if ("1".equals(isAdmin)) {
				// 是管理员查询所有老师发送的通知
				noticeService.getNoticePaging(String.valueOf(org_id), null, searchBeginDate, searchEndDate, searchKeyWord, paging);
			} else {
				// 只能查询登录教师自己发送的通知
				noticeService.getNoticePaging(String.valueOf(org_id), String.valueOf(userId), searchBeginDate, searchEndDate, searchKeyWord, paging);
			}
			
			result.setData(paging);
			result.setSuccess(true);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setSuccess(false);
			result.setMessage(ex.toString());
		}
		
		return result;
	}

	
	/**
	 * 撤回学生通知
	 * @return
	 */
	@RequestMapping(value="/revocationStudentNotice")
	public Result<String> revocationStudentNotice(HttpServletRequest request) {
		Result<String> result = new Result<String>();
		String message = null;
		try {
			SessionInfo sessionInfo = getSessionInfo(request);
			int org_id = sessionInfo.getOrgId();
			int userId = sessionInfo.getUserId();
			
			String noticeId = request.getParameter("noticeId");
			
			if (StringUtils.isNoneBlank(String.valueOf(org_id), String.valueOf(userId), noticeId)) {
				// 根据记录ID进行撤回
				// 发送类型：0-学生通知；1-教师通知
				message = noticeService.revocationNotice(String.valueOf(org_id), noticeId, "0");
				
				result.setSuccess(true);
				result.setMessage(message);
			} else {
				throw new Exception("撤回通知接口参数不完整！");
			}
		} catch (Exception ex) {
			result.setSuccess(false);
			result.setMessage(ex.toString());
		}
		
		return result;
	}
	
	
	/**
	 * 查看某条通知选择的班级列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getClassList4StudentNotice")
	public Result<List<ClassToTeacher>> getClassList4StudentNotice(HttpServletRequest request) {
		Result<List<ClassToTeacher>> result = new Result<List<ClassToTeacher>>();
		List<ClassToTeacher> classList = null;
		try {
			SessionInfo sessionInfo = getSessionInfo(request);
			int org_id = sessionInfo.getOrgId();
			int userId = sessionInfo.getUserId();
			
			String noticeId = request.getParameter("noticeId");
			
			if (StringUtils.isNoneBlank(String.valueOf(org_id), String.valueOf(userId), noticeId)) {
				// 根据通知记录查询发送时选择的班级
				Notice notice = noticeService.getNoticeRecord(String.valueOf(org_id), noticeId, "0");
				if (notice != null && notice.getOrg_id() != null && notice.getOrg_id().intValue() > 0) {
					String target = notice.getTarget();
					if (StringUtils.isNotBlank(target)) {
						// 根据班级查询班级列表
						String[] classIdArr = target.split(",");
						classList = noticeService.getClassListByIds(org_id, classIdArr);
						if (classList != null && classList.size() > 0) {
							result.setData(classList);
						}
					}
				}
				
				result.setSuccess(true);
			} else {
				throw new Exception("撤回通知接口参数不完整！");
			}
		} catch (Exception ex) {
			result.setSuccess(false);
			result.setMessage(ex.toString());
		}
		
		return result;
	}
	
	
	/**
	 * 获取重新发送所需的参数
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getStudentNotice4Resend")
	public Result<Map<String, String>> getStudentNotice4Resend(HttpServletRequest request) {
		Result<Map<String, String>> result = new Result<Map<String, String>>();
		Map<String, String> resultMap = new HashMap<String, String>();
		
		try {
			SessionInfo sessionInfo = getSessionInfo(request);
			int org_id = sessionInfo.getOrgId();
			int userId = sessionInfo.getUserId();
			
			String noticeId = request.getParameter("noticeId");
			
			String target = null;
			String content = null;
			
			if (StringUtils.isNoneBlank(String.valueOf(org_id), String.valueOf(userId), noticeId)) {
				// 根据通知记录查询发送时选择的班级
				Notice notice = noticeService.getNoticeRecord(String.valueOf(org_id), noticeId, "0");
				if (notice != null && notice.getOrg_id() != null && notice.getOrg_id().intValue() > 0) {
					target = notice.getTarget();
					content = notice.getContent();
				}
				
				result.setSuccess(true);
			} else {
				throw new Exception("获取重新发送通知所需参数接口参数不完整！");
			}
			
			resultMap.put("target", target);
			resultMap.put("content", content);
			result.setData(resultMap);
		} catch (Exception ex) {
			result.setSuccess(false);
			result.setMessage(ex.toString());
		}
		
		return result;
	}
	
	
	/**
	 * 微信通知查看详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getStudentNoticeRecord.htm")
	public Result<Notice> getStudentNoticeRecord(HttpServletRequest request) {
		Result<Notice> result = new Result<Notice>();
		
		try {
			SessionInfo sessionInfo = getSessionInfo(request);
			int org_id = sessionInfo.getOrgId();
			int userId = sessionInfo.getUserId();
			
			String noticeId = request.getParameter("noticeId");
			
			if (StringUtils.isNoneBlank(String.valueOf(org_id), String.valueOf(userId), noticeId)) {
				// 根据通知记录查询发送时选择的班级
				Notice notice = noticeService.getNoticeRecord(String.valueOf(org_id), noticeId, "0");
				if (notice != null && notice.getOrg_id() != null && notice.getOrg_id().intValue() > 0) {
					result.setData(notice);
					result.setSuccess(true);
				} else {
					result.setSuccess(false);
					result.setMessage("根据传入的记录id未查询到对应的数据库记录！");
				}
			} else {
				throw new Exception("查看通知接口参数不完整！");
			}
		} catch (Exception ex) {
			result.setSuccess(false);
			result.setMessage(ex.toString());
		}
		
		return result;
	}
	
	
	/**
	 * 获取接收到的学生通知列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getReceivingStudentNoticeList.htm")
	public Result<List<Notice>> getReceivingStudentNoticeList(HttpServletRequest request) {
		Result<List<Notice>> result = new Result<List<Notice>>();

		List<Notice> noticeList = new ArrayList<Notice>();
		try {
			SessionInfo sessionInfo = getSessionInfo(request);
			int org_id = sessionInfo.getOrgId();
			// int org_id=393;
			//int userId = sessionInfo.getUserId();
			String userId = request.getParameter("user_id");
			String length = request.getParameter("length");
			String recordId = request.getParameter("recordId");
			// direction(0:刷新（下拉）；1：上划)
			String direction = request.getParameter("direction");

			noticeList = noticeService.getReceivingStudentNoticeList(String.valueOf(org_id), userId, recordId, direction, length);

			result.setSuccess(true);
			result.setData(noticeList);
		} catch (Exception ex) {
			result.setSuccess(false);
			result.setMessage(ex.toString());
		}

		return result;
	}
}
