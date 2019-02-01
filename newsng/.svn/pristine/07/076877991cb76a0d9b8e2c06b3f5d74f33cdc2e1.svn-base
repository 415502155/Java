package sng.service;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sharding.entity.Notice;
import sharding.entity.NoticeDetail;
import sng.dao.ClassDao;
import sng.dao.NoticeDao;
import sng.dao.NoticeDetailDao;
import sng.entity.Classes;
import sng.pojo.ClassToTeacher;
import sng.pojo.StudentRoster;
import sng.pojo.WXTemplateMessage;
import sng.pojo.WXTemplateMessageData;
import sng.util.Constant;
import sng.util.JsonUtil;
import sng.util.Paging;
import sng.util.SendMessageUtil;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author sw
 * 
 */
@Service
@Transactional
public class NoticeService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private NoticeDao noticeDao;
	
	@Autowired
	private NoticeDetailDao noticeDetailDao;
	
	@Autowired
	private ClassDao classDao;
	
	@Autowired
	private MQService mqService;
	
	@Autowired
	private TokenService tokenService;
	
	public void saveNotice(Notice notice) {
		noticeDao.save(notice);
	}
	
	/**
	 * 点击发送通知后2分钟，进行发送通知
	 * @param messageMap
	 */
	public void splitNoticeToNoticeDetail(Map<String, Object> messageMap) {
		logger.info("进入splitNoticeToNoticeDetail方法，参数【{}】", messageMap.toString());
		String org_id = (String) messageMap.get("org_id");
		String recordId = (String) messageMap.get("recordId");
		
		if (StringUtils.isNoneBlank(org_id, recordId)) {
			// 查询记录是否存在和是否已被撤回
			Notice notice = noticeDao.getNoticeRecord(org_id, recordId);
			if (notice != null && notice.getId() != null) {
				// 如果通知被撤回则不发送
				if (notice.getStatus().intValue() == 0) {
					// 发送类型：0-学生通知；1-教师通知
					if (notice.getType().intValue() == 0) {
						// 根据发送的班级和目标状态查询出学生对应的家长
						String target = notice.getTarget();
						int status = notice.getTarget_status();
						String[] classIdArr = target.split(",");
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy年M月d日 HH:mm");
						String date = sdf.format(notice.getInsert_time());
						
						int totalNum = 0;
						for (String classId : classIdArr) {
							// 查询班级是否存在
							Classes c = classDao.get(Classes.class, Integer.valueOf(classId));
							if (c != null && c.getClas_id() != null && c.getClas_id().intValue() > 0 && c.getIs_del() != null
									&& c.getIs_del().intValue() == 0) {
								// 查询班级中符合状态的学生及家长信息
								List<StudentRoster> roster = null;
								if (0 == status) {
									// 查询全部状态的学生
									roster = classDao.getStudentRosterList(Integer.valueOf(org_id), c.getClas_id(), null);
								} else {
									roster = classDao.getStudentRosterList(Integer.valueOf(org_id), c.getClas_id(), String.valueOf(status));
								}
								
								Date currentDate = new Date();
								if (roster != null && roster.size() > 0) {
									// 存在学生信息
									for (StudentRoster student : roster) {
										NoticeDetail noticeDetail = new NoticeDetail();
										noticeDetail.setNotice_id(notice.getId());
										noticeDetail.setOrg_id(notice.getOrg_id());
										noticeDetail.setType(0);
										noticeDetail.setUser_id(student.getStu_userId());
										noticeDetail.setUser_name(student.getStud_name());
										noticeDetail.setClass_id(c.getClas_id());
										noticeDetail.setStatus(0);
										noticeDetail.setInsert_time(new Timestamp(currentDate.getTime()));
										noticeDetail.setUpdate_time(new Timestamp(currentDate.getTime()));
										noticeDetail.setIs_del(0);
										noticeDetail.setDept_id(0);
										
										noticeDetailDao.save(noticeDetail);
										
										if (StringUtils.isNotBlank(student.getOpenid())) {
											// 存在openid才能进行发送，放入发送队列
											Map<String, Object> paramMap = new HashMap<String, Object>();
											paramMap.put("org_id", org_id);
											// 1教师 0家长 2学生
											paramMap.put("identity", "0");
											paramMap.put("openId", student.getOpenid());
											paramMap.put("recordId", noticeDetail.getId_str());
											paramMap.put("className", c.getClas_name());
											paramMap.put("studentName", student.getStud_name());
											paramMap.put("parentName", student.getParent_name());
											paramMap.put("senderName", notice.getSender_name());
											paramMap.put("date", date);
											paramMap.put("attempts", 0);
											paramMap.put("noticeId", recordId);
											paramMap.put("receiverUserId", String.valueOf(student.getStu_userId()));
											mqService.sendMessage("sendNoticeExchange", "sendNotice", paramMap);
											logger.error("放入发送通知队列，paramMap={}", paramMap);
										} else {
											logger.error("班级=【{}】里的学生【{}】的家长【{}】没有openid，无法发送微信通知！", c.getClas_name(),
													student.getStud_name(), student.getParent_name());
										}
										
										totalNum++;
									}
								} else {
									logger.error("班级=【{}】没有查询到状态为【{}】的学生记录！", c.getClas_name(), String.valueOf(status));
								}
							} else {
								logger.error("班级id={}的记录未查询到！", classId);
							}
						}
						
						//notice.setTotal_num(totalNum);
						noticeDao.updateNoticetotalNum(org_id, recordId, totalNum);
						
						// 拆分后更改状态
						noticeDao.updateNoticeStatus(org_id, recordId, 2);
					} else {
						// 教师通知
						// TODO
					}
					
					try {
						logger.info("少年宫【{}】通知【{}】执行发送！", org_id, JsonUtil.toJson(notice));
					} catch (IOException e) {
						e.printStackTrace();
						logger.error(e.toString());
					}
				}
			} else {
				logger.error("机构ID=【{}】，recordID=【{}】不能找到对应的通知记录！", org_id, recordId);
			}
		}
	}
	
	/**
	 * 真正开始下发微信通知
	 * @param messageMap
	 */
	public void doSendNotice(Map<String, Object> messageMap) {
		logger.error("进入doSendNotice方法");
		int attempts = (int) messageMap.get("attempts");
		// 1教师 0家长 2学生
		String identity = (String) messageMap.get("identity");
		// noticeDetail表记录id
		String recordId = (String) messageMap.get("recordId");
		String org_id = (String) messageMap.get("org_id");
		String openId = (String) messageMap.get("openId");
		String senderName = (String) messageMap.get("senderName");
		String date = (String) messageMap.get("date");
		String noticeId = (String) messageMap.get("noticeId");
		String receiverUserId = (String) messageMap.get("receiverUserId");
		if (attempts < 3) {
			if (StringUtils.isNoneBlank(org_id, identity, openId, noticeId, recordId)) {
				
				// 获取notice对象
				Notice notice = noticeDao.getNoticeRecord(org_id, noticeId);
				if (notice != null && notice.getOrg_id() != null && notice.getOrg_id().intValue() > 0) {
					NoticeDetail detail = noticeDetailDao.getNoticeDetail(org_id, receiverUserId, recordId);
					if (detail != null && detail.getOrg_id() != null && detail.getOrg_id().intValue() > 0) {
						if ("0".equals(identity)) {
							// 发送学生通知
							String className = (String) messageMap.get("className");
							String studentName = (String) messageMap.get("studentName");
							String parentName = (String) messageMap.get("parentName");
							try {
								// 获取模板id
								String templateId = tokenService.getSNGWXTemplateId(org_id, Constant.TEMPLATE_NAME_CLASS_INFORM);
								
								String access_token = tokenService.getAccessTokenByOrg_Id(Integer.valueOf(org_id));
								
								// 设置WXTemplateMessage的各项值
								WXTemplateMessage templateMessage = new WXTemplateMessage();
								templateMessage.setTouser(openId);
								templateMessage.setTemplate_id(templateId);
								// 手机端完成后可确定url地址
								templateMessage.setUrl(Constant.DOMAIN_NAME + "/newsng/weixin/html/parent/notice/detail.html?org_id=" + org_id
										+ "&identity=" + identity + "&openid=" + openId + "&recordID=" + noticeId);

								Map<String, WXTemplateMessageData> data = new HashMap<String, WXTemplateMessageData>();
								data.put("first", new WXTemplateMessageData("您好，您的孩子"+studentName+"有一条新的班级通知"));
								// 班级
								data.put("keyword1", new WXTemplateMessageData(className));
								// 通知人
								data.put("keyword2", new WXTemplateMessageData(senderName));
								// 时间
								data.put("keyword3", new WXTemplateMessageData(date));
								// 通知内容
								String content = notice.getContent();
								data.put("keyword4", new WXTemplateMessageData(content.length() > 20 ? content.substring(0, 20) + "……" : content));
								// 备注
								data.put("remark", new WXTemplateMessageData("点击查看通知详情"));
								templateMessage.setData(data);
								
								// 进行微信消息发送
								String result = SendMessageUtil.sendTemplateMessageToWeiXin(access_token, templateMessage);
								ObjectMapper mapper = JsonUtil.getMapperInstance();
								JsonNode node = mapper.readTree(result);
								if (node.get("errcode").asInt() == 0 && "ok".equals(node.get("errmsg").asText())) {
									// 发送成功，更新数据库记录
									noticeDetailDao.updateNoticeDetailStatus(org_id, receiverUserId, recordId, 1);
								} else {
									logger.error("发送模板消息时失败:errorMessage=[{}]。org_id=[{}], templateMessage=[{}]", node.get("errmsg").asText(), org_id, JsonUtil.toJson(templateMessage));
									attempts++;
									messageMap.put("attempts", attempts);
									// 进行重新发送
									mqService.sendMessage("sendNoticeExchange", "sendNotice", messageMap);
								}
							} catch (Exception e) {
								e.printStackTrace();
								attempts++;
								messageMap.put("attempts", attempts);
								// 进行重新发送
								mqService.sendMessage("sendNoticeExchange", "sendNotice", messageMap);
							}
						} else if ("1".equals(identity)) {
							// 发送教师通知
							// TODO
						}
					} else {
						logger.error("通知记录详情未找到或已被删除！org_id=[],user_id=[],recordId=[]", org_id, receiverUserId, recordId);
					}
				} else {
					logger.error("通知记录未找到或已被删除！org_id=[], recordId=[]", org_id, recordId);
				}
			} else {
				logger.error("发送学生通知时参数不全，org_id=[], identity=[], openId=[], noticeId=[], recordId=[]", org_id, identity, openId, noticeId, recordId);
			}
		} else {
			if ("0".equals(identity)) {
				logger.error("尝试3发送学生通知全部失败，记录ID：【{}】", recordId);
			} else if ("1".equals(identity)) {
				logger.error("尝试3发送教师通知全部失败，记录ID：【{}】", recordId);
			}
		}
	}
	
	
	/**
	 * 根据条件查询通知发送历史
	 * @param org_id
	 * @param beginDate
	 * @param endDate
	 * @param keyWord
	 * @param paging
	 */
	public void getNoticePaging(String org_id, String userId, String beginDate, String endDate, String keyWord, Paging<Notice> paging) {
		long totalNum = 0;
		List<Notice> noticeList = null;
		
		BigInteger total = noticeDao.getTotalNumOfStudentNoticeSendHistory(org_id, userId, beginDate, endDate, keyWord);
		totalNum = total.longValue();
		
		if (totalNum > 0) {
			// 进行查询
			noticeList = noticeDao.getNoticeList4Paging(org_id, userId, beginDate, endDate, keyWord, paging.getPage(), paging.getLimit());
		}
		
		paging.setTotal(totalNum);
		paging.setData(noticeList);
	}
	
	
	/**
	 *  2分钟内撤回待发送的通知
	 * @param org_id
	 * @param noticeId
	 * @param type 发送类型：0-学生通知；1-教师通知
	 * @return
	 */
	public String revocationNotice(String org_id, String noticeId, String type) {
		String message = "";
		
		Notice notice = noticeDao.getNoticeRecord(org_id, noticeId);
		if (notice != null && notice.getId() != null && notice.getId().longValue() > 0) {
			// 判断类型是否匹配
			if (Integer.valueOf(type).intValue() == notice.getType().intValue()) {
				// 判断是否超过2分钟，超过不能进行撤回操作
				if (notice.getStatus().intValue() == 0 && notice.isCanRevocation()) {
					// 未超2分钟，进行撤回
					// 状态（0：待发送；1：已撤回；2：已发送）
					int result = noticeDao.updateNoticeStatus(org_id, noticeId, 1);
					if (result == 1) {
						message = "通知撤回成功！";
					} else {
						message = "通知撤回失败！";
					}
				} else {
					message = "已超过2分钟，不能进行撤回操作！";
				}
			} else {
				message = "要撤回的通知记录类型与数据库中不符！";
				logger.error("要撤回的通知记录类型与数据库中不符！org_id=[], recordId=[],type=[]", org_id, noticeId, type);
			}
		} else {
			message = "通知记录未找到或已被删除！";
			logger.error("通知记录未找到或已被删除！org_id=[], recordId=[]", org_id, noticeId);
		}
		
		return message;
	}
	
	/**
	 * 根据类型获取已发送的通知
	 * @param org_id
	 * @param recordId
	 * @param type
	 * @return
	 */
	public Notice getNoticeRecord(String org_id, String recordId, String type) {
		return noticeDao.getNoticeRecord(org_id, recordId, type);
	}
	
	/**
	 * 根据班级id获取班级集合
	 * @param org_id
	 * @param classIdArr
	 * @return
	 */
	public List<ClassToTeacher> getClassListByIds(int org_id, String[] classIdArr) {
		List<ClassToTeacher> classList = null;
		if (classIdArr != null && classIdArr.length > 0) {
			classList = new ArrayList<ClassToTeacher>();
			for (String classId : classIdArr) {
				ClassToTeacher c = classDao.getClassToTeacher(org_id, Integer.valueOf(classId));
				classList.add(c);
			}
		}
		
		return classList;
	}
	
	/**
	 * 获取某机构下某学生的通知列表
	 * @param org_id
	 * @param user_id
	 * @param recordId
	 * @param direction
	 * @param length
	 * @return
	 */
	public List<Notice> getReceivingStudentNoticeList(String org_id, String user_id, String recordId, String direction, String length) {
		return noticeDao.getReceivingNoticeList(org_id, user_id, recordId, direction, length, "0");
	}
}
