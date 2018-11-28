package sng.controller.web;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sng.pojo.Clas2Student;
import sng.pojo.Classes;
import sng.pojo.RefundInfo;
import sng.pojo.Result;
import sng.service.AccountService;
import sng.service.ChargeDetailService;
import sng.service.ChargeRecordService;
import sng.service.ChargeService;
import sng.service.StudentClassService;
import sng.service.StudentService;
import sng.service.TokenService;
import sng.util.Constant;
import sng.util.ESBPropertyReader;
import sng.util.ExcelUtil;
import sng.util.HttpClientUtil;
import sng.util.JsonUtil;
import sng.util.MoneyUtil;
import sng.util.Paging;
import sng.constant.Consts;
import sng.controller.common.BaseAction;
import sng.entity.Account;
import sng.entity.Charge;
import sng.entity.ChargeDetail;
import sng.entity.ChargeRecord;
import sng.entity.StudentClass;
import sng.pojo.base.Parent;
import sng.pojo.base.Teacher;
import unionpay.acp.sdk.LogUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.opencsv.CSVWriter;

@Controller
@RequestMapping("/portal/charge")
public class ChargeWebController extends BaseAction{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private StudentService studentService;
	@Autowired
	private StudentClassService studentClassService;
	@Autowired
	private ChargeService chargeService;
	@Autowired
	private ChargeDetailService chargeDetailService;
	@Autowired
	private ChargeRecordService chargeRecordService;
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private TokenService tokenService;
	
