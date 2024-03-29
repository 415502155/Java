package sng.controller.weixin;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sng.comparator.ChargeDetailSorter;
import sng.comparator.ChargeSorter;
import sng.constant.Consts;
import sng.controller.common.BaseAction;
import sng.entity.Charge;
import sng.entity.ChargeDetail;
import sng.entity.ChargeRecord;
import sng.entity.StudentClass;
import sng.pojo.Result;
import sng.pojo.base.OrgUser;
import sng.pojo.base.Organization;
import sng.pojo.base.Parent;
import sng.pojo.base.Teacher;
import sng.service.AccountService;
import sng.service.CertificationRecordService;
import sng.service.ChargeDetailService;
import sng.service.ChargeRecordService;
import sng.service.ChargeService;
import sng.service.MQService;
import sng.service.StudentClassService;
import sng.service.StudentService;
import sng.service.TokenService;
import sng.service.UserAuthService;
import sng.service.impl.ApplyServiceImpl;
import sng.util.Constant;
import sng.util.EnumLog;
import sng.util.LoggerUtil;
import sng.util.PropertyReader;
import sng.util.MoneyUtil;
import unionpay.acp.sdk.AcpService;
import unionpay.acp.sdk.LogUtil;
import unionpay.acp.sdk.SDKConfig;

import com.alibaba.fastjson.JSONObject;


@RestController
@RequestMapping("/wechat/portal/charge")
public class ChargeController extends BaseAction{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static String UNION_PAY_RESULT = PropertyReader.getCommonProperty("unionPay");

	
	
	@Autowired
	private ChargeService chargeService;
	@Autowired
	private ChargeDetailService chargeDetailService;
	@Autowired
	private ChargeRecordService chargeRecordService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private UserAuthService userAuthService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private CertificationRecordService certificationRecordService;
	@Autowired
	private MQService mqService;
	@Autowired
	private StudentClassService studentClassService;
	@Resource
	private ApplyServiceImpl applyServiceImpl;
	@Autowired
	private TokenService tokenService;
	
	
	/********************************************教师移动端********************************************/
	/**
	 * 获取当前教师所在校区、当前学期下的所有支付列表
	 */
	@RequestMapping(value = "/getTList.json")
	public Result<Map<String, Object>> getTList(HttpServletRequest request,
			@RequestParam(name = "term_id") Integer term_id,
			HttpServletResponse response, HttpSession session) throws Exception {
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();	
		String user_id = "";
		Integer teacher_id = null;
		try {		
			user_id = request.getAttribute("user_id").toString();
			Teacher teacher = this.getTechByUId(user_id);
			teacher_id = teacher.getTech_id();
		} catch (Exception e) {
			logger.error("获取登录信息不完整！");
			result.setMessage("获取登录信息不完整！");
			result.setSuccess(false);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Charge> list = chargeService.getTList(term_id,teacher_id);
			BigDecimal total = new BigDecimal(0);
			for (Charge charge : list) {
				total.add(new BigDecimal(charge.getSs_money())).subtract(new BigDecimal(charge.getSt_money()));
				charge.setClas_name(charge.getSubject_name()+"("+charge.getClas_name()+")");
				if(8==charge.getClass_week()){
					charge.setClass_info(StringUtils.isBlank(charge.getClass_unset_time())?"":charge.getClass_unset_time()+" "+charge.getCam_name()+charge.getBuilding()+charge.getClassroom_name());
				}else{
					charge.setClass_info(Consts.WEEK_MAP.get(charge.getClass_week()+"")+" "+Constant.minute.format(charge.getClass_begin_time())+"-"+Constant.minute.format(charge.getClass_over_time())+" "+charge.getCam_name()+charge.getBuilding()+charge.getClassroom_name());
				}
			}
			ChargeSorter mc = new ChargeSorter();  
			Collections.sort(list,mc); 
			map.put("list", list);
			map.put("total", total.toString());
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("获取失败");
			result.setSuccess(false);
		}
		map.put("sysTime", (new Date()).getTime());
		result.setData(map);
		return result;
	}
	
