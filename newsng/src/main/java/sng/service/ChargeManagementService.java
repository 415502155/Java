package sng.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sng.constant.Consts;
import sng.constant.ErrorConstant;
import sng.entity.Account;
import sng.entity.Charge;
import sng.entity.ChargeDetail;
import sng.entity.ChargeRecord;
import sng.entity.StudentClass;
import sng.pojo.Result;
import sng.pojo.WXTemplateMessage;
import sng.pojo.base.Organization;
import sng.pojo.base.Parent;
import sng.util.CebUtils;
import sng.util.Constant;
import sng.util.EnumLog;
import sng.util.LoggerUtil;
import sng.util.PropertyReader;
import sng.util.HttpClientUtil;
import sng.util.JsonUtil;
import sng.util.MoneyUtil;
import sng.util.RedisUtil;
import sng.util.SendMessageUtil;
import unionpay.acp.sdk.AcpService;
import unionpay.acp.sdk.LogUtil;
import unionpay.acp.sdk.SDKConfig;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Title: ChargeManagementService
 * Description: 
 * Company: 世纪伟业
 * @author Liuyang
 * @date 2018年1月26日下午7:39:39
 */
@Service
@Transactional
public class ChargeManagementService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static String ESB_REQUEST_URL = PropertyReader.getProperty("esbRequestUrl");
	private static String CEB_REFUND_URL = PropertyReader.getProperty("cebRefund");
	private static String CEB_QUERY_PAY_URL = PropertyReader.getProperty("cebQueryPay");
	private static int SJWY_ORG_ID = Integer.parseInt(PropertyReader.getProperty("sjwy_org_id").toString());
	private static String ERROR_NOTICE_API = PropertyReader.getProperty("errorNoticeAPI").toString();
	/**
	 * 对账文件路径
	 */
	protected static String BILL_CHECK_FILE_PATH = PropertyReader.getProperty("bill_check_filepath");
	
	@Autowired
	private MQService mqService;
	@Autowired
	private ChargeService chargeService;
	@Autowired
	private ChargeDetailService chargeDetailService;
	@Autowired
	private ChargeRecordService chargeRecordService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private StudentClassService studentClassService;
	@Autowired
	RedisUtil redisUtil;

	/**
	 * 发送短信和微信
	 * @param messageMap
	 */
	public void sendMessage(Map<String, Object> messageMap) {
		Boolean sms = false;
		Boolean wx = false;
//		if(messageMap.containsKey("mobile")){
//			try {
//				SendMessageUtil.sendMobileMessage(messageMap.get("mobile").toString(), messageMap.get("content").toString());
//				sms = true;
//			} catch (Exception e) {
//				logger.error(e.getMessage());
//				e.printStackTrace();
//			}
//		}
		if(messageMap.containsKey("open_id")){
			try {
				WXTemplateMessage templateMessage = SendMessageUtil.makeTemplateMessage(messageMap);
				String access_token = tokenService.getAccessTokenByOrg_Id(Integer.valueOf(messageMap.get("org_id").toString()));
				SendMessageUtil.sendTemplateMessageToWeiXin(access_token, templateMessage);
				wx = true;
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		if(messageMap.containsKey("status")&&null!=messageMap.get("status")&&messageMap.containsKey("cd_id")&&null!=messageMap.get("cd_id")){
			Integer is_send = 0;
			String status = messageMap.get("status").toString();
			if(Constant.IS_SEND_NEITHER.equals(status)){
				if(sms&&wx){
					is_send = Constant.IS_SEND_BOTH_OK;
				}else if(sms){
					is_send = Constant.IS_SEND_SMS_OK_ONLY;
				}else if(wx){
					is_send = Constant.IS_SEND_WX_OK_ONLY;
				}
			}else if(Constant.IS_SEND_SMS_OK_ONLY.equals(status)){
				if(wx){
					is_send = Constant.IS_SEND_BOTH_OK;
				}
			}else if(Constant.IS_SEND_WX_OK_ONLY.equals(status)){
				if(sms){
					is_send = Constant.IS_SEND_BOTH_OK;
				}
			}else if(Constant.IS_SEND_BOTH_OK.equals(status)){
				is_send = Constant.IS_SEND_BOTH_OK;
			}else{
				try {
					is_send = Integer.parseInt(status);
				} catch (Exception e) {
					is_send = Constant.IS_SEND_NEITHER;
				}
			}
			ChargeDetail cd = new ChargeDetail();
			cd.setCd_id(Integer.parseInt(messageMap.get("cd_id").toString()));
			cd.setIs_send(is_send);
			chargeDetailService.updateSendStatus(cd);
		}
	}

	/**
	 * @Title : remindCharge 
	 * @功能描述: 提醒缴费
	 * @返回类型：void 
	 * @throws ：
	 */
	public void remindCharge(Map<String, Object> messageMap) {
		if (messageMap != null && messageMap.size() > 0) {
			String cd_ids = messageMap.get("cd_ids").toString();
			String org_id = messageMap.get("org_id").toString();
			List<ChargeDetail> list = chargeDetailService.getListForRemind(null,null,cd_ids);
			List<Parent> parents = new ArrayList<Parent>();
			for (ChargeDetail cd : list) {
				try {
					String documentResult = HttpClientUtil.post(ESB_REQUEST_URL+"getParentPhone","stud_id="+cd.getStud_id(), "application/x-www-form-urlencoded");
					Result<List<Parent>> pResult = JsonUtil.getObjectFromJson(documentResult, new TypeReference<Result<List<Parent>>>() { });
					parents = pResult.getData();
					String template_id = "";
					try {
						template_id = tokenService.getSNGWXTemplateId(org_id, Constant.TEMPLATE_NAME_CHARGE_NOTICE_FOR_TEACHER);
					} catch (Exception e) {
					}
					if(null!=parents&&parents.size()!=0){
						for (Parent p : parents) {
							Map<String, Object> map = new HashMap<String, Object>();
							if(StringUtils.isNotBlank(p.getMobile())){//SMS
								map.put("cd_id", cd.getCd_id());
								map.put("mobile", p.getMobile());
								map.put("content", String.format(Constant.MOBILE_MESSAGE_CHARGE_NOTICE_FOR_PARENT,cd.getStud_name(),cd.getItem(),cd.getMoney()+"元，截止时间："+Constant.sdf.format(cd.getEnd_time())+"，过期名额将自动作废"));
		    				}
		    				if(StringUtils.isNotBlank(p.getOpenid())){//WX
		    					map.put("open_id", p.getOpenid());
		    					map.put("templateId", template_id);
		    					map.put("cd_id", cd.getCd_id());
		    					map.put("org_id", org_id);
		    					map.put("first", cd.getStud_name()+"家长，您好！");
		    					map.put("keyword1", cd.getItem());
		    					map.put("keyword2", cd.getMoney()+"元");
		    					map.put("keyword3", Constant.ymd.format(cd.getEnd_time()));
		    					map.put("remark", "请您务必在规定时间内缴费，否则名额将自动作废！");
		    				}
		    				map.put("status", cd.getIs_send());
		    				mqService.sendMessage("send_message_exchange", null, map);
						}
					}
				} catch (Exception e) {
					LogUtil.writeErrorLog("【错误】发送支付提醒时发生异常，未能向stud_id="+cd.getStud_id()+"的学生家长发送任何缴费提醒!");
				}
			}
		}
	}

	/**
	 * 通过退款申请
	 * @param messageMap
	 * @return
	 * @ 
	 */
	public void chargeRefund(Map<String, Object> messageMap)  {
		try {
			String org_id = messageMap.get("org_id").toString();
			String template_id4parent = null;
			String template_id4teacher = null;
			int online_success = 0;
			int online_fail = 0;
			int offline_success = 0;
			int offline_fail = 0;
			try {
				template_id4parent = tokenService.getSNGWXTemplateId(org_id, Constant.TEMPLATE_NAME_REFUND_SUCCESS_NOTICE);
				template_id4teacher = tokenService.getSNGWXTemplateId(org_id, Constant.TEMPLATE_NAME_REFUND_SUCCESS_NOTICE);
			} catch (Exception e) {
			}
			String org_user_id = messageMap.get("org_user_id").toString();
			String tech_id = messageMap.get("tech_id").toString();
			String tech_name = messageMap.get("tech_name").toString();
			String object = messageMap.get("data").toString();
			JSONArray arr = JSONArray.parseArray(object);
            Iterator<Object> it = arr.iterator();
            while (it.hasNext()) {
            	ChargeDetail cd = new ChargeDetail();
            	try {
	            	JSONObject obj = (JSONObject) it.next();
					ChargeRecord cr = chargeRecordService.getRefundByCdid(Integer.parseInt(obj.get("cd_id").toString()));
					//验证是否符合退款条件
					if(null!=cr&&new BigDecimal(cr.getMoney()).compareTo(new BigDecimal(obj.get("money").toString()))>-1){
						String refundMoney = MoneyUtil.fromYUANtoFEN(obj.get("money").toString());
						ChargeRecord record = new ChargeRecord();
						cd = chargeDetailService.getByIdForPay(cr.getCd_id());
						record.setCd_id(cr.getCd_id());
						record.setCid(cr.getCid());
						record.setInsert_time(new Date());
						record.setOrg_user_id(Integer.parseInt(org_user_id));
						record.setTxnAmt(refundMoney);
						record.setTxnTime(new Date());
						record.setUser_identify(Constant.IDENTITY_TEACHER);
						record.setUser_type_id(Integer.parseInt(tech_id));
						record.setUser_type_name(tech_name);
						record.setIs_del(Constant.IS_DEL_NO);
						record.setIs_show(Constant.YES);
						//线下缴费，走线下退费流程
						if(Constant.TXNTYPE_SJWY_OFFLINE_REFUND_APPLY.equals(cr.getTxnType())){
							record.setTxnType(Constant.TXNTYPE_SJWY_OFFLINE_REFUND_AGREE);
							if(cd.getTxnAmt().equals(record.getTxnAmt())){
								cd.setOffline_pay(Constant.CHARGE_DETAIL_STATUS_REFUND_ALL);
							}else{
								cd.setOffline_pay(Constant.CHARGE_DETAIL_STATUS_REFUND_PART);
							}
							if(!Constant.CHARGE_DETAIL_STATUS_OK.equals(cd.getOnline_pay())){
								cd.setStatus(cd.getOffline_pay());
							}
							cd.setTxnAmt((new BigDecimal(cd.getTxnAmt()).subtract(new BigDecimal(refundMoney))).toString());
							cd.setModify_time(new Date());
							Charge charge = chargeService.getById(cd.getCid());
							//在申请退费时脱坑，不在这里进行脱坑操作
							if(chargeService.chargeFinally(charge, cd, record,Consts.STUD_STATUS_8REFUND_FINISHED,null,false)){
								offline_success++;
								//将结果通知给家长和老师
								sendRefundSuccessMessage(cd,cr,record,template_id4parent,template_id4teacher);
								LoggerUtil.save(cd.getOrg_id(), charge.getCam_id(), cd.getStud_id(), EnumLog.TARGET_TYPE_STU.getKey(), cd.getStud_name(), EnumLog.OPERATION_STU_FINANCIAL_REFUND.getKey(), cd.getStud_name()+EnumLog.OPERATION_STU_FINANCIAL_REFUND.getValue(), Constant.IDENTITY_TEACHER, Integer.parseInt(tech_id), tech_name, null);
							}else{
								offline_fail++;
							}
						}else if(Constant.TXNTYPE_SJWY_ONLINE_REFUND_APPLY.equals(cr.getTxnType())){
							if(Constant.ACCTYPE_UNIONPAY.intValue()==cd.getAccType().intValue()){
								//UNION REFUND银联退款
								ChargeRecord pay = chargeRecordService.getPayRecord(cr.getCd_id());
								logger.info("pay"+pay.getCr_id());
								String origQryId = pay.getQueryId();
								String txnAmt = record.getTxnAmt();
								String merId = cd.getMerId();
								Map<String, String> data = new HashMap<String, String>();
								
								/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
								data.put("version", SDKConfig.getConfig().getVersion());               //版本号
								data.put("encoding", "UTF-8");             //字符集编码 可以使用UTF-8,GBK两种方式
								data.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
								data.put("txnType", "04");                           //交易类型 04-退货		
								data.put("txnSubType", "00");                        //交易子类型  默认00		
								data.put("bizType", "000201");                       //业务类型 B2C网关支付，手机wap支付	
								data.put("channelType", "07");                       //渠道类型，07-PC，08-手机		
								
								/***商户接入参数***/
								data.put("merId", merId);                //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
								data.put("accessType", "0");                         //接入类型，商户接入固定填0，不需修改		
								data.put("orderId", "9"+cd.getOrder_id().substring(1));          //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则，重新产生，不同于原消费		
								data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));      //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效		
								data.put("currencyCode", "156");                     //交易币种（境内商户一般是156 人民币）		
								data.put("txnAmt", txnAmt);                          //****退货金额，单位分，不要带小数点。退货金额小于等于原消费金额，当小于的时候可以多次退货至退货累计金额等于原消费金额		
								//data.put("reqReserved", "透传信息");                  //请求方保留域，如需使用请启用即可；透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节。出现&={}[]符号时可能导致查询接口应答报文解析失败，建议尽量只传字母数字并使用|分割，或者可以最外层做一次base64编码(base64编码之后出现的等号不会导致解析失败可以不用管)。		
								data.put("backUrl", SDKConfig.getConfig().getBackUrl());               //后台通知地址，后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 退货交易 商户通知,其他说明同消费交易的后台通知
								/***要调通交易以下字段必须修改***/
								data.put("origQryId", origQryId);      //****原消费交易返回的的queryId，可以从消费交易后台通知接口中或者交易状态查询接口中获取
								/**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/
								Map<String, String> submitFromData  = AcpService.signByCertInfo(data,cd.getSignCert(), cd.getPwd(), "utf-8");
								String url = SDKConfig.getConfig().getBackRequestUrl();//交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
								Map<String, String> rspData = AcpService.post(submitFromData,url,Constant.encoding);//这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
								/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
								//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
								if(!rspData.isEmpty()){
									if(AcpService.validate(rspData, Constant.encoding)){
										String respCode = rspData.get("respCode");
										if("00".equals(respCode)){
											//交易已受理，等待接收后台通知更新订单状态,也可以主动发起 查询交易确定交易状态。
											String txnType = rspData.get("txnType");
											String txnTime = rspData.get("txnTime");
											String returnTxnAmt = rspData.get("txnAmt");
											String returnOrigQryId = rspData.get("origQryId");
											String queryId = rspData.get("queryId");
											String respMsg = rspData.get("respMsg");
											String returnRespCode = rspData.get("respCode");
											String time = txnTime.substring(0,4)+"-"+txnTime.substring(4,6)+"-"+txnTime.substring(6,8)+" "+txnTime.substring(8,10)+":"+txnTime.substring(10,12)+":"+txnTime.substring(12,14);
											try {
												record.setTxnTime(Constant.sdf.parse(time));
											} catch (ParseException e) {
												record.setTxnTime(new Date());
											}
											record.setQueryId(queryId);
											record.setOrigQryId(returnOrigQryId);
											record.setTxnType(txnType);
											record.setTxnAmt(returnTxnAmt);
											record.setRespMsg(respMsg);
											record.setRespCode(returnRespCode);
											
											if(cd.getTxnAmt().equals(record.getTxnAmt())){
												cd.setOnline_pay(Constant.CHARGE_DETAIL_STATUS_REFUND_ALL);
											}else{
												cd.setOnline_pay(Constant.CHARGE_DETAIL_STATUS_REFUND_PART);
											}
											if(!Constant.CHARGE_DETAIL_STATUS_OK.equals(cd.getOffline_pay())){
												cd.setStatus(cd.getOnline_pay());
											}
											cd.setTxnAmt((new BigDecimal(cd.getTxnAmt()).subtract(new BigDecimal(record.getTxnAmt()))).toString());
											cd.setModify_time(new Date());
											Charge charge = chargeService.getById(cd.getCid());
											//只修改学员状态，不修改占坑状态，因为在申请退费的时候已经修改过了
											if(chargeService.chargeFinally(charge, cd, record, Consts.STUD_STATUS_8REFUND_FINISHED,null,false)){
												LoggerUtil.save(cd.getOrg_id(), charge.getCam_id(), cd.getStud_id(), EnumLog.TARGET_TYPE_STU.getKey(), cd.getStud_name(), EnumLog.OPERATION_STU_FINANCIAL_REFUND.getKey(), cd.getStud_name()+EnumLog.OPERATION_STU_FINANCIAL_REFUND.getValue(), Constant.IDENTITY_TEACHER, Integer.parseInt(tech_id), tech_name, null);
												online_success++;
												//通知家长和老师
												sendRefundSuccessMessage(cd,cr,record,template_id4parent,template_id4teacher);
											}else{
												online_fail++;
												if(messageMap.containsKey("autoPass")&&messageMap.get("autoPass").toString().equals(Constant.YES+"")){
													autoOnlineRefundFailed(cd,refundMoney);
												}
											}
										}else if("03".equals(respCode)|| 
												"04".equals(respCode)||
												"05".equals(respCode)){
											//后续需发起交易状态查询交易确定交易状态
											HttpClientUtil.post(ERROR_NOTICE_API,"errorType="+ErrorConstant.ERROR_TYPE_UNION_REFUND_ERROR+"&targetType="+ErrorConstant.EXCEPTION_TARGET_PROGRAMMER_PAY+"&content="+"[SNG]退费返回03,04,05,cd_id:"+cd.getCd_id()+"&org_id="+SJWY_ORG_ID+"&level="+ErrorConstant.EXCEPTION_LEVEL_NOT_NIGHT+"&interval=0", "application/x-www-form-urlencoded");
											online_fail++;
											if(messageMap.containsKey("autoPass")&&messageMap.get("autoPass").toString().equals(Constant.YES+"")){
												autoOnlineRefundFailed(cd,refundMoney);
											}
										}else{
											//其他应答码为失败请排查原因
											HttpClientUtil.post(ERROR_NOTICE_API,"errorType="+ErrorConstant.ERROR_TYPE_UNION_REFUND_ERROR+"&targetType="+ErrorConstant.EXCEPTION_TARGET_PROGRAMMER_PAY+"&content="+"[SNG]退费失败，返回其他应答码："+respCode+",cd_id:"+cd.getCd_id()+"&org_id="+SJWY_ORG_ID+"&level="+ErrorConstant.EXCEPTION_LEVEL_NOT_NIGHT+"&interval=0", "application/x-www-form-urlencoded");
											online_fail++;
											if(messageMap.containsKey("autoPass")&&messageMap.get("autoPass").toString().equals(Constant.YES+"")){
												autoOnlineRefundFailed(cd,refundMoney);
											}
										}
									}else{
										LogUtil.writeErrorLog("验证签名失败");
										// 检查验证签名失败的原因
										HttpClientUtil.post(ERROR_NOTICE_API,"errorType="+ErrorConstant.ERROR_TYPE_VALIDATE_ERROR+"&targetType="+ErrorConstant.EXCEPTION_TARGET_PROGRAMMER_PAY+"&content="+"[SNG]退费失败，验签失败,cd_id:"+cd.getCd_id()+"&org_id="+SJWY_ORG_ID+"&level="+ErrorConstant.EXCEPTION_LEVEL_NOT_NIGHT+"&interval=0", "application/x-www-form-urlencoded");
										online_fail++;
										if(messageMap.containsKey("autoPass")&&messageMap.get("autoPass").toString().equals(Constant.YES+"")){
											autoOnlineRefundFailed(cd,refundMoney);
										}
									}
								}else{
									//未返回正确的http状态
									LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
									HttpClientUtil.post(ERROR_NOTICE_API,"errorType="+ErrorConstant.ERROR_TYPE_REFUND_HTTPERROR+"&targetType="+ErrorConstant.EXCEPTION_TARGET_PROGRAMMER_PAY+"&content="+"[SNG]退费失败，未获取到返回报文或返回http状态码非200,cd_id:"+cd.getCd_id()+"&org_id="+SJWY_ORG_ID+"&level="+ErrorConstant.EXCEPTION_LEVEL_NOT_NIGHT+"&interval=0", "application/x-www-form-urlencoded");
									online_fail++;
									if(messageMap.containsKey("autoPass")&&messageMap.get("autoPass").toString().equals(Constant.YES+"")){
										autoOnlineRefundFailed(cd,refundMoney);
									}
								}
							}else if(Constant.ACCTYPE_CEB.intValue()==cd.getAccType().intValue()){
								//光大银行退款
								cr = chargeRecordService.getRefundByCdidForCEB(cd.getCd_id());
								String xml = CebUtils.getXML(Constant.CEB_TYPE_REFUND, cd, cr);
								LogUtil.writeErrorLog("发送给光大的退费请求报文:"+xml);
								org.json.JSONObject data = CebUtils.getJSONObjectByPostXML(CEB_REFUND_URL,xml);
								//退费成功
								if(data.getJSONObject("Out").getJSONObject("Head").get("AnsCode").toString().equals(Constant.CEB_ANSCODE_SUCCESS)&&
									(data.getJSONObject("Out").getJSONObject("Body").get("tranStat").toString().equals(Constant.CEB_ANSCODE_TRANSTAT_SUCCESS)||
									data.getJSONObject("Out").getJSONObject("Body").get("tranStat").toString().equals(Constant.CEB_ANSCODE_TRANSTAT_ING))){
									String txnType = Constant.TXNTYPE_UNIONPAY_REFUND;
									String txnTime = data.getJSONObject("Out").getJSONObject("Head").get("TranDateTime").toString();
									String time = txnTime.substring(0,4)+"-"+txnTime.substring(4,6)+"-"+txnTime.substring(6,8)+" "+txnTime.substring(8,10)+":"+txnTime.substring(10,12)+":"+txnTime.substring(12,14);
									String returnTxnAmt = MoneyUtil.fromYUANtoFEN(data.getJSONObject("Out").getJSONObject("Body").get("refundFee").toString());
									String queryId = data.getJSONObject("Out").getJSONObject("Body").get("seqNo").toString();
									String origQryId = cr.getQueryId();
									String returnRespCode = data.getJSONObject("Out").getJSONObject("Body").get("tranStat").toString();
									try {
										record.setTxnTime(Constant.sdf.parse(time));
									} catch (ParseException e) {
										record.setTxnTime(new Date());
									}
									record.setQueryId(queryId);
									record.setOrigQryId(origQryId);
									record.setTxnType(txnType);
									record.setTxnAmt(returnTxnAmt);
									record.setRespMsg(Constant.CEB_RESPMSG_SUCCESS);
									record.setRespCode(returnRespCode);
									record.setIs_del(Constant.IS_DEL_NO);
									record.setIs_show(Constant.YES);
									
									if(cd.getTxnAmt().equals(record.getTxnAmt())){
										cd.setOnline_pay(Constant.CHARGE_DETAIL_STATUS_REFUND_ALL);
									}else{
										cd.setOnline_pay(Constant.CHARGE_DETAIL_STATUS_REFUND_PART);
									}
									if(!Constant.CHARGE_DETAIL_STATUS_OK.equals(cd.getOffline_pay())){
										cd.setStatus(cd.getOnline_pay());
									}
									cd.setTxnAmt((new BigDecimal(cd.getTxnAmt()).subtract(new BigDecimal(record.getTxnAmt()))).toString());
									cd.setModify_time(new Date());
									Charge charge = chargeService.getById(cd.getCid());
									//只修改学员状态，不修改占坑状态，因为在申请退费的时候已经修改过了
									if(chargeService.chargeFinally(charge, cd, record,Consts.STUD_STATUS_8REFUND_FINISHED,null,false)){
										online_success++;
										//通知家长和老师
										cd.setItem(charge.getItem());
										sendRefundSuccessMessage(cd,cr,record,template_id4parent,template_id4teacher);
										LoggerUtil.save(cd.getOrg_id(), charge.getCam_id(), cd.getStud_id(), EnumLog.TARGET_TYPE_STU.getKey(), cd.getStud_name(), EnumLog.OPERATION_STU_FINANCIAL_REFUND.getKey(), cd.getStud_name()+EnumLog.OPERATION_STU_FINANCIAL_REFUND.getValue(), Constant.IDENTITY_TEACHER, Integer.parseInt(tech_id), tech_name, null);
									}else{
										online_fail++;
										if(messageMap.containsKey("autoPass")&&messageMap.get("autoPass").toString().equals(Constant.YES+"")){
											autoOnlineRefundFailed(cd,refundMoney);
										}
									}
								}else{
									String ansCode = data.getJSONObject("Out").getJSONObject("Head").get("AnsCode").toString();
									//退费失败，需要通知系统管理员	
									if(ErrorConstant.ERROR_MAP_SYSTEM_MANAGER.containsKey(ansCode)){
										HttpClientUtil.post(ERROR_NOTICE_API,"errorType="+ErrorConstant.ERROR_TYPE_CEB_REFUND_ERROR+"&targetType="+ErrorConstant.EXCEPTION_TARGET_PROGRAMMER_PAY+"&content="+ErrorConstant.ERROR_MAP_SYSTEM_MANAGER.get(ansCode)+",cd_id:"+cd.getCd_id()+"&org_id="+SJWY_ORG_ID+"&level="+ErrorConstant.EXCEPTION_LEVEL_IMMEDIATELY+"&interval=0", "application/x-www-form-urlencoded");
									}
									//退费失败，需要通知业务管理员
									if(ErrorConstant.ERROR_MAP_BUSINESS_MANAGER.containsKey(ansCode)){
										Organization org = (Organization) redisUtil.get("orginfo:"+cd.getOrg_id());
										HttpClientUtil.post(ERROR_NOTICE_API,"errorType="+ErrorConstant.ERROR_TYPE_CEB_REFUND_ERROR+"&targetType="+ErrorConstant.EXCEPTION_TARGET_SJWY_BUSINESS_PAY+"&content="+org.getOrg_name_cn()+"退款时发生异常，错误："+ErrorConstant.ERROR_MAP_BUSINESS_MANAGER.get(ansCode)+",cd_id"+cd.getCd_id()+"&org_id="+SJWY_ORG_ID+"&level="+ErrorConstant.EXCEPTION_LEVEL_NOT_NIGHT+"&interval=0", "application/x-www-form-urlencoded");
									}
									//退费失败，需要通知财务管理员（学校老师）
									if(ErrorConstant.ERROR_MAP_TEACHER_MANAGER.containsKey(ansCode)){
										HttpClientUtil.post(ERROR_NOTICE_API,"errorType="+ErrorConstant.ERROR_TYPE_CEB_REFUND_ERROR+"&targetType="+ErrorConstant.EXCEPTION_TARGET_TEACHER_CAIWU+"&content="+ErrorConstant.ERROR_MAP_SYSTEM_MANAGER.get(ansCode)+"，订单编号："+cd.getOrder_id()+"&org_id="+cd.getOrg_id()+"&level="+ErrorConstant.EXCEPTION_LEVEL_NOT_NIGHT+"&interval=0", "application/x-www-form-urlencoded");
									}
									online_fail++;
									if(messageMap.containsKey("autoPass")&&messageMap.get("autoPass").toString().equals(Constant.YES+"")){
										autoOnlineRefundFailed(cd,refundMoney);
									}
								}
							}
						}
					}else{ //不符合退款条件，只计数不做任何操作，恶意修改请求参数时才会出现的情况
						online_fail++;
						if(messageMap.containsKey("autoPass")&&messageMap.get("autoPass").toString().equals(Constant.YES+"")){
							autoOnlineRefundFailed(cd,null);
						}
					}        
            	} catch (Exception e) {
            		e.printStackTrace();
            		try {
            			LogUtil.writeErrorLog("【通过退款异常】cd_id:"+((JSONObject)it.next()).get("cd_id").toString());
					} catch (Exception e2) {
					}
            		online_fail++;
					if(messageMap.containsKey("autoPass")&&messageMap.get("autoPass").toString().equals(Constant.YES+"")){
						autoOnlineRefundFailed(cd,null);
					}
            	}
			}
            if(online_fail!=0||offline_fail!=0){
            	String message = "退款申请有部分未成功:线上退款成功"+online_success+"笔，失败"+online_fail+"笔，线下退款成功"+offline_success+"笔，失败"+offline_fail+"笔。";
            	if(0!=online_fail){
            		message += "线上退款失败时请确认您的退款账户状态正常且余额充足，";
            	}
            	message += "稍后可进入退费审核页面尝试重新审核。";
            	HttpClientUtil.post(ERROR_NOTICE_API,"errorType="+ErrorConstant.ERROR_TYPE_CEB_REFUND_RESULT+"&targetType="+ErrorConstant.EXCEPTION_TARGET_TEACHER_CAIWU+"&content="+message+"&org_id="+Integer.parseInt(messageMap.get("org_id").toString())+"&level="+ErrorConstant.EXCEPTION_LEVEL_IMMEDIATELY+"&interval=0", "application/x-www-form-urlencoded");
            }
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.writeErrorLog("【通过退款异常】");
			try {
				HttpClientUtil.post(ERROR_NOTICE_API,"errorType="+ErrorConstant.ERROR_TYPE_CEB_REFUND_ERROR+"&targetType="+ErrorConstant.EXCEPTION_TARGET_TEACHER_CAIWU+"&content="+"退款异常，请您查看审核页面重新发起审核！"+"&org_id="+Integer.parseInt(messageMap.get("org_id").toString())+"&level="+ErrorConstant.EXCEPTION_LEVEL_IMMEDIATELY+"&interval=0", "application/x-www-form-urlencoded");
			} catch (Exception e1) {
			}
		}
	}
	
	//线上自动退款发生失败时将状态置为财务审核
	private void autoOnlineRefundFailed(ChargeDetail cd,String refundMoney){
		if(StringUtils.isBlank(refundMoney)){
			try {
				HttpClientUtil.post(ERROR_NOTICE_API,"errorType="+ErrorConstant.ERROR_TYPE_CEB_REFUND_ERROR+"&targetType="+ErrorConstant.EXCEPTION_TARGET_PROGRAMMER_PAY+"&content=自动退费失败转为财务审核记录，但是创建财务审核记录时抛异常。,cd_id:"+cd.getCd_id()+"&org_id="+SJWY_ORG_ID+"&level="+ErrorConstant.EXCEPTION_LEVEL_IMMEDIATELY+"&interval=0", "application/x-www-form-urlencoded");
			} catch (Exception e1) {
			}
			return;
		}
		try {
			ChargeRecord cr = new ChargeRecord();
			cd.setOnline_pay(Constant.CHARGE_DETAIL_STATUS_REFUND_PREVIEW);
			cd.setStatus(Constant.CHARGE_DETAIL_STATUS_REFUND_APPLY);
			cd.setModify_time(new Date());
			Integer refund = chargeDetailService.update(cd);
			//创建退款记录
			if(refund==1){
				StudentClass sc = studentClassService.getById(cd.getStu_class_id());
				sc.setStuStatus(Consts.STUD_STATUS_7REFUND_AUDITED);
				studentClassService.update(sc);
				cr.setTxnType(Constant.TXNTYPE_SJWY_ONLINE_REFUND_APPLY);
				cr.setCid(cd.getCid());
				cr.setTxnAmt(refundMoney);
				cr.setCd_id(cd.getCd_id());
				cr.setTxnTime(new Date());
				cr.setInsert_time(new Date());
				cr.setOrg_user_id(0);
				cr.setUser_identify(Constant.IDENTITY_TEACHER);
				cr.setUser_type_id(0);
				cr.setUser_type_name("系统自动创建");
				cr.setIs_del(Constant.IS_DEL_NO);
				cr.setIs_show(Constant.YES);
				chargeRecordService.save(cr);
			}else{
				HttpClientUtil.post(ERROR_NOTICE_API,"errorType="+ErrorConstant.ERROR_TYPE_CEB_REFUND_ERROR+"&targetType="+ErrorConstant.EXCEPTION_TARGET_PROGRAMMER_PAY+"&content=自动退费失败转为财务审核记录，但是创建财务审核记录时失败。,cd_id:"+cd.getCd_id()+"&org_id="+SJWY_ORG_ID+"&level="+ErrorConstant.EXCEPTION_LEVEL_IMMEDIATELY+"&interval=0", "application/x-www-form-urlencoded");
			}
		} catch (Exception e) {
			try {
				HttpClientUtil.post(ERROR_NOTICE_API,"errorType="+ErrorConstant.ERROR_TYPE_CEB_REFUND_ERROR+"&targetType="+ErrorConstant.EXCEPTION_TARGET_PROGRAMMER_PAY+"&content=自动退费失败转为财务审核记录，但是创建财务审核记录时抛异常。,cd_id:"+cd.getCd_id()+"&org_id="+SJWY_ORG_ID+"&level="+ErrorConstant.EXCEPTION_LEVEL_IMMEDIATELY+"&interval=0", "application/x-www-form-urlencoded");
			} catch (Exception e1) {
			}
		}
	}
	
	private void sendRefundSuccessMessage(ChargeDetail cd,ChargeRecord cr,ChargeRecord record,String template_id4parent,String template_id4teacher){
		//通知家长
		try {
			String documentResult = HttpClientUtil.post(ESB_REQUEST_URL+"getParentPhone","stud_id="+cd.getStud_id(), "application/x-www-form-urlencoded");
			Result<List<Parent>> pResult = JsonUtil.getObjectFromJson(documentResult, new TypeReference<Result<List<Parent>>>() { });
			List<Parent> parents = pResult.getData();
			if(null!=parents&&0!=parents.size()){
				for (Parent parent : parents) {
					try {
						Map<String, Object> map = new HashMap<String, Object>();
						if(StringUtils.isNotBlank(parent.getMobile())){//SMS
							map.put("mobile", parent.getMobile());
							if(Constant.TXNTYPE_SJWY_OFFLINE_REFUND_AGREE.equals(record.getTxnType())){
								map.put("content", String.format(Constant.MOBILE_MESSAGE_CHARGE_RESULT_NOTICE,cd.getStud_name(),cd.getItem(),"的退费申请已成功提交！退款将以现金形式退还给您，请您与老师或学校财务部门联系。"));
							}else{
								map.put("content", String.format(Constant.MOBILE_MESSAGE_CHARGE_RESULT_NOTICE,cd.getStud_name(),cd.getItem(),"的退费申请已成功提交，银行退款可能需要1~7天，请关注银行的退款通知!"));
							}
						}
						if(StringUtils.isNotBlank(parent.getOpenid())){//WX
							map.put("open_id", parent.getOpenid());
							map.put("templateId", template_id4parent);
							map.put("org_id", cd.getOrg_id());
							map.put("first", cd.getStud_name()+"家长，您好！");
							map.put("keyword1", cd.getItem());
							try {
								map.put("keyword2", MoneyUtil.fromFENtoYUAN(record.getTxnAmt())+"元");
							} catch (Exception e) {
								map.put("keyword2", record.getTxnAmt()+"分");
							}
							if(Constant.TXNTYPE_SJWY_OFFLINE_REFUND_AGREE.equals(record.getTxnType())){
								map.put("keyword3", "现金退款");
								map.put("remark", "退费操作已完成，退款将以现金形式退还给您，请您与班主任老师或学校财务部门联系。感谢您的使用！");
							}else{
								map.put("keyword3", "线上退款");
								map.put("remark", "退费操作已完成，微信或银行退款可能需要1~7天，请关注微信或银行的退款通知。如有疑问请您与班主任老师或学校财务部门联系。感谢您的使用！");
							}
						}
						mqService.sendMessage("send_message_exchange", null, map);
					} catch (Exception e) {
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 【消息队列】查询收费结果 
	 * Desc:查询支付结果
	 * @param messageMap
	 * @return
	 */
	public void queryCharge(Map<String, Object> messageMap)  {
		try {
			String orderId = messageMap.get("orderId").toString();
			String clickTime = messageMap.get("clickTime").toString();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			//超时的将不做查询
			if((sdf.format(new Date()).equals(clickTime.substring(0, 8))) ||
			   (messageMap.containsKey("queryTime")&&sdf.format(new Date()).equals(messageMap.get("queryTime").toString().substring(0, 8)))){
				ChargeDetail chargeDetail = chargeDetailService.getByOrderIdForQuery(orderId);
				//没有查到支付记录的进行二次查询
				if(null!=chargeDetail&&null==chargeDetail.getQueryId()){
					if(chargeDetail.getAccType().intValue()==Constant.ACCTYPE_UNIONPAY.intValue()){
						messageMap.put("certPath", chargeDetail.getSignCert());
						messageMap.put("certPwd", chargeDetail.getPwd());
						String merId = messageMap.get("merId").toString();
						Map<String, String> data = new HashMap<String, String>();
						
						/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
						data.put("version", SDKConfig.getConfig().getVersion());                 //版本号
						data.put("encoding", "UTF-8");               //字符集编码 可以使用UTF-8,GBK两种方式
						data.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
						data.put("txnType", "00");                             //交易类型 00-默认
						data.put("txnSubType", "00");                          //交易子类型  默认00
						data.put("bizType", "000201");                         //业务类型 B2C网关支付，手机wap支付
						data.put("merId", merId);                  //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
						data.put("accessType", "0");                           //接入类型，商户接入固定填0，不需修改
						data.put("orderId", orderId);                 //****商户订单号，每次发交易测试需修改为被查询的交易的订单号
						data.put("txnTime", clickTime);                 //****订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间
						
						doQuery(messageMap,data);
					}else if(chargeDetail.getAccType().intValue()==Constant.ACCTYPE_CEB.intValue()){
						doCebQuery(messageMap,chargeDetail);
					}
				}else{
					//查询到支付记录【正常完成支付的订单绝大多数都应该进入此逻辑】
				}
			}else{
				//连续一天都没有查询到有效结果,放弃查询，向管理员发送警告信息
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			reQuery("15分钟错误异常",messageMap);
		}
	}
	
	private void doCebQuery(Map<String, Object> messageMap,ChargeDetail chargeDetail){
		if(StringUtils.isNotBlank(chargeDetail.getBillNum())){
			if(null==chargeDetail.getTxnTime()){
				chargeDetail.setTxnTime(new Date());
			}
			String xml = CebUtils.getXML(Constant.CEB_TYPE_QUERY_PAY, chargeDetail, null);
			org.json.JSONObject data = CebUtils.getJSONObjectByPostXML(CEB_QUERY_PAY_URL, xml);
			if(data.has("Out")&&data.getJSONObject("Out").has("Body")&&data.getJSONObject("Out").has("Head")){
				if(data.getJSONObject("Out").getJSONObject("Body").has("ErrorCode")&&data.getJSONObject("Out").getJSONObject("Body").get("ErrorCode").toString().equals(Constant.CEB_ANSCODE_LJTK012)){
					//查询返回“没有交易记录”，放弃继续查询
				} else if((data.getJSONObject("Out").getJSONObject("Head").has("AnsCode")&&data.getJSONObject("Out").getJSONObject("Head").get("AnsCode").toString().equals(Constant.CEB_ANSCODE_SUCCESS))||
						(data.getJSONObject("Out").getJSONObject("Body").has("tranStat")&&data.getJSONObject("Out").getJSONObject("Body").get("tranStat").toString().equals(Constant.CEB_ANSCODE_TRANSTAT_UNKNOWN))||
						(data.getJSONObject("Out").getJSONObject("Body").has("tranStat")&&data.getJSONObject("Out").getJSONObject("Body").get("tranStat").toString().equals(Constant.CEB_ANSCODE_TRANSTAT_SUCCESS))){
					String errorMsg = reviewPayStatus(data);
					if(StringUtils.isNotBlank(errorMsg)){
						reQuery(errorMsg,messageMap);
					}
				}else{
					reQuery("CEB查询失败,未知异常,cd_id:"+chargeDetail.getCd_id(),messageMap);
				}
			}else{
				reQuery("CEB查询失败,光大接口超时,cd_id:"+chargeDetail.getCd_id(),messageMap);
			}
		}else{
			reQuery("CEB查询失败,机构没有billNum",messageMap);
		}
	}
	

	/**
	 * @Title : reviewPay 
	 * @功能描述:MQ调用光大支付校正方法 （新增支付记录）
	 * @返回类型：void 
	 * @throws ：
	 */
	public void reviewPay(Map<String, Object> messageMap) {
		reviewPayStatus(new org.json.JSONObject(messageMap.get("json").toString()));
	}

	/**
	 * @Title : reviewPayStatus 
	 * @功能描述: 纠正支付结果
	 * @返回类型：String 
	 * @throws ：SNG
	 */
	public String reviewPayStatus(org.json.JSONObject data){
		org.json.JSONObject body = data.getJSONObject("Out").getJSONObject("Body");
		
		ChargeRecord cr = new ChargeRecord();
		try {
			if(body.has("cr_id")&&StringUtils.isNotBlank(body.get("cr_id").toString())){
				cr = chargeRecordService.getById(Integer.parseInt(body.get("cr_id").toString()));
			}
		} catch (Exception e) {
		}
		String txnType = Constant.TXNTYPE_UNIONPAY_PAY;
		String txnTime = body.get("TradeDate").toString()+"000000";
		String txnAmt = "";
		try {
			txnAmt = MoneyUtil.fromYUANtoFEN(body.get("tranfee").toString());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String queryId = body.get("TradeNo").toString();
		String respMsg = Constant.CEB_RESPMSG_SUCCESS;
		String settleAmt = txnAmt;
		String settleDate = body.get("TradeDate").toString().substring(4);
		String traceTime = txnTime.substring(4);
		String cd_id = body.get("payNo").toString();
		
		String time = txnTime.substring(0,4)+"-"+txnTime.substring(4,6)+"-"+txnTime.substring(6,8)+" "+txnTime.substring(8,10)+":"+txnTime.substring(10,12)+":"+txnTime.substring(12,14);
		
		ChargeDetail cd = chargeDetailService.getByIdForCorrect(Integer.parseInt(cd_id));
		if(null!=cd&&null!=cd.getCid()){
			Charge charge = chargeService.getById(cd.getCid());
			cd.setOnline_pay(Constant.CHARGE_DETAIL_STATUS_OK);
			cd.setStatus(Constant.CHARGE_DETAIL_STATUS_OK);
			cd.setTxnAmt(txnAmt);
			try {
				cd.setTxnTime(Constant.sdf.parse(time));
			} catch (ParseException e) {
				cd.setTxnTime(new Date());
			}
			cd.setModify_time(new Date());
			cd.setOrderDesc(charge.getItem()+"【来自"+cd.getClas_name()+"学生"+cd.getStud_name()+"的家长】");
			
			cr.setCid(cd.getCid());
			cr.setCd_id(cd.getCd_id());
			cr.setRespMsg(respMsg);
			cr.setRespCode(Constant.CEB_ANSCODE_SUCCESS);
			cr.setSettleAmt(settleAmt);
			cr.setQueryId(queryId);
			cr.setTxnAmt(txnAmt);
			cr.setTxnType(txnType);
			cr.setTraceTime(traceTime);
			cr.setSettleDate(settleDate);
			try {
				cr.setTxnTime(Constant.sdf.parse(time));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cr.setInsert_time(new Date());
			
			Boolean isSuccess = chargeService.chargeFinally(charge,cd,cr,Consts.STUD_STATUS_5PAID,Constant.YES,true);
			if(isSuccess){
				Map<String, Object> documentMap = new HashMap<String, Object>();
				documentMap.put("stud_id", cd.getStud_id());
				List<Parent> parents = new ArrayList<Parent>();
				
				try {
					String documentResult = HttpClientUtil.post(ESB_REQUEST_URL+"getParentPhone","stud_id="+cd.getStud_id(), "application/x-www-form-urlencoded");
					Result<List<Parent>> pResult = JsonUtil.getObjectFromJson(documentResult, new TypeReference<Result<List<Parent>>>() { });
					parents = pResult.getData();
					String template_id = "";
					try {
						template_id = tokenService.getSNGWXTemplateId(cd.getOrg_id().toString(), Constant.TEMPLATE_NAME_CHARGE_SUCCESS_NOTICE);
					} catch (Exception e) {
					}
					if(null!=parents&&parents.size()!=0){
						for (Parent p : parents) {
							Map<String, Object> map = new HashMap<String, Object>();
							if(StringUtils.isNotBlank(p.getMobile())){//SMS
								map.put("cd_id", cd.getCd_id());
								map.put("mobile", p.getMobile());
								map.put("content", String.format(Constant.MOBILE_MESSAGE_CHARGE_SUCCESS_FOR_PARENT,cd.getStud_name(),cd.getItem()));
							}
							if(StringUtils.isNotBlank(p.getOpenid())&&StringUtils.isNotBlank(template_id)){//WX
								map.put("open_id", p.getOpenid());
								map.put("templateId", template_id);
								map.put("cd_id", cd.getCd_id());
								map.put("org_id", cd.getOrg_id());
								map.put("first", "尊敬的家长，您已支付成功！");
								map.put("keyword1", cd.getItem());
								map.put("keyword2", p.getParent_name());
								map.put("keyword3", cd.getMoney()+"元");
								map.put("keyword4", time);
								map.put("remark", "感谢您的使用！");
							}
							mqService.sendMessage("send_message_exchange", null, map);
						}
					}
				} catch (Exception e) {
					LogUtil.writeErrorLog("【15分钟光大错误】发送支付通知时发生异常，未能向stud_id="+cd.getStud_id()+"的学生家长发送支付成功通知!");
				}
				return "";
			}else{
				return "更新支付数据的事物失败，执行了ROLLBACK返回"+cd_id;
			}
		}else{
			return "获取支付信息失败"+cd_id;
		}
	}

	private void reQuery(String errorMessage,Map<String, Object> messageMap){
		mqService.sendMessage("charge_query_delay_exchange", null, messageMap);
	}

	
	private void doQuery(Map<String, Object> messageMap,Map<String, String> data){
		/**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/
		Map<String, String> submitFromData  = AcpService.signByCertInfo(data,messageMap.get("certPath").toString(), messageMap.get("certPwd").toString(), "utf-8");
		String url = SDKConfig.getConfig().getBackRequestUrl();//交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
		Map<String, String> rspData = AcpService.post(submitFromData,url,Constant.encoding);//这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
		
		/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
		//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
		if(!rspData.isEmpty()){
			if(AcpService.validate(rspData, "UTF-8")){
				if("00".equals(rspData.get("respCode"))){//如果查询交易成功
					//处理被查询交易的应答码逻辑
					String origRespCode = rspData.get("origRespCode");
					if("00".equals(origRespCode)){
						//交易成功，更新商户订单状态
						try {
							ChargeDetail temp = new ChargeDetail();
							temp.setOrder_id(messageMap.get("orderId").toString());
							ChargeRecord cr = new ChargeRecord();
							String txnType = rspData.get("txnType");
							String txnAmt = rspData.get("txnAmt");
							String reqReserved = rspData.get("reqReserved");
							String queryId = rspData.get("queryId");
							String respMsg = rspData.get("respMsg");
							String settleAmt = rspData.get("settleAmt");
							String settleDate = rspData.get("settleDate");
							String traceNo = rspData.get("traceNo");
							String traceTime = rspData.get("traceTime");
							String accNo = rspData.get("accNo");
							
							Base64 base64 = new Base64();
							String str = new String(base64.decode(reqReserved), "UTF-8");
							String[] params = str.split("\\|");
							String user_id = params[1];
							String parent_id = params[2];
							String parent_name = params[3];
							String org_name = params[5];
							String identity = params[7];
							
							ChargeDetail cd = chargeDetailService.getByOrderId(messageMap.get("orderId").toString());
							if(null!=cd&&null!=cd.getCid()){
								Charge charge = chargeService.getById(cd.getCid());
								cd.setOnline_pay(Constant.CHARGE_DETAIL_STATUS_OK);
								cd.setStatus(Constant.CHARGE_DETAIL_STATUS_OK);
								cd.setTxnAmt(txnAmt);
								String time = data.get("txnTime").substring(0,4)+"-"+data.get("txnTime").substring(4,6)+"-"+data.get("txnTime").substring(6,8)+" "+data.get("txnTime").substring(8,10)+":"+data.get("txnTime").substring(10,12)+":"+data.get("txnTime").substring(12,14);
								try {
									cd.setTxnTime(Constant.sdf.parse(time));
								} catch (ParseException e) {
									cd.setTxnTime(new Date());
								}
								cd.setModify_time(new Date());
								cd.setOrderDesc(charge.getItem()+"【来自"+org_name+cd.getClas_name()+"学生"+cd.getStud_name()+"的家长"+parent_name+"】");
								
								cr.setCid(cd.getCid());
								cr.setCd_id(cd.getCd_id());
								cr.setRespMsg(respMsg);
								cr.setRespCode(origRespCode);
								cr.setAccNo(accNo);
								cr.setTraceNo(traceNo);
								cr.setSettleAmt(settleAmt);
								cr.setQueryId(queryId);
								cr.setTxnAmt(txnAmt);
								cr.setTxnType(txnType);
								cr.setTraceTime(traceTime);
								cr.setSettleDate(settleDate);
								try {
									cr.setTxnTime(Constant.sdf.parse(time));
								} catch (ParseException e) {
									e.printStackTrace();
								}
								cr.setOrg_user_id(Integer.parseInt(user_id));
								cr.setUser_identify(Integer.parseInt(identity));
								cr.setUser_type_id(Integer.parseInt(parent_id));
								cr.setUser_type_name(parent_name);
								cr.setInsert_time(new Date());
								
								Boolean isSuccess = chargeService.chargeFinally(charge, cd, cr, Consts.STUD_STATUS_5PAID,Constant.YES,true);
								if(isSuccess){
									Map<String, Object> documentMap = new HashMap<String, Object>();
									documentMap.put("stud_id", cd.getStud_id());
									List<Parent> parents = new ArrayList<Parent>();
									try {
										String documentResult = HttpClientUtil.post(ESB_REQUEST_URL+"getParentPhone","stud_id="+cd.getStud_id(), "application/x-www-form-urlencoded");
										LogUtil.writeLog(documentResult);
										Result<List<Parent>> pResult = JsonUtil.getObjectFromJson(documentResult, new TypeReference<Result<List<Parent>>>() { });
										parents = pResult.getData();
										String template_id = "";
										try {
											template_id = tokenService.getSNGWXTemplateId(cd.getOrg_id().toString(), Constant.TEMPLATE_NAME_CHARGE_SUCCESS_NOTICE);
											LogUtil.writeLog(template_id);
										} catch (Exception e) {
										}
										if(null!=parents&&parents.size()!=0){
											LogUtil.writeLog(parents.size()+"");
											for (Parent p : parents) {
												LogUtil.writeLog(JsonUtil.toJson(p));
												Map<String, Object> map = new HashMap<String, Object>();
												if(StringUtils.isNotBlank(p.getMobile())){//SMS
													map.put("cd_id", cd.getCd_id());
													map.put("mobile", p.getMobile());
													map.put("content", String.format(Constant.MOBILE_MESSAGE_CHARGE_SUCCESS_FOR_PARENT,cd.getStud_name(),cd.getItem()));
												}
												if(StringUtils.isNotBlank(p.getOpenid())&&StringUtils.isNotBlank(template_id)){//WX
													map.put("open_id", p.getOpenid());
													map.put("templateId", template_id);
													map.put("cd_id", cd.getCd_id());
													map.put("org_id", cd.getOrg_id());
													map.put("first", "尊敬的家长，您已支付成功！");
													map.put("keyword1", cd.getItem());
													map.put("keyword2", parent_name);
													map.put("keyword3", cd.getMoney()+"元");
													map.put("keyword4", time);
													map.put("remark", "感谢您的使用！");
												}
												mqService.sendMessage("send_message_exchange", null, map);
											}
										}
									} catch (Exception e) {
										LogUtil.writeErrorLog("【错误】发送支付成功结果时发生异常，未能向stud_id="+cd.getStud_id()+"的学生家长发送任何支付结果通知!");
									}
								}else{
									reQuery("chargeFinally调用时抛出异常",messageMap);
									cd.setOnline_pay(Constant.CHARGE_DETAIL_STATUS_NEVER);
									cd.setStatus(Constant.CHARGE_DETAIL_STATUS_NEVER);
									cd.setTxnAmt(null);
									cd.setTxnTime(null);
									cd.setModify_time(new Date());
									cd.setOrderDesc(charge.getItem());
									chargeDetailService.update(cd);
								}
							}else{
								reQuery("orderId获取订单失败",messageMap);
							}
						}catch(Exception e){
							e.printStackTrace();
							reQuery("异常",messageMap);
						}
					}else if("03".equals(origRespCode) ||
							 "04".equals(origRespCode) ||
							 "05".equals(origRespCode)){
						// 判断是否需要用前一天的时期查询
						if(messageMap.containsKey("queryTime")&&!messageMap.containsKey("queryed")){
							messageMap.put("queryed", true);
							data.put("txnTime", messageMap.get("queryTime").toString()); 
							doQuery(messageMap, data);
						}else if(messageMap.containsKey("queryed")){	
							messageMap.remove("queryed");
							reQuery("origRespCode查询“查询日期”的订单失败，未支付或者未查到原交易",messageMap);
						}else{
							reQuery("origRespCode查询失败，未支付或者未查到原交易",messageMap);
						}
					}else{
						reQuery("其他应答码为失败请排查原因："+origRespCode,messageMap);
					}
				}else{
					// 判断是否需要用前一天的时期查询
					if(messageMap.containsKey("queryTime")&&!messageMap.containsKey("queryed")){
						messageMap.put("queryed", true);
						data.put("txnTime", messageMap.get("queryTime").toString()); 
						doQuery(messageMap, data);
					}else if(messageMap.containsKey("queryed")){	
						messageMap.remove("queryed");
						reQuery("查询“查询日期”的订单失败，未支付或者未查到原交易",messageMap);
					}else{
						reQuery("查询失败，未支付或者未查到原交易",messageMap);
					}
				}
			}else{
				reQuery("验证签名失败",messageMap);
			}
		}else{
			reQuery("未获取到返回报文或返回http状态码非200",messageMap);
		}
	}
	
	public void checkBill(String type) {
		if(Constant.CEB_TYPE_PAY_BILL.equals(type)){
			checkPayBill();
		}else if(Constant.CEB_TYPE_REFUND_BILL.equals(type)){
			checkRefundBill();
		}
	}
	
	private void checkRefundBill() {
	}

    public void checkPayBill(){
    	StringBuffer errInfo = new StringBuffer();
    	StringBuffer reviewInfo = new StringBuffer();
    	Map<String,ChargeDetail> paidMap = new HashMap<String,ChargeDetail>();
    	Map<String,Object> mqMap = new HashMap<String,Object>();
    	//获取昨天的日期
    	Date date=new Date();
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.DATE, -1);
    	date = calendar.getTime();
    	//获取当天所有记录
    	List<ChargeDetail> paidList = chargeDetailService.getRecordList(Constant.ACCTYPE_CEB,Constant.ceb.format(date),Constant.TXNTYPE_UNIONPAY_PAY);
    	for (ChargeDetail paid : paidList) {
			if(paidMap.containsKey(paid.getQueryId())){
				errInfo.append("[重复流水号]").append(paid.getQueryId()).append(";");
			}else{
				paidMap.put(paid.getQueryId(), paid);
			}
		}
    	//获取所有的fileNum
    	List<Account> list = chargeService.getCEBList();
    	try {
			LogUtil.writeErrorLog("accountList"+JsonUtil.toJson(list));
		} catch (IOException e1) {
			LogUtil.writeErrorLog("accountList"+list.size());
		}
    	String filename = "";
    	ChargeDetail cd = new ChargeDetail();
    	for (Account account : list) {
    		LogUtil.writeErrorLog("account"+account.getFileNum());
			if(StringUtils.isNotBlank(account.getFileNum())){
				filename = Constant.ceb.format(date)+File.separator+"SJWY_"+Constant.ceb.format(date)+"_"+account.getFileNum()+".txt";
				LogUtil.writeErrorLog("filename"+filename);
				List<String[]> billList = getBillByName(filename);
		    	try {
					LogUtil.writeErrorLog("billList"+JsonUtil.toJson(billList));
				} catch (IOException e1) {
					LogUtil.writeErrorLog("billList"+billList.size());
				}
				for (String[] strs : billList) {
					LogUtil.writeErrorLog("strs"+strs);
					//1.判断对账文件内容行是否正确
					if(strs.length==6){
						//2.判断对账文件有效内容是否在数据库中存在记录
						LogUtil.writeErrorLog("111111111111111111111111");
						if(paidMap.containsKey(strs[1])){
							cd=paidMap.get(strs[1]);
							LogUtil.writeErrorLog("22222222222222222222222222222");
							//3.判断支付状态是否成功
							if(cd.getStatus()>Constant.CHARGE_DETAIL_STATUS_NEVER&&cd.getOnline_pay()>Constant.CHARGE_DETAIL_STATUS_NEVER){
								LogUtil.writeErrorLog("333333333333333333333333333");
								try {
									if(MoneyUtil.fromYUANtoFEN(strs[4]).equals(cd.getTxnAmt())){
										LogUtil.writeErrorLog("4444444444444444444444444");
										//正常
									}else{
										//金额不一样，修正（cd中的txnAmt取的是支付记录chargeRecord中的金额txnAmt）
										LogUtil.writeErrorLog("555555555555555555555555555");
										//【坑】特殊用法，把cr_id暂存在is_del字段中了
										mqMap.put("json", "{\"Out\":{\"Body\":{\"cr_id\":\""+cd.getIs_del()+"\",\"TradeDate\":\""+strs[0]+"\",\"tranfee\":\""+strs[4]+"\",\"TradeNo\":\""+strs[1]+"\",\"payNo\":\""+strs[3]+"\"}}}");
										reviewInfo.append("[发起校正1:").append(account.getFileNum()).append("]").append(strs[1]).append(";");
										mqService.sendMessage("review_pay_status_exchange", null, mqMap);
									}
								} catch (Exception e) {
									//这条记录验证失败，对账文件中的金额异常
									errInfo.append("[对账文件金额异常:").append(account.getFileNum()).append("]").append(strs[1]).append(";");
									LogUtil.writeErrorLog("6666666666666666666666666666");
								}
							}else{
								//ChargeDetail的状态是未支付【极罕见】;【坑】特殊用法，把cr_id暂存在is_del字段中了
								mqMap.put("json", "{\"Out\":{\"Body\":{\"cr_id\":\""+cd.getIs_del()+"\",\"TradeDate\":\""+strs[0]+"\",\"tranfee\":\""+strs[4]+"\",\"TradeNo\":\""+strs[1]+"\",\"payNo\":\""+strs[3]+"\"}}}");
								reviewInfo.append("[发起校正2:").append(account.getFileNum()).append("]").append(strs[1]).append(";");
								mqService.sendMessage("review_pay_status_exchange", null, mqMap);
								LogUtil.writeErrorLog("7777777777777777777777777777");
							}
							paidMap.remove(strs[1]);
						}else{
							//对账文件有一条记录不在数据库中，修正
							mqMap.put("json", "{\"Out\":{\"Body\":{\"TradeDate\":\""+strs[0]+"\",\"tranfee\":\""+strs[4]+"\",\"TradeNo\":\""+strs[1]+"\",\"payNo\":\""+strs[3]+"\"}}}");
							reviewInfo.append("[发起校正3:").append(account.getFileNum()).append("]").append(strs[1]).append(";");
							mqService.sendMessage("review_pay_status_exchange", null, mqMap);
							LogUtil.writeErrorLog("88888888888888888888888");
						}
					}
				}
			}
		}
    	//处理过的记录都已经从paidMap中删除，剩下的是对账文件中没有的
    	for (String key : paidMap.keySet()) { 
    		errInfo.append("[对账文件中未找到]").append(key).append(";");
		} 
    	LogUtil.writeErrorLog("errInfo"+errInfo.toString());
    	if(StringUtils.isNotBlank(errInfo.toString())){
    		try {
    			HttpClientUtil.post(ERROR_NOTICE_API,"errorType="+ErrorConstant.ERROR_TYPE_CEB_BILL_ERROR+"&targetType="+ErrorConstant.EXCEPTION_TARGET_PROGRAMMER_PAY+"&content="+"[SNG][未处置]"+errInfo.toString()+"&org_id="+SJWY_ORG_ID+"&level="+ErrorConstant.EXCEPTION_LEVEL_IMMEDIATELY+"&interval=0", "application/x-www-form-urlencoded");
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	LogUtil.writeErrorLog("errInfo"+reviewInfo.toString());
    	if(StringUtils.isNotBlank(reviewInfo.toString())){
    		try {
    			HttpClientUtil.post(ERROR_NOTICE_API,"errorType="+ErrorConstant.ERROR_TYPE_CEB_BILL_ERROR+"&targetType="+ErrorConstant.EXCEPTION_TARGET_PROGRAMMER_PAY+"&content="+"[SNG][已处置]"+reviewInfo.toString()+"&org_id="+SJWY_ORG_ID+"&level="+ErrorConstant.EXCEPTION_LEVEL_WORKTIME+"&interval=0", "application/x-www-form-urlencoded");
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    }

    /**
     * @Title : getMap 
     * @功能描述: 获取对账map
     */
    private List<String[]> getBillByName(String filename){
    	List<String[]> list = new ArrayList<String[]>();
    	LogUtil.writeErrorLog("filenamemamsamdsamdamsdamsdasd"+BILL_CHECK_FILE_PATH+filename);
		File file = new File(BILL_CHECK_FILE_PATH+filename);
    	BufferedReader reader = null;
    	try {
    		reader = new BufferedReader(new FileReader(file));
    		String tempString = null;
    		// 一次读入一行，直到读入null为文件结束
    		while ((tempString = reader.readLine()) != null) {
    			list.add(tempString.split("\\|"));
    		}
    		reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
    	return list;
    }
}