	/**
	 * @Title : orderPay 
	 * @功能描述:预约缴费 
	 * @返回类型：Result<String> 
	 * @throws ：
	 */
	@RequestMapping(value = "/orderPay.json")
	@ResponseBody
	public Result<String> orderPay(HttpServletRequest request,
			@RequestParam(name = "stu_clas_id") Integer stu_clas_id,
			HttpServletResponse response, HttpSession session) throws Exception {
		Result<String> result = new Result<String>();
		StudentClass sc = studentClassService.getById(stu_clas_id);
		if(sc.getStuStatus().equals(Consts.STUD_STATUS_3SIGNUP_TO_PAY)){
			sc.setStuStatus(Consts.STUD_STATUS_2QUOTA_RESERVED);
			studentClassService.orderPay(sc);
			result.setSuccess(true);
		}else{
			result.setMessage("学员状态不符合要求，预约失败！");
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 * 申请代缴
	 * 教师线下收费后确认已代收代缴
	 * cd_id：家长分订单主键
	 */
	@RequestMapping(value = "/paid.json")
	@ResponseBody
	public Result<String> paid(HttpServletRequest request, 
			@RequestParam(name = "stu_class_id") Integer stu_class_id,
			@RequestParam(name = "money") String money,
			@RequestParam(name = "pay_method",defaultValue="1") Integer pay_method,
			HttpServletResponse response, HttpSession session) throws Exception {
		Result<String> result = new Result<String>();
		String teacherId = "";
		String teacherName = "";
		String orgUserId = "";
		try {		
			orgUserId = request.getParameter("user_id").toString();
			Teacher teacher = this.getTechByUId(orgUserId);
			teacherId = teacher.getTech_id().toString();
			teacherName = teacher.getTech_name();
		} catch (Exception e) {
			result.setMessage("获取登录信息不完整！");
			result.setSuccess(false);
			return result;
		}
		if (!NumberUtils.isParsable(money)) {
			result.setMessage("输入的缴费金额不是数字！");
			result.setSuccess(false);
		}else if(money.contains(".")&&money.split("\\.")[1].length()>2){
			result.setMessage("输入的缴费金额只能精确到分！");
			result.setSuccess(false);
		}else if(new BigDecimal(MoneyUtil.fromYUANtoFEN(money)).compareTo(new BigDecimal(Constant.MAX_MONEY))>0){
			result.setMessage("输入的缴费金额过大！");
			result.setSuccess(false);
		}else if(new BigDecimal(MoneyUtil.fromYUANtoFEN(money)).compareTo(new BigDecimal(Constant.MIN_MONEY))<0){
			result.setMessage("输入的缴费金额错误！");
			result.setSuccess(false);
		}else{
			StudentClass sc = studentClassService.getInfosById(stu_class_id);
			if(sc.getStuStatus().equals(Consts.STUD_STATUS_2QUOTA_RESERVED)
				||sc.getStuStatus().equals(Consts.STUD_STATUS_3SIGNUP_TO_PAY)){
				ChargeDetail chargeDetail = chargeDetailService.getByStuClaId(stu_class_id);
				if(new BigDecimal(money).compareTo(new BigDecimal(chargeDetail.getMoney()))>0){
					result.setMessage("输入的缴费金额超过了应收金额！");
					result.setSuccess(false);
					return result;
				}
				if(chargeDetail.getStatus()==Constant.CHARGE_DETAIL_STATUS_NEVER){
					//加乐观锁
					Date lockDate = new Date();
					try {
						ChargeDetail temp = new ChargeDetail();
						temp.setCd_id(chargeDetail.getCd_id());
						Integer counter = chargeDetailService.lockChargeDetail(lockDate,temp);
						if(counter==1){
							ChargeDetail cd = chargeDetailService.getById(chargeDetail.getCd_id());
							if(cd.getOffline_pay().equals(Constant.CHARGE_DETAIL_STATUS_NEVER)
									&&cd.getOnline_pay().equals(Constant.CHARGE_DETAIL_STATUS_NEVER)
									&&cd.getStatus().equals(Constant.CHARGE_DETAIL_STATUS_NEVER)){
								//开始支付
								cd.setOffline_pay(Constant.CHARGE_DETAIL_STATUS_OK);
								cd.setStatus(Constant.CHARGE_DETAIL_STATUS_OK);
								cd.setTxnAmt(MoneyUtil.fromYUANtoFEN(cd.getMoney()));
								cd.setTxnTime(new Date());
								Date payDate = new Date();
								cd.setModify_time(payDate);
								cd.setPay_method(pay_method);
								Integer pay = chargeDetailService.updateWithLock(cd,lockDate);
								//创建支付记录
								if(pay==1){
									//添加支付记录
									ChargeRecord cr = new ChargeRecord();
									cr.setTxnType(Constant.TXNTYPE_SJWY_OFFLINE_PAY);
									cr.setCid(cd.getCid());
									cr.setCd_id(cd.getCd_id());
									cr.setTxnAmt(cd.getTxnAmt());
									cr.setTxnTime(payDate);
									cr.setInsert_time(new Date());
									cr.setOrg_user_id(Integer.parseInt(orgUserId));
									cr.setUser_identify(Constant.IDENTITY_TEACHER);
									cr.setUser_type_id(Integer.parseInt(teacherId));
									cr.setUser_type_name(teacherName);
									chargeRecordService.save(cr);
									//更新支付总表
									ChargeRecord query = new ChargeRecord();
									query.setCid(cd.getCid());
									List<ChargeRecord> list = chargeRecordService.getList(query);
									Integer no_pay_num = chargeDetailService.countNoPay(cd.getCid());
									Charge charge = chargeService.getById(cd.getCid());
									charge.setNopay_num(no_pay_num);
									charge = chargeService.updateStatus(list,charge);
									result.setMessage("缴费成功！");
									result.setSuccess(true);
									sendSuccessMessage(sc,cd,charge);
									logger.info("【线下支付成功】支付ChargeDetail主键:"+cd.getCd_id());
								}else{
									logger.error("【线下支付失败】校验账单乐观锁时失败。ChargeDetail主键:"+chargeDetail.getCd_id());
									result.setSuccess(false);
									result.setMessage("请求失败！原因：账单状态变动，请确认后重试！");
								}
							}else{
								result.setMessage("请求失败！原因：账单状态变动，请确认后重试！");
								result.setSuccess(false);
							}
						}else{
							logger.error("【线下支付错误】为支付订单设置乐观锁时失败。ChargeDetail主键:"+chargeDetail.getCd_id());
							result.setSuccess(false);
							result.setMessage("请求失败！原因：数据库异常，请稍候重试！");
						}
					} catch (Exception e) {
						logger.error("【线下支付错误】支付订单时发生错误。ChargeDetail主键:"+chargeDetail.getCd_id());
						result.setSuccess(false);
						result.setMessage("请求失败！原因：数据库异常，请稍候重试！");
					}
				}else{
					result.setMessage("学员状态不符合要求，缴费失败！");
					result.setSuccess(false);
				}
			}else{
				result.setMessage("学员状态不符合要求，预约失败！");
				result.setSuccess(false);
			}
		}
		return result;
	}
	
	/**
	 * @Title : sendSuccessMessage 
	 * @功能描述: 发送支付成功通知
	 * @返回类型：void 
	 * @throws ：
	 */
	private void sendSuccessMessage(StudentClass sc,ChargeDetail cd,Charge charge){
		Map<String, Object> documentMap = new HashMap<String, Object>();
		documentMap.put("stud_id", sc.getStudId());
		List<Parent> parents = new ArrayList<Parent>();
		
		try {
			String documentResult = HttpClientUtil.post(ESB_REQUEST_URL+"getParentPhone","stud_id="+sc.getStudId(), "application/x-www-form-urlencoded");
			Result<List<Parent>> pResult = JsonUtil.getObjectFromJson(documentResult, new TypeReference<Result<List<Parent>>>() { });
			parents = pResult.getData();
			String template_id = "";
			try {
				template_id = tokenService.getWxTemplateId(cd.getOrg_id().toString(), Constant.TEMPLATE_NAME_CHARGE_SUCCESS_NOTICE);
			} catch (Exception e) {
			}
			if(null!=parents&&parents.size()!=0){
				for (Parent p : parents) {
					Map<String, Object> map = new HashMap<String, Object>();
					if(StringUtils.isNotBlank(p.getMobile())){//SMS
						map.put("cd_id", cd.getCd_id());
						map.put("mobile", p.getMobile());
						map.put("content", String.format(Constant.MOBILE_MESSAGE_CHARGE_SUCCESS_FOR_PARENT,sc.getStud_name(),cd.getItem()));
					}
					if(StringUtils.isNotBlank(p.getOpenid())&&StringUtils.isNotBlank(template_id)){//WX
						map.put("open_id", p.getOpenid());
						map.put("templateId", template_id);
						map.put("cd_id", cd.getCd_id());
						map.put("org_id", cd.getOrg_id());
						map.put("first", "尊敬的家长，您已支付成功！");
						map.put("keyword1", cd.getOrder_id());
						map.put("keyword2", sc.getCategory_name()+" "+sc.getSubject_name());
						if(StringUtils.isNotBlank(sc.getClass_unset_time())){
							map.put("keyword3", sc.getClass_unset_time());
						}else{
							map.put("keyword3", Consts.WEEK_MAP.get(sc.getClass_week())+" "+Constant.time.format(sc.getClass_begin_time())+"-"+Constant.time.format(sc.getClass_over_time()));
						}
						map.put("keyword4", sc.getCam_name()+" "+sc.getClassroom_name());
						map.put("remark", "您可以在“我的凭证”中查看您的“电子学员凭证”");
					}
					rabbitTemplate.convertAndSend("send_charge_message_exchange", null, map);
				}
			}
		} catch (Exception e) {
			LogUtil.writeErrorLog("【错误】发送支付提醒时发生异常，未能向stud_id="+sc.getStuClassId()+"的学生家长发送任何缴费提醒!");
		}
	}
	
/*
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static String CHARGE_DETAIL_EXCEL = ESBPropertyReader.getProperty("chargeDetailExcel");
	private static String ESB_REQUEST_URL = ESBPropertyReader.getProperty("esbRequestUrl");
	private static String ESB_API_REQUEST_URL = ESBPropertyReader.getProperty("esbApiRequestUrl");
	
	@Autowired
	private ChargeService chargeService;
	@Autowired
	private ChargeDetailService chargeDetailService;
	@Autowired
	private ChargeRecordService chargeRecordService;
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private AccountService accountService;

	*//**
	 * 收费列表
	 * 查询机构下所有支付列表
	 * @param currentPageNum 页码
	 * @param pageSize 每页显示条数
	 * @param starttime 开始时间
	 * @param endtime 截止时间
	 * @param status 状态
	 * @param item 收费项
	 *//*
	@RequestMapping(value="/getList.json")
	@ResponseBody
	public Result<Paging<Charge>> getList(HttpServletRequest request,
			@RequestParam(name = "currentPageNum", defaultValue="1") Integer currentPageNum,
			@RequestParam(name = "pageSize", defaultValue=Constant.NUM_PER_PCPAGE_STRING) Integer pageSize,
			@RequestParam(name = "starttime", defaultValue="1900-01-01 00:00:00") String starttime,  
			@RequestParam(name = "endtime", defaultValue="2100-01-01 00:00:00") String endtime,  
			@RequestParam(name = "status", defaultValue="") Integer status,  
			@RequestParam(name = "item", defaultValue="") String item,  
			HttpServletResponse response, HttpSession session) {
		Result<Paging<Charge>> result = new Result<Paging<Charge>>();
		Integer org_id = Integer.parseInt(request.getParameter("org_id").toString());
		Paging<Charge> page = new Paging<Charge>();
		page.setPage(currentPageNum);
		page.setLimit(pageSize);
		Charge charge = new Charge();
		charge.setOrg_id(org_id);
		try {
			charge.setStart_time(Constant.sdf.parse(starttime+" 00:00:00"));
			charge.setEnd_time(Constant.sdf.parse(endtime+" 23:59:59"));
		} catch (Exception e) {
			logger.error("时间格式化错误！");
		}
		charge.setStatus(status);
		charge.setItem(item);
		page = chargeService.getTList(charge,page);
		result.setData(page);
		result.setSuccess(true);
		return result;
	}

	*//**
	 * 获取收费账户
	 *//*
	@RequestMapping(value="/getAccount.json")
	@ResponseBody
	public Result<List<Account>> getAccount(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		Result<List<Account>> resultEntity = new Result<List<Account>>();
		Integer org_id = Integer.parseInt(request.getParameter("org_id").toString());
		List<Account> list = accountService.getList(org_id);
		resultEntity.setData(list);
		resultEntity.setSuccess(true);
		return resultEntity;
	}

	*//**
	 * 获取收费管理员
	 * @throws Exception 
	 *//*
	@RequestMapping(value="/getChargeManager.json")
	@ResponseBody
	public Result<List<Teacher>> getChargeManager(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Integer org_id = Integer.parseInt(request.getParameter("org_id").toString());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("org_id", org_id);
		Result<List<Teacher>> result = new Result<List<Teacher>>();
		result.setSuccess(true);
		List<Teacher> list = new ArrayList<Teacher>();
		String resultManagerStr = this.callUrl(request, "api/tech/getTechsOfRlId", paramMap);
		Result<Map<String,List<Teacher>>> managerResult = JsonUtil.getObjectFromJson(resultManagerStr, new TypeReference<Result<Map<String,List<Teacher>>>>() { });
		for (Teacher t : managerResult.getData().get("techs")) {
			list.add(t);
		}
		result.setData(list);
		return result;
	}

	*//**
	 * 验证校验码
	 * @throws Exception 
	 *//*
	@RequestMapping(value="/validCode.json")
	@ResponseBody
	public Result<String> validCode(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		String tech_id = request.getParameter("tech_id");
		String code = request.getParameter("code");
		String phone = "";
		Integer org_id = Integer.parseInt(request.getParameter("org_id").toString());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("org_id", org_id);
		String resultManagerStr = this.callUrl(request, "api/tech/getTechsOfRlId", paramMap);
		Result<Map<String,List<Teacher>>> managerResult = JsonUtil.getObjectFromJson(resultManagerStr, new TypeReference<Result<Map<String,List<Teacher>>>>() { });
		for (Teacher t : managerResult.getData().get("techs")) {
			if(t.getTech_id().toString().equals(tech_id)){
				phone = t.getUser_mobile();
				break;
			}
		}
		String validResult = HttpClientUtil.post(ESB_REQUEST_URL+"validCode", "phone="+phone+"&code="+code, "application/x-www-form-urlencoded");
		Result<String> vResult = JsonUtil.getObjectFromJson(validResult, new TypeReference<Result<String>>() {});
		return vResult;
	}

	*//**
	 * 上传收费明细Excel
	 * @throws Exception 
	 *//*
	@RequestMapping(value="/uploadExcel.json")
	@ResponseBody
	public Result<Map<String,Object>> uploadExcel(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Result<Map<String,Object>> result = new Result<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		Workbook wb = null;
		BigDecimal min = new BigDecimal("0");
		BigDecimal max = new BigDecimal("0");
		BigDecimal total = new BigDecimal("0");
		String org_id = request.getParameter("org_id").toString();
		int ys_num = 0;
		int errorNum = 0;
		JSONArray arr = new JSONArray();
		List<String[]> refundExcel = new ArrayList<String[]>();
		try {
			wb = ExcelUtil.getExcelFromRequest(request);
			refundExcel = ExcelUtil.getExcel(wb,Constant.CHARGE_EXCEL_COLUMN_WITH_ERROR);
		} catch (Exception e) {
		}
		ys_num = refundExcel.size();
		for (String[] row : refundExcel) {	
			int idx = row[0].lastIndexOf("l");
			String str = row[0].substring(idx + 1, row[0].length());
			arr.add(str);
		}
		String resultJsonStr = this.callPostUrlJson(request, "api/student/getStudentsByRange", "{\"s\":"+arr.toJSONString()+"}");
		Result<List<Clas2Student>> studentResult = JsonUtil.getObjectFromJson(resultJsonStr, new TypeReference<Result<List<Clas2Student>>>() { });
			
		Map<String, String> studentsMap = new HashMap<String, String>();
		if (studentResult != null && studentResult.getCode() == 200) {
			List<Clas2Student> list = studentResult.getData();
			if (list != null && list.size() > 0) {
				for (Clas2Student s : list) {
					studentsMap.put(org_id+"l"+s.getGrade_id()+"l"+s.getClas_id()+"l"+s.getStud_id(), s.getStud_name());
				}
			}
		}
		// 遍历集合进行校验
		for (String[] row : refundExcel) {
			row[5] = "";
			if(StringUtils.isBlank(row[0])){
				row[5] += "身份唯一编码不能为空！";
			}else if(!studentsMap.containsKey(row[0])){
				row[5] += "输入的身份唯一编码没有在数据库中找到匹配值！建议重新下载模板。";
			}
			if(StringUtils.isBlank(row[3])){
				row[5] += "学生姓名不能为空！";
			}else if(!studentsMap.containsKey(row[0])){
				row[5] += "没有在数据库中匹配到学生！";
			}else if(!studentsMap.get(row[0]).equals(row[3])){
				row[5] += "学生姓名与身份唯一编码不匹配！";
			}
			if (!NumberUtils.isParsable(row[4])) {
				row[5] += "输入的缴费金额不是数字！";
			}else if(row[4].contains(".")&&row[4].split("\\.")[1].length()>2){
				row[5] += "输入的缴费金额只能精确到分！";
			}else if(new BigDecimal(MoneyUtil.fromYUANtoFEN(row[4])).compareTo(new BigDecimal(Constant.MAX_MONEY))>0){
				row[5] += "输入的缴费金额过大！";
			}else if(new BigDecimal(MoneyUtil.fromYUANtoFEN(row[4])).compareTo(new BigDecimal(Constant.MIN_MONEY))<0){
				row[5] += "输入的缴费金额错误！";
			} else {
				BigDecimal money = new BigDecimal(row[4]);
				total =  total.add(money); 
				if(new BigDecimal("0").compareTo(min)==0&&new BigDecimal("0").compareTo(max)==0){
					min = money;
					max = money;
				}else{
					if(money.compareTo(min)<1){
						min = money;
					}
					if(money.compareTo(max)>-1){
						max = money;
					}
				}
			}
			if(!StringUtils.isBlank(row[5])){
				errorNum ++;
			}
		}	
		map.put("ys_num", ys_num);
		map.put("errorNum", errorNum);
		if(min.compareTo(max)==0){
			map.put("money", max.toString());
			session.setAttribute("money", max.toString());
		}else{
			map.put("money", min.toString()+"~"+max.toString());
			session.setAttribute("money", min.toString()+"~"+max.toString());
		}
		map.put("total", total.toString());
		session.setAttribute("ys_num", ys_num);
		session.setAttribute("total", total.toString());
		session.setAttribute("chargeExcel", JsonUtil.toJson(refundExcel, Include.ALWAYS));
		session.setAttribute("studentsMap", studentsMap);
		session.setAttribute("checker", "checker");
		result.setData(map);
		if(errorNum>0||ys_num==0){
			map.put("duplication", 0);
			result.setMessage("校验失败!");
		}else{
			String item = request.getParameter("item").toString();
			Integer duplication = chargeService.duplication(org_id,item,map.get("money").toString(),"{\"s\":"+arr.toJSONString()+"}",Constant.PAY_TYPE_DIFFERENTLY);
			map.put("duplication", duplication);
			result.setMessage("校验通过!");
		}
		result.setSuccess(true);
		return result;
	}
	
	*//**
	 * 下载收费模板
	 * @param request
	 * @param response
	 * @param session
	 * @throws Exception
	 *//*
	@RequestMapping(value = "/downloadExcelTemplate.htm")
	public void downloadExcelTemplate(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws Exception {
		String range = request.getParameter("range");
		String org_id = request.getParameter("org_id");
		String param = URLDecoder.decode(URLDecoder.decode(range, "utf-8"),"utf-8");
        try{
			String resultJsonStr = this.callPostUrlJson(request, "api/student/getStudentsByRange", param);
			Result<List<Clas2Student>> result = JsonUtil.getObjectFromJson(resultJsonStr, new TypeReference<Result<List<Clas2Student>>>() { });
			if (result != null && result.getCode() == 200) {
				List<Clas2Student> list = result.getData();
				if (list != null && list.size() > 0) {
					List<String[]> exampleValueList = new ArrayList<String[]>();
					for (Clas2Student s : list) {
						if(null!=s.getStud_id()&&StringUtils.isNotBlank(s.getStud_name())){
							String[] exampleValue = new String[5];
							exampleValue[0] = org_id+"l"+s.getGrade_id()+"l"+s.getClas_id()+"l"+s.getStud_id().toString();
							exampleValue[1] = s.getGrade_name();
							exampleValue[2] = s.getClas_name();
							exampleValue[3] = s.getStud_name();
							exampleValue[4] = "";
							exampleValueList.add(exampleValue);
						}
					}
					Workbook wb = ExcelUtil.makeWorkbook("收费列表", Constant.CHARGE_EXCEL_COLUMN, exampleValueList, "xls");
					ExcelUtil.makeAndOutExcelFile(request, response, wb, "学生收费模板.xls");
				} else {
					Workbook wb = ExcelUtil.makeWorkbook("收费列表", Constant.CHARGE_EXCEL_COLUMN, null, "xls");
					ExcelUtil.makeAndOutExcelFile(request, response, wb, "学生收费模板.xls");
				}
			}
        }catch(Exception e){
        	e.printStackTrace();
        }
	}

	*//**
	 * 下载错误信息模板
	 * @param request
	 * @param response
	 * @param session
	 * @throws Exception
	 *//*
	@RequestMapping(value="/downloadErrorExcel.htm")
	public void downloadErrorExcel(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		String chargeExcelJson = (String) session.getAttribute("chargeExcel");
		List<String[]> chargeExcel = JsonUtil.getObjectFromJson(chargeExcelJson, new TypeReference<List<String[]>>() {});
		if(null!=chargeExcel&&chargeExcel.size()!=0){
			Workbook wb = ExcelUtil.makeWorkbook("错误信息反馈列表", Constant.CHARGE_EXCEL_COLUMN_WITH_ERROR, chargeExcel, "xls");
			ExcelUtil.makeAndOutExcelFile(request, response, wb, "错误信息反馈模板.xls");
		} else {
			Workbook wb = ExcelUtil.makeWorkbook("错误信息反馈列表", Constant.CHARGE_EXCEL_COLUMN_WITH_ERROR, null, "xls");
			ExcelUtil.makeAndOutExcelFile(request, response, wb, "错误信息反馈模板.xls");
		}
	}
	
	*//**
	 * 提交保存收费信息
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws Exception
	 *//*
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/saveCharge.json")
	@ResponseBody
	public Result<Map<String, Object>> saveCharge(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
		request.setCharacterEncoding("utf-8");
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();	
		String tech_id="",tech_name="",org_id="",money="",total="",chargeExcelJson="";
		String accId="",audit_id="",audit_name="",content="",end_time="",start_time="",item="",pay_type="",refund="",txnAmtYUAN="",code="",ys_num="";
		Set<String> errorMsg = new HashSet<String>();
		Map<String,Object> studentsMap = new HashMap<String,Object>();
		try {			
			String user_id = request.getParameter("user_id").toString();
			Teacher teacher = this.getTechByUId(user_id);			
			tech_id = teacher.getTech_id().toString();
			tech_name = teacher.getTech_name();
			org_id = request.getParameter("org_id").toString();
		} catch (Exception e) {
			logger.error("获取登录信息不完整！");
			result.setMessage("获取登录信息不完整！");
			result.setSuccess(false);
			return result;
		}
		try {
			accId = request.getParameter("accId");
			audit_id = request.getParameter("audit_id");
			audit_name = request.getParameter("audit_name");
			content = request.getParameter("content");
			end_time = request.getParameter("end_time");
			start_time = request.getParameter("start_time");
			item = request.getParameter("item");
			pay_type = request.getParameter("pay_type");
			refund = request.getParameter("refund");
			txnAmtYUAN = request.getParameter("txnAmt");
			code = request.getParameter("code");
		} catch (Exception e) {
			errorMsg.add("缺少必填项！");
		}
		try {
			session.getAttribute("checker").toString();
		} catch (Exception e) {
			result.setMessage("不能重复提交！");
			result.setSuccess(false);
			return result;
		}
		if((Constant.PAY_TYPE_DIFFERENTLY.toString()).equals(pay_type)){
			try {
				money = session.getAttribute("money").toString();
				total = session.getAttribute("total").toString();
				ys_num = session.getAttribute("ys_num").toString();
				chargeExcelJson = (String) session.getAttribute("chargeExcel");
				studentsMap = (Map<String, Object>) session.getAttribute("studentsMap");
			} catch (Exception e) {
				logger.error("Excel信息不完整！");
				errorMsg.add("Excel信息不完整！");
			}
		}
		String phone = "";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("org_id", org_id);
		try {
			String resultManagerStr = this.callUrl(request, "api/tech/getTechsOfRlId", paramMap);
			Result<Map<String,List<Teacher>>> managerResult = JsonUtil.getObjectFromJson(resultManagerStr, new TypeReference<Result<Map<String,List<Teacher>>>>() { });
			for (Teacher t : managerResult.getData().get("techs")) {
				if(t.getTech_id().toString().equals(audit_id)){
					phone = t.getUser_mobile();
					break;
				}
			}
			String validResult = HttpClientUtil.post(ESB_REQUEST_URL+"validCode", "phone="+phone+"&code="+code, "application/x-www-form-urlencoded");
			Result<String> vResult = JsonUtil.getObjectFromJson(validResult, new TypeReference<Result<String>>() {});
			if(200!=vResult.getCode()){
				result.setMessage("验证码错误；");
				result.setSuccess(false);
				return result;
			}else{
				session.removeAttribute("money");
				session.removeAttribute("ys_num");
				session.removeAttribute("total");
				session.removeAttribute("chargeExcel");
				session.removeAttribute("studentsMap");
				session.removeAttribute("checker");
			}
		} catch (Exception e2) {
			e2.printStackTrace();
			result.setMessage("验证码校验失败");
			result.setSuccess(false);
			return result;
		}
		Charge charge = new Charge();
		if(StringUtils.isNotBlank(accId)&&NumberUtils.isParsable(accId)){
			charge.setAccId(Integer.parseInt(accId));
		}else{
			errorMsg.add("账户信息错误；");
		}
		if(StringUtils.isNotBlank(audit_id)&&NumberUtils.isParsable(audit_id)){
			charge.setAudit_id(Integer.parseInt(audit_id));
		}else{
			errorMsg.add("审核人主键错误；");
		}
		if(StringUtils.isBlank(audit_name)){
			errorMsg.add("审核人姓名错误；");
		}else{
			charge.setAudit_name(audit_name);
		}
		charge.setContent(content);
		try {
			charge.setStart_time(Constant.sdf.parse(start_time));
		} catch (ParseException e1) {
			errorMsg.add("开始时间格式错误；");
			e1.printStackTrace();
		}
		try {
			charge.setEnd_time(Constant.sdf.parse(end_time));
		} catch (ParseException e) {
			errorMsg.add("截止时间格式错误；");
			e.printStackTrace();
		}
		if(charge.getStart_time().after(charge.getEnd_time())){
			errorMsg.add("开始时间不能晚于结束时间；");
		}
		charge.setInsert_time(new Date());
		charge.setIs_del(Constant.IS_DEL_NO);
		if(StringUtils.isBlank(item)||item.length()>10){
			errorMsg.add("收费项目不能为空，也不能过长；");
		}else{
			charge.setItem(item);
		}
		//charge.setNote(note);
		try {
			charge.setOrder_prefix("6"+String.format("%05d", Integer.parseInt(org_id))+(new Date()).getTime());
		} catch (Exception e) {
			errorMsg.add("订单前缀错误；");
		}
		if(StringUtils.isNotBlank(org_id)&&NumberUtils.isParsable(org_id)){
			charge.setOrg_id(Integer.parseInt(org_id));
		}else{
			errorMsg.add("机构主键错误；");
		}
		if(StringUtils.isNotBlank(pay_type)&&NumberUtils.isParsable(pay_type)){
			charge.setPay_type(Integer.parseInt(pay_type));
		}else{
			errorMsg.add("支付方式错误；");
		}
		if(StringUtils.isNotBlank(refund)&&NumberUtils.isParsable(refund)){
			charge.setRefund(Integer.parseInt(refund));
		}else{
			errorMsg.add("退款方式错误；");
		}
		charge.setStatus(Constant.CHARGE_STATUS_PUSH_WAITING);
		if(StringUtils.isNotBlank(tech_id)&&NumberUtils.isParsable(tech_id)){
			charge.setTech_id(Integer.parseInt(tech_id));
		}else{
			errorMsg.add("发布人主键错误；");
		}
		if(StringUtils.isBlank(tech_name)){
			errorMsg.add("发布人姓名错误；");
		}else{
			charge.setTech_name(tech_name);
		}
		charge.setPay_file(CHARGE_DETAIL_EXCEL+charge.getOrder_prefix()+UUID.randomUUID().toString().substring(0, 4)+".csv");
		File file = new File(charge.getPay_file());  
		Writer writer = null;
		try {
			writer = new FileWriter(file);
		} catch (IOException e) {
			errorMsg.add("学生收费详细信息保存时发生异常；");
			e.printStackTrace();
		}  
		CSVWriter csvWriter = new CSVWriter(writer, ',');  
		//个性化收费
		if(Constant.PAY_TYPE_DIFFERENTLY.toString().equals(pay_type)){
			List<String[]> chargeExcel = JsonUtil.getObjectFromJson(chargeExcelJson, new TypeReference<List<String[]>>() {});
			if(null==chargeExcel||chargeExcel.size()==0){
				errorMsg.add("临时文件读取异常；");
			}
			try {
				charge.setNopay_num(Integer.parseInt(ys_num));
				charge.setYs_num(charge.getNopay_num());
			} catch (Exception e) {
				errorMsg.add("人数统计异常；");
			}
			if(StringUtils.isBlank(money)){
				errorMsg.add("上传文件记录金额时发生异常；");
			}else{
				charge.setMoney(money);
			}
			if(StringUtils.isBlank(total)){
				errorMsg.add("上传文件统计金额时发生异常；");
			}else{
				charge.setYs_money(total);
			}
			if(errorMsg.isEmpty()){
				JSONArray arr = new JSONArray();
				for (String[] row : chargeExcel) {
					String[] strs = {"" , "", "", "", "", "", ""};
					strs[0] = row[0].split("l")[3];
					strs[1] = row[0].split("l")[1];
					strs[2] = row[1];
					strs[3] = row[0].split("l")[2];
					strs[4] = row[2];
					strs[5] = row[3];
					strs[6] = row[4];
					
					int idx = row[0].lastIndexOf("l");
					String str = row[0].substring(idx + 1, row[0].length());
					if(!org_id.equals(row[0].split("l")[0])){
						errorMsg.add("学校错误，请检查模板是否正确！");
					}
					if(StringUtils.isBlank(row[0])){
						errorMsg.add("身份唯一编码不能为空！");
					}else if(!studentsMap.containsKey(row[0])){
						errorMsg.add("输入的身份唯一编码没有在数据库中找到匹配值！");
					}
					if(StringUtils.isBlank(row[3])){
						errorMsg.add("学生姓名不能为空！");
					}else if(!studentsMap.get(row[0]).equals(row[3])){
						errorMsg.add("学生姓名与身份唯一编码不匹配！");
					}
					if (!NumberUtils.isParsable(row[4])) {
						errorMsg.add("输入的缴费金额不是数字！");
					}else if(row[4].contains(".")&&row[4].split("\\.")[1].length()>2){
						errorMsg.add("输入的缴费金额只能精确到分！");
					}else if(new BigDecimal(MoneyUtil.fromYUANtoFEN(row[4])).compareTo(new BigDecimal(Constant.MAX_MONEY))>0){
						errorMsg.add("输入的缴费金额过大！");
					}else if(new BigDecimal(MoneyUtil.fromYUANtoFEN(row[4])).compareTo(new BigDecimal(Constant.MIN_MONEY))<0){
						errorMsg.add("输入的缴费金额错误！");
					} else {
						arr.add(str);
						csvWriter.writeNext(strs);  
					}
				}
				JSONObject range = new JSONObject();
				range.put("s", arr);
				charge.setSf_range(range.toJSONString());
				charge.setIds(arr.toString());
			}
		//统一收费
		}else if(Constant.PAY_TYPE_UNIFIED.toString().equals(pay_type)){
			String range = request.getParameter("range");
			String txnAmt = "";
			if(StringUtils.isBlank(range)){
				errorMsg.add("收费范围异常；");
			}else{
				charge.setSf_range(range);
			}
			String token = request.getParameter("token");
			String udid = request.getParameter("udid");
			String resultJsonStr = "";
			List<Clas2Student> list = new ArrayList<Clas2Student>();
			try {
				resultJsonStr = HttpClientUtil.post(ESB_API_REQUEST_URL+"student/getStudentsByRange?token="+token+"&udid="+udid, range, "application/json");
				Result<List<Clas2Student>> sresult = new Result<List<Clas2Student>>();
				sresult = JsonUtil.getObjectFromJson(resultJsonStr, new TypeReference<Result<List<Clas2Student>>>() {});
				list = sresult.getData();
				charge.setNopay_num(list.size());
			} catch (ConnectTimeoutException e) {
				errorMsg.add("请求基础服务器时发生异常；");
				e.printStackTrace();
			} catch (SocketTimeoutException e) {
				errorMsg.add("请求基础服务器时发生异常；");
				e.printStackTrace();
			} catch (Exception e) {
				errorMsg.add("请求基础服务器时发生异常；");
				e.printStackTrace();
			}
			if(!NumberUtils.isParsable(txnAmtYUAN)){
				errorMsg.add("金额格式异常；");
			}else if(txnAmtYUAN.contains(".")&&txnAmtYUAN.split("\\.")[1].length()>2){
				errorMsg.add("输入的金额只能精确到分！");
			}else{
				charge.setMoney(txnAmtYUAN);
				try {
					txnAmt = MoneyUtil.fromYUANtoFEN(txnAmtYUAN);
				} catch (Exception e) {
					errorMsg.add("金额转换时发生异常；");
					e.printStackTrace();
				}
				if(!NumberUtils.isParsable(txnAmt)){
					errorMsg.add("金额格式异常；");
				}else if(new BigDecimal(txnAmt).compareTo(new BigDecimal(Constant.MAX_MONEY))>0){
					errorMsg.add("输入的金额过大！");
				}else if(new BigDecimal(txnAmt).compareTo(new BigDecimal(Constant.MIN_MONEY))<0){
					errorMsg.add("输入的金额错误！");
				}else{
					charge.setTxnAmt(txnAmt);
				}
			}
			charge.setYs_num(list.size());
			try {
				charge.setYs_money(new BigDecimal(txnAmtYUAN).multiply(new BigDecimal(list.size())).toString());
			} catch (Exception e) {
				errorMsg.add("应收金额计算异常；");
			}
			if(errorMsg.isEmpty()){
				for (Clas2Student s : list) {
					String[] strs = {"" , "", "", "", "", "", ""};  
					strs[0]=s.getStud_id().toString();
					strs[1]=s.getGrade_id().toString();
					strs[2]=s.getGrade_name().toString();
					strs[3]=s.getClas_id().toString();
					strs[4]=s.getClas_name();
					strs[5]=s.getStud_name();
					strs[6]=txnAmtYUAN;
					csvWriter.writeNext(strs);  
				}
			}
		}
		if(errorMsg.isEmpty()){
			//进DB
			charge.setNo_num(0);
			charge.setSs_money("0");
			charge.setSs_num(0);
			charge.setTf_money_all("0");
			charge.setTf_money_part("0");
			charge.setTf_num_all(0);
			charge.setTf_num_part(0);
			chargeService.save(charge);
			//进MQ
			if(null!=charge.getCid()){
				Map<String, Object> messageMap = new HashMap<String, Object>();
				messageMap.put("cid", charge.getCid());
				rabbitTemplate.convertAndSend("charge_detail_delay_exchange", null, messageMap);
				logger.info("【创建支付mq】"+charge.getCid()+":"+charge.getItem()+";开始时间:"+Constant.ymd.format(charge.getStart_time())+"结束时间："+Constant.ymd.format(charge.getEnd_time()));
			}else{
				errorMsg.add("保存失败");
			}
		}
		if(errorMsg.isEmpty()){
			result.setMessage("保存成功");
			result.setSuccess(true);
		}else{
			String msg = errorMsg.toString();
			result.setMessage(msg.substring(1, msg.length()-1));
			result.setSuccess(false);
		}
		try {
			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return result;
	}
	
	*//**
	 * 开启/关闭退费
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 *//*
	@RequestMapping(value="/switchRefund.json")
	@ResponseBody
	public Result<Boolean> switchRefund(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		Result<Boolean> result= new Result<Boolean>();
		Boolean can_refund = false;
		result.setSuccess(true);
		result.setMessage("退费状态修改成功！");
		String cid = request.getParameter("cid");
		*//*************光大退费临时代码 START*************光大退费业务上线后可删除*************//*
		Account account = accountService.getByCId(Integer.parseInt(cid));
		if(Constant.ACCTYPE_CEB.intValue()==account.getAccType().intValue()){
			result.setSuccess(false);
			result.setMessage("光大银行暂未开通退款服务，默认关闭退费入口！");
			return result;
		}else if(Constant.NO.intValue()==account.getOrg_id().intValue()){
			result.setSuccess(false);
			result.setMessage("关联账户在本系统中已注销！");
			return result;
		}
		*//**************光大退费临时代码 END**************光大退费业务上线后可删除**************//*
		Charge charge = chargeService.getById(Integer.parseInt(cid));
		if(Constant.NO.equals(charge.getRefund())){
			charge.setModify_time(new Date());
			charge.setRefund(Constant.YES);
			chargeService.update(charge);
			can_refund = true;
			result.setData(can_refund);
		}else if(Constant.YES.equals(charge.getRefund())){
			charge.setModify_time(new Date());
			charge.setRefund(Constant.NO);
			chargeService.update(charge);
			can_refund = false;
			result.setData(can_refund);
		}else{
			result.setSuccess(false);
			result.setMessage("退费状态查询异常！");
		}
		return result;
	}
	
	*//**
	 * 获取支付信息进入编辑页面
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception 
	 *//*
	@RequestMapping(value="/goEdit.json")
	@ResponseBody
	public Result<Charge> goEdit(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		String cid = request.getParameter("cid");
		Charge charge = chargeService.getById(Integer.parseInt(cid));
		charge.setSs_money(MoneyUtil.fromFENtoYUAN(charge.getSs_money()));
		//if(charge.getStart_time().after(new Date())&&Constant.CHARGE_STATUS_PUSH_WAITING.equals(charge.getStatus())){
			Account account = accountService.getById(charge.getAccId());
			if(Constant.ACCOUNT_TYPE_SJWY.equals(account.getAccType())){
				charge.setAccInfo("(代收)"+account.getBankName()+":"+account.getAccNo());
			}else{
				charge.setAccInfo("(自收)"+account.getBankName()+":"+account.getAccNo());
			}
		//}
		Result<Charge> result= new Result<Charge>();
		result.setSuccess(true);
		result.setMessage("获取成功！");
		result.setData(charge);
		return result;
	}

	*//**
	 * 编辑支付信息
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception 
	 *//*
	@RequestMapping(value="/edit.json")
	@ResponseBody
	public Result<String> edit(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		String cid = request.getParameter("cid");
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");
		Charge charge = chargeService.getById(Integer.parseInt(cid));
		Result<String> result= new Result<String>();
		if(Constant.CHARGE_STATUS_PUSH_WAITING.equals(charge.getStatus())){
			Date start = Constant.sdf.parse(start_time); 
			Date end = Constant.sdf.parse(end_time); 
			if(start.after(new Date())&&end.after(start)){
				charge.setStart_time(start);
				charge.setEnd_time(end);
				charge.setModify_time(new Date());
				chargeService.update(charge);
			}else{
				result.setSuccess(false);
				result.setMessage("更新失败！输入时间有误！");
				return result;
			}
		}else{
			result.setSuccess(false);
			result.setMessage("更新失败！正在收费中不可以更改时间，或输入时间有误！");
			return result;
		}
		
		result.setSuccess(true);
		result.setMessage("更新成功！");
		return result;
	}

	*//**
	 * 删除支付信息
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception 
	 *//*
	@RequestMapping(value="/close.json")
	@ResponseBody
	public Result<String> close(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		String cid = request.getParameter("cid");
		Charge charge = chargeService.getById(Integer.parseInt(cid));
		Result<String> result= new Result<String>();
		if(Constant.CHARGE_STATUS_PUSH_WAITING.equals(charge.getStatus())&&charge.getStart_time().after(new Date())){
			charge.setStatus(Constant.CHARGE_STATUS_TURNED_OFF);
			charge.setIs_del(Constant.IS_DEL_YES);
			charge.setDel_time(new Date());
			chargeService.update(charge);
			result.setSuccess(true);
			result.setMessage("删除成功！");
		}else{
			result.setSuccess(false);
			result.setMessage("删除失败！");
		}
		return result;
	}
	
	*//**
	 * 获取统计列表
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception 
	 *//*
	@RequestMapping(value="/getChargeDetailList.json")
	@ResponseBody
	public Result<Paging<ChargeDetail>> getChargeDetailList(HttpServletRequest request, 
			@RequestParam(name = "currentPageNum", defaultValue="1") Integer currentPageNum,
			@RequestParam(name = "pageSize", defaultValue=Constant.NUM_PER_PCPAGE_STRING) Integer pageSize,
			@RequestParam(name = "starttime", defaultValue="") String starttime,  
			@RequestParam(name = "endtime", defaultValue="") String endtime,  
			@RequestParam(name = "clas_id", defaultValue="") String clas_id,  
			@RequestParam(name = "status", defaultValue="") String status,  
			@RequestParam(name = "order_id", defaultValue="") String order_id,  
			@RequestParam(name = "pay", defaultValue="") String pay,  
			@RequestParam(name = "cid") String cid,  
			@RequestParam(name = "stud_name", defaultValue="") String stud_name,  
			HttpServletResponse response, HttpSession session) throws Exception {
		Integer org_id = Integer.parseInt(request.getParameter("org_id").toString());
		ChargeDetail cd = new ChargeDetail();
		cd.setOrg_id(org_id);
		cd.setCid(Integer.parseInt(cid));
		cd.setOrder_id(order_id);
		try {
			if(StringUtils.isNotBlank(starttime)){
				cd.setStart_time(Constant.sdf.parse(starttime+" 00:00:00"));
			}
			if(StringUtils.isNotBlank(endtime)){
				cd.setEnd_time(Constant.sdf.parse(endtime+" 23:59:59"));
			}
		} catch (Exception e) {
		}
		try {
			cd.setClas_id(Integer.parseInt(clas_id));
		} catch (Exception e) {
		}
		try {
			cd.setStatus(Integer.parseInt(status));
		} catch (Exception e) {
		}
		cd.setStud_name(stud_name);
		if(StringUtils.isNotBlank(pay)){
			if(Constant.TXNTYPE_UNIONPAY_PAY.equals(pay)){
				cd.setOnline_pay(Constant.CHARGE_DETAIL_STATUS_OK);
			}else if(Constant.TXNTYPE_SJWY_OFFLINE_PAY.equals(pay)){
				cd.setOffline_pay(Constant.CHARGE_DETAIL_STATUS_OK);
			}
		}
		Paging<ChargeDetail> page = new Paging<ChargeDetail>();
		page.setPage(currentPageNum);
		page.setLimit(pageSize);
		page = chargeDetailService.getList(page, cd);
		Result<Paging<ChargeDetail>> result = new Result<Paging<ChargeDetail>>();
		result.setSuccess(true);
		result.setData(page);
		return result;
	}


	*//**
	 * 申请代缴
	 * 教师线下收费后确认已代收代缴
	 * cd_id：家长分订单主键
	 *//*
	@RequestMapping(value = "/paid.json")
	@ResponseBody
	public Result<Map<String,Object>> paid(HttpServletRequest request, 
			@RequestParam(name = "cd_id") String cd_id,
			HttpServletResponse response, HttpSession session) throws Exception {
		Result<Map<String,Object>> result = new Result<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		String teacherId = "";
		String teacherName = "";
		String orgUserId = "";
		try {		
			orgUserId = request.getParameter("user_id").toString();
			Teacher teacher = this.getTechByUId(orgUserId);
			teacherId = teacher.getTech_id().toString();
			teacherName = teacher.getTech_name();

		} catch (Exception e) {
			logger.error("获取登录信息不完整！");
			result.setMessage("获取登录信息不完整！");
			result.setSuccess(false);
			return result;
		}
		//加乐观锁
		Date lockDate = new Date();
		try {
			ChargeDetail temp = new ChargeDetail();
			temp.setCd_id(Integer.parseInt(cd_id));
			Integer counter = chargeDetailService.lockChargeDetail(lockDate,temp);
			if(counter==1){
				ChargeDetail cd = chargeDetailService.getById(Integer.parseInt(cd_id));
				if(cd.getOffline_pay().equals(Constant.CHARGE_DETAIL_STATUS_NEVER)
						&&cd.getOnline_pay().equals(Constant.CHARGE_DETAIL_STATUS_NEVER)
						&&cd.getStatus().equals(Constant.CHARGE_DETAIL_STATUS_NEVER)){
					//开始支付
					cd.setOffline_pay(Constant.CHARGE_DETAIL_STATUS_OK);
					cd.setStatus(Constant.CHARGE_DETAIL_STATUS_OK);
					cd.setTxnAmt(MoneyUtil.fromYUANtoFEN(cd.getMoney()));
					cd.setTxnTime(new Date());
					Date payDate = new Date();
					cd.setModify_time(payDate);
					Integer pay = chargeDetailService.updateWithLock(cd,lockDate);
					//创建支付记录
					if(pay==1){
						//添加支付记录
						ChargeRecord cr = new ChargeRecord();
						cr.setTxnType(Constant.TXNTYPE_SJWY_OFFLINE_PAY);
						cr.setCid(cd.getCid());
						cr.setCd_id(cd.getCd_id());
						cr.setTxnAmt(cd.getTxnAmt());
						cr.setTxnTime(payDate);
						cr.setInsert_time(new Date());
						cr.setOrg_user_id(Integer.parseInt(orgUserId));
						cr.setUser_identify(Constant.IDENTITY_TEACHER);
						cr.setUser_type_id(Integer.parseInt(teacherId));
						cr.setUser_type_name(teacherName);
						chargeRecordService.save(cr);
						//更新支付总表
						ChargeRecord query = new ChargeRecord();
						query.setCid(cd.getCid());
						List<ChargeRecord> list = chargeRecordService.getList(query);
						Integer no_pay_num = chargeDetailService.countNoPay(cd.getCid());
						Charge charge = chargeService.getById(cd.getCid());
						charge.setNopay_num(no_pay_num);
						charge = chargeService.updateStatus(list,charge);
						try {
							map.put("money", MoneyUtil.fromFENtoYUAN(new BigDecimal(charge.getSs_money()).subtract(new BigDecimal(charge.getTf_money_all())).subtract(new BigDecimal(charge.getTf_money_part())).toString()));
						} catch (Exception e) {
							map.put("money", "核算中");
						}
						result.setData(map);
						result.setMessage("缴费成功！");
						result.setSuccess(true);
						logger.info("【线下支付成功】支付ChargeDetail主键:"+cd.getCd_id());
					}else{
						logger.error("【线下支付失败】校验账单乐观锁时失败。ChargeDetail主键:"+cd_id);
						result.setSuccess(false);
						result.setMessage("请求失败！原因：账单状态变动，请确认后重试！");
					}
				}else{
					result.setMessage("请求失败！原因：账单状态变动，请确认后重试！");
					result.setSuccess(false);
				}
			}else{
				logger.error("【线下支付错误】为支付订单设置乐观锁时失败。ChargeDetail主键:"+cd_id);
				result.setSuccess(false);
				result.setMessage("请求失败！原因：数据库异常，请稍候重试！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("【线下支付错误】支付订单时发生错误。ChargeDetail主键:"+cd_id);
			result.setSuccess(false);
			result.setMessage("请求失败！原因：数据库异常，请稍候重试！");
		}
		return result;
	}
	
	*//**
	 * 申请退费
	 * 教师发起为学生申请退款
	 * cd_id：收费详情表主键
	 * money：要退费的金额
	 *//*
	@RequestMapping(value = "/refund.json")
	@ResponseBody
	public Result<String> refund(HttpServletRequest request,
			@RequestParam(name = "cd_id") String cd_id, 
			@RequestParam(name = "money") String money, 
			@RequestParam(name = "type") String type, 
			HttpServletResponse response, HttpSession session) throws Exception {
		Result<String> result = new Result<String>();
		String teacherId = "";
		String teacherName = "";
		String orgUserId = "";
		
		try {		
			orgUserId = request.getParameter("user_id").toString();
			Teacher teacher = this.getTechByUId(orgUserId);
			teacherId = teacher.getTech_id().toString();
			teacherName = teacher.getTech_name();

		} catch (Exception e) {
			logger.error("获取登录信息不完整！");
			result.setMessage("获取登录信息不完整！");
			result.setSuccess(false);
			return result;
		}
		result.setSuccess(true);
		result.setMessage("申请成功！");
		//加乐观锁
		Date lockDate = new Date();
		String refundMoney = "";
		if(!NumberUtils.isParsable(money)){
			result.setMessage("请求失败！原因：输入金额格式异常！");
			result.setSuccess(false);
			return result;
		}else if(money.contains(".")&&money.split("\\.")[1].length()>2){
			result.setMessage("请求失败！原因：输入金额只能精确到分！");
			result.setSuccess(false);
			return result;
		}else if(new BigDecimal(MoneyUtil.fromYUANtoFEN(money)).compareTo(new BigDecimal(Constant.MAX_MONEY))>0){
			result.setMessage("请求失败！原因：输入金额过大！");
			result.setSuccess(false);
			return result;
		}else if(new BigDecimal(MoneyUtil.fromYUANtoFEN(money)).compareTo(new BigDecimal(Constant.MIN_MONEY))<0){
			result.setMessage("请求失败！原因：输入金额有误！");
			result.setSuccess(false);
			return result;
		}else{
			refundMoney = MoneyUtil.fromYUANtoFEN(money);
		}
		try {
			ChargeDetail temp = new ChargeDetail();
			temp.setCd_id(Integer.parseInt(cd_id));
			Integer counter = chargeDetailService.lockChargeDetail(lockDate,temp);
			if(counter==1){
				ChargeDetail cd = chargeDetailService.getByIdForRefund(temp.getCd_id());
				//验证退款条件
				if(MoneyUtil.isFirstLarger(refundMoney,cd.getTxnAmt())){
					result.setMessage("请求失败！原因：可退金额不足，请确认后重试！");
					result.setSuccess(false);
				}else if(cd.getRefund().equals(Constant.NO)){
					result.setMessage("请求失败！原因：该项收费已经关闭退费入口！");
					result.setSuccess(false);
				}else if(!Constant.CHARGE_DETAIL_STATUS_OK.equals(cd.getStatus())){
					result.setMessage("请求失败！原因：订单状态错误，请刷新后重试！");
					result.setSuccess(false);
				}else{
					ChargeRecord cr = new ChargeRecord();
					//开始退款
					if(type.equals(Constant.CHARGE_TYPE_UNIONPAY)&&cd.getOnline_pay().equals(Constant.CHARGE_DETAIL_STATUS_OK)&&cd.getStatus().equals(Constant.CHARGE_DETAIL_STATUS_OK)){
						cd.setOnline_pay(Constant.CHARGE_DETAIL_STATUS_REFUND_APPLY);
						cr.setTxnType(Constant.TXNTYPE_SJWY_ONLINE_REFUND_APPLY);
					}else if(type.equals(Constant.CHARGE_TYPE_CASH)&&cd.getOffline_pay().equals(Constant.CHARGE_DETAIL_STATUS_OK)&&cd.getStatus().equals(Constant.CHARGE_DETAIL_STATUS_OK)){
						cd.setOffline_pay(Constant.CHARGE_DETAIL_STATUS_REFUND_APPLY);
						cr.setTxnType(Constant.TXNTYPE_SJWY_OFFLINE_REFUND_APPLY);
					}
					cd.setStatus(Constant.CHARGE_DETAIL_STATUS_REFUND_APPLY);
					Date refundDate = new Date();
					cd.setModify_time(refundDate);
					Integer refund = chargeDetailService.updateWithLock(cd,lockDate);
					//创建退款记录
					if(refund==1){
						cr.setCid(cd.getCid());
						cr.setCd_id(cd.getCd_id());
						cr.setTxnAmt(refundMoney);
						cr.setTxnTime(refundDate);
						cr.setInsert_time(new Date());
						cr.setOrg_user_id(Integer.parseInt(orgUserId));
						cr.setUser_identify(Constant.IDENTITY_TEACHER);
						cr.setUser_type_id(Integer.parseInt(teacherId));
						cr.setUser_type_name(teacherName);
						chargeRecordService.save(cr);
						logger.info("【申请退款】教师tech_id"+teacherId+"于"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime())+"发起了对订单cd_id"+cd_id+"的退费申请");
					}else{
						logger.error("【退款失败】校验账单乐观锁时失败。ChargeDetail主键:"+cd_id);
						result.setMessage("请求失败！原因：账单状态变动，请确认后重试！");
						result.setSuccess(false);
					}
				}
			}else{
				logger.error("【退款错误】为退款订单设置乐观锁时失败。ChargeDetail主键:"+cd_id);
				result.setMessage("请求失败！原因：数据库异常，请稍候重试！");
				result.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("【退款错误】退款时发生异常。ChargeDetail主键:"+cd_id);
			result.setMessage("请求失败！原因：数据库异常，请稍候重试！");
			result.setSuccess(false);
		}
		return result;
	}
	
	*//**
	 * 班级筛选
	 * 统计列表页查询条件中的班级下拉列表数据
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception 
	 *//*
	@RequestMapping(value="/getClasses.json")
	@ResponseBody
	public Result<List<Classes>> getClasses(HttpServletRequest request, 
			@RequestParam(name = "cid") Integer cid,
			HttpServletResponse response, HttpSession session) throws Exception {
		Result<List<Classes>> result = new Result<List<Classes>>();
		List<Classes> classes = chargeDetailService.getClasses(cid);
		result.setData(classes);
		result.setSuccess(true);
		result.setMessage("成功！");
		return result;
	}

	*//**
	 * 未缴费提醒
	 * 教师发起提醒所有未缴费的学生
	 * @param cid：主订单主键
	 *//*
	@RequestMapping(value = "/remind.json")
	@ResponseBody
	public Result<String> remind(HttpServletRequest request, 
			@RequestParam(name = "cid") String cid,
			HttpServletResponse response, HttpSession session) throws Exception {
		Result<String> result = new Result<String>();
		String teacher_id = "";
		
		try {		
			String orgUserId = request.getParameter("user_id").toString();
			Teacher teacher = this.getTechByUId(orgUserId);
			teacher_id = teacher.getTech_id().toString();
		} catch (Exception e) {
			logger.error("获取登录信息不完整！");
			result.setMessage("获取登录信息不完整！");
			result.setSuccess(false);
			return result;
		}
		String org_id = request.getParameter("org_id").toString();
		Map<String, Object> messageMap = new HashMap<String, Object>();
		messageMap.put("cid", cid);
		messageMap.put("org_id", org_id);
		try {
			rabbitTemplate.convertAndSend("charge_remind_exchange", null, messageMap);
			result.setSuccess(true);
			result.setMessage("已通过微信和短信发送提醒消息");
			logger.info("【缴费提醒】教师tech_id"+teacher_id+"于"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime())+"发起了对订单cid"+cid+"的未交费提醒");
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("提醒失败");
		}
		return result;
	}
	
	*//**
	 * 下载退费模板
	 * @param request
	 * @param response
	 * @param session
	 * @throws Exception
	 *//*
	@RequestMapping(value = "/downloadRefundExcelTemplate.htm")
	public void downloadRefundExcelTemplate(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws Exception {
		String cid = request.getParameter("cid");
		String org_id = request.getParameter("org_id");
		List<ChargeDetail> list = chargeDetailService.getListForRefund(Integer.parseInt(cid));
		if (null!=list && list.size() > 0) {
			List<String[]> exampleValueList = new ArrayList<String[]>();
			for (ChargeDetail cd : list) {
				String[] exampleValue = new String[8];
				exampleValue[0] = org_id+"l"+cd.getGrade_id()+"l"+cd.getClas_id()+"l"+cd.getStud_id().toString();
				exampleValue[1] = cd.getGrade_name();
				exampleValue[2] = cd.getClas_name();
				exampleValue[3] = cd.getStud_name();
				exampleValue[4] = MoneyUtil.fromFENtoYUAN(cd.getTxnAmt());
				exampleValue[5] = cd.getOrder_id();
				exampleValue[6] = "";
				if(Constant.CHARGE_DETAIL_STATUS_OK.equals(cd.getOnline_pay())&&Constant.CHARGE_DETAIL_STATUS_OK.equals(cd.getOffline_pay())){
					exampleValue[7] = "银联退费(默认，您也可以选择现金退费)";
					exampleValueList.add(exampleValue);
				}else if(Constant.CHARGE_DETAIL_STATUS_OK.equals(cd.getOnline_pay())){
					exampleValue[7] = "银联退费";
					exampleValueList.add(exampleValue);
				}else if(Constant.CHARGE_DETAIL_STATUS_OK.equals(cd.getOffline_pay())){
					exampleValue[7] = "现金退费";
					exampleValueList.add(exampleValue);
				}
			}
			Workbook wb = ExcelUtil.makeWorkbook("退费列表", Constant.REFUND_EXCEL_COLUMN, exampleValueList, "xls");
			ExcelUtil.makeAndOutExcelFile(request, response, wb, "学生退费模板.xls");
		} else {
			Workbook wb = ExcelUtil.makeWorkbook("退费列表", Constant.REFUND_EXCEL_COLUMN, null, "xls");
			ExcelUtil.makeAndOutExcelFile(request, response, wb, "学生退费模板.xls");
		}
	}

	*//**
	 * 上传退费明细Excel
	 * @throws Exception 
	 *//*
	@RequestMapping(value="/uploadRefundExcel.json")
	@ResponseBody
	public Result<List<RefundInfo>> uploadRefundExcel(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Result<List<RefundInfo>> result = new Result<List<RefundInfo>>();
		String cid = request.getParameter("cid");
		String org_id = request.getParameter("org_id");
		Charge charge = chargeService.getById(Integer.parseInt(cid));
		if(Constant.NO.equals(charge.getRefund())){
			result.setMessage("失败！该项收费已经关闭退费入口！");
			result.setSuccess(false);
			return result;
		}
		Workbook wb = ExcelUtil.getExcelFromRequest(request);
		Map<String,String> className = new HashMap<String,String>();
		Map<String,Integer> errorNum = new HashMap<String,Integer>();
		Map<String,Integer> successNum = new HashMap<String,Integer>();
		Map<String,BigDecimal> successMoney = new HashMap<String,BigDecimal>();
		Map<String,Integer> notFoundNum = new HashMap<String,Integer>();
		
		List<String[]> notFoundExcel = new ArrayList<String[]>();
		List<String[]> fkFoundExcel = new ArrayList<String[]>();
		List<String[]> refundExcel = ExcelUtil.getExcel(wb,Constant.REFUND_EXCEL_COLUMN_WITH_ERROR);
		if (null!=refundExcel && refundExcel.size() > 0) {
			List<ChargeDetail> refundList = chargeDetailService.getListForRefund(Integer.parseInt(cid));
			Map<String,ChargeDetail> map = new HashMap<String,ChargeDetail>();
			for (ChargeDetail cd : refundList) {
				map.put(cd.getOrder_id(), cd);
			}
			// 遍历集合进行校验
			for (String[] excel : refundExcel) {
				String[] row = new String[excel.length+1];
				System.arraycopy(excel, 0, row, 0, excel.length);
				if(map.containsKey(excel[5])){
					row[8] = "";
					ChargeDetail cd = map.get(row[5]);
					if(!org_id.equals(row[0].split("l")[0])){
						row[8]+="学校不匹配！";
					}
					if(StringUtils.isBlank(row[0])){
						row[8]+="身份唯一编码不能为空！";
					}
					if(StringUtils.isBlank(row[3])){
						row[8]+="学生姓名不能为空！";
					}
					if(!(cd.getStud_id()+"").equals(row[0].split("l")[3])){
						row[8]+="身份唯一编码匹配错误！";
					}
					if(!cd.getStud_name().equals(row[3])){
						row[8]+="学生姓名匹配错误！";
					}
					if(StringUtils.isBlank(row[6])){
						row[8]+="退费金额不能为空！";
					}
					if (!NumberUtils.isParsable(row[6])) {
						row[8] += "退费金额不是数字！";
					}else if(row[6].contains(".")&&row[6].split("\\.")[1].length()>2){
						row[8] += "退费金额只能精确到分！";
					}else if(new BigDecimal(row[6]).compareTo(new BigDecimal(MoneyUtil.fromFENtoYUAN(cd.getTxnAmt())))>0){
						row[8] += "退费金额不能超过支付金额！";
					}else if(new BigDecimal(row[6]).compareTo(new BigDecimal(MoneyUtil.fromFENtoYUAN(Constant.MIN_MONEY)))<0){
						row[8] += "退费金额不能小于一分！";
					}	
					if(Constant.CHARGE_DETAIL_STATUS_OK.equals(cd.getOffline_pay())&&Constant.CHARGE_DETAIL_STATUS_OK.equals(cd.getOnline_pay())){
						if((row[7].indexOf("现金")!=-1||row[7].indexOf("线下")!=-1)&&row[7].indexOf("银联")==-1&&row[7].indexOf("线上")==-1){
							row[7]="现金退费";
						}else{
							row[7]="银联退费";
						}
					}else if(Constant.CHARGE_DETAIL_STATUS_OK.equals(cd.getOffline_pay())){
							row[7]="现金退费";
					}else if(Constant.CHARGE_DETAIL_STATUS_OK.equals(cd.getOnline_pay())){
						row[7]="银联退费";
					}else{
						row[8]="缴费状态异常！";
					}
					if(StringUtils.isBlank(row[8])){
						if(!className.containsKey(cd.getClas_id().toString())){
							className.put(cd.getClas_id().toString(), cd.getGrade_name()+" "+cd.getClas_name());
						}
						if(successNum.containsKey(cd.getClas_id().toString())){
							successNum.put(cd.getClas_id().toString(), successNum.get(cd.getClas_id().toString())+1);
						}else{
							successNum.put(cd.getClas_id().toString(), 1);
						}
						if(successMoney.containsKey(cd.getClas_id().toString())){
							successMoney.put(cd.getClas_id().toString(), successMoney.get(cd.getClas_id().toString()).add(new BigDecimal(row[6])));
						}else{
							successMoney.put(cd.getClas_id().toString(), new BigDecimal(row[6]));
						}
					}else{
						if(!className.containsKey(cd.getClas_id().toString())){
							className.put(cd.getClas_id().toString(), cd.getGrade_name()+" "+cd.getClas_name());
						}
						if(errorNum.containsKey(cd.getClas_id().toString())){
							errorNum.put(cd.getClas_id().toString(), errorNum.get(cd.getClas_id().toString())+1);
						}else{
							errorNum.put(cd.getClas_id().toString(), 1);
						}
					}
					fkFoundExcel.add(row);
				}else{
					String classId = excel[0].split("l")[2];
					if(!className.containsKey(classId)){
						className.put(classId, excel[1]+" "+excel[2]);
					}
					if(notFoundNum.containsKey(classId)){
						notFoundNum.put(classId, notFoundNum.get(classId)+1);
					}else{
						notFoundNum.put(classId, 1);
					}
					row[8]="在可退款列表中没有查询到本条记录，请确认订单编号及支付状态！";
					notFoundExcel.add(row);
				}
			}
		}else{
			result.setSuccess(false);
			result.setMessage("模板不正确！");
			return result;
		}

		session.setAttribute("notFoundExcelJson", JsonUtil.toJson(notFoundExcel, Include.ALWAYS));
		session.setAttribute("refundExcelJson", JsonUtil.toJson(fkFoundExcel, Include.ALWAYS));
		
		if(errorNum.isEmpty()){
			List<RefundInfo> list = new ArrayList<RefundInfo>();
			for (String classId : className.keySet()) {
				RefundInfo ri = new RefundInfo();
				ri.setClassName(className.get(classId));
				ri.setMoney(successMoney.get(classId).toString());
				ri.setNum(successNum.get(classId).toString());
				list.add(ri);
			}
			Integer notFound = 0;
			for (Integer in : notFoundNum.values()) {
				notFound += in;
			}
			if(notFound.equals(0)||list.size()==0){
				result.setCode(200);
				result.setMessage("校验成功！");
			}else{
				result.setCode(299);
				result.setMessage("校验成功！"+notFound+"名学生没有查询到可退费的订单，请重新核对订单及支付状态！");
			}
			result.setSuccess(true);
			result.setData(list);
		}else{
			result.setSuccess(false);
			result.setMessage("校验失败");
		}
		return result;
	}
	
	*//**
	 * 下载退费错误信息表
	 *//*
	@RequestMapping(value = "/downloadRefundErrorExcel.htm")
	public void downloadRefundErrorExcel(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		String refundExcelJson = (String) session.getAttribute("refundExcelJson");
		List<String[]> refundExcel = JsonUtil.getObjectFromJson(refundExcelJson, new TypeReference<List<String[]>>() {});
		if(null!=refundExcel&&refundExcel.size()!=0){
			Workbook wb = ExcelUtil.makeWorkbook("退费错误信息反馈列表", Constant.REFUND_EXCEL_COLUMN_WITH_ERROR, refundExcel, "xls");
			ExcelUtil.makeAndOutExcelFile(request, response, wb, "退费错误信息反馈模板.xls");
		} else {
			Workbook wb = ExcelUtil.makeWorkbook("退费错误信息反馈列表", Constant.REFUND_EXCEL_COLUMN_WITH_ERROR, null, "xls");
			ExcelUtil.makeAndOutExcelFile(request, response, wb, "退费错误信息反馈模板.xls");
		}
	}
	
	*//**
	 * 查看未能申请退款的学生信息表
	 *//*
	@RequestMapping(value = "/downloadRefundNotFoundExcel.htm")
	public void downloadRefundNotFoundExcel(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		String notFoundExcelJson = (String) session.getAttribute("notFoundExcelJson");
		List<String[]> notFoundExcel = JsonUtil.getObjectFromJson(notFoundExcelJson, new TypeReference<List<String[]>>() {});
		if(null!=notFoundExcel&&notFoundExcel.size()!=0){
			Workbook wb = ExcelUtil.makeWorkbook("退费错误信息反馈列表", Constant.REFUND_EXCEL_COLUMN_WITH_ERROR, notFoundExcel, "xls");
			ExcelUtil.makeAndOutExcelFile(request, response, wb, "退费错误信息反馈模板.xls");
		} else {
			Workbook wb = ExcelUtil.makeWorkbook("退费错误信息反馈列表", Constant.REFUND_EXCEL_COLUMN_WITH_ERROR, null, "xls");
			ExcelUtil.makeAndOutExcelFile(request, response, wb, "退费错误信息反馈模板.xls");
		}
	}

	*//**
	 * 提交退款申请
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 *//*
	@RequestMapping(value = "/submitRefund.json")
	@ResponseBody
	public Result<String> submitRefund(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Result<String> result = new Result<String>();
		String teacherId = "";
		String teacherName = "";
		String orgUserId = "";
		try {			
			orgUserId = request.getParameter("user_id").toString();
			Teacher teacher = this.getTechByUId(orgUserId);			
			teacherId = teacher.getTech_id().toString();
			teacherName = teacher.getTech_name();
		} catch (Exception e) {
			logger.error("获取登录信息不完整！");
			result.setMessage("获取登录信息不完整！");
			result.setSuccess(false);
			return result;
		}
		String cid = request.getParameter("cid");
		//String org_id = request.getParameter("org_id");
		Charge charge = chargeService.getById(Integer.parseInt(cid));
		if(Constant.NO.equals(charge.getRefund())){
			result.setMessage("失败！该项收费已经关闭退费入口！");
			result.setSuccess(false);
			return result;
		}
		Map<String,String> className = new HashMap<String,String>();
		Map<String,Integer> errorNum = new HashMap<String,Integer>();
		Map<String,Integer> successNum = new HashMap<String,Integer>();
		Map<String,BigDecimal> successMoney = new HashMap<String,BigDecimal>();
		Map<String,Integer> notFoundNum = new HashMap<String,Integer>();
		
		List<String[]> notFoundExcel = new ArrayList<String[]>();
		String refundExcelJson = (String) session.getAttribute("refundExcelJson");
		List<String[]> refundExcel = JsonUtil.getObjectFromJson(refundExcelJson, new TypeReference<List<String[]>>() {});
		if (null!=refundExcel && refundExcel.size() > 0) {
			List<ChargeDetail> refundList = chargeDetailService.getListForRefund(Integer.parseInt(cid));
			Map<String,ChargeDetail> map = new HashMap<String,ChargeDetail>();
			for (ChargeDetail cd : refundList) {
				map.put(cd.getOrder_id(), cd);
			}
			// 遍历集合进行校验
			for (String[] excel : refundExcel) {
				String[] row = new String[excel.length+1];
				System.arraycopy(excel, 0, row, 0, excel.length);
				if(map.containsKey(row[5])){
					row[8] = "";
					ChargeDetail cd = map.get(row[5]);
					if(StringUtils.isBlank(row[0])){
						row[8]+="身份唯一编码不能为空！";
					}
					if(StringUtils.isBlank(row[3])){
						row[8]+="学生姓名不能为空！";
					}
					if(!(cd.getStud_id()+"").equals(row[0].split("l")[3])){
						row[8]+="身份唯一编码匹配错误！";
					}
					if(!cd.getStud_name().equals(row[3])){
						row[8]+="学生姓名匹配错误！";
					}
					if(StringUtils.isBlank(row[6])){
						row[8]+="退费金额不能为空！";
					}
					if (!NumberUtils.isParsable(row[6])) {
						row[8] += "退费金额不是数字！";
					}else if(row[6].contains(".")&&row[6].split("\\.")[1].length()>2){
						row[8] += "退费金额只能精确到分！";
					}else if(new BigDecimal(row[6]).compareTo(new BigDecimal(MoneyUtil.fromFENtoYUAN(cd.getTxnAmt())))>0){
						row[5] += "退费金额不能超过支付金额！";
					}else if(new BigDecimal(row[6]).compareTo(new BigDecimal(MoneyUtil.fromFENtoYUAN(Constant.MIN_MONEY)))<0){
						row[8] += "退费金额不能小于一分！";
					}	
					if(Constant.CHARGE_DETAIL_STATUS_OK.equals(cd.getOffline_pay())&&Constant.CHARGE_DETAIL_STATUS_OK.equals(cd.getOnline_pay())){
						if((row[7].indexOf("现金")!=-1||row[7].indexOf("线下")!=-1)&&row[7].indexOf("银联")==-1&&row[7].indexOf("线上")==-1){
							row[7]="现金退费";
						}else{
							row[7]="银联退费";
						}
					}else if(Constant.CHARGE_DETAIL_STATUS_OK.equals(cd.getOffline_pay())){
							row[7]="现金退费";
					}else if(Constant.CHARGE_DETAIL_STATUS_OK.equals(cd.getOnline_pay())){
						row[7]="银联退费";
					}else{
						row[8]="缴费状态异常！";
					}
					if(StringUtils.isBlank(row[8])){
						if(!className.containsKey(cd.getClas_id().toString())){
							className.put(cd.getClas_id().toString(), cd.getGrade_name()+" "+cd.getClas_name());
						}
						if(successNum.containsKey(cd.getClas_id().toString())){
							successNum.put(cd.getClas_id().toString(), successNum.get(cd.getClas_id().toString())+1);
						}else{
							successNum.put(cd.getClas_id().toString(), 1);
						}
						if(successMoney.containsKey(cd.getClas_id().toString())){
							successMoney.put(cd.getClas_id().toString(), successMoney.get(cd.getClas_id().toString()).add(new BigDecimal(row[6])));
						}else{
							successMoney.put(cd.getClas_id().toString(), new BigDecimal(row[6]));
						}
					}else{
						if(!className.containsKey(cd.getClas_id().toString())){
							className.put(cd.getClas_id().toString(), cd.getGrade_name()+" "+cd.getClas_name());
						}
						if(errorNum.containsKey(cd.getClas_id().toString())){
							errorNum.put(cd.getClas_id().toString(), errorNum.get(cd.getClas_id().toString())+1);
						}else{
							errorNum.put(cd.getClas_id().toString(), 1);
						}
					}
				}else{
					String classId = row[0].split("l")[2];
					if(!className.containsKey(classId)){
						className.put(classId, row[1]+" "+row[2]);
					}
					if(notFoundNum.containsKey(classId)){
						notFoundNum.put(classId, notFoundNum.get(classId)+1);
					}else{
						notFoundNum.put(classId, 1);
					}
					row[8]="在可退款列表中没有查询到本条记录或在之前的提交中已经申请成功，请确认订单编号及支付状态！";
					notFoundExcel.add(row);
				}
			}
		}
		
		if(errorNum.isEmpty()){
			Integer ok = 0;
			Integer fail = 0;
			for (String[] row : refundExcel) {
				//加乐观锁
				Date lockDate = new Date();
				String refundMoney = MoneyUtil.fromYUANtoFEN(row[6]);
				try {
					ChargeDetail temp = new ChargeDetail();
					temp.setOrder_id(row[5]);
					Integer counter = chargeDetailService.lockChargeDetail(lockDate,temp);
					if(counter==1){
						ChargeDetail cd = chargeDetailService.getByOrderId(row[5]);
						//验证退款条件
						if(MoneyUtil.isFirstLarger(refundMoney,cd.getTxnAmt())){
							logger.error("【错误,订单编号："+row[6]+"】批量提交退款申请失败！原因：可退金额不足！(不影响其他退款申请)");
							fail++;
						}else if(!Constant.CHARGE_DETAIL_STATUS_OK.equals(cd.getStatus())){
							logger.error("【错误,订单编号："+row[6]+"】批量提交退款申请失败！原因：该项收费状态不正确！(请刷新或重新下载模板后再试)");
							fail++;
						}else if(Constant.NO.equals(cd.getRefund())){
							logger.error("【错误,订单编号："+row[6]+"】批量提交退款申请失败！原因：该项收费已经关闭退费入口！(其他退款申请可能遇到同样问题)");
							fail++;
						}else{
							ChargeRecord cr = new ChargeRecord();
							//开始退款
							if(Constant.CHARGE_DETAIL_STATUS_OK.equals(cd.getOffline_pay())&&Constant.CHARGE_DETAIL_STATUS_OK.equals(cd.getOnline_pay())){
								if((row[7].indexOf("现金")!=-1||row[7].indexOf("线下")!=-1)&&row[7].indexOf("银联")==-1&&row[7].indexOf("线上")==-1){
									cd.setOffline_pay(Constant.CHARGE_DETAIL_STATUS_REFUND_APPLY);
									cr.setTxnType(Constant.TXNTYPE_SJWY_OFFLINE_REFUND_APPLY);
								}else{
									cd.setOnline_pay(Constant.CHARGE_DETAIL_STATUS_REFUND_APPLY);
									cr.setTxnType(Constant.TXNTYPE_SJWY_ONLINE_REFUND_APPLY);
								}
								cd.setStatus(Constant.CHARGE_DETAIL_STATUS_REFUND_APPLY);
							}else if(Constant.CHARGE_DETAIL_STATUS_OK.equals(cd.getOnline_pay())){
								cd.setOnline_pay(Constant.CHARGE_DETAIL_STATUS_REFUND_APPLY);
								cd.setStatus(Constant.CHARGE_DETAIL_STATUS_REFUND_APPLY);
								cr.setTxnType(Constant.TXNTYPE_SJWY_ONLINE_REFUND_APPLY);
							}else if(Constant.CHARGE_DETAIL_STATUS_OK.equals(cd.getOffline_pay())){
								cd.setOffline_pay(Constant.CHARGE_DETAIL_STATUS_REFUND_APPLY);
								cd.setStatus(Constant.CHARGE_DETAIL_STATUS_REFUND_APPLY);
								cr.setTxnType(Constant.TXNTYPE_SJWY_OFFLINE_REFUND_APPLY);
							}
							Date refundDate = new Date();
							cd.setModify_time(refundDate);
							Integer refund = chargeDetailService.updateWithLock(cd,lockDate);
							//创建退款记录
							if(refund==1){
								cr.setCid(cd.getCid());
								cr.setCd_id(cd.getCd_id());
								cr.setTxnAmt(refundMoney);
								cr.setTxnTime(refundDate);
								cr.setInsert_time(new Date());
								cr.setOrg_user_id(Integer.parseInt(orgUserId));
								cr.setUser_identify(Constant.IDENTITY_TEACHER);
								cr.setUser_type_id(Integer.parseInt(teacherId));
								cr.setUser_type_name(teacherName);
								chargeRecordService.save(cr);
								logger.info("【申请退款】教师tech_id"+teacherId+"于"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime())+"发起了对订单cd_id"+cd.getCd_id()+"的退费申请");
								ok++;
							}else{
								logger.error("【退款失败】校验账单乐观锁时失败，可能的原因：账单状态变动。ChargeDetail主键:"+cd.getCd_id());
								fail++;
							}
						}
					}else{
						logger.error("【退款错误】为退款订单设置乐观锁时失败。订单编号:"+row[5]);
						fail++;
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("【退款错误】退款时发生异常。订单编号:"+row[5]);
					fail++;
				}
			}
			session.removeAttribute("notFoundExcelJson");
			session.removeAttribute("refundExcelJson");
			result.setMessage("提交成功！成功"+ok+"条，失败"+fail+"条。");
			result.setSuccess(true);
		}else{
			result.setSuccess(false);
			result.setMessage("提交失败");
		}
		return result;
	}

	private String getStatus(Integer status){
		if(Constant.CHARGE_DETAIL_STATUS_NEVER.equals(status)){
			return "未支付";
		}else if(Constant.CHARGE_DETAIL_STATUS_OK.equals(status)){
			return "已支付";
		}else if(Constant.CHARGE_DETAIL_STATUS_QUIT.equals(status)){
			return "不参与";
		}else if(Constant.CHARGE_DETAIL_STATUS_REFUND_ALL.equals(status)){
			return "已退全款";
		}else if(Constant.CHARGE_DETAIL_STATUS_REFUND_APPLY.equals(status)){
			return "申请退款";
		}else if(Constant.CHARGE_DETAIL_STATUS_REFUND_FAIL.equals(status)){
			return "退款失败";
		}else if(Constant.CHARGE_DETAIL_STATUS_REFUND_PART.equals(status)){
			return "部分退款";
		}else if(Constant.CHARGE_DETAIL_STATUS_REFUSE.equals(status)){
			return "已驳回";
		}else{
			return "未知";
		}
	}

	*//**
	 * 导出支付统计
	 *//*
	@RequestMapping(value = "/downloadChargeDetails.htm")
 	public void downloadChargeDetails(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		String cid = request.getParameter("cid");
		List<ChargeDetail> list = chargeDetailService.getList(Integer.parseInt(cid));
		List<String[]> workbook = new ArrayList<String[]>();
		for (int i = 0; i < list.size(); i++) {
			ChargeDetail cd = list.get(i);
			String[] row = new String[10];
			row[0] = (i+1)+"";
			row[1] = cd.getGrade_name();
			row[2] = cd.getClas_name();
			row[3] = cd.getStud_name();
			row[4] = getStatus(cd.getStatus());
			row[5] = cd.getMoney();
			row[6] = MoneyUtil.fromFENtoYUAN(cd.getTxnAmt());
			row[7] = cd.getOrder_id();
			if(null!=cd.getTxnTime()){
				row[8] = cd.getTxnTime().toString();
			}else{
				row[8] = "——";
			}
			if(Constant.CHARGE_DETAIL_STATUS_OK<=(cd.getOnline_pay())&&Constant.CHARGE_DETAIL_STATUS_OK<=(cd.getOffline_pay())){
				row[9] = "在线缴费|线下缴费";
			}else if(Constant.CHARGE_DETAIL_STATUS_OK<=(cd.getOnline_pay())){
				row[9] = "在线缴费";
			}else if(Constant.CHARGE_DETAIL_STATUS_OK<=(cd.getOffline_pay())){
				row[9] = "线下缴费";
			}
			workbook.add(row);
		}
		if(null!=workbook&&workbook.size()!=0){
			Workbook wb = ExcelUtil.makeWorkbook("收费情况统计表", Constant.CHARGE_DETAIL_EXCEL, workbook, "xls");
			ExcelUtil.makeAndOutExcelFile(request, response, wb, "收费情况统计表.xls");
		} else {
			Workbook wb = ExcelUtil.makeWorkbook("收费情况统计表", Constant.CHARGE_DETAIL_EXCEL, null, "xls");
			ExcelUtil.makeAndOutExcelFile(request, response, wb, "收费情况统计表.xls");
		}
	}

	*//**
	 * 查询退费申请列表
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception 
	 *//*
	@RequestMapping(value="/getRefundApplyList.json")
	@ResponseBody
	public Result<List<ChargeDetail>> getRefundApplyList(HttpServletRequest request, 
			@RequestParam(name = "starttime", defaultValue="1900-01-01 00:00:00") String starttime,  
			@RequestParam(name = "endtime", defaultValue="2100-01-01 00:00:00") String endtime,  
			@RequestParam(name = "clas_id", defaultValue="") String clas_id,  
			@RequestParam(name = "pay", defaultValue="") String pay,  
			@RequestParam(name = "status", defaultValue="") String status,  
			@RequestParam(name = "order_id", defaultValue="") String order_id,  
			@RequestParam(name = "stud_name", defaultValue="") String stud_name,  
			@RequestParam(name = "item", defaultValue="") String item,  
			@RequestParam(name = "org_id", defaultValue="") String org_id,  
			HttpServletResponse response, HttpSession session) throws Exception {
		Result<List<ChargeDetail>> result = new Result<List<ChargeDetail>>(); 
		ChargeDetail cd = new ChargeDetail();
		try {
			cd.setStart_time(Constant.sdf.parse(starttime+" 00:00:00"));
			cd.setEnd_time(Constant.sdf.parse(endtime+" 23:59:59"));
		} catch (Exception e) {
		}
		try {
			cd.setClas_id(Integer.parseInt(clas_id));
		} catch (Exception e) {
		}
		if(StringUtils.isNotBlank(pay)){
			if(Constant.TXNTYPE_UNIONPAY_PAY.equals(pay)){
				cd.setOnline_pay(Constant.CHARGE_DETAIL_STATUS_OK);
			}else if(Constant.TXNTYPE_SJWY_OFFLINE_PAY.equals(pay)){
				cd.setOffline_pay(Constant.CHARGE_DETAIL_STATUS_OK);
			}
		}
		cd.setOrg_id(Integer.parseInt(org_id));
		cd.setCr_status(status);
		cd.setOrder_id(order_id);
		cd.setStud_name(stud_name);
		cd.setItem(item);
		List<ChargeDetail> list = new ArrayList<ChargeDetail>();
		list = chargeDetailService.getRefundApplyList(cd);
		result.setSuccess(true);
		result.setData(list);
		return result;
	}

	*//**
	 * 通过退款申请
	 *//*
	@RequestMapping(value = "/passRefund.json")
	@ResponseBody
	public Result<String> passRefund(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Result<String> result = new Result<String>();
		String audit_id = request.getParameter("audit_id");
		String code = request.getParameter("code");
		String data = request.getParameter("data");
		String phone = "";
		String tech_id = "";
		String tech_name ="";
		Integer org_user_id = 0;
		Integer org_id = 0;
		try {			
			org_user_id = Integer.parseInt(request.getParameter("user_id").toString());
			Teacher teacher = this.getTechByUId(org_user_id.toString());			
			tech_id = teacher.getTech_id().toString();
			tech_name = teacher.getTech_name();
			org_id = Integer.parseInt(request.getParameter("org_id").toString());
		} catch (Exception e) {
			logger.error("获取登录信息不完整！");
			result.setMessage("获取登录信息不完整！");
			result.setSuccess(false);
			return result;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("org_id", org_id);
		try {
			String resultManagerStr = this.callUrl(request, "api/tech/getTechsOfRlId", paramMap);
			Result<Map<String,List<Teacher>>> managerResult = JsonUtil.getObjectFromJson(resultManagerStr, new TypeReference<Result<Map<String,List<Teacher>>>>() { });
			for (Teacher t : managerResult.getData().get("techs")) {
				if(t.getTech_id().toString().equals(audit_id)){
					phone = t.getUser_mobile();
					break;
				}
			}
			String validResult = HttpClientUtil.post(ESB_REQUEST_URL+"validCode", "phone="+phone+"&code="+code, "application/x-www-form-urlencoded");
			Result<String> vResult = JsonUtil.getObjectFromJson(validResult, new TypeReference<Result<String>>() {});
			if(200!=vResult.getCode()){
				result.setSuccess(false);
				result.setMessage("验证码错误！");
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("验证码校验异常！");
			return result;
		}
		Map<String, Object> messageMap = new HashMap<String, Object>();
		messageMap.put("data", data);
		messageMap.put("org_user_id", org_user_id);
		messageMap.put("tech_id", tech_id);
		messageMap.put("tech_name", tech_name);
		messageMap.put("org_id", org_id);
		rabbitTemplate.convertAndSend("charge_refund_exchange", null, messageMap);
		result.setSuccess(true);
		result.setMessage("审核已提交！");
		return result;
	}

	*//**
	 * 驳回退款申请
	 *//*
	@RequestMapping(value = "/refuseRefund.json")
	@ResponseBody
	public Result<String> refuseRefund(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Result<String> result = new Result<String>();
		String audit_id = request.getParameter("audit_id");
		String code = request.getParameter("code");
		String phone = "";
		String tech_id = "";
		String tech_name = "";
		Integer org_user_id = 0;
		Integer org_id = 0;
		try {			
			org_user_id = Integer.parseInt(request.getParameter("user_id").toString());
			Teacher teacher = this.getTechByUId(org_user_id.toString());			
			tech_id = teacher.getTech_id().toString();
			tech_name = teacher.getTech_name();
			org_id = Integer.parseInt(request.getParameter("org_id").toString());
		} catch (Exception e) {
			logger.error("获取登录信息不完整！");
			result.setMessage("获取登录信息不完整！");
			result.setSuccess(false);
			return result;
		}
		String cd_ids = request.getParameter("cd_ids");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("org_id", org_id);
		try {
			String resultManagerStr = this.callUrl(request, "api/tech/getTechsOfRlId", paramMap);
			Result<Map<String,List<Teacher>>> managerResult = JsonUtil.getObjectFromJson(resultManagerStr, new TypeReference<Result<Map<String,List<Teacher>>>>() { });
			for (Teacher t : managerResult.getData().get("techs")) {
				if(t.getTech_id().toString().equals(audit_id)){
					phone = t.getUser_mobile();
					break;
				}
			}
			String validResult = HttpClientUtil.post(ESB_REQUEST_URL+"validCode", "phone="+phone+"&code="+code, "application/x-www-form-urlencoded");
			Result<String> vResult = JsonUtil.getObjectFromJson(validResult, new TypeReference<Result<String>>() {});
			if(200!=vResult.getCode()){
				result.setSuccess(false);
				result.setMessage("验证码错误！");
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("验证码校验异常！");
			return result;
		}
		Map<String, Object> messageMap = new HashMap<String, Object>();
		messageMap.put("cd_ids", cd_ids);
		messageMap.put("org_user_id", org_user_id);
		messageMap.put("tech_id", tech_id);
		messageMap.put("tech_name", tech_name);
		messageMap.put("org_id", org_id);
		rabbitTemplate.convertAndSend("charge_refund_refuse_exchange", null, messageMap);
		result.setSuccess(true);
		result.setMessage("审核完成！");
		return result;
	}
	

	*//**
	 * 重复校验
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception 
	 *//*
	@RequestMapping(value="/duplication.json")
	@ResponseBody
	public Result<Integer> duplication(HttpServletRequest request, 
			@RequestParam(name = "range") String range,  
			@RequestParam(name = "item") String item,  
			@RequestParam(name = "txnAmt") String txnAmt,  
			@RequestParam(name = "org_id") String org_id,  
			HttpServletResponse response, HttpSession session) throws Exception {
		Result<Integer> result = new Result<Integer>(); 
		Integer duplication = chargeService.duplication(org_id,item,txnAmt,range,Constant.PAY_TYPE_UNIFIED);
		session.setAttribute("checker", "checker");
		result.setData(duplication);
		result.setSuccess(true);
		return result;
	}*/

}
