package sng.controller.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sng.entity.Account;
import sng.entity.CertifiationRecord;
import sng.pojo.Result;
import sng.service.AccountService;
import sng.service.CertificationRecordService;
import sng.service.UserAuthService;
import sng.util.Constant;
import sng.util.PropertyReader;
import unionpay.acp.sdk.AcpService;
import unionpay.acp.sdk.LogUtil;
import unionpay.acp.sdk.SDKConfig;

/**
 * @类 名： CertificationController
 * @功能描述： 认证
 * @作者信息： LiuYang
 * @创建时间： 2018年12月16日下午3:33:40
 */
@RestController
@RequestMapping("/portal/certification")
public class CertificationController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static String CERTIFICATION_PAY_RESULT = PropertyReader.getCommonProperty("certification");
	private static String MER_ID = PropertyReader.getCommonProperty("merId");
	private static String BACK_URL = PropertyReader.getProperty("backCertUrl");
	private static String FRONT_URL = PropertyReader.getProperty("frontCertUrl");
	private static Integer SJWY_ORG_ID = Integer.parseInt(PropertyReader.getProperty("sjwy_org_id"));
	
	@Autowired
	private UserAuthService userAuthService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private CertificationRecordService certificationRecordService;
	
	/**
	 * @Title : certification  
	 * @功能描述:提交认证缴费
	 * @返回类型：void 
	 * @throws ：
	 */
	@RequestMapping(value = "/certification.htm")
	public void certification(HttpServletRequest request,
			@RequestParam(name = "user_register_id") String user_register_id,
			@RequestParam(name = "name") String name,
			@RequestParam(name = "id_number") String id_number,
			@RequestParam(name = "org_id") Integer org_id,
			@RequestParam(name = "open_id") String open_id,
			HttpServletResponse response, HttpSession session) throws Exception {
		Result<Map<String,Object>> result = new Result<Map<String,Object>>();
		if(userAuthService.isIdNumberExist(id_number, org_id, "", Constant.IDENTITY_STUDENT)){
			logger.error("身份证号已存在！");
			result.setMessage("身份证号已存在！");
			result.setSuccess(false);
			request.setAttribute("result", result);
			request.getRequestDispatcher(CERTIFICATION_PAY_RESULT).forward(request, response);
			return;
		}
		result.setSuccess(true);
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
		String reqREserved = open_id+"|"+org_id+"|"+user_register_id+"|"+name+"|"+id_number;
		byte[] textByte = reqREserved.getBytes("UTF-8");
		String encodedText = base64.encodeToString(textByte);
		
		Date txnTime = new Date();
		String order_id = id_number+(new Date().getTime());
		
		//编码
		/***商户接入参数***/
		requestData.put("merId", MER_ID);    	          			  //商户号码，请改成自己申请的正式商户号或者open上注册得来的777290058112538测试商户号
		requestData.put("accessType", "0");             			  //接入类型，0：直连商户 
		requestData.put("orderId",order_id);             //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则		
		requestData.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(txnTime.getTime()));        //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		requestData.put("currencyCode", "156");         			  //交易币种（境内商户一般是156 人民币）		
		requestData.put("txnAmt", Constant.CERTIFY_FEE);             			      //交易金额，单位分，不要带小数点
		requestData.put("reqReserved",  encodedText);  //请求方保留域，如需使用请启用即可；透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节。出现&={}[]符号时可能导致查询接口应答报文解析失败，建议尽量只传字母数字并使用|分割，或者可以最外层做一次base64编码(base64编码之后出现的等号不会导致解析失败可以不用管)。		
		requestData.put("frontUrl", FRONT_URL);
		requestData.put("backUrl", BACK_URL);
		requestData.put("payTimeout", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime() + 15 * 60 * 1000));
		Account acc = accountService.getByOrgId(SJWY_ORG_ID,Constant.ACCOUNT_TYPE_ORG,Constant.ACCTYPE_UNIONPAY); 
		
		/**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
		Map<String, String> submitFromData = AcpService.signByCertInfo(requestData,acc.getSignCert(), acc.getPwd(), "utf-8");
		CertifiationRecord cfr = new CertifiationRecord();
		cfr.setId_number(id_number);
		cfr.setName(name);
		cfr.setOrder_id(order_id);
		cfr.setRegister_id(Integer.parseInt(user_register_id));
		cfr.setTime(txnTime);
		cfr.setTxnAmt(Constant.CERTIFY_FEE);
		certificationRecordService.savee(cfr);
		String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();  //获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
		String html = AcpService.createAutoFormHtml(requestFrontUrl, submitFromData,"UTF-8");   //生成自动跳转的Html表单
		
		LogUtil.writeLog("打印请求HTML，此为请求报文，为联调排查问题的依据："+html);
		//将生成的html写到浏览器中完成自动跳转打开银联支付页面；这里调用signData之后，将html写到浏览器跳转到银联页面之前均不能对html中的表单项的名称和值进行修改，如果修改会导致验签不通过
		response.getWriter().write(html);
	}
}