	/**
	 * 获取支付项的学生支付详情
	 */
	@RequestMapping(value = "/getTDetail.json")
	public Result<Map<String, Object>> getTDetail(HttpServletRequest request, 
			@RequestParam(name = "cid") Integer cid,
			HttpServletResponse response, HttpSession session) throws Exception {
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();	
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<ChargeDetail> paid = chargeDetailService.getDetailByStuStatus(cid,Consts.STUD_STATUS_5PAID);
			List<ChargeDetail> refund = chargeDetailService.getDetailByStuStatus(cid,Consts.STUD_STATUS_8REFUND_FINISHED);
			ChargeDetailSorter mc = new ChargeDetailSorter();  
			Collections.sort(paid,mc); 
			Collections.sort(refund,mc); 
			BigDecimal paid_money = new BigDecimal(0);
			BigDecimal refund_money = new BigDecimal(0);
			for (ChargeDetail cd : paid) {
				cd.setMoney(MoneyUtil.fromFENtoYUAN(cd.getTxnAmt()));
				paid_money.add(new BigDecimal(cd.getTxnAmt()));
			}
			for (ChargeDetail cd : refund) {
				try {
					cd.setMoney(MoneyUtil.fromFENtoYUAN((new BigDecimal(MoneyUtil.fromYUANtoFEN(cd.getMoney())).subtract(new BigDecimal(cd.getTxnAmt()))).toString()));
					refund_money.add(new BigDecimal(cd.getMoney()));
				} catch (Exception e) {
				}
			}
			map.put("paid", paid);
			map.put("refund", refund);
			map.put("paid_money", MoneyUtil.fromFENtoYUAN(paid_money.toString()));
			map.put("refund_money", MoneyUtil.fromFENtoYUAN(refund_money.toString()));
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("获取失败");
			result.setSuccess(false);
		}
		result.setData(map);
		return result;
	}
	
	/********************************************家长移动端********************************************/

	/**
	 * 获取指定孩子的支付记录
	 * 待支付、已支付、已作废、已退款
	 */
	@RequestMapping(value = "/getSList.json")
	public Result<JSONObject> getSList(HttpServletRequest request,
			@RequestParam(name = "stud_id",defaultValue="") String stud_id,
			HttpServletResponse response, HttpSession session) throws Exception {
		Result<JSONObject> result = new Result<JSONObject>();
		List<ChargeDetail> topay = new ArrayList<ChargeDetail>();
		List<ChargeDetail> paid = new ArrayList<ChargeDetail>();
		List<ChargeDetail> cancel = new ArrayList<ChargeDetail>();
		List<ChargeDetail> refund = new ArrayList<ChargeDetail>();
		JSONObject obj = new JSONObject();
		try {
			if(StringUtils.isBlank(stud_id)){
				stud_id = studentService.getStudentByParentUserId(request.getAttribute("user_id").toString()).get(0).getStud_id().toString();
			}
			List<ChargeDetail> list = chargeDetailService.getDetailByStudId(stud_id);
			for (ChargeDetail cd : list) {
				if(8==cd.getClass_week()){
					cd.setClass_time(cd.getClass_unset_time());
				}else{
					cd.setClass_time(Consts.WEEK_MAP.get(cd.getClass_week()+"")+" "+Constant.sfm.format(cd.getClass_begin_time())+"-"+Constant.sfm.format(cd.getClass_over_time()));
				}
				if(Constant.CHARGE_DETAIL_STATUS_NEVER.equals(cd.getStatus())){
					if(Consts.STUD_STATUS_3SIGNUP_TO_PAY==cd.getStu_status()){
						if(new Date().before(cd.getEnd_time())){
							topay.add(cd);
						}else{
							cancel.add(cd);
						}
					}else if(Consts.STUD_STATUS_2QUOTA_RESERVED==cd.getStu_status()){
						topay.add(cd);
					}else if(Consts.STUD_STATUS_4SIGNUP_VOIDED==cd.getStu_status()){
						cancel.add(cd);
					}
				}else if(Constant.CHARGE_DETAIL_STATUS_OK.equals(cd.getStatus())&&Consts.STUD_STATUS_5PAID==cd.getStu_status()){
					paid.add(cd);
				}else{
					refund.add(cd);
				}
			}
			obj.put("topay_num", topay.size());
			obj.put("topay_data", makeMap(topay));
			obj.put("paid_num", paid.size());
			obj.put("paid_data", makeMap(paid));
			obj.put("cancel_num", cancel.size());
			obj.put("cancel_data", makeMap(cancel));
			obj.put("refund_num", refund.size());
			obj.put("refund_data", makeMap(refund));
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.setSuccess(true);
		result.setCode(200);
		obj.put("sysTime", (new Date()).getTime());
		result.setData(obj);
		return result;
	}
	
	private Map<String,List<ChargeDetail>> makeMap(List<ChargeDetail> list){
		Map<String,List<ChargeDetail>> map = new HashMap<String,List<ChargeDetail>>();
		ChargeDetailSorter mc = new ChargeDetailSorter();  
		Collections.sort(list,mc); 
		for (ChargeDetail chargeDetail : list) {
			if(map.containsKey(chargeDetail.getTitle())){
				map.get(chargeDetail.getTitle()).add(chargeDetail);
			}else{
				List<ChargeDetail> temp = new LinkedList<ChargeDetail>();
				temp.add(chargeDetail);
				map.put(chargeDetail.getTitle(), temp);
			}
		}
		return map;
	}
	
	/**
	 * @Title : pay 
	 * @功能描述:提交线上缴费 
	 * @返回类型：void 
	 * @throws ：
	 */
	@RequestMapping(value = "/pay.htm")
	public void pay(HttpServletRequest request,
			@RequestParam(name = "cd_id") String cd_id,
			HttpServletResponse response, HttpSession session) throws Exception {
		String org_name = "";
		String identity = "";
		String parent_id = "";
		String user_id = "";
		String parent_name = "";
		String openid = "";
		String org_id = "";
		Result<Map<String,Object>> result = new Result<Map<String,Object>>();
		try {	
			org_id = request.getAttribute("org_id").toString();
			Organization org = this.getOrgByUId(org_id);
			org_name = org.getOrg_name_cn();
			identity = request.getAttribute("identity").toString();
			user_id = request.getAttribute("user_id").toString();
			openid = request.getAttribute("open_id").toString();
		} catch (Exception e) {
			logger.error("获取登录信息不完整！");
			result.setMessage("获取登录信息不完整！");
			result.setSuccess(false);
			request.setAttribute("result", result);
		}
		try {
			Parent parent = this.getParentByUId(user_id);
			parent_id = parent.getParent_id().toString();
			parent_name = parent.getParent_name();
		} catch (Exception e) {
		}
		result.setSuccess(true);
		//取出的orderDesc字段临时存放的是光大支付account表里的url字段值，后面使用完毕后会恢复并更新回去
		ChargeDetail cd = chargeDetailService.getByIdForPayWithCEBUrl(Integer.parseInt(cd_id));
		if(null==cd||null==cd.getCd_id()){
			result.setSuccess(false);
			result.setMessage("请求失败！原因：缴费项不存在，请刷新后重试！");
			request.setAttribute("result", result);
			request.getRequestDispatcher(UNION_PAY_RESULT).forward(request, response);
		}else if(!cd.getStatus().equals(Constant.CHARGE_DETAIL_STATUS_NEVER)||
					!cd.getOnline_pay().equals(Constant.CHARGE_DETAIL_STATUS_NEVER)||
					!cd.getOffline_pay().equals(Constant.CHARGE_DETAIL_STATUS_NEVER)){
			result.setSuccess(false);
			result.setMessage("请求失败！原因：缴费项状态异常，已经完成缴费或退费！");
			request.setAttribute("result", result);
			request.getRequestDispatcher(UNION_PAY_RESULT).forward(request, response);
		}else if(cd.getStart_time().getTime()>(new Date()).getTime()){
			result.setSuccess(false);
			result.setMessage("请求失败！原因：缴费尚未开始！");			
			request.setAttribute("result", result);
			request.getRequestDispatcher(UNION_PAY_RESULT).forward(request, response);
		}else if(cd.getEnd_time().getTime()<(new Date()).getTime()){
			result.setSuccess(false);
			result.setMessage("请求失败！原因：缴费已经结束！");			
			request.setAttribute("result", result);
			request.getRequestDispatcher(UNION_PAY_RESULT).forward(request, response);
		}else if(null!=cd.getTxnAmt()&&!cd.getTxnAmt().equals("0")){
			result.setSuccess(false);
			result.setMessage("请求失败！原因：缴费金额异常！");			
			request.setAttribute("result", result);
			request.getRequestDispatcher(UNION_PAY_RESULT).forward(request, response);
		}else if(cd.getC_status()!=Constant.CHARGE_STATUS_PUSH_OK){
			result.setSuccess(false);
			result.setMessage("请求失败！原因：缴费通知状态改变，请刷新后重试！");			
			request.setAttribute("result", result);
			request.getRequestDispatcher(UNION_PAY_RESULT).forward(request, response);
		}else{
			//取出临时存在orderDesc字段里的光大url信息备用
			String url = cd.getOrderDesc();
			//再将原本的orderDesc信息还原
			cd.setOrderDesc(cd.getItem());
			//光大支付流程
			if(Constant.ACCTYPE_CEB.intValue()==cd.getAccType().intValue()){
				chargeDetailService.update(cd);
				// 小于0表示该逻辑块关闭，不进行查询验证
				if(Constant.CHARGE_QUERY_TIMEOUT_MILLISECONDS>=0){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					Calendar c = Calendar.getInstance();
					Date clickTime = c.getTime();
					c.add(Calendar.MILLISECOND, Constant.CHARGE_QUERY_TIMEOUT_MILLISECONDS);
					Date queryTime = c.getTime();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("orderId", cd.getOrder_id());
					map.put("merId", cd.getMerId());
					map.put("clickTime", new SimpleDateFormat("yyyyMMddHHmmss").format(clickTime));
					if(!sdf.format(clickTime).equals(sdf.format(queryTime))){
						map.put("queryTime", new SimpleDateFormat("yyyyMMddHHmmss").format(queryTime));
					}
					mqService.sendMessage("charge_query_delay_exchange", null, map);
				}
				response.sendRedirect(url+"&userNo="+Constant.PAYMENT_PROJECT_SNG+cd.getCd_id()+"&filed1="+user_id);
			}else if(Constant.ACCTYPE_UNIONPAY.intValue()==cd.getAccType().intValue()){
				//记录尝试支付时间，以备发生异常时查看
				//银联：页面跳转前更新该字段（光大：跳转后由查询缴费接口更新）
				cd.setTry_time(new Date());
				chargeDetailService.update(cd);
				
				Map<String, String> requestData = new HashMap<String, String>();
				
				/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
				requestData.put("version", SDKConfig.getConfig().getVersion());   			  //版本号，全渠道默认值
				requestData.put("encoding", "UTF-8"); 			  //字符集编码，可以使用UTF-8,GBK两种方式
				requestData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
				requestData.put("txnType", "01");               			  //交易类型 ，01：消费
				requestData.put("txnSubType", "01");            			  //交易子类型， 01：自助消费
				requestData.put("bizType", "000201");           			  //业务类型，B2C网关支付，手机wap支付
				requestData.put("channelType", "07");           			  //渠道类型，这个字段区分B2C网关支付和手机wap支付；07：PC,平板  08：手机
				
				Base64 base64 = new Base64();
				String reqREserved = cd_id+"|"+user_id+"|"+parent_id+"|"+parent_name+"|"+org_id+"|"+org_name+"|"+openid+"|"+identity+"|"+cd.getItem()+"|"+cd.getStud_id();
				byte[] textByte = reqREserved.getBytes("UTF-8");
				String encodedText = base64.encodeToString(textByte);
				
				//编码
				/***商户接入参数***/
				requestData.put("merId", cd.getMerId());    	          			  //商户号码，请改成自己申请的正式商户号或者open上注册得来的777290058112538测试商户号
				requestData.put("accessType", "0");             			  //接入类型，0：直连商户 
				requestData.put("orderId",cd.getOrder_id());             //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则		
				requestData.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime()));        //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
				requestData.put("currencyCode", "156");         			  //交易币种（境内商户一般是156 人民币）		
				requestData.put("txnAmt", MoneyUtil.fromYUANtoFEN(cd.getMoney()));             			      //交易金额，单位分，不要带小数点
				requestData.put("reqReserved",  encodedText);  //请求方保留域，如需使用请启用即可；透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节。出现&={}[]符号时可能导致查询接口应答报文解析失败，建议尽量只传字母数字并使用|分割，或者可以最外层做一次base64编码(base64编码之后出现的等号不会导致解析失败可以不用管)。		
				requestData.put("frontUrl", SDKConfig.getConfig().getFrontUrl());
				requestData.put("backUrl", SDKConfig.getConfig().getBackUrl());
				requestData.put("payTimeout", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime() + 15 * 60 * 1000));
				/**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
				Map<String, String> submitFromData = AcpService.signByCertInfo(requestData,cd.getSignCert(), cd.getPwd(), "utf-8");
				
				String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();  //获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
				String html = AcpService.createAutoFormHtml(requestFrontUrl, submitFromData,"UTF-8");   //生成自动跳转的Html表单
				
				LogUtil.writeLog("打印请求HTML，此为请求报文，为联调排查问题的依据："+html);
				
				// 小于0表示该逻辑块关闭，不进行查询验证
				if(Constant.CHARGE_QUERY_TIMEOUT_MILLISECONDS>=0){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					Calendar c = Calendar.getInstance();
					Date clickTime = c.getTime();
					c.add(Calendar.MILLISECOND, Constant.CHARGE_QUERY_TIMEOUT_MILLISECONDS);
					Date queryTime = c.getTime();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("orderId", cd.getOrder_id());
					map.put("merId", cd.getMerId());
					map.put("clickTime", new SimpleDateFormat("yyyyMMddHHmmss").format(clickTime));
					if(!sdf.format(clickTime).equals(sdf.format(queryTime))){
						map.put("queryTime", new SimpleDateFormat("yyyyMMddHHmmss").format(queryTime));
					}
					mqService.sendMessage("charge_query_delay_exchange", null, map);
				}
				//将生成的html写到浏览器中完成自动跳转打开银联支付页面；这里调用signData之后，将html写到浏览器跳转到银联页面之前均不能对html中的表单项的名称和值进行修改，如果修改会导致验签不通过
				response.getWriter().write(html);
			}else{
				LogUtil.writeErrorLog("没有查询到匹配的账户类型");
			}
		}
	}
	
	/**
	 * 家长发起退费
	 */
	@RequestMapping(value = "/refund.json")
	public Result<String> refund(HttpServletRequest request,
			@RequestParam(name = "cd_id") Integer cd_id, 
			@RequestParam(name = "stu_class_id") Integer stu_class_id, 
			@RequestParam(name = "openid") String openid, 
			HttpServletResponse response, HttpSession session) throws Exception {
		Integer parent_id = null;
		String user_id = "";
		String parent_name = "";
		String org_id = "";
		OrgUser user = null;
		String mobile = "";
		Result<String> result = new Result<String>();
		StudentClass sc = studentClassService.getById(stu_class_id);
		if(!sc.getStuStatus().equals(Consts.STUD_STATUS_5PAID)){
			result.setMessage("当前学员状态不允许退费！");
			result.setSuccess(false);
			return result;
		}
		try {	
			org_id = request.getAttribute("org_id").toString();
			user_id = request.getAttribute("user_id").toString();
			user = this.getOrgUser(request);
			mobile = user.getUser_mobile();
			Parent parent = this.getParentByUId(user_id);
			parent_id = parent.getParent_id();
			parent_name = parent.getParent_name();
		} catch (Exception e) {
			result.setMessage("获取登录信息不完整！");
			result.setSuccess(false);
			return result;
		}
		ChargeDetail cd = chargeDetailService.getByIdForRefund(cd_id);
		Boolean autoPass = false;
		Date refundDate = new Date();
		if(Constant.NO==cd.getPlan_switch()){
			result.setMessage("招生计划已经关闭。请联系老师进行线下退费操作！");
			result.setSuccess(false);
		}else if(!Constant.CHARGE_DETAIL_STATUS_OK.equals(cd.getStatus())){
			result.setMessage("当前支付状态不符合退费条件！");
			result.setSuccess(false);
		}else if(!Constant.CHARGE_DETAIL_STATUS_NEVER.equals(cd.getOffline_pay())){
			result.setMessage("线下支付请联系老师申请线下退费！");
			result.setSuccess(false);
		}else if(cd.getRefund().equals(Constant.NO)){
			result.setMessage("请求失败！原因：该项收费已经关闭退费入口！");
			result.setSuccess(false);
		}else if(!Constant.CHARGE_DETAIL_STATUS_OK.equals(cd.getStatus())){
			result.setMessage("请求失败！原因：订单状态错误，请刷新后重试！");
			result.setSuccess(false);
		}else{
			ChargeRecord cr = new ChargeRecord();
			//开始退款线上
			if(cd.getOnline_pay().equals(Constant.CHARGE_DETAIL_STATUS_OK)&&cd.getStatus().equals(Constant.CHARGE_DETAIL_STATUS_OK)){
				if(Constant.NO.equals(cd.getAudit_switch())){
					autoPass = true;//审核关闭，不需要审核，系统自动提交退费审核
					cd.setOnline_pay(Constant.CHARGE_DETAIL_STATUS_REFUND_APPLY);
					cr.setTxnType(Constant.TXNTYPE_SJWY_ONLINE_REFUND_APPLY);
				}else{
					cd.setOnline_pay(Constant.CHARGE_DETAIL_STATUS_REFUND_PREVIEW);
					cr.setTxnType(Constant.TXNTYPE_SJWY_OFFLINE_REFUND_PREVIEW);
				}
			}else{
				result.setMessage("请求失败！原因：订单状态错误！");
				result.setSuccess(false);
				return result;
			}
			cd.setStatus(Constant.CHARGE_DETAIL_STATUS_REFUND_APPLY);
			cd.setModify_time(refundDate);
			Integer refund = chargeDetailService.update(cd);
			//创建退款记录
			if(refund==1){
				if(Constant.YES.equals(cd.getAudit_switch())){
					//开启了退费审核，先进入教务人员预审状态
					sc.setStuStatus(Consts.STUD_STATUS_6REFUND_TO_AUDIT);
				}else{
					sc.setStuStatus(Consts.STUD_STATUS_7REFUND_AUDITED);
				}
				studentClassService.update(sc);
				cr.setCid(cd.getCid());
				cr.setCd_id(cd.getCd_id());
				cr.setTxnAmt(cd.getTxnAmt());
				cr.setTxnTime(refundDate);
				cr.setInsert_time(new Date());
				cr.setOrg_user_id(Integer.parseInt(user_id));
				cr.setUser_identify(Constant.IDENTITY_PARENT);
				cr.setUser_type_id(parent_id);
				cr.setUser_type_name(parent_name);
				cr.setIs_del(Constant.IS_DEL_NO);
				cr.setIs_show(Constant.YES);
				chargeRecordService.save(cr);
				result.setSuccess(true);
				result.setMessage(cd.getRefund_instructions());
				LoggerUtil.save(cd.getOrg_id(), cd.getCam_id(), cd.getStud_id(), EnumLog.TARGET_TYPE_STU.getKey(), cd.getStud_name(), EnumLog.OPERATION_STU_RETURN_PREMIUM.getKey(), cd.getStud_name()+EnumLog.OPERATION_STU_RETURN_PREMIUM.getValue(), Constant.IDENTITY_PARENT, parent_id, parent_name, null);
			}else{
				logger.error("【退款失败】校验账单乐观锁时失败。ChargeDetail主键:"+cd_id);
				result.setMessage("请求失败！原因：账单状态变动，请确认后重试！");
				result.setSuccess(false);
			}
		}
		if(autoPass){
			Map<String, Object> messageMap = new HashMap<String, Object>();
			messageMap.put("data", "[{\"money\":"+cd.getMoney()+",\"cd_id\":"+cd_id+"}]");
			messageMap.put("org_user_id", user_id);
			messageMap.put("tech_id", 0);
			messageMap.put("tech_name", "系统自动审核");
			messageMap.put("org_id", org_id);
			messageMap.put("autoPass", Constant.YES);
			mqService.sendMessage("charge_refund_exchange", null, messageMap);
		}else if(result.isSuccess()){
			//非自动审核的发送通知，自动审核的等待自动审核后发送通知
			Map<String, Object> map = new HashMap<String, Object>();
			if(StringUtils.isNotBlank(mobile)){//SMS
				map.put("mobile", mobile);
				map.put("content", String.format(Constant.MOBILE_MESSAGE_CHARGE_RESULT_NOTICE,cd.getStud_name(),cd.getItem(),"的退费申请已成功提交，等待教务人员审核。"));
			}
			String template_id = "";
			try {
				template_id = tokenService.getSNGWXTemplateId(cd.getOrg_id().toString(), Constant.TEMPLATE_NAME_REFUND_SUCCESS_NOTICE);
			} catch (Exception e) {
			}
			if(StringUtils.isNotBlank(openid)&&StringUtils.isNotBlank(template_id)){//WX
				map.put("open_id", openid);
				map.put("templateId", template_id);
				map.put("org_id", org_id);
				map.put("keyword1", cd.getStud_name());
				map.put("keyword2", cd.getMoney()+"元");
				map.put("keyword3", Constant.ymd.format(refundDate));
				map.put("first", "退款申请提交成功！");
				map.put("keyword4", cd.getItem());
				map.put("remark", "退款申请已成功提交，等待教务人员审核，审核一般需要3个工作日，请关注微信通知。如有疑问请您与班主任老师或学校财务部门联系。感谢您的使用！");
			}
			mqService.sendMessage("send_message_exchange", null, map);
		}
		if(result.isSuccess()){
			//如果提交退费申请成功，调用随机时间释放名额方法
			applyServiceImpl.addQuotaHoldMq(sc.getCamId(), sc.getClasId(), sc.getStudId());
		}
		return result;
	}
}
